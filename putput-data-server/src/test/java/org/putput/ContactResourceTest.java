package org.putput;

import org.junit.Test;
import org.putput.api.model.Contact;
import org.putput.api.model.ContactAssert;
import org.putput.contacts.ContactEntity;
import org.putput.contacts.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jayway.restassured.RestAssured.get;

public class ContactResourceTest extends SpringIntegrationTest {

  @Autowired
  ContactRepository contactRepository;

  @Test
  public void testGetContactById() throws Exception {
    Contact theContact = get("/api/contact/1").as(Contact.class);

    ContactEntity contactEntity = contactRepository.findOne("1");

    ContactAssert.assertThat(theContact).hasId(contactEntity.getId());
  }

}