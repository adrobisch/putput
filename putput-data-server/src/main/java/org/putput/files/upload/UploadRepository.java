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

@Repository
public class UploadRepository {

  public static final String TEMP_SUFFIX = ".temp";

  @Autowired
  Environment environment;

  private HashMap<String, HashSet<ChunkNumber>> uploadedChunksById = new HashMap<String, HashSet<ChunkNumber>>();

  public void markUploaded(String uploadId, int chunkNumber) {
    if(uploadedChunksById.containsKey(uploadId)) {
      uploadedChunksById.put(uploadId, new HashSet<>());
    }
    uploadedChunksById.get(uploadId).add(new ChunkNumber(chunkNumber));
  }

  public boolean chunkExists(String uploadIdentifier, int chunkNumber) {
    return uploadedChunksById.get(uploadIdentifier).contains(new ChunkNumber(chunkNumber));
  }

  public synchronized void remove(String uploadId) {
    uploadedChunksById.remove(uploadId);
  }

  public void writeChunk(String path, InputStream dataStream, long streamSize, int resumableChunkSize, int resumableChunkNumber) {
    try {
      RandomAccessFile raf = getRandomAccessFileAtPosition(path, resumableChunkSize, resumableChunkNumber);
      writeBytes(streamSize, raf, dataStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void complete(String uploadIdentifier, String path) {
    remove(uploadIdentifier);
    removeTempSuffix(getTempFilePath(path));
  }

  private boolean removeTempSuffix(String path) {
    File file = new File(path);
    String finalPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - TEMP_SUFFIX.length());
    file.renameTo(new File(finalPath));
    return true;
  }

  private RandomAccessFile getRandomAccessFileAtPosition(String path, int resumableChunkSize, int resumableChunkNumber) throws IOException {
    RandomAccessFile raf = new RandomAccessFile(getTempFilePath(path), "rw");
    raf.seek((resumableChunkNumber - 1) * resumableChunkSize);
    return raf;
  }

  private String getTempFilePath(String path) {
    return getUploadBaseDir() + File.separatorChar + path + TEMP_SUFFIX;
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
