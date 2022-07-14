package ru.shanalotte.bankbarrel.webapp.service.converter;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.service.EnumToCodeAndValuePairConverter;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountWebAppDto;


/**
 * Сервис для конвертации BankAccountDto из модуля bankbarrel-core
 * в BankAccountWebAppDto модуля bb-webapp.
 */
@Service
public class BankAccountDtoConverter {

  private EnumToCodeAndValuePairConverter enumToCodeAndValuePairConverter;

  /**
   * Конструктор со всеми зависимостями.
   */
  public BankAccountDtoConverter(EnumToCodeAndValuePairConverter enumToCodeAndValuePairConverter) {
    this.enumToCodeAndValuePairConverter = enumToCodeAndValuePairConverter;
  }

  /**
   * Метод конвертации.
   */
  public BankAccountWebAppDto convert(BankAccountDto account) {
    String type = enumToCodeAndValuePairConverter
        .convert(account.getType()).getValue();
    String additionalType = enumToCodeAndValuePairConverter
        .convert(account.getAdditionalType()).getValue();
    String currency = account.getCurrency();
    String number = account.getNumber();
    BankAccountWebAppDto dto = new BankAccountWebAppDto(number, type, additionalType, currency);
    return dto;
  }
}
