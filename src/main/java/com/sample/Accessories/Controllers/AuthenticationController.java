package com.sample.Accessories.Controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sample.Accessories.DTO.AuthenticationRequest;
import com.sample.Accessories.DTO.UserCreationRequest;
import com.sample.Accessories.Exceptions.InvalidDataException;
import com.sample.Accessories.Exceptions.RestErrorVO;
import com.sample.Accessories.Exceptions.UnAuthorizedException;
import com.sample.Accessories.Services.UserAuthorityDecider;


@RestController
public class AuthenticationController {
	
	private static final String INVALID_INPUT_DATA = "INVALID INPUT DATA";
	
	private static final String NOT_AUTHORIZED = "NOT_AUTHORIZED";
	
	private static final String ALREADY_AUTHENTICTED = "ALREADY_AUTHENTICTED";
	
	private static final String USER_NAME_INVALID = "UserName not available";
	
	private static final String USER_NAME_INVALID_MESSAGE = "User exists with the provided username, please try with another username";
		
	@Autowired
	private RestErrorVO errorVO;
	
	@Autowired
	private AuthenticationManager authenticationManager;
		 
	@Autowired
	private UserAuthorityDecider userAuthorityDecider;
	
	@RequestMapping(value ="/authentication", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
	public Object getAuthenticated(@Context HttpServletRequest request, @RequestBody AuthenticationRequest authenticationRequest) {
		
		HttpSession session = request.getSession();
		if((boolean) session.getAttribute("authStatus")) {
			errorVO.setErrorCode(ALREADY_AUTHENTICTED);
			errorVO.setErrorMessage("Customer already authenticated");
			throw new UnAuthorizedException(errorVO);
		}
		Authentication authentication = null;
		if(null != authenticationRequest && null != authenticationRequest.getUsername() && null != authenticationRequest.getPassword()) {
			try {
				authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			}catch (Throwable e) {
				errorVO.setErrorMessage(e.getMessage());
			}
		}
		if(authentication == null) {
			errorVO.setErrorCode(NOT_AUTHORIZED);
			throw new UnAuthorizedException(errorVO);
		}else {
			SecurityContextHolder.getContext().setAuthentication(authentication);
			request.getSession().setAttribute("authStatus",true);
		}
		
//		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("username","authorities","accountNonExpired","accountNonLocked","enabled");
//		FilterProvider filters = new SimpleFilterProvider().addFilter("PrincipalFilter", filter);
//		MappingJacksonValue mapping = new MappingJacksonValue(authentication.getPrincipal());
//		mapping.setFilters(filters);
		String message = "Logged in successfully";
		
		return message;
	}
	
	@RequestMapping(value ="/logouthandler", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Object> logoutOperation(@Context HttpServletRequest request,@Context HttpServletResponse response){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)){
			new SecurityContextLogoutHandler().logout(request, response, authentication);
			request.getSession().setAttribute("authStatus",false);
		}else {
			request.getSession().invalidate();
		}
		return ResponseEntity.ok(null);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON )
	public Object createUser(@RequestBody UserCreationRequest userCreationRequest){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try {
			if(authentication instanceof AnonymousAuthenticationToken) {
				boolean isRequestValid = userAuthorityDecider.validateRequest(userCreationRequest);
				if(!isRequestValid) {
					errorVO.setErrorCode(INVALID_INPUT_DATA);
					errorVO.setErrorMessage("Input data is invalid");
					throw new InvalidDataException(errorVO);	
				}
				userAuthorityDecider.createUser(userCreationRequest);
			}else {
				if(userAuthorityDecider.isAuthorizedToCreateUser(
						(Collection<GrantedAuthority>)(((LdapUserDetailsImpl)authentication.getPrincipal()).getAuthorities()) , 
						userCreationRequest.getAuthoritiesRequested())) {
					userAuthorityDecider.createUser(userCreationRequest);
				}else {
					errorVO.setErrorCode(NOT_AUTHORIZED);
					errorVO.setErrorMessage("Not authorized to create user");
					throw new UnAuthorizedException(errorVO);
				}
			}
		}catch (NameAlreadyBoundException e) {
			errorVO.setErrorCode(USER_NAME_INVALID);
			errorVO.setErrorMessage(USER_NAME_INVALID_MESSAGE);
			throw new InvalidDataException(errorVO);
		}
		
		String message = "User careated successfully";
		
		return message;
	}
	
}


