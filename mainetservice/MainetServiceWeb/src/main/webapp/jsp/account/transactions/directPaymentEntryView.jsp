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
<script src="js/account/transaction/directPaymentEntry.js"
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

/* var vendorDesc = $("#bmNarration").val();
var arr = vendorDesc.split('$');

$("#rm_Receivedfrom").val(arr[0]);
$("#bmNarration").val(arr[1]); */

</script>

<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="" text="Direct Payment Entry" />
		</h2>
	</div>
	<div class="widget-content padding">
		<form:form action="DirectPaymentEntry.html" method="post"
			class="form-horizontal" id="directPaymentEntry"
			name="directPaymentEntry" novalidate="novalidate"
			modelAttribute="paymentEntryDto">
			<div class="form-group">
				<label for="paymentNo" class="col-sm-2 control-label"><spring:message
						code="accounts.payment.no" text="Payment No" /></label>
				<div class="col-sm-4">
					<form:input type="text" path="paymentNo" class="form-control"
						name="paymentNo" enable-roles="true" role="1" id="paymentNo"
						readonly="true" />
				</div>


				<label for="paymentEntryDate" class="col-sm-2 control-label"><spring:message
						code="accounts.payment.entry.date" text="Payment entry date" /></label>
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

                 <!--  commented against id :#133175 -->
				<%-- <label for="rm_Receivedfrom" class="control-label  col-sm-2 ">
					<spring:message code="accounts.receipt.name"></spring:message>
				</label>
				<div class="col-sm-4">
					<form:input id="rm_Receivedfrom" path="payeeName"
						class="form-control" disabled="true" />
				</div> --%>
					<label for="billTypeId" class="col-sm-2 control-label "><spring:message
						code="direct.payment.entry.paymenttype" text="Payment Type" /> </label>
				<div class="col-sm-4">
					<form:input path="billTypeDesc" class="form-control"
						name="billTypeDesc" id="billTypeDesc" disabled="true" />
				</div>

			</div>

			<div class="form-group">
				<label for="billRefNo" class="control-label  col-sm-2 "> <spring:message
						code="direct.payment.entry.bill.ref.no" text="Bill Ref No."></spring:message>
				</label>
				<div class="col-sm-4">
					<form:input id="billRefNo" path="billRefNo" class="form-control"
						disabled="true" />
				</div>
				
				<label for="Field" class="col-sm-2 control-label "><spring:message
						code="budget.estimate.sheet.field" text="Field" /></label>
				<div class="col-sm-4">
					<form:select path="fieldId" class="form-control mandColorClass"
						name="field" id="fieldId" disable="true" readonly="true">
						<option value=""><spring:message
								code="account.common.select" /></option>
						<c:forEach items="${listOfTbAcFieldMasterItems}"
							varStatus="status" var="fieldItem">
							<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
						</c:forEach>
					</form:select>
				</div>
				
			</div>
			<%-- <div class="form-group">
				<label for="Field" class="col-sm-2 control-label "><spring:message
						code="" text="Field" /></label>
				<div class="col-sm-4">
					<form:select path="fieldId" class="form-control mandColorClass"
						name="field" id="fieldId" disable="true" readonly="true">
						<option value=""><spring:message
								code="account.common.select" /></option>
						<c:forEach items="${listOfTbAcFieldMasterItems}"
							varStatus="status" var="fieldItem">
							<form:option value="${fieldItem.key}" code="${fieldItem.key}">${fieldItem.value}</form:option>
						</c:forEach>
					</form:select>
				</div>
			</div> --%>
			<div class="popUp">
				<!-- Budget details will appear here -->
			</div>
			<h4 class="">
				<spring:message code="direct.payment.entry.acc.head.detail"
					text="Account Head Details" />
			</h4>
			<div id="table-responsive1">
				<table id="budgetDetailsTable"
					class="table text-left table-striped table-bordered">
					<tbody>
						<tr>
							<th scope="col" width="75%"><spring:message
									code="account.deposit.accountHead" text="Account Head" /></th>
							<th scope="col" width="20%"><spring:message
									code="direct.payment.entry.pay.amount" text="Pay Amount" /></th>
							<th scope="col" width="5%"><spring:message text="Budget" code="budget.reappropriation.master.budget" /></th>
						</tr>
						<c:forEach items="${paymentDetailsList}" var="prExpList"
							varStatus="status">
							<c:set value="${status.index}" var="count"></c:set>
							<tr class="budgetDetailClass">
								<td><c:set value="${prExpList.id}" var="budgetCodeId" /> <c:forEach
										items="${expBudgetCodeMap}" varStatus="status" var="expItem">
										<c:if test="${expItem.key eq budgetCodeId}">
											<form:hidden value="${expItem.key}" path=""
												id="budgetCodeId${count}" />
											<form:input path="" value="${expItem.value}"
												class="form-control" disabled="true" />
										</c:if>
									</c:forEach></td>
								<td><form:input type="text" class="form-control text-right"
										path="paymentDetailsDto[${count}].paymentAmount"
										disabled="true" id="paymentAmount${count}" /></td>
								<td>
									<button type="button" title="View Budget"
										class="btn btn-primary btn-sm viewExp"
										onclick="viewBillDetails(${count})" id="viewExpDet${count}"
										name="">
										<i class="fa fa-eye" aria-hidden="true"></i>
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<jsp:include
				page="/jsp/account/transactions/directSubPaymentEntry.jsp" />
		</form:form>
	</div>

</div>