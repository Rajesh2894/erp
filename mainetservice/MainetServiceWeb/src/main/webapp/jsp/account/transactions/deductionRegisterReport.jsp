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
<script src="js/account/deductionRegister.js"></script>
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
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="accounts.deduction.register.head"
					text="DEDUCTION REGISTER" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form method="POST" action="DeductionRegister.html"
				cssClass="form-horizontal" name="" id="" modelAttribute="">
				<div id="receipt">
				   <div class="deductionReport">
					<!-- <div class="form-group"> -->
						<div class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-2 text-center">
							<h2 class="text-extra-large margin-bottom-0 margin-top-0"><c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
                                </c:if>
                                <c:if test="${userSession.languageId eq 2}">
                                      ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
                                </c:if>    
                             </h2>
							<p>
								<strong><spring:message code="accounts.deduction.register.header" text="Deduction Register" />
								<br/>
								<spring:message code="accounts.deduction.register.tdstype" text="TDS Type" /></strong>: ${command.paymentEntryDto.tdsTypeDesc}<br>
								
								<strong><spring:message code="accounts.deduction.register.fromdate" text="From Date" /></strong>: ${command.paymentEntryDto.fromDate}
								<strong><spring:message code="accounts.deduction.register.todate" text="To Date" /></strong>: ${command.paymentEntryDto.toDate}
							</p>
						</div>
						<div class="col-sm-2">
							<p>
								<spring:message code="accounts.date" text="Date:" />
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
							</p>
							<p>
								<spring:message
										code="accounts.financial.audit.trail.time" text="Time" /> :
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
					<!-- </div> -->
					<div class="clear"></div>
					<div class="table-responsive margin-top-10">
						<div id="export-excel">
							<table class="table table-bordered table-condensed" id="importexcel">
								<div id="tlExcel" style="display: none">
									<spring:message code="accounts.deduction.register.head"
										text="DEDUCTION REGISTER" />
								</div>
								<thead>
									<tr>
										<th data-sorter="false"><spring:message
												code="accounts.deduction.register.srno" text="Sr.No." /></th>
										<th><spring:message
												code="accounts.deduction.register.deductee"
												text="Name of Deductee" /></th>
										<th width="13%"><spring:message
												code="accounts.deduction.register.bill.amount"
												text="Bill Amount Paid" /></th>
										<th width="13%"><spring:message
												code="accounts.deduction.register.tds.amount"
												text="TDS Amount" /></th>
										<th width="13%"><spring:message
												code="accounts.deduction.register.tds.paid" text="TDS Paid" /></th>
										<th><spring:message
												code="accounts.deduction.register.payment.no"
												text="Payment No." /></th>
										<th><spring:message
												code="accounts.deduction.register.tds.paymentDate"
												text="Payment Date" /></th>
										<th width="13%"><spring:message
												code="accounts.deduction.register.tds.Balance.Amount"
												text="Balance Amount" /></th>
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
									<c:forEach items="${command.paymentEntryDto.listOfdedRegister}"
										var="deductionRegisterList" varStatus="status">
										<tr>
											<td class="text-center">${status.count}</td>
											<td> ${deductionRegisterList.vendorName}</td>
											<td class="text-right">${deductionRegisterList.billAmountStr}</td>
											<td class="text-right">${deductionRegisterList.tdsAmt}</td>
											<td class="text-right">${deductionRegisterList.tdsPaidAmt}</td>
											<td class="text-center">${deductionRegisterList.paymentNo}</td>
											<td class="text-center">${deductionRegisterList.paymentEntryDate}</td>
											<td class="text-right">${deductionRegisterList.balanceAmt}</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot class="avoid-sort">	
									<tr>
										<th colspan="2" class="text-right"><spring:message
												code="accounts.deduction.register.tds.total" text="Total" /></th>
										<th class="text-right">${command.paymentEntryDto.totalBillAmountStr}</th>
										<th class="text-right">${command.paymentEntryDto.totalTdsAmt}</th>
										<th class="text-right">${command.paymentEntryDto.totalTdsPaidAmt}</th>
										<th colspan="2"></th>
										<th class="text-right">${command.paymentEntryDto.totalBalAmt}</th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				  </div>
				</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('receipt');"
						class="btn btn-primary hidden-print" data-toggle="tooltip" data-original-title="Print">
						<i class="fa fa-print"></i>
						<spring:message code="account.budgetestimationpreparation.print"
							text="Print" />
					</button>
					<button type="button" class="btn btn-danger"
						onclick="backAbstractForm();"  data-toggle="tooltip" data-original-title="Back">
						<i class="fa fa-chevron-circle-left" aria-hidden="true"></i>
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>

				</div>
			</form:form>
		</div>
	</div>
</div>


