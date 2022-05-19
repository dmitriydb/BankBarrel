package ru.shanalotte.bankbarrel.core.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SimpleBankService implements BankService {

  private static final Logger logger = LoggerFactory.getLogger(SimpleBankService.class);

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
  public SimpleBankService(CurrencyRateService currencyRateService,
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
      logger.debug("Trying to deposit {} {} to account {}",
          amount.getValue(), amount.getCurrency(), account.getNumber());
      //account value to default currency
      String currency = account.getCurrency();
      BigDecimal value = account.getValue();
      BigDecimal accountConvertedValue =
          currencyConverterService.convertToDefaultCurrency(currencyRateService, currency, value);
      logger.debug("Account initial balance = {} {}", account.getValue().setScale(
          2, RoundingMode.HALF_UP),
          account.getCurrency());
      logger.debug("{} {} = {} {}", account.getValue().setScale(
              2, RoundingMode.HALF_UP),
          account.getCurrency(), accountConvertedValue.setScale(2, RoundingMode.HALF_UP),
          defaultMonetaryAmountCurrency);
      //amount value to default currency
      String amountCurrency = amount.getCurrency();
      BigDecimal amountValue = amount.getValue();
      BigDecimal amountConvertedValue = currencyConverterService
          .convertToDefaultCurrency(currencyRateService, amountCurrency, amountValue);
      logger.debug("{} {} = {} {}", amount.getValue().setScale(
              2, RoundingMode.HALF_UP),
          amount.getCurrency(), amountConvertedValue.setScale(2, RoundingMode.HALF_UP),
          defaultMonetaryAmountCurrency);
      //adding
      BigDecimal newValue = accountConvertedValue.add(amountConvertedValue);
      //new value to account currency
      BigDecimal newAccountValue = currencyConverterService
          .convertFromDefaultCurrency(currencyRateService, account.getCurrency(), newValue);
      account.setValue(newAccountValue);
      logger.debug("Account balance after deposit = {} {}", account.getValue()
          .setScale(2, RoundingMode.HALF_UP), account.getCurrency());
    } catch (CurrencyNotFoundException exception) {
      logger.error("Currency {} not found", amount.getCurrency());
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
      logger.debug("Trying to withdraw {} {} from account {}",
          amount.getValue(), amount.getCurrency(), account.getNumber());
      //account value in default currency
      String currency = account.getCurrency();
      BigDecimal value = account.getValue();
      BigDecimal accountConvertedValue = currencyConverterService
          .convertToDefaultCurrency(currencyRateService, currency, value);
      //amount value to default currency
      logger.debug("Account initial balance = {} {}", account.getValue().setScale(
              2, RoundingMode.HALF_UP),
          account.getCurrency());
      logger.debug("{} {} = {} {}", account.getValue().setScale(
              2, RoundingMode.HALF_UP),
          account.getCurrency(), accountConvertedValue.setScale(2, RoundingMode.HALF_UP),
          defaultMonetaryAmountCurrency);
      String amountCurrency = amount.getCurrency();
      BigDecimal amountValue = amount.getValue();
      BigDecimal amountConvertedValue = currencyConverterService
          .convertToDefaultCurrency(currencyRateService, amountCurrency, amountValue);
      logger.debug("{} {} = {} {}", amount.getValue().setScale(
              2, RoundingMode.HALF_UP),
          amount.getCurrency(), amountConvertedValue.setScale(2, RoundingMode.HALF_UP),
          defaultMonetaryAmountCurrency);
      //substracting

      if (accountConvertedValue.compareTo(amountConvertedValue) < 0) {
        logger.error("Not sufficients funds (expected {} actual {})",
            amountConvertedValue.setScale(2, RoundingMode.HALF_UP),
            accountConvertedValue.setScale(2, RoundingMode.HALF_UP)
        );
        throw new InsufficientFundsException(account + " " + amount);
      }
      BigDecimal newValue = accountConvertedValue.subtract(amountConvertedValue);
      //new value to account currency
      BigDecimal newAccountValue = currencyConverterService
          .convertFromDefaultCurrency(currencyRateService, account.getCurrency(), newValue);
      account.setValue(newAccountValue);
      logger.debug("Account balance after withdraw = {} {}", account.getValue()
          .setScale(2, RoundingMode.HALF_UP), account.getCurrency());
    } catch (CurrencyNotFoundException exception) {
      logger.error("Currency {} not found", amount.getCurrency());
      throw new UnknownCurrencyRate(amount.getCurrency());
    }
  }

  public void transfer(BankAccount from, BankAccount to, MonetaryAmount amount)
      throws InsufficientFundsException, UnknownCurrencyRate {
    withdraw(from, amount);
    deposit(to, amount);
  }

}
