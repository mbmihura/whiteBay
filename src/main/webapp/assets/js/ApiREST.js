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
	},
	shareFeed: function(params)
	{
		$.ajax({
            data: 	{message:params.feed.description,link:params.feed.link,name:params.feed.title,access_token:$ui.user.token},
            url:  	'https://graph.facebook.com/'+$ui.user.id+'/feed',
            type: 	'POST',
            success: params.success,
			error: params.error
    	});
		
	}
}