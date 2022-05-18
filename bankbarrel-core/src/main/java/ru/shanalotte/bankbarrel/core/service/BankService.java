package ru.shanalotte.bankbarrel.core.service;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.exception.CurrencyNotFoundException;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;

/**
 * Main class that performs common banking operations with banking accounts,
 * such as deposit, withdraw, transfer etc.
 */
@Service
public class BankService implements IBankService {

  @Autowired
  private CurrencyRateService currencyRateService;

  @Autowired
  private CurrencyConverterService currencyConverterService;


  private String defaultMonetaryAmountCurrency;

  /**
   * Конструктор со всеми зависимостями.
   *
   * @param currencyRateService сервис, который хранит курсы валют
   * @param currencyConverterService сервис, который конвертирует валюту
   * @param defaultMonetaryAmountCurrency валюта, используемая в денежных обменах по умолчанию
   */
  public BankService(CurrencyRateService currencyRateService,
                     CurrencyConverterService currencyConverterService,
                     @Value("${bank.monetaryAmount.defaultCurrency}")
                         String defaultMonetaryAmountCurrency) {
    this.currencyRateService = currencyRateService;
    this.currencyConverterService = currencyConverterService;
    this.defaultMonetaryAmountCurrency = defaultMonetaryAmountCurrency;
  }

  /**
   * Deposits the monetary amount to the account.
   * Converts the monetary value amount to the account's currency.
   *
   * @param account account to deposit money
   * @param amount monetary amount of the deposit
   *
   * @throws UnknownCurrencyRate if the amount currency rate is not known
   */
  public void deposit(BankAccount account, MonetaryAmount amount) throws UnknownCurrencyRate {
    try {
      //account value to default currency
      String currency = account.getCurrency();
      BigDecimal value = account.getValue();
      BigDecimal accountConvertedValue =
          currencyConverterService.convertToDefaultCurrency(currencyRateService, currency, value);
      //amount value to default currency
      String amountCurrency = amount.getCurrency();
      BigDecimal amountValue = amount.getValue();
      BigDecimal amountConvertedValue = currencyConverterService
          .convertToDefaultCurrency(currencyRateService, amountCurrency, amountValue);
      //adding
      BigDecimal newValue = accountConvertedValue.add(amountConvertedValue);
      //new value to account currency
      BigDecimal newAccountValue = currencyConverterService
          .convertFromDefaultCurrency(currencyRateService, account.getCurrency(), newValue);
      account.setValue(newAccountValue);
    } catch (CurrencyNotFoundException exception) {
      throw new UnknownCurrencyRate(amount.getCurrency());
    }
  }

  /**
   * Withdraws money from the banking account.
   * Can perform withdrawing with different currencies (e.g withdrawing rubles from USD account).
   *
   * @param account account to withdraw money
   * @param amount money amount to withdraw
   * @throws InsufficientFundsException whem there is not enough money at the account
   * @throws UnknownCurrencyRate if the amount currency rate is not known
   */
  public void withdraw(BankAccount account, MonetaryAmount amount)
      throws InsufficientFundsException, UnknownCurrencyRate {
    try {
      //account value in default currency
      String currency = account.getCurrency();
      BigDecimal value = account.getValue();
      BigDecimal accountConvertedValue = currencyConverterService
          .convertToDefaultCurrency(currencyRateService, currency, value);
      //amount value to default currency
      String amountCurrency = amount.getCurrency();
      BigDecimal amountValue = amount.getValue();
      BigDecimal amountConvertedValue = currencyConverterService
          .convertToDefaultCurrency(currencyRateService, amountCurrency, amountValue);
      //substracting

      if (accountConvertedValue.compareTo(amountConvertedValue) < 0) {
        throw new InsufficientFundsException(account.toString() + " " + amount.toString());
      }
      BigDecimal newValue = accountConvertedValue.subtract(amountConvertedValue);
      //new value to account currency
      BigDecimal newAccountValue = currencyConverterService
          .convertFromDefaultCurrency(currencyRateService, account.getCurrency(), newValue);
      account.setValue(newAccountValue);
    } catch (CurrencyNotFoundException exception) {
      throw new UnknownCurrencyRate(amount.getCurrency());
    }
  }

  public void transfer(BankAccount from, BankAccount to, MonetaryAmount amount)
      throws InsufficientFundsException, UnknownCurrencyRate {
    withdraw(from, amount);
    deposit(to, amount);
  }

}
