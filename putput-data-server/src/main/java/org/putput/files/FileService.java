package org.putput.files;

import com.j256.simplemagic.ContentInfoUtil;
import org.putput.common.UuidService;
import org.putput.users.UserEntity;
import org.putput.users.UserRepository;
import org.putput.util.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    @Autowired
    public FileService(FileRepository fileRepository, 
                       StorageConfigurationRepository storageConfigurationRepository,
                       StorageParameterRepository storageParameterRepository,
                       UserRepository userRepository, 
                       Environment environment,
                       Storages storages,
                       UuidService uuidService) {
        this.fileRepository = fileRepository;
        this.storageConfigurationRepository = storageConfigurationRepository;
        this.storageParameterRepository = storageParameterRepository;
        this.userRepository = userRepository;
        this.environment = environment;
        this.storages = storages;
        this.uuidService = uuidService;
    }

    public PutPutFile createUserFileFromSource(String username,
                                               File sourceFile,
                                               Optional<String> parentId,
                                               long size) {
        if (sourceFile.isDirectory()) {
            throw new IllegalArgumentException("unable to create file from folder");
        }

        UserEntity user = userRepository.findByUsername(username);
        String fileName = sourceFile.getName();

        Storage defaultStorage = getDefaultStorage(user);
        String storageReference = defaultStorage.store(fileName, Optional.<String>empty(), fileStream(sourceFile));

        PutPutFile putPutFile = new PutPutFile();
        putPutFile.setUser(user);
        putPutFile.setId(uuidService.uuid());
        putPutFile.setName(fileName);
        putPutFile.setStorageReference(storageReference);
        putPutFile.setIsDirectory(0);
        putPutFile.setMimeType(getMimeType(sourceFile).orElse("application/octet-stream"));
        putPutFile.setSize(size);
        putPutFile.setParent(parentId.flatMap(toFileEntity()).orElse(null));
        putPutFile.setStorageConfiguration(defaultStorage.getStorageConfiguration());
        
        return fileRepository.save(putPutFile);
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

    public Optional<String> getMimeType(File file) {
        try {
            return Optional.ofNullable(new ContentInfoUtil().findMatch(file).getMimeType());
        } catch (IOException e) {
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

    public List<PutPutFile> getUserFiles(String username) {
        return fileRepository.findByUser(username);   
    }

    public Optional<PutPutFile> getUserFile(String id) {
        // todo: check permission
        return Optional.ofNullable(fileRepository.findOne(id));
    }

    public void deleteUserFile(String id) {
        // todo: check permission
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
