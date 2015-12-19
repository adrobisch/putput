package org.putput.stream;

import brainslug.flow.builder.FlowBuilder;
import brainslug.flow.context.ExecutionContext;
import brainslug.flow.definition.Identifier;
import brainslug.flow.expression.Property;
import brainslug.flow.node.TaskDefinition;
import brainslug.flow.node.task.Task;
import org.putput.api.model.NewStreamItem;
import org.putput.users.UserRepository;
import org.putput.util.MailTemplates;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.putput.util.LangUtil.as;

public class NewItemFlow extends FlowBuilder {

  public static final Identifier id = id("newItemFlow");
  public static final Property<NewStreamItem> newItem = property(id("new_item"), NewStreamItem.class);
  public static final Property<StreamItemEntity> savedItem = property(id("saved_item"), StreamItemEntity.class);
  public static final Property<String> savedItemId = property(id("saved_item_id"), String.class);

  public static final Identifier author = id("author");
  public static final Identifier itemId = id("item_id");
  public static final Identifier start = id("start");
  public static final Identifier end = id("item_processed");

  TaskDefinition saveItem = task(id("save_item"), saveItemTask());

  Task saveItemTask() {
    return (context) -> {
      StreamItemService streamItemService = context.service(StreamItemService.class);
      NewStreamItem newItem = context.property(NewItemFlow.newItem);

      String author = context.property(NewItemFlow.author, String.class);

      StreamItemEntity newItemEntity = streamItemService.newItemEntity(author,
          newItem.getContent(),
          ofNullable(newItem.getTitle()),
          ofNullable(newItem.getSource()),
          empty(),
          empty());

      context.setProperty(savedItem, newItemEntity, true);
      context.setProperty(savedItemId, newItemEntity.getId());
    };
  }

  @Override
  public void define() {
    flowId(id);

    start(saveItem).execute(task(id("process_content"), processContent()));
  }

  Task processContent() {
    return taskContext -> {
      StreamItemEntity item = taskContext.property(savedItem);
      processFragments(item, taskContext);
    };
  }

  private void processFragments(StreamItemEntity item, ExecutionContext taskContext) {
    List<StreamItemContentParser.ContentFragment> fragments =
        StreamItemContentParser.parse(item.getContent());

    fragments.forEach(fragment -> {
      if (fragment instanceof StreamItemContentParser.Mention) {
        processMention(as(StreamItemContentParser.Mention.class, fragment), item, taskContext);
      }
    });
  }

  private void processMention(StreamItemContentParser.Mention mention, StreamItemEntity item, ExecutionContext taskContext) {
    MailSender mailSender = taskContext.service(MailSender.class);
    UserRepository userRepository = taskContext.service(UserRepository.class);

    Optional.ofNullable(userRepository.findByUsername(mention.getUsername())).ifPresent(foundUser -> {
      if (ofNullable(foundUser.getEmail()).isPresent()) {
        SimpleMailMessage mentionMessage = new SimpleMailMessage();
        mentionMessage.setFrom("info@putput.org");
        mentionMessage.setSubject("You have been mentioned by @" + item.getAuthor().getUsername());
        mentionMessage.setTo(foundUser.getEmail());
        mentionMessage.setText(new MailTemplates()
            .create("mention.txt")
            .replace("author", "@" + item.getAuthor().getUsername())
            .replace("link", "https://www.putput.org/#/item/" + item.getId())
            .replace("content", item.getContent())
            .getText()
        );
        mailSender.send(mentionMessage);
      }
    });

  }
}
