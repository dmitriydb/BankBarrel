package ru.shanalotte.bankbarrel.rest.infomodule.openapi.definitions;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Profile;

@OpenAPIDefinition(
    servers = @Server(
        description = "Локальный сервер разработки (DEV)",
        url = "http://localhost:8885"
    ),
    info = @Info(
        version = "1.0",
        description = "API сервиса справочной информации (DEV)",
        contact = @Contact(
            email = "drizhiloda@gmail.com"
        ),
        title = "Rest infomodule api"
    )
)
@Profile("dev")
public class DevApiDefinition {
}
