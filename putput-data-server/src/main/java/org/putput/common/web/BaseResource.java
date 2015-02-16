package org.putput.common.web;

import org.putput.api.model.HalLink;
import org.putput.config.PutPutConfiguration;
import org.putput.util.PathHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class BaseResource {
  @Context
  UriInfo uriInfo;

  @Autowired
  Environment environment;

  Boolean httpsEnabled = null;

  @Context
  HttpServletRequest httpServletRequest;

  protected HalLink link(Class<?> targetResource, String... paths) {
    return new HalLink().withHref(uriInfo.getBaseUriBuilder().segment(PathHelper.getPathFromResource(targetResource))
      .scheme(getProtocol())
      .segment(paths).toTemplate());
  }

  private String getProtocol() {
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

  Boolean isHttpsEnabled() {
    if (httpsEnabled == null) {
      httpsEnabled = environment.getProperty(PutPutConfiguration.HTTPS_ENABLED, Boolean.class, false);
    }
    return httpsEnabled;
  }

  protected User user() {
    return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}