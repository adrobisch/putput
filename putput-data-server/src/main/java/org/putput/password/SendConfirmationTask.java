package org.putput.password;

import brainslug.flow.context.ExecutionContext;
import brainslug.flow.execution.node.task.SimpleTask;
import org.putput.util.MailTemplates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.UUID;

public class SendConfirmationTask implements SimpleTask {
    Logger log = LoggerFactory.getLogger(SendConfirmationTask.class);

    @Override
    public void execute(ExecutionContext executionContext) {
      MailSender mailSender = executionContext.service(MailSender.class);
      String confirmationCode = UUID.randomUUID().toString();
      executionContext.setProperty(ForgotPasswordFlow.confirmationCode, confirmationCode);

      SimpleMailMessage confirmationMail = new SimpleMailMessage();
      confirmationMail.setFrom("info@putput.org");
      confirmationMail.setSubject("Your Password Reset Request");
      confirmationMail.setTo(executionContext.property(ForgotPasswordFlow.emailAddress));
      confirmationMail.setText(new MailTemplates()
          .create("passwordRequest.txt")
          .replace("code", confirmationCode)
          .getText()
      );

      mailSender.send(confirmationMail);

      log.info("sent confirmation");
    }
}
