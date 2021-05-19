package com.sample.Accessories.Services;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sample.Accessories.Entity.AccessoryCategory;
import com.sample.Accessories.Interfaces.AccessoryCategoryRepository;

@Service
public class AccessoryCategoryService {
	
	public static final String ACCESSORY_CATEGORIES_CACHE = "AccessoryCategories";
	
	@Autowired
	private AccessoryCategoryRepository accessoryCategoryRepository;
	
	@Cacheable(ACCESSORY_CATEGORIES_CACHE)
	public List<AccessoryCategory> findAll() {
		// TODO Auto-generated method stub
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

	public AccessoryCategory findByCategoryId(String categoryId) {
		AccessoryCategory accessoryCategory = accessoryCategoryRepository.findByCategoryId(categoryId);
		return accessoryCategory;
	}

}
