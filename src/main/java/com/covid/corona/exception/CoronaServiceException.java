package com.covid.corona.exception;

public class CoronaServiceException extends Exception{
	public CoronaServiceException(String msg) {
		super(msg);
	}
		
	public CoronaServiceException(Throwable e) {
		super(e);
	}


}
