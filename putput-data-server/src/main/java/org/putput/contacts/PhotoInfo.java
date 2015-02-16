package org.putput.contacts;

public class PhotoInfo {
  String mimeType;
  String url;
  String base64Data;

  public PhotoInfo(String mimeType, String url, String base64Data) {
    this.mimeType = mimeType;
    this.url = url;
    this.base64Data = base64Data;
  }

  public String getMimeType() {
    return mimeType;
  }

  public String getUrl() {
    return url;
  }

  public String getBase64Data() {
    return base64Data;
  }
}
