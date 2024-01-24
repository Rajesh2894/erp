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
<script src="js/account/transaction/RTGSPaymentEntry.js"
	type="text/javascript"></script>
<script src="js/mainet/file-upload.js"></script>
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
<h4 id="">
	<spring:message code="account.subRtgsPayment.paymentDetails" text="RTGS Payment Details" />
</h4>

<div class="form-group">
	<label for="" class="col-sm-2 control-label required-control"><spring:message
			code="account.cheque.cash.payment.mode" text="Payment Mode" /></label>
	<div class="col-sm-4">
		<form:select type="select" path="paymentMode"
			class="form-control mandColorClass" name="paymentMode"
			id="paymentMode" onclick="toggleInstruments()"
			onchange="checkPaymetCashBalanceExists()" data-rule-required="true">
			<option value=""><spring:message code="account.select"
					text="Select" /></option>
			<c:forEach items="${paymentMode}" varStatus="status" var="levelChild">
				<form:option value="${levelChild.lookUpId}"
					code="${levelChild.lookUpCode}">${levelChild.descLangFirst}</form:option>
			</c:forEach>
		</form:select>
	</div>
	<label class="col-sm-2 control-label required-control"><spring:message
			code="accounts.stop.payment.bank.account" text="Bank Account" /></label>
	<div class="col-sm-4">


		<form:select id="baAccountidPay" path="bankAcId"
			class="form-control chosen-select-no-results" disabled=""
			onchange="getChequeNos()" data-rule-required="true">
			<form:option value="">
				<spring:message code="account.common.select" />
			</form:option>
			<c:forEach items="${bankAccountMap}" varStatus="status"
				var="bankItem">
				<form:option value="${bankItem.key}">${bankItem.value}</form:option>
			</c:forEach>
		</form:select>
	</div>
</div>


<div class="form-group">

	<div id="instrumentNo">
		<label for="" class="col-sm-2 control-label required-control"><spring:message
				code="accounts.receipt.cheque_dd_no_pay_order" text="Instrument No." /></label>
		<div class="col-sm-4">
			<form:select class="form-control mandColorClass" id="chequebookDetid"
				path="instrumentNo" disabled="">
				<form:option value="">
					<spring:message code="account.common.select" />
				</form:option>
			</form:select>
		</div>
	</div>
	<div id="utrNo">
		<label for="" class="col-sm-2 control-label"><spring:message
				code="account.utr.no" text="UTR No." /></label>
		<div class="col-sm-4">
			<form:input id="utrNumber" path="utrNo"
				class="form-control hasSpecialCharAndNumber mandColorClass"
				maxLength="16" />
		</div>
	</div>

	<label for="" class="col-sm-2 control-label required-control"><spring:message
			code="accounts.receipt.cheque_dd_date" text="Instrument Date" /></label>
	<div class="col-sm-4">
		<div class="input-group">
			<c:set var="now" value="<%=new java.util.Date()%>" />
			<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date" />
			<form:input path="instrumentDate"
				class="form-control text-left mandColorClass" name="date-instrument"
				id="instrumentDate" onchange="validateChequeDate()" readonly="true"
				disabled="" value="${date}" />
			<label class="input-group-addon" for="instrumentDate"><i
				class="fa fa-calendar"></i><span class="hide"><spring:message
						code="account.additional.supplemental.auth.icon" text="icon" /></span> <input
				type="hidden" id="instDate"> </label>
		</div>
	</div>
</div>



<div class="form-group">
	<label for="" class="col-sm-2 control-label"><spring:message
			code="advance.management.master.paymentamount" text="Payment Amount" /></label>
	<div class="col-sm-4">
		<fmt:formatNumber type="number"
			value="${paymentEntryDto.totalPaymentAmount}" groupingUsed="false"
			var="famt" pattern="#,##,##,##,##0.00" maxIntegerDigits="15"
			maxFractionDigits="2" />
		<form:input id="totalAmount" path="totalPaymentAmount" value="${famt}"
			onkeypress="return hasAmount(event, this, 13, 2)"
			class="form-control text-right" readonly="true" />
		<form:hidden path="totalPaymentAmount" id="totalAmountHidden" />
	</div>

	<label for="checkerRemarks"
		class="col-sm-2 control-label required-control"><spring:message
			code="accounts.receipt.narration" text="Narration" /></label>
	<div class="col-sm-4">
		<form:textarea id="bmNarration" path="narration" maxLength="2000"
			disabled="" class="form-control" data-rule-required="true" />
	</div>
</div>

	<!-- file upload start -->
			<h4 id="">
				<spring:message code="account.subRtgsPayment.fileUpload"
					text="File Upload Details" />
			</h4>
			<div class="form-group" id="reload">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="account.subRtgsPayment.uploadDoc" text="Upload document" /></label>

					<div class="col-sm-3 text-left">
						<form:hidden path="attachments[0].documentName" />
						<apptags:formField fieldType="7"
							fieldPath="attachments[0].uploadedDocumentPath" currentCount="0"
							showFileNameHTMLId="true" folderName="0"
							fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
							maxFileCount="CHECK_LIST_MAX_COUNT"
							validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS">
						</apptags:formField>
					</div>
				</div>
				<div class="form-group text-left padding-10">
					<small class="text-blue-2"><spring:message code="account.fileupload.msg" text=""/></small>
				</div>
                             <!--  file upload end -->


<div class="text-center padding-10">
	<button type="button" class="btn btn-success btn-submit"
		onclick="savePaymentEntry(this)">
		<spring:message code="account.bankmaster.save" text="Save" />
	</button>
	<button type="button" class="btn btn-warning" id="createBtn">
		<spring:message code="account.bankmaster.reset" text="Reset" />
	</button>
	<input type="button" class="btn btn-danger"
		onclick="window.location.href='RTGSPaymentEntry.html'"
		value="<spring:message code="account.bankmaster.back" text="Back"/>" id="cancelEdit" />
</div>



</div>

