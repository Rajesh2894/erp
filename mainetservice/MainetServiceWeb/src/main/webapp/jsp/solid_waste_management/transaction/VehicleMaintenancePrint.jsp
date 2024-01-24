<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript"
	src="js/solid_waste_management/VehicleMaintenance.js"></script>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
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

<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>VEHICLE MAINTENANCE</h2>
		</div>
		<!-- Start JSP Necessary Tags -->

		<div class="content animated slideInDown">
			<!-- Start info box -->
			<div class="widget">
				<div class="widget-content padding">
					<fmt:formatDate value="${vehicleMaintenanceDTO.vemDate}"
						var="vemDateString" pattern="dd/MM/yyyy" />
					<fmt:formatDate value="${vehicleMaintenanceDTO.vemReceiptdate}"
						var="vemReceiptDateString" pattern="dd/MM/yyyy" />
					<form:form action="VehicleMaintenance.html"
						name="VehicleMaintenance" id="VehicleMaintenance"
						class="form-horizontal">
						<div id="receipt">

							<div class="col-xs-2">
								<h1>
									<img width="80" src="${userSession.orgLogoPath}">
								</h1>
							</div>
							<div class="col-xs-7 col-xs-7  text-center">
								<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
									${ userSession.getCurrent().organisation.ONlsOrgname} <br>

								</h2>
								<p>
									<spring:message code="vehicle.maintenance.heading"
										text="Vehicle Maintenance" />
								</p>
							</div>
							<div class="col-xs-3">
								<p>
								<br>
									<spring:message code="swm.day.wise.month.report.date"
										text="Date" />
									<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
									<br>
									<spring:message code="swm.day.wise.month.report.time"
										text="Time" />
									<fmt:formatDate value="${date}" pattern="hh:mm a" />
								</p>
							</div>

							<div class="clear"></div>

							<div class="form-group">
								<label class="control-label col-sm-2 col-xs-2 " for="Census"><spring:message
										code="vehicle.maintenance.date" text="Maintenance Date" /></label>
								<div class="col-sm-4 col-xs-4">${vemDateString}</div>

								<div class="col-sm-6 col-xs-6"></div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2 col-xs-2 "> <spring:message
										code="vehicle.maintenance.regno" /></label>
								<div class="col-sm-4 col-xs-4">${vehicleMaintenanceDTO.veNo}</div>
								<label class="control-label col-sm-2 col-xs-2" for="Census">
									<spring:message code="vehicle.maintenance.master.type"
										text="Census(year)" />
								</label>
								<div class="col-sm-4 col-xs-4">
									<c:set var="baseLookupCode" value="VCH" />
									<spring:eval
										expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(vehicleMaintenanceDTO.veVetype)"
										var="lookup" />${lookup.lookUpDesc }
								</div>
							</div>
							<div class="clear"></div>
							<div class="form-group">
								<label class="control-label col-sm-2 col-xs-2" for="Census"><spring:message
										code="vehicle.maintenance.type" text="Maintenance Type" /></label>
								<div class="col-sm-4 col-xs-4">
									<c:set var="baseLookupCode" value="MNT" />
									<spring:eval
										expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(vehicleMaintenanceDTO.vemMetype)"
										var="lookup" />${lookup.lookUpDesc }
								</div>
								<label class="control-label col-sm-2 col-xs-2" for="Census"><spring:message
										code="vehicle.maintenance.date" text="Maintenance Date" /></label>
								<div class="col-sm-4 col-xs-4">${vemDateString}</div>
							</div>
							<div class="clear"></div>
							<div class="form-group">
								<label for="estimatedDowntime"
									class="col-sm-2 col-xs-2 control-label "><spring:message
										code="vehicle.maintenance.master.estimatedDowntime"
										text="Estimated Downtime" /></label>
								<div class="col-sm-4 col-xs-4">
									${vehicleMaintenanceDTO.vemDowntime}
									<spring:eval
										expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(vehicleMaintenanceDTO.vemDowntimeunit)"
										var="lookup" />${lookup.lookUpDesc }
								</div>
								<label for="estimatedDowntime"
									class="col-sm-2 col-xs-2 control-label "> <spring:message
										code="vehicle.maintenance.repair.reading"
										text="Vehicle Reading During Repair/ Maintenance" />
								</label>
								<div class="col-sm-4 col-xs-4">${vehicleMaintenanceDTO.vemReading}
								</div>
							</div>
							<div class="clear"></div>
							<div class="form-group">
								<label for="vemReceiptdate"
									class="col-sm-2 col-xs-2 control-label "> <spring:message
										code="vehicle.maintenance.receiptDate" text="Invoice Date" />
								</label>
								<div class="col-sm-4 col-xs-4">${vemReceiptDateString}</div>
								<label for="estimatedDowntime"
									class="col-sm-2 col-xs-2 control-label "> <spring:message
										code="vehicle.maintenance.receiptno" text="Invoice No." />
								</label>
								<div class="col-sm-4 col-xs-4">
									${vehicleMaintenanceDTO.vemReceiptno}</div>
							</div>
							<div class="clear"></div>
							<div class="form-group">
								<label for="estimatedDowntime"
									class="col-sm-2 col-xs-2 control-label "> <spring:message
										code="VehicleMaintenanceDTO.vemCostincurred"
										text="Total Cost Incurred" />
								</label>
								<div class="col-sm-4 col-xs-4">
									${vehicleMaintenanceDTO.vemCostincurred}</div>

								<label for="vemReceiptdate"
									class="col-sm-2 col-xs-2 control-label "> <spring:message
										code="vehicle.maintenance.remark" text="Remark" />
								</label>
								<div class="col-sm-4 col-xs-4">
									${vehicleMaintenanceDTO.remark}</div>
							</div>
							<div class="clear"></div>
							<div class="form-group">
								<label for="vemReceiptdate"
									class="col-sm-2 col-xs-2 control-label "> <spring:message
										code="vehicle.maintenance.expendiureHead"
										text="Expendiure Head" />
								</label>
								<div class="col-sm-4 col-xs-4">
									${vehicleMaintenanceDTO.vemExpHead}</div>
							</div>
						</div>
						<div class="text-center padding-top-10">
							<button onclick="PrintDiv('Vehicle Maintenance Print');"
								class="btn btn-success hidden-print" type="button">
								<i class="fa fa-print"></i>
								<spring:message code="solid.waste.print" text="Print" />
							</button>
							<button type="button" class="button-input btn btn-danger"
								name="button-Cancel" value="Cancel" style=""
								onclick="backVehicleMaintenanceForm();" id="button-Cancel">
								<spring:message code="solid.waste.back" text="Back" />
							</button>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>