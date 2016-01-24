package org.putput.config;

import brainslug.flow.builder.FlowBuilder;
import brainslug.jpa.spring.SpringDatabaseConfiguration;
import brainslug.spring.SpringBrainslugConfiguration;
import com.mysema.query.jpa.HQLTemplates;
import com.mysema.query.jpa.JPQLTemplates;
import org.putput.messages.NewMessageFlow;
import org.putput.password.ForgotPasswordFlow;
import org.putput.stream.NewItemFlow;
import org.putput.stream.NewMarkerFlow;
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

    @Bean
    FlowBuilder newItemFlow() {
        return new NewItemFlow();
    }

    @Bean
    FlowBuilder newMarkerFlow() {
        return new NewMarkerFlow();
    }
    
    @Bean
    FlowBuilder newMessageFlow() {
        return new NewMessageFlow();
    }
}
