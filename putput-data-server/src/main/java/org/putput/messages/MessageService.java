package org.putput.messages;

import brainslug.flow.context.BrainslugContext;
import brainslug.flow.instance.FlowInstance;
import org.putput.common.UuidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static brainslug.flow.execution.property.ExecutionProperties.newProperties;
import static java.util.Optional.ofNullable;

@Service
@Transactional
public class MessageService {

    @Autowired
    MessageRepository messageRepository;
    
    @Autowired
    UuidService uuidService;
    
    @Autowired
    BrainslugContext brainslugContext;
    
    public String newMessage(String from, String to, String text, Optional<String> type) {
        FlowInstance instance = brainslugContext.startFlow(NewMessageFlow.id, newProperties()
                .with(NewMessageFlow.text, text)
                .with(NewMessageFlow.type, type.orElse("chat"))
                .with(NewMessageFlow.from, from)
                .with(NewMessageFlow.to, to));
        
        return instance.getProperties().value(NewMessageFlow.savedMessageId);
    }
    
    public MessageEntity storeNewMessage(String from, String to, String text, Optional<String> type) {
        MessageEntity newMessage = new MessageEntity()
                .setId(uuidService.uuid())
                .setFrom(from)
                .setTo(to)
                .setText(text)
                .setStatus("new")
                .setType(type.orElse("chat"));
        
        return messageRepository.save(newMessage);       
    }
    
    public Optional<MessageEntity> getById(String id) {
        return ofNullable(messageRepository.findOne(id));
    }
    
    public void delete(String id) {
        messageRepository.delete(id);
    }
    
}
