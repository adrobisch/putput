package org.putput.files.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

import static java.lang.Integer.parseInt;
import static java.util.Optional.ofNullable;

@RestController
public class UploadController {

    @Autowired
    UploadService uploadService;

    @RequestMapping(value = "/api/upload", method = RequestMethod.POST)
    public ResponseEntity<?> postChunk(HttpServletRequest request) throws IOException {
        UploadRequest uploadRequest = uploadedFile(request);

        if (!uploadRequest.vaild()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid upload parameters.");
        }

        if (!uploadService.upload(uploadRequest, request.getInputStream())) {
            return ResponseEntity.ok("Uploading...");
        } else {
            return ResponseEntity.created(new File(uploadRequest.getPath()).toURI()).build();
        }
    }

    @RequestMapping(value = "/api/upload", method = RequestMethod.GET)
    public ResponseEntity<?> getUploadInfo(HttpServletRequest request) {
        UploadRequest uploadRequest = uploadedFile(request);

        if (uploadService.isChunkUploaded(uploadRequest)) {
            return ResponseEntity.ok("Uploaded.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    UploadRequest uploadedFile(HttpServletRequest request) {
        int resumableChunkNumber = parseInt(ofNullable(request.getParameter("flowChunkNumber")).orElse("-1"));
        int resumableChunkSize = parseInt(ofNullable(request.getParameter("flowChunkSize")).orElse("-1"));
        long resumableTotalSize = parseInt(ofNullable(request.getParameter("flowTotalSize")).orElse("-1"));

        String resumableIdentifier = request.getParameter("flowIdentifier");
        String resumableFilename = request.getParameter("flowFilename");
        String resumableRelativePath = request.getParameter("flowRelativePath");

        UploadRequest uploadRequest = new UploadRequest();

        uploadRequest.resumableChunkSize = resumableChunkSize;
        uploadRequest.resumableTotalSize = resumableTotalSize;
        uploadRequest.resumableIdentifier = resumableIdentifier;
        uploadRequest.resumableFilename = resumableFilename;
        uploadRequest.resumableRelativePath = resumableRelativePath;
        uploadRequest.resumableChunkNumber = resumableChunkNumber;

        return uploadRequest;
    }

}