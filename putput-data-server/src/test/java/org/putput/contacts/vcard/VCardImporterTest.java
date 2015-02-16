package org.putput.contacts.vcard;

import org.junit.Test;
import org.putput.SpringTest;
import org.putput.contacts.*;
import org.putput.files.FileService;
import org.putput.files.PutPutFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class VCardImporterTest extends SpringTest {

  @Autowired
  ContactService contactService;

  @Autowired
  FileService fileService;

  @Test
  public void shouldImportVCard() throws Exception {

    ImportResult importResult = new VCardImporter(contactService, fileService).importVCard(new FileInputStream(file("erika.vcf")), "user");

    assertThat(importResult.getImportedContactEntities())
      .hasSize(1);

    ContactEntityAssert.assertThat(importResult.getImportedContactEntities().get(0))
      .hasFirstName("Erika")
      .hasLastName("Mustermann")
      .hasOrganisation("Wikipedia")
      .hasSalutation("Oberleutnant")
      .hasPhotos(new PutPutFile("image/jpg"))
      .hasContactAddressEntities(new ContactAddressEntity()
          .withCity("Köln")
          .withStreet("Heidestraße 17")
          .withPostalCode("51147")
          .withCountry("Germany")
          .withType(ContactAddressEntity.Type.HOME)
      )
      .hasPhoneNumbers(new PhoneNumber(PhoneNumber.Type.HOME, "+49-221-1234567"))
      .hasPhoneNumbers(new PhoneNumber(PhoneNumber.Type.WORK, "+49-221-9999123"))
      .hasEmails(new EMailAddress(EMailAddress.Type.HOME, "erika@mustermann.de"));
  }

  private File file(String s) {
    return new File(getClass().getClassLoader().getResource(s).getFile());
  }
}