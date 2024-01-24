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
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script src="js/account/accountYearEndProcess.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script src="js/account/deductionRegister.js"></script>

<script>
	$(document).ready(function() {
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}
	});

	$(function() {
		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
		});
		$(function() {
			$(".table").tablesorter({
				cssInfoBlock : "avoid-sort",
			});

		});
	});
</script>
<div id="content">
	<!-- Start Content here -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="Year.End.Process.Datails" text="Year End Process Datails" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form class="form-horizontal"
				modelAttribute="tbAcYearEndProcess" id="acYearEndProcess"
				method="POST" action="AccountYearEndProcess.html">
				<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						
				
				<div id="receipt">
					<input type="hidden" value="${validationError}" id="errorId">
					<table class="table table-bordered table-condensed">
						<form:hidden path="" value="${keyTest}" id="keyTest" />
						<form:hidden path="faYearid" id="faYearid" />
						<thead>
							<tr>
								<th rowspan="2"><spring:message
										code="receipt.payment.particular.acchead"
										text="Particulars/Account Head" /></th>
								<th colspan="2"><spring:message code="bank.master.opBal"
										text="Opening Balance" /></th>
								<th colspan="2"><spring:message
										code="receipt.payment.transaction" text="Transactions" /></th>
								<th colspan="2"><spring:message code="bank.master.closeBal"
										text="Closing Balance" /></th>
							</tr>
							<tr>
								<th><spring:message code="receipt.payment.debit"
										text="Debit (Rs.)" /></th>
								<th><spring:message code="receipt.payment.credit"
										text="Credit (Rs.)" /></th>
								<th><spring:message code="receipt.payment.debit"
										text="Debit (Rs.)" /></th>
								<th><spring:message code="receipt.payment.credit"
										text="Credit (Rs.)" /></th>
								<th><spring:message code="receipt.payment.debit"
										text="Debit (Rs.)" /></th>
								<th><spring:message code="receipt.payment.credit"
										text="Credit (Rs.)" /></th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th colspan="15" class="ts-pager form-horizontal">
									<div class="btn-group">
										<button type="button" class="btn first">
											<i class="fa fa-step-backward" aria-hidden="true"></i>
										</button>
										<button type="button" class="btn prev">
											<i class="fa fa-arrow-left" aria-hidden="true"></i>
										</button>
									</div> <span class="pagedisplay"></span> <!-- this can be any element, including an input -->
									<div class="btn-group">
										<button type="button" class="btn next">
											<i class="fa fa-arrow-right" aria-hidden="true"></i>
										</button>
										<button type="button" class="btn last">
											<i class="fa fa-step-forward" aria-hidden="true"></i>
										</button>
									</div> <select class="pagesize input-mini form-control"
									title="Select page size">
										<option selected="selected" value="10" class="form-control">10</option>
										<option value="20">20</option>
										<option value="30">30</option>
										<option value="all">All Records</option>
								</select> <select class="pagenum input-mini form-control"
									title="Select page number"></select>
								</th>
							</tr>
						</tfoot>
						<tbody>
							<c:forEach items="${tbAcYearEndProcess.listOfSum}"
								var="trailBalance" varStatus="status">
								<c:set value="${status.index}" var="count"></c:set>
								<tr>
									<td>${trailBalance.accountCode}</td>
									<form:hidden path="listOfSum[${count}].sacHeadId" />
									<td style="text-align:right">${trailBalance.openingDrAmount}</td>
									<td style="text-align:right">${trailBalance.openingCrAmount}</td>
									<td style="text-align:right">${trailBalance.transactionDrAmount}</td>
									<td style="text-align:right">${trailBalance.transactionCrAmount}</td>
									<td style="text-align:right">${trailBalance.closingDrAmount}</td>
									<form:hidden path="listOfSum[${count}].closingDrAmount" />
									<td style="text-align:right">${trailBalance.closingCrAmount}</td>
									<form:hidden path="listOfSum[${count}].closingCrAmount" />
								</tr>
							</c:forEach>
						</tbody>
						<tr>
							<th><b><spring:message code="account.voucher.total"
										text="Total" /></b></th>
							<th style="text-align:right">${tbAcYearEndProcess.sumOpeningDR}</th>
							<th style="text-align:right">${tbAcYearEndProcess.sumOpeningCR}</th>
							<th style="text-align:right">${tbAcYearEndProcess.sumTransactionDR}</th>
							<th style="text-align:right">${tbAcYearEndProcess.sumTransactionCR}</th>
							<th style="text-align:right">${tbAcYearEndProcess.sumClosingDR}</th>
							<th style="text-align:right">${tbAcYearEndProcess.sumClosingCR}</th>
						</tr>

					</table>
				</div>
				<div class="text-center hidden-print padding-10">
					<input type="button" id="saveBtn" class="btn btn-success btn-submit"
						onclick="saveLeveledData(this)" value=" <spring:message code="accounts.stop.payment.save" text="Save" />" />
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='AccountYearEndProcess.html'">
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>

				</div>
			</form:form>
		</div>
	</div>
</div>


