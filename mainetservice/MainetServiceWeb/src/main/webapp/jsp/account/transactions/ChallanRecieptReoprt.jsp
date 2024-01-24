<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<jsp:useBean id="date" class="java.util.Date"  scope="request"/>
<script src="js/account/accountFinancialReport.js"></script>
<script src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>

<script>
$(function() {
	$(".table").tablesorter().tablesorterPager({
		container: $(".ts-pager"),
		cssGoto  : ".pagenum",
		removeRows: false,
		size: 12
 	});

	$(function() { 

		  $(".table").tablesorter({ 
		     
		    cssInfoBlock : "avoid-sort", 
		    
		  }); 

		});
});
//Print Div

</script>

<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<div id="content">
    <apptags:breadcrumb></apptags:breadcrumb>
    <div class="content">
<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="accounts.receipt.challanName" text="Challan" />
		</h2>
	</div>

	<div class="widget-content padding">
	<div id="receipt">
		<form:form class="form-horizontal" modelAttribute="acRecieptRegister"
			method="POST" action="RecieptRegisterController.html">
			<div class="form-group padding-bottom-10 ">
				<div class="col-sm-8 col-xs-8 col-sm-offset-2 col-xs-offset-1 text-center">
								<h3 class="text-extra-large margin-bottom-0 margin-top-0">
									<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
									</c:if>
								</h3>
								<%-- <p class="excel-title"><spring:message code="accounts.receipt.challan"	text="Form No. 47S (5B)" /></p> --%>
					<p class="text-bold">
						<spring:message code="accounts.receipt.challanName"
							text="Receipt Voucher" /></p>
					<p class="text-bold"> <spring:message
							code="from.date.label" text="From Date" />: ${receiptRegisterdto.fromDate} <spring:message
							code="to.date.label" text="To Date" />: ${receiptRegisterdto.toDate}</p>  
					</div>
					
					 <div class="col-sm-2 col-xs-3" ><p><spring:message
							code="account.date" text="Date" />:<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" /><br><spring:message
							code="account.time" text="Time" />:<fmt:formatDate value="${date}" pattern="hh:mm a" /></p></div>
					 	
			</div>
			<form:hidden path="index" id="index" />
			<div id="export-excel">
			
			<c:forEach items="${receiptRegisterdto.listOfTbReceiptRegister}"
							var="listEmp" varStatus="status">
							<c:set var="empKey" value="${listEmp.key}"></c:set>
			<table class="table table-bordered table-condensed margin-bottom-30 importexcel">
				<div id="tlExcel" style="display:none;"><spring:message code="accounts.receipt.challanName" text="Challan" /></div>
			<thead>
				 <tr>
					<th style="text-align:center"><spring:message
							code="challan.receipt.tax.collector.names"
							text="User Name" /></th>
					<td colspan="8">${empKey}</td>
				</tr> 
				<tr>
					<th style="text-align:center; width:8%;"><spring:message
							code="challan.receipt.number.date"
							text="Receipt No. and Receipt Date" /></th>
					<th style="text-align: center"><spring:message
							code="challan.manual.receipt.number.date"
							text="Manual Receipt No. and Receipt Date" /></th>
							<th style="text-align: center"><spring:message
							code="challan.receipt.head.account" text="Head of Account" /></th>
							<th style="text-align: center;width: 10%" ><spring:message
							code="challan.receipt.amount.mode" text="Mode" /></th>
							<th style="text-align: center"><spring:message
							code="challan.receipt.amount.Instrument" text="Instrument No." /></th>
							<th style="text-align: center" colspan="2"><spring:message
							code="challan.receipt.bank.name" text="Bank's Name" /></th>
							
					
							<th style="text-align: center;" width="20%;"><spring:message
							code="accounts.receipt.received_from" text="Received From" /></th>
							<th style="text-align: center"><spring:message
							code="challan.receipt.amount" text="Receipt Amount (Rs.)" /></th>
					
					
					
				</tr>
				</thead>
				    <tfoot class="paginate">
				<tr>
				<th colspan="9" class="ts-pager form-horizontal">
				<div class="btn-group">
					<button type="button" class="btn first"><i class="fa fa-step-backward" aria-hidden="true"></i></button>
					<button type="button" class="btn prev"><i class="fa fa-arrow-left" aria-hidden="true"></i></button>
				</div>
				<span class="pagedisplay"></span> <!-- this can be any element, including an input -->
				<div class="btn-group">
					<button type="button" class="btn next"><i class="fa fa-arrow-right" aria-hidden="true"></i></button>
					<button type="button" class="btn last"><i class="fa fa-step-forward" aria-hidden="true"></i></button>
				</div>
				<select class="pagesize input-mini form-control" title="Select page size">
					<option selected="selected" value="12" class="form-control">12</option>
					<option value="20">20</option>
					<option value="30">30</option>
					<option value="all">All Records</option>
				</select>
				<select class="pagenum input-mini form-control" title="Select page number"></select>
			</th>
		</tr>
	</tfoot>
				<tbody>
				<c:forEach items="${listEmp.value}"
					var="RecieptList" varStatus="status">
					<c:set value="${status.index}" var="count"></c:set>
					<tr>
						<td align="center">
						${RecieptList.rmRcptno}<br>${RecieptList.rmDate}</td>
						<td>${RecieptList.manualReceiptNo}</td>
						<td>${RecieptList.receiptHead}</td>
						<td>${RecieptList.colletionMode}</td>
						<td align="right">${RecieptList.cheqno}</td>
						<td colspan="2">${RecieptList.bankName}</td>
						<td>${RecieptList.rmReceivedfrom}</td>
						<td align="right">${RecieptList.rmAmountIndianCurrency}</td>
				</c:forEach>
			
				</tbody>
				 <tbody class="avoid-sort">
				<tr>
				<td colspan="7">
							</td>
				<th style="text-align: left ;" ><spring:message
							code="challan.receipt.total.amount" text="Total Amount (Rs.)" /></th>
					<th class="text-right">
					${receiptRegisterdto.amountMap[empKey]}
					</th>
				</tr>
				</tbody>
				</table>
				
				</c:forEach>
				
				
				<table class="table table-bordered table-condensed margin-top-30" >
				<tr>
				<th class="text-left" colspan="8"><spring:message
							code="challan.receipt.amount.summary" text="Summary" /></th>
				</tr>
				<tr>
					<th style="text-align: center"><spring:message
							code="challan.receipt.head.account" text="Head of Account" /></th>
					<th style="text-align: center"><spring:message
							code="challan.receipt.cash" text="Cash (Rs.)" /></th>
							<th style="text-align: center"><spring:message
							code="challan.receipt.amount.dd" text="Cheque/DD (Rs.)" /></th>
					<th style="text-align: center"><spring:message
							code="challan.receipt.bank" text="Bank (Rs.)" /></th>
							
					
							<th style="text-align: center"><spring:message
							code="challan.receipt.amount.online" text="Online" /></th>
							<th style="text-align: center"><spring:message
							code="challan.receipt.total.amount" text="Total Amount (Rs.)" /></th>
							
				</tr>
				<c:forEach items="${receiptRegisterdto.listofSumHead}"
					var="BankList" varStatus="status">
					<tr>
						
						
						<td align="left">${BankList.receiptHead}</td>
						<td align="right">${BankList.cashAmountIndianCurrency}</td>
						<td align="right">${BankList.chequeAmountIndianCurrency}</td>
						<td align="right">${BankList.bankAmountIndianCurrency}</td>
						<td></td>
						<td align="right">${BankList.rmAmountIndianCurrency}</td>
					</tr>

				</c:forEach>
				<tr>
						<th><spring:message
							code="accounts.deduction.register.tds.total" text="Total" /></th>
						<th class="text-right">${receiptRegisterdto.sumOfCashAmountIndianCurrency}</th>
						<th class="text-right">${receiptRegisterdto.sumOfChequeAmountIndianCurrency}</th>
						<th class="text-right">${receiptRegisterdto.sumOfBankAmountIndianCurrency}</th>
						<th></th>
						<th class="text-right">${receiptRegisterdto.sumOfRmAmountindianCurrency}</th>
						
						
						
					</tr>
				
				
			</table>
			</div>
				 
		</form:form>
		<style>
			@media print{
				.paginate{
					visibility: hidden;
				}
				@page {
						margin: 15px;
					} 
			}
		  </style>
		</div>
		<div class="text-center hidden-print padding-10">
							<button onclick="PrintDiv('${accounts.receipt.challan}');" class="btn btn-primary hidden-print" type="button"><i class="fa fa-print"></i> <spring:message code="account.budgetestimationpreparation.print" text="Print" /></button>
							<button type="button" class="btn btn-danger"
								onclick="window.location.href='RecieptRegisterController.html'">
								<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
								<spring:message code="account.bankmaster.back" text="Back" />
							</button>
		</div>
	</div>
</div>
</div>
</div>