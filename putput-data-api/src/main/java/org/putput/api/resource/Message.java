
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

@Path("message")
public interface Message {


    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Consumes("application/json")
    Message.PostMessageResponse postMessage(org.putput.api.model.Message entity)
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
    Message.GetMessageByIdResponse getMessageById(
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
    Message.PutMessageByIdResponse putMessageById(
        @PathParam("id")
        String id, org.putput.api.model.Message entity)
        throws Exception
    ;

    /**
     * 
     * @param id
     *     
     */
    @DELETE
    @Path("{id}")
    Message.DeleteMessageByIdResponse deleteMessageById(
        @PathParam("id")
        String id)
        throws Exception
    ;

    public class DeleteMessageByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private DeleteMessageByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Message.DeleteMessageByIdResponse withNotFound() {
            Response.ResponseBuilder responseBuilder = Response.status(404);
            return new Message.DeleteMessageByIdResponse(responseBuilder.build());
        }

        /**
         * 
         */
        public static Message.DeleteMessageByIdResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Message.DeleteMessageByIdResponse(responseBuilder.build());
        }

        /**
         * 
         */
        public static Message.DeleteMessageByIdResponse withForbidden() {
            Response.ResponseBuilder responseBuilder = Response.status(403);
            return new Message.DeleteMessageByIdResponse(responseBuilder.build());
        }

    }

    public class GetMessageByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetMessageByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Message.GetMessageByIdResponse withNotFound() {
            Response.ResponseBuilder responseBuilder = Response.status(404);
            return new Message.GetMessageByIdResponse(responseBuilder.build());
        }

        /**
         * 
         */
        public static Message.GetMessageByIdResponse withForbidden() {
            Response.ResponseBuilder responseBuilder = Response.status(403);
            return new Message.GetMessageByIdResponse(responseBuilder.build());
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Message.GetMessageByIdResponse withHaljsonOK(org.putput.api.model.Message entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new Message.GetMessageByIdResponse(responseBuilder.build());
        }

    }

    public class PostMessageResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PostMessageResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param location
         *     
         */
        public static Message.PostMessageResponse withCreated(String location) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Location", location);
            return new Message.PostMessageResponse(responseBuilder.build());
        }

    }

    public class PutMessageByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PutMessageByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Message.PutMessageByIdResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Message.PutMessageByIdResponse(responseBuilder.build());
        }

    }

}
