package ru.shanalotte.bankbarrel.appserver.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.*;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;

@Entity
@Table(name = "money_transfer")
public class MoneyTransfer {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "from_account")
  private BankAccount fromAccount;

  @ManyToOne
  @JoinColumn(name = "to_account")
  private BankAccount toAccount;

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "transfer_date_time")
  private Timestamp timestamp;

  @ManyToOne
  @JoinColumn(name = "currency")
  private Currency currency;

  private String result;

  @ManyToOne
  @JoinColumn(name = "source")
  private OperationSource operationSource;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BankAccount getFromAccount() {
    return fromAccount;
  }

  public void setFromAccount(BankAccount fromAccount) {
    this.fromAccount = fromAccount;
  }

  public BankAccount getToAccount() {
    return toAccount;
  }

  public void setToAccount(BankAccount toAccount) {
    this.toAccount = toAccount;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public OperationSource getOperationSource() {
    return operationSource;
  }

  public void setOperationSource(OperationSource operationSource) {
    this.operationSource = operationSource;
  }
}
