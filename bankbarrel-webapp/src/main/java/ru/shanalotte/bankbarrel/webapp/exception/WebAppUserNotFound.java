package ru.shanalotte.bankbarrel.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "User is unauthorized")
public class WebAppUserNotFound extends Exception {
  public WebAppUserNotFound(String message) {
    super(message);
  }
}
