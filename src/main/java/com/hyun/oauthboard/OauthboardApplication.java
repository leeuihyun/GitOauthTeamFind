package com.hyun.oauthboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class OauthboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthboardApplication.class, args);
    }

}
