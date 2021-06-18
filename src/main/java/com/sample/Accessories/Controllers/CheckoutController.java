package com.sample.Accessories.Controllers;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.Accessories.DTO.Cart;
import com.sample.Accessories.DTO.ShippingRequest;
import com.sample.Accessories.DTO.ShippingResponse;
import com.sample.Accessories.Exceptions.InvalidDataException;
import com.sample.Accessories.Exceptions.RestErrorVO;
import com.sample.Accessories.H2Entity.ShippingInfo;
import com.sample.Accessories.Services.ShippingOptionsService;

@RestController
@Validated
@RequestMapping("/orders")
public class CheckoutController {
	
	private static final String INVALID_CART_ID = "INAVALID CART ID";
	
	private static final String INVALID_SHIPPING_ID = "INAVALID SHIPPING ID";
	
	private static final String INVALID_DATA_MESSAGE = "Input Data seems to be invalid , please try with valid data";	
	
	@Autowired
	private Cart cart;
	
	@Autowired
	private RestErrorVO error;
	
	@Autowired
	private ShippingOptionsService shippingOptionsService;
	
	@GetMapping(value = "/{cartId}/shipping-options" , consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public Object getShippingOptions(@PathVariable("cartId") String cartId) {
		validateCartId(cartId);
		List<ShippingInfo> shippingInfoList = shippingOptionsService.getAvailableShippingOptions();
		return shippingInfoList;
	}
	
	private void validateCartId(String cartId) {
		if(!cart.getCartId().equals(cartId)) {
			error.setErrorCode(INVALID_CART_ID);
			error.setErrorMessage(INVALID_DATA_MESSAGE);
			throw new InvalidDataException(error);
		}
	}

	@PostMapping(value = "/{cartId}/shipping", consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public Object saveShippingInfo(@PathVariable("cartId") String cartId , @RequestBody ShippingRequest shippingRequest) {
		validateCartId(cartId);
		List<ShippingInfo> shippingInfoList = shippingOptionsService.getAvailableShippingOptions();
		double shippingCharge = getShippingPrice(shippingInfoList, shippingRequest.getShippingId());
		//validteAddressInfo(shippingRequest.getShippingAddress());
		shippingOptionsService.saveShippingToOrder(shippingRequest,cartId);
		cart.setTotalDownPaymet(cart.getTotalDownPaymet()+shippingCharge);
		String message = "Shipping Information saved to order";
		return message;
	}
	
	@GetMapping(value = "/{cartId}/shipping", consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public Object getShippingInfo(@PathVariable("cartId") String cartId) {
		validateCartId(cartId);
		ShippingResponse shippingResponse = shippingOptionsService.getShippingInfo(cartId);
		return shippingResponse;
	}

	@PutMapping(value = "/{cartId}/shipping", consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public Object updateShippingInfo(@PathVariable("cartId") String cartId , @RequestBody ShippingRequest shippingRequest) {
		validateCartId(cartId);
		List<ShippingInfo> shippingInfoList = shippingOptionsService.getAvailableShippingOptions();
		double shippingCharge = getShippingPrice(shippingInfoList, shippingRequest.getShippingId());
		//validteAddressInfo(shippingRequest.getShippingAddress());
		double oldShippingCharge = shippingOptionsService.updateShippingToOrder(shippingRequest,cartId);
		cart.setTotalDownPaymet(cart.getTotalDownPaymet()+shippingCharge-oldShippingCharge);
		String message = "Shipping Information updated to order";
		return message;
	}

	private double getShippingPrice(List<ShippingInfo> shippingInfoList, String shippingId) {
		// TODO Auto-generated method stub
		boolean isValid = false;
		double shippingCharge = 0;
		for(ShippingInfo shippingInfo :shippingInfoList) {
			if(shippingInfo.getShippingId().equals(shippingId)) {
				isValid = true;
				shippingCharge = shippingInfo.getShippingPrice();
				break;
			}	
		}
		if(!isValid) {
			error.setErrorCode(INVALID_SHIPPING_ID);
			error.setErrorMessage(INVALID_DATA_MESSAGE);
			throw new InvalidDataException(error);
		}
		return shippingCharge;
	}

}
