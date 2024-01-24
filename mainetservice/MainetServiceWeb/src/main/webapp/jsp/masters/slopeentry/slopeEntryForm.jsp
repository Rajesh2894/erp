<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="/js/mainet/ui/jquery-ui.js"></script>
<script src="js/masters/slopeentry/slopeentry.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div id="heading_wrapper" class="content">
	<div class="widget">	
		<div class="error-div" style="display:none;" id="errorDivDeptMas"></div>
		<div class="widget-content padding">

	<form:form  method="post" name=""  class="form-horizontal">
			<div class="text-right padding-bottom-10">
					<a class="btn btn-success pullright addConnectionClass" href='${url_create}'><i class="fa fa-plus-circle"></i><spring:message code="contract.label.add" text="Add"/></a>
				</div>
			<div class="table">
				<table id="slopeEntryGrid"></table>
				<div id="pagered"></div>
			</div>

	</form:form>
	</div>
	</div>
	</div>

<script>



</script>
