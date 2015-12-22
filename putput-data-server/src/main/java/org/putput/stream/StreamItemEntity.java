package org.putput.stream;

import org.hibernate.annotations.Type;
import org.putput.common.persistence.BaseEntity;
import org.putput.users.UserEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Entity
@Table(name = "PP_ITEM")
public class StreamItemEntity extends BaseEntity<StreamItemEntity> {

  @ManyToOne
  @JoinColumn(name = "_AUTHOR")
  UserEntity author;

  @Column(name = "_TITLE")
  String title;

  @Column(name = "_CONTENT")
  @Lob
  @Type(type="org.hibernate.type.StringClobType")
  String content;

  @Column(name = "_SOURCE")
  String source;

  @Column(name = "_EXTERNAL_REF")
  String externalRef;

  @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
  List<StreamItemMarkerEntity> markers = new ArrayList<>();

  public UserEntity getAuthor() {
    return author;
  }

  public StreamItemEntity setAuthor(UserEntity author) {
    this.author = author;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public StreamItemEntity setTitle(String title) {
    this.title = title;
    return this;
  }

  public Optional<String> getExternalRef() {
    return Optional.ofNullable(externalRef);
  }

  public StreamItemEntity setExternalRef(String externalRef) {
    this.externalRef = externalRef;
    return this;
  }

  public String getContent() {
    return content;
  }

  public StreamItemEntity setContent(String content) {
    this.content = content;
    return this;
  }

  public Optional<StreamItemSource> getSource() {
    return ofNullable(source).map(StreamItemSource::valueOf);
  }

  public StreamItemEntity setSource(StreamItemSource source) {
    if (source != null) {
      this.source = source.value();
    }
    return this;
  }

  public List<StreamItemMarkerEntity> getMarkers() {
    return markers;
  }
}
