package com.sample.Accessories.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sample.Accessories.Entity.Accessory;
import com.sample.Accessories.Entity.AccessoryDetails;
import com.sample.Accessories.Entity.AccessoryProductDetails;
import com.sample.Accessories.Interfaces.AccessoryProductDetailsRepository;
import com.sample.Accessories.Interfaces.AccessorySkuRepository;

@Service
public class AccessorySkuService {

	@Autowired
	private AccessorySkuRepository accessorySkuRepository;
	
	@Autowired
	private AccessoryProductDetailsRepository accessoryProductDetailsRepository;

	@Cacheable(cacheNames = "getAllAccessories" , key = "#root.Method.Name" , unless = "#result == null")
	public List<Accessory> findAll() {
		return accessorySkuRepository.findAll();
	}

	public AccessoryDetails findBySkuID(String skuId) {
		Accessory accessory = accessorySkuRepository.findBySkuID(skuId);
		AccessoryDetails accessoryDetails = null;
		if(accessory != null && !(accessory.getEns_id().equalsIgnoreCase("dummy") || accessory.getEns_id().equalsIgnoreCase("dumy"))) {
			AccessoryProductDetails accessoryProductDetails = accessoryProductDetailsRepository.findBySkuId(skuId);
			accessoryDetails = new AccessoryDetails();
			accessoryDetails.setProductId(accessoryProductDetails.getProduct_id());
			accessoryDetails.setSkuId(accessoryProductDetails.getDefault_sku_id());
			accessoryDetails.setDisplayName(accessoryProductDetails.getCms_display_name());
			accessoryDetails.setDescription(accessoryProductDetails.getShrt_desc());
			accessoryDetails.setDetails(accessoryProductDetails.getLongdescription());
			accessoryDetails.setImageUrl(accessoryProductDetails.getImage_base_name());
			accessoryDetails.setPrice(accessory.getList_price());
			accessoryDetails.setEsembleId(accessory.getEns_id());		
			
		}else
			return null;
		
		return accessoryDetails;
	}
	
}
