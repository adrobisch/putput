package org.putput.common.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface AccessTokenRepository extends PagingAndSortingRepository<AccessTokenEntity, String> {
    @Query("select accessToken from AccessTokenEntity accessToken " +
        "where accessToken.owner.username = :username " +
        "order by accessToken.created desc")
    Page<AccessTokenEntity> findByOwner(@Param("username") String username, Pageable pageable);

    @Query("select accessToken from AccessTokenEntity accessToken " +
            "where accessToken.owner.username = :username " +
            "and accessToken.id = :id ")
    AccessTokenEntity findByOwnerAndId(@Param("username") String username, @Param("id") String id);

    AccessTokenEntity findByTokenAndSecret(String token, String secret);
}
