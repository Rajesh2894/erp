<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<!-- <script src="js/mainet/script-library.js"></script> -->
<script src="js/account/transaction/accountContraVoucherEntry.js"
	type="text/javascript"></script>
<script>
$(function() {
$(".datepicker").datepicker({
    dateFormat: 'dd/mm/yy',
	changeMonth: true,
	changeYear: true,
	maxDate: '0',
});
	var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
	var disableBeforeDate = new Date(response[0], response[1], response[2]);
	var date = new Date();
	var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());	
$("#fromDate").datepicker({
    dateFormat: 'dd/mm/yy',
	changeMonth: true,
	changeYear: true,
	minDate : disableBeforeDate,
	maxDate: today,
});
$("#fromDate").keyup(function(e){
    if (e.keyCode != 8){    
        if ($(this).val().length == 2){
            $(this).val($(this).val() + "/");
        }else if ($(this).val().length == 5){
            $(this).val($(this).val() + "/");
        }
     }
    });

$("#fromDate").datepicker('setDate', new Date()); 

$("#toDate").datepicker({
    dateFormat: 'dd/mm/yy',
	changeMonth: true,
	changeYear: true,
	minDate : disableBeforeDate,
	maxDate: today,
});
$("#toDate").keyup(function(e){
    if (e.keyCode != 8){    
        if ($(this).val().length == 2){
            $(this).val($(this).val() + "/");
        }else if ($(this).val().length == 5){
            $(this).val($(this).val() + "/");
        }
     }
    });

$("#toDate").datepicker('setDate', new Date()); 
});
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="account.contra" text="Contra" />
				<strong> <spring:message code="receipt.payment.transaction"
						text="Transactions" /></strong>
			</h2>
		<apptags:helpDoc url="ContraVoucherEntry.html" helpDocRefURL="ContraVoucherEntry.html"></apptags:helpDoc>	
		</div>

		<div class="widget-content padding" id="contraEntryDiv">
			<form:form method="POST" action="" cssClass="form-horizontal"
				name="contraEntry" id="contraEntry"
				modelAttribute="contraVoucherBean">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
				</div>
				<div class="form-group">
					<label for="" class="col-sm-2 control-label"><spring:message
							code="budget.reappropriation.authorization.fromdate" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" cssClass="form-control" name="fromDate"
								id="fromDate" maxlength="10" />
							<label class="input-group-addon" for="fromDate"><i
								class="fa fa-calendar"></i><span class="hide"><spring:message
										code="account.additional.supplemental.auth.icon" text="icon" /></span>
								<input type="hidden" id="trasaction-date-icon30"> </label>
						</div>
					</div>
					<label for="" class="col-sm-2 control-label"><spring:message
							code="budget.additionalsupplemental.authorization.todate" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" cssClass="form-control" name="toDate"
								id="toDate" maxlength="10" />
							<label class="input-group-addon" for="toDate"><i
								class="fa fa-calendar"></i><span class="hide"><spring:message
										code="account.additional.supplemental.auth.icon" text="icon" /></span>
								<input type="hidden" id="toDate"> </label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label for="transactionType" class="col-sm-2 control-label"><spring:message
							code="accounts.receipt.transaction.type" text="Transaction Type" /></label>
					<div class="col-sm-4">
						<select id="entryType" class="form-control">
							<option value="T"><spring:message
									code="account.contra.voucher.transfer.entry"
									text="Transfer Entry" /></option>
						</select>
					</div>
					<label for="transactionNo" class="col-sm-2 control-label"><spring:message
							code="account.budgetreappropriationmaster.transactionnumber" text="Transaction Number" /></label>
					<div class="col-sm-4">
						<form:input path="" cssClass="form-control" name="transactionNo"
							id="transactionNo" />

					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" id="search"
						value="<spring:message code="search.data"/>"
						class="btn btn-success searchData" onclick="searchContraEntry()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning resetSearch"
						onclick="window.location.href = 'ContraVoucherEntry.html'">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
					<button type="button" value="Contra Entry"
						class="btn btn-blue-2 createData">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="account.bankmaster.add" text="Add" />
					</button>
				</div>
				<table id="contraEntryGrid"></table>
				<div id="pagered"></div>
			</form:form>
		</div>
	</div>
</div>