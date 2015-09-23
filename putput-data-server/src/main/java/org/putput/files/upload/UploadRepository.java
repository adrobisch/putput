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

import static org.putput.util.FileHelper.requireExistingDir;

@Repository
public class UploadRepository {

  public static final String TEMP_SUFFIX = ".temp";

  Environment environment;

  @Autowired
  public UploadRepository(Environment environment) {
    this.environment = environment;
    requireExistingDir(getBaseDir());
  }

  private HashMap<String, HashSet<ChunkNumber>> uploadedChunksById = new HashMap<String, HashSet<ChunkNumber>>();

  public void markUploaded(UploadRequest upload) {
    getOrCreateUpload(upload).add(new ChunkNumber(upload.getResumableChunkNumber()));
  }

  private HashSet<ChunkNumber> getOrCreateUpload(UploadRequest uploadRequest) {
    String uploadKey = uploadKey(uploadRequest);
    if(!uploadedChunksById.containsKey(uploadKey)) {
      uploadedChunksById.put(uploadKey, new HashSet<>());
    }
    return uploadedChunksById.get(uploadKey);
  }

  public boolean chunkExists(UploadRequest uploadRequest) {
    return chunkExists(uploadRequest, uploadRequest.getResumableChunkNumber());
  }

  public boolean chunkExists(UploadRequest uploadRequest, int chunkIndex) {
    ChunkNumber chunkNumber = new ChunkNumber(chunkIndex);
    return getOrCreateUpload(uploadRequest).contains(chunkNumber);
  }

  public synchronized void remove(String uploadKey) {
    if (uploadedChunksById.containsKey(uploadKey)) {
      uploadedChunksById.remove(uploadKey);
    }
  }

  public void writeChunk(UploadRequest uploadRequest, InputStream dataStream) {
    try {
      RandomAccessFile raf = getRandomAccessFileAtPosition(uploadRequest.getUploadFolder(),
              Optional.ofNullable(uploadRequest.getPath()).orElse("") + uploadRequest.getResumableFilename(),
              uploadRequest.getResumableChunkSize(),
              uploadRequest.getResumableChunkNumber());

      writeBytes(uploadRequest.getContentLength(), raf, dataStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public File complete(UploadRequest uploadRequest) {
    remove(uploadKey(uploadRequest));
    return removeTempSuffix(getTempFile(uploadRequest.getUploadFolder(), uploadRequest.getResumableFilename()));
  }

  private String uploadKey(UploadRequest uploadRequest) {
    return uploadRequest.getUploadFolder() + uploadRequest.getResumableIdentifier();
  }

  File removeTempSuffix(File tempFile) {
    String finalPath = tempFile.getAbsolutePath().substring(0, tempFile.getAbsolutePath().length() - TEMP_SUFFIX.length());
    File finalFile = new File(finalPath);
    if (!tempFile.renameTo(finalFile)) {
      throw new IllegalStateException("unable to rename temp file to final file: " + tempFile.getAbsolutePath());
    }
    return finalFile;
  }

  private RandomAccessFile getRandomAccessFileAtPosition(String username, String fileName, int chunkSize, int chunkNumber) throws IOException {
    RandomAccessFile raf = new RandomAccessFile(getTempFile(username, fileName), "rw");
    raf.seek((chunkNumber - 1) * chunkSize);
    return raf;
  }

  private File getTempFile(String username, String fileName) {
    return new File(requireExistingDir(getUploadDir(username)), fileName + TEMP_SUFFIX);
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

  protected String getUploadDir(String username) {
    return getBaseDir() + File.separatorChar + username;
  }

  private String getBaseDir() {
    return environment.getProperty("uploads.base.dir", "/var/putput/uploads");
  }

}
