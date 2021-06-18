package com.sample.Accessories.DTO;

import java.io.Serializable;

public class ContractInfo implements Serializable {
	
	private String contractName;
	
	private int contractTerm;
	
	private double downPayment;
	
	private double monthlyInstallment;

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

}
