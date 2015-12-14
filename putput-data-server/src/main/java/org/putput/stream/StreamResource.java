package org.putput.stream;

import org.putput.api.model.*;
import org.putput.api.resource.Stream;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Controller
public class StreamResource extends BaseResource implements Stream {

  @Autowired
  StreamItemService streamItemService;

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
      Map<String, Object> params = new HashMap<>();
      params.put("page", streamItemPage.nextPageable().getPageNumber());
      links.withNextPage(link(Stream.class, params));
    }

    return GetStreamResponse.withHaljsonOK(new StreamItemList()
        .withItems(streamItems)
        .withLinks(links));
  }

  private Page<StreamItemEntity> getItems(String profile, String type, BigDecimal page) {
    return streamItemService.getByUserName(Optional.ofNullable(profile).orElse(user().getUsername()),
        ofNullable(type),
        pageable(ofNullable(page).orElse(new BigDecimal(0))));
  }

  Pageable pageable(BigDecimal page) {
    return new PageRequest(page.intValue(), 15);
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
    return streamItemEntity -> new StreamItem()
        .withId(streamItemEntity.getId())
        .withContent(streamItemEntity.getContent())
        .withCreated(streamItemEntity.getCreated().doubleValue())
        .withCreator(streamItemEntity.getAuthor().getUsername())
        .withSource(streamItemEntity.getSource().map(StreamItemSource::value).orElse(null))
        .withTitle(streamItemEntity.getTitle());
  }

  @Override
  public PostStreamItemsResponse postStreamItems(NewStreamItem entity) throws Exception {
    StreamItemEntity newItem = streamItemService.newItemEntity(user().getUsername(),
        entity.getContent(),
        ofNullable(entity.getTitle()),
        ofNullable(entity.getSource()));

    return PostStreamItemsResponse.withCreated(link(Stream.class, "item", newItem.getId()).getHref());
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
