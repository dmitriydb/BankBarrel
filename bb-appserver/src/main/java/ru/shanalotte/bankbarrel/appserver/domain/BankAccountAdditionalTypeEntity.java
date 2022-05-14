package ru.shanalotte.bankbarrel.appserver.domain;


import java.util.Objects;
import javax.persistence.*;
import ru.shanalotte.bankbarrel.core.domain.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;

@Entity
@Table(name = "bank_account_additional_types")
public class BankAccountAdditionalTypeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Enumerated(EnumType.STRING)
  private BankAccountAdditionalType code;
  private String description;
  @ManyToOne
  @JoinColumn(name = "owner_type")
  private BankAccountTypeEntity ownerType;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BankAccountAdditionalType getCode() {
    return code;
  }

  public void setCode(BankAccountAdditionalType code) {
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
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BankAccountAdditionalTypeEntity that = (BankAccountAdditionalTypeEntity) o;
    return Objects.equals(id, that.id) && code == that.code;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code);
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
