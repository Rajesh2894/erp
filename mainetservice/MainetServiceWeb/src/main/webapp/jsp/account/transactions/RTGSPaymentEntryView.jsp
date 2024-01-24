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
<script src="js/account/transaction/RTGSPaymentEntry.js"
	type="text/javascript"></script>


<div class="widget">
	<div class="widget-content" id="paymentEntryDiv">
		<form:form method="POST" action="RTGSPaymentEntry.html"
			cssClass="form-horizontal" name="RTGSPaymentEntry"
			id="RTGSPaymentEntry" modelAttribute="RTGSPaymentEntryDto">

			<form:hidden path="paymentTypeCode" id="paymentTypeCode" />
			<form:hidden path="paymentId" id="paymentId" />

			<div class="form-group">
				<label for="paymentNo" class="col-sm-2 control-label"><spring:message
						code="account.bill.payment.entry.number" text="Payment No" /></label>
				<div class="col-sm-4">
					<form:input type="text" path="paymentNo" class="form-control"
						name="paymentNo" enable-roles="true" role="1" id="paymentNo"
						readonly="true" />
				</div>
				<label for="paymentEntryDate" class="col-sm-2 control-label"><spring:message
						code="advance.management.master.paymentdate" text="Payment Date" /></label>
				<div class="col-sm-4">
					<form:input path="paymentEntryDate" cssClass="form-control"
						name="paymentEntryDate" id="paymentEntryDate" disabled="true" />

				</div>
			</div>

            <!-- commented below field against id #133285 -->
			<%-- <div class="form-group">
				<label for="billTypeId" class="col-sm-2 control-label "><spring:message
						code="" text="Bill Type" /> </label>
				<div class="col-sm-4">
					<form:input path="billTypeDesc" class="form-control"
						name="billTypeDesc" id="billTypeDesc" disabled="true" />
				</div>
			</div> --%>
			<div class="popUp">
				<!-- Budget details will appear here -->
			</div>
			<h4 id="">
				<spring:message code="account.direct.paymentOrderDetails" text="Payment Order Details" />
			</h4>
			<div class="table-responsive" id="billDetailTableDiv"
				style="overflow: visible;">
				<table id="billDetailTable"
					class="table table-bordered table-striped billDetailTableClass">
					<thead>
						<tr>
							<th scope="col" id="vendorId" width="30%"><spring:message
									code="accounts.vendormaster.vendorName" text="Vendor Name" /></th>
							<th scope="col" id="thBillNo" width="15%"><spring:message
									code="account.paymentEntry.billNumber" text="Bill Number" /></th>
							<th scope="col" id="thNetPayable" width="20%"><spring:message
									code="account.bill.net.payable" text="Net Payable" /></th>
							<th scope="col" id="thPayableAmount" width="20%"><spring:message
									code="accounts.receipt.payable.amt" text="Payable Amount" /></th>
							<th scope="col" id="thView" width="6%"><spring:message
									code="account.directTable.viewBill" text="View Bill" /></th>
						</tr>
					</thead>
					<tbody>

						<c:forEach items="${paymentDetailList}" var="billDetList"
							varStatus="status">
							<c:set value="${status.index}" var="count"></c:set>
							<tr class="billDetailClass">
								<td><form:input id=""
										path="rtgsPaymentDetailsDto[${count}].vendorDesc"
										disabled="true" class="form-control" /></td>
								<td><form:hidden path="rtgsPaymentDetailsDto[${count}].id"
										id="billId${count}" /> <form:input id=""
										path="rtgsPaymentDetailsDto[${count}].billNumber"
										disabled="true" class="form-control" /></td>
								<td><form:input id="netPayable${count}"
										aria-labelledby="thNetPayable"
										path="rtgsPaymentDetailsDto[${count}].netPayable"
										disabled="true" cssClass="form-control text-right" /></td>
								<td><form:input id="paymentAmount${count}"
										aria-labelledby="thPayableAmount"
										path="rtgsPaymentDetailsDto[${count}].paymentAmountDesc"
										onkeypress="return hasAmount(event, this, 13, 2)"
										disabled="true" cssClass="form-control text-right"
										data-rule-required="true" /></td>
								<td>
									<button type="button" title="View Budget"
										class="btn btn-primary btn-sm viewBill"
										onclick="viewRTGSBillDetails(${count})"
										id="viewBillDet${count}">
										<i class="fa fa-eye" aria-hidden="true"></i>
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<jsp:include page="/jsp/account/transactions/ViewBillRTGSPayment.jsp" />
		</form:form>
	</div>
</div>
