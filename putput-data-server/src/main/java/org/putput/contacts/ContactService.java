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
    contactEntity.getInternetIdentifiers().stream()
      .forEach(id -> {
          if (id.getId() == null) {
            id.withId(uuidService.uuid());
          }
          id.withContact(contactEntity);
      });

    contactEntity.getPhoneNumbers().stream()
      .forEach(number -> {
          if (number.getId() == null) {
            number.withId(uuidService.uuid());
          }
          number.withContact(contactEntity);
      });

    contactEntity.getEmails().stream()
      .forEach(email -> {
          if (email.getId() == null) {
            email.withId(uuidService.uuid());
          }
          email.withContact(contactEntity);
      });

    contactEntity.getContactAddresses().stream()
      .forEach(address -> {
          if (address.getId() == null) {
            address.withId(uuidService.uuid());
          }
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

  public ContactEntity update(ContactEntity updatedContact) {
    return contactRepository.save(withIds(updatedContact));
  }
}
