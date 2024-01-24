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
<style>

.col-md-1{
	margin-left:50px;
}

</style>
<div id="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message
					code="accounts.outstanding.bill.register.account.head"
					text="Statement of Outstanding Liability for Expenses" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
				  <div class="padding-20">
					<div class="form-group">
							<div
								class="col-xs-12 col-sm-12 col-md-8 col-md-offset-2 text-center">
								<h5 class="text-extra-large margin-bottom-0 margin-top-0">
									<c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
									</c:if>
								</h5>
								<h6>
									<spring:message code="statement.of.Outstanding.Liability.for.Expenses"
										text="Statement of Outstanding Liability for Expenses" />
									<spring:message code="accounts.outstanding.bill.register.ason"
										text="AS ON" />
									<spring:message code="acc.date"
										text="Date" /> : ${OutStandingBilldata.fromDate}
								</h6>
							</div>
							<div class="col-sm-12 col-xs-12 col-md-2 text-center">
							<p>
								<strong><spring:message code="acc.date" text="Date" /></strong> :
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
							</p>
							<p>
								<strong><spring:message
										code="accounts.financial.audit.trail.time" text="Time" /></strong> :
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
								
							</p>
						</div>
						<div class="col-xs-10 col-sm-10 text-left">
							<p class="margin-left-50">
								<h6><spring:message
										code="accounts.outstanding.bill.register.department"
										text="Name of Department : "/> ${OutStandingBilldata.department}</h6>
							</p>
						</div>
						<div class="col-sm-2 col-xs-2 col-md-1 text-right">
							<p class="">
							<spring:message
										code="form.gen"
										text="Form GEN-28" /> 
							</p>
						</div>
						<div class="col-xs-10 col-sm-10 text-left">
							<p class="margin-left-50">
								<h6><spring:message
										code="accounts.outstanding.bill.register.accountHead"
										text="Account Head : "/> ${accountHead}</h6>
							</p>
						</div>
					</div>
					<div class="clearfix"></div>
					<!-- <div class="padding-5 clear">&nbsp;</div> -->
					<div class="table-responsive">
						<div id="export-excel">
							<table class="table table-bordered table-condensed" id="importexcel">
								<div id="tlExcel" style="display: none">
									<spring:message
										code="accounts.outstanding.bill.register.account.head"
										text="Statement of Outstanding Liability for Expenses" />
								</div>
								<thead>
									<tr>
										<th data-sorter="false"><spring:message
												code="bill.no"
												text="Invoice/Bill No." /></th>
										<th><spring:message
												code="accounts.outstanding.bill.register.supplier"
												text="Name of the Supplier/Contractor" /></th>
										<th><spring:message
												code="accounts.outstanding.bill.register.payable"
												text="Nature of Payable" /></th>
										<th><spring:message
												code="accounts.outstanding.bill.register.account"
												text="Code of Account" /></th>
										<th><spring:message
												code="accounts.outstanding.bill.register.dateof.bill"
												text="Date of Bill" /></th>
										<th width="10%"><spring:message
												code="accounts.outstanding.bill.register.bill.amount"
												text="Bill Amount(Rs.)" /></th>
										<th><spring:message
												code="accounts.outstanding.bill.register.grant"
												text="In respect of Grant/Special Fund" /></th>
										<th width="10%"><spring:message
												code="accounts.outstanding.bill.register.remarks"
												text="Remarks" /></th>
									</tr>
								</thead>
								
								<tbody>
									<c:forEach items="${OutStandingBilldata.listOfBillRegister}"
										var="billData" varStatus="status">
										<tr>
											<td class="text-center">${billData.billNo}</td>
											<td>${billData.vendorName}</td>
											<td>${billData.narration}</td>
											<td>${billData.accountHead}</td>
											<td>${billData.billEntryDate}</td>
											<td class="text-right">${billData.billtoatalAmt}</td>
											<td class="text-right">${billData.grantFund}</td>
											<td></td>
										</tr>
									</c:forEach>
									
								</tbody>
								<tfoot>
									<tr>
										<th colspan="5" class="text-right"><spring:message
												code="accounts.outstanding.bill.register.total" text="TOTAL" /></th>
										<th class="text-right">
											<strong>${OutStandingBilldata.actualAmountStr}</strong></th>
										<th></th>
										<th></thd>
									</tr>
									<tr>
										<td colspan="8" style="line-height: 50px;"><strong><spring:message
													code="accounts.outstanding.bill.register.amount"
													text="Amount(in Words) Rs:" /> </strong>${OutStandingBilldata.amountInWords}</td>
									</tr>
									
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
							</table>
						</div>
					</div>
					<div class="row">

						<div class="col-sm-4 col-sm-offset-8">
							<p class="margin-top-10">
								<spring:message
									code="accounts.outstanding.bill.register.prepared"
									text="Prepared By**:" />
								____________________________
							</p>
							<p class="margin-top-15">
								<spring:message
									code="accounts.outstanding.bill.register.checked"
									text="Checked By**:" />
								____________________________
							</p>
						</div>
					</div>
				   </div>	
				   <style>
						@media print{
							.ts-pager{
								visibility: hidden;
							}
						}
				   </style>
				</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('${accounts.receipt.challan}');"
						class="btn btn-primary hidden-print" data-toggle="tooltip" data-original-title="Print">
						<i class="fa fa-print"></i>
						<spring:message code="account.budgetestimationpreparation.print"
							text="Print" />
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='OutstandingBillRegister.html'" data-toggle="tooltip" data-original-title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>

			</form>
		</div>
	</div>
</div>
