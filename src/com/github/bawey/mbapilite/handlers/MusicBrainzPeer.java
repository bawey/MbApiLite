package com.github.bawey.mbapilite.handlers;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import us.monoid.web.Resty;
import us.monoid.web.XMLResource;

import com.github.bawey.mbapilite.exceptions.MusicBrainzException;
import com.github.bawey.mbapilite.meta.Artist;
import com.github.bawey.mbapilite.meta.Recording;
import com.github.bawey.mbapilite.tools.XmlKit;

public class MusicBrainzPeer {
	private static MusicBrainzPeer instance;
	private Resty resty;

	private DebugPrinter debugHandler = null;

	private MusicBrainzPeer() {
		resty = new Resty();
	}

	public static MusicBrainzPeer getInstance() {
		if (instance == null) {
			synchronized (MusicBrainzPeer.class) {
				if (instance == null) {
					instance = new MusicBrainzPeer();
				}
			}
		}
		return instance;
	}

	public void setDebugHandler(DebugPrinter newHandler) {
		this.debugHandler = newHandler;
	}

	public void debug(String message) {
		if (debugHandler != null) {
			debugHandler.run(message);
		}
	}

	/**
	 * MusicBrainz query methods
	 */

	// TODO: write, improve, secure
	public List<Recording> findRecordings(String query) throws MusicBrainzException {
		try {
			XMLResource xml = resty.xml(QueryTools.buildQuery(MbAction.QUERY, MbResource.RECORDING, query));
			NodeList nodeList = xml.get("metadata");
			if (nodeList.getLength() == 0) {
				throw new MusicBrainzException("No metadata in returned xml");
			}
			nodeList = nodeList.item(0).getChildNodes();
			if (nodeList.getLength() == 0) {
				throw new MusicBrainzException("recording-list xml element missing");
			}
			nodeList = nodeList.item(0).getChildNodes();
			for (int recordingIndex = 0; recordingIndex < nodeList.getLength(); ++recordingIndex) {
				Node node = nodeList.item(recordingIndex);
				this.debug(node.getAttributes().getNamedItem("id").toString());
				for (int attrIndex = 0; attrIndex < node.getChildNodes().getLength(); ++attrIndex) {
					/**
					 * title, length, artist-credit, release-list, puid-list,
					 * isrc-list, tag-list
					 */

					Node attrNode = node.getChildNodes().item(attrIndex);
					if ("title".equals(attrNode.getNodeName())) {
						debug("title=" + attrNode.getChildNodes().item(0).getNodeValue());
					} else if ("length".equals(attrNode.getNodeName())) {
						debug("duration=" + attrNode.getChildNodes().item(0).getNodeValue());
					} else if ("artist-credit".equals(attrNode.getNodeName())) {
						List<Node> nameCredits = XmlKit.getChildren("name-credit", attrNode, null);
						for (Node nameCredit : nameCredits) {
							Node artistNode = XmlKit.getChild("artist", nameCredit);
							Artist artist = new Artist(artistNode.getAttributes().getNamedItem("id").getNodeValue(), artistNode.getFirstChild()
									.getFirstChild().getNodeValue());
							debug(artist.toString());
						}
					} else if ("release-list".equals(attrNode.getNodeName())) {
						List<Node> releases = XmlKit.getChildren("release", attrNode, null);
						for (Node release : releases) {
							debug(release.getAttributes().getNamedItem("id").getNodeValue());
							debug(XmlKit.getChild("title", release).getFirstChild().getNodeValue());
							debug(XmlKit.getChild("date", release).getFirstChild().getNodeValue());
						}
					}
				}
			}
		} catch (Exception e) {
			throw new MusicBrainzException("Recording search failed", e);
		}
		return null;
	}
}
