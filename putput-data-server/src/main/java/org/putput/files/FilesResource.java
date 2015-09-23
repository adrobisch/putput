package org.putput.files;

import org.putput.api.model.File;
import org.putput.api.model.FileList;
import org.putput.api.model.FileListLinks;
import org.putput.api.resource.Files;
import org.putput.common.persistence.BaseEntity;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FilesResource extends BaseResource implements Files {
    
    FileService fileService;

    @Autowired
    public FilesResource(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public GetFilesResponse getFiles(BigDecimal page) throws Exception {
        FileList fileList = new FileList()
                .withLinks(new FileListLinks().withSelf(link(Files.class)));

        List<File> userFiles = fileService.getUserFiles(user().getUsername())
                .stream()
                .map(toFileDto())
                .collect(Collectors.toList());
        
        fileList.setFiles(userFiles);
        
        return GetFilesResponse.withHaljsonOK(fileList);
    }

    private Function<? super PutPutFile, File> toFileDto() {
        return putputFile -> new File()
                .withId(putputFile.getId())
                .withName(putputFile.getName())
                .withIsDirectory(putputFile.isDirectory())
                .withStorageId(putputFile.getStorageConfiguration().getId())
                .withParentId(putputFile.getParent().map(BaseEntity::getId).orElse(null))
                .withMimeType(putputFile.getMimeType())
                .withCreated(putputFile.getCreated().doubleValue())
                .withUpdated(putputFile.getUpdated().map(Long::doubleValue).orElse(null))
                .withVersion(putputFile.getVersion().intValue());        
    }
}
