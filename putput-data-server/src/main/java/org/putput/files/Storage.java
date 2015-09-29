package org.putput.files;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface Storage<T extends StorageReference> {
    StorageConfiguration getStorageConfiguration();
    T store(String name, Optional<String> containerReference, InputStream dataStream);
    List<T> list(Optional<T> containerReference);
    InputStream getContent(Optional<String> storageContainerReference, String name);

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
