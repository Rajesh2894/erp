<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ attribute name="buttonLabel" required="false" rtexprvalue="false"%>
<%@ attribute name="isDisabled" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="handler" required="false" rtexprvalue="false" description="Button handler"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Custome CSS class(es) to be apply for element." %>

<c:if test="${buttonLabel == null}">
	<c:set var="buttonLabel" value="rstBtn"/>
</c:if>

<c:set var="buttonLabelText">
	<spring:message code="${buttonLabel}" text="${buttonLabel}"/>
</c:set>


<c:choose>

	<c:when test="${isDisabled}">
		<input 	type="button" class="disable ${cssClass}" disabled="disabled" onclick="${handler}" value="${buttonLabelText}" />
	</c:when>
	<c:otherwise>
		<input 	type="button" class="btn btn-warning ${cssClass}" value="${buttonLabelText}" onclick="resetForm(this)" />
	</c:otherwise>
	
</c:choose>