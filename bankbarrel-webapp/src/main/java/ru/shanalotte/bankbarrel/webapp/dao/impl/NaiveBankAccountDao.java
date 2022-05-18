package ru.shanalotte.bankbarrel.webapp.dao.impl;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.config.FakeAccountNumberGenerator;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankAccountDao;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankClientDao;
import ru.shanalotte.bankbarrel.webapp.dto.account.AccountOpeningDto;
import ru.shanalotte.bankbarrel.webapp.dto.serviceregistry.RegisteredServiceInfo;
import ru.shanalotte.bankbarrel.webapp.dto.transfer.TransferDto;
import ru.shanalotte.bankbarrel.webapp.exception.BankAccountNotFound;

/**
 * Заглушка для DAO клиентов банка. Хранит всё в памяти.
 */
@Repository
@Profile("test")
public class NaiveBankAccountDao implements BankAccountDao {

  private Set<BankAccountDto> bankAccounts = new HashSet<>();

  private FakeAccountNumberGenerator fakeAccountNumberGenerator;
  private BankClientDao bankClientDao;
  private AccountHolder accountHolder;

  public NaiveBankAccountDao(FakeAccountNumberGenerator fakeAccountNumberGenerator, BankClientDao bankClientDao, AccountHolder accountHolder) {
    this.fakeAccountNumberGenerator = fakeAccountNumberGenerator;
    this.bankClientDao = bankClientDao;
    this.accountHolder = accountHolder;
  }

  @Override
  public void save(BankAccountDto account) {
    bankAccounts.add(account);
  }

  @Override
  public BankAccountDto findByNumber(String number) {
    return bankAccounts.stream().filter(e -> e.getNumber().equals(number)).findFirst().orElse(null);
  }

  @Override
  public void delete(BankAccountDto account) {
    BankAccountDto dto = bankAccounts.stream().filter(e -> e.getNumber().equals(account.getNumber())).findFirst().get();
    for (BankClientDto client : accountHolder.getAccounts().keySet()) {
      if (accountHolder.getAccounts().get(client).contains(account)) {
        accountHolder.getAccounts().get(client).remove(account);
      }
    }
    bankAccounts.remove(dto);
  }

  @Override
  public BankAccountDto findByTransferDto(TransferDto dto) throws BankAccountNotFound {
    return bankAccounts.stream().filter(e -> e.getNumber().equals(dto.getAccountNumber())).findFirst().get();
  }

  @Override
  public void createAccount(AccountOpeningDto dto, BankClientDto bankClient) {
    BankAccountDto bankAccount = new BankAccountDto();
    bankAccount.setOwner(bankClient.getId());
    bankAccount.setNumber(fakeAccountNumberGenerator.generateNumber());
    bankAccount.setDescription(bankAccount.getNumber());
    bankAccount.setCurrency(dto.getCurrency());
    bankAccount.setType(dto.getAccountType());
    bankAccount.setAdditionalType(dto.getAccountAdditionalType());
    accountHolder.getAccounts().get(bankClient).add(bankAccount);
    save(bankAccount);
  }
}
