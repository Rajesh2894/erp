<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
    request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
%>
<!DOCTYPE html>

<c:choose>
	<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
		<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"
			dir="ltr">
	</c:when>
	<c:otherwise>
		<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="hi"
			dir="ltr">
	</c:otherwise>
</c:choose>


<head>
<meta charset="UTF-8">
<c:if test="${empty pageName}">
	<title><tiles:insertAttribute name="page-title" /></title>
</c:if>

<c:if test="${not empty pageName}">
	<title><c:out value="${pageName}" /></title>
</c:if>
<link rel="shortcut icon" href="assets/img/favicon.ico">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<!-- SEO Details -->
<c:set value="${userSession.getSEOMasterData() }" var="seo" />
<meta name="description" content="${seo.description}">
<meta name="keywords" content="${seo.keyWord}">

<link href="assets/libs/bootstrap/css/bootstrap.min-theme-1.css" rel="stylesheet" />
<link href="assets/libs/font-awesome/css/font-awesome.min.css" 
	rel="stylesheet" />
<link href="assets/libs/animate-css/animate.min.css" rel="stylesheet" />
<!-- <link href="assets/libs/pace/pace.css" rel="stylesheet" /> -->
<link href="assets/libs/chosen/chosen.css" rel="stylesheet"
	type="text/css">
<!-- <link href="assets/css/theme.css" rel="stylesheet" type="text/css" /> -->
<!-- <link href="assets/css/style-responsive.css" rel="stylesheet" /> -->
<link href="assets/libs/jqueryui/jquery-ui.min.css" rel="stylesheet"
	type="text/css" />
<link href="assets/libs/lightbox/jquery.fancybox.css" rel="stylesheet"
	type="text/css" />
<link href="css/mainet/ui.jqgrid.css" rel="stylesheet" type="text/css" />
<link href="css/QandA/alertify.css" rel="stylesheet" type="text/css" />
<link href="assets/libs/owl-carousel/owl.carousel.css" rel="stylesheet"
	type="text/css" />
<link href="assets/libs/jqueryui/jquery-ui-timepicker-addon.css"
	rel="stylesheet" type="text/css" />

<style type="text/css" media="screen">
html, body {
	font-size: 93% !important;
}
</style>
<c:choose>
	<c:when test="${ cookie['accessibilitytextsize'].value =='LGT' }">
		<style type="text/css" media="screen">
html, body {
	font-size: 110% !important;
}
</style>
	</c:when>
	<c:when test="${ cookie['accessibilitytextsize'].value =='LGR' }">
		<style type="text/css" media="screen">
html, body {
	font-size: 105% !important;
}
</style>
	</c:when>
	<c:when test="${ cookie['accessibilitytextsize'].value =='MDM' }">
		<style type="text/css" media="screen">
html, body {
	font-size: 100% !important;
}
</style>
	</c:when>
	<c:when test="${ cookie['accessibilitytextsize'].value =='SLR' }">
		<style type="text/css" media="screen">
html, body {
	font-size: 98% !important;
}
</style>
	</c:when>
	<c:when test="${ cookie['accessibilitytextsize'].value =='SLT' }">
		<style type="text/css" media="screen">
html, body {
	font-size: 95% !important;
}
</style>
	</c:when>
</c:choose>
<script src="js/eip/citizen/baseLayout.js"></script>
<script src="assets/libs/jquery/jquery-1.11.1.min.js"></script>
	
	
<script src="assets/libs/jquery/jquery-1.11.1.validate.min.js"></script>
<script src="js/mainet/validation/globalValidation.js"></script>
<script src="assets/libs/jqueryui/jquery-ui.min.js"></script>
<script src="assets/libs/superfish/superfish.min.js"></script>
<script src="assets/libs/jqueryui/grid.locale-en.js"></script>
<script src="assets/libs/jquery-slimscroll/jquery.slimscroll.js"></script>
<script src="assets/libs/owl-carousel/owl.carousel.min.js"></script>
<script src="assets/libs/jqueryui/jquery-ui-timepicker-addon.js"></script>

<c:choose>
	<c:when
		test="${ cookie['accessibilityCol'].value =='B' and cookie['accessibility'].value =='Y'}">
		<link href="assets/css/style-accessibility-theme-1.css"
			rel="stylesheet" type="text/css" id="" />

	</c:when>
	<c:when
		test="${cookie['accessibilityCol'].value =='O' and cookie['accessibility'].value =='Y'}">
		<link href="assets/css/style-accessibility-theme-1.css"
			rel="stylesheet" type="text/css" id="" />

	</c:when>
	<c:when
		test="${cookie['accessibilityCol'].value =='G' and cookie['accessibility'].value =='Y'}">
		<link href="assets/css/style-accessibility-theme-1.css"
			rel="stylesheet" type="text/css" id="" />

	</c:when>
	<c:when
		test="${ request.getParameter('accessibility')=='Y' or cookie['accessibility'].value =='Y'}">
		<link href="assets/css/style-accessibility-theme-1.css"
			rel="stylesheet" type="text/css" id="" />

	</c:when>
	<c:when
		test="${cookie['accessibilityCol'].value =='O' and cookie['accessibility'].value =='M'}">
		<link href="assets/css/style-orange-theme-1.css" rel="stylesheet"
			type="text/css" id="" />

		<link href="assets/css/dashboard-theme-1.css" rel="stylesheet"
			type="text/css" id="" />
	</c:when>
	<c:when
		test="${cookie['accessibilityCol'].value =='G' and cookie['accessibility'].value =='M'}">
		<link href="assets/css/style-green-theme-1.css" rel="stylesheet"
			type="text/css" id="" />
		<link href="assets/css/dashboard-theme-1.css" rel="stylesheet"
			type="text/css" id="" />
	</c:when>
	<c:when
		test="${ cookie['accessibility'].value =='M' or empty cookie['accessibility'].value }">
		<link href="assets/css/style-blue-theme-1.css" rel="stylesheet"
			type="text/css" id="" />
		<link href="assets/css/dashboard-theme-1.css" rel="stylesheet"
			type="text/css" id="" />



		<script>
			$(document).ready(function($) {
				var d = new Date();
				d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
				var expires = "expires=" + d.toGMTString();
				document.cookie = "accessibility" + "=" + 'M' + "; " + expires;
			});
		</script>
	</c:when>
</c:choose>
<script>
	(function($) {
		$(document).ready(function() {
			$('.key-in .list-group').slimScroll({
				color : '#000',
				size : '10px',
				height : '400px',
				alwaysVisible : true,

			});
			var example = $('#tabmenuhover').superfish();
		});
	})(jQuery);
</script>
</head>
<noscript>
	<p class="text-center">This page is trying to run JavaScript and
		your browser either does not support JavaScript or you may have
		turned-off JavaScript. If you have disabled JavaScript on your
		computer, please turn on JavaScript, to have proper access to this
		page.</p>
</noscript>
<form:form id="postMethodFormSuccess" method="POST" class="form"></form:form>
<body>
	<div id="text-resize">
		<a tabindex="-1" id="MainContent" name="MainContent"></a>
		<tiles:insertAttribute name="app-header" />

		<!-- 	<!-- <div class="container-fluid"> 
	<div class="row row-eq-height"> -->
		<%-- 	<div class="col-sm-2 bg-white-1 hidden-sm hidden-md hidden-xs hidden-xs"><div class="row"><tiles:insertAttribute name="side-menu"/></div></div>
 --%>
		<!-- <div class="col-sm-12 col-md-12 col-xs-12 col-lg-10"><div class="row"> -->
		<div class="content-page">
			<tiles:insertAttribute name="app-body" />
		</div>
		<!-- </div></div> -->

		<!-- </div>
	</div> -->
		<tiles:insertAttribute name="app-footer" />
		<!-- Pop up Dilog  -->
		<div class="msg-dialog-box ok-msg" style="display: none;"></div>
		<div class="popup-dialog dialog" style="display: none;"></div>
		<div class="child-popup-dialog dialog" style="display: none;"></div>
		<div class="online-service-dialog dialog" style="display: none;"></div>
		<div class="error-dialog" style="display: none;"></div>
	</div>

	<!-- the overlay modal element -->
	<div class="md-overlay"></div>
	<!-- End of eoverlay modal -->
	<script>
		var resizefunc = [];
	</script>

	<script
		src="assets/libs/bootstrap/js/bootstrap.min.js"></script>
	<!-- <script src="assets/libs/pace/pace.min.js"></script> Loader   -->
	<script src="assets/js/init.js"></script>
	<!-- Wedgets Control -->
	<script
		src="assets/libs/lightbox/jquery.fancybox.min.js"></script>
	<!-- LightBox -->
	<script src="assets/js/jquery.scrollbox.min.js"></script>
	<!-- Scroll Announcement plugins -->
	<!-- <script src="assets/js/menu.min.js"></script> Top Menu  -->
	<script
		src="assets/libs/chosen/chosen.jquery.min.js"></script>
	<c:if
		test="${!empty userSession.employee.emploginname and userSession.employee.emploginname ne 'NOUSER' }">
		<script src="js/mainet/jquery.jqGrid.min.js"></script>
		<script src="js/mainet/framework.grid.min.js"></script>
		<script src="js/mainet/framework-api.min.js"></script>
	</c:if>
	<script src="js/mainet/login.js"></script>
	<script src="js/mainet/script-library.js"></script>
	<script src="js/eip/citizen/landingPage.js"></script>
	<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
		<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.min.js"></script>
		
	<!-- Add fancyBox main JS and CSS files -->
	<script>
		$(document).ready(function() {
			$('.fancybox').fancybox();
		});
		$(window).load(function() {
			$('.columns-multilevel').slimScroll({
				height : '430px',
				color : '#fff',
				alwaysVisible : true
			});
		});
	</script>
</body>
</html>