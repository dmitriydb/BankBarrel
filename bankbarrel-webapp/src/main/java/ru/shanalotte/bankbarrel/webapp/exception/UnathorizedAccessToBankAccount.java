package ru.shanalotte.bankbarrel.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое бросается в случае, если совершен неавторизованный доступ к банковскому счету
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "No access to the bank account")
public class UnathorizedAccessToBankAccount extends Exception {
  public UnathorizedAccessToBankAccount(String message) {
    super(message);
  }
}
