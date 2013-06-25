$ui.facebook = {};

$ui.facebook.init = function(){
	$ui.facebook.load();
	console.log('ui.facebook.init');
}

$ui.facebook.checkLogin = function(callBackTrue,callBackFalse){
	FB.getLoginStatus(function(response) {
		 if (response.status === 'connected') {
		 	callBackTrue(response);
		 }else{
		 	callBackFalse();
		 }
	},function(error){
		console.log(error);
	});
}

$ui.facebook.login = function (){
	$ui.facebook.checkLogin(
		function(response){
			console.log(response);
      		$ui.facebook.get(response.authResponse);
		},
		function(){
			FB.login(function(response) {
		        if (response.authResponse) {
		        	$ui.facebook.get(response.authResponse);
		        }
	   		}, {scope: 'manage_pages,publish_stream'});
		}
	);   		
}

$ui.facebook.get = function(authResponse){
	FB.api('/me', function(response) {
		console.log(response);
	  	$ui.user.set(response,authResponse);
	});
}

$ui.facebook.load = function(){
	window.fbAsyncInit = function() {
	    FB.init({
	      appId      : $ui.vars.get('appId'), // App ID
	      channelUrl : '//'+$ui.vars.get('url'), // Channel File
	      status     : true, // check login status
	      cookie     : true, // enable cookies to allow the server to access the session
	      xfbml      : true  // parse XFBML
	    });

	    $ui.facebook.login();
  	};
}
