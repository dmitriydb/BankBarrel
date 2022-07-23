package ru.shanalotte.bankbarrel.appserver.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ru.shanalotte.bankbarrel.appserver.security.service.JwtConfigurer;
import ru.shanalotte.bankbarrel.appserver.security.service.JwtTokenValidator;

@Configuration
@Profile("production")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenValidator jwtTokenValidator;

  @Autowired
  public SecurityConfig(JwtTokenValidator jwtTokenProvider) {
    this.jwtTokenValidator = jwtTokenProvider;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic().disable()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/swagger-ui/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .apply(new JwtConfigurer(jwtTokenValidator));
  }
}