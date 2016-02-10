package org.putput.calendar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends PagingAndSortingRepository<EventEntity, String> {
  @Query("select event from EventEntity event where event.owner.username = :username order by event.start desc")
  Page<EventEntity> findByOwner(@Param("username") String username, Pageable pageable);

  @Query("select event from EventEntity event where event.owner.username = :username and event.id=:id")
  EventEntity findByOwnerAndId(@Param("username") String username, @Param("id") String id);
}