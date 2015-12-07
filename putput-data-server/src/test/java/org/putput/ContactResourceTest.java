package org.putput;

import org.junit.Test;
import org.putput.api.model.*;
import org.putput.contacts.ContactEntity;
import org.putput.contacts.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactResourceTest extends SpringIntegrationTest {

  @Autowired
  ContactRepository contactRepository;

  @Test
  public void shouldGetContactById() throws Exception {
    Contact theContact = get("/api/contact/1").as(Contact.class);

    ContactEntity contactEntity = contactRepository.findOne("1");

    ContactAssert.assertThat(theContact).hasId(contactEntity.getId());
  }

  @Test
  public void shouldUpdateContact() throws Exception {
    Contact theContact = get("/api/contact/1").as(Contact.class);

    theContact.getPhoneNumbers().add(new PhoneNumber()
            .withNumber("+491723646646")
            .withType("WORK"));

    theContact.getEmails().clear();

    theContact.getEmails().add(new Email()
            .withAddress("test@example.org")
            .withType("WORK"));

    theContact.getInternetIdentifiers().add(new InternetIdentifier()
            .withIdValue("@example")
            .withType("TWITTER"));

    theContact.getContactAddresses().add(new ContactAddress()
            .withCountry("Germany")
            .withHouseNo("42")
            .withPoBox("PO BOX")
            .withPostalCode("84784")
            .withStreet("Main Street")
            .withType("HOME")
            .withCity("Another City"));

    given().body(theContact)
            .contentType("application/json")
            .when()
              .put(theContact.getLinks().getSelf().getHref())
            .then()
              .assertThat()
              .statusCode(200);

    String response = get("/api/contact/1").asString();

    assertThat(response, containsString("+491723646646"));
    assertThat(response, containsString("test@example.org"));
    assertThat(response, containsString("84784"));
    assertThat(response, containsString("@example"));
  }

}