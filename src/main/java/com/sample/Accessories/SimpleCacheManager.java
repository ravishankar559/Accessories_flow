package com.sample.Accessories;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class SimpleCacheManager /**implements CacheManagerCustomizer<ConcurrentMapCacheManager>**/ {
		
    /**@Override
	public void customize(ConcurrentMapCacheManager cacheManager) {
        cacheManager.setCacheNames(Arrays.asList(AccessoryCategoryService.ACCESSORY_CATEGORIES_CACHE,AccessoryProductByCategoryService.ACCESSORIES_DETAILS,AccessoryProductByCategoryService.ACCESSORY_PRODUCT_BY_CATEGORY));	
	}**/
	
}
