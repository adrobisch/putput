package org.putput.users;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, String> {
  UserEntity findByUsername(String username);
  Optional<UserEntity> findByEmail(String email);
}
