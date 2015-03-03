package org.putput.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import java.util.Collection;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

  @Autowired
  Collection<BaseResource> baseResources;

  public JerseyConfig() {
  }

  @PostConstruct
  public void registerResources() {
    for (BaseResource baseResource: baseResources) {
      org.slf4j.LoggerFactory.getLogger(JerseyConfig.class).info("registering " + baseResource.getClass().getName());
      register(baseResource.getClass());
    }
  }

}