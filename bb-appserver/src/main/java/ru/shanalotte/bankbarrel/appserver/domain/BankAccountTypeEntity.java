package ru.shanalotte.bankbarrel.appserver.domain;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Тип банковского счета 1 уровня.
 */
@Entity
@Table(name = "bank_account_types")
public class BankAccountTypeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String code;
  private String description;
  @OneToMany(mappedBy = "ownerType", fetch = FetchType.EAGER, orphanRemoval = true)
  private Set<BankAccountAdditionalTypeEntity> subTypes = new HashSet<>();

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
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
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
