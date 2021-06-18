package com.sample.Accessories.Entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "spr_cat_acc")
public class AccessoryCategory implements Comparable<AccessoryCategory> , Serializable{
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private String category_id;
	private String display_name;
	@JsonIgnore
	private boolean display_on_front_end;
	@JsonIgnore
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

	public boolean isDisplay_on_front_end() {
		return display_on_front_end;
	}

	public void setDisplay_on_front_end(boolean display_on_front_end) {
		this.display_on_front_end = display_on_front_end;
	}

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

	@Override
	public String toString() {
		return "AccessoryCategory [category_id=" + category_id + ", display_name=" + display_name
				+ ", display_on_front_end=" + display_on_front_end + ", display_order=" + display_order + "]";
	}

}
