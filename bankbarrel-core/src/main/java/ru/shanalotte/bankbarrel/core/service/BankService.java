package ru.shanalotte.bankbarrel.core.service;

import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRateForRequestedCurrency;


@SuppressWarnings("checkstyle:MissingJavadocType")
public interface BankService {

  void deposit(BankAccount account, MonetaryAmount amount)
      throws UnknownCurrencyRateForRequestedCurrency;

  void withdraw(BankAccount account, MonetaryAmount amount)
      throws InsufficientFundsException, UnknownCurrencyRateForRequestedCurrency;

  void transfer(BankAccount from, BankAccount to, MonetaryAmount amount)
      throws InsufficientFundsException, UnknownCurrencyRateForRequestedCurrency;
}
