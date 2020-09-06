package com.covid.corona.exception;

public class JmsSenderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7567403287354886547L;

	public JmsSenderException(String msg) {
		super(msg);
	}

	public JmsSenderException(Throwable e) {
		super(e);
	}

}
