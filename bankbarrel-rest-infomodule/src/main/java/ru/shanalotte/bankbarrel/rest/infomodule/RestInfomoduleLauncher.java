package ru.shanalotte.bankbarrel.rest.infomodule;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.Servers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

/**
 * REST-микросервис справочной информации
 * В данный момент используется для получения json-ов с информацией о существующих
 * типах банковских счетов.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@OpenAPIDefinition(
    servers = @Server (
      description = "Локальный сервер разработки",
        url = "http://localhost:8887"
    ),
    info = @Info(
        version = "1.0",
        description = "API сервиса справочной информации",
        contact = @Contact(
            email = "drizhiloda@gmail.com"
        ),
        title = "Rest infomodule api"
    )
)
public class RestInfomoduleLauncher {
  public static void main(String[] args) {
    SpringApplication.run(RestInfomoduleLauncher.class, args);
  }
}
