package com.sk.wiki.models;

public enum OrderMode {
	ASC, DESC;

	public boolean isAscending() {
		return this.equals(ASC);
	}

	public boolean isDescending() {
		return this.equals(DESC);
	}

	public static OrderMode fromString(final String mode) {
		return OrderMode.valueOf(mode.toUpperCase());
	}
}
