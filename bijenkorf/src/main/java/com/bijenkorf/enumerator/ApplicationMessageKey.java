package com.bijenkorf.enumerator;


import org.springframework.http.HttpStatus;

public enum ApplicationMessageKey {

	NOT_FOUND("NOT FOUND", HttpStatus.NOT_FOUND);

	String key;
	HttpStatus httpStatus;

	ApplicationMessageKey(String key, HttpStatus httpCode) {
		this.key = key;
		this.httpStatus = httpCode;
	}

	ApplicationMessageKey(String key) {
		this(key, HttpStatus.NOT_FOUND);
	}  

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getKey() {
		return key;
	}
}
