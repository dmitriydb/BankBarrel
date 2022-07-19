package ru.shanalotte.bankbarrel.appserver.jpatest;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import net.bytebuddy.implementation.bytecode.Throw;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.shanalotte.bankbarrel.appserver.TestUtils;
import ru.shanalotte.bankbarrel.appserver.domain.AccountOpening;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountAdditionalTypeEntity;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountTypeEntity;
import ru.shanalotte.bankbarrel.appserver.domain.Currency;
import ru.shanalotte.bankbarrel.appserver.repository.AccountOpeningRepository;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyDao;
import ru.shanalotte.bankbarrel.core.domain.BankClient;

@SpringBootTest
public class AccountOpeningTest {

  @Autowired
  private AccountOpeningRepository accountOpeningRepository;

  @Autowired
  private TestUtils testUtils;

  private AccountOpening accountOpening;

  @BeforeEach
  public void refreshAccountOpening() {
    accountOpening = testUtils.generateRandomAccountOpening();
  }

  @Test
  public void accountOpeningShouldHaveClient() {
   accountOpening.setClient(null);
    assertThrows(Throwable.class, () -> {
      accountOpeningRepository.save(accountOpening);
    });
  }

  @Test
  public void accountOpeningShouldHaveTimestamp() {
    accountOpening.setTimestamp(null);
    assertThrows(Throwable.class, () -> {
      accountOpeningRepository.save(accountOpening);
    });
  }

  @Test
  public void accountOpeningShouldHaveCurrency() {
    accountOpening.setCurrency(null);
    assertThrows(Throwable.class, () -> {
      accountOpeningRepository.save(accountOpening);
    });
  }

  @Test
  public void accountOpeningShouldHaveAccountType() {
    accountOpening.setType(null);
    assertThrows(Throwable.class, () -> {
      accountOpeningRepository.save(accountOpening);
    });
  }

  @Test
  public void accountOpeningShouldHaveAdditionalType() {
    accountOpening.setAdditionalType(null);
    assertThrows(Throwable.class, () -> {
      accountOpeningRepository.save(accountOpening);
    });
  }

  @Test
  public void accountOpeningShouldHaveOperationSource() {
    accountOpening.setOperationSource(null);
    assertThrows(Throwable.class, () -> {
      accountOpeningRepository.save(accountOpening);
    });
  }

  @Test
  public void accountOpeningShouldNotHaveResult() {
    accountOpening.setResult(null);
    assertDoesNotThrow(() -> {
      accountOpeningRepository.save(accountOpening);
    });
  }

  @Test
  public void accountOpeningShouldNotHaveResultingAccountNumber() {
    accountOpening.setResultingAccount(null);
    assertDoesNotThrow(() -> {
      accountOpeningRepository.save(accountOpening);
    });
  }

  @Test
  public void successfulSaving() {
    assertDoesNotThrow(() -> {
      accountOpeningRepository.save(accountOpening);
    });
  }
}
