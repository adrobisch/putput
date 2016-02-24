package org.putput.storage;

import org.putput.common.UuidService;
import org.putput.files.FileSystemReference;
import org.putput.files.FileSystemStorage;
import org.putput.users.UserEntity;
import org.putput.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Supplier;

import static org.putput.util.FileHelper.requireExistingDir;

@Service
public class StorageService {
    StorageConfigurationRepository storageConfigurationRepository;
    StorageParameterRepository storageParameterRepository;
    Storages storages;
    Environment environment;
    UuidService uuidService;
    UserRepository userRepository;

    @Autowired
    public StorageService(StorageConfigurationRepository storageConfigurationRepository,
                          StorageParameterRepository storageParameterRepository,
                          Storages storages,
                          Environment environment,
                          UserRepository userRepository,
                          UuidService uuidService) {
        this.storageConfigurationRepository = storageConfigurationRepository;
        this.storageParameterRepository = storageParameterRepository;
        this.storages = storages;
        this.environment = environment;
        this.userRepository = userRepository;
        this.uuidService = uuidService;
    }

    public Storage<FileSystemReference> getDefaultStorage(String username) {
        return storages.getStorage(getOrCreateDefaultStorageConfig(userRepository.findByUsername(username)));
    }

    public InputStream getContentFromStorage(StorageConfiguration storageConfiguration, 
                                      Optional<String> storageContainerReference,
                                      String storageReference) {
        return storages
                .getStorage(storageConfiguration)
                .getContent(storageContainerReference, storageReference);
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
