package com.github.bawey.mbapilite.meta;

import java.util.List;

public class Release {

	private String mbid;
	private ReleaseGroup releaseGroup;
	private int year;
	private List<Artist> artists;
	private String title;
	private List<Recording> recordings;
	
	public ReleaseGroup getReleaseGroup() {
		return releaseGroup;
	}
	public void setReleaseGroup(ReleaseGroup releaseGroup) {
		this.releaseGroup = releaseGroup;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public List<Artist> getArtists() {
		return artists;
	}
	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Recording> getRecordings() {
		return recordings;
	}
	public void setRecordings(List<Recording> recordings) {
		this.recordings = recordings;
	}

	
	
	
}
