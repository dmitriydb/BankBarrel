package ru.shanalotte.bankbarrel.webapp.dto;

import java.util.Objects;

/**
 * Значение ListingDto для вывода списка строк пользователю.
 * Содержит код значения и самое значение в виде строки.
 */
public class ListingDtoItem {
  private final String code;
  private final String value;

  public ListingDtoItem(String code, String value) {
    this.code = code;
    this.value = value;
  }

  public String getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ListingDtoItem dtoItem = (ListingDtoItem) o;
    return Objects.equals(code, dtoItem.code) && Objects.equals(value, dtoItem.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, value);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DtoItem{");
    sb.append("code='").append(code).append('\'');
    sb.append(", value='").append(value).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
