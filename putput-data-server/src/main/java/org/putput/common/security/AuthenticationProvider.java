package org.putput.common.security;

import org.putput.users.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.putput.users.UserRepository;

import java.util.Arrays;

@Component
@Profile({"production", "unit"})
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  UserRepository userRepository;

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
    boolean passwordMatches = passwordEncoder.matches(userDetails.getPassword(), userRepository.findByUsername(userDetails.getUsername()).getPasswordHash());

    if (!passwordMatches) {
      throw new BadCredentialsException("Wrong password");
    }
  }

  @Override
  protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
    UserEntity user = userRepository.findByUsername(usernamePasswordAuthenticationToken.getName());
    if (user == null) {
      throw new UsernameNotFoundException("unable to find user");
    }

    return createUserDetails(user, usernamePasswordAuthenticationToken.getCredentials().toString());
  }

  private User createUserDetails(UserEntity user, String password) {
    return new User(user.getUsername(),
        password,
        Arrays.asList(new SimpleGrantedAuthority("USER")));
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    return super.authenticate(authentication);
  }
}
