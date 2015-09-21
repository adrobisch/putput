package org.putput.files.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

@Repository
public class UploadRepository {

  public static final String TEMP_SUFFIX = ".temp";

  Environment environment;

  @Autowired
  public UploadRepository(Environment environment) {
    this.environment = environment;
    requireUploadDir();
  }

  void requireUploadDir() {
    File uploadDir = new File(getUploadBaseDir());
    if (!uploadDir.isDirectory()) {
      throw new IllegalArgumentException("upload dir not properly specified, should be directory: " + uploadDir.getAbsolutePath());
    }

    if (!uploadDir.exists()) {
      try {
        if (!uploadDir.mkdirs()) {
          throw new IllegalStateException("unable to mkdirs: " + uploadDir);
        }
      } catch (Exception e) {
        throw new RuntimeException("upload directory did not exits and can't be created: " + uploadDir, e);
      }
    }
    
    if (!uploadDir.canWrite()) {
      throw new IllegalStateException("upload dir is not writable: " + uploadDir);      
    }
  }

  private HashMap<String, HashSet<ChunkNumber>> uploadedChunksById = new HashMap<String, HashSet<ChunkNumber>>();

  public void markUploaded(String uploadId, int chunkNumber) {
    getOrCreateUpload(uploadId).add(new ChunkNumber(chunkNumber));
  }

  private HashSet<ChunkNumber> getOrCreateUpload(String uploadId) {
    if(!uploadedChunksById.containsKey(uploadId)) {
      uploadedChunksById.put(uploadId, new HashSet<>());
    }
    return uploadedChunksById.get(uploadId);
  }

  public boolean chunkExists(String uploadIdentifier, int chunkNumber) {
    return getOrCreateUpload(uploadIdentifier).contains(new ChunkNumber(chunkNumber));
  }

  public synchronized void remove(String uploadId) {
    if (uploadedChunksById.containsKey(uploadId)) {
      uploadedChunksById.remove(uploadId);
    }
  }

  public void writeChunk(Optional<String> path, String uploadedFileName, InputStream dataStream, long streamSize, int resumableChunkSize, int resumableChunkNumber) {
    try {
      RandomAccessFile raf = getRandomAccessFileAtPosition(path.orElse("") + uploadedFileName, resumableChunkSize, resumableChunkNumber);
      writeBytes(streamSize, raf, dataStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public File complete(String uploadIdentifier, String fileName) {
    remove(uploadIdentifier);
    return removeTempSuffix(getTempFilePath(fileName));
  }

  File removeTempSuffix(String tempFilePath) {
    File tempFile = new File(tempFilePath);
    String finalPath = tempFile.getAbsolutePath().substring(0, tempFile.getAbsolutePath().length() - TEMP_SUFFIX.length());
    File finalFile = new File(finalPath);
    if (!tempFile.renameTo(finalFile)) {
      throw new IllegalStateException("unable to remove temp file to final file: " + tempFilePath);
    }
    return finalFile;
  }

  private RandomAccessFile getRandomAccessFileAtPosition(String fileName, int resumableChunkSize, int resumableChunkNumber) throws IOException {
    RandomAccessFile raf = new RandomAccessFile(getTempFilePath(fileName), "rw");
    raf.seek((resumableChunkNumber - 1) * resumableChunkSize);
    return raf;
  }

  private String getTempFilePath(String fileName) {
    return getUploadBaseDir() + File.separatorChar + fileName + TEMP_SUFFIX;
  }

  private void writeBytes(long length, RandomAccessFile raf, InputStream is) throws IOException {
    long read = 0;
    byte[] bytes = new byte[1024 * 100];
    while (read < length) {
      int r = is.read(bytes);
      if (r < 0) {
        break;
      }
      raf.write(bytes, 0, r);
      read += r;
    }
    raf.close();
  }

  protected String getUploadBaseDir() {
    return environment.getProperty("upload.base.dir", "/var/putput/files");
  }

}
