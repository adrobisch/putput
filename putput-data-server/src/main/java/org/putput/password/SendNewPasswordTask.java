package org.putput.password;

import brainslug.flow.context.ExecutionContext;
import brainslug.flow.execution.node.task.SimpleTask;

class SendNewPasswordTask implements SimpleTask {
  @Override
  public void execute(ExecutionContext executionContext) {
    PasswordService passwordService = executionContext.service(PasswordService.class);
    passwordService.resetPassword(executionContext.property(ForgotPasswordFlow.emailAddress, String.class));
  }
}
