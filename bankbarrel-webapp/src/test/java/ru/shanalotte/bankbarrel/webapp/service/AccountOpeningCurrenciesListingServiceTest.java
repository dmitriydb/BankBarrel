package ru.shanalotte.bankbarrel.webapp.service;


import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import ru.shanalotte.bankbarrel.webapp.dto.ListingDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:mockCurrencies.properties")
public class AccountOpeningCurrenciesListingServiceTest {

  @Autowired
  private AccountOpeningCurrenciesListingService accountOpeningCurrenciesListingService;

  @Test
  public void shouldLoadSupportedCurrenciesFromProperties() {
     ListingDto dto = accountOpeningCurrenciesListingService.getListingDto();
     assertThat(dto.getItems().size()).isEqualTo(1);
     assertThat(dto.getItems().get(0).getCode()).isEqualTo("KZT");
  }

}