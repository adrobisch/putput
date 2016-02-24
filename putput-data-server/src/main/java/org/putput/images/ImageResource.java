package org.putput.images;

import org.apache.commons.lang3.tuple.Pair;
import org.putput.api.resource.Image;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.ws.rs.core.StreamingOutput;
import java.io.InputStream;

@Component
public class ImageResource extends BaseResource implements Image {
    
    @Autowired
    ImageService imageService; 
    
    @Override
    public GetImageByIdResponse getImageById(String id) throws Exception {
        Pair<PutPutImage, InputStream> image = imageService.getImage(id);
        if (image.getKey().getType().equalsIgnoreCase("image/jpeg"))  {
            return GetImageByIdResponse.withJpegOK(stream(image));
        }
        throw new UnsupportedOperationException("unable to handle image type: " + image.getKey().getType());
    }

    StreamingOutput stream(Pair<PutPutImage, InputStream> image) {
        return outputStream -> StreamUtils.copy(image.getValue(), outputStream);
    }
}
