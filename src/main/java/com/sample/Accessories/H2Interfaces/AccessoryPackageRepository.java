package com.sample.Accessories.H2Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sample.Accessories.H2Entity.AccessoryPackage;

public interface AccessoryPackageRepository extends JpaRepository<AccessoryPackage, String> {

	@Query("select acc from AccessoryPackage acc where acc.accessoryId=:accessoryId and acc.contractName=:contractName and acc.cartId=:cartId")
	AccessoryPackage findAccessoryByAccessoryIdAndCartIdAndContractTerm(String accessoryId, String contractName, String cartId);
	
	@Query("delete AccessoryPackage acc where acc.cartId=:cartId")
	void deleteAccessoryByCartID(String cartId);

}
