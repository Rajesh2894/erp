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
<script src="js/account/accountFinancialReport.js"
	type="text/javascript"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script>
	$(function() {
		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
			size: 12
		});
		$(function() {
			$(".table").tablesorter({
				cssInfoBlock : "avoid-sort",
			});

		});
	});
</script>
<!--Mention Entry point ??????  -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title">
				<spring:message code="account.budgetestimation.level"
					text="Budget Estimation" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div id="receipt">
			     <h2 style="display:none" class="excel-title">
				 <spring:message code="account.budgetestimation.level" text="Budget Estimation" />
				</h2>
				<div class="form-group">
					<div class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
						<h3 class="text-large margin-bottom-0 margin-top-0 text-bold">
							<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
							</c:if>
						</h3>
						${reportData.approvedBy}
						<strong><spring:message
								code="accounts.reappropriation.heading.budgetperiod"
								text="For budgetary period" /> ${reportData.financialYear} <%-- ${reportData.fromDate} --%></strong>
					</div>
					<div class="col-sm-2 col-xs-3">
						<p>
							 <spring:message
                            code="account.date" text="Date" />:
							<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
							<br><spring:message
                            code="account.time" text="Time" />:
							<fmt:formatDate value="${date}" pattern="hh:mm a" />
					</div>
				</div>

				<div class="padding-5 clear">&nbsp;</div>

					<div id="export-excel">
						<table class="table table-bordered table-condensed" id="importexcel">
							<div class="excel-title" id="tlExcel" style="display: none">
								<spring:message code="account.budgetestimation.level"
									text="Budget Estimation" />
							</div>
							<thead>
								<tr>
									<th colspan="8">Budget Estimate of <strong>Receipts</strong>
										of the Municipal Fund / Special Fund for the financial year
										${reportData.financialYear} (Revised) &amp;
										${reportData.fromDate} (Original)
									</th>
								</tr>
								<tr>
									<th rowspan="2"><spring:message
											code="account.budgetprojectedrevenuemaster.budgetcode"
											text="Account Code" /></th>
									<th rowspan="2"><spring:message
											code="account.budgetprojectedrevenuemaster.budgethead"
											text="Account Head" /></th>
									<th rowspan="2"><spring:message
											code="classified.abstact.average.years"
											text="Average of last 3 years" /></th>
									<th rowspan="2"><spring:message
											code="classified.abstact.actula.last.year"
											text="Actual of last year" /></th>
									<th colspan="2"><spring:message
											code="classified.abstact.budget.current.year"
											text="Budget Estimates of current year" /></th>
									<th rowspan="2"><spring:message
											code="classified.abstact.budget.next.year"
											text="Budget Estimates of next (ensuing) year" /></th>
									<th rowspan="2"><spring:message
											code="classified.abstact.explanatory.remarks"
											text="Explanatory Remarks" /></th>
								</tr>
								<tr>
									<th>Sanctioned</th>
									<th>Revised</th>
								</tr>
							</thead>
							<tfoot class="tfoot">
								<tr>
									<th colspan="8" class="ts-pager form-horizontal">
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
											<option selected="selected" value="12" class="form-control">12</option>
											<option value="20">20</option>
											<option value="30">30</option>
											<option value="all">All Records</option>
									</select> <select class="pagenum input-mini form-control"
										title="Select page number"></select>
									</th>
								</tr>
							</tfoot>
							<tbody>
								<c:forEach items="${reportData.listOfBudgetEstimation}"
									var="budgetEstimation">
									<tr>
										<td style="width: 12%">${budgetEstimation.accountCode}</td>
										<td style="width: 20%">${budgetEstimation.accountHead}</td>
										<td style="text-align: right">${budgetEstimation.lastThreeYrAmt}</td>
										<td style="text-align: right">${budgetEstimation.lastYrAmt}</td>
										<td style="text-align: right">${budgetEstimation.originalEst}</td>
										<td style="text-align: right">${budgetEstimation.revisedEstimation}</td>
										<td style="text-align: right">${budgetEstimation.nxtYrOEstimation}</td>
										<td style="text-align: right">${budgetEstimation.remarks}</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th colspan="8">Budget Estimate of<strong>
											Expenditure</strong> of the Municipal Fund / Special Fund for the
										financial year ${reportData.financialYear} (Revised) &amp;
										${reportData.fromDate} (Original)
									</th>
								</tr>
							</tfoot>	
							<tbody>
								<c:forEach items="${reportData.listOfExpenditure}"
									var="expenditure">
									<tr>
										<td style="width: 12%">${expenditure.accountCode}</td>
										<td style="width: 12%">${expenditure.accountHead}</td>
										<td style="text-align: right">${expenditure.lastThreeYrAmt}</td>
										<td style="text-align: right">${expenditure.lastYrAmt}</td>
										<td style="text-align: right">${expenditure.originalEst}</td>
										<td style="text-align: right">${expenditure.revisedEstimation}</td>
										<td style="text-align: right">${expenditure.nxtYrOEstimation}</td>
										<td style="text-align: right">${expenditure.remarks}</td>
									</tr>
								</c:forEach>
							</tbody>	
						
						</table>
					</div>
			</div>
			<div class="text-center hidden-print padding-10">
				<button onclick="PrintDiv('receipt');"
					class="btn btn-primary hidden-print" title="Print">
					<i class="fa fa-print"></i>
					<spring:message code="account.budgetestimationpreparation.print"
						text="Print" />
				</button>
				<button type="button" class="btn btn-danger"
					onclick="window.location.href='AccountBudgetReports.html'" title="back">
					<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
					<spring:message code="account.bankmaster.back" text="Back" />
				</button>
			</div>
		</div>
	</div>
</div>