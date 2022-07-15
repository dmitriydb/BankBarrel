package ru.shanalotte.bankbarrel.webapp.service;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.shanalotte.bankbarrel.core.dto.CodeAndValuesPairsListWrapper;
import ru.shanalotte.bankbarrel.webapp.service.listing.AccountOpeningCurrenciesListingService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:mockCurrencies.properties")
public class AccountOpeningCurrenciesListingServiceTest {

  @Autowired
  private AccountOpeningCurrenciesListingService accountOpeningCurrenciesListingService;

  @Test
  public void shouldLoadSupportedCurrenciesFromProperties() {
     CodeAndValuesPairsListWrapper dto = accountOpeningCurrenciesListingService.getListingDto();
     assertThat(dto.getItems().size()).isEqualTo(1);
     assertThat(dto.getItems().get(0).getCode()).isEqualTo("KZT");
  }

}