package org.putput.stream;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class StreamItemContentParser {

  public static abstract class ContentFragment {
    String fragment;

    public ContentFragment(String fragment) {
      this.fragment = fragment;
    }

    public String getFragment() {
      return fragment;
    }
  }

  public static class Url extends ContentFragment {
    private String url;

    public Url(String fragment) {
      super(fragment);
    }

    public Url setUrl(String url) {
      this.url = url;
      return this;
    }

    public String getUrl() {
      return url;
    }
  }

  public static class Mention extends ContentFragment {
    private String username;

    public Mention(String fragment) {
      super(fragment);
    }

    public Mention setUsername(String username) {
      this.username = username;
      return this;
    }

    public String getUsername() {
      return username;
    }
  }

  public static class Words extends ContentFragment {
    public Words(String fragment) {
      super(fragment);
    }
  }

  public static class HashTag extends ContentFragment {
    private String tag;

    public HashTag(String fragment) {
      super(fragment);
    }

    public HashTag setTag(String tag) {
      this.tag = tag;
      return this;
    }

    public String getTag() {
      return tag;
    }
  }

  public static List<ContentFragment> parse(String input) {
    return asList(input.split(" "))
        .stream()
        .map(parseFragment())
        .collect(Collectors.toList());
  }

  private static Function<String, ContentFragment> parseFragment() {
    return fragment -> {
      String trimmedFragment = fragment.trim();
      if (trimmedFragment.startsWith("http://") || trimmedFragment.startsWith("https://")) {
        return new Url(fragment).setUrl(trimmedFragment);
      } else if (trimmedFragment.startsWith("@")) {
        return new Mention(fragment).setUsername(trimmedFragment.replaceFirst("@", ""));
      } else if (trimmedFragment.startsWith("#")) {
        return new HashTag(fragment).setTag(trimmedFragment.replaceFirst("#", ""));
      } else {
        return new Words(fragment);
      }
    };
  }
}
