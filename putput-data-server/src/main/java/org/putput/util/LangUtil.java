package org.putput.util;

public class LangUtil {
  public static <T, I> T as(Class<T> clazz, I instance) {
    return clazz.cast(instance);
  }
}
