package com.sample.Accessories.H2Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "AccessoryPackage" )
public class AccessoryPackage {
	
	@Id 
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	private String packageId;
	private String accessoryId;
	private String accessoryName;
	private String contractName;
	private int contractTerm;
	@JsonIgnore
	private String cartId;
	private double downPayment;
	private double monthlyInstallment;
	private int quantity;
	
	
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getAccessoryId() {
		return accessoryId;
	}
	public void setAccessoryId(String accessoryId) {
		this.accessoryId = accessoryId;
	}
	public String getAccessoryName() {
		return accessoryName;
	}
	public void setAccessoryName(String accessoryName) {
		this.accessoryName = accessoryName;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public int getContractTerm() {
		return contractTerm;
	}
	public void setContractTerm(int contractTerm) {
		this.contractTerm = contractTerm;
	}
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public double getDownPayment() {
		return downPayment;
	}
	public void setDownPayment(double downPayment) {
		this.downPayment = downPayment;
	}
	public double getMonthlyInstallment() {
		return monthlyInstallment;
	}
	public void setMonthlyInstallment(double monthlyInstallment) {
		this.monthlyInstallment = monthlyInstallment;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
