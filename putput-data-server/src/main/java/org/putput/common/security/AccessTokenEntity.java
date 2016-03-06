package org.putput.common.security;

import org.putput.common.persistence.BaseEntity;
import org.putput.users.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PP_ACCESS_TOKEN")
public class AccessTokenEntity extends BaseEntity<AccessTokenEntity> {
    
    @Column(name = "_CLIENT_ID")
    String clientId;
    
    @Column(name = "_TOKEN")
    String token;

    @Column(name = "_SECRET")
    String secret;

    @Column(name = "_EXPIRY_DATE")
    Long expiryDate;

    @ManyToOne
    @JoinColumn(name = "_OWNER_ID")
    UserEntity owner;

    public String getToken() {
        return token;
    }

    public String getSecret() {
        return secret;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public Long getExpiryDate() {
        return expiryDate;
    }

    public String getClientId() {
        return clientId;
    }

    public AccessTokenEntity setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public AccessTokenEntity setToken(String token) {
        this.token = token;
        return this;
    }

    public AccessTokenEntity setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public AccessTokenEntity setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }
}
