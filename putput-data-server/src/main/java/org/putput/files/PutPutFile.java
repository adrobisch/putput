package org.putput.files;

import org.putput.common.persistence.BaseEntity;
import org.putput.images.PutPutImage;
import org.putput.users.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Optional;

@Entity
@Table(name = "PP_FILE")
public class PutPutFile extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "_OWNER_ID")
  UserEntity user;
  
  @OneToOne
  @JoinColumn(name = "_STORAGE_CONFIG_ID")
  StorageConfiguration storageConfiguration;

  @OneToOne
  @JoinColumn(name = "_PARENT_ID")          
  PutPutFile parent;

  @Column(name = "_NAME")
  String name;  
  
  @Column(name = "_STORAGE_REFERENCE")
  String storageReference;  
  
  @Column(name = "_STORAGE_CONTAINER_REFERENCE")
  String storageContainerReference;

  @Column(name = "_IS_DIRECTORY")
  int isDirectory;

  @Column(name = "_MIME_TYPE")
  String mimeType;

  @Column(name = "_SIZE")
  Long size;

  @OneToOne
  @JoinColumn(name = "_IMAGE_ID")
  PutPutImage previewImage;

  public PutPutFile() {
  }

  public UserEntity getUser() {
    return user;
  }

  public StorageConfiguration getStorageConfiguration() {
    return storageConfiguration;
  }

  public Optional<PutPutFile> getParent() {
    return Optional.ofNullable(parent);
  }

  public String getName() {
    return name;
  }

  public boolean isDirectory() {
    return isDirectory == 1;
  }

  public String getMimeType() {
    return mimeType;
  }

  public Long getSize() {
    return size;
  }

  public PutPutFile setUser(UserEntity user) {
    this.user = user;
    return this;
  }

  public PutPutFile setStorageConfiguration(StorageConfiguration storageConfiguration) {
    this.storageConfiguration = storageConfiguration;
    return this;
  }

  public PutPutFile setParent(PutPutFile parent) {
    this.parent = parent;
    return this;
  }

  public PutPutFile setName(String name) {
    this.name = name;
    return this;
  }

  public PutPutFile setIsDirectory(int isDirectory) {
    this.isDirectory = isDirectory;
    return this;
  }

  public PutPutFile setMimeType(String mimeType) {
    this.mimeType = mimeType;
    return this;
  }

  public PutPutFile setSize(Long size) {
    this.size = size;
    return this;
  }

  public PutPutImage getPreviewImage() {
    return previewImage;
  }

  public PutPutFile setPreviewImage(PutPutImage previewImage) {
    this.previewImage = previewImage;
    return this;
  }

  public String getStorageReference() {
    return storageReference;
  }

  public PutPutFile setStorageReference(String storageReference) {
    this.storageReference = storageReference;
    return this;
  }

  public String getStorageContainerReference() {
    return storageContainerReference;
  }

  public PutPutFile setStorageContainerReference(String storageContainerReference) {
    this.storageContainerReference = storageContainerReference;
    return this;
  }
}
