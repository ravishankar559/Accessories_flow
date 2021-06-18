package com.sample.Accessories.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.sample.Accessories.DTO.ContractInfo;
import com.sample.Accessories.Entity.AccessoryDetails;
import com.sample.Accessories.webServiceClients.LeaseEligibilityClient;
import com.sample.Accessories.webServiceClients.LeaseEligibilityConfiguration;
import com.sample.Accessories.webservices.LeaseEligibility.GetLeaseEligibilityResponse;
import com.sample.Accessories.webservices.LeaseEligibility.LeaseInfoType;

@Component
public class SoapConsumerService {
	
	private static final String FULL_PAYMENT = "full payment";
	
	@Autowired
	private LeaseEligibilityConfiguration leaseEligibilityConfiguration;

	@Cacheable(cacheNames = "leaseEligibilityService" , key = "#skuId")
	public AccessoryDetails getContractDetails(String skuId, AccessoryDetails accessoryDetails) {
		// TODO Auto-generated method stub
		LeaseEligibilityClient leaseEligibilityClient = leaseEligibilityConfiguration.leaseEligibilityClient(leaseEligibilityConfiguration.marshaller());
		GetLeaseEligibilityResponse response = leaseEligibilityClient.getLeaseInformation(skuId);
		List<ContractInfo> contractInfoList = new ArrayList<ContractInfo>();
		ContractInfo contractInfo = new ContractInfo();
		contractInfo.setContractName(FULL_PAYMENT);
		contractInfo.setContractTerm(0);
		contractInfo.setDownPayment(accessoryDetails.getPrice());
		contractInfo.setMonthlyInstallment(0);
		contractInfoList.add(contractInfo);
		if(response.getAccessoryId().equals(skuId)) {
			List<LeaseInfoType> leaseInfoList = response.getLeaseInfo();
			if(leaseInfoList != null) {
				for(LeaseInfoType leaseInfo : leaseInfoList) {
					contractInfo = new ContractInfo();
					contractInfo.setContractName(leaseInfo.getContractName());
					contractInfo.setContractTerm(leaseInfo.getContractTerm());
					contractInfo.setDownPayment(leaseInfo.getDownPayment());
					contractInfo.setMonthlyInstallment(leaseInfo.getMonthlyInstallment());
					contractInfoList.add(contractInfo);
				}	
			}	
		}
		accessoryDetails.setContractInfoList(contractInfoList);
		return accessoryDetails;
	}
}
