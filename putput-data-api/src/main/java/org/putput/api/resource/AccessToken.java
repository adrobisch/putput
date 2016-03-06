
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

@Path("access-token")
public interface AccessToken {


    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Consumes("application/json")
    AccessToken.PostAccessTokenResponse postAccessToken(org.putput.api.model.AccessToken entity)
        throws Exception
    ;

    /**
     * 
     * @param id
     *     
     */
    @GET
    @Path("{id}")
    @Produces({
        "application/hal+json"
    })
    AccessToken.GetAccessTokenByIdResponse getAccessTokenById(
        @PathParam("id")
        String id)
        throws Exception
    ;

    /**
     * 
     * @param id
     *     
     * @param entity
     *     
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    AccessToken.PutAccessTokenByIdResponse putAccessTokenById(
        @PathParam("id")
        String id, org.putput.api.model.AccessToken entity)
        throws Exception
    ;

    /**
     * 
     * @param id
     *     
     */
    @DELETE
    @Path("{id}")
    AccessToken.DeleteAccessTokenByIdResponse deleteAccessTokenById(
        @PathParam("id")
        String id)
        throws Exception
    ;

    public class DeleteAccessTokenByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private DeleteAccessTokenByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static AccessToken.DeleteAccessTokenByIdResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new AccessToken.DeleteAccessTokenByIdResponse(responseBuilder.build());
        }

    }

    public class GetAccessTokenByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetAccessTokenByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static AccessToken.GetAccessTokenByIdResponse withHaljsonOK(org.putput.api.model.AccessToken entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new AccessToken.GetAccessTokenByIdResponse(responseBuilder.build());
        }

    }

    public class PostAccessTokenResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PostAccessTokenResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param location
         *     
         */
        public static AccessToken.PostAccessTokenResponse withCreated(String location) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Location", location);
            return new AccessToken.PostAccessTokenResponse(responseBuilder.build());
        }

    }

    public class PutAccessTokenByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PutAccessTokenByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static AccessToken.PutAccessTokenByIdResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new AccessToken.PutAccessTokenByIdResponse(responseBuilder.build());
        }

    }

}
