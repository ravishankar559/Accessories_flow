package com.sample.Accessories.Exceptions;

public class UnAuthorizedException extends RuntimeException {
	
	private RestErrorVO error;

	public RestErrorVO getError() {
		return error;
	}

	public void setError(RestErrorVO error) {
		this.error = error;
	}
	
	public UnAuthorizedException(RestErrorVO error){
		this.error = error;
	}

}
