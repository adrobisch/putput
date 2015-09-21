package org.putput.files.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class UploadService {

  @Autowired
  UploadRepository uploadRepository;

  public boolean upload(UploadRequest uploadRequest, InputStream dataStream) {
    uploadRepository.writeChunk(uploadRequest.getPath(), dataStream, uploadRequest.getContentLength(), uploadRequest.resumableChunkSize, uploadRequest.getResumableChunkNumber());
    uploadRepository.markUploaded(uploadRequest.getResumableIdentifier(), uploadRequest.getResumableChunkNumber());

    if (isUploadFinished(uploadRequest)) {
      uploadRepository.complete(uploadRequest.getResumableIdentifier(), uploadRequest.getPath());
      return true;
    }
    return false;
  }

  public boolean isChunkUploaded(UploadRequest uploadRequest) {
    return uploadRepository.chunkExists(uploadRequest.getResumableIdentifier(), uploadRequest.getResumableChunkNumber());
  }

  public boolean isUploadFinished(UploadRequest uploadRequest) {
    int count = (int) Math.ceil(((double) uploadRequest.resumableTotalSize) / ((double) uploadRequest.resumableChunkSize));

    for (int i = 1; i < count + 1; i++) {
      if (!uploadRepository.chunkExists(uploadRequest.getResumableIdentifier(), i)) {
        return false;
      }
    }
    return true;
  }

}
