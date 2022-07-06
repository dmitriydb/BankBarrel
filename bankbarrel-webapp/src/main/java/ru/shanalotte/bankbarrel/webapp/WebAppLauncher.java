package ru.shanalotte.bankbarrel.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Web app class launcher.
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan("ru.shanalotte.bankbarrel")
public class WebAppLauncher implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(WebAppLauncher.class);

  public static void main(String[] args) {
    SpringApplication.run(WebAppLauncher.class, args);

  }

  @Override
  public void run(String... args) throws Exception {
    for (int i = 0; i < 100; i++) {
      logger.info("StartingStartingStarting... " + i);
    }
  }
}
