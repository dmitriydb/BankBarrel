package ru.shanalotte.bankbarrel.webapp.service;

import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;

/**
 * Интерфейс делегата к банковскому сервису.
 * Поддерживает те же операции, что и обычный BankService.
 */
public interface WebAppBankService {
  void deposit(BankAccountDto account, MonetaryAmount amount);

  void withdraw(BankAccountDto account, MonetaryAmount amount)
      throws InsufficientFundsException, UnknownCurrencyRate;

  void transfer(BankAccountDto from, BankAccountDto to, MonetaryAmount amount)
      throws InsufficientFundsException, UnknownCurrencyRate;
}
