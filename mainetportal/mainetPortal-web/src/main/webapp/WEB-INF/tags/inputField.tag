<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ attribute name="fieldPath" required="true" rtexprvalue="false" type="java.lang.String"%>
<%@ attribute name="isPassword" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="hasId" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="isReadonly" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="isDisabled" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Custome CSS class(es) to be apply for element." %>
<%@ attribute name="maxlegnth" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="isMandatory" required="false" type="java.lang.String" description="To show astrik mark if the field is mandatory."%>
<jsp:useBean id="stringResolver" class="com.abm.mainet.common.util.StringUtility"/>


<c:choose>
	<c:when test="${isMandatory}">
		<c:set var="mandcolor" value="mandClassColor" />
	</c:when>
	<c:otherwise>
		<c:set var="mandcolor" value="" />
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${isPassword}">
		<form:password path="${fieldPath}" cssClass="${cssClass}" readonly="${isReadonly}" disabled="${isDisabled}" />
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${hasId}">
				
				<c:set var="id" value="${stringResolver.replaceAllDotPrefix(fieldPath)}"/>	
			
				<form:input path="${fieldPath}" 
							id="${id}" 
							cssClass="${cssClass} ${mandcolor}"
							readonly="${isReadonly}" 
							disabled="${isDisabled}" 
							maxlength="${maxlegnth}"
							autocomplete="off" />
					
			</c:when>
			<c:otherwise>
				<form:input path="${fieldPath}" 
							cssClass="${cssClass} ${mandcolor}"
							readonly="${isReadonly}" 
							disabled="${isDisabled}" 
							maxlength="${maxlegnth}" 
							autocomplete="off" />
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>