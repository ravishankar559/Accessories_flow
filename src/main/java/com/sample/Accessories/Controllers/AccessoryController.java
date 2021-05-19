package com.sample.Accessories.Controllers;

import java.util.List;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.Accessories.Entity.Accessory;
import com.sample.Accessories.Entity.AccessoryCategory;
import com.sample.Accessories.Services.AccessoryCategoryService;
import com.sample.Accessories.Services.AccessorySkuService;

@RestController
public class AccessoryController {
	
	@Autowired
	private AccessorySkuService accessorySkuService;
	
	@Autowired
	private AccessoryCategoryService accessoryCategoryService;
	
	@GetMapping(value = "/accessories" , produces = MediaType.APPLICATION_JSON)
	public Object getAccessories() {
		List<Accessory> acclist = accessorySkuService.findAll();
		return acclist;
	}
	
	@GetMapping(value = "/accessories/{skuId}", produces = MediaType.APPLICATION_JSON)
	public Object getAccessoryById(@PathVariable(value = "skuId") String skuId){
		Accessory acc = accessorySkuService.findBySkuID(skuId);
		return acc;
	}
	
	@GetMapping(value = "/accessoryCategories" , produces = MediaType.APPLICATION_JSON)
	public Object getAccessoryCategories() {
		List <AccessoryCategory> accCatList = accessoryCategoryService.findAll();
		return accCatList;
	}
	
	@GetMapping(value = "/accessoryCategories/{categoryId}" , produces = MediaType.APPLICATION_JSON)
	public Object getAccessoryCategoryById(@PathVariable(value = "categoryId") String categoryId) {
		AccessoryCategory accCat = accessoryCategoryService.findByCategoryId(categoryId);
		return accCat;
	}
	
}
