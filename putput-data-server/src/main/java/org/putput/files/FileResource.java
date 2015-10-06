package org.putput.files;

import org.putput.api.model.Tag;
import org.putput.api.model.TagLinks;
import org.putput.api.model.TagList;
import org.putput.api.resource.File;
import org.putput.common.web.BaseResource;
import org.putput.tags.TagEntity;
import org.putput.tags.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.putput.api.resource.File.GetFileByIdResponse.*;

@Component
public class FileResource extends BaseResource implements File, FileMapping {

    FileService fileService;
    TagService tagService;
    
    @Autowired
    public FileResource(FileService fileService, TagService tagService) {
        this.fileService = fileService;
        this.tagService = tagService;
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

    @Override
    public DeleteFileByIdTagByTagIdResponse deleteFileByIdTagByTagId(String tagId, String id) throws Exception {
        tagService.getTags(id)
                .stream()
                .filter(tag -> tag.getId().equals(tagId))
                .findFirst()
                .ifPresent(foundTag -> tagService.deleteTag(foundTag.getId()));

        return DeleteFileByIdTagByTagIdResponse.withOK();
    }

    @Override
    public GetFileByIdTagsResponse getFileByIdTags(String fileId, BigDecimal page) throws Exception {
        TagList tagList = new TagList().withTags(toTagDto(fileId, tagService.getTags(fileId)));
        return GetFileByIdTagsResponse.withHaljsonOK(tagList);
    }

    List<Tag> toTagDto(String fileId, List<TagEntity> tags) {
        return tags.stream().map(tagEntity -> {
            Tag tag = new Tag();
            tag.setId(tagEntity.getId());
            tag.setTaggableId(tagEntity.getTaggableId());
            tag.setName(tagEntity.getName());
            tag.setText(tagEntity.getText());

            tag.setLinks(new TagLinks().withSelf(link(File.class, fileId, "tag", tagEntity.getId())));

            return tag;
        }).collect(Collectors.toList());
    }

    @Override
    public PostFileByIdTagsResponse postFileByIdTags(String id, Tag tag) throws Exception {
        TagEntity newTag = tagService.createTag(id, tag.getName(), tag.getText());
        return PostFileByIdTagsResponse.withCreated(link(File.class, "tag", newTag.getId()).getHref());
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
