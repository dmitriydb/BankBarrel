package ru.shanalotte.bankbarrel.webapp.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRateForRequestedCurrency;

/**
 * Заглушка банковского сервиса для тестирования.
 */
@Service
@Profile({"test"})
public class TestWebAppBankService implements WebAppBankService {



  public void deposit(BankAccountDto account, MonetaryAmount amount) {

  }

  public void withdraw(BankAccountDto account, MonetaryAmount amount)
      throws InsufficientFundsException, UnknownCurrencyRateForRequestedCurrency {

  }

  public void transfer(BankAccountDto from, BankAccountDto to, MonetaryAmount amount)
      throws InsufficientFundsException, UnknownCurrencyRateForRequestedCurrency {

  }
}
