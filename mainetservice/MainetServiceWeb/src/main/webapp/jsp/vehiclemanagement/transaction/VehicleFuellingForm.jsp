<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/vehicle_management/VehicleFuelling.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated slideInDown">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="vehicle.fuelling.heading"
					text="Vehicle Fueling" />
			</h2>
			<apptags:helpDoc url="vehicleFuel.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand"
						text="Field with"></spring:message><i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory"></spring:message>
				</span>
			</div>
			<form:form action="vehicleFuel.html" name="VehicleFuelling"
				id="VehicleFuelingForm" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				
				<form:input type="hidden" path="saveMode" id="saveMode"/> 
				<form:input type="hidden" path="" id="vehicleFuelTypeVal"/> 
				
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="fuelling.details" text="Fueling Details" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<apptags:date fieldclass="lessthancurrdate"
										labelCode="vehicle.fuelling.date"
										datePath="vehicleFuellingDTO.vefDate" isMandatory="true"
										cssClass="custDate mandColorClass vefDate" >
									</apptags:date>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="Census"><spring:message
											code="vehicle.maintenance.master.type" text="Census(year)" /></label>
									<c:set var="baseLookupCode" value="VCH" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="vehicleFuellingDTO.veVetype"
										cssClass="chosen-select-no-results mandColorClass form-control" isMandatory="true"
										hasId="true" selectOptionLabelCode="selectdropdown"
										changeHandler="showVehicleRegNo()" />
									<label class="control-label col-sm-2 required-control">
										<spring:message code="vehicle.maintenance.regno" />
									</label>
									<div class="col-sm-4">
										<form:select class=" mandColorClass form-control chosen-select-no-results"
											path="vehicleFuellingDTO.veId" id="vemId" onchange="searchVeNo()" >
											<form:option value="0"><spring:message code="solid.waste.select" text="Select" /></form:option>
											<c:forEach items="${vehicles}" var="vehicle">
												<form:option value="${vehicle.veId}">${vehicle.veNo}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<apptags:input labelCode="vehicle.fuelling.vehicleReadingInKm"
										path="vehicleFuellingDTO.vefReading" isMandatory="true"
										onChange="validateVehicleReading('vehicleFuel.html', 'lastMeterReading');"
										cssClass="hasNumber mandColorClass"></apptags:input>
				
									<label class="col-sm-2 control-label " for="gender"><spring:message
											code="vehicle.vehicle.driver.name" text = "Vehicle Driver Name"/></label>
									<div class="col-sm-4">
										<form:select path="vehicleFuellingDTO.veEmpId" id="veDriverName"
											class="chosen-select-no-results form-control" label="Select">
											<form:option value="0">
												<spring:message code="solid.waste.select" text="select" /></form:option>
											<c:forEach items="${driverDet}" var="lookup">
												<form:option value="${lookup.empId}"> ${lookup.fullName} </form:option>
											</c:forEach> 
										</form:select>
									</div>
										
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"><spring:message
										code="vehicle.fuel.details" text="Fuel Details" /></a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="control-label col-sm-2 required-control">
										<spring:message code="vehicle.fuelling.pump.name" />
									</label>
									<div class="col-sm-4">
										<form:select cssClass=" mandColorClass form-control chosen-select-no-results"
											path="vehicleFuellingDTO.puId" id="puId"
											data-rule-required="true" onchange="targetInfo()">
											<form:option value=""><spring:message code="solid.waste.select" text="Select" /></form:option>
											<c:forEach items="${pumps}" var="pump">
												<form:option value="${pump.puId}">${pump.puPumpname}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<apptags:input labelCode="vehicle.fuelling.adviceno"
										path="vehicleFuellingDTO.vefDmno"
										cssClass="alphaNumeric mandColorClass form-control" isMandatory="true"></apptags:input>
									<apptags:date fieldclass="lessthancurrdate"
										labelCode="vehicle.fuelling.adviceDate"
										datePath="vehicleFuellingDTO.vefDmdate"
										cssClass="custDate mandColorClass" isMandatory="true" >
									</apptags:date>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="swm.fileupload" /></label>
									<div class="col-sm-4">
										<apptags:formField fieldType="7" labelCode="" hasId="true"
											fieldPath="" isMandatory="false" showFileNameHTMLId="true"
											fileSize="BND_COMMOM_MAX_SIZE"
											maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
											currentCount="0" />
										<small class="text-blue-2"> <spring:message code="vehicle.file.upload.tooltip"
											text="(Upload File upto 5MB and Only pdf,doc,docx,jpeg,jpg,png,gif,bmp extension(s) file(s) are allowed.)" />
										</small>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="pumpFuelDets"></div>
				<div class="text-center padding-top-10">
					<%-- Defect #153381 --%>
					<button type="button" class="btn btn-success btn-submit"
						title='<spring:message code="solid.waste.submit" text="Submit" />'
						onclick="saveVehicleFuellingData(this);">
						<spring:message code="solid.waste.submit" text="Submit" />
					</button>
					<button type="Reset" class="btn btn-warning"
						title='<spring:message code="solid.waste.reset" text="Reset" />'
						onclick="addVehicleFueling('vehicleFuel.html','AddVehicleFueling');">
						<spring:message code="solid.waste.reset" text="Reset" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						title='<spring:message code="solid.waste.back" text="Back" />'
						onclick="backVehicleFuellingForm();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

