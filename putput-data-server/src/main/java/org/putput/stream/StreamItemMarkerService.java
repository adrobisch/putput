package org.putput.stream;

import brainslug.flow.context.BrainslugContext;
import brainslug.flow.instance.FlowInstance;
import org.putput.common.UuidService;
import org.putput.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Stream;

import static brainslug.flow.execution.property.ExecutionProperties.newProperties;

@Service
@Transactional
public class StreamItemMarkerService {

  @Autowired
  StreamItemRepository streamItemRepository;

  @Autowired
  StreamItemMarkerRepository streamItemMarkerRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UuidService uuidService;

  @Autowired
  BrainslugContext brainslugContext;

  public String createNewMarker(String userName, String itemId, String markerType) {
    FlowInstance newItemFlowInstance = brainslugContext.startFlow(NewMarkerFlow.id, newProperties()
        .with(NewMarkerFlow.username, userName, true)
        .with(NewMarkerFlow.itemId, itemId, true)
        .with(NewMarkerFlow.markerType, markerType, true));

    return newItemFlowInstance
        .getProperties()
        .value(NewMarkerFlow.newMarkerId);
  }

    public StreamItemMarkerEntity saveMarker(String userName, String itemId, String markerType) {
    StreamItemMarkerEntity savedMarker = new StreamItemMarkerEntity()
        .setId(uuidService.uuid())
        .setAuthor(userRepository.findByUsername(userName))
        .setMarkerType(StreamItemMarkerType.fromValue(markerType))
        .setItem(streamItemRepository.findOne(itemId));

    return streamItemMarkerRepository.save(savedMarker);
  }

  public void deleteMarker(String username, String itemId, String markerType) {
    Optional<StreamItemEntity> itemEntity = Optional.ofNullable(streamItemRepository.findOne(itemId));

    Stream<StreamItemMarkerEntity> markers = itemEntity
        .map(StreamItemEntity::getMarkers)
        .map(java.util.Collection::stream)
        .orElse(Stream.empty());

    markers.filter(marker -> marker.getMarkerType().value().equals(markerType) &&
        marker.getAuthor().getUsername().equals(username))
        .findAny()
        .ifPresent(marker -> streamItemMarkerRepository.delete(marker.getId()));
  }

}
