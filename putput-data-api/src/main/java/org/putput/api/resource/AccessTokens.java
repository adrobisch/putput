
package org.putput.api.resource;

import java.math.BigDecimal;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.putput.api.model.AccessTokenList;
import org.putput.api.support.ResponseWrapper;

@Path("access-tokens")
public interface AccessTokens {


    /**
     * 
     * @param page
     *     The page to retrieve
     */
    @GET
    @Produces({
        "application/hal+json"
    })
    AccessTokens.GetAccessTokensResponse getAccessTokens(
        @QueryParam("page")
        BigDecimal page)
        throws Exception
    ;

    public class GetAccessTokensResponse
        extends ResponseWrapper
    {


        private GetAccessTokensResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static AccessTokens.GetAccessTokensResponse withHaljsonOK(AccessTokenList entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new AccessTokens.GetAccessTokensResponse(responseBuilder.build());
        }

    }

}
