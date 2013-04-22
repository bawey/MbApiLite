package com.github.bawey.mbapilite.exceptions;

public class MusicBrainzException extends Exception {
	private static final long serialVersionUID = -520406651931388565L;

	public MusicBrainzException() {

	}

	public MusicBrainzException(String message) {
		super(message);
	}

	public MusicBrainzException(String message, Throwable cause) {
		super(message, cause);
	}
}
