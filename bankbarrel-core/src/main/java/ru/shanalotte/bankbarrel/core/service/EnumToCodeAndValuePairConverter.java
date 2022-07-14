package ru.shanalotte.bankbarrel.core.service;

import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.CodeAndValuePair;

/**
 * Преобразует название элемента перечисления в CodeAndValuePair.
 */
@Service
public class EnumToCodeAndValuePairConverter {

  /**
   * На основе названия константы произвольного enum-а генерирует пару значений:
   * code и value, в которых code == оригинальное название, а value имеет более
   * человекочитаемый формат.
   *
   * @return пара значений в виде CodeAndValuePair
   */
  public CodeAndValuePair convert(String enumElementName) {
    String value = enumElementName.substring(0, 1).toUpperCase() + enumElementName.substring(1).toLowerCase();
    return new CodeAndValuePair(enumElementName, value);
  }
}
