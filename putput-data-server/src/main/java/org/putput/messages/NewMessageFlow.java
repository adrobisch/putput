package org.putput.messages;

import brainslug.flow.builder.FlowBuilder;
import brainslug.flow.definition.Identifier;
import brainslug.flow.execution.node.task.SimpleTask;
import brainslug.flow.expression.Property;
import com.vdurmont.emoji.EmojiParser;
import org.pegdown.PegDownProcessor;
import org.putput.users.UserEntity;
import org.putput.users.UserRepository;
import org.putput.util.MailTemplates;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Component
public class NewMessageFlow extends FlowBuilder {

  public static final Identifier id = id("newMessageFlow");
  
  public static final Property<String> from = property(id("from_username"), String.class);
  public static final Property<String> to = property(id("to_username"), String.class);
  public static final Property<String> text = property(id("text"), String.class);
  public static final Property<String> type = property(id("type"), String.class);
  
  public static final Property<String> savedMessageId = property(id("saved_message_id"), String.class);

  @Override
  public void define() {
    flowId(id);

    start(task(id("save_message"), saveMessage()))
            .execute(task(id("send_notification"), sendNotification()));
  }

  SimpleTask saveMessage() {
    return (context) -> {
      String fromUser = context.property(from);
      String toUser = context.property(to);
      String messageText = context.property(text);
      String messageType = context.property(type);
              
      MessageEntity messageEntity = context
              .service(MessageService.class)
              .storeNewMessage(fromUser, toUser, messageText, of(messageType));
      
      context.setProperty(savedMessageId, messageEntity.getId());
    };
  }

  SimpleTask sendNotification() {
    return (context) -> {
      JavaMailSender mailSender = context.service(JavaMailSender.class);
      UserRepository userRepository = context.service(UserRepository.class);

      MessageEntity messageEntity = context
              .service(MessageService.class)
              .getById(context.property(savedMessageId))
              .get();
      
      Optional.ofNullable(userRepository.findByUsername(messageEntity.getTo()))
              .ifPresent(sendMail(mailSender, messageEntity));
    };
  }

  private Consumer<UserEntity> sendMail(JavaMailSender mailSender, final MessageEntity messageEntity) {
    return foundUser -> {
      if (ofNullable(foundUser.getEmail()).isPresent()) {
        MimeMessagePreparator preparator = mimeMessage -> {
          MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
          message.setFrom("info@putput.org");
          message.setSubject("New message from @" + messageEntity.getFrom());
          message.setTo(foundUser.getEmail());
          
          String textContent = new MailTemplates()
                  .create("message")
                  .data("text", messageEntity.getText())
                  .getText();

          String htmlContent = new MailTemplates()
                  .create("message.html")
                  .data("text", htmlContent(messageEntity))
                  .getText();

          message.setText(textContent, htmlContent);
        };
        
        mailSender.send(preparator);
      }
    };
  }

  private String htmlContent(MessageEntity messageEntity) {
    return new PegDownProcessor(5000).markdownToHtml(EmojiParser.parseToUnicode(messageEntity.getText()));
  }
}
