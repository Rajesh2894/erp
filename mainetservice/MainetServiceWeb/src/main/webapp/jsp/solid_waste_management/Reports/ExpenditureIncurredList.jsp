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
	src="js/solid_waste_management/report/expenditureIncurredReport.js"></script>

<script>
	$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
		});
		$('#expenseType').on('change', function() {
			var expenseType = $('#expenseType').val();
			if (expenseType == "Fueling") {
				$(".hideMaintenacebox").not(".m").hide();
				$(".m").hide();
				$(".p").show();
			}
			if (expenseType == "Maintenance") {
				$(".p").hide();
				$(".m").show();
			}
		});
		$(".fromDateClass").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			onSelect : function(selected) {
				$(".toDateClass").datepicker("option", "minDate", selected)
			}
		});
		$(".toDateClass").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			onSelect : function(selected) {
				$(".fromDateClass").datepicker("option", "maxDate", selected)
			}
		});
	});
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="swm.Expenditure.incurred.heading"
					text="Expenditure Incurred on Transportation" />

			</h2>
			<apptags:helpDoc url="ExpenditureIncurredOnTransportation.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form action="" method="get" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div id="a0" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label for="select-1479374267587"
										class="col-sm-2 control-label required-control"><spring:message
											code="swm.Expenditure.expense.type" text="Expense Type" /></label>
									<div class="col-sm-4">
										<form:select path=""
											cssClass="form-control  chosen-select-no-results mandColorClass"
											name="select-1479374267587" id="expenseType"
											aria-required="true">
											<form:option value="" selected="true">
												<spring:message code="solid.waste.select" text="Select" />
											</form:option>
											<option value="Fueling"><spring:message
													code="swm.Expenditure.expense.type.fueling" text="Fueling" /></option>
											<option value="Maintenance"><spring:message
													code="swm.Expenditure.expense.type.maintenance"
													text="Maintenance" /></option>
										</form:select>
									</div>
									<div class="m hideMaintenacebox" style="display: none">
										<label for="select-1479374267587"
											class="col-sm-2 control-label required-control"><spring:message
												code="swm.Expenditure.maintenance.type"
												text="Maintenance Type" /></label>
										<c:set var="baseLookupCode" value="MNT" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="vehicleMaintenanceDTO.vemMetype"
											cssClass=" form-control chosen-select-no-results  mandColorClass "
											isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />
									</div>
									<div class="p hideMaintenacebox">
										<label for="select-1479374267589"
											class="col-sm-2 control-label required-control"><spring:message
												code="swm.Expenditure.pump.name" text="Pump Name" /></label>
										<div class="col-sm-4">
											<form:select path=""
												cssClass="form-control  chosen-select-no-results mandColorClass"
												id="pumpId" aria-required="true">
												<form:option value="" selected="true">
													<spring:message code="solid.waste.selec" text="Select" />
												</form:option>
												<option value="0"><spring:message
														code="solid.waste.All" text="All" /></option>
												<c:forEach items="${command.pumpMasterList}" var="pump">
													<form:option value="${pump.puId}">${pump.puPumpname}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label for="select-1479374236312"
										class="col-sm-2 control-label required-control"><spring:message
											code="swm.Expenditure.vehicle.type" text="Vehicle Type" /></label>
									<c:set var="baseLookupCode" value="VCH" />
									<apptags:lookupField
										items="${command.getSortedLevelData(baseLookupCode)}"
										path="vehicleMasterDTO.veVetype"
										cssClass="form-control  chosen-select-no-results"
										isMandatory="true" showAll="true"
										selectOptionLabelCode="selectdropdown" hasId="true"
										hasChildLookup="false" changeHandler="showVehicleRegNo()" />
									<label for="select-1479374309063"
										class="col-sm-2 control-label required-control"><spring:message
											code="swm.Expenditure.vehicle.regno" text="Vehicle Reg. No." /></label>
									<div class="col-sm-4">
										<form:select
											class=" mandColorClass form-control chosen-select-no-results"
											path="vehicleMasterDTO.veNo" id="vemId">
											<form:option value="">
												<spring:message code="solid.waste.select" text="Select" />
											</form:option>
											<form:option value="0">
												<spring:message code="solid.waste.All" text="All" />
											</form:option>
											<c:forEach items="${command.vehicleMasterList}" var="vehicle">
												<form:option value="${vehicle.veId}">${vehicle.veNo}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<apptags:date labelCode="swm.scheduleFrom"
										fieldclass="fromDateClass"
										datePath="vehicleMasterDTO.veRentFromdate" isMandatory="true">
									</apptags:date>
									<apptags:date labelCode="swm.scheduleTo"
										fieldclass="toDateClass"
										datePath="vehicleMasterDTO.veRentTodate"
										cssClass="custDate mandColorClass" isMandatory="true">
									</apptags:date>
								</div>
							</div>
							<div class="text-center padding-top-10">
								<button type="button" class="btn btn-success btn-submit"
									onClick="expenditureIncurredReportPrint('ExpenditureIncurredOnTransportation.html','report')"
									data-original-title="View">
									<spring:message code="solid.waste.print" text="Print"></spring:message>
								</button>
								<button type="Reset" class="btn btn-warning"
									onclick="resetTrip()">
									<spring:message code="solid.waste.reset" text="Reset" />
								</button>
								<apptags:backButton url="AdminHome.html"></apptags:backButton>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>