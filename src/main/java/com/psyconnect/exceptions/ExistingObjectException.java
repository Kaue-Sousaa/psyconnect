package com.psyconnect.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExistingObjectException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ExistingObjectException(String ex) {
		super(ex);
	}
}
