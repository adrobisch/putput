package org.putput.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.putput.ApiResource;
import org.putput.contacts.ContactResource;
import org.putput.contacts.ContactsResource;
import org.putput.notes.NoteResource;
import org.putput.password.PasswordRequestResource;
import org.putput.users.UserResource;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

  public JerseyConfig() {
    register(ApiResource.class);

    register(ContactsResource.class);
    register(ContactResource.class);
    register(NoteResource.class);
    register(NoteResource.class);
    register(UserResource.class);
    register(PasswordRequestResource.class);
  }

}