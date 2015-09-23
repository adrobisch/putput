package org.putput.common.persistence;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.util.Date;
import java.util.Optional;

@MappedSuperclass
public abstract class BaseEntity {
  @Id
  @Column(name = "_ID")
  String id;
  
  @Version
  @Column(name = "_VERSION")
  Long version;
  
  @Column(name = "_CREATED", insertable = true, updatable = false, nullable = false)
  private Long created;

  @Column(name = "_UPDATED", insertable = false, updatable = true)
  private Long updated;

  @PrePersist
  void onCreate() {
    this.created = new Date().getTime();
  }

  @PreUpdate
  void onUpdate() {
    this.updated = new Date().getTime();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public Long getCreated() {
    return created;
  }

  public Long getUpdated() {
    return updated;
  }

  public Date getCreatedDate() {
    if (created == null) {
      return null;
    }
    return new Date(created);
  }

  public Date getUpdatedDate() {
    if (updated == null) {
      return null;
    }
    return new Date(updated);
  }
}
