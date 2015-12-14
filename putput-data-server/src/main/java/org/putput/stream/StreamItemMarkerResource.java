package org.putput.stream;

import org.putput.api.model.NewMarker;
import org.putput.api.resource.Marker;
import org.putput.common.UuidService;
import org.putput.common.web.BaseResource;
import org.putput.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.stream.Stream;

@Controller
public class StreamItemMarkerResource extends BaseResource implements Marker {

  @Autowired
  StreamItemRepository streamItemRepository;

  @Autowired
  StreamItemMarkerRepository streamItemMarkerRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  UuidService uuidService;

  @Override
  public PostMarkerResponse postMarker(NewMarker newMarker) throws Exception {
    StreamItemMarkerEntity newMarkerEntity = new StreamItemMarkerEntity()
        .setId(uuidService.uuid())
        .setAuthor(userRepository.findByUsername(user().getUsername()))
        .setMarkerType(StreamItemMarkerType.fromValue(newMarker.getMarkerType()))
        .setItem(streamItemRepository.findOne(newMarker.getItemId()));

    streamItemMarkerRepository.save(newMarkerEntity);

    return PostMarkerResponse.withOK();
  }

  @Override
  public DeleteMarkerResponse deleteMarker(String itemId, String markerType) throws Exception {
    Optional<StreamItemEntity> itemEntity = Optional.ofNullable(streamItemRepository.findOne(itemId));

    Stream<StreamItemMarkerEntity> markers = itemEntity
        .map(StreamItemEntity::getMarkers)
        .map(java.util.Collection::stream)
        .orElse(Stream.empty());

    markers.filter(marker -> marker.getMarkerType().value().equals(markerType) &&
        marker.getAuthor().getUsername().equals(user().getUsername()))
        .findAny()
        .ifPresent(marker -> streamItemMarkerRepository.delete(marker.getId()));

    return DeleteMarkerResponse.withOK();
  }
}
