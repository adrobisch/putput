package org.putput.contacts;

import javax.persistence.*;

@Entity
@Table(name = "PP_CONTACT_PHONE")
public class PhoneNumber {

  public static enum Type {
    MOBILE,
    HOME,
    WORK,
    FAX_WORK,
    FAX_HOME,
    PAGER,
    CUSTOM,
    OTHER,
    CALLBACK,
    CAR,
    COMPANY_MAIN,
    ISDN,
    MAIN,
    OTHER_FAX,
    RADIO,
    TELEX,
    TTY_TDD,
    WORK_MOBILE,
    WORK_PAGER,
    ASSISTANT,
    MMS
  }

  @Id
  @Column(name ="_ID")
  String id;

  @Column(name ="_TYPE")
  String type;

  @Column(name = "_NUMBER")
  String number;

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
