package org.putput.messages;

import org.putput.api.model.Message;
import org.putput.api.model.MessageList;
import org.putput.api.resource.Messages;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessagesResource extends BaseResource implements Messages {

  @Autowired
  MessageRepository messageRepository;

  @Override
  public GetMessagesResponse getMessages(BigDecimal page) throws Exception {
    List<Message> messages = messageRepository
        .findToOrFromUser(user().getUsername())
        .stream()
        .map(messageEntity -> new Message()
            .withFrom(messageEntity.getFrom())
            .withTo(messageEntity.getTo())
            .withState(messageEntity.getStatus())
            .withText(messageEntity.getText())
            .withType(messageEntity.getType())
            .withId(messageEntity.getId()))
        .collect(Collectors.toList());

    MessageList messageList = new MessageList().withMessages(messages);
    return GetMessagesResponse.withHaljsonOK(messageList);
  }
}
