package org.putput.files;

import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class FileSystemStorage implements Storage {
    public static final String baseDirKey = "base.dir";

    final File baseDir;
    final StorageConfiguration configuration;

    public FileSystemStorage(StorageConfiguration configuration) {
        this.configuration = configuration;
        this.baseDir = new File(configuration.getStorageParameters().get(baseDirKey).getValue());
    }

    @Override
    public String store(String name, Optional<String> containerReference, InputStream input) {
        if (containerReference.isPresent()) {
            throw new UnsupportedOperationException("saving file with parent path not supported yet");
        }

        try {
            StreamUtils.copy(input, new FileOutputStream(new File(baseDir, name)));
            return name;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StorageConfiguration getStorageConfiguration() {
        return configuration;
    }
}
