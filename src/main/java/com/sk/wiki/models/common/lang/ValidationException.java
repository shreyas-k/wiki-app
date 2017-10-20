package com.sk.wiki.models.common.lang;

import java.util.Set;

public class ValidationException extends RuntimeException {
	private static final long serialVersionUID = 3895064693169019773L;
	private Set<String> errors;

	public ValidationException() {
		super();
	}

	public ValidationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ValidationException(final String message) {
		super(message);
	}

	public ValidationException(final Throwable cause) {
		super(cause);
	}

	public Set<String> getErrors() {
		return errors;
	}

	public void setErrors(Set<String> errors) {
		this.errors = errors;
	}
}
