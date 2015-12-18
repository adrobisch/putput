package org.putput.stream;

import com.vdurmont.emoji.EmojiParser;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.putput.stream.StreamItemContentParser.*;
import org.putput.util.YoutubeUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static org.putput.util.LangUtil.as;

@Service
public class StreamItemHtmlProcessor {

  PolicyFactory LINKS = new HtmlPolicyBuilder()
      .allowStandardUrlProtocols()
      .allowElements("a")
      .allowAttributes("href", "target")
      .onElements("a")
      .requireRelNofollowOnLinks()
      .toFactory();

  Pattern imgExtensionPattern = Pattern.compile(".*(png|PNG|jpg|jpeg|JPG|JPEG|gif|GIF)");

  PolicyFactory sanitizer = Sanitizers.IMAGES
      .and(Sanitizers.FORMATTING)
      .and(LINKS)
      .and(new HtmlPolicyBuilder()
          .allowStandardUrlProtocols()
          .allowElements("iframe")
          .allowAttributes("src", "width", "height", "frameborder", "allowfullscreen")
          .onElements("iframe")
          .toFactory());

  String replaceNewlines(String content) {
    return content.replaceAll("\n", "<br> ");
  }

  String replaceEmoji(String content) {
    return EmojiParser.parseToHtml(content);
  }

  public String itemHtml(StreamItemEntity item) {
    if (item.getSource().map(streamItemSource -> streamItemSource == StreamItemSource.RSS).orElse(false)) {
      return rssHtml(item);
    } else {
      return contentHtml(item.content);
    }
  }

  String rssHtml(StreamItemEntity item) {
    String rssHtml = ofNullable(item.getTitle())
        .map(title -> format("<a target='_blank' href='%s'>%s</a>",
            item.getExternalRef().orElse("#"), item.getTitle())
            .concat(item.getContent() == null || item.getContent().isEmpty() ? "" : ":" + item.content))
        .orElse(item.getContent());

    return sanitizer.sanitize(rssHtml);
  }


  String contentHtml(String content) {
    List<ContentFragment> parsedContent =
        StreamItemContentParser.parse(replaceEmoji(replaceNewlines(content)));

    return parsedContent
        .stream()
        .map(fragmentToHtml())
        .collect(Collectors.joining(" "));
  }

  private Function<ContentFragment, String> fragmentToHtml() {
    return fragment -> {
      if (fragment instanceof Url && isImgUrl(as(Url.class, fragment).getUrl())) {
        return format("<img src=\"%s\">", as(Url.class, fragment).getUrl());
      } else if (fragment instanceof Url && YoutubeUtil.isYoutubeUrl(as(Url.class, fragment).getUrl())) {
        return YoutubeUtil.embed(as(Url.class, fragment).getUrl());
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

  private String link(String url, String name) {
    return format("<a href=\"%s\">%s</a>", url, name);
  }

  boolean isImgUrl(String inputUrl) {
    return imgExtensionPattern.matcher(inputUrl).matches();
  }
}
