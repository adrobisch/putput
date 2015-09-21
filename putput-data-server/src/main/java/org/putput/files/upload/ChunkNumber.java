package org.putput.files.upload;

public class ChunkNumber {
  public ChunkNumber(int number) {
    this.number = number;
  }

  public int number;

  @Override
  public boolean equals(Object obj) {
    return obj instanceof ChunkNumber
      ? ((ChunkNumber) obj).number == this.number : false;
  }

  @Override
  public int hashCode() {
    return number;
  }
}
