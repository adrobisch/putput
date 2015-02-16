package org.putput.users;

import org.putput.api.model.UserInfo;
import org.putput.api.model.UserSettings;
import org.putput.api.model.UserSettingsUpdate;
import org.putput.api.resource.User;
import org.putput.common.web.BaseResource;
import org.putput.password.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserResource extends BaseResource implements User {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordService passwordService;

  @Secured("USER")
  @Override
  public GetUserInfoResponse getUserInfo() throws Exception {
    return GetUserInfoResponse.withJsonOK(new UserInfo().withDisplayName(user().getUsername()));
  }

  @Override
  public GetUserSettingsResponse getUserSettings() throws Exception {
    UserEntity currentUser = userRepository.findByUsername(user().getUsername());
    return GetUserSettingsResponse.withJsonOK(new UserSettings().withEmail(currentUser.getEmail()));
  }

  @Override
  public PutUserSettingsResponse putUserSettings(UserSettingsUpdate settingsUpdate) throws Exception {
    UserEntity userToUpdate = userRepository.findByUsername(user().getUsername());

    updateEmail(settingsUpdate, userToUpdate);
    updatePassword(settingsUpdate, userToUpdate);

    return PutUserSettingsResponse.withNoContent();
  }

  private void updateEmail(UserSettingsUpdate settingsUpdate, UserEntity userToUpdate) {
    if (userToUpdate.getEmail() != null && !userToUpdate.getEmail().isEmpty()) {
      userToUpdate.setEmail(settingsUpdate.getEmail());
      userRepository.save(userToUpdate);
    }
  }

  private Optional<PasswordService.PasswordResetSuccess> updatePassword(UserSettingsUpdate settingsUpdate, UserEntity userToUpdate) {
    return passwordService.changePassword(userToUpdate.getUsername(), settingsUpdate.getPassword(), settingsUpdate.getPasswordRepeat());
  }

}
