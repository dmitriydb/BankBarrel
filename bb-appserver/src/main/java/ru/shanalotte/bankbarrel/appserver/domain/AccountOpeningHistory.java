package ru.shanalotte.bankbarrel.appserver.domain;

import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "account_opening_history")
public class AccountOpeningHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne
  @JoinColumn(name = "account_opening")
  private AccountOpening accountOpening;

  @OneToOne
  @JoinColumn(name = "status")
  private AccountOpeningStatus status;

  @Column(name = "ts")
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
