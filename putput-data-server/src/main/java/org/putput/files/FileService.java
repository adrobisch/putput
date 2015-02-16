package org.putput.files;

import org.putput.common.UuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class FileService {
  @Autowired
  UuidService uuidService;

  @Autowired
  FileRepository fileRepository;

  @Transactional
  public PutPutFile saveUserFile(String username, Optional<String> filename, String mimeType, InputStream inputStream, long size) {
    PutPutFile file = new PutPutFile(mimeType)
      .withId(uuidService.uuid())
      .withData(getData(inputStream));

    if (filename.isPresent()) {
      String filePath = String.format("/%s/%s", username, filename);
      file.withPath(filePath);
    }

    return fileRepository.save(file);
  }

  private byte[] getData(InputStream inputStream) {
    try {
      return StreamUtils.copyToByteArray(inputStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
