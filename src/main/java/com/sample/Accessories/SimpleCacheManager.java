package com.sample.Accessories;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;
import com.sample.Accessories.Services.AccessoryCategoryService;

@Configuration
@EnableCaching
public class SimpleCacheManager implements CacheManagerCustomizer<ConcurrentMapCacheManager> {
	
	@Override
	public void customize(ConcurrentMapCacheManager cacheManager) {
        cacheManager.setCacheNames(Arrays.asList(AccessoryCategoryService.ACCESSORY_CATEGORIES_CACHE));	
	}

}
