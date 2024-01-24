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
<script src="js/menu/jquery.dynatree.js"></script>
<script src="js/account/functionalMaster.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>

<div id="heading_wrapper" class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="acc.master.funMaster" />
			</h2>
			<apptags:helpDoc url="AccountFunctionMaster.html"
				helpDocRefURL="AccountFunctionMaster.html"></apptags:helpDoc>
		</div>
		<div class="error-div" style="display: none;" id="errorDivDeptMas"></div>
		<div class="widget-content padding">

			<form:form method="post" name="" modelAttribute="tbAcFunctionMaster"
				class="form-horizontal">

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<c:if test="${defaultOrgFlagParentStatus == 'Y'}">
					<div class="text-center padding-bottom-10">
						<a class="btn btn-blue-2 addFunctionMasterClass" href=''><i
							class="fa fa-plus-circle"></i> <spring:message
								code="account.bankmaster.add" text="Add" /></a>
					</div>
				</c:if>

				<c:if test="${defaultOrgFlagStatus == 'Y'}">
					<div class="text-center padding-bottom-10">
						<a class="btn btn-blue-2 addFunctionMasterClass" href=''><i
							class="fa fa-plus-circle"></i> <spring:message
								code="account.bankmaster.add" text="Add" /></a>
					</div>
				</c:if>

				<div class="table">
					<table id="functionMasterGrid"></table>
					<div id="pagered"></div>
				</div>

			</form:form>
		</div>
	</div>
</div>

<script>
	
</script>
