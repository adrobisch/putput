package org.putput.files;

import ezvcard.util.org.apache.commons.codec.binary.Base64;
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
      .withData(getBase64Data(inputStream));

    if (filename.isPresent()) {
      String filePath = String.format("/%s/%s", username, filename);
      file.withPath(filePath);
    }

    return fileRepository.save(file);
  }

  private String getBase64Data(InputStream inputStream) {
    try {
      return Base64.encodeBase64String(StreamUtils.copyToByteArray(inputStream));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
