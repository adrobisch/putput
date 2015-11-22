package org.putput.contacts.vcard;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.parameter.AddressType;
import ezvcard.parameter.EmailType;
import ezvcard.parameter.TelephoneType;
import ezvcard.property.*;
import org.putput.contacts.*;
import org.putput.images.ImageService;
import org.putput.images.PutPutImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

@Component
public class VCardImporter {

  ContactService contactService;

  ImageService imageService;

  Logger log = LoggerFactory.getLogger(VCardImporter.class);

  @Autowired
  public VCardImporter(ContactService contactService, ImageService imageService) {
    this.contactService = contactService;
    this.imageService = imageService;
  }

  @Transactional
  public ImportResult importVCard(InputStream vcardContent, String username) {
    List<VCard> vcards = parseInput(vcardContent);
    List<ContactEntity> importedContactEntities = new ArrayList<>();

    for (VCard vcard : vcards) {
      ContactEntity contactEntity = new ContactEntity();
      mapToContact(vcard, contactEntity, username);

      if (contactEntity.getFirstName() == null && contactEntity.getLastName() == null) {
        deriveFirstNameFromMailOrPhoneNumber(contactEntity);
      }

      log.trace(format("importing %s as %s", reflectionToString(vcard), reflectionToString(contactEntity)));

      contactService.createContact(username, contactEntity);
      importedContactEntities.add(contactEntity);
    }

    return new ImportResult(importedContactEntities);
  }

  private List<VCard> parseInput(InputStream vcardContent) {
    try {
      return Ezvcard.parse(StreamUtils.copyToString(vcardContent, Charset.forName("UTF-8"))).all();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void mapToContact(VCard vcard, ContactEntity contactEntity, String username) {
    setNames(vcard, contactEntity);
    setEmails(vcard, contactEntity);
    setPhoneNumbers(vcard, contactEntity);
    setDates(vcard, contactEntity);
    setOrganisation(vcard, contactEntity);
    addPhotos(username, vcard, contactEntity);
    addAddresses(vcard, contactEntity);
  }

  private void setDates(VCard vcard, ContactEntity contactEntity) {
    if (vcard.getBirthday() != null && vcard.getBirthday().getDate() != null) {
      contactEntity.withDateOfBirth(vcard.getBirthday().getDate().getTime());
    }
    if (vcard.getAnniversary() != null && vcard.getAnniversary().getDate() != null) {
      contactEntity.withAnniversary(vcard.getAnniversary().getDate().getTime());
    }
  }

  private void addAddresses(VCard vcard, ContactEntity contactEntity) {
    for (Address address : vcard.getAddresses()) {
      ContactAddressEntity contactAddressEntity = new ContactAddressEntity()
        .withCity(address.getLocality())
        .withCountry(address.getCountry())
        .withPostalCode(address.getPostalCode())
        .withPoBox(address.getPoBox())
        .withStreet(address.getStreetAddress());

      if (address.getTypes().contains(AddressType.HOME)) {
        contactAddressEntity.withType(ContactAddressEntity.Type.HOME);
      } else if (address.getTypes().contains(AddressType.WORK)) {
        contactAddressEntity.withType(ContactAddressEntity.Type.WORK);
      } else {
        contactAddressEntity.withType(ContactAddressEntity.Type.OTHER);
      }

      contactEntity.getContactAddresses().add(contactAddressEntity);
    }
  }

  private void addPhotos(String username, VCard vcard, ContactEntity contactEntity) {
    for (Photo photo : vcard.getPhotos()) {
      if (photo.getData() != null) {
        PutPutImage photoFile = imageService.saveUserImage(username,
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
      } else if (!number.getTypes().isEmpty()) {
        PhoneNumber.Type type = new PhoneNumber.Type(first(number.getTypes()).get().getValue());
        contactEntity.getPhoneNumbers().add(new PhoneNumber(type, getNumber(number)));
      } else {
        contactEntity.getPhoneNumbers().add(new PhoneNumber(PhoneNumber.Type.OTHER, getNumber(number)));
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

  private void setEmails(VCard vcard, ContactEntity contactEntity) {
    for (Email email :  vcard.getEmails()) {
      if (email.getTypes().isEmpty() || email.getTypes().stream().anyMatch(type -> type == EmailType.HOME)) {
        contactEntity.getEmails().add(new EMailAddress(EMailAddress.Type.HOME, email.getValue()));
      } else if (email.getTypes().stream().anyMatch(type -> type == EmailType.WORK)) {
        contactEntity.getEmails().add(new EMailAddress(EMailAddress.Type.WORK, email.getValue()));
      } else if (!email.getTypes().isEmpty()) {
        EmailType firstType = first(email.getTypes()).get();
        contactEntity.getEmails().add(new EMailAddress(new EMailAddress.Type(firstType.getValue()), email.getValue()));
      } else {
        contactEntity.getEmails().add(new EMailAddress(EMailAddress.Type.OTHER, email.getValue()));
      }
    }
  }

  private <T> Optional<T> first(Collection<T> list) {
    if (list.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(new ArrayList<>(list).get(0));
  }

  private void setNames(VCard vcard, ContactEntity contactEntity) {
    String titleString = vcard.getTitles().stream().map(Title::getValue).reduce("", String::concat);

    if (!titleString.isEmpty()) {
      contactEntity.withSalutation(titleString);
    }

    if (vcard.getStructuredName() != null) {
      contactEntity.withFirstName(vcard.getStructuredName().getGiven());
      contactEntity.withLastName(vcard.getStructuredName().getFamily());
      contactEntity.withAdditionalNames(vcard.getStructuredName().getAdditional().stream().reduce("", String::concat));
    } else if (vcard.getFormattedName() != null){
      contactEntity.withFirstName(vcard.getFormattedName().getValue());
    }
  }

  public void deriveFirstNameFromMailOrPhoneNumber(ContactEntity contact) {
    first(contact.getPhoneNumbers()).map(number -> {
      contact.withFirstName(number.getNumber());
      return number;
    });

    first(contact.getEmails()).map(mail -> {
      contact.withFirstName(mail.getAddress());
      return mail;
    });
  }

}
