package org.putput.files;

import org.springframework.stereotype.Component;

@Component
public class Storages {
    
    public Storage getStorage(StorageConfiguration configuration) {
        if (Storage.Type.FILE_SYSTEM.code().equalsIgnoreCase(configuration.getType())) {
            return new FileSystemStorage(configuration);
        }
        throw new RuntimeException("unknown storage type");
    }
    
}
