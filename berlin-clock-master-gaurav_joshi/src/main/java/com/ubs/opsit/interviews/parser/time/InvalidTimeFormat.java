package com.ubs.opsit.interviews.parser.time;

@SuppressWarnings("serial")
public class InvalidTimeFormat extends RuntimeException {

	public InvalidTimeFormat(String cause) {
		super(cause);
	}

}
