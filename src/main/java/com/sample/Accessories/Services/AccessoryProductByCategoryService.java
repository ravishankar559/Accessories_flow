package com.sample.Accessories.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sample.Accessories.Entity.Accessory;
import com.sample.Accessories.Entity.AccessoryDetails;
import com.sample.Accessories.Entity.AccessoryProductByCategory;
import com.sample.Accessories.Entity.AccessoryProductDetails;
import com.sample.Accessories.Interfaces.AccessoryProductByCategoryRepository;
import com.sample.Accessories.Interfaces.AccessoryProductDetailsRepository;
import com.sample.Accessories.Interfaces.AccessorySkuRepository;

@Service
public class AccessoryProductByCategoryService {
	
	public static final String ACCESSORIES_DETAILS = "AccessoriesDetails";
	
	public static final String ACCESSORY_PRODUCT_BY_CATEGORY = "AccessoryProductByCategory";
	
	@Autowired
	private AccessoryProductByCategoryRepository accessoryProductByCategoryRepository;
	
	@Autowired
	private AccessoryProductDetailsRepository accessoryProductDetailsRepository;
	
	@Autowired
	private AccessorySkuRepository accessorySkuRepository;
	
	@Cacheable(value = ACCESSORY_PRODUCT_BY_CATEGORY , key = "#categoryId" , unless = "#result == null")
	public List<AccessoryProductByCategory> findAccessoryProductsByCategory(String categoryId) {
		List<AccessoryProductByCategory> accessoryProductsByCategoires = accessoryProductByCategoryRepository.findAccessoryProductsByCategory(categoryId);
		return accessoryProductsByCategoires;
	}

	@Cacheable(value = ACCESSORIES_DETAILS , key = "#categoryId")
	public List<AccessoryDetails> getAccessoriesDetails(List<AccessoryProductByCategory> accessoryProductsByCategory , String categoryId) {
		List<AccessoryDetails> accessoriesDetails = null;
		AccessoryDetails accessoryDetails = null;
		if(null != accessoryProductsByCategory) {
			accessoriesDetails = new ArrayList<AccessoryDetails>();
			for(AccessoryProductByCategory accessoryProductByCategory : accessoryProductsByCategory) {
				String productId = accessoryProductByCategory.getProduct_id();
				AccessoryProductDetails accessoryProductDetails = accessoryProductDetailsRepository.findByProductId(productId);
				if(accessoryProductDetails !=  null) {
					Accessory accessory = accessoryProductDetails.getAccessory();
					if(accessory != null) {
						if(accessory.getEns_id().equalsIgnoreCase("dummy") || accessory.getEns_id().equalsIgnoreCase("dumy"))
							continue;
						accessoryDetails = new AccessoryDetails();
						accessoryDetails.setCategoryId(accessoryProductByCategory.getCategory_id());
						accessoryDetails.setProductId(productId);
						accessoryDetails.setSkuId(accessoryProductDetails.getDefault_sku_id());
						accessoryDetails.setDisplayName(accessoryProductDetails.getCms_display_name());
						accessoryDetails.setDescription(accessoryProductDetails.getShrt_desc());
						accessoryDetails.setDetails(accessoryProductDetails.getLongdescription());
						accessoryDetails.setImageUrl(accessoryProductDetails.getImage_base_name());
						accessoryDetails.setPrice(accessory.getList_price());
						accessoryDetails.setEsembleId(accessory.getEns_id());
						accessoriesDetails.add(accessoryDetails);
					}
				}
			}
		}
		return accessoriesDetails;
	}

	@CacheEvict(cacheNames = {ACCESSORIES_DETAILS,ACCESSORY_PRODUCT_BY_CATEGORY}, allEntries = true )
	public void removeCache() {
		// TODO Auto-generated method stub
		
	}
	
}
