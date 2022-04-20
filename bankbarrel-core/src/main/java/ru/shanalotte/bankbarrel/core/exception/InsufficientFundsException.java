package ru.shanalotte.bankbarrel.core.exception;

/**
 * Exception throws in case of insufficient funds.
 */
public class InsufficientFundsException extends Exception {

  public InsufficientFundsException(String message) {
    super(message);
  }
}
