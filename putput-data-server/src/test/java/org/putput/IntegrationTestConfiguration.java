package org.putput;

import org.putput.common.security.AuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.RememberMeServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class IntegrationTestConfiguration {

  @Bean
  @Profile("integration")
  AuthenticationProvider authenticationProvider() {
    return new AuthenticationProvider() {
      @Override
      public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authentication;
      }
    };
  }

  @Bean
  @Profile("integration")
  public RememberMeServices rememberMeServices() {
    return new RememberMeServices() {
      @Override
      public Authentication autoLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        final List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        String credentials = "$2a$10$NV4rlug0FGyW0UuivufxOetXnm7FeVgDGXbBmtajo6htdDl6g9bs.";
        return new UsernamePasswordAuthenticationToken(new User("user", credentials, authorities), credentials, authorities);
      }

      @Override
      public void loginFail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

      }

      @Override
      public void loginSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

      }
    };
  }
}
