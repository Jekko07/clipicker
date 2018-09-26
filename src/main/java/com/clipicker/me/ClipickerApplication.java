package com.clipicker.me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableScheduling
public class ClipickerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClipickerApplication.class, args);
  }
}
