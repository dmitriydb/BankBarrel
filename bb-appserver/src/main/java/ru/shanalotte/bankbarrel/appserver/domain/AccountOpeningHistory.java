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
 * Класс, который инкапсулирует информацию о событии процесса открытия счета.
 */
@Entity
@Table(name = "account_openings_history")
public class AccountOpeningHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(optional = false)
  @JoinColumn(name = "account_opening", nullable = false)
  private AccountOpening accountOpening;

  @OneToOne(optional = false)
  @JoinColumn(name = "status", nullable = false)
  private AccountOpeningStatus status;

  @Column(name = "ts", nullable = false)
  private Timestamp timestamp;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public AccountOpening getAccountOpening() {
    return accountOpening;
  }

  public void setAccountOpening(AccountOpening accountOpening) {
    this.accountOpening = accountOpening;
  }

  public AccountOpeningStatus getStatus() {
    return status;
  }

  public void setStatus(AccountOpeningStatus status) {
    this.status = status;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }
}
