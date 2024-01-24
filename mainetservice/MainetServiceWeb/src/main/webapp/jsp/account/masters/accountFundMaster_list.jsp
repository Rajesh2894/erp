<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/menu/jquery.dynatree.js"></script>
<link href="css/menu/ui.dynatree.css" rel="stylesheet" type="text/css">

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="js/account/accountfundMaster.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="fund.master.widget.header" text=""></spring:message>
			</h2>
		<apptags:helpDoc url="AccountFundMaster.html" helpDocRefURL="AccountFundMaster.html"></apptags:helpDoc>		
		</div>
		<div class="widget-content padding">
			<form:form action="" modelAttribute="designation" class="form">

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
						<a class="btn btn-blue-2 addFundMasterClass" href='${url_create}'><i
							class="fa fa-plus-circle"></i> <spring:message code="account.bankmaster.add"
								text="Add" /></a>
					</div>
				</c:if>

				<c:if test="${defaultOrgFlagStatus == 'Y'}">
					<div class="text-center padding-bottom-10">
						<a class="btn btn-blue-2 addFundMasterClass" href='${url_create}'><i
							class="fa fa-plus-circle"></i> <spring:message code="account.bankmaster.add"
								text="Add" /></a>
					</div>
				</c:if>

				<table id="grid"></table>
				<div id="pagered"></div>
			</form:form>
		</div>
	</div>
</div>

