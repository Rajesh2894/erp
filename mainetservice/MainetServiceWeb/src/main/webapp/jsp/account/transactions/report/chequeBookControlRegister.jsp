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

	$(document).ready(function() {
		$('.fancybox').fancybox();
	});
</script>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2 class="excel-title"> <spring:message
                            code="cHEQUE.BOOK.CONTROL.REGISTER" text="CHEQUE BOOK CONTROL REGISTER" /></h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
					<!-- <div class="form-group"> -->
						<div
							class="col-sm-8 col-sm-offset-2 col-xs-8 col-xs-offset-1 text-center">
						<h3 class="text-extra-large margin-bottom-0 margin-top-0">
							<strong><c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
								</c:if> <c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
								</c:if></strong>
						</h3>
						<p class="excel-title">
								
								 <spring:message
                            code="cHEQUE.BOOK.CONTROL.REGISTER" text="CHEQUE BOOK CONTROL REGISTER" />
							</p>
						</div>

						<div class="col-sm-2 col-xs-3">
							<p>
								<spring:message
                            code="account.date" text="Date" />:
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br><spring:message
                            code="account.time" text="Time" />:
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
					<div class="clear"></div>
					<h5>${reportData.accountCode}</h5>
					<table class="table table-bordered table-condensed" id="importexcel">
					<div class="excel-title" id="tlExcel" style="display: none">Cheque Book Control Register</div>
						<thead>
							<tr>
								<th colspan="5" class="text-center"><spring:message code="cHEQUE.Particulars.of.Receipts" text="Particulars of Receipts" /></th>
								<th colspan="3" class="text-center"><spring:message code="cHEQUE.Particulars.Issue" text="Particular Issue" /></th>
								<th colspan="3" class="text-center"><spring:message code="cHEQUE.Particulars.Countfoil" text="Particular of return of
									Cheque Counterfoil" /></th>
								<th rowspan="2" class="text-center"><spring:message code="cHEQUE.Remarks" text="Remarks, if any" /></th>
							</tr>
							<tr>
								<th><spring:message code="cHEQUE.Date.of.receipt" text="Date of receipt" /></th>
								<th><spring:message code="cHEQUE.Bank.name" text="Bank name" /></th>
								<th><spring:message code="cHEQUE.Branch.Name" text="Branch Name" /></th>
								<th><spring:message code="cHEQUE.Number.of.First.Leaf" text="Number of First Leaf" /></th>
								<th><spring:message code="cHEQUE.Number.of.Last.Leaf" text="Number of Last Leaf" /></th>
								<th><spring:message code="cHEQUE.Date.of.issue" text="Date of issue" /></th>
								<th><spring:message code="cHEQUE.Issued.to.whom" text="Issued to whom" /></th>
								<th><spring:message code="cHEQUE.Signature.of.recipient" text="Signature of recipient" /></th>
								<th><spring:message code="cHEQUE.Date.of.return" text="Date of return" /></th>
								<th><spring:message code="cHEQUE.Returned.by.whom" text="Returned by whom" /></th>
								<th><spring:message code="cHEQUE.leaf" text="Number and details of leaf (leaves cancelled)" /></th>
							</tr>
						</thead>
						<tfoot class="tfoot">
							<tr>
								<th colspan="12" class="ts-pager form-horizontal">
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
							<c:forEach items="${reportData.listofchequepayment}"
								var="checkbookaccount">
								<tr>
									<td>${checkbookaccount.receiptDate}</td>
									<td>${checkbookaccount.bankname}</td>
									<td>${checkbookaccount.branchName}</td>
									<td>${checkbookaccount.noFirstLeave}</td>
									<td>${checkbookaccount.nolastLeave}</td>
									<td>${checkbookaccount.doIssue}</td>
									<td>${checkbookaccount.issueToWhom}</td>
									<td>${checkbookaccount.signatureOfReceipent}</td>
									<td>${checkbookaccount.dateOfReturn}</td>
									<td>${checkbookaccount.returnToWhom}</td>
									<td>${checkbookaccount.leaveCancelled}</td>
									<td>${checkbookaccount.remarks}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('${cheque.dishonor.register}');"
						class="btn btn-primary hidden-print" type="button" title="Print">
						<i class="fa fa-print"></i> <spring:message
                            code="account.budgetestimationpreparation.print" text="Print" />
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='AccountExpensesReports.html'" title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>

			</form>
		</div>
	</div>
</div>