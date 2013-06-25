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

	$('#userFeedsView').hide();
	$("#mainNav").hide();
	
}

$ui.panel.refresh = function(){
	$('#userFeedsView').show();
	$("#mainNav").show();
	$("#userNav").html($ui.user.name);
	$("#userNav").attr("href",$ui.user.link);
	$("#userNav").attr("target","_blank");
	
}
