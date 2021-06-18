package com.sample.Accessories.DTO;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sample.Accessories.H2Entity.AccessoryPackage;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@JsonIgnoreProperties({"targetClass", "targetSource", "targetObject", "advisors", "frozen", "exposeProxy", "preFiltered", "proxiedInterfaces", "proxyTargetClass" , "advisorCount"})
public class Cart {
	
	private String cartId;
	private double totalDownPaymet;
	private double totalMonthlyInstallment;
	private List<AccessoryPackage> accessories;
	
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public double getTotalDownPaymet() {
		return totalDownPaymet;
	}
	public void setTotalDownPaymet(double totalDownPaymet) {
		this.totalDownPaymet = totalDownPaymet;
	}
	public double getTotalMonthlyInstallment() {
		return totalMonthlyInstallment;
	}
	public void setTotalMonthlyInstallment(double totalMonthlyInstallment) {
		this.totalMonthlyInstallment = totalMonthlyInstallment;
	}
	public List<AccessoryPackage> getAccessories() {
		return accessories;
	}
	public void setAccessories(List<AccessoryPackage> accessories) {
		this.accessories = accessories;
	}
	public void addAccessory(AccessoryPackage accessory) {
		this.accessories.add(accessory);
	}
	
}
