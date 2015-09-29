package org.putput.files;

import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class FileSystemStorage implements Storage<FileSystemReference> {
    public static final String baseDirKey = "base.dir";
    public static final String slash = "/";

    final File baseDir;
    final StorageConfiguration configuration;
    private final MimeTypes mimeTypes;

    public FileSystemStorage(StorageConfiguration configuration,
                             MimeTypes mimeTypes) {
        this.configuration = configuration;
        this.mimeTypes = mimeTypes;
        this.baseDir = createBaseDir(configuration);
    }

    protected File createBaseDir(StorageConfiguration configuration) {
        return new File(configuration.getStorageParameters().get(baseDirKey).getValue());
    }

    @Override
    public FileSystemReference store(String name, Optional<String> containerReference, InputStream input) {
        if (containerReference.isPresent()) {
            throw new UnsupportedOperationException("saving file with parent path not supported yet");
        }

        try {
            File file = new File(baseDir, name);
            StreamUtils.copy(input, new FileOutputStream(file));
            return fileRef(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FileSystemReference fileRef(File file) {
        String parentPathOrSlash = Optional.ofNullable(file.getParentFile())
                .flatMap(toParentPath()).orElse(slash);

        return new FileSystemReference()
                .setAbsolutePath(file.getAbsolutePath())
                .setMimeType(mimeTypes.getMimeType(file).orElse(null))
                .setSize(file.length())
                .setContainerReference(parentPathOrSlash)
                .setDirectory(file.isDirectory())
                .setName(file.getName());
    }

    @Override
    public StorageConfiguration getStorageConfiguration() {
        return configuration;
    }

    @Override
    public InputStream getContent(Optional<String> containerReference, String storageReference) {
        try {
            File folder = containerReference
                    .map(containerRef -> new File(baseDir, containerRef))
                    .orElse(baseDir);
            return new FileInputStream(new File(folder, storageReference));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FileSystemReference> list(Optional<FileSystemReference> containerReference) {
        if (!containerReference.isPresent()) {
            return folderList(baseDir);
        }

        return containerReference.get()
                .getContainerReference()
                .map(folder -> folderList(new File(new File(folder), containerReference.get().getName())))
                .orElse(folderList(new File(baseDir, containerReference.get().getName())));
    }

    protected List<FileSystemReference> folderList(File file) {
        File[] children = file.listFiles();

        if (children == null) {
            return Collections.emptyList();
        }

        return asList(children)
                .stream()
                .map(child -> fileRef(child)).collect(Collectors.toList());
    }

    private Function<File, Optional<String>> toParentPath() {
        return parent -> {
            Path pathAbsolute = Paths.get(parent.getAbsolutePath());
            Path pathBase = Paths.get(baseDir.getAbsolutePath());
            String relativePath = pathBase.relativize(pathAbsolute).toFile().getPath();
            if (relativePath.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(relativePath);
            }
        };
    }
}
