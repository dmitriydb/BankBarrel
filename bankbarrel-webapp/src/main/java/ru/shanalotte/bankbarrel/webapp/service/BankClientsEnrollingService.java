package ru.shanalotte.bankbarrel.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.domain.BankClient;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;
import ru.shanalotte.bankbarrel.webapp.dao.interfaces.BankClientDao;
import ru.shanalotte.bankbarrel.webapp.dto.bankclient.BankClientInfoDto;

/**
 * Сервис, который создает и сохраняет новых банковских клиентов.
 */
@Service
public class BankClientsEnrollingService {

  @Autowired
  private BankClientDao bankClientDao;

  /**
   * Получает DTO из контроллера в момент заполнения пользователем клиентом
   * веб-приложения формы регистрации.
   * Должен создать объект класса BankClient и сохранить его.
   *
   * @see BankClientInfoDto
   * @see BankClient
   * @param dto DTO
   * @return созданный объект класса BankClient, если создание прошло успешно
   */
  public BankClientDto enrollClient(BankClientInfoDto dto) {
    BankClientDto bankClientDto = new BankClientDto();
    bankClientDto.setGivenName(dto.getFirstName());
    bankClientDto.setFamilyName(dto.getLastName());
    bankClientDto.setTelephone(dto.getTelephone());
    bankClientDto.setEmail(dto.getEmail());
    bankClientDao.save(bankClientDto);
    Long clientId = bankClientDao.idByDto(bankClientDto);
    bankClientDto.setId(clientId);
    return bankClientDto;
  }
}
