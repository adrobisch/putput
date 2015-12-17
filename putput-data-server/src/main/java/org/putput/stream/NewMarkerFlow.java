package org.putput.stream;

import brainslug.flow.builder.FlowBuilder;
import brainslug.flow.definition.Identifier;
import brainslug.flow.expression.Property;
import brainslug.flow.node.TaskDefinition;

public class NewMarkerFlow extends FlowBuilder {

  public static final Identifier id = id("createMarker");

  public static final Identifier saveMarker = id("saveMarker");
  public static final Property<String> itemId = property(id("itemId"), String.class);
  public static final Property<String> username = property(id("username"), String.class);
  public static final Property<String> markerType = property(id("markerType"), String.class);
  public static final Property<String> newMarkerId = property(id("newMarkerId"), String.class);

  @Override
  public void define() {
    flowId(id);

    start(saveMarkerTask);
  }

  TaskDefinition saveMarkerTask = task(saveMarker, context -> {
    StreamItemMarkerService markerService = context.service(StreamItemMarkerService.class);

    StreamItemMarkerEntity newMarker = markerService.saveMarker(context.property(username),
        context.property(itemId),
        context.property(markerType));

    context.setProperty(newMarkerId, newMarker.getId());
  });
}
