package org.putput.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeUtil {

  static Pattern youtubeUrlPatter = Pattern.compile("(?:https?:\\/\\/)?(?:www\\.)?youtu(.be\\/|be\\.com\\/watch\\?v=)(.{11})");

  public static Boolean isYoutubeUrl(String input) {
    return youtubeUrlPatter.matcher(input).matches();
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
