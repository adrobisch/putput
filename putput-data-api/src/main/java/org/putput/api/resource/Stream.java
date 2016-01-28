
package org.putput.api.resource;

import java.math.BigDecimal;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.putput.api.model.NewStreamItem;
import org.putput.api.model.StreamItemList;
import org.putput.api.model.StreamItemResource;

@Path("stream")
public interface Stream {


    /**
     * 
     * @param profile
     *     
     * @param page
     *     The page to retrieve
     * @param type
     *     the type of stream to receive
     */
    @GET
    @Produces({
        "application/hal+json"
    })
    Stream.GetStreamResponse getStream(
        @QueryParam("profile")
        String profile,
        @QueryParam("type")
        String type,
        @QueryParam("page")
        BigDecimal page)
        throws Exception
    ;

    /**
     * 
     * @param userName
     *     
     */
    @GET
    @Path("rss/{userName}")
    @Produces({
        "application/atom+xml"
    })
    Stream.GetStreamRssByUserNameResponse getStreamRssByUserName(
        @PathParam("userName")
        String userName)
        throws Exception
    ;

    /**
     * 
     * @param entity
     *     
     */
    @POST
    @Path("items")
    @Consumes("application/json")
    Stream.PostStreamItemsResponse postStreamItems(NewStreamItem entity)
        throws Exception
    ;

    /**
     * 
     * @param id
     *     
     */
    @GET
    @Path("item/{id}")
    @Produces({
        "application/hal+json"
    })
    Stream.GetStreamItemByIdResponse getStreamItemById(
        @PathParam("id")
        String id)
        throws Exception
    ;

    /**
     * 
     * @param id
     *     
     */
    @DELETE
    @Path("item/{id}")
    Stream.DeleteStreamItemByIdResponse deleteStreamItemById(
        @PathParam("id")
        String id)
        throws Exception
    ;

    public class DeleteStreamItemByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private DeleteStreamItemByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Stream.DeleteStreamItemByIdResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new Stream.DeleteStreamItemByIdResponse(responseBuilder.build());
        }

        /**
         * 
         */
        public static Stream.DeleteStreamItemByIdResponse withBadRequest() {
            Response.ResponseBuilder responseBuilder = Response.status(400);
            return new Stream.DeleteStreamItemByIdResponse(responseBuilder.build());
        }

    }

    public class GetStreamItemByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetStreamItemByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Stream.GetStreamItemByIdResponse withHaljsonOK(StreamItemResource entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new Stream.GetStreamItemByIdResponse(responseBuilder.build());
        }

        /**
         * 
         */
        public static Stream.GetStreamItemByIdResponse withNotFound() {
            Response.ResponseBuilder responseBuilder = Response.status(404);
            return new Stream.GetStreamItemByIdResponse(responseBuilder.build());
        }

    }

    public class GetStreamResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetStreamResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Stream.GetStreamResponse withHaljsonOK(StreamItemList entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new Stream.GetStreamResponse(responseBuilder.build());
        }

    }

    public class GetStreamRssByUserNameResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetStreamRssByUserNameResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Stream.GetStreamRssByUserNameResponse withAtomxmlOK(StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/atom+xml");
            responseBuilder.entity(entity);
            return new Stream.GetStreamRssByUserNameResponse(responseBuilder.build());
        }

    }

    public class PostStreamItemsResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private PostStreamItemsResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param location
         *     
         */
        public static Stream.PostStreamItemsResponse withCreated(String location) {
            Response.ResponseBuilder responseBuilder = Response.status(201).header("Location", location);
            return new Stream.PostStreamItemsResponse(responseBuilder.build());
        }

    }

}
