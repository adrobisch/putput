package org.putput.files;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends CrudRepository<PutPutFile, String> {
    @Query("SELECT file FROM PutPutFile file WHERE file.user.username = :username ORDER BY file.name ASC")
    List<PutPutFile> findByUser(@Param("username") String userName);

    @Query("SELECT file FROM PutPutFile file WHERE file.storageReference = :storageReference " +
            "AND file.storageContainerReference = :storageContainerReference")
    PutPutFile findByContentReference(@Param("storageReference") String contentReference);

    @Query("SELECT file FROM PutPutFile file WHERE file.storageContainerReference = :storageContainerReference")
    PutPutFile findByContainerReference(@Param("storageContainerReference") String storageContainerReference);
}
