package org.putput.util;

import org.springframework.core.annotation.AnnotationUtils;

import javax.ws.rs.Path;

public class PathHelper {
  public static String getPathFromResource(Class<?> targetResource) {
    String pathValue = AnnotationUtils.findAnnotation(targetResource, Path.class).value();
    return pathValue != null && pathValue.equals("/") ? "" : pathValue;
  }
}
