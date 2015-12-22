package org.putput.users;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, String> {
  UserEntity findByUsername(String username);
  Optional<UserEntity> findByEmail(String email);
  List<UserEntity> findByUsernameContaining(String search);
}
