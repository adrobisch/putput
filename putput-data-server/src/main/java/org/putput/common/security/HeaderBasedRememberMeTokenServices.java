package org.putput.common.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HeaderBasedRememberMeTokenServices extends TokenBasedRememberMeServices {

  public HeaderBasedRememberMeTokenServices(String key, UserDetailsService userDetailsService) {
    super(key, userDetailsService);
  }

  @Override
  protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
    super.setCookie(tokens, maxAge, request, response);

    String tokenValue = encodeCookie(tokens);
    response.setHeader(WebSecurityConfig.tokenHeaderName, tokenValue);
  }

  @Override
  protected String extractRememberMeCookie(HttpServletRequest request) {
    String extractedCookie = super.extractRememberMeCookie(request);
    String tokenHeaderValue = request.getHeader(WebSecurityConfig.tokenHeaderName);

    return tokenHeaderValue == null ? extractedCookie : tokenHeaderValue;
  }
}
