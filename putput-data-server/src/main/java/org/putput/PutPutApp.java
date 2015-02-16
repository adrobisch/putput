package org.putput;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Configuration
@ComponentScan
public class PutPutApp {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(PutPutApp.class)
          .profiles("production")
          .run(args);
    }

}