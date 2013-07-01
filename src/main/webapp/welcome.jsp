<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@page import="com.tacs13G6.fbApp.FB_CONSTANTS"%>
<%@ page import= "com.tacs13G6.fbApp.ApplicationConfig" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>WhiteBay: Social Feeds</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Solcial Feed App">
<meta name="author" content="TACS Grupo 6">
<link href="/assets/css/bootstrap.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 20px;
	padding-bottom: 60px;
}

.container {
	margin: 0 auto;
	max-width: 1000px;
}

.container>hr {
	margin: 60px 0;
}
/* Special class on .container surrounding .navbar, used for positioning it into place. */
.navbar-wrapper {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	z-index: 10;
	margin-top: 20px;
	margin-bottom: -90px;
	/* Negative margin to pull up carousel. 90px is roughly margins and height of navbar. */
}

/* Downsize the brand/project name a bit */
.navbar .brand {
	padding-right: 20px;
	font-size: 16px;
	font-weight: bold;
}

/* Navbar links: increase padding for taller navbar */
.navbar .nav>li>a {
	padding-right: 20px;
}

/* Offset the responsive button for proper vertical alignment */
.navbar .btn-navbar {
	margin-top: 10px;
}

/* Customize the navbar links to be fill the entire space of the .navbar */
.navbar .navbar-inner {
	padding-right: 0px;
}

/* queda */
.navbar .nav li a {
	text-align: center;
}

/* queda */
#mainNav .navbar .nav li:first-child a {
	border-left: 1px solid rgba(255, 255, 255, .75);
}

/* No Feeds Message: */
.jumbotron {
	margin: 80px 0;
	text-align: center;
}

.jumbotron h1 {
	font-size: 100px;
	line-height: 1;
}

.jumbotron .lead {
	font-size: 24px;
	line-height: 1.25;
}

.jumbotron .btn {
	font-size: 21px;
	padding: 14px 24px;
}

.alert .text-center {
	text-align: center
}
</style>
<link href="/assets/css/bootstrap-responsive.css" rel="stylesheet">
</head>

<body>
	
  	<div id="fb-root"></div>
	<div class="container">
		<div class="masthead">
			<h3 class="muted">White Bay Social Torrents Feeds</h3>
			<!-- Navigation Bar -->
			<div class="navbar" style="display:none">
				<div class="navbar-inner">
					<a class="brand" id="userNav" style="border-right: 1px solid rgba(0, 0, 0, .1);"
						href="javascript:$ui.facebook.login();">loading user data</a>

						<ul id="mainNav" class="nav">
							<li data-showview="#userFeedsView" class="active"><a
								href="#">Your feeds</a></li>
							<li data-showview="#addFeedView"><a href="#">Create feed</a></li>
							<li data-showview="#addTorrentView"><a href="#">Add
									torrent</a></li>
						</ul>
				</div>
			</div>
		</div>
		<div id="mainAlert" class="alert text-center" style="display: none">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<span></span>
		</div>
		<!-- Add Auth View -->
		<div id="loadingView" class="jumbotron">
  			<img src="assets/img/loading_animation.gif" style="width: 30px;">			
				<p class="lead">Connecting with facebook</p>
			</div>
		<!-- User's Feeds List -->
		<div id="userFeedsView" style="display: none">
			<div id="noFeedMsg" class="jumbotron" style="display: none">
				<h1>Create a Feed!</h1>
				<p class="lead">You have no feed yet. Create a feed to share it
					with your friends.</p>
				<a class="btn btn-large btn-success" onclick="showView('#addFeedView')" href="#">Create Feed</a>
			</div>
			<div id="feedsList" class="row-fluid">
				<div class="span4 template" style="display: none">
					<h2>Private Feed:</h2>
					<p class="description" style="font-style: italic"></p>
					<p>
						Feed's rss: <a class="feedLink">utntcas.appspot.com/rss/23423</a>
					</p>
					<ul>
					</ul>
					<p>
						<a class="btn shareFeed" href="#">Share in
							Facebook! &raquo;</a>
					</p>
				</div>
			</div>
		</div>

		<!-- Add Feed View -->
		<form id="addFeedView" class="form-horizontal" class="jumbotron"
			style="display: none">
			<legend>New Feed</legend>
			<div class="control-group">
				<label class="control-label" for="feedTitleInput">Title: </label>
				<div class="controls">
					<input type="text" id="feedTitleInput"
						placeholder="feed's title...">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="feedLinkInput">Link: </label>
				<div class="controls">
					<input type="text" id="feedLinkInput" placeholder="feed's link...">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="feedDescInput">Description:
				</label>
				<div class="controls">
					<textarea rows="3" id="feedDescInput"
						placeholder="feed's description..."></textarea>
				</div>
			</div>
			<div class="form-actions">
				<button type="submit" onclick="createFeed();"
					class="btn btn-primary">Create Feed</button>
				<button type="button" class="btn" onclick="createFeedResetAndHideForm();">Cancel</button>
			</div>
		</form>
		<!-- Add Torrent to Feed View -->
		<form id="addTorrentView" class="form-horizontal" class="jumbotron"
			style="display: none">
			<legend>Add torrent to feed</legend>
			<div class="control-group">
				<label class="control-label" for="addTorrentTitleInput">Title:
				</label>
				<div class="controls">
					<input type="text" id="addTorrentTitleInput"
						placeholder="torrent's title...">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="addTorrentLinkInput">Url:
				</label>
				<div class="controls">
					<input type="text" id="addTorrentLinkInput"
						placeholder="torrent's url...">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="addTorrentDescTxtarea">Description:
				</label>
				<div class="controls">
					<textarea rows="3" id="addTorrentDescTxtarea"
						placeholder="torrent's description..."></textarea>
				</div>
			</div>
			<div class="control-group">

				<label class="control-label" for="addTorrentFeedSelect">Feed:
				</label>
				<div class="controls">
					<select id="addTorrentFeedSelect"></select>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label class="checkbox"> <input id="addTorrentShareInFb"
						type="checkbox"> Share in Facebook!
					</label>
				</div>
			</div>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary"
					onclick="addTorrent();">Save Torrent</button>
				<button type="button" class="btn" onclick="addTorrentResetAndHideForm();">Cancel</button>
			</div>
		</form>

		<hr>
		<div class="footer">
			<p>&copy; UTN - TACS 2013C1: Grupo 6</p>
		</div>

	</div>

	<!-- javascript -->
	<script src="/assets/js/jquery-2.0.2.min.js"></script>
	<script src="/assets/js/bootstrap.js"></script>
  	<script src="/assets/js/ui.vars.js"></script>
 	<script src="/assets/js/ui.facebook.js"></script>
 	<script src="/assets/js/requestsCoordinator.js"></script>
	<script src="/assets/js/bootstrapExtension.js"></script>
	<script src="/assets/js/ApiREST.js"></script>
  	<script type="text/javascript">
	        
	        
	  </script>
	<script type="text/javascript">
	 (function(d){
	     var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
	     if (d.getElementById(id)) {return;}
	     js = d.createElement('script'); js.id = id; js.async = true;
	     js.src = "//connect.facebook.net/en_US/all.js";
	     ref.parentNode.insertBefore(js, ref);
	   }(document));
	 
		$(document).ready(function() {
			$ui.settings.set('appId','<%= ApplicationConfig.client_id%>');
			$ui.settings.set('domain','http://utntacs.appspot.com');
			$ui.settings.set('appUrl','http://apps.facebook.com/captainjacksgroupsix');

		<%//TODO excape char avoid XSS
			String title = request.getParameter("title");
			String link = request.getParameter("link");
			String description = request.getParameter("desc");
			String idViewToLoad = "#userFeedsView";
			if (title != null & link != null)
			{
				idViewToLoad = "#addTorrentView";
				title = "'"+title+"'";
				link = "'"+link+"'";
				description = (description == null)? "null":"'" + description + "'";
				%>
				// If user comes from a share torrent link
				var f = function loadAppDisplayingAddTorrentView(title,url,description) {
					if (title != null & url != null) {
						$("#addTorrentTitleInput").val(title);
						$("#addTorrentLinkInput").val(url);
						if (description != null)
							$("#addTorrentDescTxtarea").val(description);
					}
				};
				f(<%=title%>,<%=link%>,<%=description%>);
		<%	} %>
			$ui.init(function(){showView("<%=idViewToLoad %>");});
		});
		
		function loadNavBar(){
			$("#mainNav").children().each(function() {
				$(this).click(function() {
					showView($(this).attr('data-showview'));
				});
			});	
			$(".navbar").fadeIn();
		}

		function notification(msg, type) {
			$('#mainAlert > span').text(msg)
			return $('#mainAlert').removeClass(
					"alert-block alert-error alert-success alert-info")
					.addClass(type)
		}

		function showView(viewId) {
			// Effects:
			$("#mainNav").children().each(function() {
				$(this).removeClass('active');
			});
			$('[data-showview="'+viewId+'"]').addClass('active');
			
			// Show selected view:
			$("#loadingView").hide();
			$("#userFeedsView").hide();
			$("#addFeedView").hide();
			$("#addTorrentView").hide();
			$(viewId).fadeIn();
		}
		
		function setLoadingMsg(msg) {
			$("#loadingView .lead").text(msg);
		}

		//---- Views sprecific functions ----
		// All feed view:
		var feedsList = {};		
		function loadFeedsFromServer(callback) {
			API.getFeeds({
				error : function() {
					notification(
							"Could load your feeds. Please, try again later.",
							alertStyle.error).show();
				},
				success : function(response) {
					$("#feedsList > .aFeed").remove()
					feedsList = JSON.parse(response);
					refreshFeedList();
					if (callback)
						callback();
					notification().hide();
				}
			});
		}
		function refreshFeedList() {
			// If there are feed, update de list UI to show them
			if (feedsList.length > 0)
			{
				$("#noFeedMsg").hide();
				$(".aFeed").remove()
				$.each(feedsList, function(i, item) {
					var feed = $("#feedsList > .template").clone()
							.appendTo("#feedsList").removeClass("template")
							.addClass("aFeed").fadeIn();
					feed.find("h2").text(item.title);
					feed.find(".feedLink").text($ui.settings.get('domain') + item.rssUrl).attr("href",$ui.settings.get('domain') + item.rssUrl);
					feed.find(".description").text(item.description);
					feed.find(".shareFeed").attr('data-feed-id', item.title);
					feed.find(".shareFeed").click(function() {
						shareFeedInFb($(this).attr('data-feed-id'))
						});
					for (var j = 0; j < item.torrents.length; ++j)
						{
							feed.find("ul").html(feed.find("ul").html() + '<li><a href="'+item.torrents[j].link+'">'+item.torrents[j].title+'</a></li>');
						}
					$('#addTorrentFeedSelect').html($('#addTorrentFeedSelect').html() + "<option value='"+item.title+"'>"+item.title+"</option>");
				});
				$("#feedsList").show();
			} else {
				$("#feedsList").hide();
				$("#noFeedMsg").show();
			}
		}
		function shareFeedInFb(feedTitle)
		{	
			$.each(feedsList, function(i, f) { 
				if(f.title == feedTitle) {
					//TODO: adaptar a publicacion en fb usando js.
					//Posible cient-side vaidations
					var result = API.shareFeed({
						feed: f,
						error: function() { notification("Feed couldn't be share on facebook!",alertStyle.error).flash(); },
						success: function() { 
							notification(f.title + " was shared on our facebook!",alertStyle.success).flash();
						}
					});	
				} 
			});
		}
		
		// Create feed view:
		function createFeed() {
			//Posible cient-side vaidations
			var newFeed = {
					title : $("#feedTitleInput").val(),
					link : $("#feedLinkInput").val(),
					description : $("#feedDescInput").val()
				};
			var titleAlreadyUser = false;
			$.each(feedsList, function (i, elem) {
				titleAlreadyUser = titleAlreadyUser || elem.title == newFeed.title;
			});
			if (titleAlreadyUser)
				notification("Feed already has a that title. Select another title.", alertStyle.warning).flash();
			else
				API.createFeed({
					feed: newFeed,
					error: function(xhr) {
						if (xhr.status == 400)
							notification(xhr.responseText, alertStyle.warning)
								.flash();
						else
							notification("Feed couldn't be created.", alertStyle.error)
							.flash();
					},
					success: function() {
						notification("Feed Created!", alertStyle.success).flash();
						newFeed.torrents = [];
						feedsList.push(newFeed);
						refreshFeedList();
						createFeedResetAndHideForm();
					}
				});
		}

		function createFeedResetAndHideForm() {
			$("#feedTitleInput").val("");
			$("#feedLinkInput").val("");
			$("#feedDescInput").val("");
			showView("#userFeedsView");
		}

		// Add Torrents view:
		function addTorrent() {
			//Posible cient-side vaidations
			
			var feed = $("#addTorrentFeedSelect").val();
			var newTorrent = {
				title : $("#addTorrentTitleInput").val(),
				link : $("#addTorrentLinkInput").val(),
				description : $("#addTorrentDescTxtarea").val(),
			};
			var result = API.addTorrent({
				feed : feed,
				torrent : newTorrent,
				error : function(xhr) {
					if (xhr.status == 400)
						notification(xhr.responseText, alertStyle.warning)
							.flash();
					else if (xhr.status == 404)
						notification("Select a feed from the list where to add the torrent.", alertStyle.warning)
						.flash();
					else
						notification("Torrent couldn't be save!", alertStyle.error)
						.flash();
					
				},
				success : function() {
					notification("Torrent added to " + feed + "!",alertStyle.success).flash();
					$.each(feedsList, function(i, f) {
					    if (f.title == feed) {
					        f.torrents.push(newTorrent)
					        return;
					    }
					});
					refreshFeedList();
					addTorrentResetAndHideForm();
				}
			});
			if ($("#addTorrentShareInFb").is(":checked"))
			{
				API.shareTorrent({
					torrent: newTorrent
				});
			}
		}

		function addTorrentResetAndHideForm() {
			$("#addTorrentTitleInput").val("");
			$("#addTorrentLinkInput").val("");
			$("#addTorrentDescTxtarea").val("");
			$("#addTorrentFeedSelect").val("");
			$("#addTorrentShareInFb").prop('checked', false);
			showView("#userFeedsView");
		}
	</script>
</body>
</html>