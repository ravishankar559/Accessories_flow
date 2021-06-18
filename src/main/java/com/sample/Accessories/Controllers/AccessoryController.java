package com.sample.Accessories.Controllers;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sample.Accessories.DTO.AccessoriesRequest;
import com.sample.Accessories.DTO.AccessoryRequest;
import com.sample.Accessories.Entity.Accessory;
import com.sample.Accessories.Entity.AccessoryCategory;
import com.sample.Accessories.Entity.AccessoryDetails;
import com.sample.Accessories.Entity.AccessoryProductByCategory;
import com.sample.Accessories.Exceptions.InvalidDataException;
import com.sample.Accessories.Exceptions.RestErrorVO;
import com.sample.Accessories.Exceptions.UnAuthorizedException;
import com.sample.Accessories.Services.AccessoryCategoryService;
import com.sample.Accessories.Services.AccessoryProductByCategoryService;
import com.sample.Accessories.Services.AccessorySkuService;
import com.sample.Accessories.Services.AddToCartService;
import com.sample.Accessories.Services.SoapConsumerService;
import com.sample.Accessories.Services.UserAuthorityDecider;

import io.swagger.annotations.ApiOperation;

@RestController
public class AccessoryController {
	
	private static final String INVALID_CATEGORY_ID = "INAVLID CATEGORY ID";
	
	private static final String INVALID_ACCESSORY_ID = "INAVLID ACCESSORY ID";
	
	private static final String INVALID_DATA_MESSAGE = "Input Data seems to be invalid , please try with valid data";	
	
	private static final String INVALID_VERSION_ID = "INAVLID VERSION ID";
	
	private static final String NOT_AUTHORIZED = "NOT_AUTHORIZED";
	
	private static final String VERSION_NOT_SUPPORTED = " Oops Version v2 Not supported - Give us some time";
	
	
	private final Logger log = LoggerFactory.getLogger(AccessoryController.class);
	
	@Autowired
	private AccessorySkuService accessorySkuService;
	
	@Autowired
	private AccessoryCategoryService accessoryCategoryService;
	
	@Autowired
	private AccessoryProductByCategoryService accessoryProductByCategoryService;
	
	@Autowired
	private AddToCartService addToCartService;
	
	@Autowired
	private SoapConsumerService soapConsumerService;
	
	@Autowired
	private UserAuthorityDecider userAuthorityDecider;
	
	@Autowired
	private RestErrorVO error;
	
	@GetMapping(value = "/v1/accessories" , consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public MappingJacksonValue getAccessoriesv1() {
		/*List<Accessory> acclist = accessorySkuService.findAll();
		return acclist;*/
		
		List<Accessory> acclist = accessorySkuService.findAll();

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("sku_id","ens_id","list_price");

		FilterProvider filters = new SimpleFilterProvider().addFilter("AccesoryFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(acclist);

		mapping.setFilters(filters);

		return mapping;
		
		
	}
	
	@GetMapping(value = "/v2/accessories" , consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public Object getAccessoriesv2() {
		error.setErrorCode(INVALID_VERSION_ID);
		error.setErrorMessage(VERSION_NOT_SUPPORTED);
		throw new InvalidDataException(error);
	}
	
	
	@GetMapping(value = "/accessories" , consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public MappingJacksonValue getAccessoriesv3() {
		List<Accessory> acclist = accessorySkuService.findAll();

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("sku_id","ens_id");

		FilterProvider filters = new SimpleFilterProvider().addFilter("AccesoryFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(acclist);

		mapping.setFilters(filters);

		return mapping;
		
	}
	
	
	/*@GetMapping(value = "/accessories")
	public Object getAccessories() {
		List<Accessory> acclist = accessorySkuService.findAll();
		return acclist;
	}*/
	
	@GetMapping(value = "/accessories/{skuId}" , consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public Object getAccessoryById(@PathVariable(value = "skuId") String skuId){
		AccessoryDetails accessoryDetails = accessorySkuService.findBySkuID(skuId);
		if(null == accessoryDetails) {
			error.setErrorCode(INVALID_ACCESSORY_ID);
			error.setErrorMessage(INVALID_DATA_MESSAGE);
			throw new InvalidDataException(error);
		}
		accessoryDetails = soapConsumerService.getContractDetails(skuId,accessoryDetails);
		return accessoryDetails;
	}
	
	@ApiOperation(response = AccessoryCategory.class, responseContainer = "List" , value = "Get list of Accessory Catergories" )
	@GetMapping(value = "/accessoryCategories" , consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public Object getAccessoryCategories() {
		List <AccessoryCategory> accCatList = accessoryCategoryService.findAll();
		return accCatList;
	}
	
	@ApiOperation(response = AccessoryCategory.class, value = "Get Accessory Catergory by Id" )
	@GetMapping(value = "/accessoryCategories/{categoryId}" , consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public Object getAccessoryCategoryById(@PathVariable(value = "categoryId") String categoryId) {
		AccessoryCategory accCat = accessoryCategoryService.findByCategoryId(categoryId);
		if(null == accCat) {
			error.setErrorCode(INVALID_CATEGORY_ID);
			error.setErrorMessage(INVALID_DATA_MESSAGE);
			throw new InvalidDataException(error);
		}
		return accCat;
	}
	
	@ApiOperation(response = AccessoryDetails.class, responseContainer = "List", value = "Get Accessories by Catergory Id" )
	@GetMapping(value = "/accessoryCategories/{categoryId}/accessories" , consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public Object getAccessoriesByCategory(@PathVariable(value = "categoryId") String categoryId) {
		AccessoryCategory accCat = accessoryCategoryService.findByCategoryId(categoryId);
		if(null == accCat) {
			error.setErrorCode(INVALID_CATEGORY_ID);
			error.setErrorMessage(INVALID_DATA_MESSAGE);
			throw new InvalidDataException(error);
		}
		List<AccessoryProductByCategory> accessoryProductsByCategory = accessoryProductByCategoryService.findAccessoryProductsByCategory(categoryId);
		
		List<AccessoryDetails> accessoriesDetails = accessoryProductByCategoryService.getAccessoriesDetails(accessoryProductsByCategory,categoryId);
		return accessoriesDetails;
	}
	
	@ApiOperation(value = "clear service level cache")
	@DeleteMapping(value = "/removeCache" , consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Object> removeCache() {
		accessoryProductByCategoryService.removeCache();
		accessoryCategoryService.removeCache();
		return ResponseEntity.status(204).body(null);
	}
	
	@PostMapping(value = "/addToCart" , consumes = MediaType.APPLICATION_JSON , produces = MediaType.APPLICATION_JSON)
	public Object addToCart(@RequestBody AccessoriesRequest accessoriesRequest) {
//		String authority = userAuthorityDecider.getUserRole(SecurityContextHolder.getContext().getAuthentication());
//		//Not authorized to create cart
//		if(authority == null) {
//			error.setErrorCode(NOT_AUTHORIZED);
//			error.setErrorMessage("Please login to add accessories to cart");
//			throw new UnAuthorizedException(error);
//		}
		addToCartService.addToCart(accessoriesRequest);
		String message = "Accessory Packages are added to cart";
		return message;
	}
}
