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
<script src="js/account/accountFinancialReport.js"
	type="text/javascript"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script type="text/javascript">
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
<!-- USE THE BELOW CSS ONLY WHEN THE TABLE HEADER NEEDS TO BE FIXED -->
<style>
	div.tableContainer > table thead th, div.tableContainer > table tfoot th {
		position: -webkit-sticky; /* for Safari */
		position: sticky;
	}
	
	div.tableContainer > table thead th { top: -1px;}	
	div.tableContainer > table tfoot th { bottom: -1px; }
</style>
<!-- ---------------------------------------------------------------- -->

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="budget.reappropriation.master.secondaryaccountcode"
					text="Secondary Head Code" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
				<div id="export-excel">

					<div
						class="col-sm-8 col-sm-offset-2 col-xs-8 col-xs-offset-1 text-center">
						<h3 class="text-extra-large margin-bottom-0 margin-top-0">
									<c:if test="${userSession.languageId eq 1}">
                                     	${ userSession.getCurrent().organisation.ONlsOrgname}<br>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
									                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
									</c:if></h3>
						<h3 class="text-extra-large margin-bottom-0 margin-top-0">
							<spring:message code="budget.reappropriation.master.secondaryaccountcode"
								text="Secondary Head Code" />
						</h3>
						<%-- <p class="text-large margin-bottom-0 margin-top-0">${reportData.financialYear}</p> --%>
					</div>
					<div class="col-sm-2 col-xs-3">
						<p>
							<spring:message code="accounts.date"
								text="Date:" />
							<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
							<br><spring:message code="acounts.time"
								text="Time:" />
							<fmt:formatDate value="${date}" pattern="hh:mm a" />
					</div>
					<div class="clearfix padding-10"></div>
					<div class="padding-5 clear">&nbsp;</div>
					<div class="overflow-visible tableContainer">
					<span id="errorId"></span>
						<!-- <div id="export-excel"> -->
							<table class="table table-bordered table-condensed" id="importexcel">
								<div id="tlExcel" style="display:none;"><spring:message code="budget.reappropriation.master.secondaryaccountcode" text="Secondary Head Code" /></div>		
								<thead>
								 <tr> 
										<th width="5%"><spring:message
												code="opening.balance.report.srno" text="Sr. No." /></th>
										<th><spring:message
												code="account.budget.code.master.functioncode"
												text="Function" /></th>
										<th><spring:message
												code="account.budget.code.master.primaryaccountcode"
												text="Primary Head" /></th>
										<th><spring:message
												code="account.budget.code.master.secondaryaccountcode"
												text="Secondary Head" /></th>
										<th><spring:message
												code="account.budget.code.master.status"
												text="status" /></th>
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
								 <c:forEach items="${reportList.secondList}"
										var="SecondaryHead" varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td>${SecondaryHead.function}</td>
											<td>${SecondaryHead.primaryHeadCode}</td>
											<td>${SecondaryHead.secondaryHeadCode}</td>
											<td>${SecondaryHead.status}</td>
										</tr>
									</c:forEach> 
								</tbody>
								<tr>
									<%-- <th colspan="2"><spring:message
											code="opening.balance.report.totalcredit"
											text="Total Opening Credit" /></th>
									<th class="text-right">${reportData.totalCrAmountIndianCurrency}</th>
									<th colspan="2"></th>
 --%>
								</tr>

								<tr>
									<%-- <th colspan="2"><spring:message
											code="opening.balance.report.totaldebit"
											text="Total Opening Debit" /></th>
									<th class="text-right">${reportData.totalDrAmountIndianCurrency}</th>
									<th colspan="2"></th> --%>

								</tr>
							</table>
						</div>
					</div>
					<style>
					@media print{
						tfoot{
							display: none;
						}
						@page {
							margin: 15px;
						} 
					}
				  </style>
				</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('receipt');"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="account.budgetestimationpreparation.print"
							text="Print" />
					</button>
					<apptags:backButton url="tbAcSecondaryheadMaster.html"
						cssClass="btn btn-danger"></apptags:backButton>
				</div>
			</form>
		</div>
	</div>
</div>