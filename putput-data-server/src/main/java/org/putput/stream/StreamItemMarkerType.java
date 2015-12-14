package org.putput.stream;

import java.util.stream.Stream;

public enum StreamItemMarkerType {
  LIKE("like"),
  DISLIKE("dislike"),
  FAVORITE("favorite"),
  LATER("later");

  StreamItemMarkerType(String value) {
    this.value = value;
  }

  final String value;

  String value() {
    return value;
  }

  static StreamItemMarkerType fromValue(String value) {
    return Stream.of(StreamItemMarkerType.values())
        .filter(markerType -> markerType.value().equals(value))
        .findFirst()
        .get();
  }
}
