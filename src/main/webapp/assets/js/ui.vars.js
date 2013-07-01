$ui = {};
$ui.init = function(idViewToLoad) {$ui.facebook.init(idViewToLoad)};

$ui.settings = {};
console.log('ui.settings initialized');

$ui.settings.set = function(key,value){
	$ui.settings[key] = value;	
};

$ui.settings.get = function(key){
	return $ui.settings[key];
};

$ui.user = {};

$ui.user.set = function(userResponse,authResponse){
	$ui.user.id = userResponse.id;
	$ui.user.token = authResponse.accessToken;
	$ui.user.signedRequest = authResponse.signedRequest;
	$ui.user.name = userResponse.name;
    $ui.user.link = userResponse.link;
};

function getRssUrl(feed){
	var url = $ui.settings.get('domain') + '/rss/' + feed.userId + '/' + feed.title;
	if (feed.rssAuthToken)
		url = url + '?authToken=' + feed.rssAuthToken;
	return url;
}

