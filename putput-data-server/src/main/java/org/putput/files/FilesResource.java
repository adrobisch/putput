package org.putput.files;

import org.putput.api.model.File;
import org.putput.api.model.FileList;
import org.putput.api.model.FileListLinks;
import org.putput.api.resource.Files;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FilesResource extends BaseResource implements Files, FileMapping {
    
    FileService fileService;

    @Autowired
    public FilesResource(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public GetFilesResponse getFiles(String path, BigDecimal page) throws Exception {
        FileList fileList = new FileList()
                .withLinks(new FileListLinks().withSelf(link(Files.class)));

        List<File> userFiles = fileService.getUserFiles(user().getUsername(), Optional.ofNullable(path))
                .stream()
                .map(toFileDto())
                .collect(Collectors.toList());
        
        fileList.setFiles(userFiles);
        
        return GetFilesResponse.withHaljsonOK(fileList);
    }
}
