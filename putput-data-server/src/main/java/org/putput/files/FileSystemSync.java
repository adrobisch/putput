package org.putput.files;

import org.putput.common.UuidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class FileSystemSync {

    Logger log = LoggerFactory.getLogger(getClass());

    Storages storages;
    StorageConfigurationRepository storageConfigurationRepository;
    FileRepository fileRepository;
    private final UuidService uuidService;

    @Autowired
    public FileSystemSync(Storages storages,
                          StorageConfigurationRepository storageConfigurationRepository,
                          FileRepository fileRepository,
                          UuidService uuidService) {
        this.storages = storages;
        this.storageConfigurationRepository = storageConfigurationRepository;
        this.fileRepository = fileRepository;
        this.uuidService = uuidService;
    }

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void sync() {
        log.info("syncing file storage...");

        List<StorageConfiguration> fileSystemConfigurations =
                storageConfigurationRepository.findByType(Storage.Type.FILE_SYSTEM.code());

        for (StorageConfiguration configuration : fileSystemConfigurations) {
            Storage storage = storages.getStorage(configuration);

            LinkedList<StorageReference> fileQueue = new LinkedList<>();
            fileQueue.addAll(storage.list(Optional.<StorageReference>empty()));

            while(!fileQueue.isEmpty()) {
                StorageReference storageRef = fileQueue.pop();

                if (storageRef.isDirectory()) {
                    fileQueue.addAll(storage.list(Optional.of(storageRef)));
                }

                syncFile(storage).accept(storageRef);
            }
        }
    }

    Consumer<StorageReference> syncFile(Storage storage) {
        return storageReference -> {
            Optional<PutPutFile> existingFile = Optional.ofNullable(fileRepository.findByFullReference(storageReference.getName(),
                    storageReference.getContainerReference().orElse(null)));

            if (!existingFile.isPresent()) {
                PutPutFile newFile = new PutPutFile()
                        .setId(uuidService.uuid())
                        .setUser(storage.getStorageConfiguration().getUser())
                        .setStorageConfiguration(storage.getStorageConfiguration())
                        .setMimeType(storageReference.getMimeType())
                        .setName(storageReference.getName())
                        .setStorageReference(storageReference.getName())
                        .setStorageContainerReference(storageReference.getContainerReference().orElse(null))
                        .setIsDirectory(storageReference.isDirectory() ? 1 : 0)
                        .setSize(storageReference.getSize());

                fileRepository.save(newFile);

                log.info("syncing file: " + storageReference);
            }
        };
    }
}
