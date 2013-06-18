alertStyle = {
		warning: "alert-block",
		error: "alert-error",
		success: "alert-success",
		info: "alert-info"	
}

jQuery.fn.flash = function(delayTime, fadeOutTime )
{
	if (delayTime == null) delayTime = 1200;
	if (fadeOutTime == null) fadeOutTime = 1000;
	this.show().delay(delayTime).fadeOut(fadeOutTime);
}