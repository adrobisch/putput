package org.putput.contacts;

import java.util.List;

public class ListOfContacts {
  List<ContactEntity> contacts;

  public ListOfContacts(List<ContactEntity> contacts) {
    this.contacts = contacts;
  }

  public List<ContactEntity> getContacts() {
    return contacts;
  }
}
