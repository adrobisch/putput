package org.putput.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfiguration {

  @Bean
  DozerBeanMapper dozerBeanMapper() {
    return new DozerBeanMapper();
  }

}
