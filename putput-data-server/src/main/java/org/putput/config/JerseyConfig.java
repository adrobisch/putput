package org.putput.config;

import org.glassfish.hk2.runlevel.RunLevelException;
import org.glassfish.jersey.server.ResourceConfig;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

  public JerseyConfig() {
  }

  @PostConstruct
  public void registerResources() {
    ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
    provider.addIncludeFilter(new AssignableTypeFilter(BaseResource.class));

    for (BeanDefinition component : provider.findCandidateComponents("org/putput")) {
      try {
        Class<?> resourceClass = Class.forName(component.getBeanClassName());
        org.slf4j.LoggerFactory.getLogger(JerseyConfig.class).info("registering " + resourceClass.getName());
        register(resourceClass);
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    }

  }

}