package org.putput.files;

import java.io.File;

public class FileSystemReference extends StorageReference<FileSystemReference> {
    String absolutePath;

    public String getAbsolutePath() {
        return absolutePath;
    }

    public FileSystemReference setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
        return this;
    }

    public File toFile() {
        return new File(absolutePath);
    }

}
