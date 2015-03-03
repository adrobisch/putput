package org.putput.password;

import org.putput.api.model.PasswordReset;
import org.putput.api.resource.PasswordRequest;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordRequestResource extends BaseResource implements PasswordRequest {

  @Autowired
  PasswordService passwordService;

  @Override
  public PostPasswordRequestResponse postPasswordRequest(PasswordReset passwordReset) throws Exception {
    Optional<PasswordService.PasswordResetSuccess> passwordResetSuccess = passwordService.resetPassword(passwordReset.getEmail());
    if (passwordResetSuccess.isPresent()) {
      return PostPasswordRequestResponse.withNoContent();
    } else {
      return PostPasswordRequestResponse.withBadRequest();
    }
  }
}
