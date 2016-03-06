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
}
