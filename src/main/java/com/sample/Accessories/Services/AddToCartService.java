package com.sample.Accessories.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.Accessories.DTO.AccessoriesRequest;
import com.sample.Accessories.DTO.AccessoryRequest;
import com.sample.Accessories.DTO.Cart;
import com.sample.Accessories.DTO.ContractInfo;
import com.sample.Accessories.Entity.AccessoryDetails;
import com.sample.Accessories.Exceptions.InvalidDataException;
import com.sample.Accessories.Exceptions.RestErrorVO;
import com.sample.Accessories.H2Entity.AccessoryPackage;
import com.sample.Accessories.H2Interfaces.AccessoryPackageRepository;

@Service
public class AddToCartService {
	
	private static final String INVALID_ACCESSORY_ID = "INVALID ACCESSORY ID";
	
	private static final String INVALID_INPUT = "INVALID INPUT DATA";
	
	private static final String INVALID_CONTRACT_TERM = "INVALID CONTRACT TERM";
	
	private static final String INVALID_DATA_MESSAGE = "Input Data seems to be invalid , please try with valid data";
	
	@Autowired
	private AccessorySkuService accessorySkuService;
	
	@Autowired
	private AccessoryPackageRepository accessoryPackageRepository;
	
	@Autowired
	private SoapConsumerService soapConsumerService;
	
	@Autowired
	private RestErrorVO error;
	
	@Autowired
	private Cart cart;

	public void addToCart(AccessoriesRequest accessoriesRequest) {
		boolean isValidRequest = vaidateAccessoryRequest(accessoriesRequest);
		if(!isValidRequest)
			InvalidDataException(INVALID_INPUT);
		for(AccessoryRequest accessoryRequest : accessoriesRequest.getAccessoriesRequest()) {
			AccessoryDetails accessoryDetails = accessorySkuService.findBySkuID(accessoryRequest.getAccessory_id());
			if(accessoryDetails == null)
				InvalidDataException(INVALID_ACCESSORY_ID);
			accessoryDetails = soapConsumerService.getContractDetails(accessoryRequest.getAccessory_id(),accessoryDetails);
			ContractInfo selectedContract = validateContractTerm(accessoryDetails.getContractInfoList(),accessoryRequest.getContractName());
			AccessoryPackage existingAccessory = 
					accessoryPackageRepository.findAccessoryByAccessoryIdAndCartIdAndContractTerm(accessoryRequest.getAccessory_id(),accessoryRequest.getContractName(),cart.getCartId());
			if(existingAccessory == null) {
				AccessoryPackage accessoryPackage = new AccessoryPackage();
				accessoryPackage.setAccessoryId(accessoryDetails.getSkuId());
				accessoryPackage.setCartId(cart.getCartId());
				accessoryPackage.setDownPayment(selectedContract.getDownPayment());
				accessoryPackage.setAccessoryName(accessoryDetails.getDisplayName());
				accessoryPackage.setMonthlyInstallment(selectedContract.getMonthlyInstallment());
				accessoryPackage.setQuantity(accessoryRequest.getQuantity());
				accessoryPackage.setContractName(accessoryRequest.getContractName());
				accessoryPackage.setContractTerm(selectedContract.getContractTerm());
				accessoryPackageRepository.save(accessoryPackage);
				if(cart.getAccessories() == null)
					cart.setAccessories(new ArrayList<AccessoryPackage>());
				cart.addAccessory(accessoryPackage);
			}else {
				existingAccessory.setQuantity(existingAccessory.getQuantity()+accessoryRequest.getQuantity());
				accessoryPackageRepository.save(existingAccessory);
				updateCart(existingAccessory,accessoryRequest);
			}
			cart.setTotalDownPaymet(cart.getTotalDownPaymet()+selectedContract.getDownPayment()*accessoryRequest.getQuantity());
			cart.setTotalMonthlyInstallment(cart.getTotalMonthlyInstallment()+selectedContract.getMonthlyInstallment()*accessoryRequest.getQuantity());
		}
	}
	
	private ContractInfo validateContractTerm(List<ContractInfo> contractInfoList, String contractName) {
		for(ContractInfo contractInfo : contractInfoList) {
			if(contractInfo.getContractName().equals(contractName))
				return contractInfo;
		}
		InvalidDataException(INVALID_CONTRACT_TERM);
		return null;
	}

	private void updateCart(AccessoryPackage existingAccessory , AccessoryRequest accessoryRequest) {
		// TODO Auto-generated method stub
		if(!cart.getAccessories().isEmpty()) {
			for(AccessoryPackage accessoryPackage : cart.getAccessories()) {
				if(accessoryPackage.getPackageId().equals(existingAccessory.getPackageId())) {
					accessoryPackage.setQuantity(accessoryPackage.getQuantity() + accessoryRequest.getQuantity());
					break;
				}
			}
		}	
	}
	
	private void InvalidDataException(String errorCode) {
		// TODO Auto-generated method stub
		error.setErrorCode(errorCode);
		error.setErrorMessage(INVALID_DATA_MESSAGE);
		throw new InvalidDataException(error);
		
	}

	public boolean vaidateAccessoryRequest(AccessoriesRequest accessoriesRequest) {
		for(AccessoryRequest accessoryRequest : accessoriesRequest.getAccessoriesRequest()) {
			if(accessoryRequest.getQuantity() <= 0 || accessoryRequest.getAccessory_id() == null)
				return false;
		}
		return true;
	}

}
