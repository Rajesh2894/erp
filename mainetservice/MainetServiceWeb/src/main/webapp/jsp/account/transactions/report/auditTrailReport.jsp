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
			size: 12
		});
		$(function() {
			$(".table").tablesorter({
				cssInfoBlock : "avoid-sort",
			});

		});
	});
</script>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title">
				<spring:message code="accounts.financial.audit.trail.head"
					text="Audit Trail for Master Data" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">

				<div id="receipt">
					<!-- <div class="form-group"> -->
						<div class="col-sm-offset-2 col-sm-8 text-center">
						<h3 class="text-extra-large margin-bottom-0 margin-top-0">
							<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
							</c:if>
						</h3>
						<p>
								<strong class="excel-title"><spring:message
										code="accounts.financial.audit.trail.head"
										text="Audit Trail for Master Data" /></strong>
							</p>
							<p>
								<strong><spring:message
										code="accounts.financial.audit.trail.fromdate"
										text="From Date:" /></strong> ${reportData.fromDate} <strong><spring:message
										code="accounts.financial.audit.trail.todate" text="To Date:" /></strong>${reportData.toDate}
							</p>
						</div>
						<div class="col-sm-2">
							<p>
								<strong><spring:message code="account.date" text="Date" /></strong> :
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
							</p>
							<p>
								<strong><spring:message
										code="accounts.financial.audit.trail.time" text="Time" /></strong> :
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
					<!-- </div> -->
					<div class="clearfix"></div>
					<!-- <div class="overflow-visible"> -->
					<div class="margin-top-10">
						<div id="export-excel">
							<table class="table table-bordered " id="importexcel">
								<h2 class="excel-title" id="tlExcel" style="display: none">
									<spring:message code="accounts.financial.audit.trail.head"
										text="Audit Trail for Master Data" />
								</h2>
								<thead>
									<tr>
										<th class="text-left"><spring:message code=""
												text="Bill No" /></th>

										<th class="text-left"><spring:message code=""
												text="Bill Entry Date  <br> Vendor Name" /></th>

										<th class="text-left"><spring:message code=""
												text="Expenditure Head <br>Expenditure Amount <br> Saction Amount" /></th>

										<th class="text-left"><spring:message code=""
												text="Deduction Head <br> Deduction Amount" /></th>

										<th class="text-left" width="10%"><spring:message code=""
												text="Narration" /></th>

										<th class="text-left"><spring:message code=""
												text="Created By <br> Created Date <br> Ip Address" /></th>

										<th class="text-left"><spring:message code=""
												text="Updated By <br> Updated Date <br> Updated Ip Address" /></th>

									</tr>
								</thead>
								<tfoot class="tfoot">
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
									<c:forEach items="${reportData.listOfmasterData}"
										var="auditdata">
										<tr>
											<td>${auditdata.billNo}</td>
											<td>${auditdata.billEntryDate}<br>${auditdata.vendorName}</td>
											<td>${auditdata.secHeadId}<br>${auditdata.billChargeAmt}<br>${auditdata.billTotalAmount}</td>
											<td>${auditdata.secHeadId1}<br>${auditdata.deductionAmt}</td>
											<td>${auditdata.narration}</td>
											<td>${auditdata.createdBy}<br>${auditdata.createdDate}<br>${auditdata.lgIpMacAddress}</td>
											<td>${auditdata.updatedBy}<br>${auditdata.updatedDate}<br>${auditdata.lgIpMacAddressUpdated}</td>
										</tr>
									</c:forEach>

								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('${accounts.receipt.challan}');"
						class="btn btn-primary hidden-print" title="Print">
						<i class="fa fa-print"></i>
						<spring:message code="account.budgetestimationpreparation.print"
							text="Print" />
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='AccountOtherReports.html'" title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>
			</form>
		</div>
	</div>
</div>