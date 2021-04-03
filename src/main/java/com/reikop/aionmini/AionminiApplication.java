package com.reikop.aionmini;

import kong.unirest.Unirest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AionminiApplication {

    public static void main(String[] args) {

        Unirest.config()
                .verifySsl(false);

        SpringApplication.run(AionminiApplication.class, args);
    }

}
