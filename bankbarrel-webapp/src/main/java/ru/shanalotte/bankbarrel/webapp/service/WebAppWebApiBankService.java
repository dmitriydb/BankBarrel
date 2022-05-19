package ru.shanalotte.bankbarrel.webapp.service;

import java.net.URI;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.shanalotte.bankbarrel.core.domain.MonetaryAmount;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.DepositDto;
import ru.shanalotte.bankbarrel.core.dto.TransferDto;
import ru.shanalotte.bankbarrel.core.dto.WithdrawDto;
import ru.shanalotte.bankbarrel.core.exception.InsufficientFundsException;
import ru.shanalotte.bankbarrel.core.exception.UnknownCurrencyRate;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceRegistryProxy;
import ru.shanalotte.bankbarrel.webapp.service.serviceregistry.ServiceUrlBuilder;

/**
 * Делегат банковского сервиса для веб-приложения.
 * Делегирует все операции с банковскими счетами по REST-у в Web Api Gateway.
 */
@Service
@Profile({"dev", "production"})
public class WebAppWebApiBankService implements WebAppBankService {

  private ServiceUrlBuilder serviceUrlBuilder;
  private ServiceRegistryProxy registryProxy;

  public WebAppWebApiBankService(ServiceUrlBuilder serviceUrlBuilder,
                                 ServiceRegistryProxy registryProxy) {
    this.serviceUrlBuilder = serviceUrlBuilder;
    this.registryProxy = registryProxy;
  }

  /**
   * Обрабатывает денежный вклад.
   */
  public void deposit(BankAccountDto account, MonetaryAmount amount) {
    final String url =
        serviceUrlBuilder.buildServiceUrl(registryProxy.getWebApiInfo()) + "/deposit";
    DepositDto dto = new DepositDto();
    dto.setCurrency(amount.getCurrency());
    dto.setAmount(amount.getValue());
    dto.setAccount(account.getIdentifier());
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.postForEntity(URI.create(url), dto, DepositDto.class);
  }

  /**
   * Обрабатывает снятие средств.
   */
  public void withdraw(BankAccountDto account, MonetaryAmount amount)
      throws InsufficientFundsException, UnknownCurrencyRate {
    String url = serviceUrlBuilder.buildServiceUrl(registryProxy.getWebApiInfo()) + "/withdraw";
    WithdrawDto dto = new WithdrawDto();
    dto.setCurrency(amount.getCurrency());
    dto.setAmount(amount.getValue());
    dto.setAccount(account.getIdentifier());
    RestTemplate restTemplate = new RestTemplate();
    try {
      WithdrawDto result =
          restTemplate.postForEntity(URI.create(url), dto, WithdrawDto.class).getBody();
    } catch (Exception ex) {
      ex.printStackTrace();
      //TODO
      throw new InsufficientFundsException("");
    }
  }

  /**
   * Обрабатывает перевод денежный средств.
   */
  public void transfer(BankAccountDto from, BankAccountDto to, MonetaryAmount amount)
      throws InsufficientFundsException, UnknownCurrencyRate {
    String url = serviceUrlBuilder.buildServiceUrl(registryProxy.getWebApiInfo()) + "/transfer";
    TransferDto dto = new TransferDto();
    dto.setCurrency(amount.getCurrency());
    dto.setAmount(amount.getValue());
    dto.setToAccount(to.getIdentifier());
    dto.setFromAccount(from.getIdentifier());
    RestTemplate restTemplate = new RestTemplate();
    try {
      System.out.println(dto);
      TransferDto result =
          restTemplate.postForEntity(URI.create(url), dto, TransferDto.class).getBody();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new InsufficientFundsException("");
    }
  }
}
