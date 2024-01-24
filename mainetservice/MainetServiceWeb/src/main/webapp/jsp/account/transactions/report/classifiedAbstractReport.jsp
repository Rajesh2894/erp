<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script src="js/account/accountFinancialReport.js"
	type="text/javascript"></script>
<script>
	function printdiv(printpage) {
		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
</script>
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<!-- Start Content here -->
		<div class="widget">
			<div class="widget-header">
				<h2 class="excel-title">
					<spring:message code="classified.abstact"
						text="Classified Abstract" />
				</h2>
			</div>
			<div class="widget-content padding">
				<form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<div class="form-group">
							<div
								class="col-sm-8 col-sm-offset-2 col-xs-8 col-xs-offset-1 text-center">
								<h3 class="text-extra-large margin-bottom-0 margin-top-0">${userSession.getCurrent().organisation.ONlsOrgname}</h3>
								 <strong ><spring:message
											code="classified.abstact" text="Classified Abstract" /></strong>
								<p class="margin-top-10">
									<strong class="excel-title"><spring:message
											code="classified.abstact.receipt"
											text="Classified Abstract of Receipts" /></strong>
								</p>
								<p><strong>For the Period From Date:
									${reportData.fromDate} To Date: ${reportData.toDate}</strong></p>
							</div>
							<div class="col-sm-2 col-xs-3">
								<p>
									Date:
									<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
									<br>Time:
									<fmt:formatDate value="${date}" pattern="hh:mm a" />
								</p>
							</div>
						</div>
						<input type="hidden" value="${validationError}" id="errorId">
						
						<table class="table table-bordered table-condensed clear ">
							<tr>
								
								<th><spring:message code="account.deposit.accountHead"
										text="Account Head" /></th>
								<th><spring:message
										code="classified.abstact.actual.receipt"
										text="Actual receipts for the Period" /></th>
								<th><spring:message code="classified.abstact.income.till"
										text="Income till date for the year" /></th>
								<th><spring:message
										code="classified.abstact.budgetory.provision"
										text="Budgetary Provision" /></th>
								<th><spring:message
										code="classified.abstact.balance.recover"
										text="Balance Recoverable" /></th>
								<c:forEach items="${receiptSideList.listOfSum}"
									var="receiptSide">

									<tr>

										<td>${receiptSide.accountCode}</td>
										<td style="text-align: right;">${receiptSide.currentYearAmount}</td>
										<td style="text-align: right;">${receiptSide.actualAmountReceived}</td>
										<td style="text-align: right;">${receiptSide.budgetAmount}</td>
										<td style="text-align: right;">${receiptSide.balanceRecoverable}</td>
									</tr>

								</c:forEach>
							<tr>

								<th><spring:message code="account.voucher.total"
										text="Total" /></th>
								<th style="text-align: right;">${receiptSideList.balanceAmountIndianCurrency}</th>
								<th style="text-align: right;">${receiptSideList.sumAcutualAmountIndianCurrency}</th>
								<th style="text-align: right;">${receiptSideList.sumBudgetAmountIndianCurrency}</th>
								<th style="text-align: right;">${receiptSideList. sumbalanceRecoverableIndianCurrency}</th>

							</tr>



						</table>
						
						<div class="form-group">
							<div class="col-xs-12 text-center">
								<p>
									<strong><spring:message
											code="classified.abstact.payment"
											text="Classified Abstract of Payments" /></strong>
								</p>
								<strong>For the Period From Date:
									${reportData.fromDate} To Date: ${reportData.toDate}</strong>
							</div>
						</div>

						<table class="table table-bordered table-condensed">
							<tr>
								
								<th><spring:message code="account.deposit.accountHead"
										text="Account Head" /></th>
								<th><spring:message
										code="classified.abstact.actual.payment.period"
										text="Actual Payments for the Period" /></th>
								<th><spring:message
										code="classified.abstact.expenditure.year"
										text="Expenditure to date for the year" /></th>
								<th><spring:message
										code="classified.abstact.budgetory.provision"
										text="Budgetary Provision" /></th>
								<th><spring:message
										code="classified.abstact.balance"
										text="Balance available" /></th>
							</tr>
							<c:forEach items="${paymentSideList.listOfSum}" var="paymentSide">
								
									<tr>
										
										<td>${paymentSide.accountCode}</td>
										<td align="right">${paymentSide.currentYearAmount}</td>
										<td align="right">${paymentSide.actualAmountReceived}</td>
										<td align="right">${paymentSide.budgetAmount}</td>
										<td align="right">${paymentSide.balanceRecoverable}</td> 
									</tr>
								
							</c:forEach>
							
							<tr>
										
										<th><spring:message code="account.voucher.total" text="Total"/></th>
										<th style="text-align: right;">${paymentSideList.totalDepositIndianCurrency}</th>
										<th style="text-align: right;">${paymentSideList.sumAcutualAmountIndianCurrency}</th>
										<th style="text-align: right;">${paymentSideList.sumBudgetAmountIndianCurrency}</th>
										<th style="text-align: right;">${paymentSideList.subTotalAmountIndianCurrency}</th>
										
									</tr>
							
							
							
							
							
						</table>
						<div class="text-center hidden-print padding-10">
							<button onclick="printdiv('receipt');"
								class="btn btn-primary hidden-print" title="Print">
								<i class="fa fa-print"></i>
								<spring:message code="account.budgetestimationpreparation.print"
									text="Print" />
							</button>
							<button type="button" class="btn btn-danger"
								onclick="window.location.href='AccountFinancialReport.html'" title="Back">
								<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
								<spring:message code="account.bankmaster.back" text="Back" />
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>