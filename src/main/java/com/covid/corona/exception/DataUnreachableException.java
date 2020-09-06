package com.covid.corona.exception;

import java.util.Arrays;

public class DataUnreachableException extends Exception {
	private static final long serialVersionUID = 4039539618107379113L;
	public DataUnreachableException(String msg) {
		super(msg);
	}
		
	public DataUnreachableException(Throwable e) {
		super(e);
	}


}
