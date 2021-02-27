package com.example.my_back_end;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBackEndApplication.class, args);
    }

}

