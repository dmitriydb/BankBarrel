package ru.shanalotte.bankbarrel.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * REST-микросервис справочной информации
 * В данный момент используется для получения json-ов с информацией о существующих
 * типах банковских счетов.
 */
@SpringBootApplication
@ComponentScan("ru.shanalotte.bankbarrel.rest.infomodule")
@ComponentScan("ru.shanalotte.bankbarrel.rest.infomodule.repository")
@EnableScheduling
public class RestInfomoduleLauncher {
  public static void main(String[] args) {
    SpringApplication.run(RestInfomoduleLauncher.class, args);
  }



}
