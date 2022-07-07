package ru.shanalotte.bankbarrel.webapp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.shanalotte.bankbarrel.core.dto.BankAccountDto;
import ru.shanalotte.bankbarrel.core.dto.BankClientDto;

/**
 * Бин, используемый во время тестирования веб-приложения для
 * хранения информации о счетах клиентов.
 * Хранит в карте счета индивидуального клиента и инжектится в заглушки тех классов, которые
 * добавляют/удаляют информацию о счетах пользователей во время интеграционного тестирования.
 */
@Service
@Profile({"test", "dev"})
public class AccountHolder {

  private Map<BankClientDto, List<BankAccountDto>> accounts = new HashMap<>();

  public Map<BankClientDto, List<BankAccountDto>> getAccounts() {
    return accounts;
  }
}
