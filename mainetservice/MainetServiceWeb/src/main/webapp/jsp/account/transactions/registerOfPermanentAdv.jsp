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
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script src="js/account/accountFinancialReport.js"
	type="text/javascript"></script>

<script>
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
<!-- Start Content here -->
<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="accounts.registerof.permanent.davance.header"
				text="Register of Permanent Advances" />
			<h2>
	</div>
	<div class="widget-content padding">
		<form action="" method="get" class="form-horizontal">
			<div id="receipt">
				<div class="form-group padding-bottom-10">
					<div class="col-xs-12 text-center">
						<h3 class="text-extra-large margin-bottom-0 margin-top-0">${userSession.getCurrent().organisation.ONlsOrgname}</h3>
						 <strong> <spring:message
									code="accounts.registerof.permanent.davance.register"
									text="Register of Permanent Advance of" /> <spring:message
									code="accounts.registerof.permanent.davance.foryear"
									text="For The Year" /></strong>
					</div>
				</div>
				<div id="export-excel">
					<table class="table table-bordered table-condensed">
						<tbody>
						<thead>
							<tr>
								<th><spring:message
										code="accounts.registerof.permanent.davance.sr.no"
										text="Sr.No." /></th>
								<th><spring:message
										code="accounts.registerof.permanent.davance.date" text="Date" /></th>
								<th><spring:message
										code="accounts.registerof.permanent.davance.payment.date.no"
										text="Payment Order No." /></th>
								<th><spring:message
										code="accounts.registerof.permanent.davance.sr.no.sub.voucher"
										text="Sr.No. Of Sub Voucher for which Payment order is Submitted" /></th>
								<th><spring:message
										code="accounts.registerof.permanent.davance.amount"
										text="Amount(Rs.)" /></th>
								<th><spring:message
										code="accounts.registerof.permanent.davance.natureof.expenditure"
										text="Nature Of Expenditure" /></th>
								<th><spring:message
										code="accounts.registerof.permanent.davance.dateof.bill"
										text="Date Of Bill" /></th>
								<th><spring:message
										code="accounts.registerof.permanent.davance.payee's.name"
										text="Payee's Name" /></th>
								<th><spring:message
										code="accounts.registerof.permanent.davance.amount"
										text="Amount Paid(Rs.)" /></th>
								<th><spring:message
										code="accounts.registerof.permanent.davance.daily.balance"
										text="Daily Blance(Rs.)" /></th>
								<th><spring:message
										code="Initials Of the Officer Holding the Advance"
										text="accounts.registerof.permanent.davance.initials" /></th>
								<th><spring:message
										code="accounts.registerof.permanent.davance.remarks"
										text="Remarks" /></th>
							</tr>
						</thead>
						<tfoot>
							<tr>
								<th colspan="11" class="ts-pager form-horizontal">
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
							<tr class="text-center">
								<td>1</td>
								<td>2</td>
								<td>3</td>
								<td>4</td>
								<td>5</td>
								<td>6</td>
								<td>7</td>
								<td>8</td>
								<td>9</td>
								<td>10</td>
								<td>11</td>
								<td>12</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="text-center hidden-print padding-10">
				<button onclick="PrintDiv('${accounts.receipt.challan}');"
					class="btn btn-primary hidden-print">
					<i class="fa fa-print"></i>
					<spring:message code="account.budgetestimationpreparation.print"
						text="Print" />
				</button>
				<button type="button" class="btn btn-danger"
					onclick="window.location.href='OutstandingBillRegister.html'">
					<spring:message code="account.bankmaster.back" text="Back" />
				</button>
			</div>
		</form>
	</div>
</div>
