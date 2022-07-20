package ru.shanalotte.bankbarrel.serviceregistry;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

/**
 * Launcher.
 */
@SpringBootApplication
@ComponentScan("ru.shanalotte.bankbarrel.serviceregistry")
@OpenAPIDefinition(
    servers = @Server(
        description = "Локальный реестр сервисов",
        url = "http://localhost:8181"
    ),
    info = @Info(
        version = "1.0",
        description = "API реестра сервисов",
        title = "Service Registry API Definition",
        contact = @Contact(
            name = "drizhiloda@gmail.com"
        )
    )
)
public class ServiceRegistryLauncher {

  private static final Logger logger = LoggerFactory.getLogger(ServiceRegistryLauncher.class);

  public static void main(String[] args) {
    SpringApplication.run(ServiceRegistryLauncher.class, args);
    logger.info("Service registry started");
  }
}
