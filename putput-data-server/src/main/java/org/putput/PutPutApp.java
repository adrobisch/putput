package org.putput;

import brainslug.jpa.entity.FlowInstanceEntity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Configuration
@ComponentScan
@EntityScan(basePackageClasses={FlowInstanceEntity.class, PutPutApp.class})
public class PutPutApp {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(PutPutApp.class)
          .profiles("production")
          .run(args);
    }

}