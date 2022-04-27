package ru.shanalotte.bankbarrel.core.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Интерфейс DTO, содержащего список строк.
 */
public class ListingDto {
  protected List<ListingDtoItem> items = new ArrayList<>();

  public void addItem(ListingDtoItem item) {
    items.add(item);
  }

  public List<ListingDtoItem> getItems() {
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
    ListingDto dto = (ListingDto) o;
    return Objects.equals(items, dto.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ListingDto{");
    sb.append("items=").append(items);
    sb.append('}');
    return sb.toString();
  }
}
