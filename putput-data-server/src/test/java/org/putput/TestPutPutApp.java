package org.putput;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@Configuration
@ComponentScan
public class TestPutPutApp {
    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(TestPutPutApp.class)
          .profiles("integration")
          .run(args);
    }
}