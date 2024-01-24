<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>


<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/account/transaction/tdsPaymentEntry.js"
	type="text/javascript"></script>

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

<script>
	var vendorDesc = $("#bmNarration").val();
	var arr = vendorDesc.split('$');

	$("#rm_Receivedfrom").val(arr[0]);
	$("#bmNarration").val(arr[1]);
</script>

<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="tds.payment.entry" text="TDS Payment Entry" />
		</h2>
	</div>
	<div class="widget-content padding">
		<form:form method="POST" action="TdsPaymentEntry.html"
			cssClass="form-horizontal" name="paymentEntry"
			id="tdsPaymentEntryFrm" modelAttribute="tdsPaymentEntryDto">

			<div class="form-group">
				<label for="paymentNo" class="col-sm-2 control-label"><spring:message
						code="account.bill.payment.entry.number" text="Payment No" /></label>
				<div class="col-sm-4">
					<form:input type="text" path="paymentNo" class="form-control"
						name="paymentNo" enable-roles="true" role="1" id="paymentNo"
						readonly="true" />
				</div>


				<label for="paymentEntryDate" class="col-sm-2 control-label"><spring:message
						code="accounts.payment.entry.date" text="Payment Entry Date" /></label>

				<div class="col-sm-4">
					<form:input path="paymentEntryDate" cssClass="form-control"
						name="paymentEntryDate" id="paymentEntryDate" disabled="true" />

				</div>
			</div>
			<div class="form-group">
				<label for="vendorId" class="col-sm-2 control-label"><spring:message
						code="accounts.vendormaster.vendorName" text="Vendor Name" /></label>
				<div class="col-sm-4">
					<form:input path="vendorDesc" value="" class="form-control"
						name="vendorDesc" id="vendorDesc" disabled="true" />
				</div>

			<%-- 	<label for="billTypeId" class="col-sm-2 control-label "><spring:message
						code="" text="Payment Type" /> </label>
				<div class="col-sm-4">
					<form:input path="billTypeDesc" class="form-control"
						name="billTypeDesc" id="billTypeDesc" disabled="true" />
				</div> --%>

			</div>
			<div class="popUp">
				<!-- Budget details will appear here -->
			</div>
			<h4 class="">
				<spring:message code="account.tds.details" text="TDS Details" />
			</h4>

			<div id="table-responsive1">

				<table id="budgetDetailsTable"
					class="table text-left table-striped table-bordered">
					<tbody>
						<tr>
							<th width="15%"><spring:message code="opening.balance.report.srno" text="Sr. No." /></th>
							<th width="20%"><spring:message code="accounts.vendormaster.vendorName" text="Vendor Name" /></th>
							<th width="25%"><spring:message code="account.bill.no.validation" text="Bill No" /></th>
							<th width="15%"><spring:message code="bill.date" text="Bill Date" /></th>
							<th width="25%"><spring:message code="accounts.deduction.register.tds.amount" text="TDS Amount" /></th>
						</tr>
						<c:forEach items="${paymentDetailsList}" var="prExpList"
							varStatus="status">
							<c:set value="${status.index}" var="count"></c:set>
							<tr class="budgetDetailClass">
								<td><form:input path="" id="" class=" form-control "
										disabled="true" name="text-01" value="${count+1}" /></td>
								<td><form:input type="text" class="form-control"
										path="paymentDetailsDto[${count}].vendorName" disabled="true"
										id="vendorName${count}" /></td>

								<td><form:input type="text" class="form-control"
										path="paymentDetailsDto[${count}].billNumber" disabled="true"
										id="billNumber${count}" /></td>

								<td><form:input type="text" class="form-control text-right"
										path="paymentDetailsDto[${count}].billDate" disabled="true"
										id="billDate${count}" /></td>

								<td><form:input type="text" class="form-control text-right"
										path="paymentDetailsDto[${count}].paymentAmount"
										disabled="true" id="paymentAmount${count}" /></td>
							</tr>
						</c:forEach>
						<tr>
							<th colspan="4" class="text-right"><spring:message code="accounts.outstanding.bill.register.total" text="Total" /></th>
							<td><form:input type="text" class="form-control text-right"
									path="totalPaymentAmount" disabled="true"
									id="totalPaymentAmount" /></td>
						</tr>
					</tbody>
				</table>
			</div>

		</form:form>
		<jsp:include page="/jsp/account/transactions/tdsSubEntryReport.jsp" />
	</div>

</div>