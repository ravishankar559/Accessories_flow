package com.sample.Accessories.Controllers;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sample.Accessories.DTO.Cart;
import com.sample.Accessories.Exceptions.InvalidDataException;
import com.sample.Accessories.Exceptions.RestErrorVO;
import com.sample.Accessories.H2Interfaces.AccessoryPackageRepository;

@RestController
public class CartController {
	
	private static final String INVALID_CART_ID = "INAVLID CART ID";
	
	private static final String INVALID_DATA_MESSAGE = "Input Data seems to be invalid , please try with valid data";	
	
	@Autowired
	private Cart cart;
	
	@Autowired
	private AccessoryPackageRepository accessoryPackageRepository;
	
	@Autowired
	private RestErrorVO error;
	
	@GetMapping(value = "/cart" , consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public Cart getCart() {
		return cart;
	}
	
	@DeleteMapping(value = "/cart/{cartId}" , consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public Object getDeleteCart(@PathVariable("cartId") String cartId) {
		if(cartId.equals(cart.getCartId())) {
			cart.setTotalDownPaymet(0);
			cart.setTotalMonthlyInstallment(0);
			cart.setAccessories(null);
			accessoryPackageRepository.deleteAccessoryByCartID(cartId);
			String message = "Cart has been deleted successfully";
			return message;
		}else {
			error.setErrorCode(INVALID_CART_ID);
			error.setErrorMessage(INVALID_DATA_MESSAGE);
			throw new InvalidDataException(error);
		}
	}
}
