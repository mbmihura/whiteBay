$ui.user = {}

$ui.user.set = function(object,authResponse){
	$ui.user.id = object.id;
	$ui.user.token = authResponse.accessToken;
	$ui.user.name = object.name;
    $ui.user.link = object.link;
    API.logIn({
    	userId : $ui.user.id,
		error : function() {
			notification("Could not load your facebook user.", alertStyle.error).flash();
		}
	});
    
	$ui.panel.refresh();
}

$ui.user.setSession = function(object){
    $ui.facebook.checkLogin(
            function(response){
                if(object.userID == response.authResponse.userID)
                    $ui.user.set(object);
                else
                    $ui.facebook.logout();    
            },
            function(){
                $ui.user.logout();
            }
        );
}

