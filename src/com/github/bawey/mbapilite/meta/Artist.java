package com.github.bawey.mbapilite.meta;

public class Artist {
	private String mbid;
	private String name;

	public Artist(String mbid, String name) {
		super();
		this.mbid = mbid;
		this.name = name;
	}

	public String getMbid() {
		return mbid;
	}

	public void setMbid(String mbid) {
		this.mbid = mbid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStub() {
		return this.mbid == null || this.name == null;
	}

	@Override
	public String toString() {
		return "Artist [mbid=" + mbid + ", name=" + name + "]";
	}

}
