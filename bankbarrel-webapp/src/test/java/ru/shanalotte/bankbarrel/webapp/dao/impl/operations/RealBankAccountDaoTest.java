package ru.shanalotte.bankbarrel.webapp.dao.impl.operations;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.impl.RealBankAccountDao;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceUrlBuilder;
import ru.shanalotte.bankbarrel.webapp.user.WebAppUser;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("production")
class RealBankAccountDaoTest {

  @MockBean
  ServiceUrlBuilder serviceUrlBuilder;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private RealBankAccountDao realBankAccountDao;

  @MockBean
  private RestTemplate restTemplate;

  @Test
  public void savingBankAccount() {
    when(serviceUrlBuilder.buildServiceUrl(any())).thenReturn("");
    BankAccountDto bankAccountDto = new BankAccountDto();
    realBankAccountDao.save(bankAccountDto);
    verify(restTemplate).postForEntity(any(), any(), any());
  }


}