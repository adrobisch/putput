package org.putput.storage;

import java.util.Optional;

public class StorageReference<Self extends StorageReference> {
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

    public Self setName(String name) {
        this.name = name;
        return self();
    }

    private Self self() {
        return (Self) this;
    }

    public Optional<String> getContainerReference() {
        return Optional.ofNullable(containerReference);
    }

    public Self setContainerReference(String containerReference) {
        this.containerReference = containerReference;
        return self();
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public Self setDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
        return self();
    }

    public long getSize() {
        return size;
    }

    public Self setSize(long size) {
        this.size = size;
        return self();
    }

    public Optional<String> getMimeType() {
        return Optional.ofNullable(mimeType);
    }

    public Self setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return self();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StorageReference that = (StorageReference) o;

        if (isDirectory != that.isDirectory) return false;
        if (size != that.size) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (mimeType != null ? !mimeType.equals(that.mimeType) : that.mimeType != null) return false;
        return !(containerReference != null ? !containerReference.equals(that.containerReference) : that.containerReference != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (mimeType != null ? mimeType.hashCode() : 0);
        result = 31 * result + (containerReference != null ? containerReference.hashCode() : 0);
        result = 31 * result + (isDirectory ? 1 : 0);
        result = 31 * result + (int) (size ^ (size >>> 32));
        return result;
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
