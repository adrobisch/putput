package org.putput.files;

import com.j256.simplemagic.ContentInfoUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Component
public class MimeTypes {

    public Optional<String> getMimeType(File file) {
        if (file.isDirectory()) {
            return Optional.empty();
        }

        try {
            return Optional.ofNullable(new ContentInfoUtil().findMatch(file))
                    .flatMap(contentMatch -> Optional.ofNullable(contentMatch.getMimeType()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
