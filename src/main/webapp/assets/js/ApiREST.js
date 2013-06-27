API = {
	logIn: function(params)
	{
		$.ajax({
		  url: '/auth',
		  type: 'POST',
		  data: {userId: params.userId},
		  success: params.success,
		  error: params.error
		});
	},
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
            data: 	{
            	message:params.feed.description,
            	link:params.feed.link,
            	name:params.feed.title,
            	access_token:$ui.user.token},
            url:  	'https://graph.facebook.com/'+$ui.user.id+'/feed',
            type: 	'POST',
            success: params.success,
			error:   params.error
    	});
		
	},
	shareTorrent: function(params)
	{
		$.ajax({
            data: 	{
            	message: $ui.user.name + " has add a new torrent to Captain Jack S. Social Feeds!",
            	link: "http://apps.facebook.com/captainjacksgroupsix/?" + params.torrent.serialize(),
            	name:params.torrent.title,
            	access_token:$ui.user.token},
            url:  	'https://graph.facebook.com/'+$ui.user.id+'/feed',
            type: 	'POST',
            success: params.success,
			error:   params.error
    	});
		
	}
}