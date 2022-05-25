package ru.shanalotte.bankbarrel.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ru.shanalotte.bankbarrel.monitoring")
public class WebAppMonitoringServiceLauncher {

  public static void main(String[] args) {
    SpringApplication.run(WebAppMonitoringServiceLauncher.class, args);
  }
}
