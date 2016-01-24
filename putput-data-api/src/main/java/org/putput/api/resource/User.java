
package org.putput.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.putput.api.model.RssFeed;
import org.putput.api.model.RssFeedList;
import org.putput.api.model.UserInfo;
import org.putput.api.model.UserRegistration;
import org.putput.api.model.UserSettings;
import org.putput.api.model.UserSettingsUpdate;

@Path("user")
public interface User {


    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Consumes("application/json")
    User.PostUserResponse postUser(UserRegistration entity)
        throws Exception
    ;

    /**
     * 
     */
    @GET
    @Path("info")
    @Produces({
        "application/json"
    })
    User.GetUserInfoResponse getUserInfo()
        throws Exception
    ;

    /**
     * 
     */
    @GET
    @Path("settings")
    @Produces({
        "application/json"
    })
    User.GetUserSettingsResponse getUserSettings()
        throws Exception
    ;

    /**
     * 
     * @param entity
     *     
     */
    @PUT
    @Path("settings")
    @Consumes("application/json")
    User.PutUserSettingsResponse putUserSettings(UserSettingsUpdate entity)
        throws Exception
    ;

    /**
     * 
     */
    @GET
    @Path("rss-feeds")
    @Produces({
        "application/json"
    })
    User.GetUserRssFeedsResponse getUserRssFeeds()
        throws Exception
    ;

    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Path("rss-feeds")
    @Consumes("application/json")
    User.PostUserRssFeedsResponse postUserRssFeeds(RssFeed entity)
        throws Exception
    ;

    /**
     * 
     */
    @PUT
    @Path("rss-feed")
    User.PutUserRssFeedResponse putUserRssFeed()
        throws Exception
    ;

    /**
     * 
     * @param id
     *     
     */
    @DELETE
    @Path("rss-feed/{id}")
    User.DeleteUserRssFeedByIdResponse deleteUserRssFeedById(
        @PathParam("id")
        String id)
        throws Exception
    ;

    public class DeleteUserRssFeedByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private DeleteUserRssFeedByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static User.DeleteUserRssFeedByIdResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new User.DeleteUserRssFeedByIdResponse(responseBuilder.build());
        }

    }

    public class GetUserInfoResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetUserInfoResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static User.GetUserInfoResponse withJsonOK(UserInfo entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new User.GetUserInfoResponse(responseBuilder.build());
        }

    }

    public class GetUserRssFeedsResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetUserRssFeedsResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static User.GetUserRssFeedsResponse withJsonOK(RssFeedList entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new User.GetUserRssFeedsResponse(responseBuilder.build());
        }

    }

    public class GetUserSettingsResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetUserSettingsResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static User.GetUserSettingsResponse withJsonOK(UserSettings entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/json");
            responseBuilder.entity(entity);
            return new User.GetUserSettingsResponse(responseBuilder.build());
        }

    }

    public class PostUserResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PostUserResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static User.PostUserResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new User.PostUserResponse(responseBuilder.build());
        }

    }

    public class PostUserRssFeedsResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PostUserRssFeedsResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static User.PostUserRssFeedsResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new User.PostUserRssFeedsResponse(responseBuilder.build());
        }

    }

    public class PutUserRssFeedResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PutUserRssFeedResponse(Response delegate) {
            super(delegate);
        }

    }

    public class PutUserSettingsResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PutUserSettingsResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static User.PutUserSettingsResponse withNoContent() {
            Response.ResponseBuilder responseBuilder = Response.status(204);
            return new User.PutUserSettingsResponse(responseBuilder.build());
        }

    }

}
