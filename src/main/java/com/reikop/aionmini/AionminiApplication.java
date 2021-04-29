package com.reikop.aionmini;

import kong.unirest.Unirest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class AionminiApplication {

    public static void main(String[] args) {

        Unirest.config().verifySsl(false);

//        SpringApplication.run(AionminiApplication.class, args);
        new SpringApplicationBuilder()
                .listeners(new ApplicationPidFileWriter("./aionmini.pid"))
                .build()
                .run(AionminiApplication.class, args);
    }

}
