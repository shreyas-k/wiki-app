package com.sk.wiki.models.common.lang;

import java.util.HashSet;
import java.util.Set;

public class AppException {
	private String type;
	private String message;
	private Set<String> errors = new HashSet<>();

	public AppException(final String type, final String message) {
		this.type = type;
		this.message = message;
	}

	public AppException(final String type, final String message, final Set<String> errors) {
		this.type = type;
		this.message = message;
		this.errors = errors;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public Set<String> getErrors() {
		return errors;
	}

	public void setErrors(final Set<String> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "AppException [type=" + type + ", message=" + message + ", errors=" + errors + "]";
	}
}
