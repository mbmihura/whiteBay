$ui.facebook = {};

$ui.facebook.init = function(nextLoad){
	$ui.facebook.load(nextLoad);
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
      		$ui.user.get(response.authResponse);
		},
		function(){
			FB.login(function(response) {
		        if (response.authResponse) {
		        	console.log(response);
		          	$ui.user.get(response.authResponse);
		        }
	   		}, {scope: 'manage_pages,publish_stream'});
		}
	);   		
}

$ui.facebook.logout = function (){
	FB.logout(function(response) {
	  	$ui.user.logout();
	});
}

$ui.facebook.register = function (){
	window.location.href="/Register";
}

$ui.facebook.postMessage = function(){
	var data = {
		message:$('.message').val()
	};
	$.ajax({
            data: 	data,
            url:  	'/FacebookApp/PostMessage/'+$ui.user.id,
            type: 	'post',
            success:  function (response) {
                console.log(response);
            }
    });
}

$ui.facebook.get = function(){
	FB.api('/me', function(response) {
	  	$ui.user.set(response);
	});
}

$ui.facebook.load = function(nextLoad){
	window.fbAsyncInit = function() {
	    FB.init({
	      appId      : $ui.vars.get('appId'), // App ID
	      channelUrl : '//'+$ui.vars.get('url'), // Channel File
	      status     : true, // check login status
	      cookie     : true, // enable cookies to allow the server to access the session
	      xfbml      : true  // parse XFBML
	    });

	    nextLoad();
  	};
}
