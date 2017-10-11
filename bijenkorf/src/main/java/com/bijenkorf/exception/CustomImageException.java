package com.bijenkorf.exception;

import com.amazonaws.AmazonClientException;
import com.bijenkorf.enumerator.ApplicationMessageKey;

public class CustomImageException extends RuntimeException {

	private static final long serialVersionUID = 7268463153107646478L;
	
	private ApplicationMessageKey messageKey;
	AmazonClientException amazonClientException;

	public CustomImageException() {
		super();
	}

	public CustomImageException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	public CustomImageException(ApplicationMessageKey messageKey) {
		this.messageKey = messageKey;
	}
	
	public CustomImageException(AmazonClientException amazonClientException) {
		this.amazonClientException = amazonClientException;
	}
}
