package org.putput.files;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface Storage {
    StorageConfiguration getStorageConfiguration();
    StorageReference store(String name, Optional<String> containerReference, InputStream dataStream);
    InputStream getContent(Optional<String> storageContainerReference, String storageReference);
    List<StorageReference> list(Optional<String> containerReference);

    enum Type {
        FILE_SYSTEM("fs");

        private final String code;

        Type(String code) {
            this.code = code;
        }

        public String code() {
            return code;
        }
    }
}
