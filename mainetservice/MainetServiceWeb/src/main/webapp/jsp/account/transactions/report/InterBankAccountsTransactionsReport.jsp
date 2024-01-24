<!-- Start JSP Necessary Tags -->
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
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script
	src="assets/libs/excel-export/excel-export.js"></script>
<script src="js/account/interBankTransactionsReportReport.js"
	type="text/javascript"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title"><spring:message code="inter.Bank.Transactions.Report" text="Inter Bank Transactions Report" /></h2>
			<apptags:helpDoc url="incomeAndExpenditureSheduleReport.html"
				helpDocRefURL="incomeAndExpenditureSheduleReport.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form action="" class="form-horizontal"
				id="interBankTransactionsReport">
				<div id="receipt">
						<div class="text-center col-sm-10 margin-bottom-10">
							<h3 class="margin-top-0 margin-bottom-0"><c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
</c:if>
<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
</c:if> </h3>
							<div>
								<spring:message code="inter.Bank.Accounts.Transactions" text="Inter Bank Accounts Transactions" /> <br/>
								<spring:message code="from.date.label" text="From Date" />: <span
									class="text-bold">${command.accountFinancialReportDTO.fromDate}</span>
								<spring:message code="to.date.label" text="To Date" />: <span class="text-bold">${command.accountFinancialReportDTO.toDate}</span>
							</div>
						</div>
						<div class="col-sm-2 margin-bottom-10">
							<span class="text-bold"><spring:message
									code="swm.day.wise.month.report.date" text="Date" /></span>
							<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
							<div class="margin-top-12">
								<span class="text-bold"><spring:message
										code="swm.day.wise.month.report.time" text="Time" /></span>
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</div>
						</div>
					<div class="clearfix"></div>
					<div class="table-responsive margin-top-10">
						<div id="export-excel">
							<table class="table table-bordered" id="importexcel">
							<h2 class="excel-title" id="tlExcel" style="display: none">Inter Bank Transactions Report</h2>
								<thead>
									<tr>
										<th class="text-center">Transaction Date</th>
										<th class="text-center">Transaction No.</th>
										<th class="text-center">Payment Bank Account</th>
										<th class="text-center">Receipt Bank Account</th>
										<th class="text-center">Transfer Amount</th>
										<th class="text-center">Particulars</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.accountFinancialReportDTO.listOfDeposit}" var="listofBankData">
										<tr>
											<td class="text-center">${listofBankData.transactionDate}</td>
											<td class="text-center">${listofBankData.transactionNo}</td>
											<td class="text-center">${listofBankData.bankAcNo}</td>
											<td class="text-center">${listofBankData.receiptNo}</td>
											<td class="text-right">${listofBankData.paymentAmnt}</td>
											<td>${listofBankData.narration}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</form:form>
			<div class="text-center hidden-print padding-10">
				<button onClick="printdiv('receipt');"
					class="btn btn-primary hidden-print" title='<spring:message code="account.budget.code.print" text="Print" />'>
					<i class="fa fa-print"></i><spring:message code="account.budget.code.print" text="Print" />
				</button>
				<button id="btnExport1" type="button"
					class="btn btn-blue-2 hidden-print" title='<spring:message code="account.common.account.downlaod" text="Download" />'>
					<i class="fa fa-file-excel-o"></i><spring:message code="account.common.account.downlaod" text="Download" />
				</button>
				<button type="button" class="btn btn-danger" onclick="back();" title='<spring:message code="solid.waste.back" text="Back" />'>
					<i class="fa fa-chevron-circle-left padding-right-5"
						aria-hidden="true"></i>
					<spring:message code="solid.waste.back" text="Back" />
				</button>
			</div>


		</div>
	</div>
</div>