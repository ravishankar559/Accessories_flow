package com.sample.Accessories.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.sample.Accessories.DTOs.Accessory;

@Component
public interface AccessorySkuRepository extends JpaRepository<Accessory , String> {

	@Query("select acc from Accessory acc where acc.sku_id=:sku_id ")
	Accessory findBySkuID(@Param("sku_id") String sku_id);
}
