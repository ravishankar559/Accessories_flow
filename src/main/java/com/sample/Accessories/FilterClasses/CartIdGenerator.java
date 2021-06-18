package com.sample.Accessories.FilterClasses;

import java.io.IOException;
import java.security.SecureRandom;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.sample.Accessories.DTO.Cart;

@Configuration
@Order(1)
public class CartIdGenerator implements Filter {

	@Autowired
	private Cart cart;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession(true);
		if(null == session.getAttribute("authStatus")) {
			session.setAttribute("authStatus", false);
		}
		if(cart.getCartId() == null)
			generateCartId();
		filterChain.doFilter(request, response);
	}

	private void generateCartId() {
		// TODO Auto-generated method stub
		
		String allCharacters = "0123456789";
		int size = 7;
		int allCharactersSize = 10;
		char[] randomValue = new char[size];
		SecureRandom random = new SecureRandom();
		for(int i=0; i<size; i++) {
			randomValue[i] = allCharacters.charAt(random.nextInt(allCharactersSize));
		}
		String randomValueString = new String(randomValue);
		cart.setCartId("cart"+randomValueString);

	}

}
