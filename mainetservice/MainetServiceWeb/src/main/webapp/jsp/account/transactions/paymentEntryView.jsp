<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<style>
.popUp {
	width: 350px;
	position: absolute;
	z-index: 111;
	display: block;
	top: 275px;
	left: 400px;
	border: 5px solid #ddd;
	border-radius: 5px;
	display: none;
}

.popUp table th {
	padding: 3px !important;
	font-size: 12px;
	background: none !important;
}

.popUp table td {
	padding: 3px !important;
	font-size: 12px;
}
</style>

<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/account/transaction/paymentEntry.js"
	type="text/javascript"></script>
<div class="widget">
	<div class="widget-content padding" id="paymentEntryDiv">
		<form:form method="POST" action="PaymentEntry.html"
			cssClass="form-horizontal" name="paymentEntry" id="paymentEntry"
			modelAttribute="paymentEntryDto">
			
			<form:hidden path="paymentTypeCode" id="paymentTypeCode"/>
			
			<div class="form-group">
				<label for="paymentNo" class="col-sm-2 control-label"><spring:message
						code="accounts.stop.payment.payment.number" text="Payment No." /></label>
				<div class="col-sm-4">
					<form:input type="text" path="paymentNo" class="form-control"
						name="paymentNo" enable-roles="true" role="1" id="paymentNo"
						readonly="true" />
				</div>
				<label for="paymentEntryDate" class="col-sm-2 control-label"><spring:message
						code="advance.management.master.paymentdate" text="Payment Date" /></label>
				<div class="col-sm-4">
					<form:input path="paymentEntryDate"
						cssClass="form-control" name="paymentEntryDate"
						id="paymentEntryDate" disabled="true" />

				</div>
			</div>

			<div class="form-group">
				<label for="billTypeId" class="col-sm-2 control-label "><spring:message
						code="bill.type" text="Bill Type" /> </label>
				<div class="col-sm-4">
					<form:input path="billTypeDesc" class="form-control"
						name="billTypeDesc" id="billTypeDesc" disabled="true" />
				</div>
				<label for="vendorId" class="col-sm-2 control-label"><spring:message
						code="account.tenderentrydetails.VendorEntry" text="Vendor Name" /></label>
				<div class="col-sm-4">
					<form:input path="vendorDesc" value="" class="form-control"
						name="vendorDesc" id="vendorDesc" disabled="true" />
				</div>
			</div>
			
			<div class="form-group">
				<label for="paymentTypeDesc" class="col-sm-2 control-label "><spring:message
						code="direct.payment.entry.paymenttype" text="Payment Type" /> </label>
				<div class="col-sm-4">
					<form:input path="paymentTypeDesc" class="form-control"
						name="paymentTypeDesc" id="paymentTypeDesc" disabled="true"/>
				</div>
				<label for="Field" class="col-sm-2 control-label "><spring:message
						code="budget.estimate.sheet.field" text="Field" /></label>
				<div class="col-sm-4">
					<form:select path="fieldId"
						class="form-control mandColorClass"
						name="field" id="fieldId" disabled="true" readonly="false">
						<option value=""><spring:message
								code="account.common.select" /></option>
						<c:forEach items="${listOfTbAcFieldMasterItems}"
							varStatus="status" var="fieldItem">
							<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div>
			
			<div class="popUp">
				<!-- Budget details will appear here -->
			</div>
			<h4 id="">
				<spring:message code="payment.entry.invoice.bill.detail"
					text="Invoice / Bill Details" />
			</h4>
			<div class="table-responsive" id="billDetailTableDiv">
				<table id="billDetailTable"
					class="table table-bordered table-striped billDetailTableClass">
					<thead>
						<tr>									
							<th scope="col" id="thBillNo" width="10%"><spring:message code="account.paymentEntry.billNumber"
									text="Bill Number" /></th>
							<th scope="col" id="bchId" width="25%"><spring:message code="account.paymentEntry.expenditureHead"
									text="Expenditure Head" /><span class="mand"></span></th>
							<th scope="col" id="thBillAmount" width="12%"><spring:message code="Bill.Amount"
									text="Bill Amount" /></th>
							<th scope="col" id="thDedcutions" width="12%"><spring:message code="account.paymentEntry.totalDeduction"
									text="Total Deductions" /></th>
							<th scope="col" id="thNetPayable" width="12%"><spring:message code="account.bill.net.payable"
									text="Net Payable" /></th>
							<th scope="col" id="thPayableAmount" width="12%"><spring:message code="accounts.receipt.payable.amt"
									text="Payable Amount" /></th>
							<th scope="col" id="thView" width="5%"><spring:message code="bill.budget"
									text="Budget" /></th>
						</tr>
					</thead>
					<tbody>
					
						<c:forEach items="${paymentDetailList}" var="billDetList"
							varStatus="status">
							<c:set value="${status.index}" var="count"></c:set>
							<tr class="billDetailClass">
								<td><form:hidden path="paymentDetailsDto[${count}].id"
										id="billId${count}" /> <form:input id=""
										path="paymentDetailsDto[${count}].billNumber" disabled="true"
										class="form-control" /></td>
								<td><form:hidden path="paymentDetailsDto[${count}].bchId"
										id="bchId${count}" /> <form:input id=""
										path="paymentDetailsDto[${count}].accountCode" disabled="true"
										class="form-control" /></td>
								<td><form:input id="amount${count}"
										aria-labelledby="thBillAmount"
										path="paymentDetailsDto[${count}].amount" disabled="true"
										cssClass="form-control text-right" /></td>
								<td><form:input id="deductions${count}"
										aria-labelledby="thDedcutions"
										path="paymentDetailsDto[${count}].deductions" disabled="true"
										cssClass="form-control text-right" /></td>
								<td><form:input id="netPayable${count}"
										aria-labelledby="thNetPayable"
										path="paymentDetailsDto[${count}].netPayable" disabled="true"
										cssClass="form-control text-right" /></td>
								<td><form:input id="paymentAmount${count}"
										aria-labelledby="thPayableAmount"
										path="paymentDetailsDto[${count}].paymentAmountDesc"
										onkeypress="return hasAmount(event, this, 13, 2)"
										disabled="true" cssClass="form-control text-right"
										data-rule-required="true" /></td>
								<td>
									<button type="button" title="View Budget"
										class="btn btn-primary btn-sm viewBill"
										onclick="viewPaymentBillDetails(${count})"
										id="viewBillDet${count}">
										<i class="fa fa-eye" aria-hidden="true"></i>
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<jsp:include page="/jsp/account/transactions/ViewBillPayment.jsp" />	
			</form:form>
			</div>
	</div>