package ru.shanalotte.bankbarrel.webapp.entities;

public class WebAppOperationType {
  private String name;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("WebAppOperationType{");
    sb.append("name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
