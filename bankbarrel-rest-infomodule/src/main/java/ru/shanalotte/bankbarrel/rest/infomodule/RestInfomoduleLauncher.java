package ru.shanalotte.bankbarrel.rest.infomodule;

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
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class RestInfomoduleLauncher {

  public static void main(String[] args) {
    SpringApplication.run(RestInfomoduleLauncher.class, args);
  }


}
