package org.putput.storage;

import org.putput.files.FileSystemStorage;
import org.putput.files.MimeTypes;
import org.springframework.stereotype.Component;

@Component
public class Storages {
    
    public <T extends StorageReference> Storage<T> getStorage(StorageConfiguration configuration) {
        if (Storage.Type.FILE_SYSTEM.code().equalsIgnoreCase(configuration.getType())) {
            return (Storage<T>) new FileSystemStorage(configuration, new MimeTypes());
        }
        throw new RuntimeException("unknown storage type");
    }
    
}
