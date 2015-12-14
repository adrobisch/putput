package org.putput.stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface StreamItemRepository extends PagingAndSortingRepository<StreamItemEntity, String> {
  @Query("SELECT streamItem FROM StreamItemEntity streamItem " +
      "WHERE streamItem.author.username = :username " +
      "OR streamItem.author.username IN (SELECT follower.followed FROM FollowerEntity follower WHERE follower.follower = :username) " +
      "ORDER BY streamItem.created DESC")
  Page<StreamItemEntity> findByUserName(@Param("username") String userName, Pageable pageable);

  @Query("SELECT streamItem FROM StreamItemEntity streamItem " +
      "JOIN streamItem.markers markers " +
      "WHERE streamItem.author.username = :username " +
      "OR streamItem.author.username IN (SELECT follower.followed FROM FollowerEntity follower WHERE follower.follower = :username) " +
      "AND markers.markerType = :markerType " +
      "ORDER BY streamItem.created DESC")
  Page<StreamItemEntity> findByUserNameAndMarkerType(@Param("username") String userName,
                                                     @Param("markerType") String markerType,
                                                     Pageable pageable);

  @Query("SELECT COUNT(streamItem) FROM StreamItemEntity streamItem " +
      "WHERE streamItem.author.username = :username ")
  Long countByUsername(@Param("username") String userName);

}
