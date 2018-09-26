package com.clipicker.me.exception;

public class ValidateFailedException extends RuntimeException {
	private static final long serialVersionUID = -4612083161335892648L;
	private String message;

	public ValidateFailedException(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return message;
	}

}
