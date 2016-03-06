package org.putput.common.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PutPutRememberMeServices extends TokenBasedRememberMeServices {

  public static final String tokenHeaderName = "X-PutPut-Token";
  
  public PutPutRememberMeServices(String key, UserDetailsService userDetailsService) {
    super(key, userDetailsService);
    this.setCookieName(tokenHeaderName);
    this.setParameter("rememberMe"); // for the login form
    this.setAlwaysRemember(true);
  }

  @Override
  protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
    super.setCookie(tokens, maxAge, request, response);

    String tokenValue = encodeCookie(tokens);
    response.setHeader(tokenHeaderName, tokenValue);
  }

  @Override
  protected String extractRememberMeCookie(HttpServletRequest request) {
    String extractedCookie = super.extractRememberMeCookie(request);
    String tokenHeaderValue = request.getHeader(tokenHeaderName);

    return tokenHeaderValue == null ? extractedCookie : tokenHeaderValue;
  }
}
