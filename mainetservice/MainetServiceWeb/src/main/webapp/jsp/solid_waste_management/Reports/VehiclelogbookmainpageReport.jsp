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
	src="js/solid_waste_management/report/VehicleWiseCollection.js"></script>
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
				<spring:message code=""
					text="Vehicle Log Book Main Page Survey Report" />
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
								<spring:message code=""
									text="Vehicle Log Book Main Page Survey Report" />
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
						<%-- 	<div class="clearfix padding-10"></div>
						<div class="form-group">
							<label for="select-1479372680758" class="col-xs-2 control-label"><spring:message
									code="swm.fromDate" text="From Date" /></label>
							<div class="col-xs-4">
								<p>${command.vehicleScheduleDTO.fromDate}</p>
							</div>
							<label for="select-1479372680758" class="col-xs-2 control-label"><spring:message
									code="swm.toDate" text="To Date" /></label>
							<div class="col-xs-4">
								<p>${command.vehicleScheduleDTO.toDate}</p>
							</div>
						</div> --%>
						<div class="padding-5 clear">&nbsp;</div>
						<div id="export-excel">
							<div class="form-group">
								<label class="control-label col-sm-2 " for=""><spring:message
										code="" text="Vehicle No." /></label>
								<div class="col-sm-4">
									<form:input path="" class="form-control" id="" value=""
										readonly="true"></form:input>
								</div>

							</div>
							<div class="form-group">
								<label class="control-label col-sm-2 " for=""><spring:message
										code="" text="Beat Of Population" /></label>
								<div class="col-sm-4">
									<form:input path="" class="form-control" id="" value=""
										readonly="true"></form:input>
								</div>
								<label class="control-label col-sm-2 " for=""><spring:message
										code="" text="Home Count In Beat" /></label>
								<div class="col-sm-4">
									<form:input path="" class="form-control" id="" value=""
										readonly="true"></form:input>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2 " for="StartingPoint"><spring:message
										code="" text="Animal Count In Beat" /></label>
								<div class="col-sm-4">
									<form:input path="" class="form-control" id="" value=""
										readonly="true"></form:input>
								</div>
								<label class="control-label col-sm-2 " for="StartingPoint"><spring:message
										code="" text="Home/Established Count In Compost" /></label>
								<div class="col-sm-4">
									<form:input path="" class="form-control" id="" value=""
										readonly="true"></form:input>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2 " for=""><spring:message
										code="" text="Vehicle Capacity For Dry waste " /></label>
								<div class="col-sm-4">
									<form:input path="" class="form-control" id="" value=""
										readonly="true"></form:input>
								</div>
								<label class="control-label col-sm-2 " for="StartingPoint"><spring:message
										code="" text="Wet Vehicle Capacity For Wet Waste" /></label>
								<div class="col-sm-4">
									<form:input path="" class="form-control" id="" value=""
										readonly="true"></form:input>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2 " for="StartingPoint"><spring:message
										code="" text="Vehicle Capacity For Hazardous" /></label>
								<div class="col-sm-4">
									<form:input path="" class="form-control" id="" value=""
										readonly="true"></form:input>
								</div>
							</div>
							<table class="table table-bordered table-condensed">
								<thead>
									<tr>
										<th rowspan="2">Date</th>
										<th rowspan="2"><spring:message code="" text="In Time" /></th>
										<th rowspan="2"><spring:message code="" text="Out Time" /></th>
										<th colspan="3"><spring:message code=""
												text="Waste Capacity of Vehicle(In %)" /></th>
										<th rowspan="2" width="10%"><spring:message code=""
												text="Is 100%  waste Segregation Gone For MRF Center Yes or No" /></th>
										<th rowspan="2" width="10%"><spring:message code=""
												text="How Many Waste Segregation Collected From Home And Establishment" /></th>
										<th rowspan="2"><spring:message code="" text="Signature" /></th>
									</tr>
									<tr>
										<th>Dry Waste (%)</th>
										<th>Wet Waste (%)</th>
										<th>Hazardious Waste(%)</th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th colspan="9" class="ts-pager form-horizontal">
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
										<td>01/11/2018</td>
										<td>08:00</td>
										<td>12:00</td>
										<td>40</td>
										<td>30</td>
										<td>80</td>
										<td>Yes</td>
										<td>20</td>
										<td>Not Good</td>
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