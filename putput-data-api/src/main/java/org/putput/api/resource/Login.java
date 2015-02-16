
package org.putput.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.putput.api.support.ResponseWrapper;

@Path("login")
public interface Login {


    /**
     * 
     * @param password
     *     the password of the user
     * @param username
     *     the user trying to login
     */
    @POST
    @Consumes("application/x-www-form-urlencoded")
    Login.PostLoginResponse postLogin(
        @FormParam("username")
        String username,
        @FormParam("password")
        String password)
        throws Exception
    ;

    public class PostLoginResponse
        extends ResponseWrapper
    {


        private PostLoginResponse(Response delegate) {
            super(delegate);
        }

    }

}
