package com.jdc.cthu.utils.exception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jdc.cthu.demo.output.ErrorResponse;
import com.jdc.cthu.demo.output.ErrorResponse.Type;

@RestControllerAdvice
public class ExceptionHandlers {
		
	private Logger log = LoggerFactory.getLogger(ExceptionHandlers.class);

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	ErrorResponse handle(ApiValidationException e) {
		log.error("Validation errors : ");
		for(var errorList : e.getErrors()) {
			log.error(errorList);;
		}
		return new ErrorResponse(Type.Validation, e.getErrors());
	}
	
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	ErrorResponse handle(ApiBusinessException e) {
		log.error(e.getMessage());
		return new ErrorResponse(Type.Business, List.of(e.getMessage()));
	}
	
	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	ErrorResponse handle(RuntimeException e) {
		log.error(e.getMessage());
		e.printStackTrace();
		return new ErrorResponse(Type.PlatForm, List.of(e.getMessage()));
	}

}
