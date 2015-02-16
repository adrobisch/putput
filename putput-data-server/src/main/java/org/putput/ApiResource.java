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
      .withUser(link(User.class, "info"))
      .withUserSettings(link(User.class, "settings"))
      .withPasswordReset(link(PasswordRequest.class))
      .withContacts(link(Contacts.class))
      .withLogin(link(Login.class))
      .withLogout(link(Logout.class))
      .withContact(link(Contact.class));

    ServiceDocument serviceDocument = new ServiceDocument()
      .withLinks(serviceDocumentLinks);

    return GetResponse.withHaljsonOK(serviceDocument);
  }
}
