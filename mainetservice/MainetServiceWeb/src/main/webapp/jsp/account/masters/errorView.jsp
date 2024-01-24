<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/account/fieldMaster.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="mand-label clearfix">
	<span>Exception View</span>
</div>

<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="" text="Exception View" />
		</h2>
	</div>
	<div class="widget-content padding">
		<b>${name}</b>: ${message}

		<% long statusCode = response.getStatus(); 
	out.println("Status is : "+statusCode);
	
	%>
	</div>
</div>