
package org.putput.api.resource;

import java.math.BigDecimal;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.putput.api.model.UserList;
import org.putput.api.support.ResponseWrapper;

@Path("users")
public interface Users {


    /**
     * 
     * @param search
     *     
     * @param page
     *     The page to retrieve
     */
    @GET
    @Produces({
        "application/hal+json"
    })
    Users.GetUsersResponse getUsers(
        @QueryParam("search")
        String search,
        @QueryParam("page")
        BigDecimal page)
        throws Exception
    ;

    public class GetUsersResponse
        extends ResponseWrapper
    {


        private GetUsersResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Users.GetUsersResponse withHaljsonOK(UserList entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new Users.GetUsersResponse(responseBuilder.build());
        }

    }

}
