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
<script src="js/account/bankReconciliation.js"></script>
<script>
	$(document).ready(function() {
		var table = $('.main1').DataTable({
			"oLanguage" : {
				"sSearch" : ""
			},
			"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true,
			"bPaginate" : true,
			"bFilter" : true,
			"ordering" : false,
			"order" : [ [ 1, "desc" ] ]
		});
	});
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="form-div margin-top-20" id="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="account.bank.reconciliation" text="Bank Reconciliation" /></h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span>Field with <i class="text-red-1">*</i> is mandatory
				</span>
			</div>
			<form:form action="" modelAttribute="tbBankReconciliation"
				class="form-horizontal" id="tbChequeDishonour">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<label for="" class="col-sm-2 control-label"><spring:message code="from.date.label" text="From Date" /><span
						class="mand">*</span></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="formDate" cssClass="datepicker form-control"
								id="fromDate" maxlength="10" />
							<label class="input-group-addon" for="toDate"><i
								class="fa fa-calendar"></i> <input type="hidden"
								id="trasaction-date-icon30"></label>
						</div>
					</div>
					<label for="" class="col-sm-2 control-label"><spring:message code="to.date.label" text="To Date" /><span
						class="mand">*</span></label>
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
					<label for="" class="col-sm-2 control-label"><spring:message code="bank.account.label" text="Bank Account" /><span
						class="mand">*</span>
					</label>
					<div class="col-sm-4">
						<form:select id="bankAccount" path="bankAccount"
							class="form-control chosen-select-no-results mandColorClass">
							<form:option value="">
								<spring:message code="account.common.select" text="Select" />
							</form:option>
							<c:forEach items="${bankList}" varStatus="status"
								var="bankAccountMap">
								<form:option value="${bankAccountMap.key}"
									code="${bankAccountMap.key}">${bankAccountMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="text-center col-sm-12 margin-top-10 margin-bottom-10">
					<button type="button" class="btn btn-success padding-top-5"
						title="Search" onclick="searchBankReconcilationData(this);">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning " title="Reset"
						onclick="resetReconcilation();">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
					<button type="button" class="btn btn-blue-2 " title="Add"
						onclick="addBankReconcilationForm('BankReconciliation.html','AddForm');">
						<i class="fa fa-plus-circle" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.add" text="Add" />
					</button>
					
				</div>
				<div class="table-responsive clear">
					<table class="table table-bordered table-condensed main1"
						id="main-table">
						<thead>
							<tr>
								<th class="text-center"><spring:message code="account.transaction.date" text="Transaction Date" /></th>
								<th class="text-center"><spring:message code="account.transaction.type" text="Transaction Type" /></th>
								<th class="text-center"><spring:message code="account.transaction.mode" text="Transaction Mode" /></th>
								<th class="text-center"><spring:message code="account.instrument.number" text="Instrument Number" /></th>
								<th class="text-center"><spring:message code="account.instrument.date" text="Instrument Date" /></th>
								<th class="text-center"><spring:message code="account.amount" text="Amount" /></th>
								<th class="text-center"><spring:message code="account.reconciliation.date" text="Reconciliation Date" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${tbBankReconciliation.bankReconciliationDTO}"
								var="rcltn" varStatus="status">
								<c:set value="${status.index}" var="count"></c:set>
								<tr>
									<td>${rcltn.transactionDate}</td>
									<td>${rcltn.serchType}</td>
									<td>${rcltn.transactionMode}</td>
									<td>${rcltn.chequeddno}</td>
									<td>${rcltn.chequedddate}</td>
									<td class="text-right">${rcltn.amount}</td>
									<td>${rcltn.date}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>