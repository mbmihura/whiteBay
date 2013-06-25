API = {
	getFeeds: function(params)
	{
		$.ajax({
		  url: '/feeds',
		  type: 'GET',
		  success: params.success,
		  error: params.error
		});
	},
	createFeed: function(params)
	{	
		$.ajax({
			  url: '/feeds/' + params.feed.title,
			  type: 'PUT',
			  data: {link: params.feed.link, description: params.feed.description},
			  success: params.success,
			  error: params.error
			});
	},
	addTorrent: function(params)
	{
		$.ajax({
			  url: '/feeds/' + params.feed,
			  type: 'POST',
			  data: params.torrent,
			  success: params.success,
			  error: params.error
			});
	}
}