package org.putput;

import brainslug.jpa.entity.FlowInstanceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.subethamail.wiser.Wiser;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
@ComponentScan
@EntityScan(basePackageClasses={FlowInstanceEntity.class, TestPutPutApp.class})
public class TestPutPutApp {
    public static void main(String[] args) throws Exception {
        startWiser();

        new SpringApplicationBuilder(TestPutPutApp.class)
          .profiles("integration")
          .run(args);
    }

    private static Wiser startWiser() {
        Wiser wiser = new Wiser();
        wiser.setPort(2500); // Default is 25
        wiser.start();

        Logger log = LoggerFactory.getLogger(TestPutPutApp.class);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            wiser.getMessages().forEach(message -> log.info("wiser message:"  + new String(message.getData())));
        }, 0, 3, TimeUnit.SECONDS);

        return wiser;
    }
}