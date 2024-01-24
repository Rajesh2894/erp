<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="buttonLabel" required="false" rtexprvalue="false"%>
<%@ attribute name="url" required="true" rtexprvalue="false"%>
<%@ attribute name="isDisabled" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Custome CSS class(es) to be apply for element." %>

<c:if test="${buttonLabel == null}">
	<c:set var="buttonLabel" value="bckBtn"/>
</c:if>

<c:set var="buttonLabelText">
	<spring:message code="${buttonLabel}" text="${buttonLabel}"/>
</c:set>
	
<c:choose>
	
	<c:when test="${isDisabled}">
		<input 	type="button" class="btn btn-danger" disabled="disabled" value="${buttonLabelText}"/>
	</c:when>
	
	<c:otherwise>
		<input 	type="button" class="btn btn-danger" onclick="window.location.href='${url}'" value="${buttonLabelText}" title="${buttonLabelText}"/>
	</c:otherwise>
</c:choose>