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
				 <form:hidden path=""  id="faYearid" /> 
				  <form:hidden path=""  id="openDr"  value="${transactionTrackingDto.openingDrAmount}" /> 
				  <form:hidden path=""  id="openCr"  value="${transactionTrackingDto.openingCrAmount}" /> 
				  <form:hidden path=""  id="accountCode"  value="${transactionTrackingDto.accountCode}" /> 
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
			<th rowspan="2">Months </th>
			<th colspan="2">Opening Balance</th>
			<th colspan="2">Transactions</th>
			<th colspan="2">Closing Balance</th>
			</tr>
			<tr>
			<th> Debit (Rs.) </th>
			<th> Credit (Rs.)</th>
			<th> Debit (Rs.) </th>
			<th> Credit (Rs.)</th>
			<th> Debit (Rs.) </th>
			<th> Credit (Rs.)</th>
			</tr>
			
			
			      <c:forEach items="${transactionTrackingDto.listOfSum}" var="trailBalance">
			      
			      

                              
 		               	<tr>               
 			                    <td><a href="#"   onclick="findMonthWise( '${trailBalance.fromDate}' , '${trailBalance.toDate}' )">${trailBalance.month}</a></td>
								<td align="right">${trailBalance.openingDrAmount}</td>
								<td align="right">${trailBalance.openingCrAmount}</td>
								<td align="right">${trailBalance.transactionDrAmount}</td>
								<td align="right">${trailBalance.transactionCrAmount}</td>
								<td align="right">${trailBalance.closingDrAmount}</td>
								<td align="right">${trailBalance.closingCrAmount}</td>
							</tr>

				</c:forEach>
			
			
			<tr>	
			<th >Total</th>
		   
			<th style="text-align: right"> </th>
			<th style="text-align: right"></th>
			<th style="text-align: right"> ${transactionTrackingDto.sumTransactionDR}</th>
			<th style="text-align: right">${transactionTrackingDto.sumTransactionCR}</th>
			<th style="text-align: right"></th>
			<th style="text-align: right"></th>
			</tr>
			</table>
			</div>
			<div class="text-center clear padding-10">
			<button type="button" class="btn btn-success btn-submit" onclick="window.location.href='TransactionTracking.html'">Home</button>
				<button type="button" class="btn btn-danger"
								onclick="searchTransactionDetails()">
								<spring:message code="account.bankmaster.back" text="Back" />
							</button>
							</div>
			
              </div>
			</div>
			</div>
			</div>
		