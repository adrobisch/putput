package org.putput.files.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;

@Service
public class UploadService {

  UploadRepository uploadRepository;

  @Autowired
  public UploadService(UploadRepository uploadRepository) {
    this.uploadRepository = uploadRepository;
  }

  public synchronized Optional<File> upload(UploadRequest uploadRequest, InputStream dataStream) {
    uploadRepository.writeChunk(uploadRequest, dataStream);
    uploadRepository.markUploaded(uploadRequest);

    if (isUploadFinished(uploadRequest)) {
      File completedFile = uploadRepository.complete(uploadRequest);
      return Optional.of(completedFile);
    }
    return Optional.empty();
  }

  public boolean isChunkUploaded(UploadRequest uploadRequest) {
    return uploadRepository.chunkExists(uploadRequest);
  }

  public boolean isUploadFinished(UploadRequest uploadRequest) {
    for (int chunkIndex = 1; chunkIndex < uploadRequest.getTotalChunks() + 1; chunkIndex++) {
      if (!uploadRepository.chunkExists(uploadRequest, chunkIndex)) {
        return false;
      }
    }
    return true;
  }
}
