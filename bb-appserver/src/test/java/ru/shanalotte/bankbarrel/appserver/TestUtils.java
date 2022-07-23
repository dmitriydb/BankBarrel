package ru.shanalotte.bankbarrel.appserver;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import io.swagger.v3.oas.annotations.Operation;
import org.checkerframework.checker.units.qual.A;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.appserver.domain.AccountOpening;
import ru.shanalotte.bankbarrel.appserver.domain.AccountOpeningHistory;
import ru.shanalotte.bankbarrel.appserver.domain.AccountOpeningStatus;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountAdditionalTypeEntity;
import ru.shanalotte.bankbarrel.appserver.domain.BankAccountTypeEntity;
import ru.shanalotte.bankbarrel.appserver.domain.Currency;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyDeposit;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyDepositHistory;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyDepositStatus;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyTransfer;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyTransferHistory;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyTransferStatus;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyWithdraw;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyWithdrawHistory;
import ru.shanalotte.bankbarrel.appserver.domain.MoneyWithdrawStatus;
import ru.shanalotte.bankbarrel.appserver.domain.OperationSource;
import ru.shanalotte.bankbarrel.appserver.repository.AccountOpeningRepository;
import ru.shanalotte.bankbarrel.appserver.repository.AccountOpeningStatusDao;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountAdditionalTypeDao;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountDao;
import ru.shanalotte.bankbarrel.appserver.repository.BankAccountTypeDao;
import ru.shanalotte.bankbarrel.appserver.repository.BankClientDao;
import ru.shanalotte.bankbarrel.appserver.repository.CurrencyDao;
import ru.shanalotte.bankbarrel.appserver.repository.DepositDao;
import ru.shanalotte.bankbarrel.appserver.repository.MoneyDepositHistoryDao;
import ru.shanalotte.bankbarrel.appserver.repository.MoneyDepositStatusDao;
import ru.shanalotte.bankbarrel.appserver.repository.MoneyTransferHistoryDao;
import ru.shanalotte.bankbarrel.appserver.repository.MoneyTransferStatusDao;
import ru.shanalotte.bankbarrel.appserver.repository.MoneyWithdrawHistoryDao;
import ru.shanalotte.bankbarrel.appserver.repository.MoneyWithdrawStatusDao;
import ru.shanalotte.bankbarrel.appserver.repository.OperationSourceDao;
import ru.shanalotte.bankbarrel.appserver.repository.TransferDao;
import ru.shanalotte.bankbarrel.appserver.repository.WithdrawDao;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;
import ru.shanalotte.bankbarrel.core.domain.BankClient;

@Service
public class TestUtils {

  @Autowired
  private CurrencyDao currencyDao;

  @Autowired
  private BankClientDao bankClientDao;

  @Autowired
  private BankAccountTypeDao bankAccountTypeDao;

  @Autowired
  private BankAccountAdditionalTypeDao bankAccountAdditionalTypeDao;

  @Autowired
  private OperationSourceDao operationSourceDao;

  @Autowired
  private AccountOpeningRepository accountOpeningRepository;

  @Autowired
  private AccountOpeningStatusDao accountOpeningStatusDao;

  @Autowired
  private BankAccountDao bankAccountDao;

  @Autowired
  private MoneyDepositHistoryDao moneyDepositHistoryDao;

  @Autowired
  private MoneyDepositStatusDao moneyDepositStatusDao;

  @Autowired
  private DepositDao depositDao;

  @Autowired
  private TransferDao transferDao;

  @Autowired
  private WithdrawDao withdrawDao;

  @Autowired
  private MoneyWithdrawHistoryDao moneyWithdrawHistoryDao;

  @Autowired
  private MoneyWithdrawStatusDao moneyWithdrawStatusDao;

  @Autowired
  private MoneyTransferHistoryDao moneyTransferHistoryDao;

  @Autowired
  private MoneyTransferStatusDao moneyTransferStatusDao;

  public Currency generateAndPersistRandomCurrency() {
    Currency currency = generateRandomCurrency();
    currencyDao.save(currency);
    return currency;
  }

  public String randomValue() {
    return UUID.randomUUID().toString().substring(0, 10);
  }

  public BankClient generateAndPersistRandomClient() {
    BankClient bankClient = new BankClient();
    bankClient.setTelephone(randomValue());
    bankClient.setGivenName(randomValue());
    bankClient.setFamilyName(randomValue());
    bankClient.setEmail(randomValue());
    bankClientDao.save(bankClient);
    return bankClient;
  }

  public BankAccountTypeEntity generateAndPersistRandomAccountType() {
    BankAccountTypeEntity bankAccountType = generateRandomAccountType();
    bankAccountTypeDao.save(bankAccountType);
    return bankAccountType;
  }

  public BankAccountAdditionalTypeEntity generateAndPersistRandomAdditionalAccountType() {
    BankAccountAdditionalTypeEntity bankAccountAdditionalTypeEntity = generateRandomAdditionalAccountType();
    bankAccountAdditionalTypeDao.save(bankAccountAdditionalTypeEntity);
    return bankAccountAdditionalTypeEntity;
  }

  public BankAccountAdditionalTypeEntity generateRandomAdditionalAccountType() {
    BankAccountAdditionalTypeEntity bankAccountAdditionalTypeEntity = new BankAccountAdditionalTypeEntity();
    bankAccountAdditionalTypeEntity.setDescription(randomValue());
    bankAccountAdditionalTypeEntity.setCode(randomValue());
    bankAccountAdditionalTypeEntity.setOwnerType(generateAndPersistRandomAccountType());
    return bankAccountAdditionalTypeEntity;
  }

  public AccountOpening generateRandomAccountOpening() {
    AccountOpening accountOpening = new AccountOpening();
    Currency currency = generateAndPersistRandomCurrency();
    BankClient client = generateAndPersistRandomClient();
    accountOpening.setCurrency(currency);
    accountOpening.setClient(client);
    accountOpening.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
    accountOpening.setAdditionalType(generateAndPersistRandomAdditionalAccountType());
    accountOpening.setType(accountOpening.getAdditionalType().getOwnerType());
    accountOpening.setOperationSource(generateAndPersistRandomOperationSource());
    return accountOpening;
  }

  public AccountOpening generateAndPersistRandomAccountOpening() {
    AccountOpening accountOpening = generateRandomAccountOpening();
    accountOpeningRepository.save(accountOpening);
    return accountOpening;
  }

  private OperationSource generateAndPersistRandomOperationSource() {
    OperationSource operationSource = generateRandomOperationSource();
    operationSourceDao.save(operationSource);
    return operationSource;
  }

  public AccountOpeningHistory generateRandomAccountOpeningHistory() {
    AccountOpeningHistory accountOpeningHistory = new AccountOpeningHistory();
    accountOpeningHistory.setAccountOpening(generateAndPersistRandomAccountOpening());
    accountOpeningHistory.setStatus(generateAndPersistAccountOpeningStatus());
    accountOpeningHistory.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
    return accountOpeningHistory;
  }

  private AccountOpeningStatus generateAndPersistAccountOpeningStatus() {
    AccountOpeningStatus accountOpeningStatus = new AccountOpeningStatus();
    accountOpeningStatus.setStatus(randomValue());
    accountOpeningStatusDao.save(accountOpeningStatus);
    return accountOpeningStatus;
  }

  public AccountOpeningStatus generateRandomAccountOpeningStatus() {
    AccountOpeningStatus accountOpeningStatus = new AccountOpeningStatus();
    accountOpeningStatus.setStatus(randomValue());
    return accountOpeningStatus;
  }

  public BankAccountTypeEntity generateRandomAccountType() {
    BankAccountTypeEntity bankAccountType = new BankAccountTypeEntity();
    bankAccountType.setCode(randomValue());
    bankAccountType.setDescription(randomValue());
    return bankAccountType;
  }

  public Currency generateRandomCurrency() {
    Currency currency = new Currency();
    currency.setCode(randomValue());
    return currency;
  }

  public OperationSource generateRandomOperationSource() {
    OperationSource operationSource = new OperationSource();
    operationSource.setName(randomValue());
    return operationSource;
  }

  private BankAccount generateAndPersistRandomBankAccount() {
    BankAccount bankAccount = new BankAccount();
    bankAccount.setOwner(generateAndPersistRandomClient());
    bankAccount.setCurrency(generateAndPersistRandomCurrency().getCode());
    bankAccount.setDescription(randomValue());
    bankAccount.setValue(BigDecimal.ONE);
    bankAccount.setNumber(randomValue());
    bankAccount.setIdentifier(UUID.randomUUID().toString());
    BankAccountAdditionalTypeEntity additionalType = generateAndPersistRandomAdditionalAccountType();
    bankAccount.setAdditionalType(additionalType.getCode());
    bankAccount.setBankAccountType(additionalType.getOwnerType().getCode());
    bankAccountDao.save(bankAccount);
    return bankAccount;
  }

  public MoneyDeposit generateRandomMoneyDeposit() {
    MoneyDeposit moneyDeposit = new MoneyDeposit();
    moneyDeposit.setAmount(BigDecimal.ONE);
    moneyDeposit.setOperationSource(generateAndPersistRandomOperationSource());
    moneyDeposit.setCurrency(generateAndPersistRandomCurrency());
    moneyDeposit.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
    moneyDeposit.setAccount(generateAndPersistRandomBankAccount());
    return moneyDeposit;
  }

  public MoneyDeposit generateAndPersistRandomMoneyDeposit() {
    MoneyDeposit moneyDeposit = generateRandomMoneyDeposit();
    depositDao.save(moneyDeposit);
    return moneyDeposit;
  }

  public MoneyDepositHistory generateRandomMoneyDepositHistory() {
    MoneyDepositHistory moneyDepositHistory = new MoneyDepositHistory();
    moneyDepositHistory.setMoneyDeposit(generateAndPersistRandomMoneyDeposit());
    moneyDepositHistory.setStatus(generateAndPersistMoneyDepositStatus());
    moneyDepositHistory.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
    return moneyDepositHistory;
  }

  private MoneyDepositStatus generateAndPersistMoneyDepositStatus() {
    MoneyDepositStatus moneyDepositStatus = new MoneyDepositStatus();
    moneyDepositStatus.setStatus(randomValue());
    moneyDepositStatusDao.save(moneyDepositStatus);
    return moneyDepositStatus;
  }

  public MoneyDepositStatus generateRandomMoneyDepositStatus() {
    MoneyDepositStatus moneyDepositStatus = new MoneyDepositStatus();
    moneyDepositStatus.setStatus(randomValue());
    return moneyDepositStatus;
  }

  //

  public MoneyWithdraw generateRandomMoneyWithdraw() {
    MoneyWithdraw moneyWithdraw = new MoneyWithdraw();
    moneyWithdraw.setAmount(BigDecimal.ONE);
    moneyWithdraw.setOperationSource(generateAndPersistRandomOperationSource());
    moneyWithdraw.setCurrency(generateAndPersistRandomCurrency());
    moneyWithdraw.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
    moneyWithdraw.setAccount(generateAndPersistRandomBankAccount());
    return moneyWithdraw;
  }

  public MoneyWithdraw generateAndPersistRandomMoneyWithdraw() {
    MoneyWithdraw moneyWithdraw = generateRandomMoneyWithdraw();
    withdrawDao.save(moneyWithdraw);
    return moneyWithdraw;
  }

  public MoneyWithdrawHistory generateRandomMoneyWithdrawHistory() {
    MoneyWithdrawHistory moneyWithdrawHistory = new MoneyWithdrawHistory();
    moneyWithdrawHistory.setMoneyWithdraw(generateAndPersistRandomMoneyWithdraw());
    moneyWithdrawHistory.setStatus(generateAndPersistMoneyWithdrawStatus());
    moneyWithdrawHistory.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
    return moneyWithdrawHistory;
  }

  private MoneyWithdrawStatus generateAndPersistMoneyWithdrawStatus() {
    MoneyWithdrawStatus moneyWithdrawStatus = new MoneyWithdrawStatus();
    moneyWithdrawStatus.setStatus(randomValue());
    moneyWithdrawStatusDao.save(moneyWithdrawStatus);
    return moneyWithdrawStatus;
  }

  public MoneyWithdrawStatus generateRandomMoneyWithdrawStatus() {
    MoneyWithdrawStatus moneyWithdrawStatus = new MoneyWithdrawStatus();
    moneyWithdrawStatus.setStatus(randomValue());
    return moneyWithdrawStatus;
  }

  //

  public MoneyTransfer generateRandomMoneyTransfer() {
    MoneyTransfer moneyTransfer = new MoneyTransfer();
    moneyTransfer.setAmount(BigDecimal.ONE);
    moneyTransfer.setOperationSource(generateAndPersistRandomOperationSource());
    moneyTransfer.setCurrency(generateAndPersistRandomCurrency());
    moneyTransfer.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
    moneyTransfer.setToAccount(generateAndPersistRandomBankAccount());
    moneyTransfer.setFromAccount(generateAndPersistRandomBankAccount());
    return moneyTransfer;
  }

  public MoneyTransfer generateAndPersistRandomMoneyTransfer() {
    MoneyTransfer moneyTransfer = generateRandomMoneyTransfer();
    transferDao.save(moneyTransfer);
    return moneyTransfer;
  }

  public MoneyTransferHistory generateRandomMoneyTransferHistory() {
    MoneyTransferHistory moneyTransferHistory = new MoneyTransferHistory();
    moneyTransferHistory.setMoneyTransfer(generateAndPersistRandomMoneyTransfer());
    moneyTransferHistory.setStatus(generateAndPersistMoneyTransferStatus());
    moneyTransferHistory.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
    return moneyTransferHistory;
  }

  private MoneyTransferStatus generateAndPersistMoneyTransferStatus() {
    MoneyTransferStatus moneyTransferStatus = new MoneyTransferStatus();
    moneyTransferStatus.setStatus(randomValue());
    moneyTransferStatusDao.save(moneyTransferStatus);
    return moneyTransferStatus;
  }

  public MoneyTransferStatus generateRandomMoneyTransferStatus() {
    MoneyTransferStatus moneyTransferStatus = new MoneyTransferStatus();
    moneyTransferStatus.setStatus(randomValue());
    return moneyTransferStatus;
  }


}
