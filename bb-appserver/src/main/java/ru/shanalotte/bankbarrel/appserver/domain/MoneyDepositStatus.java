package ru.shanalotte.bankbarrel.appserver.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Возможный статус событий, возникающих в ходе обработки денежного вклада.
 */
@Entity
@Table(name = "money_deposit_status")
public class MoneyDepositStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 255, nullable = false)
  private String status;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MoneyDepositStatus that = (MoneyDepositStatus) o;
    return Objects.equals(id, that.id) && Objects.equals(status, that.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, status);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("MoneyDepositStatus{");
    sb.append("id=").append(id);
    sb.append(", status='").append(status).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
