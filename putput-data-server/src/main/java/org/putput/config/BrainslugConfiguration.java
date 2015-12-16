package org.putput.config;

import brainslug.flow.builder.FlowBuilder;
import brainslug.jpa.spring.SpringDatabaseConfiguration;
import brainslug.spring.SpringBrainslugConfiguration;
import com.mysema.query.jpa.HQLTemplates;
import com.mysema.query.jpa.JPQLTemplates;
import org.putput.password.ForgotPasswordFlow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SpringBrainslugConfiguration.class, SpringDatabaseConfiguration.class})
public class BrainslugConfiguration {

    @Bean
    JPQLTemplates jpqlTemplates() {
      return new HQLTemplates();
    }

    @Bean
    FlowBuilder forgotPasswordFlow() {
        return new ForgotPasswordFlow();
    }
}
