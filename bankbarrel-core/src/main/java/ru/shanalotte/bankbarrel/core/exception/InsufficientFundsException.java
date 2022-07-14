package ru.shanalotte.bankbarrel.core.exception;

public class InsufficientFundsException extends Exception {
  public InsufficientFundsException(String message) {
    super(message);
  }
}
