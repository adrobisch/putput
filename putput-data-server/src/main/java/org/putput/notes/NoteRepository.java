package org.putput.notes;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends CrudRepository<NoteEntity, String> {
  @Query("SELECT note FROM NoteEntity note WHERE note.user.username = :username ORDER BY note.updated DESC")
  List<NoteEntity> findByUserName(@Param("username") String userName);
}
