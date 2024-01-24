<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<% request.setCharacterEncoding("UTF-8"); response.setContentType("text/html;charset=UTF-8"); %>
<!DOCTYPE html>
<c:if test="${userSession.languageId eq 1}">
<html lang="en">
</c:if>
<c:if test="${userSession.languageId eq 2}">
<html lang="hi">
</c:if>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title><tiles:insertAttribute name="page-title" />
<c:if test="${userSession.languageId eq 1}"><spring:message code="" text="${userSession.organisation.ONlsOrgname}"/></c:if>
<c:if test="${userSession.languageId eq 2}"><spring:message code="" text="${userSession.organisation.ONlsOrgnameMar}"/></c:if>
</title>

<c:choose>
	<c:when test="${userSession.orgLogoPath ne null && userSession.orgLogoPath ne ''}">
	<link rel="shortcut icon" href="${userSession.orgLogoPath}"></c:when>
	<c:otherwise><link rel="shortcut icon" href="assets/img/abm_logo.ico"></c:otherwise>
</c:choose>
		
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<!-- Base Css Files for Global -->
<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
<link href="assets/libs/animate-css/animate.min.css" rel="stylesheet" />
<link href="assets/libs/pace/pace.css" rel="stylesheet" />
<!-- <link href="assets/css/style-green.css" rel="stylesheet" type="text/css" />
<link href="assets/css/style-responsive.css" rel="stylesheet" /> -->
<link href="assets/libs/lightbox/jquery.fancybox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="assets/libs/chosen/chosen.css">
<link rel="stylesheet" type="text/css" href="css/notification.css"/>
<link href="assets/libs/virtual-keyboard/css/keyboard.css" rel="stylesheet" type="text/css"/>




<!-- scripts -->
<%--  <script src="js/mainet/ui/jquery-1.10.2.min.js"></script> --%>
<script src="js/mainet/ui/jquery.min.js"></script>
<%--<script src="js/mainet/ui/jquery-1.11.1.validate.min.js"></script> --%>
<script src="js/mainet/ui/jquery.validate.min.js"></script>
<script src="js/mainet/login.js"></script>
<script src="js/mainet/alertify.min.js"></script>
<script src="js/mainet/translator.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="assets/libs/bootstrap/js/bootstrap.min.js"></script> 
 <%-- <script src="assets/libs/jqueryui/jquery-ui-1.10.4.custom.min.js"></script> --%> 
<script src="js/mainet/ui/jquery-ui.js"></script>
<script src="assets/libs/pace/pace.min.js"></script> <!--Loader--> 
<script src="assets/libs/jquery-detectmobile/detect.js"></script> <!-- Mobile Detect --> 
<script src="assets/libs/chosen/chosen.jquery.js"></script> 
<script src="assets/js/init.js"></script> <!-- Wedgets Control --> 
<script src="assets/libs/lightbox/jquery.fancybox.js"></script> <!-- LightBox --> 
<script src="assets/libs/virtual-keyboard/js/keyboard.js"></script>


<script>
	$(document).ready(function() {
	$('.fancybox').fancybox();
	});
</script>
<c:choose>
<c:when test="${ cookie['accessibilityCol'].value =='B' and cookie['accessibility'].value =='Y'}">
	<link href="assets/css/blue-contrast.css" rel="stylesheet" type="text/css"  id=""/>
</c:when>
<c:when test="${cookie['accessibilityCol'].value =='O' and cookie['accessibility'].value =='Y'}">
	<link href="assets/css/orange-contrast.css" rel="stylesheet" type="text/css"  id=""/>
</c:when>
<c:when test="${cookie['accessibilityCol'].value =='G' and cookie['accessibility'].value =='Y'}">
	<link href="assets/css/green-contrast.css" rel="stylesheet" type="text/css"  id=""/>
</c:when>
<c:when test="${ request.getParameter('accessibility')=='Y' or cookie['accessibility'].value =='Y'}">
	<link href="assets/css/blue-contrast.css" rel="stylesheet" type="text/css"  id=""/>
</c:when>
<c:when test="${cookie['accessibilityCol'].value =='O' and cookie['accessibility'].value =='M'}">
	<link href="assets/css/style-orange.css" rel="stylesheet" type="text/css"  id=""/>
</c:when>
<c:when test="${cookie['accessibilityCol'].value =='B' and cookie['accessibility'].value =='M'}">
	<link href="assets/css/style.css" rel="stylesheet" type="text/css"  id=""/>
</c:when>
<c:when test="${ cookie['accessibility'].value =='M' or empty cookie['accessibility'].value }">
	<link href="assets/css/style-green.css" rel="stylesheet" type="text/css"  id=""/>
	<script>
	 $(document).ready(function($) {
	  var d = new Date();
	  d.setTime(d.getTime() + (1*24
			  *60*60*1000));
	  var expires = "expires=" + d.toGMTString();
	  document.cookie = "accessibility"+"="+'M'+"; "+expires; 
	});
	</script>
</c:when>
</c:choose>
<link href="assets/css/style-responsive.css" rel="stylesheet" />



</head>
<body>
	
	<tiles:insertAttribute name="app-header" />
    <tiles:insertAttribute name="body" />	
	<!-- <div id="ajaxloader">&nbsp;</div> -->
	<div class="msg-dialog-box ok-msg" style="display: none;"></div>
	<div class="popup-dialog dialog" style="display: none;"></div>
	<div class="child-popup-dialog dialog" style="display: none;"></div>
	<div class="online-service-dialog dialog" style="display: none;"></div>
	<div class="error-dialog" style="display: none;"></div>
	<tiles:insertAttribute name="app-footer" />
</body>
</html>
