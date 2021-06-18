package com.sample.Accessories.DTO;

public class AccessoryRequest {
	
	private String accessory_id;
	private String contractName;
	private int quantity;
	
	public String getAccessory_id() {
		return accessory_id;
	}
	public void setAccessory_id(String accessory_id) {
		this.accessory_id = accessory_id;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
