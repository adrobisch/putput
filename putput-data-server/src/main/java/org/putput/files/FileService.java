package org.putput.files;

import org.putput.common.UuidService;
import org.putput.users.UserEntity;
import org.putput.users.UserRepository;
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

import static org.putput.util.FileHelper.requireExistingDir;

@Service
@Transactional
public class FileService {
    
    FileRepository fileRepository;
    StorageConfigurationRepository storageConfigurationRepository;
    StorageParameterRepository storageParameterRepository;
    UserRepository userRepository;
    Environment environment;
    Storages storages;
    UuidService uuidService;
    MimeTypes mimeTypes;

    @Autowired
    public FileService(FileRepository fileRepository, 
                       StorageConfigurationRepository storageConfigurationRepository,
                       StorageParameterRepository storageParameterRepository,
                       UserRepository userRepository, 
                       Environment environment,
                       Storages storages,
                       UuidService uuidService,
                       MimeTypes mimeTypes) {
        this.fileRepository = fileRepository;
        this.storageConfigurationRepository = storageConfigurationRepository;
        this.storageParameterRepository = storageParameterRepository;
        this.userRepository = userRepository;
        this.environment = environment;
        this.storages = storages;
        this.uuidService = uuidService;
        this.mimeTypes = mimeTypes;
    }

    public PutPutFile createUserFileFromSource(String username,
                                               File sourceFile,
                                               Optional<String> parentId) {
        if (sourceFile.isDirectory()) {
            throw new IllegalArgumentException("unable to create file from folder");
        }

        UserEntity user = userRepository.findByUsername(username);
        String fileName = sourceFile.getName();

        Storage defaultStorage = getDefaultStorage(user);
        StorageReference storageReference = defaultStorage.store(fileName, Optional.<String>empty(), fileStream(sourceFile));

        PutPutFile putPutFile = createPutPutFile(sourceFile,
                parentId,
                user,
                fileName,
                defaultStorage,
                storageReference);

        return fileRepository.save(putPutFile);
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

    private Storage getDefaultStorage(UserEntity user) {
        return storages.getStorage(getOrCreateDefaultStorageConfig(user));
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

    public List<PutPutFile> getUserFiles(String username) {
        return fileRepository.findByUser(username);   
    }

    public Optional<PutPutFile> getUserFile(String id) {
        return Optional.ofNullable(fileRepository.findOne(id));
    }
    
    public Optional<InputStream> getFileContent(String id) {
        return Optional.ofNullable(fileRepository.findOne(id))
                .flatMap(file -> Optional.of(getFileContentFromStorage(file)));    
    }

    InputStream getFileContentFromStorage(PutPutFile file) {
        return storages.getStorage(file.getStorageConfiguration()).getContent(
                file.getStorageContainerReference(), 
                file.getStorageReference().get()
        );
    }

    public void deleteUserFile(String id) {
        fileRepository.delete(id);
    }

    public StorageConfiguration getOrCreateDefaultStorageConfig(UserEntity user) {
        return storageConfigurationRepository.findDefaultByUser(user.getUsername()).orElseGet(newDefaultStorage(user));
    }

    private Supplier<? extends StorageConfiguration> newDefaultStorage(UserEntity user) {
        return () -> {
            StorageConfiguration newDefaultStorageConfiguration = createNewDefaultConfiguration(user);
            StorageParameter baseDirParameter = createBaseDirParameter(user.getUsername(), newDefaultStorageConfiguration);
            
            newDefaultStorageConfiguration.getStorageParameters().put(FileSystemStorage.baseDirKey, baseDirParameter);
            
            return storageConfigurationRepository.save(newDefaultStorageConfiguration);
        };
    }

    private StorageParameter createBaseDirParameter(String username, StorageConfiguration newDefaultStorageConfiguration) {
        StorageParameter baseDirParameter = new StorageParameter()
                .setKey(FileSystemStorage.baseDirKey)
                .setValue(requireExistingDir(getFilesDir(username)).getAbsolutePath());

        baseDirParameter.setId(uuidService.uuid());
        baseDirParameter.setStorageConfiguration(newDefaultStorageConfiguration);

        return storageParameterRepository.save(baseDirParameter);
    }

    private StorageConfiguration createNewDefaultConfiguration(UserEntity user) {
        StorageConfiguration newDefaultStorageConfiguration = new StorageConfiguration();
        newDefaultStorageConfiguration.setId(uuidService.uuid());
        newDefaultStorageConfiguration.setIsDefault(1);
        newDefaultStorageConfiguration.setUser(user);
        newDefaultStorageConfiguration.setType(Storage.Type.FILE_SYSTEM.code());

        return storageConfigurationRepository.save(newDefaultStorageConfiguration);
    }

    String getFilesDir(String username) {
        return environment.getProperty("files.base.dir", "/var/putput/files") + File.separatorChar + username;
    }
}
