package org.putput.notes;

import org.putput.common.persistence.BaseEntity;
import org.putput.users.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "PP_NOTE")
public class NoteEntity extends BaseEntity {
  @Column(name = "_SHORT_TEXT")
  String shortText;

  @Column(name = "_CONTENT", columnDefinition = "CLOB")
  String content;

  @ManyToOne
  @JoinColumn(name = "_OWNER_ID")
  UserEntity user;

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

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }
}
