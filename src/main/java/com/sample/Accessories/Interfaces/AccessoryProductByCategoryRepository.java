package com.sample.Accessories.Interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.sample.Accessories.Entity.AccessoryProductByCategory;

@Component
public interface AccessoryProductByCategoryRepository extends JpaRepository<AccessoryProductByCategory, String>{
	
	@Query("select accCat from AccessoryProductByCategory accCat where accCat.category_id=:categoryId ")
	public List<AccessoryProductByCategory> findAccessoryProductsByCategory(@Param("categoryId") String categoryId); 
}
