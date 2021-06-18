package com.sample.Accessories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sample.Accessories.Exceptions.ErrorClass;
import com.sample.Accessories.Exceptions.InvalidDataException;
import com.sample.Accessories.Exceptions.RestErrorVO;
import com.sample.Accessories.Exceptions.UnAuthorizedException;

@ControllerAdvice
public class RestExceptionHandler{

	private static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";

	@Autowired
	private RestErrorVO errorVO;

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<Object> handleInvalidDataException(InvalidDataException ex){
		ErrorClass errorClass = new ErrorClass();
		errorClass.addError(ex.getError());
		return ResponseEntity.status(422).body(errorClass);
	}
	
	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseEntity<Object> handleUnAuthorizedException(UnAuthorizedException ex) {
		ErrorClass errorClass = new ErrorClass();
		errorClass.addError(ex.getError());
		return ResponseEntity.status(401).body(errorClass);
	}

}
