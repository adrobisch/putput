package org.putput.ui;

import org.fluentlenium.core.annotation.Page;
import org.junit.Test;
import org.putput.BrowserTest;

import java.util.concurrent.TimeUnit;

public class CreateNewContactTest extends BrowserTest {
  @Page
  public ContactsPage contactsPage;

  @Test
  public void newContactShouldBeVisibleInTable() {
    goTo(contactsPage);

    click("#btn-new-contact");
    fill("#firstname").with("John");
    fill("#lastname").with("Foo");
    fill("#email").with("john@foo.com");
    fill("#mobile").with("+4917112345675");

    click("#btn-submit-contact");

    await().atMost(60, TimeUnit.SECONDS).until("td").containsText("John");
  }
}
