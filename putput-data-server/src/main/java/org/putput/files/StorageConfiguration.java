package org.putput.files;

import org.putput.common.persistence.BaseEntity;
import org.putput.users.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "PP_STORAGE")
public class StorageConfiguration extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name = "_OWNER_ID")
    UserEntity user;

    @Column(name = "_TYPE")
    String type;

    @Column(name = "_IS_DEFAULT")
    int isDefault;
    
    @OneToMany
    @JoinColumn(name = "_STORAGE_ID")
    @MapKeyColumn(name="_KEY")
    Map<String, StorageParameter> storageParameters = new HashMap<>();

    public UserEntity getUser() {
        return user;
    }

    public String getType() {
        return type;
    }

    public StorageConfiguration setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public StorageConfiguration setType(String type) {
        this.type = type;
        return this;
    }

    public boolean isDefault() {
        return isDefault == 1;
    }

    public StorageConfiguration setIsDefault(int isDefault) {
        this.isDefault = isDefault;
        return this;
    }

    public Map<String, StorageParameter> getStorageParameters() {
        return storageParameters;
    }

    public void setStorageParameters(Map<String, StorageParameter> storageParameters) {
        this.storageParameters = storageParameters;
    }
}
