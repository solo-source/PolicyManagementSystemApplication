package com.exception;

import java.time.LocalDate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {


		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now(), ex.getMessage(),
				request.getDescription(false),HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@ExceptionHandler({InvalidAgencyException.class,InvalidPropertyException.class})
	public final ResponseEntity<ExceptionResponse> handleNotFoundException(RuntimeException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now(), ex.getMessage(),
				request.getDescription(false),HttpStatus.NOT_FOUND.getReasonPhrase());
	
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,

		HttpHeaders headers, HttpStatusCode status, WebRequest request) {	
		String details = "";
			for(ObjectError error : ex.getBindingResult().getAllErrors()) {
				details+=error.getDefaultMessage()+", ";
			}
			//sysout
			ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now(),details,
					request.getDescription(false),HttpStatus.BAD_REQUEST.getReasonPhrase());


			return new ResponseEntity(exceptionResponse , HttpStatus.BAD_REQUEST);
	}
	

}