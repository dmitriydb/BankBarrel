package ru.shanalotte.bankbarrel.webapp.service.converter;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.service.EnumToListingDtoItemConverter;
import ru.shanalotte.bankbarrel.webapp.dto.account.BankAccountDto;

/**
 * Класс, который преобразует бизнес-объекты класса BankAccount в соответствующее DTO.
 *
 * @see BankAccountDto
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
  public BankAccountDto convert(BankAccount account) {
    String type = enumToListingDtoItemConverter
        .convert(account.getBankAccountType().name()).getValue();
    String additionalType = enumToListingDtoItemConverter
        .convert(account.getAdditionalType().name()).getValue();
    String currency = account.getCurrency();
    String number = account.getNumber();
    BankAccountDto dto = new BankAccountDto(number, type, additionalType, currency);
    return dto;
  }
}
