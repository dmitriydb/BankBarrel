package ru.shanalotte.bankbarrel.rest.infomodule.openapi.definitions;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Profile;

@OpenAPIDefinition(
    servers = @Server(
        description = "Production",
        url = "http://localhost:8887"
    ),
    info = @Info(
        version = "1.0",
        description = "API сервиса справочной информации (PRODUCTION)",
        contact = @Contact(
            email = "drizhiloda@gmail.com"
        ),
        title = "Rest infomodule api"
    )
)
@Profile("production")
public class ProductionApiDefinition {
}
