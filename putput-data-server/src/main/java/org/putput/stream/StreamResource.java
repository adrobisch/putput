package org.putput.stream;

import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import org.apache.commons.lang3.tuple.Pair;
import org.putput.api.model.*;
import org.putput.api.resource.Stream;
import org.putput.common.web.BaseResource;
import org.putput.stream.html.StreamItemHtmlProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.StreamingOutput;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

@Controller
public class StreamResource extends BaseResource implements Stream {

  @Autowired
  StreamItemService streamItemService;

  @Autowired
  StreamItemHtmlProcessor itemHtmlProcessor;

  @Override
  public GetStreamResponse getStream(String profile,
                                     String type,
                                     BigDecimal page) throws Exception {
    Page<StreamItemEntity> streamItemPage = getItems(profile, type, page);

    List<org.putput.api.model.StreamItemResource> streamItems = streamItemPage
        .getContent()
        .stream()
        .map(entityToItem())
        .map(itemToItemResource())
        .collect(Collectors.toList());

    StreamItemListLinks links = new StreamItemListLinks()
        .withSelf(link(Stream.class));

    if (streamItemPage.hasNext()) {
      Map<String, String> params = nextPageParams(streamItemPage);
      ofNullable(profile).map(profileValue -> params.put("profile", profileValue));
      ofNullable(type).map(typeValue -> params.put("type", typeValue));

      links.withNextPage(link(Stream.class, params));
    }

    return GetStreamResponse.withHaljsonOK(new StreamItemList()
        .withItems(streamItems)
        .withLinks(links));
  }

  @Override
  public GetStreamRssByUserNameResponse getStreamRssByUserName(String userName) throws Exception {
    StreamingOutput rssOutput = outputStream -> {
      PageRequest pageable = new PageRequest(0, 30);

      SyndFeed feed = new SyndFeedImpl();
      feed.setFeedType("rss_2.0");

      feed.setTitle("PutPut RSS");
      feed.setLink("https://putput.org/api/stream/rss/"+userName);
      feed.setDescription("PutPut feed for @" + userName);

      feed.setEntries(rssEntries(userName, pageable));
      try {
        outputStream.write(new SyndFeedOutput().outputString(feed, true).getBytes("UTF-8"));
      } catch (FeedException e) {
        throw new RuntimeException(e);
      }
    };

    return GetStreamRssByUserNameResponse.withRssxmlOK(rssOutput);
  }

  public List<SyndEntry> rssEntries(String userName, PageRequest pageable) {
    return streamItemService
        .getByUserName(userName, empty(), pageable)
        .getContent()
        .stream()
        .map(item -> {
          SyndEntry entry = new SyndEntryImpl();
          entry.setTitle(item.getTitle());
          entry.setAuthor(userName);
          entry.setLink("https://www.putput.org/#/item/" + item.getId());
          entry.setPublishedDate(item.getCreatedDate());
          SyndContent description = new SyndContentImpl();
          description.setType("text/plain");
          description.setValue(item.getContent());
          entry.setDescription(description);
          return entry;
        }).collect(Collectors.toList());
  }

  private Page<StreamItemEntity> getItems(String profile, String type, BigDecimal page) {
    Optional<String> optionalType = ofNullable(type);
    Optional<String> optionalProfile = Optional.ofNullable(profile);

    Pageable pageable = pageable(ofNullable(page).orElse(new BigDecimal(0)));

    if (optionalProfile.isPresent()) {
      return streamItemService.getByUserName(optionalProfile.get(),
          optionalType,
          pageable);
    } else if (optionalType.isPresent()){
      return streamItemService.getByUserName(optionalProfile.orElse(user().getUsername()),
          optionalType,
          pageable);
    } else {
      return streamItemService.getFollowedByUserName(user().getUsername(), pageable);
    }
  }

  private Function<StreamItem, StreamItemResource> itemToItemResource() {
    return item -> new StreamItemResource()
        .withStreamItem(item)
        .withMarkerInfo(markerInfo(user().getUsername(), item))
        .withLinks(itemLinks(item));
  }

  private MarkerInfo markerInfo(String username, StreamItem item) {
    return streamItemService.getMarkerInfo(username, item.getId());
  }

  private StreamItemLinks itemLinks(StreamItem item) {
    StreamItemLinks streamItemLinks = new StreamItemLinks();
    HalLink self = link(Stream.class, "item", item.getId());

    streamItemLinks.withSelf(self);
    if (item.getCreator().equals(user().getUsername())) {
      streamItemLinks.withDelete(self);
    }

    return streamItemLinks;
  }

  Function<StreamItemEntity, StreamItem> entityToItem() {
    return streamItemEntity -> {
      Pair<String, List<StreamItemMedia>> htmlAndMedia = itemHtmlProcessor.itemHtml(streamItemEntity);

      return new StreamItem()
              .withId(streamItemEntity.getId())
              .withContent(htmlAndMedia.getKey())
              .withMedia(htmlAndMedia.getValue())
              .withCreated(streamItemEntity.getCreated().doubleValue())
              .withCreator(streamItemEntity.getAuthor().getUsername())
              .withSource(streamItemEntity.getSource().map(StreamItemSource::value).orElse(null))
              .withTitle(streamItemEntity.getTitle())
              .withExternalReference(streamItemEntity.getExternalRef().orElse(null))
              .withReference(streamItemEntity.getItemRef());
    };
  }

  @Override
  public PostStreamItemsResponse postStreamItems(NewStreamItem newItem) throws Exception {
    String newItemId = streamItemService.newUserItem(newItem,
        user().getUsername(),
        Optional.ofNullable(newItem.getReference()));
    return PostStreamItemsResponse.withCreated(link(Stream.class, "item", newItemId).getHref());
  }

  @Override
  public GetStreamItemByIdResponse getStreamItemById(String id) throws Exception {
    Optional<StreamItemEntity> itemEntity = streamItemService.getById(id);
    if (!itemEntity.isPresent()) {
      return GetStreamItemByIdResponse.withNotFound();
    } else {
      StreamItem streamItem = entityToItem().apply(itemEntity.get());
      return GetStreamItemByIdResponse.withHaljsonOK(itemToItemResource().apply(streamItem));
    }
  }

  @Override
  public DeleteStreamItemByIdResponse deleteStreamItemById(String id) throws Exception {
    try {
      streamItemService.deleteUserItem(user().getUsername(), id);
      return DeleteStreamItemByIdResponse.withOK();
    } catch (IllegalArgumentException illegalArgument) {
      return DeleteStreamItemByIdResponse.withBadRequest();
    }
  }
}
