package ru.shanalotte.bankbarrel.webapp.service.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.service.EnumToCodeAndValuePairConverter;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountDetailsDto;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountWebAppDto;

/**
 * Класс, который преобразует бизнес-объекты класса BankAccount в соответствующее DTO.
 *
 * @see BankAccountWebAppDto
 */
@Service
public class BankAccountDetailsDtoConverter {

  private EnumToCodeAndValuePairConverter enumToCodeAndValuePairConverter;
  private CurrencyPresentationConverter currencyPresentationConverter;

  public BankAccountDetailsDtoConverter(EnumToCodeAndValuePairConverter
                                            enumToCodeAndValuePairConverter,
                                        CurrencyPresentationConverter
                                            currencyPresentationConverter) {
    this.enumToCodeAndValuePairConverter = enumToCodeAndValuePairConverter;
    this.currencyPresentationConverter = currencyPresentationConverter;
  }

  /**
   * Метод преобразует бизнес-объект класса BankAccount в соответствующее DTO.
   * В данном методе названия констант перечислений банковских счетов напрямую передаются
   * в сервис enumToCodeAndValuePairConverter, что в дальнейшем может создать ненужный coupling.
   */
  public BankAccountDetailsDto convert(BankAccountDto account) {
    final String type = enumToCodeAndValuePairConverter
        .convert(account.getType()).getValue();
    String additionalType = enumToCodeAndValuePairConverter
        .convert(account.getAdditionalType()).getValue();
    String currency = account.getCurrency();
    final String currencySign = currencyPresentationConverter.currencyToSign(currency);
    final String number = account.getNumber();
    final String description = account.getDescription();
    BankAccountDetailsDto dto = new BankAccountDetailsDto();
    if (account.getBalance() == null) {
      account.setBalance("0");
    }
    dto.setBalance(new BigDecimal(account.getBalance())
        .setScale(2, RoundingMode.HALF_UP).toString());
    dto.setAdditionalType(additionalType);
    dto.setCurrency(currency);
    dto.setDescription(description);
    dto.setNumber(number);
    dto.setType(type);
    dto.setCurrencySign(currencySign);
    return dto;
  }
}
