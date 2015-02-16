package org.putput.contacts.vcard;

import org.putput.contacts.ContactEntity;

import java.util.List;

public class ImportResult {
  private final List<ContactEntity> importedContactEntities;

  public ImportResult(List<ContactEntity> importedContactEntities) {
    this.importedContactEntities = importedContactEntities;
  }

  public List<ContactEntity> getImportedContactEntities() {
    return importedContactEntities;
  }
}
