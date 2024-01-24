<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />


<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<!-- Start Content here -->
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="register.movable.property" text="Register Of Movable Property " />
				</h2>
			</div>
			<div class="widget-content padding">
				<form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<div class="form-group">
							<div
								class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
								<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
					<h3 class="text-extra-large margin-bottom-0 margin-top-0">${ userSession.getCurrent().organisation.ONlsOrgname}</h3>
					</c:if>
					<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
					<h3 class="text-extra-large margin-bottom-0 margin-top-0">${ userSession.getCurrent().organisation.oNlsOrgnameMar}</h3>
					</c:if>
								<p>
									<spring:message code="register.movable.property" text="Register Of Movable Property " />
								</p>
							</div>
							<div class="col-sm-2 col-xs-3">
								<p>
									<spring:message code="asset.report.date"
										text="" />:
									<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
									<br><spring:message code="asset.report.time"
										 />:
									<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</div>
						</div>
						<input type="hidden" value="${validationError}" id="errorId">
						<div class="clearfix padding-10"></div>
						<div class="overflow-visible">
							<div id="export-excel">
							
								<table class="table table-bordered table-condensed">
									<thead>
									<tr>
										<th><spring:message code="report.sr.no" text="Sr. No." /></th>
										<th><spring:message code="movable.identi.number" text="Asset Identification Number" /></th>
										<th><spring:message code="movable.description.property" text="Perticular and description of the Property"/></th>
										<th><spring:message code="movable.loc.property" text="location of the property"/></th>
										<th><spring:message code="movable.number.quantity"  text="Number or quantity" /></th>
										<th><spring:message code="movable.date.of.acquisition" text="Date of acquisition/ constraction/ improvement"/></th>
										<th><spring:message code="movable.mode.acquisition" text="Mode of acquisition" /></th>
										<th><spring:message code="movable.payment.order.no" text="Payment order no." /></th>
										<th><spring:message code="movable.ref.no.cash" text="Ref. no of cash book/  journal book/ ledger where entry is recorded" /></th>
										<th><spring:message code="movable.cost.of.acquisition" text="Cost of acquisition/ construction/ improvement(Rs) " /></th>
										<th><spring:message code="movable.to.whom.paid" text="To whom paid " /></th>
										<th><spring:message code="movable.purpose.of.expenditure" text="Purpose of expenditure" /></th>
										<th><spring:message code="movable.source.of.fund" text="Source of fund" /></th>
										<th><spring:message code="report.opening.written.down" text="Opening Written Down
											Value (Rs.) (equal to column 6 in first year)" /></th>
										<th><spring:message code="report.year.of.depreciation" text="Year of Depreciation" /></th>
										<th><spring:message code="report.depreciation.provided" text="Depreciation Provided (Rs.)" /></th>
										<th><spring:message code="report.closing.written.down.value" text="Closing Written Down Value (Rs.)" /></th>
										<th><spring:message code="report.date.of.disposal" text="Date of Disposal" /></th>
										<th><spring:message code="movable.to.whome.disposal" text="To whom disposal and nature of disposal" /></th>
										<th><spring:message code="movable.no.date.of.disposal" text="No. and date of disposal order " /></th>
										<th><spring:message code="movable.number.quantity.disposed" text="Number and quantity of disposed " /></th>
										<th><spring:message code="movable.amount.sold" text="Amount realised if sold, & date of cradit in treasury or bank" /></th>
										<th><spring:message code="movable.balance.quantity" text="Balance  quantity" /></th>
										<th><spring:message code="movable.security.deposit" text="Security deposit retained" /></th>
										<th><spring:message code="movable.date.and.amount" text="Date and amount of security deposit released" /></th>
										<th><spring:message code="report.initial.authorised.officer" text="Initial of the Authorised Officer" /></th>
										<th><spring:message code="report.remarks" text="Remarks" /></th>
									</tr>
									</thead>

									<tbody>
									<c:forEach items="${reportDetailsListDTO}" var="reportDetailsList" varStatus="index">
									<tr>
									            <td align="center">${index.count}</td>
									            <td align="center">${reportDetailsList.assetCode}</td>
												<td align="center">${reportDetailsList.assetDesc}</td>
												<td align="center">${reportDetailsList.assetLocationDesc}</td>
												<td align="center">${reportDetailsList.quantityDisposed}</td>
												<td align="center">${reportDetailsList.dateOfacquisitions}</td>
												<td align="center">${reportDetailsList.modeOfAcquisitionDesc}</td>
												<td align="center">${reportDetailsList.payOrderNo}</td>
												<td align="center">${reportDetailsList.refCashBook}</td>
												<td align="right">${reportDetailsList.costOfAcquisition}</td>
												<td align="center">${reportDetailsList.paidPersonNameDesc}</td>
												<td align="center">${reportDetailsList.expensePurpose}</td>
												<td align="center">${reportDetailsList.fundSource}</td>
												<td align="right">${reportDetailsList.openWrittenValue}</td>
												<td align="center">${reportDetailsList.depreciationYearDesc}</td>
												<td align="right">${reportDetailsList.depreciation}</td>
												<td align="right">${reportDetailsList.closeWrittenValue}</td>
												<td align="center">${reportDetailsList.dateOfdisposals}</td>
												<td align="center">${reportDetailsList.disposedPersonNameDesc}</td>
												<td align="center">${reportDetailsList.dateOfdisposals}</td>
												<td align="center">${reportDetailsList.noOfDisposalOrder}</td>
												<td align="center">${reportDetailsList.saleValueRealised}</td>
												<td align="right">${reportDetailsList.balanceQuantity}</td>
												<td align="center">${reportDetailsList.releasedSecurityDeposit}</td>
												<td align="center">${reportDetailsList.autOfficerDesc}</td>
												<td></td>
												<td></td>
												
											</tr>
										</c:forEach>
									</tbody>
									
								</table>
							</div>
						</div>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="PrintDivDefault('Register Of Movable');"
							class="btn btn-success hidden-print" type="button">
							<i class="fa fa-print"></i> <spring:message code="asset.report.print"/>
						</button>
						<apptags:backButton url="AssetDetailsReport.html"></apptags:backButton>
					</div>

				</form>
			</div>
		</div>
	</div>
</div>