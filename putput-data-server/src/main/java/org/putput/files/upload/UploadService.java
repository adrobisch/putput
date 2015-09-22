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
    String fileName = uploadRequest.getResumableFilename();
    
    uploadRepository.writeChunk(Optional.ofNullable(uploadRequest.getPath()), fileName, dataStream, uploadRequest.getContentLength(), uploadRequest.getResumableChunkSize(), uploadRequest.getResumableChunkNumber());
    uploadRepository.markUploaded(uploadRequest.getResumableIdentifier(), uploadRequest.getResumableChunkNumber());

    if (isUploadFinished(uploadRequest)) {
      File completedFile = uploadRepository.complete(uploadRequest.getResumableIdentifier(), fileName);
      
      PutPutFile newFile = fileService.createUserFileFromSource(username,
              completedFile,
              Optional.<String>empty(),
              uploadRequest.getResumableTotalSize());
      
      return Optional.of(newFile);
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
