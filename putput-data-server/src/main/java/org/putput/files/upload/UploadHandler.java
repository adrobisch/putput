package org.putput.files.upload;

import java.io.File;

public interface UploadHandler<T> {
    String handledType();
    
    T handleUpload(String username, UploadRequest uploadRequest, File tempfile); 
}
