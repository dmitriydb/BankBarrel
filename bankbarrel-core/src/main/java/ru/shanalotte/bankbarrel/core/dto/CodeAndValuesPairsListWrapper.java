package ru.shanalotte.bankbarrel.core.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("checkstyle:MissingJavadocType")
public class CodeAndValuesPairsListWrapper {
  protected final List<CodeAndValuePair> items = new ArrayList<>();

  public void addItem(CodeAndValuePair item) {
    items.add(item);
  }

  public List<CodeAndValuePair> getItems() {
    return items;
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
    return Objects.equals(items, dto.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CodeAndValuesPairsListWrapper{");
    sb.append("items=").append(items);
    sb.append('}');
    return sb.toString();
  }
}
