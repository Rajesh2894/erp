
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/asset/report/assetSearchReport.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
	<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script>

	$(function() {
		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
		});
	});
	$(function() {

		$(".table").tablesorter({

			cssInfoBlock : "avoid-sort",

		});

	});
</script>
<div class="widget-content padding">
	<form action="" method="get" class="form-horizontal">
		<div id="receipt">
			<div class="form-group">
				<div class="col-xs-8 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
				<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
					<h3 class="text-extra-large margin-bottom-0 margin-top-0">${ userSession.getCurrent().organisation.ONlsOrgname}</h3>
					</c:if>
					<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
					<h3 class="text-extra-large margin-bottom-0 margin-top-0">${ userSession.getCurrent().organisation.oNlsOrgnameMar}</h3>
					</c:if>
					<strong><spring:message code="asset.detail.report" text="Asset Detail Report" /></strong>
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
						<tr class="filterHeader">
                  			<th><spring:message code="asset.search.criteria" text="Search Criteria" /></th>
                  			<td colspan="9">${reportDto.astGroupDes} ${reportDto.astTypeDesc} ${reportDto.astClass} ${reportDto.astClassification}</td>
                		</tr>
							<tr>
							<th class="text-center"><spring:message code="asset.report.assetcode" text="Asset Code" /></th>
								<th class="text-center"><spring:message code="asset.report.serialno" text="Serial No" /></th>
								<th class="text-center"><spring:message code="asset.name" text="Asset Name" /></th>
								<%-- <th class="text-center"><spring:message code="asset.group" text="Asset Group" /></th>
								<th class="text-center"><spring:message code="asset.type" text="Asset Type" />
								</th> --%>
								<%-- <th class="text-center"><spring:message code="asset.classification"
										text="Asset Classification" /></th> --%>
								<th class="text-center"><spring:message code="asset.class" text="Asset Class" /></th>
								<th class="text-center"><spring:message code="asset.acquisition.date"
										text="Asset Acquisition Date" /></th>
								<th class="text-center"><spring:message code="asset.life.in.year"
										text="Asset Life In Year" /></th>
								<th class="text-center"><spring:message code="current.value"
										text="Current Value" /></th>
							<th class="text-center"><spring:message code="asset.purchaser.cost"/></th>
							</tr>
						</thead>
						<tfoot>
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
										<option selected="selected" value="10" class="form-control">10</option>
										<option value="20">20</option>
										<option value="30">30</option>
										<option value="all">All Records</option>
								</select> <select class="pagenum input-mini form-control"
									title="Select page number"></select>
								</th>
							</tr>
						</tfoot>
						<tbody>
							<c:forEach items="${reportDto.list}" var="viewData">
								<tr>
								    <td align="center">${viewData.assetCode}</td>
									<td align="center">${viewData.serialNo}</td>
									<td align="left">${viewData.astName}</td>
								<%-- 	<td align="center">${viewData.astGroupDes}</td>
									<td align="center">${viewData.astTypeDesc}</td> --%>
									<%-- <td align="center">${viewData.astClassification}</td> --%>
									<td align="center">${viewData.astClass}</td>
									<td align="center"><fmt:formatDate value="${viewData.dateOfacquisition}"
														pattern="dd/MM/yyyy" /></td>
									<td align="center">${viewData.lifeYear}</td>
									<td align="right">${viewData.currentvalue}</td>
									<td align="right">${viewData.costOfacqui}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</div>
					</div>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="PrintDivDefault('Asset Detail Report');"
							class="btn btn-success hidden-print" type="button">
							<i class="fa fa-print"></i>  <spring:message code="asset.report.print"/>
						</button>
						<apptags:backButton url="AssetDetailsReport.html"></apptags:backButton>
					</div>
				
	</form>
</div>
