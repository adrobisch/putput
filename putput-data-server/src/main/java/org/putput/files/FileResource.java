package org.putput.files;

import org.putput.api.resource.File;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Function;

import static org.putput.api.resource.File.GetFileByIdResponse.*;

@Component
public class FileResource extends BaseResource implements File, FileMapping {

    FileService fileService;
    
    @Autowired
    public FileResource(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public GetFileByIdResponse getFileById(String id) throws Exception {
        // todo: check permission

        return fileService
                .getUserFile(id).map(file -> withHaljsonOK(toFileDto().apply(file)))
                .orElse(GetFileByIdResponse.withNotFound());
    }

    @Override
    public DeleteFileByIdResponse deleteFileById(String id) throws Exception {
        // todo: check permission

        fileService.deleteUserFile(id);
        return DeleteFileByIdResponse.withOK();
    }

    @Override
    public GetFileByIdContentResponse getFileByIdContent(String id, Boolean attachmentDisposition) throws Exception {
        // todo: check permission
        boolean dispositionFlag = attachmentDisposition != null && attachmentDisposition;
        return fileService
                .getUserFile(id).map(toContentResponse(dispositionFlag))
                .orElse(GetFileByIdContentResponse.withNotFound());
    }

    private Function<PutPutFile, GetFileByIdContentResponse> toContentResponse(boolean attachmentDisposition) throws IOException {
        return file -> {
            Optional<InputStream> content = fileService.getFileContent(file.getId());
            if (!content.isPresent()) {
                return GetFileByIdContentResponse.withNotFound();
            }

            GetFileByIdContentResponse contentResponse = GetFileByIdContentResponse.withOctetstreamOK(getContentDisposition(attachmentDisposition, file), outputStream -> {
                StreamUtils.copy(content.get(), outputStream);
            });
            
            contentResponse.getHeaders().remove("Content-Type");
            contentResponse.getHeaders().add("Content-Type", file.getMimeType());
            
            return contentResponse;
        };
    }

    private String getContentDisposition(boolean attachmentDisposition, PutPutFile file) {
        if (!attachmentDisposition) {
            return "inline; filename=" + file.getName();
        }
        return "attachment; filename=" + file.getName();
    }
}
