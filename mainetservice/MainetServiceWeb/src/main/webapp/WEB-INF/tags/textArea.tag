<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ attribute name="path" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="isReadonly" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="isDisabled" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Custome CSS class(es) to be apply for element." %>
<%@ attribute name="maxlegnth" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="isMandatory" required="false" type="java.lang.String" description="To show astrik mark if the field is mandatory."%>
<%@ attribute name="labelCode" required="true" rtexprvalue="false" type="java.lang.String"%>
<%@ attribute name="dataRuleMinlength" required="false" rtexprvalue="false" type="java.lang.String"%>

<jsp:useBean id="stringResolver" class="com.abm.mainet.common.utility.StringUtility"/>

<c:set var="id" value="${stringResolver.replaceAllDotPrefix(path)}"/>	

<c:choose>
	<c:when test="${isMandatory}">
		<c:set var="mandcolor" value="mandColorClass" />
		<c:set var="requiredControl" value="required-control" />
		
	</c:when>
	<c:otherwise>
		<c:set var="mandcolor" value="" />
		<c:set var="requiredControl" value="" />
	</c:otherwise>
</c:choose>

<label class="col-sm-2 control-label ${requiredControl}" for="${id}"><spring:message code="${labelCode}" text="${labelCode}"/></label>

	<div class="col-sm-4">
				
			
				<form:textarea path="${path}" 
							id="${id}"
							cssClass="form-control ${cssClass} ${mandcolor}"
							readonly="${isReadonly}" 
							disabled="${isDisabled}" 
							maxlength="${maxlegnth}"
							data-rule-required="${isMandatory}"
							data-rule-minlength="${dataRuleMinlength}"
							autocomplete="off"/>
					
	</div>