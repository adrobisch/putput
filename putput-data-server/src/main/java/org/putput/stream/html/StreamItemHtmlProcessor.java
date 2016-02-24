package org.putput.stream.html;

import org.apache.commons.lang3.tuple.Pair;
import org.putput.api.model.StreamItemMedia;
import org.putput.stream.StreamItemContentParser;
import org.putput.stream.StreamItemContentParser.*;
import org.putput.stream.StreamItemEntity;
import org.putput.stream.StreamItemSource;
import org.putput.util.YoutubeUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.putput.util.LangUtil.as;

@Service
public class StreamItemHtmlProcessor {

  Pattern imgExtensionPattern = Pattern.compile(".*(png|PNG|jpg|jpeg|JPG|JPEG|gif|GIF)");

  String replaceNewlines(String content) {
    return content.replaceAll("\n", "<br> ");
  }

  public Pair<String, List<StreamItemMedia>> itemHtml(StreamItemEntity item) {
    if (item.getSource().map(streamItemSource -> streamItemSource == StreamItemSource.RSS).orElse(false)) {
      return rssHtml(item);
    } else {
      return contentHtml(item.getContent());
    }
  }

  Pair<String, List<StreamItemMedia>> rssHtml(StreamItemEntity item) {
    return new MediaAwareSanitizer().sanitize(item.getContent());
  }

  Pair<String, List<StreamItemMedia>> contentHtml(String content) {
    List<ContentFragment> parsedContent =
        StreamItemContentParser.parse(replaceNewlines(content));

    List<StreamItemMedia> itemMedia = new ArrayList<>();
    
    String html = parsedContent
            .stream()
            .peek(addToMedia(itemMedia))
            .map(fragmentToHtml())
            .collect(Collectors.joining(" "));
    
    return Pair.of(html, itemMedia);
  }

  private Consumer<? super ContentFragment> addToMedia(List<StreamItemMedia> itemMedia) {
    return fragment -> {
      if (isImage(fragment)) {
        itemMedia.add(new StreamItemMedia().withType("image").withUrl(as(Url.class, fragment).getUrl()));  
      } else if (youtubeId(fragment).isPresent()) {
        itemMedia.add(new StreamItemMedia()
                .withKey(youtubeId(fragment).get())
                .withType("youtube")
                .withUrl(as(Url.class, fragment).getUrl()));
      }
    };
  }


  private Function<ContentFragment, String> fragmentToHtml() {
    return fragment -> {
      if (isImage(fragment) || youtubeId(fragment).isPresent()) {
        return "";
      } else if (fragment instanceof Url) {
        String url = as(Url.class, fragment).getUrl();
        return link(url, url);
      } else if (fragment instanceof Mention){
        String username = as(Mention.class, fragment).getUsername();
        return link("/@"+username, "@" + username);
      } else if (fragment instanceof HashTag){
        return format("<span class='label label-default'>%s</span>",
            as(HashTag.class, fragment).getTag());
      } else if (fragment instanceof Words) {
        return fragment.getFragment();
      } else {
        throw new IllegalStateException("unknown content fragment: " + fragment);
      }
    };
  }

  private Optional<String> youtubeId(ContentFragment fragment) {
    if (!(fragment instanceof Url)) {
      return Optional.empty();
    } else {
      return YoutubeUtil.isYoutubeUrl(as(Url.class, fragment).getUrl());
    }
  }

  private boolean isImage(ContentFragment fragment) {
    return fragment instanceof Url && isImgUrl(as(Url.class, fragment).getUrl());
  }

  private String link(String url, String name) {
    return format("<a href=\"%s\">%s</a>", url, name);
  }

  boolean isImgUrl(String inputUrl) {
    return imgExtensionPattern.matcher(inputUrl).matches();
  }

}
