<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script
	src="assets/libs/excel-export/excel-export.js"></script>
<script src="js/account/summaryOfBudgetreport.js"></script>
<script language="javascript">
	function printdiv(printpage) {
		debugger;
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
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title"><spring:message code="summary.Of.budget.report.heading" text="Summary Of Budget"/></h2>
			<div class="additional-btn">
				<apptags:helpDoc url="budgetEstimateConsolidationFormat.html"
					helpDocRefURL="budgetEstimateConsolidationFormat.html"></apptags:helpDoc>
			</div>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
				<div class="form-group">
						<div
							class="ccol-xs-6 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">${ userSession.getCurrent().organisation.ONlsOrgname}
								<br> <u><spring:message code="summary.Of.budget.report.heading" text="Summary Of Budget"/></u>
								<br><spring:message code="summary.Of.budget.report.for.period" text="For The Period"/> <u> ${command.accountFinancialReportDTO.financialYear}</u></br>
							</h3>
						</div>
						<div class="col-sm-2 col-xs-3 pull-right">
							<p>
								<spring:message code="swm.day.wise.month.report.date"
									text="Date" />
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<spring:message code="swm.day.wise.month.report.time"
									text="Time" />
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
							<span class="text-bold">Form BUD-3</span>
						</div>
					</div>
					<div class="container">
						<div id="export-excel">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th class="text-center"><spring:message code="summary.Of.budget.report.particular" text="Particulars"/></th>
										<th class="text-center"><spring:message code="summary.Of.budget.report.previous.year" text="Actual For The Previous Year Rs."/></th>
										<th class="text-center"><spring:message code="summary.Of.budget.report.current.year" text="Budget Estimates For The Current Year Rs."/></th>
										<th class="text-center"><spring:message code="summary.Of.budget.report.revised.current.year" text="Revised Estimates For The Current Year Rs."/></th>
										<th class="text-center"><spring:message code="summary.Of.budget.report.next.year" text="Budget Estimates For The Next Year Rs."/></th>
									</tr>
									<tr>
										<th class="text-center">1</th>
										<th class="text-center">2</th>
										<th class="text-center">3</th>
										<th class="text-center">4</th>
										<th class="text-center">5</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><spring:message code="summary.Of.budget.report.opening.balance" text="Opening Balance*"/></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td><u><spring:message code="summary.Of.budget.report.add" text="Add:"/></u></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td><spring:message code="summary.Of.budget.report.revenue.receipts" text="Revenue Receipts"/></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td><spring:message code="summary.Of.budget.report.capital.receipts" text="Capital Receipts"/></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td><u><spring:message code="summary.Of.budget.report.less" text="Less"/></u></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td><spring:message code="summary.Of.budget.report.revenue.expenditure" text="Revenue Expenditure"/></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td><spring:message code="summary.Of.budget.report.capital.expenditure" text="Capital Expenditure"/></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td><spring:message code="summary.Of.budget.report.closing.balance" text="Closing Balance*"/></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<spring:message code="summary.Of.budget.report.bank.balance" text="*Balances denote cash and bank balance"/>
				</div>
			</form>
			<div class="text-center hidden-print padding-10">
				<button onClick="printdiv('receipt');"
					class="btn btn-primary hidden-print" data-toggle="tooltip"
					data-original-title="Print">
					<i class="fa fa-print"></i> <spring:message code="" text="Print"/>
				</button>
				<button id="btnExport" type="button"
					class="btn btn-blue-2 hidden-print" data-toggle="tooltip"
					data-original-title="Download">
					<i class="fa fa-file-excel-o"></i><spring:message code="" text="Download"/> 
				</button>
				<button type="button" class="btn btn-danger" onclick="back();"
					data-toggle="tooltip" data-original-title="Back">
					<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
					<spring:message code="solid.waste.back" text="Back" />
				</button>
			</div>
		</div>
	</div>
</div>