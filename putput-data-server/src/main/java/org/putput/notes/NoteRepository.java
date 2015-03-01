package org.putput.notes;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends CrudRepository<NoteEntity, String> {
  @Query("SELECT c FROM NoteEntity c WHERE c.user.username = :username")
  List<NoteEntity> findByUserName(@Param("username") String userName);
}
