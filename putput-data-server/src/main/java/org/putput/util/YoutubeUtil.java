package org.putput.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeUtil {

  static Pattern youtubeUrlPatter = Pattern.compile("(?:https?:\\/\\/)?(?:www\\.)?youtu(.be\\/|be\\.com\\/watch\\?v=)(.{11})(.*)");

  public static Optional<String> isYoutubeUrl(String input) {
    Matcher matcher = youtubeUrlPatter.matcher(input);
    if (matcher.find()) {
      return Optional.of(matcher.group(2));
    }
    return Optional.empty();
  }

  public static String embed(String youtubeUrl) {
    Matcher matcher = youtubeUrlPatter.matcher(youtubeUrl);
    matcher.find();
    String videoId = matcher.group(2);
    return String.format("<iframe " +
        "width='560' " +
        "height='315' " +
        "src='//www.youtube.com/embed/%s' " +
        "frameborder='0' " +
        "allowfullscreen></iframe>", videoId);
  }
}
