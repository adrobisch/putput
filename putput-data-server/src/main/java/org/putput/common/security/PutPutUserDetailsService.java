package org.putput.common.security;

import org.putput.users.UserRepository;
import org.putput.users.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class PutPutUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("user not found: " + username);
    }

    return new org.springframework.security.core.userdetails.User(
      username,
      user.getPasswordHash(),
      Arrays.asList(new SimpleGrantedAuthority("USER"))
    );
  }
}
