package org.putput.contacts;

import org.putput.images.PutPutImage;
import org.putput.users.UserEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PP_CONTACT")
public class ContactEntity {

  @Id
  @Column(name = "_ID")
  String id;

  @Column(name = "_FIRST_NAME")
  String firstName;

  @Column(name = "_LAST_NAME")
  String lastName;

  @Column(name = "_ADDITIONAL_NAMES")
  String additionalNames;

  @Column(name = "_SALUTATION")
  String salutation;

  @Column(name = "_DATE_OF_BIRTH")
  Long dateOfBirth;

  @Column(name = "_ANNIVERSARY")
  Long anniversary;

  @Column(name = "_ORGANISATION")
  String organisation;

  @Column(name = "_NOTES")
  String notes;

  @ManyToOne
  @JoinColumn(name = "_OWNER_ID")
  UserEntity user;

  @Version
  @Column(name = "_VERSION")
  Long version;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "_CONTACT_ID")
  Set<ContactAddressEntity> contactAddresses = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "_CONTACT_ID")
  Set<PhoneNumber> phoneNumbers = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "_CONTACT_ID")
  Set<InternetIdentifier> internetIdentifiers = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "_CONTACT_ID")
  Set<EMailAddress> emails = new HashSet<>();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name="PP_CONTACT_IMAGE",
    joinColumns={@JoinColumn(name="_CONTACT_ID", referencedColumnName="_ID")},
    inverseJoinColumns={@JoinColumn(name="_IMAGE_ID", referencedColumnName="_ID")})
  Set<PutPutImage> photos = new HashSet<>();

  public String getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getAdditionalNames() {
    return additionalNames;
  }

  public String getSalutation() {
    return salutation;
  }

  public Long getDateOfBirth() {
    return dateOfBirth;
  }

  public String getOrganisation() {
    return organisation;
  }

  public Long getVersion() {
    return version;
  }

  public ContactEntity withId(String id) {
    this.id = id;
    return this;
  }

  public ContactEntity withFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public ContactEntity withLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public ContactEntity withAdditionalNames(String additionalNames) {
    this.additionalNames = additionalNames;
    return this;
  }

  public ContactEntity withSalutation(String salutation) {
    this.salutation = salutation;
    return this;
  }

  public ContactEntity withDateOfBirth(Long dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  public ContactEntity withOrganisation(String organisation) {
    this.organisation = organisation;
    return this;
  }

  public ContactEntity withAnniversary(Long anniversary) {
    this.anniversary = anniversary;
    return this;
  }

  public UserEntity getUser() {
    return user;
  }

  public ContactEntity withUser(UserEntity user) {
    this.user = user;
    return this;
  }

  public Long getAnniversary() {
    return anniversary;
  }

  public String getNotes() {
    return notes;
  }

  public Set<PhoneNumber> getPhoneNumbers() {
    return phoneNumbers;
  }

  public Set<ContactAddressEntity> getContactAddresses() {
    return contactAddresses;
  }

  public Set<InternetIdentifier> getInternetIdentifiers() {
    return internetIdentifiers;
  }

  public Set<EMailAddress> getEmails() {
    return emails;
  }

  public Set<PutPutImage> getPhotos() {
    return photos;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setAdditionalNames(String additionalNames) {
    this.additionalNames = additionalNames;
  }

  public void setSalutation(String salutation) {
    this.salutation = salutation;
  }

  public void setDateOfBirth(Long dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public void setAnniversary(Long anniversary) {
    this.anniversary = anniversary;
  }

  public void setOrganisation(String organisation) {
    this.organisation = organisation;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public void setContactAddresses(Set<ContactAddressEntity> contactAddresses) {
    this.contactAddresses = contactAddresses;
  }

  public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
    this.phoneNumbers = phoneNumbers;
  }

  public void setInternetIdentifiers(Set<InternetIdentifier> internetIdentifiers) {
    this.internetIdentifiers = internetIdentifiers;
  }

  public void setEmails(Set<EMailAddress> emails) {
    this.emails = emails;
  }

  public void setPhotos(Set<PutPutImage> photos) {
    this.photos = photos;
  }
}
