<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/signpathmaster/signpathmaster.js"></script>
<style>
.textarea-min{height:30px !important;}
</style>
<h1 class="form-elements padding_top_10"><spring:message code="" text="Report Details" /></h1>
<c:set var="editveiwflag" value="${tbSignPathDet.editFlag}"/>
<div id="childcontent">

	<c:url value="${saveAction}" var="action_url_form_submit" />
	<form:form action="${action_url_form_submit}" method="GET" name="" class="form" modelAttribute="tbSignPathDet">
	 
	 <!-- Hidden Data -->
		<form:hidden path="signId"  id="signId"/>
		<form:hidden path="addFlag"  id="addFlag"/>
		<form:hidden path="viewFlag"  id="viewFlag"/>
		<form:hidden path="editFlag" id="editflag"/>
		<form:hidden path="langId"  id="langId"/>
		<form:hidden path="orgid"  id="orgid"/>
		
		
				<table id="focustable" class="gridtable padding_10 clear">
				<tr>
				<th><spring:message code="common.master.report.name" text="Report Name"/></th>
				<th><spring:message code="common.master.report.name.regional" text="Report Name Regional"/></th>
				<th><spring:message code="common.master.report.desc" text="Report Description"/></th>
				
				<c:choose>
				<c:when test="${tbSignPathDet.prefixFlag eq 'Y'}">
				<th><spring:message code="common.master.prefix.name" text="Prefix Name"/></th>
				</c:when>
				<c:otherwise>
				<th><spring:message code="common.master.signature.path" text="Signature Path"/></th>
				</c:otherwise>
				</c:choose>
				
				<th><spring:message code="common.master.click.hierarchical" text="Click if Hierarchical"/></th>
				</tr>
				
				<tr>
				<td><form:textarea path="rdfname" class="input2 textarea-min mandClassColor"  /></td>
				<td><form:textarea path="rdfnameReg" class="input2 textarea-min mandClassColor"  /></td>
				<td><form:textarea path="reportdesc" class="input2 textarea-min mandClassColor"  /></td>
				<c:choose>
				<c:when test="${tbSignPathDet.prefixFlag eq 'Y'}">
				<td><form:textarea path="prefixName" class="input2 textarea-min mandClassColor" /></td>
				</c:when>
				<c:otherwise>
				<td><form:textarea path="signpath" class="input2 textarea-min mandClassColor" /></td>
				</c:otherwise>
				</c:choose>
				<c:choose>
				<c:when test="${tbSignPathDet.prefixFlag eq 'Y'}">
				<td><form:checkbox path="prefixFlag" id="prefixFlag" value="Y"  /></td>
				</c:when>
				<c:otherwise>
				<td><form:checkbox path="prefixFlag" id="prefixFlag" value="Y" onclick="returnWaidWise(this)"/></td>
				</c:otherwise>
				</c:choose>
				</tr>
				</table>
				
				<div class="form-elements padding_top_10">
				<jsp:include page="/jsp/masters/signpath/signpathwithoutword.jsp"></jsp:include>
				</div>
		
	</form:form>
	
	</div>
	
	