package ru.shanalotte.bankbarrel.appserver.domain;

import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.*;
import ru.shanalotte.bankbarrel.core.domain.BankAccount;
import ru.shanalotte.bankbarrel.core.domain.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.domain.BankClient;

@Entity
@Table(name = "account_openings")
public class AccountOpening {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "client")
  private BankClient client;

  @Column(name = "opening_date_time")
  private Timestamp timestamp;

  @ManyToOne
  @JoinColumn(name = "currency")
  private Currency currency;

  private String result;

  @ManyToOne
  @JoinColumn(name = "type")
  private BankAccountTypeEntity type;

  @ManyToOne
  @JoinColumn(name = "additional_type")
  private BankAccountAdditionalTypeEntity additionalType;

  @ManyToOne
  @JoinColumn(name = "source")
  private OperationSource operationSource;

  @OneToOne
  @JoinColumn(name = "resulting_account")
  private BankAccount resultingAccount;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BankClient getClient() {
    return client;
  }

  public void setClient(BankClient client) {
    this.client = client;
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

  public BankAccountTypeEntity getType() {
    return type;
  }

  public void setType(BankAccountTypeEntity type) {
    this.type = type;
  }

  public BankAccountAdditionalTypeEntity getAdditionalType() {
    return additionalType;
  }

  public void setAdditionalType(BankAccountAdditionalTypeEntity additionalType) {
    this.additionalType = additionalType;
  }

  public OperationSource getOperationSource() {
    return operationSource;
  }

  public void setOperationSource(OperationSource operationSource) {
    this.operationSource = operationSource;
  }

  public BankAccount getResultingAccount() {
    return resultingAccount;
  }

  public void setResultingAccount(BankAccount resultingAccount) {
    this.resultingAccount = resultingAccount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AccountOpening that = (AccountOpening) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
