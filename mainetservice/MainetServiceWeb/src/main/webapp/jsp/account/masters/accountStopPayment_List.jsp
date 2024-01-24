<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/accountStopPayment.js"></script>
<script>
	$(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
	});
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="accounts.stop.payment.title" text="Stop Payment "></spring:message>
			</h2>
			<apptags:helpDoc url="AccountStopPayment.html"
				helpDocRefURL="AccountStopPayment.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">

			<form:form action="" modelAttribute="stopPayment"
				class="form-horizontal" id="tbChequeDishonour">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<form:hidden path="bankReconIds" id="bankReconIds" />
				<form:hidden path="clearanceDateIds" id="clearanceDateIds" />
				<form:hidden path="bankReconMap" id="bankReconMap" />
				<form:hidden path="successfulFlag" id="successfulFlag" />

				<div class="form-group">
					<label for="toDate" class="col-sm-2 control-label required-control"><spring:message
							code="from.date.label" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="formDate" cssClass="datepicker form-control"
								name="formDate" id="formDate" maxlength="10" />
							<label class="input-group-addon" for="formdate"><i
								class="fa fa-calendar"></i> <input type="hidden"
								id="trasaction-date-icon30"></label>
						</div>
					</div>

					<label for="toDate" class="col-sm-2 control-label required-control"><spring:message
							code="to.date.label" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="toDate" cssClass="datepicker form-control"
								name="toDate" id="toDate" maxlength="10" />
							<label class="input-group-addon" for="toDate"><i
								class="fa fa-calendar"></i> <input type="hidden"
								id="trasaction-date-icon30"></label>
						</div>
					</div>

				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="bank.account.label" text="Bank Account" /></label>
					<div class="col-sm-4">
						<form:select id="bankAccount" path="bankAccount"
							class="form-control chosen-select-no-results mandColorClass">
							<form:option value="">
								<spring:message code="" text="Select" />
							</form:option>
							<c:forEach items="${bankList}" varStatus="status"
								var="bankAccountMap">
								<form:option value="${bankAccountMap.key}"
									code="${bankAccountMap.key}">${bankAccountMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success searchData"
						onclick="searchBankReconciliationData(this)">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning"
						onclick="addBankReconcilationForm('AccountStopPayment.html','AddForm');">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
					<%-- Defect #153407 --%>
					<apptags:backButton url="AccountStopPayment.html" cssClass="btn btn-danger"></apptags:backButton>
				</div>
				<div class="panel-group accordion-toggle" id="ledgerDetailIds">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 type="h4" class="panel-title table" id="">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2">
									<spring:message code="accounts.stop.payment.details.title" text="Stop Payment Details" /></a>
							</h4>
						</div>

					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

