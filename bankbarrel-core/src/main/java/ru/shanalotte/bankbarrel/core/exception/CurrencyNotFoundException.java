package ru.shanalotte.bankbarrel.core.exception;

/**
 * Exception that throws in case if the currency is not present in system or rate is unknowm.
 */
public class CurrencyNotFoundException extends Exception {
  public CurrencyNotFoundException(String message) {
    super(message);
  }
}
