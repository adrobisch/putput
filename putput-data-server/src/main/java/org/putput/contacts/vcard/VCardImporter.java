package org.putput.contacts.vcard;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.parameter.AddressType;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.*;
import org.putput.contacts.*;
import org.putput.files.FileService;
import org.putput.files.PutPutFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Component
public class VCardImporter {

  ContactService contactService;

  FileService fileService;

  Logger log = LoggerFactory.getLogger(VCardImporter.class);

  @Autowired
  public VCardImporter(ContactService contactService, FileService fileService) {
    this.contactService = contactService;
    this.fileService = fileService;
  }

  @Transactional
  public ImportResult importVCard(InputStream vcardContent, String username) {
    List<VCard> vcards = parseInput(vcardContent);
    List<ContactEntity> importedContactEntities = new ArrayList<>();

    for (VCard vcard : vcards) {
      ContactEntity contactEntity = new ContactEntity();
      mapToContact(vcard, contactEntity, username);

      log.info(format("importing %s as %s", vcard, contactEntity));

      contactService.createContact(username, contactEntity);
      importedContactEntities.add(contactEntity);
    }

    return new ImportResult(importedContactEntities);
  }

  private List<VCard> parseInput(InputStream vcardContent) {
    try {
      return Ezvcard.parse(vcardContent).all();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void mapToContact(VCard vcard, ContactEntity contactEntity, String username) {
    setNames(vcard, contactEntity);
    setEmail(vcard, contactEntity);
    setPhoneNumbers(vcard, contactEntity);
    setOrganisation(vcard, contactEntity);
    addPhotos(username, vcard, contactEntity);
    addAddresses(vcard, contactEntity);
  }

  private void addAddresses(VCard vcard, ContactEntity contactEntity) {
    for (Address address : vcard.getAddresses()) {
      ContactAddressEntity contactAddressEntity = new ContactAddressEntity()
        .withCity(address.getLocality())
        .withCountry(address.getCountry())
        .withPostalCode(address.getPostalCode())
        .withStreet(address.getStreetAddress());

      if (address.getTypes().contains(AddressType.HOME)) {
        contactAddressEntity.withType(ContactAddressEntity.Type.HOME);
      } else if (address.getTypes().contains(AddressType.WORK)) {
        contactAddressEntity.withType(ContactAddressEntity.Type.WORK);
      }

      contactEntity.getContactAddressEntities().add(contactAddressEntity);
    }
  }

  private void addPhotos(String username, VCard vcard, ContactEntity contactEntity) {
    for (Photo photo : vcard.getPhotos()) {
      if (photo.getData() != null) {
        PutPutFile photoFile = fileService.saveUserFile(username,
          Optional.<String>empty(),
          photo.getContentType().getMediaType(),
          new ByteArrayInputStream(photo.getData()),
          photo.getData().length);

        contactEntity.getPhotos().add(photoFile);
      }
    }
  }

  private void setOrganisation(VCard vcard, ContactEntity contactEntity) {
    if (vcard.getOrganization() != null) {
      contactEntity.withOrganisation(vcard.getOrganization().getValues().stream().reduce("", String::concat));
    }
  }

  private void setPhoneNumbers(VCard vcard, ContactEntity contactEntity) {
    for (Telephone number : vcard.getTelephoneNumbers()) {
      if (number.getTypes().isEmpty() || number.getTypes().stream().anyMatch(type -> type == TelephoneType.HOME)) {
        contactEntity.getPhoneNumbers().add(new PhoneNumber(PhoneNumber.Type.HOME, getNumber(number)));
      } else if (number.getTypes().stream().anyMatch(type -> type == TelephoneType.WORK)) {
        contactEntity.getPhoneNumbers().add(new PhoneNumber(PhoneNumber.Type.WORK, getNumber(number)));
      } else if (number.getTypes().stream().anyMatch(type -> type == TelephoneType.CELL)) {
        contactEntity.getPhoneNumbers().add(new PhoneNumber(PhoneNumber.Type.MOBILE, getNumber(number)));
      }
    }
  }

  private String getNumber(Telephone number) {
    if (number.getText() != null && !number.getText().isEmpty()) {
      return number.getText();
    } else if (number.getUri() != null) {
      return number.getUri().getNumber();
    }
    throw new IllegalArgumentException("Number should be set as text or in URI");
  }

  private void setEmail(VCard vcard, ContactEntity contactEntity) {
    for (Email email :  vcard.getEmails()) {
      if (email.getTypes().isEmpty() || email.getTypes().stream().anyMatch(type -> type == EmailType.HOME)) {
        contactEntity.getEmails().add(new EMailAddress(EMailAddress.Type.HOME, email.getValue()));
      } else if (email.getTypes().stream().anyMatch(type -> type == EmailType.WORK)) {
        contactEntity.getEmails().add(new EMailAddress(EMailAddress.Type.WORK, email.getValue()));
      }
    }
  }

  private void setNames(VCard vcard, ContactEntity contactEntity) {
    String titleString = vcard.getTitles().stream().map(Title::getValue).reduce("", String::concat);

    contactEntity.withSalutation(titleString);
    if (vcard.getStructuredName() != null) {
      contactEntity.withFirstName(vcard.getStructuredName().getGiven());
      contactEntity.withLastName(vcard.getStructuredName().getFamily());
    }
  }
}
