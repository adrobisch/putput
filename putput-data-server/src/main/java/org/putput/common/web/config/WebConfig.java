package org.putput.common.web.config;

import com.github.ziplet.filter.compression.CompressingFilter;
import org.putput.common.web.filter.AllowAllCorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

  private static final String[] ROOT_RESOURCES = {
    "classpath:/web/"};

  private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
    "classpath:/META-INF/resources/", "classpath:/resources/",
    "classpath:/static/", "classpath:/public/"};

  @Autowired
  Environment environment;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {

    if (!registry.hasMappingForPattern("/**")) {
      registry
          .addResourceHandler("/**")
          .addResourceLocations(ROOT_RESOURCES)
          .setCachePeriod(60*60);
    }

    if (!registry.hasMappingForPattern("/static/**")) {
      registry
          .addResourceHandler("/static/**")
          .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS)
          .setCachePeriod(3600*24);
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

  @Bean
  public OncePerRequestFilter protocolForwardFilter() {
    return new OncePerRequestFilter() {
      @Override
      protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String forwardedProtocolHeader = request.getHeader("X-Forwarded-Proto");
        boolean forwardToHttps = Boolean.parseBoolean(environment.getProperty("forward.to.https", "true"));

        if (forwardToHttps && forwardedProtocolHeader != null && forwardedProtocolHeader.equals("http")) {
          response.sendRedirect(environment.getProperty("secure.base.url", "https://putput.org") + request.getRequestURI());
        } else {
          filterChain.doFilter(request, response);
        }

      }
    };
  }

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.setUseSuffixPatternMatch(false);
  }
}