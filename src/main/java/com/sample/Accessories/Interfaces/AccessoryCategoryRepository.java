package com.sample.Accessories.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.sample.Accessories.Entity.AccessoryCategory;

@Component
public interface AccessoryCategoryRepository extends JpaRepository<AccessoryCategory, String> {

	@Query("select accCat from AccessoryCategory accCat where accCat.category_id=:categoryId ")
	public AccessoryCategory findByCategoryId(@Param("categoryId") String categoryId);
	
}
