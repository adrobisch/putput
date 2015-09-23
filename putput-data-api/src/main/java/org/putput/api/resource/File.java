
package org.putput.api.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

@Path("file")
public interface File {


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
    File.GetFileByIdResponse getFileById(
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
    @Path("{id}")
    File.DeleteFileByIdResponse deleteFileById(
        @PathParam("id")
        String id)
        throws Exception
    ;

    /**
     * 
     * @param disposition
     *     A flag to signal that that a content disposition header should be added
     * @param id
     *     
     */
    @GET
    @Path("{id}/content")
    @Produces({
        "application/octet-stream"
    })
    File.GetFileByIdContentResponse getFileByIdContent(
        @PathParam("id")
        String id,
        @QueryParam("disposition")
        Boolean disposition)
        throws Exception
    ;

    public class DeleteFileByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private DeleteFileByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static File.DeleteFileByIdResponse withOK() {
            Response.ResponseBuilder responseBuilder = Response.status(200);
            return new File.DeleteFileByIdResponse(responseBuilder.build());
        }

    }

    public class GetFileByIdContentResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetFileByIdContentResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static File.GetFileByIdContentResponse withNotFound() {
            Response.ResponseBuilder responseBuilder = Response.status(404);
            return new File.GetFileByIdContentResponse(responseBuilder.build());
        }

        /**
         * 
         */
        public static File.GetFileByIdContentResponse withForbidden() {
            Response.ResponseBuilder responseBuilder = Response.status(403);
            return new File.GetFileByIdContentResponse(responseBuilder.build());
        }

        /**
         * 
         * @param contentDisposition
         *     
         * @param entity
         *     
         */
        public static File.GetFileByIdContentResponse withOctetstreamOK(String contentDisposition, StreamingOutput entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/octet-stream").header("Content-Disposition", contentDisposition);
            responseBuilder.entity(entity);
            return new File.GetFileByIdContentResponse(responseBuilder.build());
        }

    }

    public class GetFileByIdResponse
        extends org.putput.api.support.ResponseWrapper
    {


        private GetFileByIdResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static File.GetFileByIdResponse withNotFound() {
            Response.ResponseBuilder responseBuilder = Response.status(404);
            return new File.GetFileByIdResponse(responseBuilder.build());
        }

        /**
         * 
         * @param entity
         *     
         */
        public static File.GetFileByIdResponse withHaljsonOK(org.putput.api.model.File entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new File.GetFileByIdResponse(responseBuilder.build());
        }

    }

}
