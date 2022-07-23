package ru.shanalotte.bankbarrel.rest.infomodule.service.caching;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev", "test"})
public class MapCachingManager implements CachingManager {

  private Map<String, Object> cache = new HashMap<>();

  public boolean hasKey(String key) {
    return cache.containsKey(key);
  }

  public Object getValueFromCache(String key) {
    return cache.get(key);
  }

  public void cacheValue(String key, Object value) {
    cache.put(key, value);
  }
}
