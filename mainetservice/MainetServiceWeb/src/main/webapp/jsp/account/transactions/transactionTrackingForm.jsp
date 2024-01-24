<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
 <link href="assets/libs/Fixed-Header-Table-master/css/myTheme.css" rel="stylesheet"/>
<link href="assets/libs/Fixed-Header-Table-master/css/defaultTheme.css" rel="stylesheet"/>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="assets/libs/Fixed-Header-Table-master/jquery.fixedheadertable.min.js"></script> 

<script src="js/account/transaction/transactionTracking.js"
	type="text/javascript"></script>
	<script>
	$(document).ready(function(){
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
			
		}
	});
	var rowCount = $('.abc tbody tr').length;
	if(rowCount>12){
		$('.abc').addClass('fancyTable');
		$('.abc').attr('id', 'myTable01');
	}
	
	 $(document).ready(function() {
		    $('#myTable01').fixedHeaderTable({ footer: true });
		    
	}); 
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="transaction.tracking.title"
					text="TransactionTracking" />
			</h2>
		</div>
		<div class="widget-content padding">
		
			<form:form action="" method="post" class="form-horizontal"
				novalidate="novalidate" modelAttribute="transactionTrackingDto">
				<form:hidden path="" value="${keyTest}" id="keyTest" />
				 
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
					<label for="" class="col-sm-2 control-label required-control"><spring:message
							code="receivable.demand.entry.finacial.year" text="Financial Year"></spring:message></label>
					<div class="col-sm-4">
					<form:select type="select"
							class="form-control chosen-select-no-results" name=""
							id="faYearid" path="faYearid" data-rule-required="true">
							<form:option value="0">
								<spring:message
									code="account.budgetopenmaster.selectfinancialyear"
									text="Select Financial Year" />
							</form:option>
							<c:forEach items="${financialYearMap}" var="entry">
								<form:option value="${entry.key}" code="${entry.key}">${entry.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
				</div>

				<div class="text-center clear padding-10">
					<button type="button" class="button-input btn btn-success" name=""
						id="searchBtn" onclick="searchTransactionDetails()">
						<i class="fa fa-search"></i>
						<spring:message code="account.bankmaster.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning resetSearch"
						onclick="window.location.href = 'TransactionTracking.html'">
						<spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
				</div>
			</form:form>
			<div id="receipt">
			
			<table class=" table table-bordered abc" >
			<thead>
			<tr>
			<th rowspan="2"><spring:message code="account.Head" text="Account Head " /></th>
			<th colspan="2"><spring:message code="bank.master.opBal" text="Opening Balance" /></th>
			<th colspan="2"><spring:message code="receipt.payment.transaction" text="Transactions" /></th>
			<th colspan="2"><spring:message code="bank.master.closeBal" text="Closing Balance" /></th>
			
			</tr>
			<tr>
			<th><spring:message code="receipt.payment.debit" text="Debit (Rs.) " /></th>
			<th<spring:message code="receipt.payment.credit" text="Credit (Rs.) " /></th>
			<th><spring:message code="receipt.payment.debit" text="Debit (Rs.) " /></th>
			<th><spring:message code="receipt.payment.credit" text="Credit (Rs.) " /></th>
			<th><spring:message code="receipt.payment.debit" text="Debit (Rs.) " /></th>
			<th> <spring:message code="receipt.payment.credit" text="Credit (Rs.) " /></th>
			</tr>
			</thead>
			<tfoot>
			<tr>
			<th><spring:message code="accounts.deduction.register.tds.total" text="Total " /> </th>
			<th style="text-align: right">${transactionTrackingDto.sumOpeningDR}</th>
			<th style="text-align: right">${transactionTrackingDto.sumOpeningCR}</th>
			<th style="text-align: right"> ${transactionTrackingDto.sumTransactionDR}</th>
			<th style="text-align: right">${transactionTrackingDto.sumTransactionCR}</th>
			<th style="text-align: right">${transactionTrackingDto.sumClosingDR}</th>
			<th style="text-align: right">${transactionTrackingDto.sumClosingCR}</th>
			</tr>
			</tfoot>
			<tbody>
			      <c:forEach items="${transactionTrackingDto.listOfSum}" var="trailBalance">

                               
 				<tr>
                               
								<td><a href="#"   onclick="findHeadValue(${trailBalance.accountHead},${trailBalance.openingDrAmount},${trailBalance.openingCrAmount})" >${trailBalance.accountCode}</a></td>
								<td align="right" id=openDr value ="${trailBalance.openingDrAmount}">${trailBalance.openingDrAmount}</td>
								<td align="right" id=openCr value ="${trailBalance.openingCrAmount}">${trailBalance.openingCrAmount}</td>
								<td align="right">${trailBalance.transactionDrAmount}</td>
								<td align="right">${trailBalance.transactionCrAmount}</td>
								<td align="right">${trailBalance.closingDrAmount}</td>
								<td align="right">${trailBalance.closingCrAmount}</td>

				</tr>

				</c:forEach>
				
			</tbody>
			</table>
			</div>
			</div>
		</div>
	</div>
