API = {
	getFeeds: function(params)
	{
		$.ajax({
		  url: '/feeds/mbmihura/',
		  type: 'GET',
		  success: params.success,
		  error: params.error
		});
	},
	createFeed: function(params)
	{	
		$.ajax({
			  url: '/feeds/mbmihura/b',
			  type: 'PUT',
			  data: params.feed,
			  success: params.success,
			  error: params.error
			});
	},
	addTorrent: function(params)
	{
		$.ajax({
			  url: '/feeds/mbmihura/' + params.feed,
			  type: 'POST',
			  data: params.torrent,
			  success: params.success,
			  error: params.error
			});
	},
	shareFeed: function()
	{
		$.ajax({
			  url: '/social/b',
			  type: 'POST',
			  success: callback.success,
			  error: callback.error
			});	
	}
}