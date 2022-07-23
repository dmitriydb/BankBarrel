package ru.shanalotte.bankbarrel.webapp.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankClientDao;

/**
 * Заглушка для DAO клиентов банка. Хранит всё в памяти.
 */
@Repository
@Profile({"test", "dev"})
public class NaiveBankClientDao implements BankClientDao {

  private static long NEXT_ID = 0;
  private Set<BankClientDto> bankClients = new HashSet<>();
  private AccountHolder accountHolder;

  public NaiveBankClientDao(AccountHolder accountHolder) {
    this.accountHolder = accountHolder;
  }

  @Override
  public int count() {
    return bankClients.size();
  }

  @Override
  public void save(BankClientDto newBankClient) {
    newBankClient.setId(++NEXT_ID);
    bankClients.add(newBankClient);
    accountHolder.getAccounts().put(newBankClient, new ArrayList<>());
  }

  @Override
  public BankClientDto findByGivenName(String givenName) {
    return bankClients.stream().filter(e -> e.getGivenName().equals(givenName)).findFirst().get();
  }

  @Override
  public List<BankAccountDto> accounts(BankClientDto clientDto) {
    List<BankAccountDto> accountDtos = accountHolder.getAccounts().get(clientDto);
    if (accountDtos == null) {
      return new ArrayList<>();
    } else {
      return accountDtos;
    }
  }

  @Override
  public Long idByDto(BankClientDto dto) {
    Optional<BankClientDto> result = bankClients.stream().filter(e -> e.equals(dto)).findFirst();
    return result.map(BankClientDto::getId).orElse(null);
  }
}
