<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	
<script>
	$(function() {
		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
			size: 12
		});
	});
</script>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title">
				<spring:message code="cheque.dishonor.register"
					text="Cheques Dishonoured Register" />
		</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
					<div class="form-group">
						<div
							class="col-sm-8 col-sm-offset-2 col-xs-8 col-xs-offset-1 text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">
								<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
								</c:if>
							</h3>
							<strong class="excel-title"><spring:message
									code="cheque.dishonor.register"
									text="Cheques Dishonoured Register" /></strong>
							<p class="margin-left-30">
								<strong><spring:message
										code="cheque.dishonor.register.fromdate" text="From Date :" /></strong>${reportData.fromDate}
									<strong><spring:message code="cheque.dishonor.register.todate"
										text="To Date:" /> </strong>${reportData.toDate}
							</p>
						</div>
						<div class="col-sm-2 col-xs-3">
							<p>
								<spring:message code="account.date"
										text="Date" />:
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br><spring:message code="account.time"
										text="Time" />:
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
					</div>
					<div class="clearfix padding-10"></div>
						<div  id="export-excel">
							<table class="table table-bordered table-condensed" id="importexcel">
							<div class="excel-title" id="tlExcel" style="display: none"><spring:message code="cheque.dishonor.register"	
							text="Cheques Dishonoured Register" />	</div>
								<thead>
									<tr>
										<th data-sorter="false"><spring:message code="cheque.dishonor.register.srno"
												text="Sr. no." /></th>
										<th><spring:message code="cheque.dishonor.register.name"
												text="Name of Owner/Trader/Licensee/Others" /></th>
										<th><spring:message
												code="cheque.dishonor.register.assenceno"
												text="Assenssee No./ Trade license No./ Stall No./ Others" /></th>
										<th><spring:message
												code="cheque.dishonor.register.cheque"
												text="Cheque/ Draft No." /></th>
										<th><spring:message
												code="cheque.dishonor.register.amount"
												text="Amount not Realised" /></th>
										<th><spring:message
												code="cheque.dishonor.register.initiate"
												text="Dishonor Charge" /></th>
										<th><spring:message
												code="cheque.dishonor.register.receipts"
												text="Dishonor Date" /></th>
									</tr>
								</thead>
								<tfoot class="tfoot">
									<tr>
										<th colspan="7" class="ts-pager form-horizontal">
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
									<c:forEach items="${reportData.listofchequeDishonor}"
										var="chequeDishonor">
										<tr>
											<td style="text-align: center">${chequeDishonor.index}</td>
											<td>${chequeDishonor.receivedFrom}</td>
											<td>${chequeDishonor.receiptNumber}</td>
											<td>${chequeDishonor.chequeNo}</td>
											<td style="text-align: right">${chequeDishonor.ramt}</td>
											<td style="text-align: right">${chequeDishonor.discharge}</td>
											<td>${chequeDishonor.chkDate}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
				</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('${cheque.dishonor.register}');"
						class="btn btn-primary hidden-print" type="button" title="Print">
						<i class="fa fa-print"></i> <spring:message code="account.budgetestimationpreparation.print" text="Print" />
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='AccountCollectionReports.html'" title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>
			</form>
		</div>
	</div>
</div>

