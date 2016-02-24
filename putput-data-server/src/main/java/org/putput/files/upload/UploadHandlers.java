package org.putput.files.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadHandlers {
    
    @Autowired(required = false)
    Collection<UploadHandler> uploadHandlers;

    public Map<String, UploadHandler> getUploadHandlers() {
        Map<String, UploadHandler> uploadHandlerMap = new HashMap<>();
        uploadHandlers.stream().forEach(uploadHandler -> uploadHandlerMap.put(uploadHandler.handledType(), uploadHandler));
        return uploadHandlerMap;
    }
}
