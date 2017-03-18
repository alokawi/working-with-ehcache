/**
 * 
 */
package alokawi.ehcache.test;

import java.util.UUID;

import org.junit.Test;

import alokawi.ehcache.core.EhCacheManager;
import junit.framework.TestCase;

/**
 * @author alokkumar
 *
 */
public class EhCacheManagerTest extends TestCase {

	@Test
	public void testEhCachePutAndGet() {
		EhCacheManager cacheManager = new EhCacheManager();
		for (int i = 0; i < 100000; i++) {

			String key = UUID.randomUUID().toString();
			String value = UUID.randomUUID().toString();
			cacheManager.putCacheEntry(key, value);
			assertEquals(value, cacheManager.getCacheEntry(key));

		}
	}

}
