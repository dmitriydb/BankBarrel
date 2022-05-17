package ru.shanalotte.bankbarrel.webapp.service.converter;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.service.EnumToListingDtoItemConverter;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountWebAppDto;

/**
 * Класс, который преобразует бизнес-объекты класса BankAccount в соответствующее DTO.
 *
 * @see BankAccountWebAppDto
 */
@Service
public class BankAccountDtoConverter {

  private EnumToListingDtoItemConverter enumToListingDtoItemConverter;

  public BankAccountDtoConverter(EnumToListingDtoItemConverter enumToListingDtoItemConverter) {
    this.enumToListingDtoItemConverter = enumToListingDtoItemConverter;
  }

  /**
   * Метод преобразует бизнес-объект класса BankAccount в соответствующее DTO.
   * В данном методе названия констант перечислений банковских счетов напрямую передаются
   * в сервис enumToListingDtoItemConverter, что в дальнейшем может создать ненужный coupling.
   */
  public BankAccountWebAppDto convert(BankAccount account) {
    String type = enumToListingDtoItemConverter
        .convert(account.getBankAccountType()).getValue();
    String additionalType = enumToListingDtoItemConverter
        .convert(account.getAdditionalType()).getValue();
    String currency = account.getCurrency();
    String number = account.getNumber();
    BankAccountWebAppDto dto = new BankAccountWebAppDto(number, type, additionalType, currency);
    return dto;
  }
}
