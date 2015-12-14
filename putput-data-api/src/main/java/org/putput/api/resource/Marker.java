
package org.putput.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.putput.api.model.NewMarker;

@Path("marker")
public interface Marker {


    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Consumes("application/json")
    Marker.PostMarkerResponse postMarker(NewMarker entity)
        throws Exception
    ;

    /**
     * 
     * @param itemId
     *     
     * @param markerType
     *     
     */
    @DELETE
    Marker.DeleteMarkerResponse deleteMarker(
        @QueryParam("itemId")
        String itemId,
        @QueryParam("markerType")
        String markerType)
        throws Exception
    ;

    public class DeleteMarkerResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private DeleteMarkerResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Marker.DeleteMarkerResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Marker.DeleteMarkerResponse(responseBuilder.build());
        }

    }

    public class PostMarkerResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PostMarkerResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Marker.PostMarkerResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Marker.PostMarkerResponse(responseBuilder.build());
        }

    }

}
