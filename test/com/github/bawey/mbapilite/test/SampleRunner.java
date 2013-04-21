package com.github.bawey.mbapilite.test;

import com.github.bawey.mbapilite.handlers.DebugPrinter;
import com.github.bawey.mbapilite.handlers.MusicBrainzPeer;

public class SampleRunner {

	public static final void main(String[] args) throws Exception {
		MusicBrainzPeer mbp = MusicBrainzPeer.getInstance();
		mbp.setDebugHandler(new DebugPrinter() {
			@Override
			public void run(String message) {
				System.out.println(message);
			}
		});
		mbp.findRecordings("linkin park jay z");
	}
}
