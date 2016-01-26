package org.putput.messages;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends PagingAndSortingRepository<MessageEntity, String> {
  @Query("select message from MessageEntity message where message.from = :username or message.to = :username order by message.created desc")
  Page<MessageEntity> findToOrFromUser(@Param("username") String username, Pageable pageable);
}
