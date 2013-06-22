package com.tacs13G6.models;

import java.util.Date;

import com.tacs13G6.models.exceptions.FeedMalformedException;
import com.tacs13G6.models.exceptions.TorrentMalformedException;

public class MockFeed {

	public static Feed Create() throws FeedMalformedException {
		Feed feed = Feed.createFeed("title", "www.link.com", "description", "user");
		Torrent feedItem = null;
		try {
			feedItem = new Torrent("<![CDATA[ The Listener S04E01 HDTV XviD-AFG[ettv] ]]>",
					"description os feeed item",
					"magnet:?xt=urn:btih:E9ABE5951EEB7C0FB484D4676C84EB7F37430EFB&dn=The+Listener+S04E01+HDTV+XviD-AFG%5Bettv%5D");
		} catch (TorrentMalformedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		feed.torrents.add(feedItem);
		return feed;
	}
}
