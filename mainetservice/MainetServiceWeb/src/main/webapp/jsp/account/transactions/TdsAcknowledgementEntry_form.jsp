<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/transaction/tdsAcknowledgementEntry.js"
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
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script>
$(function() {
	
	$("#challanDateId").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });
	$("#ackDate").keyup(function(e){
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
<div class="widget">
	<div class="widget-content padding" id="paymentEntryDiv">

		<form:form method="POST" action="TdsAcknowledgementEntry.html"
			cssClass="form-horizontal" name="paymentEntry" id="paymentEntry"
			modelAttribute="paymentEntryDto">

			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<form:hidden path="paymentId" id="paymentId" />
			<form:hidden path="paymentEntryDate" id="paymentEntryDate" />
			<form:hidden path="qtrId" id="qtrId" />
			<div class="form-group">

				<label for="challanId"
					class="col-sm-2 control-label required-control"><spring:message
						code="" text="Challan No" /></label>
				<div class="col-sm-4">

					<form:input path="challanNo" id="challanId"
						cssClass="mandColorClass form-control" data-rule-required="true"></form:input>
				</div>

				<label for="ChallanDate"
					class="col-sm-2 control-label required-control"><spring:message
						code="" text="Challan Date" /></label>

				<div class="col-sm-4">
					<div class="input-group">
						<form:input path="challanDate" id="challanDateId"
							cssClass="mandColorClass form-control" data-rule-required="true" maxlength="10"></form:input>
						<label class="input-group-addon mandColorClass" for="challanDate"><i
							class="fa fa-calendar"></i> </label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label for="Acknowledgement No"
					class="col-sm-2 control-label required-control"><spring:message
						code="" text="Acknowledgement No" /></label>
				<div class="col-sm-4">
					<form:input path="ackNo" id="acknowledgementId"
						cssClass="mandColorClass form-control" data-rule-required="true"></form:input>
				</div>
				<label for="Acknowledgement Date"
					class="col-sm-2 control-label required-control"><spring:message
						code="" text="Acknowledgement Date" /></label>

				<div class="col-sm-4">
					<div class="input-group">
						<form:input path="ackDate" id="ackDate"
							cssClass="mandColorClass form-control" data-rule-required="true" maxlength="10"></form:input>
						<label class="input-group-addon mandColorClass" for="ackDate"><i
							class="fa fa-calendar"></i> </label>
					</div>
				</div>

			</div>
			<div class="text-center padding-10">
				<button type="button" class="btn btn-success btn-submit"
					onclick="saveTdsAckPaymentEntry(this)">
					<spring:message code="account.bankmaster.save" text="Save" />
				</button>
				<button type="button" class="btn btn-danger"
					name="button-1496152380275" style=""
					onclick="window.location.reload()" id="backBtn">
					<spring:message code="account.bankmaster.back" text="Back" />
				</button>
			</div>
		</form:form>
	</div>
</div>
