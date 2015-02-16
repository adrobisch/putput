package org.putput.common.web.config;

import org.apache.catalina.connector.Connector;
import org.putput.config.PutPutConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

@Configuration
public class HttpsConfig {

  @Autowired
  Environment environment;

  @Bean
  @Autowired
  public EmbeddedServletContainerCustomizer containerCustomizer() {
    Boolean httpsEnabled = environment.getProperty(PutPutConfiguration.HTTPS_ENABLED, Boolean.class, false);

    String keystoreFile = environment.getProperty("keystore.file", "classpath:keystore.jks");
    String keystorePassword = environment.getProperty("keystore.password", "password");
    String keystoreType = environment.getProperty("keystore.type", "jks");
    String keystoreAlias = environment.getProperty("keystore.alias");

    return (ConfigurableEmbeddedServletContainer factory) -> {
      TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) factory;

      if (httpsEnabled) {
        final String absoluteKeystoreFile = getKeyStoreFilePath(keystoreFile);

        containerFactory.addConnectorCustomizers((TomcatConnectorCustomizer) (Connector connector) -> {
          connector.setSecure(true);
          connector.setScheme("https");
          connector.setAttribute("keystoreFile", absoluteKeystoreFile);
          connector.setAttribute("keystorePass", keystorePassword);
          connector.setAttribute("keystoreType", keystoreType);

          if (keystoreAlias != null) {
            connector.setAttribute("keyAlias", keystoreAlias);
          }

          connector.setAttribute("clientAuth", "false");
          connector.setAttribute("sslProtocol", "TLS");
          connector.setAttribute("SSLEnabled", true);
        });
      }
    };
  }

  private String getKeyStoreFilePath(String keystoreFile) {
    try {
      return ResourceUtils.getFile(keystoreFile).getAbsolutePath();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
