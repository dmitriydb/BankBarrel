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
import ru.shanalotte.bankbarrel.core.exception.CurrencyNotRegisteredInSystemException;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRateForRequestedCurrency;

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
   * @param currencyRateService           сервис, который хранит курсы валют
   * @param currencyConverterService      сервис, который конвертирует валюту
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
   * @param amount  monetary amount of the deposit
   * @throws UnknownCurrencyRateForRequestedCurrency if the amount currency rate is not known
   */
  public void deposit(BankAccount account, MonetaryAmount amount)
      throws UnknownCurrencyRateForRequestedCurrency {
    try {
      logger.debug("Trying to deposit {} {} to account {}",
          amount.getValue(), amount.getCurrency(), account.getNumber());
      BigDecimal accountMoneyAmountInDefaultCurrency =
          currencyConverterService.convertToDefaultCurrency(
              currencyRateService, account.getCurrency(), account.getValue()
          );
      logger.debug("Account initial balance = {} {}", account.getValue().setScale(
              2, RoundingMode.HALF_UP),
          account.getCurrency());
      logger.debug("{} {} = {} {}", account.getValue().setScale(
              2, RoundingMode.HALF_UP),
          account.getCurrency(),
          accountMoneyAmountInDefaultCurrency.setScale(2, RoundingMode.HALF_UP),
          defaultMonetaryAmountCurrency);
      BigDecimal depositMoneyAmountInDefaultCurrency = currencyConverterService
          .convertToDefaultCurrency(currencyRateService, amount.getCurrency(), amount.getValue());
      logger.debug("{} {} = {} {}", amount.getValue().setScale(
              2, RoundingMode.HALF_UP),
          amount.getCurrency(),
          depositMoneyAmountInDefaultCurrency.setScale(2, RoundingMode.HALF_UP),
          defaultMonetaryAmountCurrency);
      BigDecimal moneyAmountAfterDeposit =
          accountMoneyAmountInDefaultCurrency.add(depositMoneyAmountInDefaultCurrency);
      BigDecimal accountMoneyAmountAfterDeposit = currencyConverterService
          .convertFromDefaultCurrency(currencyRateService, account.getCurrency(),
              moneyAmountAfterDeposit
          );
      account.setValue(accountMoneyAmountAfterDeposit);
      logger.debug("Account balance after deposit = {} {}", account.getValue()
          .setScale(2, RoundingMode.HALF_UP), account.getCurrency());
    } catch (CurrencyNotRegisteredInSystemException exception) {
      logger.error("Currency {} not found", amount.getCurrency(), exception);
      throw new UnknownCurrencyRateForRequestedCurrency(amount.getCurrency());
    }
  }

  /**
   * Withdraws money from the banking account.
   * Can perform withdrawing with different currencies (e.g withdrawing rubles from USD account).
   *
   * @param account account to withdraw money
   * @param amount  money amount to withdraw
   * @throws InsufficientFundsException              whem there is not enough money at the account
   * @throws UnknownCurrencyRateForRequestedCurrency if the amount currency rate is not known
   */
  public void withdraw(BankAccount account, MonetaryAmount amount)
      throws InsufficientFundsException, UnknownCurrencyRateForRequestedCurrency {
    try {
      logger.debug("Trying to withdraw {} {} from account {}",
          amount.getValue(), amount.getCurrency(), account.getNumber());
      BigDecimal accountMoneyAmountInDefaultCurrency = currencyConverterService
          .convertToDefaultCurrency(currencyRateService, account.getCurrency(), account.getValue());
      logger.debug("Account initial balance = {} {}", account.getValue().setScale(
              2, RoundingMode.HALF_UP),
          account.getCurrency());
      logger.debug("{} {} = {} {}", account.getValue().setScale(
              2, RoundingMode.HALF_UP),
          account.getCurrency(),
          accountMoneyAmountInDefaultCurrency.setScale(2, RoundingMode.HALF_UP),
          defaultMonetaryAmountCurrency);
      BigDecimal withdrawMoneyAmountInDefaultCurrency = currencyConverterService
          .convertToDefaultCurrency(currencyRateService, amount.getCurrency(), amount.getValue());
      logger.debug("{} {} = {} {}", amount.getValue().setScale(
              2, RoundingMode.HALF_UP),
          amount.getCurrency(),
          withdrawMoneyAmountInDefaultCurrency.setScale(2, RoundingMode.HALF_UP),
          defaultMonetaryAmountCurrency);
      if (accountMoneyAmountInDefaultCurrency.compareTo(withdrawMoneyAmountInDefaultCurrency) < 0) {
        logger.error("Not sufficients funds (expected {} actual {})",
            withdrawMoneyAmountInDefaultCurrency.setScale(2, RoundingMode.HALF_UP),
            accountMoneyAmountInDefaultCurrency.setScale(2, RoundingMode.HALF_UP)
        );
        throw new InsufficientFundsException(account + " " + amount);
      }
      BigDecimal moneyAmountAfterWithdraw =
          accountMoneyAmountInDefaultCurrency.subtract(withdrawMoneyAmountInDefaultCurrency);
      BigDecimal accountMoneyAmountAfterWithdraw = currencyConverterService
          .convertFromDefaultCurrency(
              currencyRateService, account.getCurrency(), moneyAmountAfterWithdraw
          );
      account.setValue(accountMoneyAmountAfterWithdraw);
      logger.debug("Account balance after withdraw = {} {}", account.getValue()
          .setScale(2, RoundingMode.HALF_UP), account.getCurrency());
    } catch (CurrencyNotRegisteredInSystemException exception) {
      logger.error("Currency {} not found", amount.getCurrency());
      throw new UnknownCurrencyRateForRequestedCurrency(amount.getCurrency());
    }
  }

  public void transfer(BankAccount from, BankAccount to, MonetaryAmount amount)
      throws InsufficientFundsException, UnknownCurrencyRateForRequestedCurrency {
    withdraw(from, amount);
    deposit(to, amount);
  }

}
