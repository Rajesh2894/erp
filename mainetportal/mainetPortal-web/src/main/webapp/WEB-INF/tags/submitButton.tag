<%@tag import="com.abm.mainet.common.exception.FrameworkException"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="buttonLabel" required="false" rtexprvalue="false"%>
<%@ attribute name="isChildButton" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="isDisabled" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="successUrl" required="false" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="entityLabelCode" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Custome CSS class(es) to be apply for element." %>
<%@ attribute name="actionParam" required="false" rtexprvalue="false" description="Tells extra parameter of form action rather than default one. Default is 'save'."%>
<%@ attribute name="pageSpecSuccessMsg" required="false" rtexprvalue="true" type="java.lang.String"%>
<%
	if (isChildButton != null && isChildButton == false && (successUrl == null || successUrl.length() == 0))
	{
		throw new FrameworkException("You must specify 'successUrl' for Submit button when not used for a child form.");
	}
%>
<c:if test="${buttonLabel == null}">
	<c:set var="buttonLabel" value="saveBtn"/>
</c:if>

<c:set var="buttonLabelText">
	<spring:message code="${buttonLabel}" text="${buttonLabel}"/>
</c:set>
	
<c:set var="entityLabelText">
	<spring:message code="${entityLabelCode}" text="${entityLabelCode}"/>
</c:set>

<c:set var="successMessage">
	<spring:message code="Save.SUCCESS" arguments="${entityLabelText}" text="Save.SUCCESS"/>
</c:set>
 <c:if test="${not empty pageSpecSuccessMsg }">
	<c:set var="successMessage" value ="${pageSpecSuccessMsg }"/>
</c:if> 
<c:choose>
	
	<c:when test="${isDisabled}">
		<input 	type="submit" class="disable ${cssClass}" disabled="disabled" value="${buttonLabelText}"/>
	</c:when>
	
	<c:otherwise>
	
		<c:set var="onClickScript" value="return saveOrUpdateForm(this, '${successMessage}', '${successUrl}','${actionParam}');"/>
		
		<c:if test="${isChildButton}">
			<c:set var="onClickScript" value="return _saveChildForm(this, '${successMessage}');"/>
		</c:if>
	
		<input 	type="submit" class="css_btn ${cssClass}" onclick="${onClickScript}" value="${buttonLabelText}"/>
		
	</c:otherwise>
</c:choose>