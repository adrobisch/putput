package org.putput.contacts;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends CrudRepository<ContactEntity, String> {
  @Query("SELECT c FROM ContactEntity c WHERE c.user.username = :username")
  List<ContactEntity> findByUserName(@Param("username") String userName);
}
