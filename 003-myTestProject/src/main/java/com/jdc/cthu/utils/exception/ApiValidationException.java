package com.jdc.cthu.utils.exception;

import java.util.List;

import org.springframework.validation.BindingResult;

public class ApiValidationException extends RuntimeException{


	private static final long serialVersionUID = 1L;
	
	private List<String> errors;
	
	public ApiValidationException(List<String> errors) {
		super();
		this.errors = errors;
	}
	
	public ApiValidationException(BindingResult result) {
		this.errors = result.getFieldErrors().stream().map(m -> m.getDefaultMessage()).toList();
	}

	public List<String> getErrors(){
		return errors;
	}
}
