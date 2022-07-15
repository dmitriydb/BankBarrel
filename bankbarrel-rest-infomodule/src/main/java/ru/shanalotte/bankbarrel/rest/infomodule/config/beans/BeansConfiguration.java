package ru.shanalotte.bankbarrel.rest.infomodule.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.service.EnumToCodeAndValuePairConverter;

@Configuration
public class BeansConfiguration {

  @Bean
  public EnumToCodeAndValuePairConverter enumToListingDtoItemConverter() {
    return new EnumToCodeAndValuePairConverter();
  }

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
