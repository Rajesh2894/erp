<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="buttonLabel" required="false" rtexprvalue="false"%>
<%@ attribute name="isChildButton" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="isDisabled" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Custome CSS class(es) to be apply for element." %>

<c:if test="${buttonLabel == null}">
	<c:set var="buttonLabel" value="cancelBtn"/>
</c:if>

<c:set var="buttonLabelText">
	<spring:message code="${buttonLabel}" text="${buttonLabel}"/>
</c:set>
	
<c:choose>
	
	<c:when test="${isDisabled}">
		<input 	type="button" class="disable ${cssClass}" disabled="disabled" value="${buttonLabelText}"/>
	</c:when>
	
	<c:otherwise>
	
		<c:set var="onClickScript" value="return false;"/>
		
		<c:if test="${isChildButton}">
			<c:set var="onClickScript" value="return _cancelChildForm(this);"/>
		</c:if>
	
		<input 	type="button" class="css_btn ${cssClass}" onclick="${onClickScript}" value="${buttonLabelText}"/>
		
	</c:otherwise>
</c:choose>