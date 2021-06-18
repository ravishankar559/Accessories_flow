package com.sample.Accessories.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.sample.Accessories.Entity.Accessory;

@Component
public interface AccessorySkuRepository extends JpaRepository<Accessory , String> {

	@Query("select acc from Accessory acc where acc.sku_id=:skuId and retired = 0 and condition = 300001 and inventory = 200001 ")
	Accessory findBySkuID(@Param("skuId") String skuId);
}
