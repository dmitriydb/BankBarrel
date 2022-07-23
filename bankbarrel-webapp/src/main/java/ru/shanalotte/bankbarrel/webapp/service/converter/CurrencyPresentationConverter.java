package ru.shanalotte.bankbarrel.webapp.service.converter;

import org.springframework.stereotype.Service;

/**
 * Сервис, который конвертирует символьное представление валюты в другие текстовые представления.
 */
@Service
public class CurrencyPresentationConverter {

  /**
   * Конвертирует символьное значение валюты в знак валюты.
   * * Например, RUB -> ₽,
   * * USD -> $,
   * * KZT -> ₸
   *
   * @param currency символьное представление валюты (USD, RUB...)
   * @return знак валюты
   */
  public String currencyToSign(String currency) {
    switch (currency) {
      case "RUB": {
        return "₽";
      }
      case "USD": {
        return "$";
      }
      case "KZT": {
        return "₸";
      }
      default: {
        throw new IllegalArgumentException(currency + " is not supported");
      }
    }
  }

  /**
   * Конвертирует символьное представление валюты в название.
   * Например, USD -> 'Доллар'
   *
   * @param currency символьное представление валюты (USD, RUB...)
   * @return название валюты
   */
  public String currencyToName(String currency) {
    switch (currency) {
      case "RUB": {
        return "Рубль";
      }
      case "USD": {
        return "Доллар";
      }
      case "KZT": {
        return "Тенге";
      }
      default: {
        throw new IllegalArgumentException(currency + " is not supported");
      }
    }
  }
}
