$ui.vars = {}

$ui.vars.init = function(){
	$ui.vars.load();
	console.log('ui.vars.init');		
}

$ui.vars.set = function(key,value){
	$ui.vars.settings[key] = value;	
}

$ui.vars.get = function(key){
	return $ui.vars.settings[key];
}

$ui.vars.load = function(){
	$ui.vars.settings = {};
}

