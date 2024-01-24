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




	<form:form method="POST" action="RTGSPaymentEntry.html"
		cssClass="form-horizontal" name="RTGSPaymentEntry"
		id="RTGSPaymentEntry" modelAttribute="RTGSPaymentEntryDto">
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
		
		<h4>
			<spring:message code="account.subRtgsPayment.paymentDetails"
				text="RTGS Payment Details" />
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
			
			<c:if test="${RTGSPaymentEntryDto.modeCode ne 'C' && RTGSPaymentEntryDto.modeCode ne 'A' && RTGSPaymentEntryDto.modeCode ne 'PCA'}">
			<c:set value="${RTGSPaymentEntryDto.bankAcId}" var="bankId"></c:set>
				<c:forEach items="${bankAccountMap}"
							varStatus="status" var="bankItem">	
					<c:if test="${bankId eq bankItem.key}">
						<form:input type="text" value="${bankItem.value}" path=""
									class="form-control" id="bankId" readonly="true" />
					</c:if>				
				</c:forEach>
			</c:if>
			
			<c:if test="${RTGSPaymentEntryDto.modeCode eq 'C' || RTGSPaymentEntryDto.modeCode eq 'A' || RTGSPaymentEntryDto.modeCode eq 'PCA'}">
				<form:input id="bankId" path=""
								 class="form-control" readonly="true" />
			</c:if>
			
			</div>
			
		</div>
		<div class="form-group">
			
			<c:if test="${RTGSPaymentEntryDto.modeCode eq 'B'}">
			<label for="" class="col-sm-2 control-label"><spring:message
					code="account.utr.no" text="UTR No." /></label>
			<div class="col-sm-4">
			
				<form:input id="utrNumber" path="utrNo"
					value="" class="form-control" readonly="true" />
			</div>
			</c:if>
			
			<c:if test="${RTGSPaymentEntryDto.modeCode ne 'B'}">
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
				<form:input id="totalAmount" path="sanctionedAmountStr"
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
   
   
         <h4 id="">
				<spring:message code="account.subRtgsPayment.fileUpload"
					text="File Upload Details" />
			</h4>
		  <div class="form-group">	
			<div class="col-sm-12 text-left">
							<div class="table-responsive">
								<table class="table table-bordered table-striped"
									id="attachDocs">
									<tr>
										<th><spring:message code="scheme.document.name" text="" /></th>
										<th><spring:message code="scheme.view.document" text="" /></th>
										<%-- <th><spring:message code="scheme.action" text=""></spring:message>
										</th> --%>
									</tr>
									<c:forEach items="${RTGSPaymentEntryDto.attachDocsList}"
										var="lookUp">
										<tr>
											<td>${lookUp.attFname}</td>
											<td><apptags:filedownload filename="${lookUp.attFname}"
													filePath="${lookUp.attPath}"
													actionUrl="RTGSPaymentEntry.html?Download" /></td>
											<%-- <td class="text-center"><a href='#' id="deleteFile"
												onclick="return false;" class="btn btn-danger btn-sm"><i
													class="fa fa-trash"></i></a> <form:hidden path=""
													value="${lookUp.attId}" /></td>
 --%>										</tr>
									</c:forEach>
								</table>
							</div>
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


