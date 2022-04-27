package ru.shanalotte.bankbarrel.core.service;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.ListingDtoItem;

/**
 * Преобразует название элемента перечисления в ListingDtoItem.
 */
@Service
public class EnumToListingDtoItemConverter {

  /**
   * На основе названия константы произвольного enum-а генерирует пару значений:
   * code и value, в которых code == оригинальное название, а value имеет более
   * человекочитаемый формат.
   *
   * @return пару значений в виде ListingDtoItem
   */
  public ListingDtoItem convert(String enumElementName) {
    String code = enumElementName;
    String value = code.substring(0, 1).toUpperCase() + code.substring(1).toLowerCase();
    return new ListingDtoItem(code, value);
  }
}
