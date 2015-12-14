package org.putput.stream;

import org.putput.common.persistence.BaseEntity;
import org.putput.users.UserEntity;

import javax.persistence.*;

import static java.util.Optional.ofNullable;

@Entity
@Table(name = "PP_ITEM_MARKER")
public class StreamItemMarkerEntity extends BaseEntity<StreamItemMarkerEntity> {

  @ManyToOne
  @JoinColumn(name = "_ITEM_ID")
  StreamItemEntity item;

  @OneToOne
  @JoinColumn(name = "_AUTHOR")
  UserEntity author;

  @Column(name = "_MARKER_TYPE")
  String markerType;

  public StreamItemEntity getItem() {
    return item;
  }

  public UserEntity getAuthor() {
    return author;
  }

  public StreamItemMarkerType getMarkerType() {
    return ofNullable(markerType)
        .map(markerTypeString -> StreamItemMarkerType.fromValue(markerType))
        .get();
  }

  public StreamItemMarkerEntity setItem(StreamItemEntity item) {
    this.item = item;
    return this;
  }

  public StreamItemMarkerEntity setAuthor(UserEntity author) {
    this.author = author;
    return this;
  }

  public StreamItemMarkerEntity setMarkerType(StreamItemMarkerType markerType) {
    if (markerType != null) {
      this.markerType = markerType.value();
    }
    return this;
  }
}
