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
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/report/wasteWiseSegregation.js"></script>
<script type="text/javascript">
	$(function() {
		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
		});
		$(function() {
			$(".table").tablesorter({
				cssInfoBlock : "avoid-sort",
			});

		});
	});
</script>
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="swm.waste.wise.segregation.head"
						text="Waste Wise Segregation Report" />
				</h2>
			</div>
			<div class="widget-content padding">
				<form:form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<div class="col-xs-3">
							<h1>
								<img width="80" src="${userSession.orgLogoPath}">
							</h1>
						</div>
						<div class="col-xs-6 text-center">
							<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
								<c:if test="${userSession.languageId eq 1}">
									 ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
         							 ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
								</c:if>
								<spring:message code="swm.waste.wise.segregation.head"
									text="Waste Wise Segregation Report" />
							</h2>
						</div>
						<div class="col-xs-3">
							<p>
								<spring:message code="solid.waste.date" text="Date" />
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<spring:message code="solid.waste.time" text="Time" />
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
						<div class="clearfix"></div>
						<div class="form-group text-center">
							<label for="select-1479372680758" class="control-label"><spring:message
								code="swm.fromDate" text="From Date" /></label>
							<span>
								: ${command.wastageSeg.fromDate}
							</span>
							<label for="select-1479372680758" class="control-label margin-left-10"><spring:message
								code="swm.toDate" text="To Date" /></label>
							<span>
								: ${command.wastageSeg.toDate}
							</span>
						</div>
						<div class="clearfix"></div>

						<%-- <div class="form-group">
							<label for="select-1479372680758" class="col-xs-2 control-label"><spring:message
									code="swm.fromDate" text="From Date" /></label>
							<div class="col-xs-4">
								<p class="padding-5">${command.wastageSeg.fromDate}</p>
							</div>
							<label for="select-1479372680758" class="col-xs-2 control-label"><spring:message
									code="swm.toDate" text="To Date" /></label>
							<div class="col-xs-4">
								<p class="padding-5">${command.wastageSeg.toDate}</p>
							</div>
						</div> --%>
						<!-- <div class="padding-5 clear">&nbsp;</div> -->
						<div id="export-excel">

							<c:if test="${command.wastageSeg.dName ne null}">
								<p>
									<spring:message code="swm.waste.wise.segregation.dumping.site"
										text="Dumping Site " />
									: <b>${command.wastageSeg.dName}</b>
								</p>
							</c:if>
							<table class="table table-bordered table-condensed">
								<thead>
									<tr>
										<th class="text-center"><spring:message
												code="swm.waste.wise.segregation.waste.date" text="Date" /></th>
										<th class="text-center"><spring:message
												code="swm.waste.wise.segregation.waste.type"
												text="Waste Type" /></th>
										<th class="text-center"><spring:message
												code="swm.waste.wise.segregation.waste.type1"
												text="Waste SubType1" /></th>
										<th class="text-center"><spring:message
												code="swm.waste.wise.segregation.waste.type2"
												text="Waste SubType2" /></th>
										<th class="text-center"><spring:message
												code="route.master.disposal.site"
												text="MRF Center" /></th>
										<th class="text-center"><spring:message
												code="swm.waste.wise.segregation.volume.inkg"
												text="Volume In Kg" /></th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="6" class="ts-pager form-horizontal">
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
									<c:forEach
										items="${command.wastageSegregationDto.wastageSegregationList}"
										var="data" varStatus="index">
										<tr>
											<td class="text-center">${data.fromDate}</td>
											<td>${data.waste}</td>
											<td>${data.subTypeWaste}</td>
											<td>${data.subTypeWaste2}</td>
											<td>${data.dName}</td>
											<td class="text-right">${data.volume}</td>
										</tr>
									</c:forEach>
									<tr>
										<th class="text-right" colspan="5"><spring:message
												code="swm.total" text="Total" /></th>
										<th class="text-right">${command.wastageSegregationDto.totalVolume}</th>
									</tr>
								</tbody>
							</table>
						</div>

					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="PrintDiv('${cheque.dishonor.register}');"
							class="btn btn-success hidden-print" type="button">
							<i class="fa fa-print"></i>
							<spring:message code="solid.waste.print" text="Print" />
						</button>
						<button type="button" class="btn btn-danger" onclick="back();">
							<spring:message code="solid.waste.cancel" text="Cancel" />
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>