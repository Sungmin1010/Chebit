package com.nesty.chebit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ChebitProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChebitProjectApplication.class, args);
    }

}
