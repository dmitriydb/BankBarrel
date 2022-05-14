package ru.shanalotte.bankbarrel.appserver.domain;

import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "money_transfer_history")
public class MoneyTransferHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JoinColumn(name = "money_transfer")
  private MoneyTransfer moneyTransfer;

  @OneToOne
  @JoinColumn(name = "status")
  private MoneyTransferStatus status;

  @Column(name = "ts")
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
