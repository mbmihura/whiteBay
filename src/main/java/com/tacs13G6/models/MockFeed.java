package com.tacs13G6.models;

import java.util.Date;

import com.tacs13G6.models.exceptions.FeedMalformedException;

public class MockFeed extends Feed{

	public MockFeed() throws FeedMalformedException {
		super("title", "www.link.com", "description", new Date());
		Torrent feedItem = new Torrent("<![CDATA[ The Listener S04E01 HDTV XviD-AFG[ettv] ]]>",
				"description os feeed item",
				"magnet:?xt=urn:btih:E9ABE5951EEB7C0FB484D4676C84EB7F37430EFB&dn=The+Listener+S04E01+HDTV+XviD-AFG%5Bettv%5D");
        this.torrents.add(feedItem);
	}
}
