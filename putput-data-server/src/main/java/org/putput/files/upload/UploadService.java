package org.putput.files.upload;

import org.putput.files.FileService;
import org.putput.files.PutPutFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;

@Service
public class UploadService {

  UploadRepository uploadRepository;
  FileService fileService;

  @Autowired
  public UploadService(UploadRepository uploadRepository, FileService fileService) {
    this.uploadRepository = uploadRepository;
    this.fileService = fileService;
  }

  public synchronized Optional<PutPutFile> upload(String username, UploadRequest uploadRequest, InputStream dataStream) {
    uploadRepository.writeChunk(uploadRequest, dataStream);
    uploadRepository.markUploaded(uploadRequest);

    if (isUploadFinished(uploadRequest)) {
      File completedFile = uploadRepository.complete(uploadRequest);
      
      PutPutFile newFile = fileService.createUserFileFromSource(username,
              completedFile,
              Optional.<String>empty(),
              uploadRequest.getResumableTotalSize());
      
      return Optional.of(newFile);
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
