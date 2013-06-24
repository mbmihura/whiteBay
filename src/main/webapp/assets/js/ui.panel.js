$ui.panel = {};

$ui.panel.init = function(){
	$("#mainNav").children().each(function() {
		$(this).click(function() {
			$("#mainNav").children().each(function() {
				$(this).removeClass('active');
			});
			$(this).addClass('active');
			showView($(this).attr('data-showview'));
		});
	});
	
	$('.navbar-inner .dropdown').hide();
	$('#userFeedsView').hide();
	$('.navbar-inner .login').click(function(){
		$ui.facebook.login();
	});
	$('.navbar-inner .logout').click(function(){
		$ui.facebook.logout();
	});
	$('.navbar-inner .register').click(function(){
		$ui.facebook.register();
	});
}

$ui.panel.refresh = function(){
	if($ui.user.isRegister()){
		$('.navbar-inner .login').hide();
		$('.navbar-inner .register').hide();
		$('.navbar-inner .dropdown').show();
		$('#userFeedsView').show();
	}else{
		$('.navbar-inner .login').hide();
		$('.navbar-inner .dropdown').show();
		$('.navbar-inner .register').show();
	}
}
