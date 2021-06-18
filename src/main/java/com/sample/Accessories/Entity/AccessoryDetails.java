package com.sample.Accessories.Entity;

import java.io.Serializable;
import java.util.List;

import com.sample.Accessories.DTO.ContractInfo;

public class AccessoryDetails implements Serializable {
	
	private String productId;
	private String categoryId;
	private String skuId;
	private String esembleId;
	private String displayName;
	private Double price;
	private String details;
	private String description;
	private String imageUrl;
	private List<ContractInfo> contractInfoList;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getEsembleId() {
		return esembleId;
	}
	public void setEsembleId(String esembleId) {
		this.esembleId = esembleId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public List<ContractInfo> getContractInfoList() {
		return contractInfoList;
	}
	public void setContractInfoList(List<ContractInfo> contractInfoList) {
		this.contractInfoList = contractInfoList;
	}
	
	@Override
	public String toString() {
		return "AccessoryDetails [productId=" + productId + ", categoryId=" + categoryId + ", skuId=" + skuId
				+ ", esembleId=" + esembleId + ", displayName=" + displayName + ", price=" + price + ", details="
				+ details + ", description=" + description + ", imageUrl=" + imageUrl + "]";
	}
	
	
}
