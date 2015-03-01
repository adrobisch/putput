package org.putput.contacts;

import org.putput.users.UserEntity;
import org.putput.users.UserRepository;
import org.putput.common.UuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService {
  @Autowired
  ContactRepository contactRepository;

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
    contactEntity.getInternetIdentifiers().stream().filter(id -> id.getId() == null)
      .forEach(id -> id.withId(uuidService.uuid()));

    contactEntity.getPhoneNumbers().stream().filter(number -> number.getId() == null)
      .forEach(number -> number.withId(uuidService.uuid()));

    contactEntity.getEmails().stream().filter(number -> number.getId() == null)
      .forEach(email -> email.withId(uuidService.uuid()));

    contactEntity.getContactAddresses().stream().filter(address -> address.getId() == null)
      .forEach(address -> address.withId(uuidService.uuid()));

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

  public void update(ContactEntity updatedContact) {
    contactRepository.save(withIds(updatedContact));
  }
}
