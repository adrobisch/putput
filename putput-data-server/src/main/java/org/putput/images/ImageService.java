package org.putput.images;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.putput.common.UuidService;
import org.putput.files.FileSystemReference;
import org.putput.storage.Storage;
import org.putput.storage.StorageReference;
import org.putput.storage.StorageService;
import org.putput.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.Optional;

@Service
public class ImageService {
  public static final String IMAGES_CONTAINER = ".images";
  
  @Autowired
  UuidService uuidService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ImageRepository imageRepository;
  
  @Autowired
  StorageService storageService;

  @Transactional
  public PutPutImage saveUserImage(String username, Optional<String> filename, String mimeType, InputStream inputStream) {
    Storage<FileSystemReference> defaultStorage = storageService.getDefaultStorage(username);
    FileSystemReference imagesContainer = defaultStorage.createContainer(IMAGES_CONTAINER);
    StorageReference reference = defaultStorage.store(filename.orElse(uuidService.uuid()), Optional.of(imagesContainer.getName()), inputStream);
    
    PutPutImage file = new PutPutImage(mimeType)
      .withId(uuidService.uuid())
      .withPath(reference.getName())
      .withUser(userRepository.findByUsername(username));

    return imageRepository.save(file);
  }

  @Transactional
  public Pair<PutPutImage, InputStream> getImage(String id) {
    PutPutImage image = imageRepository.findOne(id);
    Storage<FileSystemReference> defaultStorage = storageService.getDefaultStorage(image.getUser().getUsername());
    InputStream imageContent = defaultStorage.getContent(Optional.of(".images"), image.getPath());
    return new ImmutablePair<>(image, imageContent);
  }
}
