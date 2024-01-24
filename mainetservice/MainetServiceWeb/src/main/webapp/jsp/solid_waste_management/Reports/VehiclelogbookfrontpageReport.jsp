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
	src="js/solid_waste_management/report/vehicleLogBookFront.js"></script>
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
				<spring:message code="swm.vehicle.log.front.page"
					text="Vehicle log Book Front Page Report" />
			</div>
			<div class="widget-content padding">
				<form:form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<div class="col-xs-2">
							<h1>
								<img width="80" src="${userSession.orgLogoPath}">
							</h1>
						</div>
						<div class="col-xs-8 col-xs-8  text-center">
							<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
								<c:if test="${userSession.languageId eq 1}">
									 ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
         							 ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
								</c:if>
								<spring:message code="swm.vehicle.log.front.page"
									text="Vehicle log Book Front Page Report" />
							</h2>
						</div>
						<div class="col-xs-2">
							<p>
								<spring:message code="swm.day.wise.month.report.date"
									text="Date" />
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<spring:message code="swm.day.wise.month.report.time"
									text="Time" />
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
						<div class="clearfix padding-10"></div>
						<div class="form-group">
							<label for="select-1479372680758" class="col-xs-2 control-label"><spring:message
									code="swm.tripdate " text="Trip Date" /></label>
							<div class="col-xs-4">
								<p>
									<fmt:formatDate value="${command.vehicleLogBookDTO.date}"
										pattern="dd/MM/yyyy" />
								</p>
							</div>
						</div>
					
							<div class="form-group">
								<label for="select-1479365507176" class="col-xs-2 control-label"><spring:message
										code="swm.day.wise.month.mrfName" text="MRF Center Name" /></label>
								<div class="col-xs-4">
									${command.MRFMasterDto.mrfPlantName}
								</div>
							</div>
							
							<div class="form-group">
								<label for="select-1479365507176" class="col-xs-2 control-label"><spring:message
										code="MRFMasterDto.mrfPlantId" text="Plant Id" /></label>
								<div class="col-xs-4">${command.MRFMasterDto.mrfPlantId}</div>
							</div>


							<table
								class="table table-bordered table-condensed padding-top-10">
								<thead>
									<tr>
										<th class="text-center" width="10%"><spring:message
												code="population.master.srno" text="Sr.No." /></th>
										<th class="text-center" width="45%"><spring:message
												code="swm.subject" text="Subject" /></th>
										<th class="text-center" width="45%"><spring:message
												code="swm.description" text="Description" /></th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="3" class="ts-pager form-horizontal">
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
												<option value="all"><spring:message
														code="swm.report.all.records" text="All Records" />
												</option>
										</select> <select class="pagenum input-mini form-control"
											title="Select page number"></select>
										</th>
									</tr>
								</tfoot>
								<tbody>
									<tr>
										<td>1</td>
										<td class="text-left"><spring:message
												code="swm.veNo.veType" text="Vehicle Type and Vehicle No." /></td>
										<td class="text-right"><br> <c:forEach
												items="${command.vehicleNo}" var="vehicle" varStatus="loop">
												<c:if test="${userSession.languageId eq 1}">
										${command.vehicleNo[loop.index][1]}	&nbsp;&nbsp;${command.vehicleNo[loop.index][0]} <br>
												</c:if>
												<c:if test="${userSession.languageId eq 2}">
										${command.vehicleNo[loop.index][2]}	&nbsp;&nbsp;${command.vehicleNo[loop.index][0]} <br>
												</c:if>
											</c:forEach></td>
									</tr>
									<tr>
										<td>2</td>
										<td class="text-left"><spring:message
												code="swm.report.no.of.ward"
												text="No. of Wards in Serviceable Area" /></td>
										<td class="text-right">${command.wardCount[0]}</td>
									</tr>
									<tr>
									<tr>
										<td>3</td>
										<td class="text-left"><spring:message
												code="swm.report.wardNos"
												text="Ward No. Available in Serviceable Area" /></td>
										<td class="text-right">${command.vehicleLogBookDTO.wardNo}</td>
									</tr>
									<tr>
										<td>4</td>
										<td class="text-left"><spring:message
												code="swm.report.residentialCount"
												text="Ward No. Available in Serviceable Area" /></td>
										<td class="text-right">${command.vehicleLogBookDTO.residentialCount}</td>
									</tr>
									<tr>
										<td>5</td>
										<td class="text-left"><spring:message
												code="swm.report.commercialCount"
												text="No. of Commercial area in Serviceable Area" /></td>
										<td class="text-right">${command.vehicleLogBookDTO.commercialCount}</td>
									</tr>
									<tr>
										<td>6</td>
										<td class="text-left"><spring:message
												code="swm.report.commercialshopsCount"
												text="Ward No. Available in Serviceable Area" /></td>
										<td class="text-right">${command.vehicleLogBookDTO.commercialShopsCount}</td>
									</tr>
									<tr>
										<td>7</td>
										<td class="text-left"><spring:message
												code="swm.report.beatCount"
												text="No. of Beats in Serviceable Area" /></td>
										<td class="text-right">${command.beatCount[0][1]}</td>
									</tr>
								</tbody>
							</table>
					
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