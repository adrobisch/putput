package org.putput.password;

import brainslug.flow.context.ExecutionContext;
import brainslug.flow.execution.expression.ContextPredicate;
import org.putput.users.UserEntity;
import org.putput.users.UserRepository;

import java.util.Optional;

public class UserExistsPredicate implements ContextPredicate {
  @Override
  public boolean isFulfilled(ExecutionContext context) {
    UserRepository userService = context.service(UserRepository.class);
    Optional<UserEntity> user = userService.findByEmail(context.property(ForgotPasswordFlow.emailAddress));
    if (user.isPresent()) {
      context.setProperty(ForgotPasswordFlow.username.getValue().stringValue(), user.get().getUsername());
      return true;
    }
    return false;
  }
}
