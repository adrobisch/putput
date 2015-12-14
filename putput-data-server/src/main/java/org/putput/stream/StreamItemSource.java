package org.putput.stream;

public enum StreamItemSource {
  PUTPUT("putput");

  StreamItemSource(String value) {
    this.value = value;
  }

  final String value;

  String value() {
    return value;
  }
}
