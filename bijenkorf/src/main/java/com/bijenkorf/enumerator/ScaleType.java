package com.bijenkorf.enumerator;

public enum ScaleType {
	CROP("CROP"),
	FILL("FILL"),
	SKEW("SKEW");	

	private String scaleType;

	private ScaleType(String scaleType) {
		this.scaleType = scaleType;
	}

	public String toString() {
		return scaleType;
	}
}
