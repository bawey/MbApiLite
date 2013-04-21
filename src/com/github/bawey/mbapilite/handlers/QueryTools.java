package com.github.bawey.mbapilite.handlers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.management.MBeanAttributeInfo;

public class QueryTools {
	public static final String[] RECORDING_KEYS = { "artist", "alias", "recording", "release", "arid" };
	public static final String[] ARTIST_KEYS = { "artist", "alias" };
	public static final String[] RELEASE_GROUP_KEYS = { "artist", "alias", "release", "rgid", "arid" };

	public static final String ws = "http://musicbrainz.org/ws/2/";
	public static final String ATTR_COUNT = "count";
	public static final String ATTR_OFFSET = "offset";

	/**
	 * 
	 * @param action
	 * @param resource
	 * @param params
	 *            list of search keywords or mbid for lookup / browse
	 * @return
	 * @throws MusicBrainzException
	 */
	public static String buildQuery(MbAction action, MbResource resource, String params) throws MusicBrainzException {
		StringBuilder qb = new StringBuilder(ws).append(resource).append("/");
		if (action.equals(MbAction.QUERY)) {
			qb.append("?query=").append(buildLuceneSubquery(params, resource));
		} else {
			qb.append(params);
			// TODO: all the include-s
		}
		MusicBrainzPeer.getInstance().debug(qb.toString());
		return qb.toString();
	}

	private static String purify(String input) throws MusicBrainzException {
		try {
			return URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new MusicBrainzException("Failed to encode the query", e);
		}
	}

	private static final String buildLuceneSubquery(String searchTerms, MbResource resource) throws MusicBrainzException {
		switch (resource) {
		case ARTIST:
			return buildLuceneQuery(searchTerms, ARTIST_KEYS);
		case RELEASE_GROUP:
			return buildLuceneQuery(searchTerms, RELEASE_GROUP_KEYS);
		case RECORDING:
		default:
			return buildLuceneQuery(searchTerms, RECORDING_KEYS);
		}
	}

	private static final String buildLuceneQuery(String searchTerms, String[] fields) throws MusicBrainzException {
		StringBuilder sb = new StringBuilder();
		String[] tokens = searchTerms.split(" ");
		for (String token : tokens) {
			token = purify(token);
			sb.append("(");
			for (String field : fields) {
				sb.append(field).append(":");
				sb.append(token);
				if (!field.equals(fields[fields.length - 1])) {
					sb.append("%20OR%20");
				}
			}
			sb.append(")");
			if (!token.equals(tokens[tokens.length - 1])) {
				sb.append("%20AND%20");
			}
		}
		return sb.toString();
	}

}
