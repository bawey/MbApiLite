package com.github.bawey.mbapilite.handlers;

public enum MbResource {

	ARTIST("artist"), RELEASE_GROUP("release-group"), RELEASE("release"), RECORDING("recording");

	private String queryString;

	private MbResource(String queryString) {
		this.queryString = queryString;
	}

	@Override
	public String toString() {
		return queryString;
	}

}
