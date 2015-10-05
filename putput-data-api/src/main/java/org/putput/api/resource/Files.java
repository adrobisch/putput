
package org.putput.api.resource;

import java.math.BigDecimal;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.putput.api.model.FileList;
import org.putput.api.support.ResponseWrapper;

@Path("files")
public interface Files {


    /**
     * 
     * @param parent
     *     the parent folder to list files for
     * @param page
     *     The page to retrieve
     */
    @GET
    @Produces({
        "application/hal+json"
    })
    Files.GetFilesResponse getFiles(
        @QueryParam("parent")
        String parent,
        @QueryParam("page")
        BigDecimal page)
        throws Exception
    ;

    public class GetFilesResponse
        extends ResponseWrapper
    {


        private GetFilesResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Files.GetFilesResponse withHaljsonOK(FileList entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new Files.GetFilesResponse(responseBuilder.build());
        }

    }

}
