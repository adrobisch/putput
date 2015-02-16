package org.putput.contacts;

import org.dozer.DozerBeanMapper;
import org.putput.api.model.ContactList;
import org.putput.api.model.ContactListLinks;
import org.putput.api.resource.Contacts;
import org.putput.common.web.BaseResource;
import org.putput.contacts.vcard.ImportResult;
import org.putput.contacts.vcard.VCardImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;

@Service
public class ContactsResource extends BaseResource implements Contacts {

  @Autowired
  DozerBeanMapper beanMapper;

  @Autowired
  ContactService contactService;

  @Autowired
  VCardImporter cardImporter;

  @Autowired
  CounterService counterService;

  @Override
  public GetContactsResponse getContacts(BigDecimal page) throws Exception {
    ContactList contactList = beanMapper.map(contactService.getByUserName(user().getUsername()), ContactList.class);
    return GetContactsResponse.withHaljsonOK(contactList.withLinks(new ContactListLinks().withSelf(link(Contacts.class))));
  }

  @Override
  public PostOctetstreamContactsImportResponse postOctetstreamContactsImport(InputStream contactStream) throws Exception {
    importContacts(contactStream);
    return PostOctetstreamContactsImportResponse.withOK();
  }

  @Override
  public PostVcardContactsImportResponse postVcardContactsImport(String vcardInput) throws Exception {
    importContacts(new ByteArrayInputStream(vcardInput.getBytes("UTF-8")));
    return PostVcardContactsImportResponse.withOK();
  }

  public ImportResult importContacts(InputStream vcardInput) throws Exception {
    counterService.increment("vcards.imported");
    return cardImporter.importVCard(vcardInput, user().getUsername());
  }
}
