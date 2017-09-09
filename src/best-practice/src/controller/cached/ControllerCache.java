package controller.cached;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import java.util.concurrent.TimeUnit;

public class ControllerCache
{
  private final Cache<Integer, Object> cache;

  public ControllerCache()
  {
    cache = CacheBuilder.newBuilder().maximumSize(100).
      expireAfterWrite(300, TimeUnit.SECONDS).
      expireAfterAccess
      (300, TimeUnit.SECONDS).build();
  }

  public <T> T get(Integer hash)
  {
    assert(contains(hash));
    return (T) cache.asMap().get(hash);
  }

  public boolean contains(Integer hash)
  {
    return cache.asMap().containsKey(hash);
  }

  public void put(Integer hash, Object value)
  {
    cache.put(hash, value);
  }
}
