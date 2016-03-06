package org.putput.common.security;

public class AccessToken {
  String username;
  Long expiryDate;
  String hash;

  public AccessToken(String username, Long expiryDate, String hash) {
    this.username = username;
    this.expiryDate = expiryDate;
    this.hash = hash;
  }

  public String getUsername() {
    return username;
  }

  public Long getExpiryDate() {
    return expiryDate;
  }

  public String getHash() {
    return hash;
  }
}
