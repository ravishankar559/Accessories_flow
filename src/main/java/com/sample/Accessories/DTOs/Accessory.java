package com.sample.Accessories.DTOs;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "spr_sku_acc")
public class Accessory {

	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private String sku_id;
	private String ens_id;
	private double list_price;
	private String inventory;
	
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}
	public String getEns_id() {
		return ens_id;
	}
	public void setEns_id(String ens_id) {
		this.ens_id = ens_id;
	}
	public double getList_price() {
		return list_price;
	}
	public void setList_price(double list_price) {
		this.list_price = list_price;
	}
	public String getInventory() {
		return inventory;
	}
	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
	

}
