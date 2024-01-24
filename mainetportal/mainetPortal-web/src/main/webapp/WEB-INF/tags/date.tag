<%@tag import="com.abm.mainet.common.util.ApplicationSession"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ attribute name="datePath" required="true" rtexprvalue="true" %>
<%@ attribute name="fieldclass" required="true" rtexprvalue="true" %>
<%@ attribute name="readonly" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<%@ attribute name="labelCode" required="false" rtexprvalue="false" %>
<%@ attribute name="isMandatory" required="false" rtexprvalue="true"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true" type="java.lang.String" description="Custome CSS class(es) to be apply for element." %>
<%@ attribute name="showDefault" required="false" rtexprvalue="true" type="java.lang.Boolean" description="To show default system date. default is false." %>
<%@ attribute name="tabIndex" required="false" rtexprvalue="true" type="java.lang.String" description="Tabindex"%>
<%@ attribute name="isDisabled" required="false" rtexprvalue="true" type="java.lang.Boolean"%>	

<%
	String maxLength	=	"19";
	String placeHolder	=	"dd/MM/yyyy hh:mm am/pm";

	boolean isReadOnly	= false;
			
			
			if((readonly != null && readonly.booleanValue()) || fieldclass.equals("date")){
				isReadOnly = true;
			}
		
		
	if(fieldclass.equals("datepicker") || fieldclass.equals("date") || fieldclass.equals("lessthancurrdate") || fieldclass.equals("fromDateClass") || fieldclass.equals("toDateClass"))
	{	
		maxLength	=	"10";
		placeHolder	=	ApplicationSession.getInstance().getMessage("date.placeholder");
	}	
%>
<jsp:useBean id="stringResolver" class="com.abm.mainet.common.util.StringUtility"/>
<c:set var="id" value="${stringResolver.replaceAllDotPrefix(datePath)}"/>
<c:set var="dateVal" value=""/>
<c:if test="${showDefault}">
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<c:set var="dateVal">
		<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" />
	</c:set>
</c:if>
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
		<div class="input-group">
			<form:input cssClass=" form-control ${fieldclass} ${cssClass} ${mandcolor}" 
			id="${id}" 
			path="${datePath}"
			value="${dateVal}"
			maxlength="<%=maxLength%>"
			readonly="<%=isReadOnly%>"
			disabled="false"		
			placeholder="<%=placeHolder%>"
			tabindex="${tabIndex}" 
			data-rule-required="${isMandatory}"/>
	      <label class="input-group-addon"><i class="fa fa-calendar"></i></label>
	    </div>
	</div>



