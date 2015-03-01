package org.putput.config;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfiguration {

  @Bean
  MapperFacade beanMapper() {
    return new DefaultMapperFactory.Builder().build().getMapperFacade();
  }

}
