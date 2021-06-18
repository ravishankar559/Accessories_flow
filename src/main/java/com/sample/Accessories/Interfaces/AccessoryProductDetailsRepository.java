package com.sample.Accessories.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sample.Accessories.Entity.AccessoryDetails;
import com.sample.Accessories.Entity.AccessoryProductDetails;

public interface AccessoryProductDetailsRepository extends JpaRepository<AccessoryProductDetails, String> {

	@Query("select accCat from AccessoryProductDetails accCat where accCat.product_id=:productId ")
	public AccessoryProductDetails findByProductId(@Param("productId") String productId);

	@Query("select accCat from AccessoryProductDetails accCat where accCat.default_sku_id=:skuId ")
	public AccessoryProductDetails findBySkuId(String skuId);
}
