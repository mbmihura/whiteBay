API = {
	logIn: function(params)
	{
		$.ajax({
		  url: '/auth',
		  type: 'POST',
		  data: {signedRequest: params.signedRequest},
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
		var rssUrl = $ui.settings.get('domain') + getRssUrl(params.feed);
		$.ajax({
            data: 	{
            	message: $ui.user.name + ' has shared a new feed using Captain Jack S. Social Feeds! Add to your bitTorrent by entering ' + rssUrl,
            	picture: $ui.settings.get('domain') + '/assets/img/RSS_icono.png',
            	link: rssUrl,
            	name: params.feed.title,
            	description: params.feed.description,
            	access_token:$ui.user.token
            	},
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
            	message: $ui.user.name + ' has add a new torrent using Captain Jack S. Social Feeds!',
            	picture: $ui.settings.get('domain') + '/assets/img/RSS_icono.png',
            	link: $ui.settings.get('appUrl') + '/?' + $.param(params.torrent),
            	name:params.torrent.title,
            	access_token:$ui.user.token,
            	description: 'Agregar el torrent a unos de tus feed haciendo click en el link.'},
            url:  	'https://graph.facebook.com/'+$ui.user.id+'/feed',
            type: 	'POST',
            success: params.success,
			error:   params.error
    	});
		
	}
}