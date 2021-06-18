package com.sample.Accessories.Entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "spr_prd_acc")
public class AccessoryProductDetails implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private String product_id;
	private String manufacturer;
	private String default_sku_id;
	private String cms_display_name;
	private String longdescription;
	private String shrt_desc;
	private String image_base_name;

	@OneToOne
	@JoinColumn(name = "default_sku_id" , referencedColumnName = "sku_id" , insertable = false, updatable = false)
	private Accessory accessory;
	
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getDefault_sku_id() {
		return default_sku_id;
	}
	public void setDefault_sku_id(String default_sku_id) {
		this.default_sku_id = default_sku_id;
	}
	public String getCms_display_name() {
		return cms_display_name;
	}
	public void setCms_display_name(String cms_display_name) {
		this.cms_display_name = cms_display_name;
	}
	public String getLongdescription() {
		return longdescription;
	}
	public void setLongdescription(String longdescription) {
		this.longdescription = longdescription;
	}

	public String getShrt_desc() {
		return shrt_desc;
	}
	public void setShrt_desc(String shrt_desc) {
		this.shrt_desc = shrt_desc;
	}
	public String getImage_base_name() {
		return image_base_name;
	}
	public void setImage_base_name(String image_base_name) {
		this.image_base_name = image_base_name;
	}
	
	public Accessory getAccessory() {
		return accessory;
	}
	public void setAccessory(Accessory accessory) {
		this.accessory = accessory;
	}
	@Override
	public String toString() {
		return "AccessoryProductDetails [product_id=" + product_id + ", manufacturer=" + manufacturer
				+ ", default_sku_id=" + default_sku_id + ", cms_display_name=" + cms_display_name + ", longdescription="
				+ longdescription + ", shrt_desc=" + shrt_desc + ", image_base_name=" + image_base_name + ", accessory="
				+ accessory + "]";
	}
	
}
