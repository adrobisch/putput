
package org.putput.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.putput.api.model.NewContact;

@Path("contact")
public interface Contact {


    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Consumes("application/json")
    Contact.PostContactResponse postContact(NewContact entity)
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

}
