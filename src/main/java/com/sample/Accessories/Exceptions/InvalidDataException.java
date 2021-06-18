package com.sample.Accessories.Exceptions;

public class InvalidDataException extends RuntimeException{
	
	private RestErrorVO error;

	public RestErrorVO getError() {
		return error;
	}

	public void setError(RestErrorVO error) {
		this.error = error;
	}
	
	public InvalidDataException(RestErrorVO error){
		this.error = error;
	}
	
}
