package org.putput;

import org.putput.api.model.ServiceDocument;
import org.putput.api.model.ServiceDocumentLinks;
import org.putput.api.resource.*;
import org.putput.common.web.BaseResource;
import org.springframework.stereotype.Service;

@Service
public class ApiResource extends BaseResource implements Root {
  @Override
  public GetResponse get() throws Exception {
    ServiceDocumentLinks serviceDocumentLinks = new ServiceDocumentLinks()
      .withSelf(link(Root.class))
      .withUsers(link(Users.class))
      .withUser(link(User.class, "info"))
      .withUserSettings(link(User.class, "settings"))
      .withUserFeeds(link(User.class, "rss-feeds"))
      .withUserFeed(link(User.class, "rss-feed"))
      .withMessages(link(Messages.class))
      .withPasswordReset(link(PasswordRequest.class))
      .withPasswordResetConfirmation(link(PasswordRequest.class, "confirmation"))
      .withLogin(link(Login.class))
      .withLogout(link(Logout.class))
      .withNotes(link(Notes.class))
      .withNote(link(Note.class, "{id}").withTemplated(true))
      .withContacts(link(Contacts.class))
      .withContact(link(Contact.class, "{id}").withTemplated(true))
      .withFile(link(File.class, "{id}"))
      .withFiles(link(Files.class));

    ServiceDocument serviceDocument = new ServiceDocument()
      .withLinks(serviceDocumentLinks);

    return GetResponse.withHaljsonOK(serviceDocument);
  }
}
