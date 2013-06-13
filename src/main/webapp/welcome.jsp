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
	
	/* Customize the navbar links to be fill the entire space of the .navbar */
	.navbar .navbar-inner {
		padding: 0;
	}
	
	.navbar .nav {
		margin: 0;
		display: table;
		width: 100%;
	}
	
	.navbar .nav li {
		display: table-cell;
		width: 1%;
		float: none;
	}
	
	.navbar .nav li a {
		font-weight: bold;
		text-align: center;
		border-left: 1px solid rgba(255, 255, 255, .75);
		border-right: 1px solid rgba(0, 0, 0, .1);
	}
	
	.navbar .nav li:first-child a {
		border-left: 0;
		border-radius: 3px 0 0 3px;
	}
	
	.navbar .nav li:last-child a {
		border-right: 0;
		border-radius: 0 3px 3px 0;
	}
	</style>
	<link href="/assets/css/bootstrap-responsive.css" rel="stylesheet">
</head>

<body>
	<div class="container">
		<div class="masthead">
			<h3 class="muted">White Bay Social Torrents	Feeds</h3>
			<!-- Navigation Bar -->
			<div class="navbar">
				<div class="navbar-inner">
					<div class="container">
						<ul id="mainNav" class="nav">
							<li class="active" data-showview="#userFeedsView"><a
								href="#">Your Feeds</a></li>
							<li data-showview="#addFeedView"><a href="#">Create
									Feed</a></li>
							<li data-showview="#addTorrentView"><a href="#">Add
									Torrent</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<!-- User's Feeds List -->
		<div id="userFeedsView">
		<div id="noFeedMsg" class="jumbotron" style="display: none">
			<h1>Create a Feed!</h1>
			<p class="lead">You have no feed yet. Create a feed to share it
				with your friends.</p>
			<a class="btn btn-large btn-success" href="#">Create Feed</a>
		</div>
		<div id="feedsList" class="row-fluid">
			<div class="span4">
				<h2>Private Feed:</h2>
				<p>
					torrent client url: <a>www.privatefeed.com</a>
				</p>
				<ul>
					<li>torrent1</li>
					<li>torrent1</li>
				</ul>
				<p>
					<a class="btn" href="#">Share in Facebook! &raquo;</a>
				</p>
			</div>
			<div class="span4">
				<h2>Feed 2</h2>
				<ul>
					<li>torrent1</li>
					<li>torrent1</li>
				</ul>
				<p>
					<a class="btn" href="#">Add torrent &raquo;</a>
				</p>
			</div>
			<div class="span4">
				<h2>Feed 3</h2>
				<ul>
					<li>torrent1</li>
					<li>torrent1</li>
				</ul>
				<p>
					<a class="btn" href="#">Add torrent &raquo;</a>
				</p>
			</div>
		</div>
		</div>
	
		<!-- Add Feed View -->
		<form id="addFeedView" class="form-horizontal" class="jumbotron"
			style="display: none">
			<legend>New Feed</legend>
			<div class="control-group">
				<label class="control-label" for="nameInput">Name: </label>
				<div class="controls">
					<input type="text" id="nameInput" placeholder="feed's name...">
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label class="checkbox"> <input type="checkbox">
						Share in Facebook!
					</label>
				</div>
			</div>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">Create Feed</button>
				<button type="button" class="btn">Cancel</button>
			</div>
		</form>
		<!-- Add Torrent to Feed View -->
		<form id="addTorrentView" class="form-horizontal" class="jumbotron"
			style="display: none">
			<legend>Add torrent to feed</legend>
			<div class="control-group">
				<label class="control-label" for="urlInput">Url: </label>
				<div class="controls">
					<input type="text" id="urlInput" placeholder="torrent's url...">
				</div>
			</div>
			<div class="control-group">

				<label class="control-label" for="feedSelect">Feed: </label>
				<div class="controls">
					<select id="feedSelect">
						<option>Private Feed</option>
						<option>Feed 1</option>
						<option>Feed 2</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<label class="checkbox"> <input type="checkbox">
						Share in Facebook!
					</label>
				</div>
			</div>
			<div class="form-actions">
				<button type="submit" class="btn btn-primary">Save Torrent</button>
				<button type="button" class="btn">Cancel</button>
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
	<script type="text/javascript">
		$(document).ready(function() {
			$("#mainNav").children().each(function() {
				$(this).click(function() {
					$("#mainNav").children().each(function() {
						$(this).removeClass('active');
					});
					$(this).addClass('active');
					showView($(this).attr('data-showview'));
				});
			});
		});
		function showView(viewId) {
			$("#userFeedsView").hide();
			$("#addFeedView").hide();
			$("#addTorrentView").hide();
			$(viewId).fadeIn();
		}
	</script>

</body>
</html>