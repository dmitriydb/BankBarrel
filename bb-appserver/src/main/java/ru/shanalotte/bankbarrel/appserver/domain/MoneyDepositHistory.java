package ru.shanalotte.bankbarrel.appserver.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Объект, который инкапсулирует событие, случившееся
 * в ходе процесса обработки денежного вклада.
 */
@Entity
@Table(name = "money_deposit_history")
public class MoneyDepositHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JoinColumn(name = "money_deposit")
  private MoneyDeposit moneyDeposit;

  @OneToOne
  @JoinColumn(name = "status")
  private MoneyDepositStatus status;

  @Column(name = "ts")
  private Timestamp timestamp;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public MoneyDeposit getMoneyDeposit() {
    return moneyDeposit;
  }

  public void setMoneyDeposit(MoneyDeposit moneyDeposit) {
    this.moneyDeposit = moneyDeposit;
  }

  public MoneyDepositStatus getStatus() {
    return status;
  }

  public void setStatus(MoneyDepositStatus status) {
    this.status = status;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }
}
