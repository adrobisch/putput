package org.putput.messages;

import brainslug.flow.builder.FlowBuilder;
import brainslug.flow.definition.Identifier;
import brainslug.flow.expression.Property;
import brainslug.flow.node.task.Task;
import org.putput.users.UserRepository;
import org.putput.util.MailTemplates;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
            .execute(task(id("send_notification"), sendNotification()).async(true));
  }

  Task saveMessage() {
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

  Task sendNotification() {
    return (context) -> {
      MailSender mailSender = context.service(MailSender.class);
      UserRepository userRepository = context.service(UserRepository.class);

      MessageEntity messageEntity = context
              .service(MessageService.class)
              .getById(context.property(savedMessageId))
              .get();
      
      Optional.ofNullable(userRepository.findByUsername(messageEntity.getTo())).ifPresent(foundUser -> {
        if (ofNullable(foundUser.getEmail()).isPresent()) {
          SimpleMailMessage mentionMessage = new SimpleMailMessage();
          mentionMessage.setFrom("info@putput.org");
          mentionMessage.setSubject("New Message from @" + messageEntity.getFrom());
          mentionMessage.setTo(foundUser.getEmail());
          mentionMessage.setText(new MailTemplates()
                  .create("message")
                  .data("message", messageEntity)
                  .getText()
          );
          mailSender.send(mentionMessage);
        }
      });
    };
  }
}
