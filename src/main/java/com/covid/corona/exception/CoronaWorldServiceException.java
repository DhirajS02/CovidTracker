package com.covid.corona.exception;

public class CoronaWorldServiceException extends Exception{
	public CoronaWorldServiceException(String msg) {
		super(msg);
	}
		
	public CoronaWorldServiceException(Throwable e) {
		super(e);
	}

}
