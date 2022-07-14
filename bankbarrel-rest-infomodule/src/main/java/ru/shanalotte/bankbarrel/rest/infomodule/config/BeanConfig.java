package ru.shanalotte.bankbarrel.rest.infomodule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.shanalotte.bankbarrel.core.service.EnumToCodeAndValuePairConverter;

/**
 * Java-конфигурация контейнера спринга для данного микросервиса.
 * Используется, чтобы не тащить все бины из модуля bankbarrel-core.
 */
@Configuration
public class BeanConfig {

  @Bean
  public EnumToCodeAndValuePairConverter enumToListingDtoItemConverter() {
    return new EnumToCodeAndValuePairConverter();
  }
}
