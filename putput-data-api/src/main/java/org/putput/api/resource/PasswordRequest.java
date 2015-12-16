
package org.putput.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.putput.api.model.PasswordReset;
import org.putput.api.model.PasswordResetConfirmation;

@Path("password_request")
public interface PasswordRequest {


    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Consumes("application/json")
    PasswordRequest.PostPasswordRequestResponse postPasswordRequest(PasswordReset entity)
        throws Exception
    ;

    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Path("confirmation")
    @Consumes("application/json")
    PasswordRequest.PostPasswordRequestConfirmationResponse postPasswordRequestConfirmation(PasswordResetConfirmation entity)
        throws Exception
    ;

    public class PostPasswordRequestConfirmationResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PostPasswordRequestConfirmationResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static PasswordRequest.PostPasswordRequestConfirmationResponse withNoContent() {
            Response.ResponseBuilder responseBuilder = Response.status(204);
            return new PasswordRequest.PostPasswordRequestConfirmationResponse(responseBuilder.build());
        }

        /**
         * Bad Request, unable to reset password
         * 
         */
        public static PasswordRequest.PostPasswordRequestConfirmationResponse withBadRequest() {
            Response.ResponseBuilder responseBuilder = Response.status(400);
            return new PasswordRequest.PostPasswordRequestConfirmationResponse(responseBuilder.build());
        }

    }

    public class PostPasswordRequestResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PostPasswordRequestResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static PasswordRequest.PostPasswordRequestResponse withNoContent() {
            Response.ResponseBuilder responseBuilder = Response.status(204);
            return new PasswordRequest.PostPasswordRequestResponse(responseBuilder.build());
        }

        /**
         * Bad Request, unable to reset password
         * 
         */
        public static PasswordRequest.PostPasswordRequestResponse withBadRequest() {
            Response.ResponseBuilder responseBuilder = Response.status(400);
            return new PasswordRequest.PostPasswordRequestResponse(responseBuilder.build());
        }

    }

}
