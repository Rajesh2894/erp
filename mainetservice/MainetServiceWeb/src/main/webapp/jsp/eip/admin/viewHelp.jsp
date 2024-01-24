<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="eip.citizen.pbNotice.doc" /></title>
<script>

function showHelpDoc(url)
{
	
	 window.open('./'+url,'_self',false);	
	
}

</script>

</head>
<c:set var="showDocMeth" value=""/>
<c:choose>
<c:when test="${not empty helpDocPath}">
<c:set var="showDocMeth" value="showHelpDoc('${helpDocPath}')"/>
</c:when>
<c:when test="${not empty command.filePath}">
<c:set var="showDocMeth" value="showHelpDoc('${command.filePath}')"/>

<!-- Write this code to escape quote in file name -->
<c:set var="filename" value="${command.filePath}" />
<c:set var="quote" value="'" />
<c:set var="escapeQuote" value="\\&apos;" />
<c:if test="${fn:contains(filename,quote)}">
	<c:set var="filePath" value="${fn:replace(filename,quote,escapeQuote)}"/>
	<c:set var="showDocMeth" value="showHelpDoc('${filePath}')"/>
</c:if>

</c:when>
</c:choose>
<body onload="${showDocMeth}">

</body>
</html>
		