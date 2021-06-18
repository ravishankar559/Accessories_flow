package com.sample.Accessories.Entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;

@Entity
@Table(name = "spr_sku_acc")
@JsonFilter("AccesoryFilter")
public class Accessory implements Serializable {

	@Id 
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private String sku_id;
	private String ens_id;
	private double list_price;
	
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
	@Override
	public String toString() {
		return "Accessory [sku_id=" + sku_id + ", ens_id=" + ens_id + ", list_price=" + list_price + "]";
	}
	
}
