package org.putput.password;

import brainslug.flow.builder.FlowBuilder;
import brainslug.flow.definition.Identifier;
import brainslug.flow.expression.PredicateExpression;
import brainslug.flow.expression.Property;
import brainslug.flow.node.EventDefinition;
import brainslug.flow.node.TaskDefinition;

public class ForgotPasswordFlow extends FlowBuilder {

  public static Identifier id = id("forgot_password");

  public static Property<String> emailAddress = property(id("email"), String.class);
  public static Property<String> username = property(id("username"), String.class);
  public static Property<String> confirmationCode = property(id("code"), String.class);

  public static final Identifier confirmationReceivedId = id("confirmationReceivedId");
  public EventDefinition confirmationReceived = event(confirmationReceivedId);
  public static EventDefinition passwordRequest = event(id("password_request"));
  public EventDefinition invalidEmail = event(id("invalid_email"));

  TaskDefinition sendConfirmationCode = task(id("send_confirmation_mail"), new SendConfirmationTask());
  TaskDefinition sendNewPassword = task(id("send_new_password"), new SendNewPasswordTask());

  @Override
  public void define() {
    flowId(id);

    start(passwordRequest)
        .choice(id("user_exists"))
        .when(userExists())
          .execute(sendConfirmationCode)
          .waitFor(confirmationReceived)
          .execute(sendNewPassword)
        .otherwise()
        .end(invalidEmail);
  }

  private PredicateExpression<UserExistsPredicate> userExists() {
    return predicate(new UserExistsPredicate());
  }

}
