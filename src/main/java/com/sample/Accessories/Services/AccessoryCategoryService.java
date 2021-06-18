package com.sample.Accessories.Services;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sample.Accessories.Entity.AccessoryCategory;
import com.sample.Accessories.Interfaces.AccessoryCategoryRepository;

@Service
public class AccessoryCategoryService {
		
	@Autowired
	private AccessoryCategoryRepository accessoryCategoryRepository;
	
	@Cacheable(cacheNames = "accessory" , key = "#categoryId" , unless = "#result == null")
	public AccessoryCategory findByCategoryId(String categoryId) {
		AccessoryCategory accessoryCategory = accessoryCategoryRepository.findByCategoryId(categoryId);
		System.out.println("AccessoryCategory - cache Populated");
		return accessoryCategory;
	}
	
	@Cacheable(cacheNames = "AccessoryCategories" , key = "#root.methodName")
	public List<AccessoryCategory> findAll() {
		List<AccessoryCategory> accessoryCategoryList = null;
		accessoryCategoryList = accessoryCategoryRepository.findAll();
		Iterator<AccessoryCategory> accessoryCategoryIterator = accessoryCategoryList.iterator();
		while(accessoryCategoryIterator.hasNext()) {
			if(!accessoryCategoryIterator.next().isDisplay_on_front_end())
				accessoryCategoryIterator.remove();
		}
		Collections.sort(accessoryCategoryList);
		System.out.println("AccessoryCategories - cache Populated");
		return accessoryCategoryList;
	}
	
	
	@CacheEvict(cacheNames = {"AccessoryCategories","accessory"} , allEntries=true)
	public void removeCache() {
		
	}

}
