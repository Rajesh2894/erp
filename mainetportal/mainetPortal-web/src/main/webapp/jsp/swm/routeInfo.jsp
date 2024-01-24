<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script	src="https://maps.googleapis.com/maps/api/js?libraries=places&key=AIzaSyAvvwgwayDHlTq9Ng83ouZA_HWSxbni25c"></script>
<script src="js/swm/routeInfo.js"></script>

<form:form action="RouteInfo.html" method="post" class="form-horizontal"	id="routeInfo">
	<div class="form-group">
		<div class="border-1 padding-5" style="height: 600px; width: 100%;"	id="map-canvas2"></div>
	</div>
</form:form>