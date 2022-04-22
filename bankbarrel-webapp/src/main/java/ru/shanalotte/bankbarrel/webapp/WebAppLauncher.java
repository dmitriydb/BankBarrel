package ru.shanalotte.bankbarrel.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Web app class launcher.
 */
@SpringBootApplication
@ComponentScan("ru.shanalotte.bankbarrel")
public class WebAppLauncher {
  public static void main(String[] args) {
    SpringApplication.run(WebAppLauncher.class, args);
  }
}
