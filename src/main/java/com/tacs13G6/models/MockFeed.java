package com.tacs13G6.models;

import java.util.Date;

public class MockFeed extends Feed{

	public MockFeed() {
		super("title", "www.link.com", "description", "language", new Date());
		Torrent feedItem = new Torrent();
		feedItem.title = "<![CDATA[ The Listener S04E01 HDTV XviD-AFG[ettv] ]]>";
		feedItem.description = "description os feeed item";
		feedItem.guid = "http://thepiratebay.se/torrent/8516814/";
		feedItem.link = "magnet:?xt=urn:btih:E9ABE5951EEB7C0FB484D4676C84EB7F37430EFB&dn=The+Listener+S04E01+HDTV+XviD-AFG%5Bettv%5D";
        this.torrents.add(feedItem);
	}
}
