package org.putput.contacts;

import org.putput.common.persistence.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "PP_CONTACT_EMAIL")
public class EMailAddress extends BaseEntity<EMailAddress> {

  public static class Type {

    String name;

    public Type(String name) {
      this.name = name;
    }

    public String name() {
      return name;
    }

    public static Type HOME = new Type("HOME");
    public static Type WORK = new Type("WORK");
    public static Type MOBILE = new Type("MOBILE");
    public static Type OTHER = new Type("OTHER");

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Type type = (Type) o;

      if (name != null ? !name.equals(type.name) : type.name != null) return false;

      return true;
    }

    @Override
    public int hashCode() {
      return name != null ? name.hashCode() : 0;
    }
  }

  @Column(name ="_TYPE")
  String type;

  @Column(name ="_ADDRESS")
  String address;

  @ManyToOne
  @JoinColumn(name = "_CONTACT_ID")
  ContactEntity contact;

  public EMailAddress() {
  }

  public EMailAddress(EMailAddress.Type type, String address) {
    this.type = type.name();
    this.address = address;
  }

  public String getType() {
    return type;
  }

  public String getAddress() {
    return address;
  }

  public ContactEntity getContact() {
      return contact;
  }

  public EMailAddress setContact(ContactEntity contact) {
      this.contact = contact;
      return this;
  }

  public EMailAddress setType(String type) {
    this.type = type;
    return self();
  }

  public EMailAddress setAddress(String address) {
    this.address = address;
    return self();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    EMailAddress that = (EMailAddress) o;

    if (address != null ? !address.equals(that.address) : that.address != null) return false;
    if (type != null ? !type.equals(that.type) : that.type != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (address != null ? address.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "EMailAddress{" +
      "id='" + id + '\'' +
      ", type='" + type + '\'' +
      ", address='" + address + '\'' +
      '}';
  }
}
