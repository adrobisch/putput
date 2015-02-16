
package org.putput.api.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.putput.api.model.UserInfo;
import org.putput.api.model.UserSettings;
import org.putput.api.model.UserSettingsUpdate;

@Path("user")
public interface User {


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
