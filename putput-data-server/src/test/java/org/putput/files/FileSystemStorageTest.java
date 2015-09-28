package org.putput.files;

import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class FileSystemStorageTest {
    MimeTypes mimeTypes = mock(MimeTypes.class);
    
    File folder = mockFile(null, true, "aFolder", "/the/base/test/sub");

    File child = mockFile(folder, false, "aFile", "/the/base/test/sub/aFile");
    File subFolder = mockFile(folder, true, "aSubFolder", "/the/base/test/sub/aSubFolder");
    
    File baseDirMock = mockFile(null, true, "baseDir", "/the/base");
    
    @Test
    public void shouldListChildren() {
        FileSystemStorage storage = storageWithMocks();
        given(folder.listFiles()).willReturn(new File[]{child, subFolder});
        
        given(mimeTypes.getMimeType(child)).willReturn(Optional.of("application/octet-stream"));
        given(mimeTypes.getMimeType(subFolder)).willReturn(Optional.<String>empty());

        // when:
        List<StorageReference> list = storage.folderList(folder);
        
        // then:
        assertThat(list)
                .hasSize(2)
                .contains(new StorageReference()
                        .setName("aFile")
                        .setContainerReference("test/sub")
                        .setDirectory(false)
                        .setMimeType("application/octet-stream")
                        .setSize(0))
                .contains(new StorageReference()
                        .setName("aSubFolder")
                        .setContainerReference("test/sub")
                        .setMimeType(null)
                        .setDirectory(true)
                        .setSize(0));
    }

    FileSystemStorage storageWithMocks() {
        StorageConfiguration storageConfiguration = new StorageConfiguration();

        return new FileSystemStorage(storageConfiguration, mimeTypes) {
            @Override
            protected File createBaseDir(StorageConfiguration configuration) {
                return baseDirMock;   
            }
        };
    }

    private File mockFile(File parentFile, boolean isDirectory, String fileName, String absolutePath) {
        File fileMock = mock(File.class);
        given(fileMock.getName()).willReturn(fileName);
        given(fileMock.getParentFile()).willReturn(parentFile);
        given(fileMock.getAbsolutePath()).willReturn(absolutePath);
        given(fileMock.isDirectory()).willReturn(isDirectory);
        return fileMock;
    }
}