package ru.shanalotte.bankbarrel.core.exception;

public class UnknownCurrencyRateForRequestedCurrency extends Exception {
  public UnknownCurrencyRateForRequestedCurrency(String message) {
    super(message);
  }
}
