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
 * Класс инкапсулирует событие, возникающее в ходе обработки снятия средств со счета.
 */
@Entity
@Table(name = "money_withdraw_history")
public class MoneyWithdrawHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JoinColumn(name = "money_withdraw")
  private MoneyWithdraw moneyWithdraw;

  @OneToOne
  @JoinColumn(name = "status")
  private MoneyWithdrawStatus status;

  @Column(name = "ts")
  private Timestamp timestamp;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public MoneyWithdraw getMoneyWithdraw() {
    return moneyWithdraw;
  }

  public void setMoneyWithdraw(MoneyWithdraw moneyWithdraw) {
    this.moneyWithdraw = moneyWithdraw;
  }

  public MoneyWithdrawStatus getStatus() {
    return status;
  }

  public void setStatus(MoneyWithdrawStatus status) {
    this.status = status;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }
}
