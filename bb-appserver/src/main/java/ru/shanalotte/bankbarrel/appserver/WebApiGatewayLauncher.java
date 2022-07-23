package ru.shanalotte.bankbarrel.appserver;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * Лаунчер веб-приложения.
 */
@SpringBootApplication
@ComponentScan("ru.shanalotte.bankbarrel.appserver")
@ComponentScan("ru.shanalotte.bankbarrel.core")
@EntityScan(value = {"ru.shanalotte.bankbarrel.core.domain", "ru.shanalotte.bankbarrel.appserver"})
@OpenAPIDefinition(
    info = @Info(
        description = "API REST-сервиса бизнес-логики",
        contact = @Contact(
            email = "drizhiloda@gmail.com",
            name = "Drizhilo D."
        ),
        version = "1.0",
        title = "Web API Gateway API Definition"
    )
)
@EnableScheduling
public class WebApiGatewayLauncher {
  public static void main(String[] args) {
    SpringApplication.run(WebApiGatewayLauncher.class, args);
  }
}
