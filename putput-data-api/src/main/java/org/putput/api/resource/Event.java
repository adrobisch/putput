
package org.putput.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("event")
public interface Event {


    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Consumes("application/json")
    Event.PostEventResponse postEvent(org.putput.api.model.Event entity)
        throws Exception
    ;

    /**
     * 
     * @param id
     *     
     */
    @GET
    @Path("{id}")
    @Produces({
        "application/hal+json"
    })
    Event.GetEventByIdResponse getEventById(
        @PathParam("id")
        String id)
        throws Exception
    ;

    /**
     * 
     * @param id
     *     
     * @param entity
     *     
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    Event.PutEventByIdResponse putEventById(
        @PathParam("id")
        String id, org.putput.api.model.Event entity)
        throws Exception
    ;

    /**
     * 
     * @param id
     *     
     */
    @DELETE
    @Path("{id}")
    Event.DeleteEventByIdResponse deleteEventById(
        @PathParam("id")
        String id)
        throws Exception
    ;

    public class DeleteEventByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private DeleteEventByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Event.DeleteEventByIdResponse withNotFound() {
            Response.ResponseBuilder responseBuilder = Response.status(404);
            return new Event.DeleteEventByIdResponse(responseBuilder.build());
        }

        /**
         * 
         */
        public static Event.DeleteEventByIdResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Event.DeleteEventByIdResponse(responseBuilder.build());
        }

        /**
         * 
         */
        public static Event.DeleteEventByIdResponse withForbidden() {
            Response.ResponseBuilder responseBuilder = Response.status(403);
            return new Event.DeleteEventByIdResponse(responseBuilder.build());
        }

    }

    public class GetEventByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetEventByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Event.GetEventByIdResponse withNotFound() {
            Response.ResponseBuilder responseBuilder = Response.status(404);
            return new Event.GetEventByIdResponse(responseBuilder.build());
        }

        /**
         * 
         */
        public static Event.GetEventByIdResponse withForbidden() {
            Response.ResponseBuilder responseBuilder = Response.status(403);
            return new Event.GetEventByIdResponse(responseBuilder.build());
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Event.GetEventByIdResponse withHaljsonOK(org.putput.api.model.Event entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new Event.GetEventByIdResponse(responseBuilder.build());
        }

    }

    public class PostEventResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PostEventResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param location
         *     
         */
        public static Event.PostEventResponse withCreated(String location) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Location", location);
            return new Event.PostEventResponse(responseBuilder.build());
        }

    }

    public class PutEventByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PutEventByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Event.PutEventByIdResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Event.PutEventByIdResponse(responseBuilder.build());
        }

    }

}
