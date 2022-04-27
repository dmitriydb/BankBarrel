package ru.shanalotte.bankbarrel.rest.infomodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * REST-микросервис справочной информации
 * В данный момент используется для получения json-ов с информацией о существующих
 * типах банковских счетов.
 */
@SpringBootApplication
public class Launcher {

  public static void main(String[] args) {
    SpringApplication.run(Launcher.class, args);
  }
}
