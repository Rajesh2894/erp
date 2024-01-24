<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<form:form action="" modelAttribute="depositViewData"
	class="form-horizontal" id="frmReeversalSearch">
	<div class="error-div alert alert-danger alert-dismissible"
		id="errorDivId" style="display: none;">
		<button type="button" class="close" onclick="closeOutErrBox()"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>

	<h4>
		<spring:message code="deposite.slip.detail"
			text="Deposit Slip Details" />
	</h4>


	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message
				code="deposite.slip.number" text="Slip Number" /></label>
		<div class="col-sm-4">
			<form:input path="" class="form-control" disabled="true" value="" />
		</div>
		<label class="col-sm-2 control-label"><spring:message
				code="deposite.slip.date" text="Deposit Slip Date" /></label>
		<div class="col-sm-4">
			<form:input path="" class="form-control" disabled="true" value="" />
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message
				code="account.cheque.dishonour.deposit.mode" text="Deposit Mode" /></label>
		<div class="col-sm-4">
			<form:input path="" class="form-control" disabled="true" value="" />
		</div>
		<label class="col-sm-2 control-label"><spring:message
				code="account.fund.bank.acc" text="Bank Account" /></label>
		<div class="col-sm-4">
			<form:input path="" class="form-control" disabled="true" value="" />
		</div>
	</div>
	<c:set var="d" value="0" scope="page" />
	<div class="table-overflow-sm">
		<table class="table table-bordered table-striped" id="mappingDetails">
			<tbody>
				<tr>
					<th width="20%"><spring:message code="account.srno"
							text="Sr. No." /></th>
					<th width="10%"><spring:message
							code="accounts.receipt.receipt_no" text="Receipt Number" /></th>
					<th width="10%"><spring:message
							code="accounts.receipt.receipt_date" text="Receipt Date" /></th>
					<th width="10%"><spring:message
							code="account.tenderentrydetails.Department" text="Department" />
					</th>
					<th width="10%"><spring:message
							code="account.cheque.dishonour.instrument.type"
							text="Instrument Type" /></th>
					<th width="10%"><spring:message
							code="accounts.receipt.cheque_dd_no_pay_order"
							text="Instrument No." /></th>
					<th width="10%"><spring:message
							code="accounts.receipt.cheque_dd_date" text="Instrument Date" />
					</th>
					<th width="10%"><spring:message
							code="account.cheque.dishonour.drawn.bank" text="Drawn On Bank" /></th>
					<th width="10%"><spring:message
							code="budget.allocation.master.amount" text="Amount" /></th>
				</tr>
				<c:forEach items="${receiptViewData.collectionDetails}"
					var="detailInfo" varStatus="varIndex">
					<tr class="tableRowClass">
						<c:set var="index" value="${varIndex.index}" scope="page" />
						<td><form:input path="" value="${index+1}"
								cssClass="form-control" disabled="true" /></td>
						<td><form:input path="" value=""
								cssClass="form-control text-right" disabled="true" /></td>
						<td><form:input path="" value=""
								cssClass="form-control text-right" disabled="true" /></td>
						<td><form:input path="" value=""
								cssClass="form-control text-right" disabled="true" /></td>
						<td><form:input path="" value=""
								cssClass="form-control text-right" disabled="true" /></td>
						<td><form:input path="" value=""
								cssClass="form-control text-right" disabled="true" /></td>
						<td><form:input path="" value=""
								cssClass="form-control text-right" disabled="true" /></td>
						<td><form:input path="" value=""
								cssClass="form-control text-right" disabled="true" /></td>
						<td><form:input path="" value=""
								cssClass="form-control text-right" disabled="true" /></td>
					</tr>
				</c:forEach>
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
