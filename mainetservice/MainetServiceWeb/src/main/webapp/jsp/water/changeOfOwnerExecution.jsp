<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- <script type="text/javascript" src="js/water/water.js"></script> -->
<script type="text/javascript" src="js/water/changeOfOwnerExecution.js"></script>
<!-- <div class="content-page"> -->
<script type="text/javascript">
	$(document).ready(function() {
		var applicationDate = $("#applicationDate").val();
		$(".datepicker").datepicker("option", "minDate", applicationDate);
	});
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="water.ChangeofOwnerExecution" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<!--  <div class="mand-label clearfix"><span>Field with <i class="text-red-1">*</i> is mandatory</span></div> -->
			<form:form action="ExecuteChangeOfOwner.html" method="POST"
				class="form-horizontal">
				<h4 class="margin-top-0">
					<spring:message code="water.form.appdetails" />
				</h4>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="water.service.name" /></label>
					<div class="col-sm-4">
						<form:input path="" class="form-control" disabled="true"
							value="${command.commonDto.serviceName}" />

						<%-- <input type="text" value="${command.commonDto.serviceName}" class="form-control" disabled="disabled"> --%>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="water.application.number" /></label>
					<div class="col-sm-4">
						<form:input path="commonDto.serviceName" class="form-control"
							disabled="true" value="${command.commonDto.apmApplicationId}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="water.applicnt.name" /></label>
					<div class="col-sm-4">
						<form:input path="commonDto.serviceName" class="form-control"
							disabled="true" value="${command.commonDto.applicantFullName}" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="water.application.date" /></label>
					<div class="col-sm-4">
						<form:input path="commonDto.serviceName" class="form-control"
							disabled="true" value="${command.commonDto.applicationDate}"
							id="applicationDate" />
					</div>
				</div>
				<h4>
					<spring:message code="water.reconnection.executionDetail" />
				</h4>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="water.apprvdBy" /></label>
					<div class="col-sm-4">
						<form:input path="commonDto.approvedBy" class="form-control"
							readonly="true" />
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="water.apprvdDate" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="commonDto.approvedDate" class="form-control"
								readonly="true" />
							<!--  <label class="input-group-addon" for="datepicker2"><i class="fa fa-calendar"></i></label> -->
						</div>
					</div>
				</div>
				<div class="text-center">
					<button type="button" class="btn btn-success"
						id="executionOwnerSaveBtn">
						<spring:message code="water.Approve" />
					</button>
					<!-- <input type="button" class="btn btn-default" onclick="window.location.href='ChangeOfOwnership.html'" value=<spring:message code="rstBtn"/> /> -->
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="water.btn.back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
