package org.putput.common.persistence;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {
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

  public Long getCreated() {
    return created;
  }

  public Long getUpdated() {
    return updated;
  }

  public Date getCreatedDate() {
    return new Date(created);
  }

  public Date getUpdatedDate() {
    return new Date(updated);
  }
}
