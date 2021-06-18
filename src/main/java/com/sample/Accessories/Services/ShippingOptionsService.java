package com.sample.Accessories.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.Accessories.DTO.ShippingAddress;
import com.sample.Accessories.DTO.ShippingRequest;
import com.sample.Accessories.DTO.ShippingResponse;
import com.sample.Accessories.H2Entity.ShippingInfo;
import com.sample.Accessories.H2Entity.UserShippingDetails;
import com.sample.Accessories.H2Interfaces.ShippingInfoRepository;
import com.sample.Accessories.H2Interfaces.UserShippingDetailsRepository;

@Service
public class ShippingOptionsService {
	
	@Autowired
	private ShippingInfoRepository shippingInfoRepository;
	
	@Autowired
	private UserShippingDetailsRepository userShippingDetailsRepository;

	public List<ShippingInfo> getAvailableShippingOptions() {
		// TODO Auto-generated method stub
		List<ShippingInfo> shippingInfoList = shippingInfoRepository.findAll();
		return shippingInfoList;
	}

	public void saveShippingToOrder(ShippingRequest shippingRequest, String cartId) {
		// TODO Auto-generated method stub
		UserShippingDetails userShippingDetails = new UserShippingDetails();
		userShippingDetails.setCartId(cartId);
		userShippingDetails.setShippingId(shippingRequest.getShippingId());
		userShippingDetails.setAddressLine1(shippingRequest.getShippingAddress().getAddressLine1());
		userShippingDetails.setAddressLine2(shippingRequest.getShippingAddress().getAddressLine2());
		userShippingDetails.setCity(shippingRequest.getShippingAddress().getCity());
		userShippingDetails.setState(shippingRequest.getShippingAddress().getState());
		userShippingDetails.setZipcode(shippingRequest.getShippingAddress().getZipcode());
		userShippingDetailsRepository.save(userShippingDetails);
	}

	public double updateShippingToOrder(ShippingRequest shippingRequest, String cartId) {
		// TODO Auto-generated method stub
		 double oldShippingCharge = 0;
		 Optional<UserShippingDetails> userShippingDetails = userShippingDetailsRepository.findById(cartId);
		 if(userShippingDetails.isPresent()) {
			 Optional<ShippingInfo> shippingInfo = shippingInfoRepository.findById(userShippingDetails.get().getShippingId());
			 if(shippingInfo.isPresent()) {
				 oldShippingCharge = shippingInfo.get().getShippingPrice();
			 }
			 userShippingDetails.get().setShippingId(shippingRequest.getShippingId());
			 userShippingDetails.get().setAddressLine1(shippingRequest.getShippingAddress().getAddressLine1());
			 userShippingDetails.get().setAddressLine2(shippingRequest.getShippingAddress().getAddressLine2());
			 userShippingDetails.get().setCity(shippingRequest.getShippingAddress().getCity());
			 userShippingDetails.get().setState(shippingRequest.getShippingAddress().getState());
			 userShippingDetails.get().setZipcode(shippingRequest.getShippingAddress().getZipcode());
			 
		 }
		 return oldShippingCharge;
	}
	
	public ShippingResponse getShippingInfo(String cartId) {
		// TODO Auto-generated method stub
		Optional<ShippingInfo> shippingInfo = null;
		ShippingResponse shippingResponse = new ShippingResponse();
		ShippingAddress shippingAddress = new ShippingAddress();
		Optional<UserShippingDetails> userShippingDetails = userShippingDetailsRepository.findById(cartId);
		if(userShippingDetails.isPresent()) {
			shippingAddress.setAddressLine1(userShippingDetails.get().getAddressLine1());
			shippingAddress.setAddressLine2(userShippingDetails.get().getAddressLine2());
			shippingAddress.setCity(userShippingDetails.get().getCity());
			shippingAddress.setState(userShippingDetails.get().getState());
			shippingAddress.setZipcode(userShippingDetails.get().getZipcode());
			shippingResponse.setShippingAddress(shippingAddress);
			shippingInfo = shippingInfoRepository.findById(userShippingDetails.get().getShippingId());
			shippingResponse.setShippingInfo(shippingInfo.get());
		}
		return shippingResponse;
	}

}
