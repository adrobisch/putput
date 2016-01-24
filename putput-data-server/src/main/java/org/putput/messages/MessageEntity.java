package org.putput.messages;

import org.hibernate.annotations.Type;
import org.putput.common.persistence.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "PP_MESSAGE")
public class MessageEntity extends BaseEntity<MessageEntity> {
  @Column(name = "_FROM")
  String from;

  @Column(name = "_TO")
  String to;

  @Column(name = "_TEXT")
  @Lob
  @Type(type="org.hibernate.type.StringClobType")
  String text;

  @Column(name = "_TYPE")
  String type;

  @Column(name = "_STATUS")
  String status;

  public String getFrom() {
    return from;
  }

  public MessageEntity setFrom(String from) {
    this.from = from;
    return this;
  }

  public String getTo() {
    return to;
  }

  public MessageEntity setTo(String to) {
    this.to = to;
    return this;
  }

  public String getText() {
    return text;
  }

  public MessageEntity setText(String text) {
    this.text = text;
    return this;
  }

  public String getType() {
    return type;
  }

  public MessageEntity setType(String type) {
    this.type = type;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public MessageEntity setStatus(String status) {
    this.status = status;
    return this;
  }
}
