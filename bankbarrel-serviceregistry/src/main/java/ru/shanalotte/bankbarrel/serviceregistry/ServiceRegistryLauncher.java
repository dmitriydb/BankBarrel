package ru.shanalotte.bankbarrel.serviceregistry;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ru.shanalotte.bankbarrel.serviceregistry")
public class ServiceRegistryLauncher {
  public static void main(String[] args) {
    SpringApplication.run(ServiceRegistryLauncher.class, args);
  }
}
