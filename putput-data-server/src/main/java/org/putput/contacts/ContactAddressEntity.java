package org.putput.contacts;

import javax.persistence.*;

@Entity
@Table(name = "PP_CONTACT_ADDRESS")
public class ContactAddressEntity {

  public static enum Type {
    HOME,
    WORK,
    SECONDARY_HOME,
    OTHER
  }

  @Id
  @Column(name ="_ID")
  String id;

  @Column(name = "_TYPE")
  String type;

  @Column(name = "_STREET")
  String street;

  @Column(name = "_HOUSE_NO")
  String houseNo;

  @Column(name = "_PO_BOX")
  String poBox;

  @Column(name = "_CITY")
  String city;

  @Column(name = "_COUNTRY")
  String country;

  @Column(name = "_POSTAL_CODE")
  String postalCode;

  public ContactAddressEntity() {
  }

  public String getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public String getStreet() {
    return street;
  }

  public String getHouseNo() {
    return houseNo;
  }

  public String getPoBox() {
    return poBox;
  }

  public String getCity() {
    return city;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getCountry() {
    return country;
  }

  public ContactAddressEntity withStreet(String street) {
    this.street = street;
    return this;
  }

  public ContactAddressEntity withHouseNo(String houseNo) {
    this.houseNo = houseNo;
    return this;
  }

  public ContactAddressEntity withPoBox(String poBox) {
    this.poBox = poBox;
    return this;
  }

  public ContactAddressEntity withCity(String city) {
    this.city = city;
    return this;
  }

  public ContactAddressEntity withCountry(String country) {
    this.country = country;
    return this;
  }

  public ContactAddressEntity withPostalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  public ContactAddressEntity withId(String id) {
    this.id = id;
    return this;
  }

  public ContactAddressEntity withType(ContactAddressEntity.Type type) {
    this.type = type.name();
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ContactAddressEntity contactAddressEntity = (ContactAddressEntity) o;

    if (city != null ? !city.equals(contactAddressEntity.city) : contactAddressEntity.city != null) return false;
    if (country != null ? !country.equals(contactAddressEntity.country) : contactAddressEntity.country != null) return false;
    if (houseNo != null ? !houseNo.equals(contactAddressEntity.houseNo) : contactAddressEntity.houseNo != null) return false;
    if (poBox != null ? !poBox.equals(contactAddressEntity.poBox) : contactAddressEntity.poBox != null) return false;
    if (postalCode != null ? !postalCode.equals(contactAddressEntity.postalCode) : contactAddressEntity.postalCode != null) return false;
    if (street != null ? !street.equals(contactAddressEntity.street) : contactAddressEntity.street != null) return false;
    if (type != null ? !type.equals(contactAddressEntity.type) : contactAddressEntity.type != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (street != null ? street.hashCode() : 0);
    result = 31 * result + (houseNo != null ? houseNo.hashCode() : 0);
    result = 31 * result + (poBox != null ? poBox.hashCode() : 0);
    result = 31 * result + (city != null ? city.hashCode() : 0);
    result = 31 * result + (country != null ? country.hashCode() : 0);
    result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Address{" +
      "id='" + id + '\'' +
      ", type='" + type + '\'' +
      ", street='" + street + '\'' +
      ", houseNo='" + houseNo + '\'' +
      ", poBox='" + poBox + '\'' +
      ", city='" + city + '\'' +
      ", country='" + country + '\'' +
      ", postalCode='" + postalCode + '\'' +
      '}';
  }
}
