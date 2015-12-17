package org.putput.stream;

import brainslug.flow.builder.FlowBuilder;
import brainslug.flow.definition.Identifier;
import brainslug.flow.node.TaskDefinition;
import brainslug.flow.node.task.Task;
import org.putput.api.model.NewStreamItem;

import static java.util.Optional.ofNullable;

public class NewItemFlow extends FlowBuilder {

  public static final Identifier id = id("newItemFlow");
  public static final Identifier newItem = id("new_item");
  public static final Identifier author = id("author");
  public static final Identifier itemId = id("item_id");
  public static final Identifier start = id("start");
  public static final Identifier end = id("item_processed");

  TaskDefinition saveItem = task(id("save_item"), saveItemTask());

  Task saveItemTask() {
    return (context) -> {
      StreamItemService streamItemService = context.service(StreamItemService.class);
      NewStreamItem newItem = context.property(NewItemFlow.newItem, NewStreamItem.class);

      String author = context.property(NewItemFlow.author, String.class);

      StreamItemEntity newItemEntity = streamItemService.newItemEntity(author,
          newItem.getContent(),
          ofNullable(newItem.getTitle()),
          ofNullable(newItem.getSource()));

      context.setProperty(itemId.stringValue(), newItemEntity.getId());
    };
  }

  @Override
  public void define() {
    flowId(id);

    start(start)
        .execute(saveItem)
        .end(end);
  }
}
