package org.putput.contacts;

import ma.glasnost.orika.MapperFacade;
import org.putput.api.model.ContactLinks;
import org.putput.api.model.NewContact;
import org.putput.api.resource.Contact;
import org.putput.common.web.BaseResource;
import org.putput.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactResource extends BaseResource implements Contact {

  @Autowired
  ContactService contactService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  MapperFacade beanMapper;

  @Override
  public PostContactResponse postContact(NewContact newContact) throws Exception {
    ContactEntity contactEntityToBeCreated = new ContactEntity()
      .withFirstName(newContact.getFirstName())
      .withLastName(newContact.getLastName());

    String email = newContact.getEmail();
    if (email != null && !email.isEmpty()) {
      contactEntityToBeCreated.getEmails().add(new EMailAddress(EMailAddress.Type.HOME, email));
    }

    String mobilePhone = newContact.getMobilePhone();
    if (mobilePhone != null && !mobilePhone.isEmpty()) {
      contactEntityToBeCreated.getPhoneNumbers().add(new PhoneNumber(PhoneNumber.Type.MOBILE, mobilePhone));
    }

    ContactEntity newContactEntity = contactService.createContact(user().getUsername(), contactEntityToBeCreated);

    return PostContactResponse.withCreated(link(Contact.class, newContactEntity.getId()).getHref());
  }

  @Override
  public GetContactByIdResponse getContactById(String id) throws Exception {
    org.putput.api.model.Contact contact =
      beanMapper.map(contactService.getById(id), org.putput.api.model.Contact.class)
        .withLinks(new ContactLinks().withSelf(link(Contact.class, id)));

    return GetContactByIdResponse.withHaljsonOK(contact);
  }

  @Override
  public PutContactByIdResponse putContactById(String id, org.putput.api.model.Contact updatedContact) throws Exception {
    ContactEntity contactEntity = beanMapper.map(updatedContact, ContactEntity.class)
            .withUser(userRepository.findByUsername(user().getUsername()));
    contactService.update(contactEntity);
    return PutContactByIdResponse.withOK();
  }

  @Override
  public DeleteContactByIdResponse deleteContactById(String id) throws Exception {
    contactService.deleteById(id);
    return DeleteContactByIdResponse.withOK();
  }
}
