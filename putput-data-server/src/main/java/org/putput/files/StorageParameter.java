package org.putput.files;

import org.putput.common.persistence.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PP_STORAGE_PARAMETER")
public class StorageParameter extends BaseEntity {

    @Column(name = "_KEY")
    String key;

    @Column(name = "_VALUE")
    String value;
    
    @ManyToOne
    @JoinColumn(name = "_STORAGE_ID")
    StorageConfiguration storageConfiguration;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public StorageParameter setKey(String key) {
        this.key = key;
        return this;
    }

    public StorageParameter setValue(String value) {
        this.value = value;
        return this;
    }

    public StorageConfiguration getStorageConfiguration() {
        return storageConfiguration;
    }

    public StorageParameter setStorageConfiguration(StorageConfiguration storageConfiguration) {
        this.storageConfiguration = storageConfiguration;
        return this;
    }
}
