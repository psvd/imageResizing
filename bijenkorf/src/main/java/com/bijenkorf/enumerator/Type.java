package com.bijenkorf.enumerator;

public enum Type {
	JPG("JPG"),
	PNG("PNG");

	private String type;

	private Type(String type) {
		this.type = type;
	}

	public String toString() {
		return type;
	}
}
