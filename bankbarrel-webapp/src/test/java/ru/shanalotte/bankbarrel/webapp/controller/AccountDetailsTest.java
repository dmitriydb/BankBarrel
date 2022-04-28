package ru.shanalotte.bankbarrel.webapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.webapp.dao.WebAppUserDao;
import ru.shanalotte.bankbarrel.webapp.dto.AccountOpeningDto;
import ru.shanalotte.bankbarrel.webapp.dto.BankAccountDetailsDto;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountCreationService;
import ru.shanalotte.bankbarrel.webapp.service.BankAccountDetailsDtoConverter;
import ru.shanalotte.bankbarrel.webapp.service.CurrencyPresentationConverter;
import ru.shanalotte.bankbarrel.webapp.testutils.TestDtoFactory;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountDetailsTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EnrollingHelper enrollingHelper;

  @Autowired
  private BankAccountCreationService bankAccountCreationService;

  @Autowired
  private WebAppUserDao webAppUserDao;

  @Autowired
  private BankAccountDetailsDtoConverter bankAccountDetailsDtoConverter;

  @Autowired
  private CurrencyPresentationConverter currencyPresentationConverter;

  @Test
  public void shouldThrow404WhenUserIsAuthorizedButThereIsNotSuchAccount() throws Exception {
    enrollingHelper.enrollTestUser();
    mockMvc.perform(MockMvcRequestBuilders.get("/user/testuser/account/idontexts"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void shouldThrow403WhenTryingToSeeAnothersUserAccountInfo() throws Exception {
    enrollingHelper.enrollUser("user1216-1");
    enrollingHelper.enrollUser("user1216-2");
    BankClient client1 = webAppUserDao.findByUsername("user1216-1").getClient();
    BankClient client2 = webAppUserDao.findByUsername("user1216-2").getClient();
    bankAccountCreationService.createAccount(TestDtoFactory.accountOpeningDto(), client1);
    bankAccountCreationService.createAccount(TestDtoFactory.accountOpeningDto(), client2);
    String unathorizedAccountNumber = client2.getAccounts().iterator().next().getIdentifier();
    mockMvc.perform(MockMvcRequestBuilders.get("/user/user1216-1/account/" + unathorizedAccountNumber))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  public void testingAccountDetails() throws Exception {
    enrollingHelper.enrollUser("user1220-1");
    BankClient client = webAppUserDao.findByUsername("user1220-1").getClient();
    AccountOpeningDto dto = TestDtoFactory.accountOpeningDto();
    bankAccountCreationService.createAccount(dto, client);
    String accountNumber = client.getAccounts().iterator()
        .next().getIdentifier();
    BankAccountDetailsDto expectedDto = bankAccountDetailsDtoConverter.convert(client.getAccounts().iterator().next());
    mockMvc.perform(MockMvcRequestBuilders.get("/user/user1220-1/account/" + accountNumber))
        .andExpect(MockMvcResultMatchers.model().attributeExists("account"))
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedDto.getAdditionalType())))
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedDto.getCurrency())))
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedDto.getBalance())))
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedDto.getDescription())))
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedDto.getCurrencySign())))
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedDto.getNumber())))
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(expectedDto.getType())));
  }

  @Test
  public void freshlyCreatedAccountShowZeroBalance() throws Exception {
    enrollingHelper.enrollUser("user1248");
    BankClient client = webAppUserDao.findByUsername("user1248").getClient();
    AccountOpeningDto dto = TestDtoFactory.accountOpeningDto();
    bankAccountCreationService.createAccount(dto, client);
    String accountNumber = client.getAccounts().iterator()
        .next().getIdentifier();
    BankAccountDetailsDto expectedDto = bankAccountDetailsDtoConverter.convert(client.getAccounts().iterator().next());
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/user1248/account/" + accountNumber))
        .andReturn();
    BankAccountDetailsDto actualDto = (BankAccountDetailsDto) result.getModelAndView().getModel().get("account");
    String actualBalance = actualDto.getBalance();
    assertThat(actualBalance).isEqualTo("0.00");
  }

  @Test
  public void shouldShowBankAccountCurrencyInTheSameCurrencyAndAccount() throws Exception {
    enrollingHelper.enrollUser("user1252");
    BankClient client = webAppUserDao.findByUsername("user1252").getClient();
    AccountOpeningDto dto = TestDtoFactory.accountOpeningDto();
    dto.setCurrency("KZT");
    bankAccountCreationService.createAccount(dto, client);
    BankAccount account = client.getAccounts().iterator().next();
    String accountNumber = account.getIdentifier();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/user1252/account/" + accountNumber))
        .andReturn();
    BankAccountDetailsDto actualDto = (BankAccountDetailsDto) result.getModelAndView().getModel().get("account");
    String currencySign = actualDto.getCurrencySign();
    assertThat(currencySign).isEqualTo(currencyPresentationConverter.currencyToSign(account.getCurrency()));
  }
}
