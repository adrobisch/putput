package org.putput.stream;

public enum StreamItemSource {
  PUTPUT("PUTPUT"),
  RSS("RSS");

  StreamItemSource(String value) {
    this.value = value;
  }

  final String value;

  public String value() {
    return value;
  }
}
