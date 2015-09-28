package org.putput.files;

import java.util.Optional;

public class StorageReference {
    String name;
    String mimeType;
    String containerReference;
    protected boolean isDirectory = false;
    long size;

    public StorageReference() {
    }

    public String getName() {
        return name;
    }

    public StorageReference setName(String name) {
        this.name = name;
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

    public long getSize() {
        return size;
    }

    public StorageReference setSize(long size) {
        this.size = size;
        return this;
    }

    public String getMimeType() {
        return mimeType;
    }

    public StorageReference setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    @Override
    public String toString() {
        return "StorageReference{" +
                "name='" + name + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", containerReference='" + containerReference + '\'' +
                ", isDirectory=" + isDirectory +
                ", size=" + size +
                '}';
    }
}
