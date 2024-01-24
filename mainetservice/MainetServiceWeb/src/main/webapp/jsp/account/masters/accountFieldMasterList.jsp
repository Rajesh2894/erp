<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script src="js/account/fieldMaster.js"></script>
<script src="js/menu/jquery.dynatree.js"></script>
<link href="css/menu/ui.dynatree.css" rel="stylesheet" type="text/css">

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="field.master.widget.header"
					text="Field Master" />
			</h2>
		<apptags:helpDoc url="AccountFieldMaster.html" helpDocRefURL="AccountFieldMaster.html"></apptags:helpDoc>	
		</div>
		<div class="widget-content padding">
			<form:form action="" modelAttribute="tbAcFieldMaster" class="form">

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
						<button type="button" class="btn btn-blue-2 createData">
							<i class="fa fa-plus-circle"></i>
							<spring:message code="account.bankmaster.add" text="Add" />
						</button>
					</div>
				</c:if>

				<c:if test="${defaultOrgFlagStatus == 'Y'}">
					<div class="text-center padding-bottom-10">
						<button type="button" class="btn btn-blue-2 createData">
							<i class="fa fa-plus-circle"></i>
							<spring:message code="account.bankmaster.add" text="Add" />
						</button>
					</div>
				</c:if>

				<table id="grid"></table>
				<div id="pagered"></div>

			</form:form>
		</div>
	</div>

</div>