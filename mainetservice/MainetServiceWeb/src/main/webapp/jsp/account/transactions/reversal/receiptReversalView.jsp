<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<form:form action="" modelAttribute="receiptViewData"
	class="form-horizontal" id="frmReeversalSearch">
	<div class="error-div alert alert-danger alert-dismissible"
		id="errorDivId" style="display: none;">
		<button type="button" class="close" onclick="closeOutErrBox()"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>

	<h4>
		<spring:message code="accounts.receipt.receipt_collection_details"
			text="Receipt Collection Details" />
	</h4>


	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message
				code="receipt.reversal.receiptno" text="Receipt No" /></label>
		<div class="col-sm-4">
			<form:input path="" class="form-control" disabled="true"
				value="${receiptViewData.receiptNo}" />
		</div>
		<label class="col-sm-2 control-label"><spring:message
				code="accounts.receipt.receipt_date" text="Receipt Date" /></label>
		<div class="col-sm-4">
			<form:input path="" class="form-control" disabled="true"
				value="${receiptViewData.receiptDate}" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message
				code="accounts.receipt.received_from" text="Received From" /></label>
		<div class="col-sm-4">
			<form:input path="" class="form-control" disabled="true"
				value="${receiptViewData.receivedFrom}" />
		</div>
		<label class="col-sm-2 control-label"><spring:message
				code="accounts.receipt.name" text="Payee Name" /></label>
		<div class="col-sm-4">
			<form:input path="" class="form-control" disabled="true"
				value="${receiptViewData.payeeName}" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message
				code="accounts.receipt.mobile_no" text="Mobile No." /></label>
		<div class="col-sm-4">
			<form:input path="" class="form-control" disabled="true"
				value="${receiptViewData.mobileNo}" />
		</div>
		<label class="col-sm-2 control-label"><spring:message
				code="accounts.receipt.email_id" text="Email Id" /></label>
		<div class="col-sm-4">
			<form:input path="" class="form-control" disabled="true"
				value="${receiptViewData.emailId}" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message
				code="accounts.receipt.manual_receipt_no" text="Manual Receipt No." /></label>
		<div class="col-sm-4">
			<form:input path="" class="form-control" disabled="true"
				value="${receiptViewData.manualReceiptNo}" />
		</div>
		<label class="col-sm-2 control-label"><spring:message
				code="accounts.receipt.narration" text="Narration" /></label>
		<div class="col-sm-4">
			<form:input path="" class="form-control" disabled="true"
				value="${receiptViewData.narration}" />
		</div>
	</div>

	<h4>
		<spring:message code="accounts.receipt.receipt_collection_details"
			text="Receipt Collection Details" />
	</h4>

	<c:set var="d" value="0" scope="page" />
	<div class="table-overflow-sm">
		<table class="table table-bordered table-striped" id="mappingDetails">
			<tbody>
				<tr>
					<th width="20%"><spring:message
							code="receipt.reversal.receipthead" text="Receipt Head" /></th>
					<th width="10%"><spring:message
							code="accounts.receipt.receipt_amount" text="Receipt Amount" /></th>
				</tr>
				<c:forEach items="${receiptViewData.collectionDetails}"
					var="detailInfo" varStatus="varIndex">
					<tr class="tableRowClass">
						<c:set var="index" value="${varIndex.index}" scope="page" />
						<td><form:input path="" value="${detailInfo.receiptHead}"
								cssClass="form-control" disabled="true" /></td>
						<td><form:input path="" value="${detailInfo.receiptAmount}"
								cssClass="form-control text-right" disabled="true" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<h4>
		<spring:message code="accounts.receipt.collection_mode_details"
			text="Collection Mode Details" />
	</h4>

	<c:set var="d" value="0" scope="page" />
	<div class="table-overflow-sm">
		<table class="table table-bordered table-striped" id="mappingDetails">
			<tbody>
				<tr>
					<th width="10%"><spring:message
							code="accounts.receipt.payment_mode" text="Mode" /></th>
					<c:if test="${receiptViewData.modeShortCode ne 'C'}">
						<th width="20%"><spring:message
								code="accounts.receipt.drawn_on" text="Bank Name" /></th>
						<th width="10%"><spring:message
								code="receipt.reversal.trn.number" text="TRN. No" /></th>
						<th width="10%"><spring:message
								code="receipt.reversal.trn.date" text="TRN. Date" /></th>
					</c:if>
					<th width="10%"><spring:message
							code="accounts.receipt.mode_amount" text="Total Amount" /></th>
				</tr>
				<tr class="tableRowClass">
					<td><form:input path="" value="${receiptViewData.mode}"
							cssClass="form-control" disabled="true" /></td>
					<c:if test="${receiptViewData.modeShortCode ne 'C'}">
						<td><form:input path="" value="${receiptViewData.bankName}"
								cssClass="form-control" disabled="true" /></td>
						<td><form:input path="" value="${receiptViewData.trnNo}"
								cssClass="form-control" disabled="true" /></td>
						<td><form:input path="" value="${receiptViewData.trnDate}"
								cssClass="form-control" disabled="true" /></td>
					</c:if>
					<td><form:input path="" value="${receiptViewData.totalAmount}"
							cssClass="form-control text-right" disabled="true" /></td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="text-center padding-top-10">
		<button type="button" class="btn btn-danger"
			onclick="window.location.href='AccountVoucherReversal.html?back'"
			id="backBtn">
			<spring:message code="account.bankmaster.back" text="Back" />
		</button>
	</div>

</form:form>
