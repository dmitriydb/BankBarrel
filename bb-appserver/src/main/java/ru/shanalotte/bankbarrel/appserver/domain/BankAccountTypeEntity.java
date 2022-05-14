package ru.shanalotte.bankbarrel.appserver.domain;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import ru.shanalotte.bankbarrel.core.domain.BankAccountAdditionalType;
import ru.shanalotte.bankbarrel.core.domain.BankAccountType;

@Entity
@Table(name = "bank_account_types")
public class BankAccountTypeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Enumerated(EnumType.STRING)
  private BankAccountType code;
  private String description;
  @OneToMany(mappedBy = "ownerType", fetch = FetchType.EAGER)
  private Set<BankAccountAdditionalTypeEntity> subTypes = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BankAccountType getCode() {
    return code;
  }

  public void setCode(BankAccountType code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<BankAccountAdditionalTypeEntity> getSubTypes() {
    return subTypes;
  }

  public void setSubTypes(Set<BankAccountAdditionalTypeEntity> subTypes) {
    this.subTypes = subTypes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BankAccountTypeEntity that = (BankAccountTypeEntity) o;
    return Objects.equals(id, that.id) && code == that.code;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("BankAccountTypeEntity{");
    sb.append("id=").append(id);
    sb.append(", code=").append(code);
    sb.append(", description='").append(description).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
