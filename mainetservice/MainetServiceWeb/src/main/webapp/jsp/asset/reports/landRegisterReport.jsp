<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/asset/report/landRegisterReport.js"
	type="text/javascript"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />


<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<!-- Start Content here -->
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="report.land.register"
						text="Land Registration Report" />
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
									<spring:message code="report.land.register"
										text="Land Registration Report" />
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
									<tr>
										<th style="text-align: left"><spring:message
												code=""
												text="Asset Code :" /></th>
										<td width="20%" align="left">${command.infoReport.assetIdentiNo}</td>
										<th style="text-align: left"><spring:message
												code="report.land.description.structure"
												text="Description of the Structure
											: " /></th>
										<td align="left">${command.infoReport.descriptionStru}</td>
									</tr>
									<tr>
										<th style="text-align: left"><spring:message
												code="report.register.of.land.leasehold"
												text="Specify, if leasehold/freehold : " /></th>
										<td align="left"></td>
										<th style="text-align: left"><spring:message
												code="report.land.location.structure"
												text="Location of the Structure : " /></th>
										<td align="left">${command.infoReport.locationDes}</td>
									</tr>
									<tr>
										<th style="text-align: left"><spring:message
												code="report.land.property.survey.land.on"
												text="Survey No. of the land on
											which constructed (Sq. mtr): " /></th>
										<td align="left"></td>
										<th style="text-align: left"><spring:message
												code="report.area.land.property.whichconstructed"
												text="Area of land on which
											constructed (Sq. mtr) :" /></th>
										<td width="20%" align="left">${command.infoReport.area}
											${command.infoReport.areaUnitDesc}</td>
									</tr>
									<tr>
										<th style="text-align: left"><spring:message
												code="report.land.title.document.available"
												text="Title document available :" /></th>
										<td align="left"></td>
										<th style="text-align: left"><spring:message
												code="report.land.property.mode.of.acquisition"
												text="Mode of acquisition : " /></th>
										<td align="left">${command.infoReport.acquMethodDesc}</td>
									</tr>
									<tr>
										<th style="text-align: left"><spring:message
												code="report.land.security.deposit.retained"
												text="Specify whether any building,tress, etc., acquired with land:" />
										</th>
										<td align="left"></td>
										<th style="text-align: left"><spring:message
												code="report.land.value.paid"
												text="Value paid for aquiring building, trees, etc :" /></th>
										<td align="left"></td>
									</tr>
									<tr>
										<th style="text-align: left"><spring:message
												code="report.land.property.security.deposit.retained"
												text="Security Deposit retained :" /></th>
										<td align="left"></td>
										<th style="text-align: left"><spring:message
												code="report.land.property.date.amount.security.released"
												text="Date and amount of Security
											Deposit released :" /></th>
										<td align="left"></td>
									</tr>
								</table>
								<table class="table table-bordered table-condensed">
									<thead>
									<tr>
										<th><spring:message code="report.land.property.sr.no"
												text="Sr. No." /></th>
										<th><spring:message
												code="report.land.property.date.acquisition.constrcution"
												text="Date of acquisition / Constrcution / Improvement" /></th>
										<th><spring:message
												code="report.land.property.payment.order.no"
												text="Payment Order No." /></th>
										<th><spring:message
												code="report.land.property.ref.No.cash.book.journal"
												text="Ref. No. of Cash Book Journal Book / Ledger where
											entry is recorded" /></th>
										<th><spring:message
												code="report.land.property.ref.register.land"
												text="Ref. No. of Register of Land" /></th>
										<th><spring:message
												code="report.land.property.cost.acquisition.construction.improvement"
												text="Cost of acquisition of construction / Improvement
											(Rs.) Please specify incidental cost separetely" /></th>
										<th><spring:message
												code="report.land.property.to.whom.name.contractor"
												text="To whom Paid / Name of the contractor" /></th>
										<th><spring:message
												code="report.land.property.purpose.of.expenditure"
												text="Purpose of Expenditure" /></th>
										<th><spring:message
												code="report.land.property.source.of.funds"
												text="Source of Funds" /></th>
										<th><spring:message
												code="report.land.property.in.case.of.building"
												text="In Case of Building, specify how building is being" /></th>
										<th width="10%"><spring:message
												code="report.land.property.date.of.disposal"
												text="Date of Disposal" /></th>
										<th width="10%"><spring:message
												code="report.land.property.receipt.voucher.no"
												text="Receipt Voucher No." /></th>
										<th width="10%"><spring:message
												code="report.land.property.name.person.whom.structure"
												text="Name of the Person whom Land is
											disposed" /></th>
										<th width="10%"><spring:message
												code="report.land.property.sale.value"
												text="Value Realised(Rs.)" /></th>
										<th width="10%"><spring:message
												code="report.land.property.initial.authorised.officer"
												text="Initial of the Authorised Officer" /></th>
										<th width="10%"><spring:message
												code="report.land.property.remarks" text="Remarks" /></th>
									</tr>
									</thead>

									<tbody>
										<c:forEach items="${command.reportList}" var="dtoList">
											<tr>
												<td>${dtoList.serialNo}</td>
												<td><fmt:formatDate
														value="${dtoList.dateOfacquisition}" pattern="dd/MM/yyyy" />
												</td>
												<td align="center">${dtoList.payOrderNo}</td>
												<td align="center">${dtoList.refCashBook}</td>
												<td align="center">${dtoList.refRegisterAsset}</td>
												<td align="right">${dtoList.costOfAcquisition}</td>
												<td align="center">${dtoList.paidPersonNameDesc}</td>
												<td align="center"></td>
												<td align="center">${dtoList.fundSource}</td>
												<td align="center">${dtoList.landUsageDesc}</td>
												<td align="center"><fmt:formatDate value="${dtoList.dateOfDisposal}"
														pattern="dd/MM/yyyy" /></td>
												<td align="center">${dtoList.receiptVoucherNo}</td>
												<td align="center">${dtoList.disposedPersonNameDesc}</td>
												<td align="center">${dtoList.saleValueRealised}</td>
												<td align="center">${dtoList.autOfficerDesc}</td>
												<td align="center">${dtoList.remarks}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="PrintDivDefault('Land Register');"
							class="btn btn-success hidden-print" type="button">
							<i class="fa fa-print"></i>  <spring:message code="asset.report.print"/>
						</button>
						<apptags:backButton url=""></apptags:backButton>
					</div>

				</form>
			</div>
		</div>
	</div>
</div>