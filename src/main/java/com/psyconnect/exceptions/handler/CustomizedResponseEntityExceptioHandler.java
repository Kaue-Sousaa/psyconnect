package com.psyconnect.exceptions.handler;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.psyconnect.exceptions.ExceptionResponse;
import com.psyconnect.exceptions.InvalidCpfException;
import com.psyconnect.exceptions.InvalidCpfLengthException;
import com.psyconnect.exceptions.InvalidPasswordException;
import com.psyconnect.exceptions.RequiredObjectIsNullException;
import com.psyconnect.exceptions.ResourceNotFoundException;
import com.psyconnect.exceptions.ValidFieldResponse;

@RestControllerAdvice
public class CustomizedResponseEntityExceptioHandler {
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
    public final ResponseEntity<ValidFieldResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, 
            WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError -> 
            errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage())
        );
        
        return buildResponseEntity(errorMessages, request);
    }

    @ExceptionHandler({RequiredObjectIsNullException.class, InvalidCpfLengthException.class,
			InvalidPasswordException.class, InvalidCpfException.class})
    public final ResponseEntity<ValidFieldResponse> handleOtherExceptions(
            Exception ex, 
            WebRequest request) {
        List<String> errorMessages = Collections.singletonList(ex.getMessage());
        return buildResponseEntity(errorMessages, request);
    }

    private ResponseEntity<ValidFieldResponse> buildResponseEntity(List<String> errorMessages, WebRequest request) {
        ValidFieldResponse exceptionResponse = new ValidFieldResponse(
                LocalDateTime.now(), errorMessages, request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundException(Exception ex, WebRequest request){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				LocalDateTime.now(), ex.getMessage() , request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
//	@ExceptionHandler({InvalidJwtAuthenticationException.class})
//		TokenExpiredException.class, InternalAuthenticationServiceException.class})
	public final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthenticationException(Exception ex, WebRequest request){
        
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				LocalDateTime.now(), ex.getMessage() , request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler({Exception.class,IllegalStateException.class, NullPointerException.class, InvocationTargetException.class})
	public final ResponseEntity<ExceptionResponse> handleInternalServerErroException(
			Exception ex, WebRequest request){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				LocalDateTime.now(), ex.getMessage() , request.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
