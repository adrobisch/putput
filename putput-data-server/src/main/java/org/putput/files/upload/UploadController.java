package org.putput.files.upload;

import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;

@Controller
@Path("/upload")
public class UploadController extends BaseResource {

    @Autowired
    UploadService uploadService;

    @POST
    public Response postChunk() throws IOException {
        UploadRequest uploadRequest = uploadedFile(httpServletRequest);

        if (!uploadRequest.vaild()) {
            return Response.status(HttpStatus.BAD_REQUEST.value()).entity("Invalid upload parameters.").build();
        }

        Optional<File> completelyUploadedFile = uploadService.upload(uploadRequest, httpServletRequest.getInputStream());
        
        if (!completelyUploadedFile.isPresent()) {
            return Response.ok("Uploading...").build();
        } else {
            return Response.created(completelyUploadedFile.get().toURI()).build();
        }
    }

    @GET
    public Response getUploadInfo() {
        UploadRequest uploadRequest = uploadedFile(httpServletRequest);

        if (uploadService.isChunkUploaded(uploadRequest)) {
            return Response.ok("Uploaded.").build();
        } else {
            return Response.status(404).build();
        }
    }

    UploadRequest uploadedFile(HttpServletRequest request) {
        int resumableChunkNumber = parseInt(ofNullable(request.getParameter("flowChunkNumber")).orElse("-1"));
        int totalChunks = parseInt(ofNullable(request.getParameter("flowTotalChunks")).orElse("-1"));
        int resumableChunkSize = parseInt(ofNullable(request.getParameter("flowChunkSize")).orElse("-1"));
        long resumableTotalSize = parseInt(ofNullable(request.getParameter("flowTotalSize")).orElse("-1"));

        String resumableIdentifier = request.getParameter("flowIdentifier");
        String resumableFilename = request.getParameter("flowFilename");
        String resumableRelativePath = request.getParameter("flowRelativePath");

        UploadRequest uploadRequest = new UploadRequest();

        uploadRequest.setResumableChunkSize(resumableChunkSize);
        uploadRequest.setResumableTotalSize(resumableTotalSize);
        uploadRequest.setResumableIdentifier(resumableIdentifier);
        uploadRequest.setResumableFilename(resumableFilename);
        uploadRequest.setResumableRelativePath(resumableRelativePath);
        uploadRequest.setResumableChunkNumber(resumableChunkNumber);
        uploadRequest.setContentLength(request.getContentLength());
        uploadRequest.setTotalChunks(totalChunks);

        return uploadRequest;
    }

}