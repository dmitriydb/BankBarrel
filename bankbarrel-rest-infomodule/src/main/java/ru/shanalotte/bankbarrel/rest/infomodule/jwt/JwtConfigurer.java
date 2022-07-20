package ru.shanalotte.bankbarrel.rest.infomodule.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,
    HttpSecurity> {
  private JwtTokenValidator jwtTokenProvider;

  public JwtConfigurer(JwtTokenValidator jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void configure(HttpSecurity httpSecurity) throws Exception {
    JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
    httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }
}