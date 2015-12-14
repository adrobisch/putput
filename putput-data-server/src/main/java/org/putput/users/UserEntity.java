package org.putput.users;

import org.putput.common.persistence.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PP_USER")
public class UserEntity extends BaseEntity<UserEntity> {
  @Column(name = "_USERNAME")
  String username;

  @Column(name = "_EMAIL")
  String email;

  @Column(name = "_ABOUT")
  String about;

  @Column(name = "_DISPLAY_NAME")
  String displayName;

  @Column(name = "_PASSWORD_HASH")
  String passwordHash;

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public String getEmail() {
    return email;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserEntity setUsername(String username) {
    this.username = username;
    return this;
  }
}