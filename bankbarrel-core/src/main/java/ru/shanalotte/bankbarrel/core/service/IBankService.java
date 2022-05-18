package ru.shanalotte.bankbarrel.core.service;

import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;

public interface IBankService {

  void deposit(BankAccount account, MonetaryAmount amount) throws UnknownCurrencyRate;

  void withdraw(BankAccount account, MonetaryAmount amount) throws InsufficientFundsException, UnknownCurrencyRate;

  void transfer(BankAccount from, BankAccount to, MonetaryAmount amount)
      throws InsufficientFundsException, UnknownCurrencyRate;


}
