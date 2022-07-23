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
 * Класс инкапсулирует событие, возникающее в ходе обработки
 * перевода средств с одного счета на другой.
 */
@Entity
@Table(name = "money_transfer_history")
public class MoneyTransferHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(optional = false)
  @JoinColumn(name = "money_transfer")
  private MoneyTransfer moneyTransfer;

  @OneToOne(optional = false)
  @JoinColumn(name = "status")
  private MoneyTransferStatus status;

  @Column(name = "ts", nullable = false)
  private Timestamp timestamp;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public MoneyTransfer getMoneyTransfer() {
    return moneyTransfer;
  }

  public void setMoneyTransfer(MoneyTransfer moneyTransfer) {
    this.moneyTransfer = moneyTransfer;
  }

  public MoneyTransferStatus getStatus() {
    return status;
  }

  public void setStatus(MoneyTransferStatus status) {
    this.status = status;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }
}
