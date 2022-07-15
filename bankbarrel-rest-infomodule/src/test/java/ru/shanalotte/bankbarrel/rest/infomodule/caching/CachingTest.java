package ru.shanalotte.bankbarrel.rest.infomodule.caching;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ru.shanalotte.bankbarrel.core.service.EnumToCodeAndValuePairConverter;
import ru.shanalotte.bankbarrel.rest.infomodule.controller.AccountTypesController;
import ru.shanalotte.bankbarrel.rest.infomodule.service.AccountTypesReader;
import ru.shanalotte.bankbarrel.rest.infomodule.service.DevAccountTypesReader;
import ru.shanalotte.bankbarrel.rest.infomodule.service.caching.CachingManager;
import ru.shanalotte.bankbarrel.rest.infomodule.service.caching.MapCachingManager;

public class CachingTest {

  @Test
  public void shouldSaveIntoCacheForThe1stTime() {
    CachingManager cachingManager = spy(new MapCachingManager());
    AccountTypesReader accountTypesReader = spy(new DevAccountTypesReader(new EnumToCodeAndValuePairConverter()));
    AccountTypesController accountTypesController = new AccountTypesController(cachingManager, accountTypesReader);

    accountTypesController.getAccountTypes();
    verify(accountTypesReader).getAccountTypes();
    verify(cachingManager, never()).getValueFromCache(any());
    verify(cachingManager).cacheValue(any(), any());
  }

  @Test
  public void shouldGrabFromCacheForThe2ndTime() {
    CachingManager cachingManager = spy(new MapCachingManager());
    AccountTypesReader accountTypesReader = spy(new DevAccountTypesReader(new EnumToCodeAndValuePairConverter()));
    AccountTypesController accountTypesController = new AccountTypesController(cachingManager, accountTypesReader);
    accountTypesController.getAccountTypes();
    accountTypesController.getAccountTypes();
    verify(accountTypesReader, times(1)).getAccountTypes();
    verify(cachingManager).getValueFromCache(any());
  }

  @Test
  public void shouldSaveAdditionalIntoCacheForThe1stTime() {
    CachingManager cachingManager = spy(new MapCachingManager());
    AccountTypesReader accountTypesReader = spy(new DevAccountTypesReader(new EnumToCodeAndValuePairConverter()));
    AccountTypesController accountTypesController = new AccountTypesController(cachingManager, accountTypesReader);

    accountTypesController.getAdditionalAccountTypes("SAVING");
    verify(accountTypesReader).getAdditionalAccountTypes("SAVING");
    verify(cachingManager, never()).getValueFromCache(any());
    verify(cachingManager).cacheValue(any(), any());
  }

  @Test
  public void shouldGrabAdditionalFromCacheForThe2ndTime() {
    CachingManager cachingManager = spy(new MapCachingManager());
    AccountTypesReader accountTypesReader = spy(new DevAccountTypesReader(new EnumToCodeAndValuePairConverter()));
    AccountTypesController accountTypesController = new AccountTypesController(cachingManager, accountTypesReader);
    accountTypesController.getAdditionalAccountTypes("SAVING");
    accountTypesController.getAdditionalAccountTypes("SAVING");
    verify(accountTypesReader, times(1)).getAdditionalAccountTypes("SAVING");
    verify(cachingManager).getValueFromCache(any());
  }
}
