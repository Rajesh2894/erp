<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="apptags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>404 Not Found</title>
<link rel="shortcut icon" href="assets/img/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
<link href="assets/libs/animate-css/animate.min.css" rel="stylesheet" />
<link href="assets/libs/pace/pace.css" rel="stylesheet" />
<link href="assets/css/style.css" rel="stylesheet" type="text/css" />
<style>
.widget {
	margin: 0rem;
}
.widget-content {
	height: 100vh;
}
.pnf-container {
	height: 60vh;
	position: relative;
}
.pnf-container img[alt="Page not found"] {
	position: absolute;
	top: 50%;
	left: 50%;
	-webkit-transform: translate(-50%, -50%);
	-moz-transform: translate(-50%, -50%);
	-o-transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
	transform: translate(-50%, -50%);
}
</style>

</head>
<body class="fixed-left full-content">

<!-- <div class="container">
	<div class="full-content-center animated flipInX">
		<h1>404</h1>
		<h2>Thank you for your interest.</h2>
		<h4>The document is under process <br>
		or page is under construction</h4>
		<a class="btn btn-blue-1 btn-sm" href="CitizenHome.html">Back to Home</a>
	</div> 
	</div> -->
	<div class="content animated">
		<div class="widget">
			<div class="widget-content padding text-center">
	           <div class="pnf-container">
	           	<img src="images/page-not-found.jpg" alt="Page not found" class="img-responsive text-center" style="margin:auto;">
	           </div>
	           <div class="text-center">
	           	<a class="btn btn-blue-1 btn-md" href="CitizenHome.html"><spring:message code="BacktoHome" text="Back to Home" /></a>
	           </div>
	        </div>
	     </div>
     </div>


<script src="assets/libs/pace/pace.min.js"></script>  
</body>
</html>