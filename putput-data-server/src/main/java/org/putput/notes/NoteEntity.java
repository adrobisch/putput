package org.putput.notes;

import org.putput.users.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "PP_NOTE")
public class NoteEntity {
  @Id
  @Column(name = "_ID")
  String id;

  @Column(name = "_SHORT_TEXT")
  String shortText;

  @Column(name = "_CONTENT")
  String content;

  @ManyToOne
  @JoinColumn(name = "_OWNER_ID")
  UserEntity user;

  @Version
  @Column(name = "_VERSION")
  Long version;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getShortText() {
    return shortText;
  }

  public void setShortText(String shortText) {
    this.shortText = shortText;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
