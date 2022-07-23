package ru.shanalotte.bankbarrel.rest.infomodule.service.caching;

@SuppressWarnings("checkstyle:MissingJavadocType")
public interface CachingManager {
  boolean hasKey(String key);

  Object getValueFromCache(String key);

  void cacheValue(String key, Object value);
}