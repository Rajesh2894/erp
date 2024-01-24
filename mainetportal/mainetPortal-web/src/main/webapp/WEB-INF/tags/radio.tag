<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ attribute name="labelCode" required="true" rtexprvalue="false" type="java.lang.String"%>
<%@ attribute name="path" required="true" rtexprvalue="true"%>
<%@ attribute name="radioValue" required="true" type="java.lang.String" %>
<%@ attribute name="radioLabel" required="true" type="java.lang.String" %>
<%@ attribute name="changeHandler" required="false" rtexprvalue="true"%>
<%@ attribute name="disabled" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Custome CSS class(es) to be apply for element." %>
<%@ attribute name="isMandatory" required="false" type="java.lang.String" description="To show astrik mark if the field is mandatory."%>
<%@ attribute name="defaultCheckedValue" required="false" rtexprvalue="false" type="java.lang.String"%>

<jsp:useBean id="stringResolver" class="com.abm.mainet.common.util.StringUtility"/>

<c:set var="id" value="${stringResolver.replaceAllDotPrefix(path)}"/>
<c:set var="values" value="${stringResolver.splitByComma(radioValue)}"/>
<c:set var="labels" value="${stringResolver.splitByComma(radioLabel)}"/>

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
	<div class="radio col-sm-4">
  <c:forEach items="${values}" var="value" varStatus="indexValue"> 
 <c:set var="label" value="${labels[indexValue.index]}"/>
		<label class="radio-inline padding-top-0"> 
		
		<c:choose>
		<c:when test="${value eq defaultCheckedValue}">
		<form:radiobutton path="${path}" value="${value}" id="${id}" disabled="${disabled}"
		 cssClass="${cssClass}" onclick="${changeHandler}" checked="checked"/>
		</c:when>
		<c:otherwise>
		<form:radiobutton path="${path}" value="${value}" id="${id}" disabled="${disabled}"
		 cssClass="${cssClass}" onclick="${changeHandler}" />
		</c:otherwise>
		</c:choose>
	
		<spring:message code="${label}" text="${label}"/>
		
		
		</label> 
 </c:forEach> 
 
 
 
	</div>