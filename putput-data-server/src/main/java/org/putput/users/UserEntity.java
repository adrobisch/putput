package org.putput.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PP_USER")
public class UserEntity {
  @Id
  @Column(name = "_ID")
  String id;

  @Column(name = "_USERNAME")
  String username;

  @Column(name = "_EMAIL")
  String email;

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

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}