package ru.shanalotte.bankbarrel.core.dto;

import java.util.Objects;

public class CurrencyDto {
  private Long id;
  private String code;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CurrencyDto that = (CurrencyDto) o;
    return Objects.equals(id, that.id) && Objects.equals(code, that.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CurrencyDto{");
    sb.append("id=").append(id);
    sb.append(", code='").append(code).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
