package org.putput.common.web.config;

import com.github.ziplet.filter.compression.CompressingFilter;
import org.putput.common.web.filter.AllowAllCorsFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

  private static final String[] ROOT_RESOURCES = {
    "classpath:/web/"};

  private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
    "classpath:/META-INF/resources/", "classpath:/resources/",
    "classpath:/static/", "classpath:/public/"};

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {

    if (!registry.hasMappingForPattern("/**")) {
      registry.addResourceHandler("/**").addResourceLocations(
        ROOT_RESOURCES);
    }

    if (!registry.hasMappingForPattern("/static/**")) {
      registry.addResourceHandler("/static/**").addResourceLocations(
        CLASSPATH_RESOURCE_LOCATIONS);
    }
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("forward:/index.html");
  }

  @Bean
  public FilterRegistrationBean compressingFilter() {
    CompressingFilter compressingFilter = new CompressingFilter();

    FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
    filterRegistration.setFilter(compressingFilter);

    return filterRegistration;
  }

  @Bean
  public AllowAllCorsFilter corsFilter() {
    return new AllowAllCorsFilter();
  }

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.setUseSuffixPatternMatch(false);
  }
}