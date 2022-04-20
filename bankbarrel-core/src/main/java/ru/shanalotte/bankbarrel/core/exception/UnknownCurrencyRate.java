package ru.shanalotte.bankbarrel.core.exception;

/**
 * Exception throws in case if the currency rate is unknown.
 */
public class UnknownCurrencyRate extends Exception {
  public UnknownCurrencyRate(String message) {
    super(message);
  }
}
