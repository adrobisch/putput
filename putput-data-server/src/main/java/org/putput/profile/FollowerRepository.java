package org.putput.profile;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowerRepository extends CrudRepository<FollowerEntity, String> {
  @Query("SELECT follower FROM FollowerEntity follower WHERE follower.followed = :followed")
  List<FollowerEntity> findByFollowed(@Param("followed") String followed);

  @Query("SELECT follower FROM FollowerEntity follower WHERE follower.follower = :follower")
  List<FollowerEntity> findByFollower(@Param("follower") String follower);

  @Query("SELECT follower FROM FollowerEntity follower " +
      "WHERE follower.follower = :follower " +
      "AND follower.followed = :followed")
  FollowerEntity findByFollowerAndFollowed(@Param("follower") String follower,
                                           @Param("followed") String followed);
}
