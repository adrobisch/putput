package org.putput.rss;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RssFeedInfoRepository extends CrudRepository<RssFeedInfoEntity, String> {
  @Query("select rssFeed from RssFeedInfoEntity rssFeed where rssFeed.owner.username =:username")
  List<RssFeedInfoEntity> findByUsername(@Param("username") String username);
}
