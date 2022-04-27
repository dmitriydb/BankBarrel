package ru.shanalotte.bankbarrel.rest.infomodule.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.shanalotte.bankbarrel.core.domain.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;
import ru.shanalotte.bankbarrel.core.dto.ListingDto;
import ru.shanalotte.bankbarrel.core.dto.ListingDtoItem;
import ru.shanalotte.bankbarrel.core.service.EnumToListingDtoItemConverter;

/**
 * Контроллер, который возвращает json с возможными типами банковских счетов 1 и 2 уровня.
 */
@RestController
public class AccountTypesController {

  private EnumToListingDtoItemConverter enumToListingDtoItemConverter;

  public AccountTypesController(EnumToListingDtoItemConverter enumToListingDtoItemConverter) {
    this.enumToListingDtoItemConverter = enumToListingDtoItemConverter;
  }

  /**
   * Возвращает json со всеми типами счетов 1 уровня.
   */
  @CrossOrigin(origins = "http://localhost:8888")
  @GetMapping("/accounttypes")
  public List<ListingDtoItem> accountTypes() {
    List<ListingDtoItem> listingDtoItems = new ArrayList<>();
    try {
      for (BankAccountType bankAccountType : BankAccountType.values()) {
        listingDtoItems.add(enumToListingDtoItemConverter.convert(bankAccountType.name()));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return listingDtoItems;
  }

  /**
   * Возвращает json с типами счетов 2 уровня.
   *
   * @param code код типа 1 уровня
   * */
  @CrossOrigin(origins = "http://localhost:8888")
  @GetMapping("/accounttype/{code}/additionaltypes")
  public List<ListingDtoItem> additionalTypes(@PathVariable("code") String code) {
    List<ListingDtoItem> listingDtoItems = new ArrayList<>();
    try {
      BankAccountType type = BankAccountType.valueOf(code);
      for (BankAccountAdditionalType bankAccountAdditionalType : type.getAdditionalTypes()) {
        listingDtoItems.add(
            enumToListingDtoItemConverter.convert(bankAccountAdditionalType.name()));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return listingDtoItems;
  }
}
