package org.putput.rss;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rometools.fetcher.FetcherException;
import com.rometools.fetcher.impl.FeedFetcherCache;
import com.rometools.fetcher.impl.HashMapFeedInfoCache;
import com.rometools.fetcher.impl.HttpClientFeedFetcher;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import org.jdom2.Element;
import org.putput.stream.StreamItemRepository;
import org.putput.stream.StreamItemService;
import org.putput.stream.StreamItemSource;
import org.putput.util.YoutubeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Service
public class RssFeedItemImportService {

  public static final String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36";

  Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  RssFeedInfoRepository rssFeedInfoRepository;

  @Autowired
  StreamItemRepository streamItemRepository;

  @Autowired
  StreamItemService streamItemService;

  @Scheduled(fixedRate = 30000L)
  public void importItems() {
    rssFeedInfoRepository
        .findAll()
        .forEach(importRssFeed());
  }

  private Consumer<? super RssFeedInfoEntity> importRssFeed() {
    return feedInfo -> {
      try {
        reverse(getFeed(feedInfo.getUrl())
            .getEntries())
            .stream()
            .forEach(importEntry(feedInfo));
      } catch(Exception e) {
        log.error("unable to import feed: " +  feedInfo.getUrl(), e);
      }
    };
  }

  private <T> Collection<T> reverse(List<T> collection) {
    Collections.reverse(collection);
    return collection;
  }

  private Consumer<SyndEntry> importEntry(RssFeedInfoEntity feedInfo) {
    return entry -> {
      try {
        saveEntryAsItem(feedInfo, entry);
      } catch (Exception e) {
        log.error("unable to save entry {} ", entry, e);
      }
    };
  }

  private void saveEntryAsItem(RssFeedInfoEntity feedInfo, SyndEntry entry) {
    String title = ofNullable(entry.getTitle()).orElse("no title");
    String link = ofNullable(entry.getLink()).orElse(feedInfo.getUrl() + "#" + title);
    Date publishedDate = ofNullable(entry.getPublishedDate()).orElse(new Date());

    String description = getDescription(entry).orElse(title + ": " + publishedDate.toString());

    if (streamItemRepository.findByExternalRefAndUser(feedInfo.getOwner().getUsername(), link).isEmpty()) {
      log.info("importing: " + link);
      streamItemService.newItemEntity(feedInfo.getOwner().getUsername(),
          description,
          Optional.of(title),
          Optional.of(StreamItemSource.RSS.value()),
          Optional.of(link),
          Optional.of(publishedDate));
    }
  }

  private Optional<String> getDescription(SyndEntry entry) {
    Optional<String> entryDescription = entryDescription(entry);

    Optional<String> firstContent = entry
        .getContents()
        .stream()
        .findFirst()
        .flatMap(content -> ofNullable(content.getValue()));

    Optional<String> customDescription = getCustomDescription(entry);

    if (customDescription.isPresent()) {
      return customDescription;
    } else if (firstContent.isPresent()){
      return firstContent;
    } else if (entryDescription.isPresent()) {
      return entryDescription;
    } else {
      return Optional.empty();
    }
  }

  private Optional<String> entryDescription(SyndEntry entry) {
    return ofNullable(entry.getDescription())
          .flatMap(syndContent -> ofNullable(syndContent.getValue()));
  }

  private Optional<String> getCustomDescription(SyndEntry entry) {
    return entry.getForeignMarkup()
        .stream()
        .findFirst()
        .flatMap(element -> {
          if (element.getNamespacePrefix().equals("soup")) {
            return of(element);
          }
          return empty();
        }).map(extractSoupDescription(entry));
  }

  private Function<Element, String> extractSoupDescription(SyndEntry entry) {
    return element -> {
      try {
        Map<String, Object> json = new ObjectMapper().readValue(element.getValue(), Map.class);
        String type = ofNullable(json.get("type")).orElse("").toString();
        String source = ofNullable(json.get("source")).orElse("").toString();

        if (type.equals("video")) {
          return source.contains("youtube") ? YoutubeUtil.embed(source) : source;
        } else {
          return entryDescription(entry).orElse(source);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    };
  }

  private SyndFeed getFeed(String url) {
    FeedFetcherCache feedCache = HashMapFeedInfoCache.getInstance();
    try {
      return new HttpClientFeedFetcher(feedCache).retrieveFeed(userAgent, new URL(url));
    } catch (IOException | FeedException | FetcherException e) {
      throw new RuntimeException(e);
    }
  }
}
