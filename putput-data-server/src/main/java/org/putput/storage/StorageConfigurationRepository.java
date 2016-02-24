package org.putput.storage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StorageConfigurationRepository extends CrudRepository<StorageConfiguration, String> {
    @Query("SELECT storage FROM StorageConfiguration storage WHERE storage.user.username = :username and storage.isDefault = 1")
    Optional<StorageConfiguration> findDefaultByUser(@Param("username") String userName);

    @Query("SELECT storage FROM StorageConfiguration storage WHERE storage.type = :type")
    List<StorageConfiguration> findByType(@Param("type") String type);
}
