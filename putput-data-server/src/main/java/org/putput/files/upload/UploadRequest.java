package org.putput.files.upload;

import org.springframework.util.StringUtils;

public class UploadRequest {
  private long contentLength;
  public int resumableChunkSize;
  public long resumableTotalSize;
  public String resumableIdentifier;
  public String resumableFilename;
  public String resumableRelativePath;
  public int resumableChunkNumber;

  public String path;

  public boolean vaild() {
    if (resumableChunkSize < 0 || resumableTotalSize < 0
      || StringUtils.isEmpty(resumableIdentifier)
      || StringUtils.isEmpty(resumableFilename)
      || StringUtils.isEmpty(resumableRelativePath)) {
      return false;
    } else {
      return true;
    }
  }

  public String getResumableFilename() {
    return resumableFilename;
  }

  public String getResumableIdentifier() {
    return resumableIdentifier;
  }

  public String getPath() {
    return path;
  }

  public long getContentLength() {
    return contentLength;
  }

  public void setContentLength(long contentLength) {
    this.contentLength = contentLength;
  }

  public int getResumableChunkNumber() {
    return resumableChunkNumber;
  }

  public void setResumableChunkNumber(int resumableChunkNumber) {
    this.resumableChunkNumber = resumableChunkNumber;
  }
}
