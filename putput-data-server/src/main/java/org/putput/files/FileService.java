package org.putput.files;

import org.putput.common.UuidService;
import org.putput.files.upload.UploadHandler;
import org.putput.files.upload.UploadRequest;
import org.putput.storage.Storage;
import org.putput.storage.StorageReference;
import org.putput.storage.StorageService;
import org.putput.users.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@Transactional
public class FileService implements UploadHandler<PutPutFile> {
    
    FileRepository fileRepository;
    Environment environment;
    UuidService uuidService;
    StorageService storageService;
    MimeTypes mimeTypes;

    @Autowired
    public FileService(FileRepository fileRepository, 
                       Environment environment,
                       UuidService uuidService,
                       StorageService storageService,
                       MimeTypes mimeTypes) {
        this.fileRepository = fileRepository;
        this.environment = environment;
        this.uuidService = uuidService;
        this.storageService = storageService;
        this.mimeTypes = mimeTypes;
    }

    public PutPutFile createUserFileFromSource(String username,
                                               File sourceFile,
                                               Optional<String> parentId,
                                               Optional<String> containerPath) {
        if (sourceFile.isDirectory()) {
            throw new IllegalArgumentException("unable to create file from folder");
        }

        Storage defaultStorage = storageService.getDefaultStorage(username);

        String fileName = sourceFile.getName();

        StorageReference<?> storageReference = parentId
                .map(parentIdValue -> fileRepository.findOne(parentId.get()))
                .map(saveInParentFolder(fileStream(sourceFile), defaultStorage, fileName))
                .orElseGet(saveInContainerPath(sourceFile, containerPath, defaultStorage, fileName));

        Optional<PutPutFile> existingFile = Optional.ofNullable(fileRepository.findByFullReference(storageReference.getName(),
                storageReference.getContainerReference().get()));

        if (!existingFile.isPresent()) {
            PutPutFile putPutFile = createPutPutFile(sourceFile,
                    parentId,
                    defaultStorage.getStorageConfiguration().getUser(),
                    fileName,
                    defaultStorage,
                    storageReference);

            return fileRepository.save(putPutFile);
        } else {
            return existingFile.get();
        }
    }

    private Supplier<StorageReference> saveInContainerPath(File sourceFile, Optional<String> containerPath, Storage defaultStorage, String fileName) {
        return () -> defaultStorage.store(fileName, containerPath, fileStream(sourceFile));
    }

    private Function<PutPutFile, StorageReference> saveInParentFolder(InputStream fileStream, Storage defaultStorage, String fileName) {
        return putputFile -> defaultStorage.store(fileName,
                putputFile.getStorageContainerReference().map(containerRef -> containerRef + defaultStorage.containerSeparator() + putputFile.getName()),
                fileStream);
    }

    public PutPutFile createPutPutFile(File sourceFile, Optional<String> parentId, UserEntity user, String fileName, Storage defaultStorage, StorageReference<?> storageReference) {
        PutPutFile putPutFile = new PutPutFile();
        putPutFile.setUser(user);
        putPutFile.setId(uuidService.uuid());
        putPutFile.setName(fileName);
        putPutFile.setStorageReference(storageReference.getName());
        putPutFile.setStorageContainerReference(storageReference.getContainerReference().get());
        putPutFile.setIsDirectory(storageReference.isDirectory() ? 1 : 0);
        putPutFile.setMimeType(mimeTypes.getMimeType(sourceFile).orElse("application/octet-stream"));
        putPutFile.setSize(sourceFile.length());
        putPutFile.setMd5Hash(md5(sourceFile));
        putPutFile.setParent(parentId.flatMap(toFileEntity()).orElse(null));
        putPutFile.setStorageConfiguration(defaultStorage.getStorageConfiguration());
        putPutFile.setFileCreated(createdTime(sourceFile));
        return putPutFile;
    }

    private Long createdTime(File sourceFile) {
        Path p = Paths.get(sourceFile.getAbsolutePath());
        try {
            BasicFileAttributes view = Files.getFileAttributeView(p, BasicFileAttributeView.class)
                    .readAttributes();
            return view.creationTime().toMillis();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String md5(File sourceFile) {
        try {
            if (sourceFile.isDirectory()) {
                return null;
            }
            return org.apache.commons.codec.digest.DigestUtils.md5Hex(fileStream(sourceFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    FileInputStream fileStream(File sourceFile) {
        try {
            return new FileInputStream(sourceFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    Function<? super String, Optional<PutPutFile>> toFileEntity() {
        return id -> {
            PutPutFile foundFile = fileRepository.findOne(id);
            if (foundFile != null) {
                return Optional.of(foundFile);                
            }
            return Optional.empty();
        };        
    }

    public List<PutPutFile> getUserFiles(String username, Optional<String> parentId, Optional<String> tagValue) {
        if (tagValue.isPresent()) {
            return fileRepository.findByUserAndTag(username, tagValue.get());
        }

        if (parentId.isPresent()) {
            return fileRepository.findByUserAndParent(username, parentId.get());
        }
        
        return fileRepository.findByUserAndContainer(username, "/");
    }

    public Optional<PutPutFile> getUserFile(String id) {
        return Optional.ofNullable(fileRepository.findOne(id));
    }
    
    public Optional<InputStream> getFileContent(String id) {
        return Optional.ofNullable(fileRepository.findOne(id))
                .flatMap(file -> Optional.of(getFileContentFromStorage(file)));    
    }

    InputStream getFileContentFromStorage(PutPutFile file) {
        return storageService.getContentFromStorage(file.getStorageConfiguration(),
                file.getStorageContainerReference(), 
                file.getStorageReference().get());
    }

    public void deleteUserFile(String id) {
        fileRepository.delete(id);
    }

    @Override
    public String handledType() {
        return "file";
    }

    @Override
    public PutPutFile handleUpload(String username, UploadRequest uploadRequest, File tempfile) {
        return createUserFileFromSource(username,
                tempfile,
                Optional.ofNullable(uploadRequest.getResumableRelativePath()),
                Optional.<String>empty());
    }
}
