package com.example.indatacore_backend.exception;

public class UserException extends RuntimeException{
	private static final long serialVersionUID = -7092595252332873823L;
	public UserException(String message) {
		super(message);
	}
}

