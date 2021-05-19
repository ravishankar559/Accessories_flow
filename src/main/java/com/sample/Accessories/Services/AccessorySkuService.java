package com.sample.Accessories.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.Accessories.Entity.Accessory;
import com.sample.Accessories.Interfaces.AccessorySkuRepository;

@Service
public class AccessorySkuService {

	@Autowired
	private AccessorySkuRepository accessorySkuRepository;

	public List<Accessory> findAll() {
		return accessorySkuRepository.findAll();
	}

	public Accessory findBySkuID(String skuId) {
		return accessorySkuRepository.findBySkuID(skuId);
	}
}
