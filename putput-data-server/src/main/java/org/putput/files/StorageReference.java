package org.putput.files;

import java.util.Optional;

public class StorageReference {
    String contentReference;
    String containerReference;
    protected boolean isDirectory;

    public StorageReference() {
    }

    public Optional<String> getContentReference() {
        return Optional.ofNullable(contentReference);
    }

    public StorageReference setContentReference(String contentReference) {
        this.contentReference = contentReference;
        return this;
    }

    public Optional<String> getContainerReference() {
        return Optional.ofNullable(containerReference);
    }

    public StorageReference setContainerReference(String containerReference) {
        this.containerReference = containerReference;
        return this;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public StorageReference setDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
        return this;
    }
}
