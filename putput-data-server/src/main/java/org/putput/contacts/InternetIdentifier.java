package org.putput.contacts;

import org.putput.common.persistence.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "PP_CONTACT_INTERNET_ID")
public class InternetIdentifier extends BaseEntity<InternetIdentifier> {

  public enum Type {
    MAIL_HOME,
    MAIL_WORK,
    MSN,
    AIM,
    JABBER,
    SKYPE,
    TWITTER,
    GITHUB,
    GOOGLE,
    OTHER
  }

  @Column(name ="_TYPE")
  String type;

  @Column(name = "_ID_VALUE")
  String idValue;

  @ManyToOne
  @JoinColumn(name = "_CONTACT_ID")
  ContactEntity contact;

  public InternetIdentifier() {
  }

  public InternetIdentifier(InternetIdentifier.Type type, String idValue) {
    this.type = type.name();
    this.idValue = idValue;
  }

  public String getType() {
    return type;
  }

  public String getIdValue() {
    return idValue;
  }

  public ContactEntity getContact() {
      return contact;
  }

  public InternetIdentifier setContact(ContactEntity contact) {
      this.contact = contact;
      return this;
  }

  public InternetIdentifier setType(String type) {
    this.type = type;
    return self();
  }

  public InternetIdentifier setIdValue(String idValue) {
    this.idValue = idValue;
    return self();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    InternetIdentifier that = (InternetIdentifier) o;

    if (idValue != null ? !idValue.equals(that.idValue) : that.idValue != null) return false;
    if (type != null ? !type.equals(that.type) : that.type != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (idValue != null ? idValue.hashCode() : 0);
    return result;
  }
}
