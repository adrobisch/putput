package org.putput.profile;

import org.putput.common.persistence.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PP_FOLLOWER")
public class FollowerEntity extends BaseEntity<FollowerEntity> {
  @Column(name = "_FOLLOWER")
  String follower;

  @Column(name = "_FOLLOWED")
  String followed;

  public String getFollower() {
    return follower;
  }

  public FollowerEntity setFollower(String follower) {
    this.follower = follower;
    return this;
  }

  public String getFollowed() {
    return followed;
  }

  public FollowerEntity setFollowed(String followed) {
    this.followed = followed;
    return this;
  }
}
