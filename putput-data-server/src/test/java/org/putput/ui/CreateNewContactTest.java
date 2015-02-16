package org.putput.ui;

import com.google.common.base.Function;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.core.annotation.Page;
import org.junit.Test;
import org.putput.BrowserTest;

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

    await().until("td").containsText("John");
  }
}
