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
      margin-bottom: -90px; /* Negative margin to pull up carousel. 90px is roughly margins and height of navbar. */
    }
    
	/* Downsize the brand/project name a bit */
    .navbar .brand {
      padding-right: 20px;
      font-size: 16px;
      font-weight: bold;
    }
    
    /* Navbar links: increase padding for taller navbar */
    .navbar .nav > li > a {
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
	<div class="container">
		<div class="masthead">
			<h3 class="muted">White Bay Social Torrents	Feeds</h3>
			<!-- NNEWWW Navigation Bar -->
			<div class="navbar">
	          <div class="navbar-inner">
	            <!-- Responsive Navbar Part 1: Button for triggering responsive navbar (not covered in tutorial). Include responsive CSS to utilize. -->
	            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
	              <span class="icon-bar"></span>
	              <span class="icon-bar"></span>
	              <span class="icon-bar"></span>
	            </button>
	            <a class="brand" style="border-right: 1px solid rgba(0, 0, 0, .1);" href="#">username</a>
	            <!-- Responsive Navbar Part 2: Place all navbar contents you want collapsed withing .navbar-collapse.collapse. -->
	            <div class="nav-collapse collapse">
	              <ul id="mainNav" class="nav">
	                <li data-showview="#userFeedsView" class="active"><a href="#">Your feeds</a></li>
	                <li data-showview="#addFeedView"><a href="#">Create feed</a></li>
	                <li data-showview="#addTorrentView"><a href="#">Add torrent</a></li>
	              </ul>
	              <ul class="nav" style="float: right;margin: 0;border-left: 0px;">
	                <!-- Read about Bootstrap dropdowns at http://twitter.github.com/bootstrap/javascript.html#dropdowns -->
	                <li class="dropdown">
	                  <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-cog"></i><b class="caret"></b></a>
	                  <ul class="dropdown-menu">
	                    <li><a href="#">SignOut</a></li>
	                  </ul>
	                </li>
	              </ul>
	            </div><!--/.nav-collapse -->
	          </div><!-- /.navbar-inner -->
	        </div>
		</div>
		<div id="mainAlert" class="alert text-center" style="display:none"> <button type="button" class="close" data-dismiss="alert">&times;</button><span></span></div>
		<!-- User's Feeds List -->
		<div id="userFeedsView">
		<div id="noFeedMsg" class="jumbotron" style="display: none">
			<h1>Create a Feed!</h1>
			<p class="lead">You have no feed yet. Create a feed to share it
				with your friends.</p>
			<a class="btn btn-large btn-success" href="#">Create Feed</a>
		</div>
		<div id="feedsList" class="row-fluid">
			<div class="span4 template" style="display:none">
				<h2>Private Feed:</h2>
				<p class="description" style="font-style:italic"></p>
				<p>
					Feed rss url: <a class="feedLink">utntcas.appspot.com/rss/23423</a>
				</p>
				<ul>
				</ul>
				<p>
					<a class="btn" onclick="API.shareFeed('')" href="#">Share in Facebook! &raquo;</a>
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
					<input type="text" id="feedTitleInput" placeholder="feed's title...">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="feedLinkInput">Link: </label>
				<div class="controls">
					<input type="text" id="feedLinkInput" placeholder="feed's link...">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="feedDescInput">Description: </label>
				<div class="controls">
					<textarea rows="3" id="feedDescInput" placeholder="feed's description..."></textarea>
				</div>
			</div>
			<div class="form-actions">
				<button type="submit" onclick="createFeed();" class="btn btn-primary">Create Feed</button>
				<button type="button" class="btn" onclick="createFeedCancel();">Cancel</button>
			</div>
		</form>
		<!-- Add Torrent to Feed View -->
		<form id="addTorrentView" class="form-horizontal" class="jumbotron"
			style="display: none">
			<legend>Add torrent to feed</legend>
			<div class="control-group">
				<label class="control-label" for="addTorrentTitleInput">Title: </label>
				<div class="controls">
					<input type="text" id="addTorrentTitleInput" placeholder="torrent's title...">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="addTorrentLinkInput">Url: </label>
				<div class="controls">
					<input type="text" id="addTorrentLinkInput" placeholder="torrent's url...">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="addTorrentDescTxtarea">Description: </label>
				<div class="controls">
					<textarea rows="3" id="addTorrentDescTxtarea" placeholder="torrent's description..."></textarea>
				</div>
			</div>
			<div class="control-group">

				<label class="control-label" for="addTorrentFeedSelect">Feed: </label>
				<div class="controls">
					<select id="addTorrentFeedSelect">
						<option>Private Feed</option>
						<option>Feed 1</option>
						<option>Feed 2</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label class="checkbox"> <input id="addTorrentShareInFb" type="checkbox">
						Share in Facebook!
					</label>
				</div>
			</div>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary" onclick="addTorrent();">Save Torrent</button>
				<button type="button" class="btn" onclick="addTorrentCancel();">Cancel</button>
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
	<script src="/assets/js/bootstrapExtension.js"></script>
	<script src="/assets/js/ApiREST.js"></script>
	<script type="text/javascript">
        // UI Javascript Functions:
		$(document).ready(function() {
			//Init Nav bar behavior
			$("#mainNav").children().each(function() {
				$(this).click(function() {
					$("#mainNav").children().each(function() {
						$(this).removeClass('active');
					});
					$(this).addClass('active');
					showView($(this).attr('data-showview'));
				});
			});
			
			updateFeedList();
			// If user comes from a share torrent link
			var f = function loadAppDisplayingAddTorrentView(torrenturl) {
				if (torrenturl != null)
				{
					//todo: update nav buttons state. remove from $(document).ready()
					var t = getTorrent(torrenturl);
					$("#addTorrentTitle").val(t.title);
					$("#addTorrentUrlInput").val(t.url);
					if (t.description =! null)
						$("#addTorrentDesc").val(t.description);
					showView("addTorrentView");
				}
			};
			
			<%
			//TODO excape char avoid XSS
				String addTorrent = request.getParameter("addTorrent");
				if (addTorrent != null)
			    	out.println("f('" + addTorrent +"');");
			%>
		});
	
		function notification(msg,type){
			$('#mainAlert > span').text(msg)
			return $('#mainAlert').removeClass("alert-block alert-error alert-success alert-info").addClass(type)
		}
		
		function showView(viewId) {
			$("#userFeedsView").hide();
			$("#addFeedView").hide();
			$("#addTorrentView").hide();
			$(viewId).fadeIn();
		}
		
		//---- Views sprecific functions ----
		var feedsList;
		// All feed view:
		function updateFeedList()
		{
			API.getFeeds({
				error: function() { notification("Could load your feeds. Please, try again later.",alertStyle.error).show();},
				success: function(response) {
					$("#feedsList > .aFeed").remove()
					feedsList = JSON.parse(response);
					//TODO: load feeds:
					$.each( feedsList, function( i, item ) {
						var feed = $("#feedsList > .template")
							.clone()
							.appendTo( "#feedsList" )
							.removeClass("template")
							.addClass("aFeed")
							.fadeIn();
						feed.find("h2").text(item.title);
						feed.find(".feedLink").text(item.link);
						feed.find(".description").text(item.description);
						//feed.find("ul").text(item.link);
						
					      
					});				
					notification().hide();
				}
			});
		}
		
		// Create feed view:
		function createFeed()
		{
			//Posible cient-side vaidations
			API.createFeed({
				feed: {
					title: $("#feedTitleInput").val(),
					link: $("#feedLinkInput").val(),
					description: $("#feedDescInput").val()
				},
				error: function() { notification("Feed couldn't be created.",alertStyle.error).flash(); },
				success: function() { 
					notification("Feed Created!",alertStyle.success).flash();
					showView("#userFeedsView");
				}
			});
		}
		function createFeedCancel()
		{
			$("#feedTitleInput").val("");
			$("#feedLinkInput").val("");
			$("#feedDescInput").val("");
			showView("#userFeedsView");
		}

		// Add Torrents view:
		function addTorrent()
		{
			//Posible cient-side vaidations
			var feed = $("#addTorrentFeedSelect").val();
			var result = API.addTorrent({
				feed: feed,
				torrent: {
					title:$("#addTorrentTitleInput").val(),
					link:$("#addTorrentLinkInput").val(),
					description: $("#addTorrentDesc").val(),
					shareInFb: $("#addTorrentShareInFb").is(":checked") 
				},
				error: function() { notification("Torrent couldn't be save!",alertStyle.error).flash(); },
				success: function() { 
					notification("Torrent added to " + feed + "!",alertStyle.success).flash();
					showView("#userFeedsView");
				}
			});	
		}
		function addTorrentCancel(feedId)
		{
			$("#addTorrentTitleInput").val("");
			$("#addTorrentLinkInput").val("");
			$("#addTorrentDescTxtarea").val("");
			$("#addTorrentFeedSelect").val("");
			$("#addTorrentShareInFb").prop('checked',false);
			showView("#userFeedsView");
		}
	</script>
</body>
</html>