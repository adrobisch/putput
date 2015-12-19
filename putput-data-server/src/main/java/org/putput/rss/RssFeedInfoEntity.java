package org.putput.rss;

import org.putput.common.persistence.BaseEntity;
import org.putput.users.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "PP_RSS_FEED_INFO")
public class RssFeedInfoEntity extends BaseEntity<RssFeedInfoEntity> {
  @Column(name = "_URL")
  String url;

  @ManyToOne
  @JoinColumn(name = "_USERNAME", referencedColumnName = "_USERNAME")
  UserEntity owner;

  public String getUrl() {
    return url;
  }

  public UserEntity getOwner() {
    return owner;
  }

  public RssFeedInfoEntity setUrl(String url) {
    this.url = url;
    return this;
  }

  public RssFeedInfoEntity setOwner(UserEntity owner) {
    this.owner = owner;
    return this;
  }
}
