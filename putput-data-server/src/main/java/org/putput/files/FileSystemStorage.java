package org.putput.files;

import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class FileSystemStorage implements Storage {
    public static final String baseDirKey = "base.dir";

    final File baseDir;
    final StorageConfiguration configuration;
    private final MimeTypes mimeTypes;

    public FileSystemStorage(StorageConfiguration configuration,
                             MimeTypes mimeTypes) {
        this.configuration = configuration;
        this.mimeTypes = mimeTypes;
        this.baseDir = new File(configuration.getStorageParameters().get(baseDirKey).getValue());
    }

    @Override
    public StorageReference store(String name, Optional<String> containerReference, InputStream input) {
        if (containerReference.isPresent()) {
            throw new UnsupportedOperationException("saving file with parent path not supported yet");
        }

        try {
            StreamUtils.copy(input, new FileOutputStream(new File(baseDir, name)));
            return new StorageReference()
                    .setContainerReference(containerReference.orElse(null))
                    .setName(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StorageConfiguration getStorageConfiguration() {
        return configuration;
    }

    @Override
    public InputStream getContent(Optional<String> containerReference, String storageReference) {
        try {
            File folder = containerReference.map(containerRef -> new File(containerRef)).orElse(baseDir);
            return new FileInputStream(new File(folder, storageReference));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StorageReference> list(Optional<StorageReference> containerReference) {
        if (!containerReference.isPresent()) {
            return folderList(baseDir);
        }

        return containerReference.get()
                .getContainerReference()
                .map(folder -> folderList(new File(new File(folder), containerReference.get().getName())))
                .orElse(folderList(new File(baseDir, containerReference.get().getName())));
    }

    private List<StorageReference> folderList(File file) {
        File[] children = file.listFiles();

        if (children == null) {
            return Collections.emptyList();
        }

        return asList(children)
                .stream()
                .map(child -> {
                    String parentPathOrNull = Optional.ofNullable(child.getParentFile())
                            .map(parent -> parent.getAbsoluteFile().getAbsolutePath()).orElse(null);

                    return new StorageReference()
                            .setMimeType(mimeTypes.getMimeType(child).orElse("application/octet-stream"))
                            .setSize(file.length())
                            .setContainerReference(parentPathOrNull)
                            .setDirectory(child.isDirectory())
                            .setName(child.getName());
                })
                .collect(Collectors.toList());
    }
}
