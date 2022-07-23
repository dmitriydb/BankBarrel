package ru.shanalotte.bankbarrel.webapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое должно вызывать HTTP Error 401.
 * Бросается в случае, если пользователь веб-приложения
 * с таким именем не существует.
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "User is unauthorized")
public class WebAppUserNotFound extends Exception {
  public WebAppUserNotFound(String message) {
    super(message);
  }
}
