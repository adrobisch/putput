
package org.putput.api.resource;

import java.math.BigDecimal;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.putput.api.model.NoteList;
import org.putput.api.support.ResponseWrapper;

@Path("notes")
public interface Notes {


    /**
     * 
     * @param page
     *     The page to retrieve
     */
    @GET
    @Produces({
        "application/hal+json"
    })
    Notes.GetNotesResponse getNotes(
        @QueryParam("page")
        BigDecimal page)
        throws Exception
    ;

    public class GetNotesResponse
        extends ResponseWrapper
    {


        private GetNotesResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Notes.GetNotesResponse withHaljsonOK(NoteList entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new Notes.GetNotesResponse(responseBuilder.build());
        }

    }

}
