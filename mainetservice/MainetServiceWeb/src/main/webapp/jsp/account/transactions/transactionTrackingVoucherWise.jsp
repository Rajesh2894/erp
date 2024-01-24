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

<script src="js/account/transaction/transactionTracking.js"
	type="text/javascript"></script>
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
				 <form:hidden path="faYearid"  id="faYearid" />
				 <form:hidden path="accountHead"  id="accountHead" /> 
				 <form:hidden path="successfulFlag"  id="successfulFlag" />
				 <form:hidden path="fromDate"  id="fromDate" /> 
				 <form:hidden path="toDate"  id="toDate" /> 
				 <form:hidden path="voucherDate"  id="voucherDate" /> 
				 <form:hidden path=""  id="openDr"  value="${transactionTrackingDto.openingDrAmount}" /> 
				  <form:hidden path=""  id="openCr"  value="${transactionTrackingDto.openingCrAmount}" />
				  <form:hidden path=""  id="accountCode"  value="${transactionTrackingDto.accountCode}" />
				 
				 <form:hidden path=""  id="faYearid" />  
				 <div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
				</div>
				</form:form>
		<div id="receipt">	
		${transactionTrackingDto.accountCode}
	<div class="table-responsive" style="height:300px;">
			<table class="table table-bordered table-condensed">
			<tr>
			<th rowspan="2">Voucher Number </th>
			<th rowspan="2">Date </th>
			<th rowspan="2">Narration </th>
			<th colspan="2">Transactions</th>
			</tr>
			<tr>
			<th> Debit (Rs.) </th>
			<th> Credit (Rs.)</th>
			</tr>
			
			
			      <c:forEach items="${transactionTrackingDto.listOfSum}" var="trailBalance">
			      
			      

                              
 		               	<tr>               
 			                    <td><a href="#"   onclick="findShowVoucher(${trailBalance.voucherId})" >${trailBalance.voucherNumber}</a></td>
 			                    <td>${trailBalance.fromDate}</td>
 			                    <td>${trailBalance.narration}</td>
								<td align="right">${trailBalance.transactionDrAmount}</td>
								<td align="right">${trailBalance.transactionCrAmount}</td>
							</tr>

				</c:forEach>
			
			
			<tr>	
			<th colspan="3">Total</th>
		   
			<th style="text-align: right"> ${transactionTrackingDto.sumTransactionDR}</th>
			<th style="text-align: right">${transactionTrackingDto.sumTransactionCR}</th>
			</tr>
			</table>
			</div>
			<div class="text-center clear padding-10">
			<button type="button" class="btn btn-success btn-submit" onclick="window.location.href='TransactionTracking.html'">Home</button>
				<button type="button" class="btn btn-danger"
								onclick="findMonthWiseBack()">
								<spring:message code="account.bankmaster.back" text="Back" />
							</button>
							</div>
			</div>
			</div>
			</div>
		</div>