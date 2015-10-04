package org.putput.files;

import org.apache.commons.lang3.tuple.Pair;
import org.putput.common.persistence.BaseEntity;
import org.putput.users.UserEntity;
import org.putput.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class FileSystemSync {

    Logger log = LoggerFactory.getLogger(getClass());

    Storages storages;
    StorageConfigurationRepository storageConfigurationRepository;
    FileRepository fileRepository;
    FileService fileService;
    UserRepository userRepository;

    @Autowired
    public FileSystemSync(Storages storages,
                          StorageConfigurationRepository storageConfigurationRepository,
                          FileRepository fileRepository,
                          FileService fileService,
                          UserRepository userRepository) {
        this.storages = storages;
        this.storageConfigurationRepository = storageConfigurationRepository;
        this.fileRepository = fileRepository;
        this.fileService = fileService;
        this.userRepository = userRepository;
    }

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void sync() {
        log.info("syncing file storage...");

        Iterable<UserEntity> users = userRepository.findAll();
        
        users.forEach(user -> {
            StorageConfiguration storageConfiguration = fileService.getOrCreateDefaultStorageConfig(user);
            Storage<FileSystemReference> storage = storages.getStorage(storageConfiguration);

            LinkedList<Pair<Optional<FileSystemReference>, FileSystemReference>> fileQueue = new LinkedList<>();
            
            storage.list(Optional.<FileSystemReference>empty()).forEach(rootFile -> {
                fileQueue.add(Pair.of(Optional.empty(), rootFile));
            });
            
            while(!fileQueue.isEmpty()) {
                Pair<Optional<FileSystemReference>, FileSystemReference> storageRef = fileQueue.pop();
                
                FileSystemReference currentFile = storageRef.getValue();
                Optional<FileSystemReference> parent = storageRef.getKey();
                
                if (currentFile.isDirectory()) {
                    storage.list(Optional.of(currentFile)).forEach(child -> {
                        fileQueue.add(Pair.of(Optional.of(currentFile), child));
                    });
                }

                syncFile(parent, storage).accept(currentFile);
            }                
        });
    }

    Consumer<StorageReference<?>> syncFile(Optional<FileSystemReference> parent, Storage storage) {
        return storageReference -> {
            Optional<PutPutFile> existingFile = Optional.ofNullable(fileRepository.findByFullReference(storageReference.getName(),
                    storageReference.getContainerReference().get()));

            Optional<String> parentId = parent.flatMap(refToFile()).map(BaseEntity::getId);

            FileSystemReference fileRef = (FileSystemReference) storageReference;

            if (!existingFile.isPresent()) {
                PutPutFile newFile = fileService.createPutPutFile(fileRef.toFile(),
                        parentId,
                        storage.getStorageConfiguration().getUser(),
                        storageReference.getName(),
                        storage,
                        storageReference);

                fileRepository.save(newFile);

                log.info("syncing file: " + storageReference);
            }
        };
    }

    private Function<FileSystemReference, Optional<PutPutFile>> refToFile() {
        return parentRef -> {
            String containerRefOrSlash = parentRef.getContainerReference().orElse("/");
            return Optional.ofNullable(fileRepository.findByFullReference(parentRef.getName(), containerRefOrSlash));
        };
    }
}
