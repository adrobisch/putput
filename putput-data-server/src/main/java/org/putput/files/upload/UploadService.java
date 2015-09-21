package org.putput.files.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;

@Service
public class UploadService {

  @Autowired
  UploadRepository uploadRepository;

  public synchronized Optional<File> upload(UploadRequest uploadRequest, InputStream dataStream) {
    uploadRepository.writeChunk(Optional.ofNullable(uploadRequest.getPath()), uploadRequest.getResumableFilename(), dataStream, uploadRequest.getContentLength(), uploadRequest.getResumableChunkSize(), uploadRequest.getResumableChunkNumber());
    uploadRepository.markUploaded(uploadRequest.getResumableIdentifier(), uploadRequest.getResumableChunkNumber());

    if (isUploadFinished(uploadRequest)) {
      return Optional.of(uploadRepository.complete(uploadRequest.getResumableIdentifier(), uploadRequest.getResumableFilename()));
    }
    return Optional.empty();
  }

  public boolean isChunkUploaded(UploadRequest uploadRequest) {
    return uploadRepository.chunkExists(uploadRequest.getResumableIdentifier(), uploadRequest.getResumableChunkNumber());
  }

  public boolean isUploadFinished(UploadRequest uploadRequest) {
    for (int i = 1; i < uploadRequest.getTotalChunks() + 1; i++) {
      if (!uploadRepository.chunkExists(uploadRequest.getResumableIdentifier(), i)) {
        return false;
      }
    }
    return true;
  }

}
