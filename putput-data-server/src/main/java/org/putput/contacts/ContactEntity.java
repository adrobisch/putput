package org.putput.contacts;

import org.putput.files.PutPutFile;
import org.putput.users.UserEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
  List<ContactAddressEntity> contactAddressEntities = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "_CONTACT_ID")
  List<PhoneNumber> phoneNumbers = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "_CONTACT_ID")
  List<InternetIdentifier> internetIdentifiers = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "_CONTACT_ID")
  List<EMailAddress> emails = new ArrayList<>();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name="PP_CONTACT_IMAGE",
    joinColumns={@JoinColumn(name="_CONTACT_ID", referencedColumnName="_ID")},
    inverseJoinColumns={@JoinColumn(name="_FILE_ID", referencedColumnName="_ID")})
  List<PutPutFile> photos = new ArrayList<>();

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

  public List<PhoneNumber> getPhoneNumbers() {
    return phoneNumbers;
  }

  public List<ContactAddressEntity> getContactAddressEntities() {
    return contactAddressEntities;
  }

  public List<InternetIdentifier> getInternetIdentifiers() {
    return internetIdentifiers;
  }

  public List<EMailAddress> getEmails() {
    return emails;
  }

  public List<PutPutFile> getPhotos() {
    return photos;
  }
}
