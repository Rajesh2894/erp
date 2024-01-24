<!DOCTYPE html>
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

<script>
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '0',
	});
	$(".datepicker").datepicker('setDate', new Date());
	var response = __doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET',
			{}, false, 'json');
	var disableBeforeDate = new Date(response[0], response[1], response[2]);
	var date = new Date();
	var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
	$("#transactionDateId").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : disableBeforeDate,
		maxDate : today
	});

</script>



	<form:form method="POST" action="DirectPaymentEntry.html"
		cssClass="form-horizontal" name="directPaymentEntry"
		id="directPaymentEntry" modelAttribute="paymentEntryDto">
		<div class="error-div alert alert-danger alert-dismissible"
			id="errorDivId" style="display: none;">
			<button type="button" class="close" onclick="closeOutErrBox()"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<span id="errorId"></span>
		</div>
		<jsp:include page="/jsp/tiles/validationerror.jsp" />

		<form:hidden path="billTypeId" id="billTypeId" />
		<form:hidden path="vendorId" id="vendorId" />
		<form:hidden path="vendorDesc" id="vendorDesc" />
		<form:hidden path="successfulFlag" id="successfulFlag" />
		<form:hidden path="" id="bankBalance" />
		<form:hidden path="modeCode" id="modeCode" />
		
		<h4 class="" id="">
			<spring:message code="direct.payment.entry.details"
				text="Direct Payment Details" />
		</h4>

		<div class="form-group">
			<label for="" class="col-sm-2 control-label"><spring:message
					code="account.cheque.cash.payment.mode" text="Payment Mode" /></label>
			<div class="col-sm-4">
				   <form:input id="modeDesc" path="modeDesc"
					value="" class="form-control" readonly="true" />
			</div>
			<label for="baAccountidPay"
				class="col-sm-2 control-label"><spring:message
					code="account.fund.bank.acc" text="Bank Account" /></label>
			<div class="col-sm-4">
			
			<c:if test="${paymentEntryDto.modeCode ne 'C' && paymentEntryDto.modeCode ne 'PCA' && paymentEntryDto.modeCode ne 'A'}">
			<c:set value="${paymentEntryDto.bankAcId}" var="bankId"></c:set>
				<c:forEach items="${bankAccountMap}"
							varStatus="status" var="bankItem">
					<c:if test="${bankId eq bankItem.key}">
						<form:input type="text" value="${bankItem.value}" path=""
									class="form-control" id="bankId" readonly="true" />
					</c:if>				
				</c:forEach>
			</c:if>
			
			<c:if test="${paymentEntryDto.modeCode eq 'C' || paymentEntryDto.modeCode eq 'PCA' || paymentEntryDto.modeCode eq 'A' }">		
				<form:input id="bankId" path=""
								 class="form-control" readonly="true" />
			</c:if>
			
			</div>
			
		</div>
		<div class="form-group">
		
			<c:if test="${paymentEntryDto.modeCode eq 'B'}">
			<label for="" class="col-sm-2 control-label"><spring:message
					code="account.utr.no" text="UTR No." /></label>
			<div class="col-sm-4">
			
				<form:input id="utrNumber" path="utrNo"
					value="" class="form-control" readonly="true" />
			</div>
			</c:if>
		
			<c:if test="${paymentEntryDto.modeCode ne 'B'}">
			<label for="" class="col-sm-2 control-label"><spring:message
					code="accounts.receipt.cheque_dd_no_pay_order" text="Instrument No." /></label>
			<div class="col-sm-4">
			
				<form:input id="instrumentNo" path="instrumentNo"
					value="" class="form-control" readonly="true" />
			</div>
			</c:if>
			
	         <label for="" class="col-sm-2 control-label"><spring:message
					code="accounts.receipt.cheque_dd_date" text="Instrument Date" /></label>
			
			<div class="col-sm-4">
				<form:input path="InsttDate" class="form-control"
				id="InsttDate" value="" readonly="true" />
			</div>
			
		</div>
		
		<div class="form-group">
			<label for="" class="col-sm-2 control-label"><spring:message
					code="advance.management.master.paymentamount" text="Payment Amount" /></label>
			<div class="col-sm-4">
				<form:input id="totalPaymentAmount" path="totalPaymentAmount"
					value="" class="form-control" readonly="true"  style="text-align:right"/>
			</div>
			
			<label for="" class="col-sm-2 control-label"><spring:message
					code="bill.narration" /></label>
			<div class="col-sm-4">
				<form:textarea path="narration" class="form-control mandColorClass"
					name="bmNarration" id="bmNarration" disabled=""
					data-rule-required="true"   readonly="true" />
			</div>

		</div>

<div class="text-center padding-10">
			<c:choose>
				<c:when test="${modeFlag eq 'V'}">
					<input type="button" class="btn btn-danger"
						onclick="window.location.href='AccountVoucherReversal.html?back'"
						value="Back" id="cancelEdit" />
				</c:when>
				<c:otherwise>
					<button type="button" class="btn btn-danger"
						name="button-1496152380275" style=""
						onclick="window.location.reload()" id="backBtn">
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</c:otherwise>
			</c:choose>

		</div>

	</form:form>




