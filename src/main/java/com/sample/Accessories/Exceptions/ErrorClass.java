package com.sample.Accessories.Exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ErrorClass {
	
	private List<RestErrorVO> errors;
	
	public List<RestErrorVO> getErrors() {
		return errors;
	}

	public void setErrors(List<RestErrorVO> error) {
		this.errors = error;
	}

	public void addError(RestErrorVO errorVO) {
		if(this.errors == null) {
			this.errors = new ArrayList<RestErrorVO>();
		}
		this.errors.add(errorVO);
	}

}
