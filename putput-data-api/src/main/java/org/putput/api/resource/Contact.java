
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

@Path("contact")
public interface Contact {


    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Consumes("application/json")
    Contact.PostContactResponse postContact(org.putput.api.model.Contact entity)
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
    Contact.GetContactByIdResponse getContactById(
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
    Contact.PutContactByIdResponse putContactById(
        @PathParam("id")
        String id, org.putput.api.model.Contact entity)
        throws Exception
    ;

    /**
     * 
     * @param id
     *     
     */
    @DELETE
    @Path("{id}")
    Contact.DeleteContactByIdResponse deleteContactById(
        @PathParam("id")
        String id)
        throws Exception
    ;

    public class DeleteContactByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private DeleteContactByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Contact.DeleteContactByIdResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Contact.DeleteContactByIdResponse(responseBuilder.build());
        }

    }

    public class GetContactByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetContactByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Contact.GetContactByIdResponse withHaljsonOK(org.putput.api.model.Contact entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new Contact.GetContactByIdResponse(responseBuilder.build());
        }

    }

    public class PostContactResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PostContactResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param location
         *     
         */
        public static Contact.PostContactResponse withCreated(String location) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Location", location);
            return new Contact.PostContactResponse(responseBuilder.build());
        }

    }

    public class PutContactByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PutContactByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Contact.PutContactByIdResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Contact.PutContactByIdResponse(responseBuilder.build());
        }

    }

}
