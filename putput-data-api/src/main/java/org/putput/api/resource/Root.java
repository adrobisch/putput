
package org.putput.api.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.putput.api.model.ServiceDocument;
import org.putput.api.support.ResponseWrapper;

@Path("/")
public interface Root {


    /**
     * 
     */
    @GET
    @Produces({
        "application/hal+json"
    })
    Root.GetResponse get()
        throws Exception
    ;

    public class GetResponse
        extends ResponseWrapper
    {


        private GetResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Root.GetResponse withHaljsonOK(ServiceDocument entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new Root.GetResponse(responseBuilder.build());
        }

    }

}
