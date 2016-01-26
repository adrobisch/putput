package org.putput.messages;

import org.putput.api.model.Message;
import org.putput.api.model.MessageList;
import org.putput.api.model.MessageListLinks;
import org.putput.api.resource.Messages;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.putput.messages.MessageResource.*;

@Component
public class MessagesResource extends BaseResource implements Messages {

  @Autowired
  MessageRepository messageRepository;

  @Override
  public GetMessagesResponse getMessages(BigDecimal page) throws Exception {
    Pageable pageable = pageable(page);

    Page<MessageEntity> messagesPage = messageRepository
        .findToOrFromUser(user().getUsername(), pageable);

    List<Message> messages = messagesPage
        .getContent()
        .stream()
        .map(toMessage())
        .collect(Collectors.toList());

    MessageListLinks messageListLinks = new MessageListLinks();
    messageListLinks.withSelf(link(Messages.class));

    if (messagesPage.hasNext()) {
      messageListLinks.withNextPage(link(Messages.class, nextPageParams(messagesPage)));
    }

    if (messagesPage.hasPrevious()) {
      messageListLinks.withPreviousPage(link(Messages.class, previousPageParams(messagesPage)));
    }

    MessageList messageList = new MessageList()
        .withMessages(messages)
        .withLinks(messageListLinks)
        .withCount(new Long(messagesPage.getTotalElements()).intValue())
        .withCurrentPage(pageable.getPageNumber())
        .withPageSize(pageable.getPageSize());

    return GetMessagesResponse.withHaljsonOK(messageList);
  }

}
