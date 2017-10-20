package com.sk.wiki.models.common.lang;

public class InternalError extends RuntimeException {
	private static final long serialVersionUID = 1045942593018705963L;

	public InternalError() {
		super();
	}

	public InternalError(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InternalError(final String message) {
		super(message);
	}

	public InternalError(final Throwable cause) {
		super(cause);
	}
}
