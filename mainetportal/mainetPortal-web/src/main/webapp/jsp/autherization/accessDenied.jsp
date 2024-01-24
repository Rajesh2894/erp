<script src="js/mainet/jquery-1.11.2.js"></script>
<script>
	$(document).ready(function(e) {
		setTimeout(function() {
			window.open('CitizenHome.html', '_self');
		}, 5000);
	});
</script>


<style>
.session-expired {
	padding: 10px;
	margin: auto;
	margin-top: 13%;
	width: 600px;
	border: 1px solid #ddd;
	border-radius: 10px;
	overflow: hidden;
	background: #F6F6F6;
}

.session-expired .data {
	float: left;
	width: 400px;
	padding: 0px 20px;
}

.session-expired .data h1 {
	font-size: 1.5em;
	line-height: 35px;
	font-weight: normal;
	color: #274472;
	padding: 5px 0px;
	margin: 0px;
	font-family: 'Arial';
	float: left;
}

.session-expired .data p {
	font-size: 0.85em;
	line-height: 13px;
	padding: 5px 0px;
	color: #000;
	margin: 0px;
	clear: both;
	font-family: 'Arial';
	float: left;
}

.left {
	float: left;
	padding: 10px;
}

.session-expired .data ul {
	float: left;
	list-style: none;
	clear: both;
	margin: 10px 0px;
	padding: 0px;
	display: block;
}

.session-expired .data ul li {
	font-size: 0.75em;
	list-style: inside;
	margin-left: 10px;
	float: left;
	overflow: hidden;
	line-height: 15px;
	font-style: italic;
	padding: 5px 0px;
	color: #313131;
	margin: 0px;
	font-family: 'Arial';
}

.css_btn {
	background: #63b2f5; */
	border: 1px solid #3ea1f6; */
	border-radius: 3px;
	-webkit-border-radius: 4px;
	color: #fff;
	cursor: pointer;
	display: block;
	font-size: 12px;
	border: 0px !important;
	font-weight: normal;
	padding: 10px 20px;
	line-height: 15.5px;
}

.css_btn:hover {
	background: #274472;
	border: 1px solid rgba(39, 68, 114, 0.5);
	box-shadow: inset 0 5px 10px rgba(39, 68, 114, 0.5), inset 0px 1px 0px
		0px #274472;
}
</style>

<div class="session-expired">

	<img src="assets/img/error-7-xxl.png" width="100" height="100" alt="error-7-xxl"
		class="left" />
	<div class="data">
		<div class="form">
			<h1>You have been logged out since you logged in from another device.</h1>
			<br>

		</div>
	</div>
</div>