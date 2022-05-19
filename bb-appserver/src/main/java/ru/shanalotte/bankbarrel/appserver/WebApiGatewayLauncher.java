package ru.shanalotte.bankbarrel.appserver;

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
@EnableScheduling
public class WebApiGatewayLauncher {
  public static void main(String[] args) {
    SpringApplication.run(WebApiGatewayLauncher.class, args);
  }
}
