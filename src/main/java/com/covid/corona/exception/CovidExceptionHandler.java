package com.covid.corona.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.covid.corona.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CovidExceptionHandler {
	
	
		
	@ExceptionHandler(value = {DataUnreachableException.class})
	protected ResponseEntity<ErrorResponseDto> handleDataUnreachableException(DataUnreachableException ex, WebRequest request) {
		log.error("{}; {}", request.getDescription(false), ex.getMessage());
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ErrorResponseDto errorResponse = new ErrorResponseDto(httpStatus.value(), ex.toString());
		return new ResponseEntity<ErrorResponseDto>(errorResponse, httpStatus);
	}
	@ExceptionHandler(value = {CoronaServiceException.class})
	protected ResponseEntity<ErrorResponseDto> coronaServiceException(Exception ex, WebRequest request) {
		log.error("{}; {}", request.getDescription(false), ex.toString(), ex);
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorResponseDto errorResponse = new ErrorResponseDto(httpStatus.value(), ex.toString());
		return new ResponseEntity<ErrorResponseDto>(errorResponse, httpStatus);
	}
	@ExceptionHandler(value = {Exception.class})
	protected ResponseEntity<ErrorResponseDto> handleAnyException(Exception ex, WebRequest request) {
		log.error("{}; {}", request.getDescription(false), ex.toString(), ex);
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorResponseDto errorResponse = new ErrorResponseDto(httpStatus.value(), ex.toString());
		return new ResponseEntity<ErrorResponseDto>(errorResponse, httpStatus);
	}
	@ExceptionHandler(value = {CoronaWorldServiceException.class})
	protected ResponseEntity<ErrorResponseDto> coronaWorldServiceException(Exception ex, WebRequest request) {
		log.error("{}; {}", request.getDescription(false), ex.toString(), ex);
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorResponseDto errorResponse = new ErrorResponseDto(httpStatus.value(), ex.toString());
		return new ResponseEntity<ErrorResponseDto>(errorResponse, httpStatus);
	}

}
