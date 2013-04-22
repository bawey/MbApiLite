package com.github.bawey.mbapilite.exceptions;

public class MusicBrainzResponseFormatException extends MusicBrainzException {
	private static final long serialVersionUID = 1L;

	public MusicBrainzResponseFormatException() {
		super();
	}

	public MusicBrainzResponseFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public MusicBrainzResponseFormatException(String message) {
		super(message);
	}

}
