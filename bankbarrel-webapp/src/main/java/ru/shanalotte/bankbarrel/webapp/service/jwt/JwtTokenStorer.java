package ru.shanalotte.bankbarrel.webapp.service.jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenStorer {

  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
