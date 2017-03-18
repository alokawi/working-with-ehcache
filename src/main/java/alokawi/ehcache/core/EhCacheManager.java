/**
 * 
 */
package alokawi.ehcache.core;

import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.CacheManagerBuilder.newCacheManagerBuilder;
import static org.ehcache.config.builders.ResourcePoolsBuilder.heap;
import static org.ehcache.config.units.MemoryUnit.MB;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

/**
 * @author alokkumar
 *
 */
public class EhCacheManager extends AbstractCacheManager<String, String> {

	private static final String CACHE_ONE = "cache-01";
	private static final String CACHE_TWO = "cache-02";

	private CacheManager cacheManager;

	public EhCacheManager() {
		cacheManager = initializeCacheManager();
	}

	private static CacheManager initializeCacheManager() {
		return newCacheManagerBuilder()
				.withCache(CACHE_ONE,
						newCacheConfigurationBuilder(String.class, String.class, heap(5000).offheap(5, MB)
								//.disk(10, MB)
								)
								.withExpiry(Expirations.timeToLiveExpiration(new Duration(1, TimeUnit.HOURS))))
				.withCache(CACHE_TWO,
						newCacheConfigurationBuilder(String.class, ArrayList.class,
								heap(5000).offheap(5, MB)
								//.disk(10, MB)
								)
										.withExpiry(Expirations.timeToLiveExpiration(new Duration(1, TimeUnit.HOURS))))
				.build(true);
	}

	@Override
	public void putCacheEntry(String key, String value) {
		@SuppressWarnings("unchecked")
		Cache<String, String> cache = (Cache<String, String>) this.cacheManager.getCache(CACHE_ONE, key.getClass(),
				value.getClass());
		cache.put(key, value);
	}

	@Override
	public String getCacheEntry(String key) {
		@SuppressWarnings("unchecked")
		Cache<String, String> cache = (Cache<String, String>) this.cacheManager.getCache(CACHE_ONE, key.getClass(),
				String.class);
		return cache.get(key);
	}

}
