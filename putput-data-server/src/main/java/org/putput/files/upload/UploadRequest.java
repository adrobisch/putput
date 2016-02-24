package org.putput.files.upload;

import org.springframework.util.StringUtils;

public class UploadRequest {
  private String uploadFolder;
  private String path;
  private String type;
  private long contentLength;
  private int resumableChunkSize;
  private long resumableTotalSize;
  private String resumableIdentifier;
  private String resumableFilename;
  private String resumableRelativePath;
  private int resumableChunkNumber;
  private int totalChunks;

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

  public int getResumableChunkSize() {
    return resumableChunkSize;
  }

  public void setResumableChunkSize(int resumableChunkSize) {
    this.resumableChunkSize = resumableChunkSize;
  }

  public long getResumableTotalSize() {
    return resumableTotalSize;
  }

  public void setResumableTotalSize(long resumableTotalSize) {
    this.resumableTotalSize = resumableTotalSize;
  }

  public void setResumableIdentifier(String resumableIdentifier) {
    this.resumableIdentifier = resumableIdentifier;
  }

  public void setResumableFilename(String resumableFilename) {
    this.resumableFilename = resumableFilename;
  }

  public String getResumableRelativePath() {
    return resumableRelativePath;
  }

  public void setResumableRelativePath(String resumableRelativePath) {
    this.resumableRelativePath = resumableRelativePath;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public int getTotalChunks() {
    return totalChunks;
  }

  public void setTotalChunks(int totalChunks) {
    this.totalChunks = totalChunks;
  }

  public String getUploadFolder() {
    return uploadFolder;
  }

  public void setUploadFolder(String uploadFolder) {
    this.uploadFolder = uploadFolder;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
