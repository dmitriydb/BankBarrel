package ru.shanalotte.bankbarrel.webapp.service;

import java.net.URI;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.DepositDto;
import ru.shanalotte.bankbarrel.core.dto.TransferDto;
import ru.shanalotte.bankbarrel.core.dto.WithdrawDto;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.IServiceUrlBuilder;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceRegistryProxy;

@Service
@Profile({"test"})
public class TestWebAppBankService implements IWebAppBankService {



  public void deposit(BankAccountDto account, MonetaryAmount amount) {

  }

  public void withdraw(BankAccountDto account, MonetaryAmount amount) throws InsufficientFundsException, UnknownCurrencyRate {

  }

  public void transfer(BankAccountDto from, BankAccountDto to, MonetaryAmount amount) throws InsufficientFundsException, UnknownCurrencyRate {

  }
}
