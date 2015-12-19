package org.putput.stream;

import brainslug.flow.context.BrainslugContext;
import brainslug.flow.instance.FlowInstance;
import org.putput.api.model.MarkerInfo;
import org.putput.api.model.NewStreamItem;
import org.putput.common.UuidService;
import org.putput.users.UserEntity;
import org.putput.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;

import static brainslug.flow.execution.property.ExecutionProperties.newProperties;
import static java.util.Optional.ofNullable;

@Service
@Transactional
public class StreamItemService {

  @Autowired
  UuidService uuidService;

  @Autowired
  StreamItemRepository streamItemRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  BrainslugContext brainslugContext;

  public String newUserItem(NewStreamItem newItem, String userName) {
    FlowInstance newItemFlowInstance = brainslugContext.startFlow(NewItemFlow.id, newProperties()
        .with(NewItemFlow.newItem, newItem, true)
        .with(NewItemFlow.author, userName, true));

    return newItemFlowInstance
        .getProperties()
        .value(NewItemFlow.savedItemId);
  }


  public StreamItemEntity newItemEntity(String userName,
                                        String content,
                                        Optional<String> title,
                                        Optional<String> sourceName,
                                        Optional<String> externalRef,
                                        Optional<Date> createdDate) {
    Optional<UserEntity> user = ofNullable(userRepository.findByUsername(userName));
    return streamItemRepository.save(new StreamItemEntity()
        .setId(uuidService.uuid())
        .setAuthor(user.orElseThrow(
            () -> new IllegalArgumentException("unable to create item for unknown user: " +userName)
        ))
        .setContent(content)
        .setTitle(title.orElse(null))
        .setSource(sourceName.map(StreamItemSource::valueOf).orElse(null))
        .setExternalRef(externalRef.orElse(null))
        .setCreated(createdDate.orElse(new Date()).getTime()));
  }

  public Page<StreamItemEntity> getFollowedByUserName(String username, Pageable pageable) {
      return streamItemRepository.findFollowedItems(username, pageable);
  }

  public Page<StreamItemEntity> getByUserName(String username, Optional<String> markerType, Pageable pageable) {
    if (!markerType.isPresent()) {
      return streamItemRepository.findUserItems(username, pageable);
    } else {
      return streamItemRepository.findByUserMarker(username, markerType.get(), pageable);
    }
  }

  public Optional<StreamItemEntity> getById(String itemId) {
    return ofNullable(streamItemRepository.findOne(itemId));
  }

  public MarkerInfo getMarkerInfo(String userName, String itemId) {
    MarkerInfo markerInfo = markerInfoWithDefaults();

    getById(itemId)
        .get()
        .getMarkers()
        .stream()
        .forEach(updateMarkerInfo(userName, markerInfo));

    return markerInfo;
  }

  private MarkerInfo markerInfoWithDefaults() {
    MarkerInfo markerInfo = new MarkerInfo();
    markerInfo.setDislikes(0.0);
    markerInfo.setLikes(0.0);
    markerInfo.setIsDislike(false);
    markerInfo.setIsLike(false);
    markerInfo.setIsFavorite(false);
    markerInfo.setIsReadLater(false);
    return markerInfo;
  }

  private Consumer<StreamItemMarkerEntity> updateMarkerInfo(String userName, MarkerInfo markerInfo) {
    return marker -> {
      if (marker.getMarkerType() == StreamItemMarkerType.DISLIKE) {
        updateDislike(userName, markerInfo, marker);
      } else if (marker.getMarkerType() == StreamItemMarkerType.LIKE) {
        updateLike(userName, markerInfo, marker);
      } else if (marker.getMarkerType() == StreamItemMarkerType.FAVORITE &&
          isUserMarker(userName, marker)) {
        markerInfo.setIsFavorite(true);
      } else if (marker.getMarkerType() == StreamItemMarkerType.LATER &&
          isUserMarker(userName, marker)) {
        markerInfo.setIsReadLater(true);
      }
    };
  }

  private void updateLike(String userName, MarkerInfo markerInfo, StreamItemMarkerEntity marker) {
    markerInfo.setLikes(markerInfo.getLikes() + 1);
    if (isUserMarker(userName, marker)) {
      markerInfo.setIsLike(true);
    }
  }

  private void updateDislike(String userName, MarkerInfo markerInfo, StreamItemMarkerEntity marker) {
    markerInfo.setDislikes(markerInfo.getDislikes() + 1);
    if (isUserMarker(userName, marker)) {
      markerInfo.setIsDislike(true);
    }
  }

  private boolean isUserMarker(String userName, StreamItemMarkerEntity marker) {
    return marker.getAuthor().getUsername().equals(userName);
  }

  public void deleteUserItem(String userName, String itemId) {
    Optional<StreamItemEntity> item = Optional.ofNullable(streamItemRepository.findOne(itemId));
    if (!item.isPresent()) {
      throw new IllegalArgumentException("cant delete item, not found: " + itemId);
    } else if (!item.get().getAuthor().getUsername().equals(userName)){
      throw new IllegalArgumentException("cant delete item, does not belong to user: " + userName);
    } else {
      streamItemRepository.delete(item.get());
    }
  }
}
