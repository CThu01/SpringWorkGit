package com.jdc.cthu.demo.output;

import java.util.List;

public record ErrorResponse(
		Type type,
		List<String> errors
		) {
	
	public enum Type{
		Validation, Business, PlatForm
	}

}
