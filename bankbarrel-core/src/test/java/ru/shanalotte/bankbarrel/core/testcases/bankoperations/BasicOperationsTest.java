package ru.shanalotte.bankbarrel.core.testcases.bankoperations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.api.Test;
import ru.shanalotte.bankbarrel.core.service.DummyTestingEntitiesProvider;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRateForRequestedCurrency;
import ru.shanalotte.bankbarrel.core.service.SimpleBankService;


public class BasicOperationsTest {

  @Test
  public void deposit100DollarsToAccount() throws UnknownCurrencyRateForRequestedCurrency {
    SimpleBankService bankService = DummyTestingEntitiesProvider.createTestingSimpleBankService();
    BankAccount account = DummyTestingEntitiesProvider.createCheckingBankAccountDummy();
    bankService.deposit(account, new MonetaryAmount(100.0));
    assertThat(account.balance().doubleValue()).isEqualTo(100.0);
  }

  @Test
  public void newAccount_ShouldHaveZeroValue() {
    BankAccount account = DummyTestingEntitiesProvider.createCheckingBankAccountDummy();
    assertThat(account.balance().doubleValue()).isZero();
  }

  @Test
  public void monetaryAmountCurrencyIsUSDByDefault() {
    MonetaryAmount monetaryAmount = new MonetaryAmount(100);
    assertThat(monetaryAmount.getCurrency()).isEqualTo("USD");
  }
}
