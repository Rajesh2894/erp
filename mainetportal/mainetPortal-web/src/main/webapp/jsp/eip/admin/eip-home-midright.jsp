<nav class="navbar navbar-default" role="navigation">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target="#bs-navbar-collapse-1">
			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>
			<span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<span class="brand visible-xs">Patna Municipal Corporation</span>
	</div>

	<div class="collapse navbar-collapse" id="bs-navbar-collapse-1">
		<ul class="nav navbar-nav">
			<li class="active"><a href="#">Home</a></li>
			<li><a href="CitizenAboutUs.html">About Us</a></li>
			<li><a href="#">Online Citizen Services</a></li>
			<li><a href="#">FAQ's</a></li>
		</ul>
		<form class="navbar-form navbar-left form-search pull-right"
			role="search">
			<div class="form-group">
				<input class="form-control" name="Search" type="text" value="<spring:message code="eip.search"/>"
					onblur="if (this.value=='') this.value = 'Search'"
					onfocus="if (this.value=='Search') this.value = ''" />
				<button type="submit" class="btn btn-default">Submit</button>
			</div>
		</form>
	</div>
	
</nav>



