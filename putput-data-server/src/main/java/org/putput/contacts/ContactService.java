package org.putput.contacts;

import org.putput.api.model.Contact;
import org.putput.api.model.ContactAddress;
import org.putput.users.UserEntity;
import org.putput.users.UserRepository;
import org.putput.common.UuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class ContactService {
  @Autowired
  ContactRepository contactRepository;

  @Autowired
  ContactAddressRepository contactAddressRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UuidService uuidService;

  @Transactional
  public ContactEntity createContact(String userName, ContactEntity contactEntity) {
    UserEntity user = userRepository.findByUsername(userName);
    saveContact(withIds(contactEntity.withUser(user)));
    return contactEntity;
  }

  private ContactEntity withIds(ContactEntity contactEntity) {
    contactEntity.getInternetIdentifiers().stream()
      .forEach(id -> {
          id.withId(uuidService.uuid());
          id.withContact(contactEntity);
      });

    contactEntity.getPhoneNumbers().stream()
      .forEach(number -> {
          number.withId(uuidService.uuid());
          number.withContact(contactEntity);
      });

    contactEntity.getEmails().stream()
      .forEach(email -> {
          email.withId(uuidService.uuid());
          email.withContact(contactEntity);
      });

    contactEntity.getContactAddresses().stream()
      .forEach(address -> {
          address.withId(uuidService.uuid());
          address.withContact(contactEntity);
      });

    return contactEntity;
  }

  ListOfContacts getByUserName(String userName) {
    return new ListOfContacts(contactRepository.findByUserName(userName));
  }

  private ContactEntity saveContact(ContactEntity contactEntity) {
    String contactId = uuidService.uuid();
    contactEntity.withId(contactId);
    contactRepository.save(contactEntity);
    return contactEntity;
  }

  public ContactEntity getById(String id) {
    return contactRepository.findOne(id);
  }

  public void deleteById(String id) {
    contactRepository.delete(id);
  }

  @Transactional
  public Optional<ContactEntity> update(org.putput.api.model.Contact updatedContact, User user) {
    if (updatedContact.getId() == null || updatedContact.getId().isEmpty()) {
      throw new IllegalArgumentException("contact update without id given");
    }

    return ofNullable(contactRepository.findOne(updatedContact.getId()))
        .filter(contactEntity -> contactEntity.getUser().getUsername().equals(user.getUsername()))
        .map(mergeExistingWithUpdated(updatedContact))
        .map(updatedContactEntity -> contactRepository.save(updatedContactEntity));
  }

  Function<? super ContactEntity, ContactEntity> mergeExistingWithUpdated(Contact updatedContact) {
    return contactEntity -> {
      contactEntity.setFirstName(updatedContact.getFirstName());
      contactEntity.setLastName(updatedContact.getLastName());
      contactEntity.setAdditionalNames(updatedContact.getAdditionalNames());
      contactEntity.setOrganisation(updatedContact.getOrganisation());
      contactEntity.setSalutation(updatedContact.getSalutation());
      contactEntity.setNotes(updatedContact.getNotes());

      ofNullable(updatedContact.getAnniversary()).ifPresent(anniversary ->
          contactEntity.setAnniversary(anniversary.longValue()));

      ofNullable(updatedContact.getDateOfBirth()).ifPresent(dateOfBirth ->
          contactEntity.setDateOfBirth(dateOfBirth.longValue()));

      Map<String, List<ContactAddressEntity>> existingAddresses = contactEntity.getContactAddresses()
          .stream()
          .collect(Collectors.groupingBy(ContactAddressEntity::getId));

      List<String> newAddressIds = updatedContact.getContactAddresses()
          .stream()
          .map(newContactAddress -> mergeAddress(contactEntity, newContactAddress, existingAddresses))
          .map(ContactAddressEntity::getId)
          .collect(Collectors.toList());

      contactEntity
          .getContactAddresses()
          .stream()
          .filter(notIn(newAddressIds))
          .forEach(removeFrom(contactEntity));

      return contactEntity;
    };
  }

  private Consumer<ContactAddressEntity> removeFrom(ContactEntity contactEntity) {
    return address -> {
      contactAddressRepository.delete(address);
      contactEntity.getContactAddresses().remove(address);
    };
  }

  private Predicate<ContactAddressEntity> notIn(List<String> newAddressIds) {
    return id -> !newAddressIds.contains(id.getId());
  }

  ContactAddressEntity mergeAddress(ContactEntity contact, ContactAddress newContactAddress, Map<String, List<ContactAddressEntity>> existingAddresses) {
    Optional<ContactAddressEntity> existingAddressWithId = ofNullable(existingAddresses.get(newContactAddress.getId()))
        .flatMap(toFirst());

    return contactAddressRepository.save(withFieldsOf(newContactAddress).apply(existingAddressWithId
        .orElseGet(createNewContactAddress(contact))));
  }

  private Supplier<ContactAddressEntity> createNewContactAddress(ContactEntity contact) {
    return () -> contactAddressRepository.save(new ContactAddressEntity()
        .setId(uuidService.uuid())
        .setContact(contact));
  }

  Function<ContactAddressEntity, ContactAddressEntity> withFieldsOf(ContactAddress newContactAddress) {
    return existingContactAddress -> {
      existingContactAddress.setCity(newContactAddress.getCity());
      existingContactAddress.setCountry(newContactAddress.getCountry());
      existingContactAddress.setHouseNo(newContactAddress.getHouseNo());
      existingContactAddress.setPoBox(newContactAddress.getPoBox());
      existingContactAddress.setPostalCode(newContactAddress.getPostalCode());
      existingContactAddress.setStreet(newContactAddress.getStreet());
      existingContactAddress.setType(newContactAddress.getType());
      return existingContactAddress;
    };
  }

  <T> Function<List<T>, Optional<T>> toFirst() {
    return list -> list.stream().findFirst();
  }

}
