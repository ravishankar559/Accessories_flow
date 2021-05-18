package com.sample.Accessories.Controllers;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sample.Accessories.DTOs.Accessory;
import com.sample.Accessories.Interfaces.AccessorySkuRepository;

@RestController
public class AccessoryController {
	
	@Autowired
	private AccessorySkuRepository accessorySkuRepository;
	
	@GetMapping(value = "/accessories" , produces = MediaType.APPLICATION_JSON)
	public Object getAccessories() {
		List<Accessory> acclist = accessorySkuRepository.findAll();
		return acclist;
	}
	
	@GetMapping(value = "/accessories/{id}", produces = MediaType.APPLICATION_JSON)
	public Object getAccessoryById(@PathVariable(value = "id") String sku_id){
		Accessory acc = accessorySkuRepository.findBySkuID(sku_id);
		return acc;
	}
	
}
