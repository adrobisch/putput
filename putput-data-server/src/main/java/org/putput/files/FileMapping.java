package org.putput.files;

import org.putput.api.model.File;
import org.putput.api.model.FileLinks;
import org.putput.common.persistence.BaseEntity;
import org.putput.common.web.HalSupport;

import java.util.Optional;
import java.util.function.Function;

public interface FileMapping extends HalSupport {
    default Function<? super PutPutFile, File> toFileDto() {
        return putputFile -> new File()
                .withId(putputFile.getId())
                .withName(putputFile.getName())
                .withIsDirectory(putputFile.isDirectory())
                .withStorageId(putputFile.getStorageConfiguration().getId())
                .withParentId(putputFile.getParent().map(BaseEntity::getId).orElse(null))
                .withMimeType(putputFile.getMimeType())
                .withCreated(putputFile.getCreated().doubleValue())
                .withUpdated(Optional.ofNullable(putputFile.getUpdated()).map(Long::doubleValue).orElse(null))
                .withVersion(putputFile.getVersion().intValue())
                .withLinks(new FileLinks().withSelf(link(org.putput.api.resource.File.class, putputFile.getId())));
    }
}
