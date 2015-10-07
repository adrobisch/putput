package org.putput.files;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends CrudRepository<PutPutFile, String> {
    
    String folderFirstOrder = " ORDER BY file.isDirectory DESC, file.name ASC";
    
    @Query("SELECT file " +
            "FROM PutPutFile file " +
            "WHERE file.user.username = :username" + folderFirstOrder)
    List<PutPutFile> findByUser(@Param("username") String userName);

    @Query("SELECT file " +
            "FROM PutPutFile file " +
            "WHERE file.user.username = :username " +
            "AND file.parent.id = :parentId" + folderFirstOrder)
    List<PutPutFile> findByUserAndParent(@Param("username") String userName,
                                            @Param("parentId") String parentId);

    @Query("SELECT file " +
            "FROM PutPutFile file, TagEntity tag " +
            "WHERE file.user.username = :username " +
            "AND UPPER(tag.text) = UPPER(:tag) " +
            "AND tag.taggableId = file.id")
    List<PutPutFile> findByUserAndTag(@Param("username") String userName,
                                      @Param("tag") String tag);

    @Query("SELECT file " +
            "FROM PutPutFile file " +
            "WHERE file.user.username = :username " +
            "AND file.storageContainerReference = :containerReference" + folderFirstOrder)
    List<PutPutFile> findByUserAndContainer(@Param("username") String userName, 
                                            @Param("containerReference") String containerReference);

    @Query("SELECT file " +
            "FROM PutPutFile file " +
            "WHERE file.storageContainerReference = :storageContainerReference " +
            "AND file.storageReference = :storageReference")
    PutPutFile findByFullReference(@Param("storageReference") String storageReference,
                                   @Param("storageContainerReference") String storageContainerReference);
}
