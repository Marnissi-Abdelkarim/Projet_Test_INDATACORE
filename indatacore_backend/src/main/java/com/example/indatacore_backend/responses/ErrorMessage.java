package com.example.indatacore_backend.responses;

import java.util.Date;

public class ErrorMessage {
	private Date timestamp;
	private String message ;
	public ErrorMessage(Date timestamp, String message) {
		super();
		this.timestamp = timestamp;
		this.message = message;
	}
}
