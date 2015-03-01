
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

@Path("note")
public interface Note {


    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Consumes("application/json")
    Note.PostNoteResponse postNote(org.putput.api.model.Note entity)
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
    Note.GetNoteByIdResponse getNoteById(
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
    Note.PutNoteByIdResponse putNoteById(
        @PathParam("id")
        String id, org.putput.api.model.Note entity)
        throws Exception
    ;

    /**
     * 
     * @param id
     *     
     */
    @DELETE
    @Path("{id}")
    Note.DeleteNoteByIdResponse deleteNoteById(
        @PathParam("id")
        String id)
        throws Exception
    ;

    public class DeleteNoteByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private DeleteNoteByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Note.DeleteNoteByIdResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Note.DeleteNoteByIdResponse(responseBuilder.build());
        }

    }

    public class GetNoteByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetNoteByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Note.GetNoteByIdResponse withHaljsonOK(org.putput.api.model.Note entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new Note.GetNoteByIdResponse(responseBuilder.build());
        }

    }

    public class PostNoteResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PostNoteResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param location
         *     
         */
        public static Note.PostNoteResponse withCreated(String location) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Location", location);
            return new Note.PostNoteResponse(responseBuilder.build());
        }

    }

    public class PutNoteByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PutNoteByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Note.PutNoteByIdResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Note.PutNoteByIdResponse(responseBuilder.build());
        }

    }

}
