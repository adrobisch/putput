
package org.putput.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.putput.api.model.Empty;
import org.putput.api.support.ResponseWrapper;

@Path("logout")
public interface Logout {


    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Consumes("application/json")
    Logout.PostLogoutResponse postLogout(Empty entity)
        throws Exception
    ;

    public class PostLogoutResponse
        extends ResponseWrapper
    {


        private PostLogoutResponse(Response delegate) {
            super(delegate);
        }

    }

}
