package org.putput.messages;

import org.putput.api.resource.Message;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class MessageResource extends BaseResource implements Message {
    
    @Autowired
    MessageService messageService;
    
    @Override
    public PostMessageResponse postMessage(org.putput.api.model.Message message) throws Exception {
        String savedMessageId = messageService.newMessage(user().getUsername(), message.getTo(), message.getText(), Optional.ofNullable(message.getType()));
        return PostMessageResponse.withCreated(link(Message.class, savedMessageId).getHref());
    }

    @Override
    public GetMessageByIdResponse getMessageById(String id) throws Exception {
        Optional<org.putput.api.model.Message> message = messageService.getById(id).map(toMessage());
        if (!message.isPresent()) {
            return GetMessageByIdResponse.withNotFound();
        } else {
            return GetMessageByIdResponse.withHaljsonOK(message.get());    
        }
    }

    @Override
    public PutMessageByIdResponse putMessageById(String id, org.putput.api.model.Message message) throws Exception {
        throw new UnsupportedOperationException("not supported yet");
    }

    @Override
    public DeleteMessageByIdResponse deleteMessageById(String id) throws Exception {
        messageService.delete(id);
        return DeleteMessageByIdResponse.withOK();
    }

    public static Function<MessageEntity, org.putput.api.model.Message> toMessage() {
        return messageEntity -> new org.putput.api.model.Message()
                .withId(messageEntity.getId())
                .withCreated(messageEntity.getCreated().doubleValue())
                .withFrom(messageEntity.getFrom())
                .withTo(messageEntity.getTo())
                .withState(messageEntity.getStatus())
                .withText(messageEntity.getText())
                .withType(messageEntity.getType());
    }
}
