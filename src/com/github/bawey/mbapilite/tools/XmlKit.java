package com.github.bawey.mbapilite.tools;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import com.github.bawey.mbapilite.exceptions.MusicBrainzResponseFormatException;

public class XmlKit {
	public static Node getChild(String name, Node context) throws MusicBrainzResponseFormatException {
		if (context.getChildNodes() == null) {
			throw new MusicBrainzResponseFormatException("Hoped to find some children here!");
		}
		for (int index = 0; index < context.getChildNodes().getLength(); ++index) {
			Node child = context.getChildNodes().item(index);
			if (child.getNodeName().equalsIgnoreCase(name)) {
				return child;
			}
		}
		throw new MusicBrainzResponseFormatException("Didn't find the element of desired name");
	}

	public static List<Node> getChildren(String name, Node context, Integer limit) throws MusicBrainzResponseFormatException {
		if (context.getChildNodes() == null) {
			throw new MusicBrainzResponseFormatException("Hoped to find some children here!");
		}
		List<Node> results = new ArrayList<Node>(context.getChildNodes().getLength());
		for (int index = 0; index < context.getChildNodes().getLength(); ++index) {
			Node child = context.getChildNodes().item(index);
			if (child.getNodeName().equalsIgnoreCase(name)) {
				results.add(child);
				if (limit != null && results.size() >= limit) {
					return results;
				}
			}
		}
		if (results.isEmpty()) {
			throw new MusicBrainzResponseFormatException("Didn't find the element of desired name");
		} else {
			return results;
		}
	}
}
