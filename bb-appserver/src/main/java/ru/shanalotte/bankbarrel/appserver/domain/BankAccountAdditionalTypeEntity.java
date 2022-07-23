package ru.shanalotte.bankbarrel.appserver.domain;


import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Тип банковского счета 1 уровня.
 */
@Entity
@Table(name = "bank_account_additional_types")
public class BankAccountAdditionalTypeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String code;
  @Column(nullable = false)
  private String description;
  @ManyToOne(optional = false)
  @JoinColumn(name = "owner_type", nullable = false)
  private BankAccountTypeEntity ownerType;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public BankAccountTypeEntity getOwnerType() {
    return ownerType;
  }

  public void setOwnerType(BankAccountTypeEntity ownerType) {
    this.ownerType = ownerType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankAccountAdditionalTypeEntity entity = (BankAccountAdditionalTypeEntity) o;
    return Objects.equals(code, entity.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("BankAccountAdditionalTypeEntity{");
    sb.append("id=").append(id);
    sb.append(", code=").append(code);
    sb.append(", description='").append(description).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
