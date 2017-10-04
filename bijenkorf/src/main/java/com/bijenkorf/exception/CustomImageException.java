package com.bijenkorf.exception;

import com.bijenkorf.enumerator.ApplicationMessageKey;

public class CustomImageException extends Exception {

	private static final long serialVersionUID = 7268463153107646478L;
	
	private ApplicationMessageKey messageKey;

	public CustomImageException() {
		super();
	}

	public CustomImageException(String message, Throwable cause) {
		super(message, cause);
	}
}
