package org.putput.files;

import java.io.InputStream;
import java.util.Optional;

public interface Storage {
    String store(String name, Optional<String> containerReference, InputStream dataStream);

    StorageConfiguration getStorageConfiguration();
    InputStream getContent(Optional<String> storageContainerReference, String storageReference);

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
