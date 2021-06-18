package com.sample.Accessories.Entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "spr_acc_prd_acc_cat")
public class AccessoryProductByCategory implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private String product_id;
	private String category_id;
	
	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	@Override
	public String toString() {
		return "AccessoryProductByCategory [product_id=" + product_id + ", category_id=" + category_id + "]";
	}
	
	
}
