package org.putput.files.upload;

import org.putput.common.web.BaseResource;
import org.putput.files.FileService;
import org.putput.files.PutPutFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;

@Controller
@Path("upload")
public class UploadResource extends BaseResource {

    @Autowired
    UploadService uploadService;
    
    @Autowired
    FileService fileService;
    
    @Autowired
    UploadHandlers uploadHandlers;

    @POST
    public void postChunk(@Suspended final AsyncResponse asyncResponse) throws IOException {
        UploadRequest uploadRequest = uploadedFile(httpServletRequest);
        
        if (!uploadRequest.vaild()) {
            respondWith(asyncResponse, Response.status(HttpStatus.BAD_REQUEST.value()).entity("Invalid upload parameters.").build());
        }
        
        Optional<File> completelyUploadedFile = uploadService.upload(uploadRequest, httpServletRequest.getInputStream());
        
        if (!completelyUploadedFile.isPresent()) {
            respondWith(asyncResponse, Response.ok("Uploading...").build());
        } else {
            UploadHandler uploadHandler = uploadHandlers.getUploadHandlers().get(uploadRequest.getType());
            Object handledUpload = uploadHandler.handleUpload(user(), uploadRequest, completelyUploadedFile.get());
            
            if (handledUpload instanceof PutPutFile) {
                respondWith(asyncResponse, Response.created(toUri(link(PutPutFile.class, ((PutPutFile) handledUpload).getId()).getHref())).build());                
            } else {
                respondWith(asyncResponse, Response.created(toUri(link(UploadResource.class, uploadRequest.getResumableIdentifier()).getHref())).build());
            }
        }
    }

    private void respondWith(AsyncResponse asyncResponse, Response response) {
        new Thread() {
            @Override
            public void run() {
                asyncResponse.resume(response);
            }
        }.start();
    }

    private URI toUri(String href) {
        try {
            return new URL(href).toURI();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
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
        String type = ofNullable(request.getParameter("type")).orElse("file");
        int resumableChunkNumber = parseInt(ofNullable(request.getParameter("flowChunkNumber")).orElse("-1"));
        int totalChunks = parseInt(ofNullable(request.getParameter("flowTotalChunks")).orElse("-1"));
        int resumableChunkSize = parseInt(ofNullable(request.getParameter("flowChunkSize")).orElse("-1"));
        long resumableTotalSize = parseInt(ofNullable(request.getParameter("flowTotalSize")).orElse("-1"));

        String resumableIdentifier = request.getParameter("flowIdentifier");
        String resumableFilename = request.getParameter("flowFilename");
        String resumableRelativePath = request.getParameter("flowRelativePath");

        UploadRequest uploadRequest = new UploadRequest();

        uploadRequest.setType(type);
        uploadRequest.setResumableChunkSize(resumableChunkSize);
        uploadRequest.setResumableTotalSize(resumableTotalSize);
        uploadRequest.setResumableIdentifier(resumableIdentifier);
        uploadRequest.setResumableFilename(resumableFilename);
        uploadRequest.setResumableRelativePath(resumableRelativePath);
        uploadRequest.setResumableChunkNumber(resumableChunkNumber);
        uploadRequest.setContentLength(request.getContentLength());
        uploadRequest.setTotalChunks(totalChunks);
        uploadRequest.setUploadFolder(user());

        return uploadRequest;
    }

}