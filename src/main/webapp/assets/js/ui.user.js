$ui.user = {}

$ui.user.set = function(object){
	$ui.user.id = object.userID;
	$ui.user.token = object.accessToken;
	$ui.user.name = object.userName;
    $ui.user.register = object.register || false ;
	$ui.panel.refresh();
}

$ui.user.get = function(authResponse){
	$.ajax({
            data: 	{},
           	url:  	'/FacebookApp/GetUser/'+authResponse.userID,
            type: 	'get',
            success:  function (response) {
                $ui.user.set(response);
            },
            error:  function (response) {
                $ui.facebook.getUser();
            }
    });
	
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

$ui.user.logout = function(){
    $.ajax({
            data:   {},
            url:    '/Logout',
            type:   'get',
            success:  function (response) {
                window.location.reload();
            }
        });
}


$ui.user.isRegister = function(){
	return $ui.user.register;
}

