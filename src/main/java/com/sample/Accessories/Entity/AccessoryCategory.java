package com.sample.Accessories.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "spr_cat_acc")
public class AccessoryCategory implements Comparable<AccessoryCategory>{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private String category_id;
	private String display_name;
	private boolean display_on_front_end;
	private Integer display_order;
	
	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	@JsonIgnore
	@JsonProperty(value = "display_on_front_end")
	public boolean isDisplay_on_front_end() {
		return display_on_front_end;
	}

	public void setDisplay_on_front_end(boolean display_on_front_end) {
		this.display_on_front_end = display_on_front_end;
	}

	@JsonIgnore
	@JsonProperty(value = "dispaly_order")
	public Integer getDisplay_order() {
		return display_order;
	}

	public void setDisplay_order(Integer display_order) {
		this.display_order = display_order;
	}

	@Override
	public int compareTo(AccessoryCategory accCat) {
		if(accCat.display_order == null && this.display_order == null)
			return 0;
		if(accCat.display_order == null)
			return -1;
		if(this.display_order == null)
			return 1;
		
		if(accCat.display_order < this.display_order)
			return 1;
		else if(accCat.display_order > this.display_order)
			return -1;
		else
			return 0;
	}

}
