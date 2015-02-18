package org.putput.files;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.UnsupportedEncodingException;

@Entity
@Table(name = "PP_FILE")
public class PutPutFile {

  @Id
  @Column(name = "_ID")
  String id;

  @Column(name = "_PATH")
  String path;

  @Column(name = "_DATA", columnDefinition = "CLOB")
  String data;

  @Column(name = "_TYPE")
  String type;

  public PutPutFile() {
  }

  public PutPutFile(String type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public PutPutFile withId(String id) {
    this.id = id;
    return this;
  }

  public String getPath() {
    return path;
  }

  public PutPutFile withData(String base64Data) {
    this.data = base64Data;
    return this;
  }

  public PutPutFile withPath(String path) {
    this.path = path;
    return this;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return "PutPutFile{" +
      "id='" + id + '\'' +
      ", path='" + path + '\'' +
      ", type='" + type + '\'' +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PutPutFile that = (PutPutFile) o;

    if (path != null ? !path.equals(that.path) : that.path != null) return false;
    if (type != null ? !type.equals(that.type) : that.type != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = path != null ? path.hashCode() : 0;
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }
}
