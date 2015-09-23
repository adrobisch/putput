package org.putput.files;

import org.putput.api.resource.File;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;

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
        return fileService
                .getUserFile(id).map(file -> withHaljsonOK(toFileDto().apply(file)))
                .orElse(GetFileByIdResponse.withNotFound());
    }

    @Override
    public DeleteFileByIdResponse deleteFileById(String id) throws Exception {
        fileService.deleteUserFile(id);
        return DeleteFileByIdResponse.withOK();
    }

    @Override
    public GetFileByIdContentResponse getFileByIdContent(String id, Boolean disposition) throws Exception {
        GetFileByIdContentResponse foo = GetFileByIdContentResponse.withOctetstreamOK("foo", outputStream -> {
            outputStream.write("".getBytes());
        });
        foo.getHeaders().remove("Content-Type");
        foo.getHeaders().add("Content-Type", "text/plain");
        return foo;
    }
}
