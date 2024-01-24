<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="js/account/transaction/chequeCancellation.js"
	type="text/javascript"></script>

<script>
$(function() {
	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		maxDate: '0',
		
	});
	
	$(".datepicker").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });
	
});
</script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="cheque.book.utilization.cancellation.reissue"
					text="Cheque Cancellation and Re issue Entry" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="" method="get" class="form-horizontal"
				id="chequeCancellationForm" name="chequeCancellationForm"
				modelAttribute="chequeCancellationDto" novalidate="novalidate">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="successfulFlag" id="successfulFlag" />
				<div class="form-group">
					<label for="bankAccountId"
						class="col-sm-2 control-label required-control"><spring:message
							code="accounts.stop.payment.bank.account" text="Bank Account"></spring:message></label>
					<div class="col-sm-4">
						<form:select path="bankId" required="required"
							onchange="getChequeNos()"
							class="form-control mandColorClass chosen-select-no-results"
							name="" id="bankAccountId" data-rule-required="true">
							<form:option value="">
								<spring:message code="account.common.select" />
							</form:option>
							<c:forEach items="${bankAccountMap}" varStatus="status"
								var="bankItem">
								<form:option value="${bankItem.key}">${bankItem.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label for="issuedChequeNo"
						class="col-sm-2 control-label required-control"><spring:message
							code="accounts.receipt.cheque_dd_no_pay_order" text="Instrument No"></spring:message></label>
					<div class="col-sm-4">
						<form:select type="select" path="oldChequeBookDetId"
							required="required"
							class="form-control mandColorClass chosen-select-no-results"
							name="" id="issuedChequeNo" data-rule-required="true">
							<form:option value="">
								<spring:message code="account.common.select" />
							</form:option>
						</form:select>
					</div>
				</div>

				<div class="text-center clear padding-10">
					<button type="button" class="button-input btn btn-success"
						name="searchButton" style="" onclick="getNotIssuedChequeNos()"
						id="searchButton">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
				</div>
				<h4 class="" id="">
					<spring:message code="cheque.book.utilization.transaction.detail"
						text="Transaction Details" />
				</h4>
				<div class="form-group">
					<label for="bankAccount" class="col-sm-2 control-label"><spring:message
							code="accounts.stop.payment.bank.account" text="Bank Account"></spring:message></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control" name=""
							enable-roles="true" role="1" id="bankAccount" disabled="true"
							path=""></form:input>
					</div>
					<label for="existingInstrumentNo" class="col-sm-2 control-label"><spring:message
							code="account.cancel.existingInstruNo" text="Existing Instrument Number"></spring:message></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control" name=""
							enable-roles="true" role="1" id="existingInstrumentNo"
							disabled="true" path=""></form:input>
					</div>
				</div>
				<div class="form-group">
					<label for="cancellationDate"
						class="col-sm-2 control-label required-control"><spring:message
							code="advertiser.cancellation.date" text="Cancellation Date"></spring:message></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="cancellationDate"
								class="datepicker mandColorClass form-control" name=""
								id="cancellationDate" maxlength="10" data-rule-required="true" onchange="checkingBackDatedEntryDate(this)"/>
							<label class="input-group-addon" for="cancellationDate"><i
								class="fa fa-calendar"></i> <input type="hidden"
								id="invoicedate"> </label>
						</div>
					</div>
					<label for="notIssuedChequeNo"
						class="col-sm-2 control-label required-control"><spring:message
							code="account.cancel.newInsNo" text="New Instrument No"></spring:message></label>
					<div class="col-sm-4">
						<form:select path="newChequeBookDetId"
							class="form-control mandColorClass chosen-select-no-results"
							name="" id="notIssuedChequeNo" data-rule-required="true">
							<form:option value="">
								<spring:message code="account.common.select" />
							</form:option>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label for="cancellationReason"
						class="col-sm-2 control-label required-control"><spring:message
							code="account.cancel.reasonCancel" text="Reson for cancellation"></spring:message></label>
					<div class="col-sm-4">
						<form:textarea type="textarea" path="cancellationReason"
							required="required" class="form-control mandColorClass" name=""
							id="cancellationReason" data-rule-required="true"></form:textarea>
					</div>
				</div>
				<div class="text-center padding-10">
					<button type="button" class="btn btn-success btn-submit"
						onclick="saveChequeCancellation(this)">
						<spring:message code="account.bankmaster.save" text="Save" />
					</button>
					<button type="reset" class="btn btn-warning" onclick="clearForm()">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>