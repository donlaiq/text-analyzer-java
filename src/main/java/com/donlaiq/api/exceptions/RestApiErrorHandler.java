package com.donlaiq.api.exceptions;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestApiErrorHandler {
	
	private static final Logger log = LoggerFactory.getLogger(RestApiErrorHandler.class);
	private final MessageSource messageSource;
	
	@Autowired
	public RestApiErrorHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Error> handleException(HttpServletRequest request, Exception ex, Locale locale)
	{
		Error error = ErrorUtils.createError(ErrorCode.INPUT_ERROR.getErrMsgKey(), 
											ErrorCode.INPUT_ERROR.getErrCode(), 
											HttpStatus.INTERNAL_SERVER_ERROR.value())
											.setUrl(request.getRequestURL().toString())
											.setReqMethod(request.getMethod());
		
		log.info("The user enter a invalid input");
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
