package com.sample.Accessories.webServiceClients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.sample.Accessories.webservices.LeaseEligibility.GetLeaseEligibilityRequest;
import com.sample.Accessories.webservices.LeaseEligibility.GetLeaseEligibilityResponse;

public class LeaseEligibilityClient extends WebServiceGatewaySupport {
	
	 private static final Logger log = LoggerFactory.getLogger(LeaseEligibilityClient.class);

	  public GetLeaseEligibilityResponse getLeaseInformation(String accessoryId) {

		 GetLeaseEligibilityRequest request = new GetLeaseEligibilityRequest();
	     request.setAccessoryId(accessoryId);

	    log.info("Requesting location for " + accessoryId);

	    GetLeaseEligibilityResponse response = (GetLeaseEligibilityResponse) getWebServiceTemplate()
	        .marshalSendAndReceive("http://localhost:8030/ws/LeaseEligibility", request,
	            new SoapActionCallback(
	                "http://www.example.org/LeaseEligbility/getLeaseEligibilityRequest"));

	    return response;
	  }

}
