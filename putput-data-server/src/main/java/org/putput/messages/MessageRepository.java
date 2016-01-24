package org.putput.messages;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository<MessageEntity, String> {
  @Query("select message from MessageEntity message where message.from = :username or message.to = :username")
  List<MessageEntity> findToOrFromUser(@Param("username") String username);
}
