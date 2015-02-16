package org.putput.contacts;

/**
 * Entry point for BDD assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
public class BddAssertions {

  /**
   * Creates a new instance of <code>{@link org.putput.api.model.ContactAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.putput.api.model.ContactAssert then(org.putput.api.model.Contact actual) {
    return new org.putput.api.model.ContactAssert(actual);
  }

  /**
   * Creates a new instance of <code>{@link org.putput.contacts.ContactEntityAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static org.putput.contacts.ContactEntityAssert then(org.putput.contacts.ContactEntity actual) {
    return new org.putput.contacts.ContactEntityAssert(actual);
  }

  /**
   * Creates a new <code>{@link BddAssertions}</code>.
   */
  protected BddAssertions() {
    // empty
  }
}
