package org.putput.files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class FileSystemSync {

    Storages storages;
    StorageConfigurationRepository storageConfigurationRepository;
    FileRepository fileRepository;

    @Autowired
    public FileSystemSync(Storages storages,
                          StorageConfigurationRepository storageConfigurationRepository,
                          FileRepository fileRepository) {
        this.storages = storages;
        this.storageConfigurationRepository = storageConfigurationRepository;
        this.fileRepository = fileRepository;
    }

    @Scheduled(fixedRate = 10000)
    public void sync() {
        List<StorageConfiguration> fileSystemConfigurations =
                storageConfigurationRepository.findByType(Storage.Type.FILE_SYSTEM.code());

        LinkedList<StorageReference> fileQueue = new LinkedList<>();
        fileQueue.add(new StorageReference().setDirectory(true));

        while(!fileQueue.isEmpty()) {
            for (StorageConfiguration configuration : fileSystemConfigurations) {
                List<StorageReference> storageReferences = storages.getStorage(configuration).list(Optional.empty());

                storageReferences.stream().forEach(storageReference());
            }
        }
    }

    private Consumer<StorageReference> storageReference() {
        return storageReference -> {
            if (storageReference.isDirectory()) {
                syncDir(storageReference);
            } else {
                syncFile(storageReference);
            }
        };
    }

    private void syncFile(StorageReference storageReference) {

    }

    private void syncDir(StorageReference storageReference) {
        String storageContainerReference = storageReference.getContainerReference().get();
        Optional.ofNullable(fileRepository.findByContainerReference(storageContainerReference));
    }
}
