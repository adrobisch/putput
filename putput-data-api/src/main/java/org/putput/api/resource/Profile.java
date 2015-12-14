
package org.putput.api.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.putput.api.model.UserProfile;

@Path("profile")
public interface Profile {


    /**
     * 
     * @param userName
     *     
     */
    @GET
    @Path("{userName}")
    @Produces({
        "application/hal+json"
    })
    Profile.GetProfileByUserNameResponse getProfileByUserName(
        @PathParam("userName")
        String userName)
        throws Exception
    ;

    /**
     * 
     * @param userName
     *     
     */
    @POST
    @Path("{userName}/follow")
    Profile.PostProfileByUserNameFollowResponse postProfileByUserNameFollow(
        @PathParam("userName")
        String userName)
        throws Exception
    ;

    /**
     * 
     * @param userName
     *     
     */
    @DELETE
    @Path("{userName}/follow")
    Profile.DeleteProfileByUserNameFollowResponse deleteProfileByUserNameFollow(
        @PathParam("userName")
        String userName)
        throws Exception
    ;

    public class DeleteProfileByUserNameFollowResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private DeleteProfileByUserNameFollowResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Profile.DeleteProfileByUserNameFollowResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Profile.DeleteProfileByUserNameFollowResponse(responseBuilder.build());
        }

    }

    public class GetProfileByUserNameResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetProfileByUserNameResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Profile.GetProfileByUserNameResponse withHaljsonOK(UserProfile entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new Profile.GetProfileByUserNameResponse(responseBuilder.build());
        }

        /**
         * 
         */
        public static Profile.GetProfileByUserNameResponse withNotFound() {
            Response.ResponseBuilder responseBuilder = Response.status(404);
            return new Profile.GetProfileByUserNameResponse(responseBuilder.build());
        }

    }

    public class PostProfileByUserNameFollowResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PostProfileByUserNameFollowResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Profile.PostProfileByUserNameFollowResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Profile.PostProfileByUserNameFollowResponse(responseBuilder.build());
        }

    }

}
