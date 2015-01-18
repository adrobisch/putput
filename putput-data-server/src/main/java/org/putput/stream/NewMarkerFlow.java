package org.putput.stream;

import brainslug.flow.builder.FlowBuilder;
import brainslug.flow.definition.Identifier;
import brainslug.flow.expression.Property;
import brainslug.flow.node.TaskDefinition;
import org.putput.util.MailTemplates;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;

public class NewMarkerFlow extends FlowBuilder {

  public static final Identifier id = id("createMarker");

  public static final Property<String> itemId = property(id("itemId"), String.class);
  public static final Property<String> username = property(id("username"), String.class);
  public static final Property<String> markerType = property(id("markerType"), String.class);
  public static final Property<String> newMarkerId = property(id("newMarkerId"), String.class);

  @Override
  public void define() {
    flowId(id);

    start(saveMarkerTask).execute(sendNotifications);
  }

  TaskDefinition sendNotifications = task(id("send_notifications"), context -> {
      MailSender mailSender = context.service(MailSender.class);
      StreamItemRepository streamItemRepository = context.service(StreamItemRepository.class);

      String itemId = context.property(NewMarkerFlow.itemId);
      Optional.ofNullable(streamItemRepository
          .findOne(itemId))
          .ifPresent(sendMailToAuthor(mailSender,
              context.property(username),
              itemId,
              context.property(markerType)));
  });

  private Consumer<StreamItemEntity> sendMailToAuthor(MailSender mailSender,
                                                      String markerAuthor,
                                                      String itemId,
                                                      String markerType) {
    return foundItem -> {
      Optional<String> authorMail = ofNullable(foundItem.getAuthor().getEmail());
      if (validMailAddress(authorMail) &&
          !itemAuthorEqualsMarkerAuthor(markerAuthor, foundItem)) {
        SimpleMailMessage mentionMessage = new SimpleMailMessage();
        mentionMessage.setFrom("info@putput.org");
        mentionMessage.setSubject("You got a new '" + markerType + "' on a put!");
        mentionMessage.setTo(foundItem.getAuthor().getEmail());
        mentionMessage.setText(new MailTemplates()
            .create("markerNotification")
            .replace("markerType", markerType)
            .replace("markerAuthor", "@" + markerAuthor)
            .replace("link", "https://www.putput.org/#/item/" + itemId)
            .replace("content", contentPreview(foundItem))
            .getText()
        );
        mailSender.send(mentionMessage);
      }
    };
  }

  private boolean itemAuthorEqualsMarkerAuthor(String markerAuthor, StreamItemEntity foundItem) {
    return foundItem.getAuthor().getUsername().equals(markerAuthor);
  }

  private boolean validMailAddress(Optional<String> authorMail) {
    return authorMail.isPresent() && !authorMail.get().isEmpty();
  }

  String contentPreview(StreamItemEntity foundItem) {
    return foundItem.getContent().substring(0,
        Math.min(foundItem.getContent().length() - 1, 300)) + "...";
  }

  TaskDefinition saveMarkerTask = task(id("save_marker"), context -> {
    StreamItemMarkerService markerService = context.service(StreamItemMarkerService.class);

    StreamItemMarkerEntity newMarker = markerService.saveMarker(context.property(username),
        context.property(itemId),
        context.property(markerType));

    context.setProperty(newMarkerId, newMarker.getId());
  });
}
