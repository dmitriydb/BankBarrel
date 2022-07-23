package ru.shanalotte.bankbarrel.webapp.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Сервис для генерации номеров счетов во время разработки.
 * Меняет только последние 7 цифр (порядковый номер счёта в банке)
 */
@Service
public class FakeAccountNumberGenerator {

  public static int NEXT_VALUE = 0;
  private static final String INITIAL_PREFIX = "4070000050000";

  public String generateNumber() {
    int nextValue = ++NEXT_VALUE;
    return INITIAL_PREFIX + padWithZeros(String.valueOf(nextValue));
  }

  private String padWithZeros(String s) {
    while (s.length() < 7) {
      s = "0" + s;
    }
    return s;
  }

}
