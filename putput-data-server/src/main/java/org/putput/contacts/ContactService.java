package org.putput.contacts;

import org.putput.api.model.*;
import org.putput.common.persistence.BaseEntity;
import org.putput.users.UserEntity;
import org.putput.users.UserRepository;
import org.putput.common.UuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
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
  EMailAddressRepository eMailAddressRepository;
  
  @Autowired
  PhoneNumberRepository phoneNumberRepository;
  
  @Autowired
  InternetIdentifierRepository internetIdentifierRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UuidService uuidService;

  @Transactional
  public ContactEntity createContact(String userName, ContactEntity contactEntity) {
    UserEntity user = userRepository.findByUsername(userName);
    saveContact(withNewIds(contactEntity.withUser(user)));
    return contactEntity;
  }

  private ContactEntity withNewIds(ContactEntity contactEntity) {
    contactEntity.getInternetIdentifiers().stream()
      .forEach(id -> {
          id.setId(uuidService.uuid());
          id.setContact(contactEntity);
      });

    contactEntity.getPhoneNumbers().stream()
      .forEach(number -> {
          number.setId(uuidService.uuid());
          number.setContact(contactEntity);
      });

    contactEntity.getEmails().stream()
      .forEach(email -> {
          email.setId(uuidService.uuid());
          email.setContact(contactEntity);
      });

    contactEntity.getContactAddresses().stream()
      .forEach(address -> {
          address.setId(uuidService.uuid());
          address.setContact(contactEntity);
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

      updateCollections(updatedContact, contactEntity);

      return contactEntity;
    };
  }

  private void updateCollections(Contact updatedContact, ContactEntity contactEntity) {
    updatePhoneNumbers(updatedContact, contactEntity);
    updateAddresses(updatedContact, contactEntity);
    updateEMails(updatedContact, contactEntity);
    updateInternetIdentifiers(updatedContact, contactEntity);
  }

  private void updateInternetIdentifiers(Contact updatedContact, ContactEntity contactEntity) {
    updateCollection(contactEntity.getInternetIdentifiers(),
            updatedContact.getInternetIdentifiers(),
            () -> new InternetIdentifier()
                    .setId(uuidService.uuid())
                    .setContact(contactEntity),
            org.putput.api.model.InternetIdentifier::getId,
            newIdentifier -> existingIdentifier -> existingIdentifier
                    .setIdValue(newIdentifier.getIdValue())
                    .setType(newIdentifier.getType()),
            internetIdentifierRepository);  
  }

  private void updatePhoneNumbers(Contact updatedContact, ContactEntity contactEntity) {
    updateCollection(contactEntity.getPhoneNumbers(),
            updatedContact.getPhoneNumbers(),
            () -> new PhoneNumber()
                    .setId(uuidService.uuid())
                    .setContact(contactEntity),
            org.putput.api.model.PhoneNumber::getId,
            newNumber -> existingPhoneNumber -> existingPhoneNumber
                    .setNumber(newNumber.getNumber())
                    .setType(newNumber.getType()),
            phoneNumberRepository);  
  }

  private void updateEMails(Contact updatedContact, ContactEntity contactEntity) {
    updateCollection(contactEntity.getEmails(),
            updatedContact.getEmails(),
            () -> new EMailAddress()
                    .setId(uuidService.uuid())
                    .setContact(contactEntity),
            Email::getId,
            email -> (existingEMail) -> existingEMail
                    .setAddress(email.getAddress())
                    .setType(email.getType()),
            eMailAddressRepository);
  }

  private void updateAddresses(Contact updatedContact, ContactEntity contactEntity) {
    updateCollection(contactEntity.getContactAddresses(),
        updatedContact.getContactAddresses(),
        () -> new ContactAddressEntity()
                          .setId(uuidService.uuid())
                          .setContact(contactEntity),
        ContactAddress::getId,
        newContactAddress -> existingContactAddress -> {
          existingContactAddress.setCity(newContactAddress.getCity());
          existingContactAddress.setCountry(newContactAddress.getCountry());
          existingContactAddress.setHouseNo(newContactAddress.getHouseNo());
          existingContactAddress.setPoBox(newContactAddress.getPoBox());
          existingContactAddress.setPostalCode(newContactAddress.getPostalCode());
          existingContactAddress.setStreet(newContactAddress.getStreet());
          existingContactAddress.setType(newContactAddress.getType());
          return existingContactAddress;
        },
        contactAddressRepository);
  }

  private <T extends BaseEntity, U> void updateCollection(Collection<T> currentValues,
                                                       Collection<U> updatedValues,
                                                       Supplier<T> newInstance,
                                                       Function<U, String> idFun,
                                                       Function<U, Function<T, T>> updateFunctionSupplier,
                                                       CrudRepository<T, String> repository) {
    Map<String, List<T>> valuesMap = currentValues.stream()
        .collect(Collectors.groupingBy(T::getId));

    List<String> newIds = updatedValues
        .stream()
        .map(newCollectionItem -> {
          Optional<T> existingItem = ofNullable(valuesMap.get(idFun.apply(newCollectionItem)))
              .flatMap(toFirst());

          return repository.save(updateFunctionSupplier.apply(newCollectionItem).apply(existingItem
              .orElseGet(newInstance)));
        })
        .map(T::getId)
        .collect(Collectors.toList());

    currentValues
        .stream()
        .filter(notIn(newIds))
        .forEach(removeFrom(currentValues, repository));
  }

  private <T> Consumer<T> removeFrom(Collection<T> collection, CrudRepository<T, String> repository) {
    return item -> {
      repository.delete(item);
      collection.remove(item);
    };
  }

  private <T extends BaseEntity> Predicate<T> notIn(List<String> newAddressIds) {
    return id -> !newAddressIds.contains(id.getId());
  }

  <T> Function<List<T>, Optional<T>> toFirst() {
    return list -> list.stream().findFirst();
  }

}
