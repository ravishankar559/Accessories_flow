package com.sample.Accessories.DTO;

import com.sample.Accessories.H2Entity.ShippingInfo;

public class ShippingResponse {
	
	private ShippingInfo shippingInfo;
	
	private ShippingAddress shippingAddress;

	public ShippingInfo getShippingInfo() {
		return shippingInfo;
	}

	public void setShippingInfo(ShippingInfo shippingInfo) {
		this.shippingInfo = shippingInfo;
	}

	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

}
