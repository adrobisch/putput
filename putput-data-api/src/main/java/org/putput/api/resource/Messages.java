
package org.putput.api.resource;

import java.math.BigDecimal;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.putput.api.model.MessageList;
import org.putput.api.support.ResponseWrapper;

@Path("messages")
public interface Messages {


    /**
     * 
     * @param with
     *     show only messages from or to a specific user
     * @param page
     *     The page to retrieve
     */
    @GET
    @Produces({
        "application/hal+json"
    })
    Messages.GetMessagesResponse getMessages(
        @QueryParam("with")
        String with,
        @QueryParam("page")
        BigDecimal page)
        throws Exception
    ;

    public class GetMessagesResponse
        extends ResponseWrapper
    {


        private GetMessagesResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Messages.GetMessagesResponse withHaljsonOK(MessageList entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new Messages.GetMessagesResponse(responseBuilder.build());
        }

    }

}
