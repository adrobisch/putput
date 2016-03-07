package org.putput.common.web;

import org.putput.config.PutPutConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class BaseResource implements HalSupport, PagingSupport {
  @Context
  UriInfo uriInfo;

  @Autowired
  Environment environment;

  Boolean httpsEnabled = null;

  @Context
  protected HttpServletRequest httpServletRequest;

  @Override
  public UriInfo getUriInfo() {
    return uriInfo;
  }

  @Override
  public String getProtocol() {
    String protocolHeader = httpServletRequest.getHeader("X-Forwarded-Proto");
    return protocolHeader == null ? defaultProtocol() : protocolHeader;
  }

  private String defaultProtocol() {
    if (isHttpsEnabled()) {
      return "https";
    } else {
      return "http";
    }
  }

  public Boolean isHttpsEnabled() {
    if (httpsEnabled == null) {
      httpsEnabled = environment.getProperty(PutPutConfiguration.HTTPS_ENABLED, Boolean.class, false);
    }
    return httpsEnabled;
  }

  public String user() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

}