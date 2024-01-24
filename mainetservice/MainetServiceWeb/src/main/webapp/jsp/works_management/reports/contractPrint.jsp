<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script type="text/javascript"
	src="js/works_management/reports/contractPrint.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<style>
@media print {
	.print-before {
		page-break-after: always;
	}
}
</style>
<div class="content">
	<div class="widget">
		<div class="widget-content padding">
			<div id="export-excel">
				<div class="row">
					<div class="col-xs-3">
						<img src="${userSession.orgLogoPath}" width="80">
					</div>
					<div class="col-xs-6 text-center">
						<c:if test="${command.langId eq 1 }">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">${ userSession.getCurrent().organisation.ONlsOrgname}</h3></c:if>
						<c:if test="${command.langId ne 1 }">
						-	<h3 class="text-extra-large margin-bottom-0 margin-top-0">${ userSession.getCurrent().organisation.ONlsOrgnameMar}</h3></c:if>	
						<p class="excel-title">
						<h4>
							<strong><spring:message code="agreement.content1"
									text="" /></strong>
						</h4>
					</div>
				</div>
				<div>
					<div class="print-before">
						<div class="form-group">

							<div class="col-sm-6 col-xs-6 text-left">
								<p>
									<spring:message code="agreement.content2" text="" />${command.contractAgreementSummaryDTO.contNo}
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</p>
							</div>
							<div class="col-sm-6 col-xs-6 text-right">
								<p>
									<spring:message code="agreement.content18"
										text="Agreement Date" />
									&nbsp;${command.contractAgreementSummaryDTO.contDate}
								</p>
							</div>
						</div>
						<br>
						<div class="col-sm-12">

							<h4>
								<spring:message code="contract.content1" text="" />
								&nbsp;&nbsp;${command.contractAgreementSummaryDTO.contDate}
								<spring:message code="contract.content2" text="" />
								&nbsp;&nbsp;${command.tenderWorkDto.vendorName}
								<spring:message code="contract.content3" text="" />
								<c:if test="${command.langId eq 1 }">
								&nbsp;&nbsp;${ userSession.getCurrent().organisation.ONlsOrgname}</c:if>
								<c:if test="${command.langId ne 1 }">
								&nbsp;&nbsp;${ userSession.getCurrent().organisation.ONlsOrgnameMar}</c:if>
								<spring:message code="contract.content4" text="" />
								&nbsp;&nbsp;${command.contractAgreementSummaryDTO.contDate}
								<spring:message code="contract.content5" text="" />
								&nbsp;&nbsp;${command.tenderWorkDto.workName}
								<spring:message code="contract.content6" text="" />
							</h4>




							<h4>
								<spring:message code="contract.content7" text="" />
							</h4>
							<br>
							<h4>
								<spring:message code="contract.content8" text="" />
							</h4>


							<h4 class="text-center">
								<spring:message code="contract.content9" text="" />
							</h4>

							<p>
							<h4>
								<spring:message code="contract.content10" text="" />
							</h4>
							<br>
							<p>
							<h4>
								<spring:message code="contract.content11" text="" />
							</h4>
							<br>
							<p>
							<h4>
								<spring:message code="contract.content12" text="" />
							</h4>
							<br>
							<p>
							<h4>
								<spring:message code="contract.content13" text="" />
							</h4>
							<br>
							<p>
							<h4>
								<spring:message code="contract.content14" text="" />
							</h4>
							<p>
							<h4>
								<spring:message code="contract.content15" text="" />
							</h4>
							<p>
							<h4>
								<spring:message code="contract.content16" text="" />
							</h4>
							<p>
							<h4>
								<spring:message code="contract.content17" text="" />
							</h4>
							<br>
							<p>
							<h4>
								<spring:message code="contract.content18" text="" />
							</h4>
							<br>
							<p>
							<h4>
								<spring:message code="contract.content19" text="" />
							</h4>
							<br>
							<p>
							<h4>
								<spring:message code="contract.content20" text="" />
							</h4>
							<br>
							<p>
							<h4>
								<spring:message code="contract.content21" text="" />
							</h4>
							<br>

						</div>
					</div>
					<div>
						<div class="clearfix padding-10"></div>

						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label "><spring:message
									code="work.def.workname" /></label>
							<div class="col-sm-4 col-xs-4">${command.tenderWorkDto.workName}</div>

							<label class="col-sm-2 col-xs-2 control-label "><spring:message
									code="work.end.date" /></label>
							<div class="col-sm-4 col-xs-4">${command.tenderWorkDto.workEndDate}</div>

						</div>

						<div class="container">
							<c:set var="d" value="0" scope="page"></c:set>
							<table class="table table-bordered  table-fixed" id="calculation">
								<caption class="text-center">
									<spring:message code="contract.content28" />
								</caption>
								<thead>
									<tr>
										<th scope="col" width="8%" align="center"><spring:message
												code="sor.iteamno" /></th>
										<th scope="col" width="52%" align="center"><spring:message
												code="work.estimate.report.description.item" /></th>
										<th scope="col" width="8%" align="center"><spring:message
												code="work.estimate.quantity" /></th>
										<th scope="col" width="8%" align="center"><spring:message
												code="work.management.unit" /></th>
										<th scope="col" width="12%" align="center"><spring:message
												code="work.estimate.rate" /></th>
										<th scope="col" width="12%" align="center"><spring:message
												code="work.estimate.report.amount" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.workMasterDtosList}"
										var="workEstimateList" varStatus="status">
										<tr class="totalCalculation">
											<td id="sordItemNo${d}">${workEstimateList.sorDIteamNo}</td>
											<td id="sorDesc${d}">${workEstimateList.sorDDescription}</td>
											<td id="workQuantity${d}" align="right">${workEstimateList.workEstimQuantity}</td>
											<td id="sorUnit${d}" align="center ">${workEstimateList.sorIteamUnitDesc}</td>
											<td id="rate${d}" align="right">${workEstimateList.rate}</td>
											<td id="amount${d}" align="right">${command.contractAgreementSummaryDTO.contAmount}</td>
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:forEach>

									<tr>
										<th colspan="5" class="text-right"><spring:message
												code="work.estimate.report.amount.total" /></th>
										<th colspan="1" class="text-right" id="totalEstimate">${command.contractAgreementSummaryDTO.contAmount}</th>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="clear"></div>
					<div class="margin-top-30">
						<div class="col-sm-12 col-xs-5">

							<h4>
								<spring:message code="agreement.content14" text="" />
							</h4>
							<h4>
								<spring:message code="agreement.content15" text="" />
							</h4>

							<h4>
								<spring:message code="agreement.content16" text="" />
							</h4>
						</div>
						<div class="clear"></div>
						<div class="col-sm-4 col-xs-5">
							<h4>
								<spring:message code="agreement.content15" text="" />
							</h4>
							<h4>
								<spring:message code="agreement.content16" text="" />
							</h4>

						</div>



						<div class="col-sm-4 col-xs-4">

							<h4>
								<spring:message code="agreement.content17" text="" />
							</h4>
							<c:if test="${command.langId eq 1 }">
							<h4>${ userSession.getCurrent().organisation.ONlsOrgname}</h4></c:if>
							<c:if test="${command.langId ne 1 }">
							<h4>${ userSession.getCurrent().organisation.ONlsOrgnameMar}</c:if>

						</div>
						<div class="col-sm-4 col-xs-3">

							<h4>${command.tenderWorkDto.vendorName}</h4>
							<h4>
								<spring:message code="contract.print.vendor.name"
									text="Vendor Name" />
							</h4>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
