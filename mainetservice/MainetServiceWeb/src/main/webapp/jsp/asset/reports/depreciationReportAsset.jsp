<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css">
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/asset/report/depreciationReportAsset.js"></script>

<jsp:useBean id="date" class="java.util.Date" scope="request" />

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="asset.depreciation.report.header" />
			</h2>
			<apptags:helpDoc url="AssetSearch.htm"></apptags:helpDoc>
		</div>
		<div class="pagediv">
			<div class="widget-content padding">

				<form:form id="assetDepReport" name="assetDepReport"
					class="form-horizontal" action="#" method="post">
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
									<spring:message code="asset.depreciation.report.header" text="" />
								</p>
							</div>
							<c:set var="assetFlag"
								value="${userSession.moduleDeptCode == 'AST' ? true : false}" />
							<div class="col-sm-2 col-xs-3">
								<p>
									<spring:message code="asset.report.date" text="" />
									:
									<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
									<br>
									<spring:message code="asset.report.time" />
									:
									<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</div>
						</div>
						<div class="compalint-error-div">
							<jsp:include page="/jsp/tiles/validationerror.jsp" />
							<div
								class="warning-div error-div alert alert-danger alert-dismissible"
								id="errorDiv"></div>
						</div>

						<table class="table table-bordered table-condensed">
							<c:choose>

								<c:when test="${assetFlag}">
									<tr>
										<th style="text-align: left"><spring:message
												code="asset.information.assetsrno" text="" /></th>
										<td width="20%" align="left">${command.depReportDTO.serialNo}</td>
										<th style="text-align: left"><spring:message
												code="asset.information.assetname" text="" /></th>
										<td align="left">${command.depReportDTO.assetName}</td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr>
										<th style="text-align: left"><spring:message
												code="asset.information.registerSerialNo" text="" /></th>
										<td width="20%" align="left">${command.depReportDTO.serialNo}</td>
										<th style="text-align: left"><spring:message
												code="asset.information.hardwareName" text="" /></th>
										<td align="left">${command.depReportDTO.assetClass2Desc}</td>
									</tr>
								</c:otherwise>
							</c:choose>
							<c:if test="${assetFlag}">
								<tr>
									<th style="text-align: left"><spring:message
											code="asset.search.assetClassification" text="" /></th>
									<td align="left">${command.depReportDTO.assetClass1Desc}</td>
									<th style="text-align: left"><spring:message
											code="asset.search.assetclass" text="" /></th>
									<td align="left">${command.depReportDTO.assetClass2Desc}</td>
								</tr>
							</c:if>
							<tr>
								<th style="text-align: left"><spring:message
										code="asset.information.assetdetails" text="" /></th>
								<td align="left">${command.depReportDTO.details}</td>
								<c:choose>

									<c:when test="${assetFlag}">
										<th style="text-align: left"><spring:message
												code="asset.acquisition.date" text="" /></th>
										<td width="20%" align="left"><fmt:formatDate
												value="${command.depReportDTO.dateOfAcquisition}"
												pattern="dd/MM/yyyy" /></td>
									</c:when>
									<c:otherwise>
										<th style="text-align: left"><spring:message
												code="asset.acquisition.dateOfPurchase" text="" /></th>
										<td width="20%" align="left"><fmt:formatDate
												value="${command.depReportDTO.dateOfAcquisition}"
												pattern="dd/MM/yyyy" /></td>
									</c:otherwise>
								</c:choose>

							</tr>
							<tr>
								<c:choose>
									<c:when test="${assetFlag}">
										<th style="text-align: left"><spring:message
												code="asset.purchaser.cost" text="" /></th>
										<td align="left">${command.depReportDTO.costOfAcquisition}</td>
									</c:when>
									<c:otherwise>
										<th style="text-align: left"><spring:message
												code="asset.purchaser.purchaseValue" text="" /></th>
										<td align="left">${command.depReportDTO.costOfAcquisition}</td>
									</c:otherwise>
								</c:choose>

								<th style="text-align: left"><spring:message
										code="asset.purchaser.bookvalue" text="" /></th>
								<td align="left">${command.depReportDTO.purchaseBookValue}</td>
							</tr>
						</table>
						<div class="table-responsive clear">
							<table
								class="table table-striped table-bordered margin-bottom-20"
								id="assetDepReportPage">
								<thead>
									<tr>
										<th width="10%" scope="col" align="center"><spring:message
												code="asset.depreciation.financial.year" /></th>
										<th width="10%" scope="col" align="center"><spring:message
												code="asset.depreciation.bookValue.start" /></th>
										<th width="5%" scope="col" align="center"><spring:message
												code="asset.depreciation.depreciation.Expense" /></th>
										<th width="10%" scope="col" align="center"><spring:message
												code="asset.depreciation.accumulated.depreciation" /></th>
										<th width="10%" scope="col" align="center"><spring:message
												code="asset.depreciation.bookValue.end" /></th>
										<th width="10%" scope="col" align="center"><spring:message
												code="asset.depreciation.depreciation.type" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.reportDTOList}" var="reportDTO"
										varStatus="count">
										<tr>
											<td align="center">${reportDTO.bookFinYearDesc}</td>
											<td align="right">${reportDTO.bookValue}</td>
											<td align="right">${reportDTO.deprValue}</td>
											<td align="right">${reportDTO.accumDeprValue}</td>
											<td align="right">${reportDTO.endBookValue}</td>
											<td align="center">${reportDTO.changeType}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div class="text-center">
						<button
							onClick="printdiv('<spring:message code="asset.depreciation.report.header" />');"
							class="btn btn-primary hidden-print">
							<i class="fa fa-print"></i>
							<spring:message code="work.estimate.report.print" />
						</button>
						<button type="button" class="btn btn-danger" id="back"
							onclick="backToSearch('${userSession.moduleDeptCode == 'AST' ? 'AST':'IAST'}');">
							<spring:message code="back.msg" text="Back" />
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>