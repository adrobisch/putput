package org.putput.stream;

import org.putput.api.model.NewMarker;
import org.putput.api.resource.Marker;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StreamItemMarkerResource extends BaseResource implements Marker {

  @Autowired
  StreamItemMarkerService streamItemMarkerService;

  @Override
  public PostMarkerResponse postMarker(NewMarker newMarker) throws Exception {
    String newMarkerId = streamItemMarkerService.createNewMarker(user(),
        newMarker.getItemId(),
        newMarker.getMarkerType());

    return PostMarkerResponse.withOK();
  }

  @Override
  public DeleteMarkerResponse deleteMarker(String itemId, String markerType) throws Exception {
    streamItemMarkerService.deleteMarker(user(), itemId, markerType);
    return DeleteMarkerResponse.withOK();
  }
}
