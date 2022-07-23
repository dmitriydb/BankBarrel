package ru.shanalotte.bankbarrel.rest.infomodule.service.caching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("production")
public class RedisCachingManager implements CachingManager {

  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  public RedisCachingManager(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public boolean hasKey(String value) {
    return Boolean.TRUE.equals(redisTemplate.hasKey(value));
  }

  public Object getValueFromCache(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public void cacheValue(String key, Object value) {
    redisTemplate.opsForValue().set(key, value);
  }
}
