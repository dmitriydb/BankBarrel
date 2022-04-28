package ru.shanalotte.bankbarrel.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое бросается в случае, если выполняется операция с несуществующим/удаленным счетом
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Bank account doesn't exist")
public class BankAccountNotExists extends Exception {
  public BankAccountNotExists(String message) {
    super(message);
  }
}
