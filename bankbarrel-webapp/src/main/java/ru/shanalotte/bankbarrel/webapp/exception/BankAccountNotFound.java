package ru.shanalotte.bankbarrel.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое бросается в случае, если счет не найден.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Bank account not found")
public class BankAccountNotFound extends Exception {
  public BankAccountNotFound(String message) {
    super(message);
  }
}
