
package org.putput.api.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.putput.api.support.ResponseWrapper;

@Path("image")
public interface Image {


    /**
     * 
     * @param id
     *     
     */
    @GET
    @Path("{id}")
    @Produces({
        "image/png",
        "image/jpeg",
        "image/gif"
    })
    Image.GetImageByIdResponse getImageById(
        @PathParam("id")
        String id)
        throws Exception
    ;

    public class GetImageByIdResponse
        extends ResponseWrapper
    {


        private GetImageByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Image.GetImageByIdResponse withJpegOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "image/jpeg");
            responseBuilder.entity(entity);
            return new Image.GetImageByIdResponse(responseBuilder.build());
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Image.GetImageByIdResponse withPngOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "image/png");
            responseBuilder.entity(entity);
            return new Image.GetImageByIdResponse(responseBuilder.build());
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Image.GetImageByIdResponse withGifOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "image/gif");
            responseBuilder.entity(entity);
            return new Image.GetImageByIdResponse(responseBuilder.build());
        }

    }

}
