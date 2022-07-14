package ru.shanalotte.bankbarrel.core.exception;

public class CurrencyNotRegisteredInSystemException extends Exception {
  public CurrencyNotRegisteredInSystemException(String message) {
    super(message);
  }
}
