package org.putput.contacts;

import javax.persistence.*;

@Entity
@Table(name = "PP_CONTACT_INTERNET_ID")
public class InternetIdentifier {

  public enum Type {
    MAIL_HOME,
    MAIL_WORK,
    MSN,
    AIM,
    JABBER,
    SKYPE,
    TWITTER,
    GITHUB,
    GOOGLE
  }

  @Id
  @Column(name ="_ID")
  String id;

  @Column(name ="_TYPE")
  String type;

  @Column(name = "_ID_VALUE")
  String idValue;

  @OneToOne
  @JoinColumn(name = "_CONTACT_ID")
  ContactEntity contact;

  public InternetIdentifier() {
  }

  public InternetIdentifier(InternetIdentifier.Type type, String idValue) {
    this.type = type.name();
    this.idValue = idValue;
  }

  public String getId() {
    return id;
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

  public InternetIdentifier withContact(ContactEntity contact) {
      this.contact = contact;
      return this;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setIdValue(String idValue) {
    this.idValue = idValue;
  }

  public InternetIdentifier withId(String id) {
    this.id = id;
    return this;
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
