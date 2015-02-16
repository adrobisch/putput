
package org.putput.api.resource;

import java.io.InputStream;
import java.math.BigDecimal;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.putput.api.model.ContactList;

@Path("contacts")
public interface Contacts {


    /**
     * 
     * @param page
     *     The page to retrieve
     */
    @GET
    @Produces({
        "application/hal+json"
    })
    Contacts.GetContactsResponse getContacts(
        @QueryParam("page")
        BigDecimal page)
        throws Exception
    ;

    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Path("import")
    @Consumes("application/octet-stream")
    Contacts.PostOctetstreamContactsImportResponse postOctetstreamContactsImport(InputStream entity)
        throws Exception
    ;

    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Path("import")
    @Consumes("text/vcard")
    Contacts.PostVcardContactsImportResponse postVcardContactsImport(String entity)
        throws Exception
    ;

    public class GetContactsResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetContactsResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Contacts.GetContactsResponse withHaljsonOK(ContactList entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new Contacts.GetContactsResponse(responseBuilder.build());
        }

    }

    public class PostOctetstreamContactsImportResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PostOctetstreamContactsImportResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Contacts.PostOctetstreamContactsImportResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Contacts.PostOctetstreamContactsImportResponse(responseBuilder.build());
        }

    }

    public class PostVcardContactsImportResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PostVcardContactsImportResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Contacts.PostVcardContactsImportResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Contacts.PostVcardContactsImportResponse(responseBuilder.build());
        }

    }

}
