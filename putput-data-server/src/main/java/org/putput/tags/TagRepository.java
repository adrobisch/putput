package org.putput.tags;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, String> {
    @Query("SELECT tag " +
            "FROM TagEntity tag " +
            "WHERE tag.taggableId = :taggableId")
    List<TagEntity> findByTaggableId(@Param("taggableId") String taggableId);
}
