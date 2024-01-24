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
	src="js/solid_waste_management/report/ehicleLogBookMainPage.js"></script>
<script type="text/javascript">
	
</script>
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<spring:message code="" text="Vehicle Log Book Main Page Report" />
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
								  Vehicle Wise Daily Waste colllection For Month - ${command.vehicleLogBookMainPageList.monthName} <br>
								<spring:message code="" text="Vehicle Log Book Main Page Report" />
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
						<div class="padding-5 clear">&nbsp;</div>
						<div id="export-excel">
							<table class="table table-bordered table-condensed">
								<thead>
									<tr>
										<th style="text-align: left" colspan="3">Vehicle No.</th>
										<td style="text-align: left" colspan="3">${command.vehicleLogBookMainPageList.vehicleNo} </td>
										<th style="text-align: left" colspan="3">Total Population
											On Beat (Count)</th>
										<td style="text-align: left" colspan="3">${command.vehicleLogBookMainPageList.toatalPopInbeatCount}</td>
									</tr>
									<tr>
										<th style="text-align: left" colspan="3">Total No Of
											House / Establishment On Beat (Count)</th>
										<td style="text-align: left" colspan="3">${command.vehicleLogBookMainPageList.toatalHouseInbeatCount}&nbsp;/&nbsp;${command.vehicleLogBookMainPageList.totalEstInbeatCount}</td>
										<th style="text-align: left" colspan="3">Total
											AnimalCount On Beat (Count)</th>
										<td style="text-align: left" colspan="3">${command.vehicleLogBookMainPageList.totalAnimalCount}</td>
									</tr>
									<tr>
										<th style="text-align: left" colspan="3">Total No Of
											Houses / Establishment Preparing Compost(Count)</th>
										<td style="text-align: left" colspan="3">${command.vehicleLogBookMainPageList.totalHouseForCompost} </td>
										<th style="text-align: left" colspan="3">Max Waste
											Capacity Of Dry Waste(cu. Feet)</th>
										<td style="text-align: left" colspan="3">${command.vehicleLogBookMainPageList.dryCapacity}</td>
									</tr>
									<tr>
										<th style="text-align: left" colspan="3">Max Waste
											Capacity Of Wet Waste(cu.Feet)</th>
										<td style="text-align: left" colspan="3">${command.vehicleLogBookMainPageList.wetCapacity}</td>
										<th style="text-align: left" colspan="3">Max Waste
											Capacity Of Hazardous waste(cu.Feet)</th>
										<td style="text-align: left" colspan="3">${command.vehicleLogBookMainPageList.hazardiusCapacity}</td>
									</tr>
									<tr>
										<th rowspan="2">Date</th>
										<th rowspan="2"><spring:message code="" text="In Time" /></th>
										<th rowspan="2"><spring:message code="" text="Out Time" /></th>
										<th colspan="6"><spring:message code=""
												text="Waste Capacity of Vehicle(In %)" /></th>
										<th rowspan="2" width="10%"><spring:message code=""
												text="Is 100%  waste Segregation Gone For MRF Center Yes or No" /></th>
										<th rowspan="2" width="10%"><spring:message code=""
												text="How Many Waste Segregation Collected From Home And Establishment" /></th>
										<th rowspan="2"><spring:message code="" text="Signature" /></th>
									</tr>
									<tr>
										<th>Dry Waste (%)</th>
										<th>Out Put Weight</th>
										<th>Wet Waste (%)</th>
										<th>Out Put Weight</th>
										<th>Hazardious Waste(%)</th>
										<th>Out Put Weight</th>
									</tr>
								</thead>
								<tfoot>
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
									<c:forEach
										items="${command.vehicleLogBookMainPageList.vehicleLogBookMainPageList}"
										var="data" varStatus="index">
										<tr>
											<td>${data.tripDate}</td>
											<td>${data.timeIn}</td>
											<td>${data.timeOut}</td>
											
											<td>${data.dry}</td>
											<td>${data.dryOutPutwait}</td>
											
											<td>${data.wet}</td>
											<td>${data.wetOutPutwait}</td>
											
											<td>${data.hazardous}</td>
											<td>${data.hazardousOutPutwait}</td>
											
											<td>${data.approved}</td>
											<td></td>
											<td></td>
										</tr>
									</c:forEach>
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