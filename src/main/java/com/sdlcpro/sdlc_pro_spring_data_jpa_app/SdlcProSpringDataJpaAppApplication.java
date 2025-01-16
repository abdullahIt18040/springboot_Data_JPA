package com.sdlcpro.sdlc_pro_spring_data_jpa_app;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SdlcProSpringDataJpaAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SdlcProSpringDataJpaAppApplication.class, args);
    }

    @Autowired
    private EntityManager entityManager;

    @Override
    public void run(String... args) throws Exception {

    }
}
