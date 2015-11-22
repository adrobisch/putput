package org.putput.contacts;

import javax.persistence.*;

@Entity
@Table(name = "PP_CONTACT_PHONE")
public class PhoneNumber {

  public static class Type {
    String name;

    public String name() {
      return name;
    }

    public Type(String name) {
      this.name = name;
    }

    public static Type MOBILE = new Type("MOBILE");
    public static Type HOME = new Type("HOME");
    public static Type WORK = new Type("WORK");
    public static Type FAX_WORK = new Type("FAX_WORK");
    public static Type FAX_HOME = new Type("FAX_HOME");
    public static Type PAGER = new Type("PAGER");
    public static Type CUSTOM = new Type("CUSTOM");
    public static Type OTHER = new Type("OTHER");
    public static Type CALLBACK = new Type("CALLBACK");
    public static Type CAR = new Type("CAR");
    public static Type COMPANY_MAIN = new Type("COMPANY_MAIN");
    public static Type ISDN = new Type("ISDN");
    public static Type MAIN = new Type("MAIN");
    public static Type OTHER_FAX = new Type("OTHER_FAX");
    public static Type RADIO = new Type("RADIO");
    public static Type TELEX = new Type("TELEX");
    public static Type TTY_TDD = new Type("TTY_TDD");
    public static Type WORK_MOBILE = new Type("WORK_MOBILE");
    public static Type WORK_PAGER = new Type("WORK_PAGER");
    public static Type ASSISTANT = new Type("ASSISTANT");
    public static Type MMS = new Type("MMS");

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

  @Id
  @Column(name ="_ID")
  String id;

  @Column(name ="_TYPE")
  String type;

  @Column(name = "_NUMBER")
  String number;

  @OneToOne
  @JoinColumn(name = "_CONTACT_ID")
  ContactEntity contact;

  public PhoneNumber() {
  }

  public PhoneNumber(PhoneNumber.Type type, String number) {
    this.type = type.name();
    this.number = number;
  }

  public String getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public String getNumber() {
    return number;
  }

  public ContactEntity getContact() {
      return contact;
  }

  public PhoneNumber withContact(ContactEntity contact) {
      this.contact = contact;
      return this;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public PhoneNumber withId(String id) {
    this.id = id;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PhoneNumber that = (PhoneNumber) o;

    if (number != null ? !number.equals(that.number) : that.number != null) return false;
    if (type != null ? !type.equals(that.type) : that.type != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (number != null ? number.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "PhoneNumber{" +
      "id='" + id + '\'' +
      ", type='" + type + '\'' +
      ", number='" + number + '\'' +
      '}';
  }
}
