package com.jdc.cthu.utils.exception;

public class ApiBusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ApiBusinessException(String message) {
		super(message);
	}

}
