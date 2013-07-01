$ui.facebook = {};

$ui.facebook.init = function(idViewToLoad){
	window.fbAsyncInit = function() {
	    FB.init({
	      appId      : $ui.settings.get('appId'),    // App ID
	      channelUrl : '//'+$ui.settings.get('domain'), // Channel File
	      status     : true, // check login status
	      cookie     : true, // enable cookies to allow the server to access
								// the session
	      xfbml      : true  // parse XFBML
	    });
	    console.log('FB sdk initialied');
	    $ui.facebook.login(idViewToLoad);
  	};
	console.log('FB sdk initializing...');
}

$ui.facebook.login = function(idViewToLoad){
	var onEndCall = idViewToLoad;
	// Check current status, logIn if not already logged.
	FB.getLoginStatus(function(response) {
		setLoadingMsg("LogIn to Facebook")
		 if (response.status === 'connected') {
			 console.log('FB sdk: already logged as user ' + response.authResponse.userID + ':');
			 console.log(response);
			 $ui.facebook.loadUser(response, onEndCall);
		 }else{
			 console.log('FB sdk: not logged. Performing login...');
			 FB.login(function(response) {
			        if (response.authResponse) {
			        	console.log('FB sdk: user logged as ' + response.authResponse.userID + ':');
					 	console.log(response);
			        	$ui.facebook.loadUser(response, onEndCall);
			        }
		   	}, {scope: 'manage_pages,publish_stream'});
		 }
		 
	},function(error){
		console.log(error);
	});
	console.log('FB sdk: checking login status...');
}

$ui.facebook.loadUser = function(response, onEndCall){
	loadNavBar();
	setLoadingMsg("Retrieving user's data from Facebook")
	var authResponse = response.authResponse;
	console.log("retrieving user's data...");
	// Syncs callback function for all ajax request
	var requestsCoordinator = new RequestsCoordinator({
	    numRequest: 2,
	    singleCallback: function(){
	    	// When all finished call callback function
	    	console.log("user's data complete");
	    	if (onEndCall)
    	  		onEndCall();
	    }
	});
	// Get user's facebook data
	FB.api('/me', function(userResponse) {
		console.log("FB's data... OK");
		console.log(userResponse);
	  	$ui.user.set(userResponse,authResponse);
	  	$(".navbar .brand").html($ui.user.name).attr("href",$ui.user.link).attr("target","_blank");	
	  	requestsCoordinator.requestComplete(true);
	});
	// Get user's white bay data
	API.logIn({
    	signedRequest: authResponse.signedRequest,
    	success : function() {
    		console.log("WhiteBay's data... OK");
    		setLoadingMsg("Loading feeds")
    		loadFeedsFromServer(function()
    				{
    					requestsCoordinator.requestComplete(true);
    				});
    		
		},
		error : function() {
			notification("Could not load your facebook user.", alertStyle.error).flash();
		}
	});	
}