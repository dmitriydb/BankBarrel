package ru.shanalotte.bankbarrel.core.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CodeAndValuesPairsListWrapper {
  protected final List<CodeAndValuePair> codeAndValuePairs = new ArrayList<>();

  public void addItem(CodeAndValuePair item) {
    codeAndValuePairs.add(item);
  }

  public List<CodeAndValuePair> getCodeAndValuePairs() {
    return codeAndValuePairs;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CodeAndValuesPairsListWrapper dto = (CodeAndValuesPairsListWrapper) o;
    return Objects.equals(codeAndValuePairs, dto.codeAndValuePairs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codeAndValuePairs);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CodeAndValuesPairsListWrapper{");
    sb.append("items=").append(codeAndValuePairs);
    sb.append('}');
    return sb.toString();
  }
}
