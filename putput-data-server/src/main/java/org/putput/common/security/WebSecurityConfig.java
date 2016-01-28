package org.putput.common.security;

import org.putput.api.resource.Login;
import org.putput.api.resource.Logout;
import org.putput.api.resource.PasswordRequest;
import org.putput.api.resource.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.putput.util.FileHelper.getPathFromResource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  AuthenticationProvider authenticationProvider;

  public static final String tokenHeaderName = "X-PutPut-Token";

  private static final String apiBasePath = "/api/";
  private static String loginPath = apiBasePath + getPathFromResource(Login.class);
  private static String logoutPath = apiBasePath + getPathFromResource(Logout.class);
  private static String userPath = apiBasePath + getPathFromResource(User.class);
  public static final String passwordRequestPath = apiBasePath + getPathFromResource(PasswordRequest.class);

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .exceptionHandling().authenticationEntryPoint(getAuthenticationEntryPoint())
      .and()
      .authorizeRequests()
      .requestMatchers(request -> request.getRequestURI().startsWith(loginPath)).anonymous()
      .requestMatchers(request -> request.getRequestURI().equals(userPath) && request.getMethod().equalsIgnoreCase("POST")).anonymous()
      .requestMatchers(request -> request.getRequestURI().startsWith(passwordRequestPath)).permitAll()
      .requestMatchers(request -> request.getRequestURI().startsWith(apiBasePath + getPathFromResource(PasswordRequest.class))).anonymous()
      .requestMatchers(request -> request.getRequestURI().startsWith(apiBasePath)).authenticated()
      .and()
      .formLogin()
      .loginPage(loginPath)
      .usernameParameter("username")
      .passwordParameter("password")
      .successHandler(loginSuccessHandler())
      .failureHandler(loginFailureHandler())
      .permitAll()
      .and()
      .logout()
      .logoutUrl(logoutPath)
      .logoutSuccessHandler(logoutSuccessHandler())
      .permitAll();

    http.rememberMe().rememberMeServices(rememberMeServices()).key("token");
  }

  private SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler() {
    SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
    handler.setUseReferer(true);
    return handler;
  }

  private SimpleUrlLogoutSuccessHandler logoutSuccessHandler() {
    SimpleUrlLogoutSuccessHandler handler = new SimpleUrlLogoutSuccessHandler();
    handler.setUseReferer(true);
    return handler;
  }

  private AuthenticationFailureHandler loginFailureHandler() throws IOException {
    return (request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
  }

  private AuthenticationEntryPoint getAuthenticationEntryPoint() throws IOException {
    return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
  }

  @Bean
  @Profile({"production", "unit"})
  public RememberMeServices rememberMeServices() {
    TokenBasedRememberMeServices rememberMeServices = new HeaderBasedRememberMeTokenServices("token", userDetailsService);
    rememberMeServices.setCookieName(tokenHeaderName);
    rememberMeServices.setParameter("rememberMe"); // for the login form
    rememberMeServices.setAlwaysRemember(true);
    return rememberMeServices;
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/static/public/**");
  }
}
