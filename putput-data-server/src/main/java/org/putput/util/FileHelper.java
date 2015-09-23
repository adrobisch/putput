package org.putput.util;

import org.springframework.core.annotation.AnnotationUtils;

import javax.ws.rs.Path;
import java.io.File;

public class FileHelper {
    public static String getPathFromResource(Class<?> targetResource) {
        String pathValue = AnnotationUtils.findAnnotation(targetResource, Path.class).value();
        return pathValue != null && pathValue.equals("/") ? "" : pathValue;
    }

    public static File requireExistingDir(String path) {
        File directory = new File(path);

        if (directory.exists() && !directory.isDirectory()) {
            throw new IllegalArgumentException("dir not properly specified, should be directory: " + directory.getAbsolutePath());
        }

        if (!directory.exists()) {
            try {
                if (!directory.mkdirs()) {
                    throw new IllegalStateException("unable to mkdirs: " + directory);
                }
            } catch (Exception e) {
                throw new RuntimeException("directory did not exist and can't be created: " + directory, e);
            }
        }

        if (!directory.canWrite()) {
            throw new IllegalStateException("dir is not writable: " + directory);
        }
        return directory;
    }
}
