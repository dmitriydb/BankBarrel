package ru.shanalotte.bankbarrel.serviceregistry;

import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Launcher.
 */
@SpringBootApplication
@ComponentScan("ru.shanalotte.bankbarrel.serviceregistry")
public class ServiceRegistryLauncher {

  private static final Logger logger = LoggerFactory.getLogger(ServiceRegistryLauncher.class);

  public static void main(String[] args) {
    SpringApplication.run(ServiceRegistryLauncher.class, args);
    logger.info("Service registry started");
  }
}
