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
<link rel="stylesheet" type="text/css"
	href="css/mainet/themes/jquery-ui-timepicker-addon.css" />

<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/vehicle_management/logBook.js"></script>

<script type="text/javascript">
	$(".Moredatepicker").timepicker({
		//s dateFormat: 'dd/mm/yy',		
		changeMonth : true,
		changeYear : true,
		minDate : '0',
	});

	$("#time").timepicker({

	});
</script>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="VehicleLogBookDTO.book"
						text="Vehicle Log Book" /></strong>
				<apptags:helpDoc url="VehicleLogBook.html"></apptags:helpDoc>
			</h2>
		</div>
		<div class="widget-content padding">

			<form:form action="vehicleLogBookCon.html" id="frmVehicleLogBook"
				name="frmVehicleLogBook" method="POST" commandName="command"
				class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<div
					class="warning-div error-div aaaaaalert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">

					<%-- <apptags:input labelCode="VehicleLogBookDTO.veNo"
						path="vehicleLogBookDTO.veNo"  isDisabled="${command.saveMode eq 'V'}"  isReadonly="${command.saveMode eq 'E'}"  isMandatory="true"
						cssClass="" />
					<apptags:input labelCode="VehicleLogBookDTO.driverName"
						path="vehicleLogBookDTO.driverName"   isDisabled="${command.saveMode eq 'V'}" isReadonly="${command.saveMode eq 'E'}" isMandatory="true" cssClass="" /> --%>

					<%-- 	<label class="col-sm-2 control-label required-control"
						for="department"><spring:message code="" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control" id="department" data-rule-required="true"
							isMandatory="true" onchange="getEmployeeByDept(this);">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="dept">
								<option value="${dept.dpDeptid}">${dept.dpDeptdesc}</option>
							</c:forEach>
						</form:select>
					</div>	 --%>


					<label class="col-sm-2 control-label required-control"
						for="vehicle">Vehicle No</label>
					<div class="col-sm-4">
						<form:select id="veNo" path="vehicleLogBookDTO.veNo"
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}"
							cssClass="form-control" data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${ListDriver}" var="vehicle">
								<form:option value="${vehicle.veNo}">${vehicle.veNo}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label required-control"
						for="employee">Driver</label>
					<div class="col-sm-4">
						<form:select id="driverName" path="vehicleLogBookDTO.driverId"
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}"
							cssClass="form-control" data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${employees}" var="employee">
								<form:option value="${employee.empId}" label="${employee.fullName}"></form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>


			<%-- 	<div class="form-group">
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="VehicleLogBookDTO.outDate" text="Out Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="vehicleLogBookDTO.outDate"  id="outDate"
								disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}"
								class="form-control mandColorClass datepicker dateValidation" placeholder="dd/mm/yyyy"
								maxLength="10" />
							<label class="input-group-addon" for="outDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=outDate></label>
						</div>
					</div> --%>

                          <div class="form-group">
					<apptags:date fieldclass="datepicker"
						datePath="vehicleLogBookDTO.outDate"
						labelCode="VehicleLogBookDTO.outDate"
						isDisabled="${command.saveMode eq 'V'}" cssClass="mandColorClass"
						isMandatory="true"></apptags:date>

					<apptags:date fieldclass="Moredatepicker"
						labelCode="VehicleLogBookDTO.vehicleOutTime"
						datePath="vehicleLogBookDTO.vehicleOutTime"
						isDisabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}"
						isMandatory="true" cssClass="">
					</apptags:date>

				</div>

				<div class="form-group">
					<label class="col-sm-2"> <spring:message
							code="VehicleLogBookDTO.inDate" text="In Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="vehicleLogBookDTO.inDate"
								disabled="${command.saveMode eq 'V'}" id="inDate"
								class="form-control mandColorClass datepicker dateValidation"
								maxLength="10" />
							<label class="input-group-addon" for="inDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=inDate></label>
						</div>
					</div>

					<apptags:date fieldclass="Moredatepicker"
						labelCode="VehicleLogBookDTO.vehicleInTime"
						datePath="vehicleLogBookDTO.vehicleInTime"
						isDisabled="${command.saveMode eq 'V'}"
						cssClass="">
					</apptags:date>
				</div>

				<div class="form-group">
					<apptags:input labelCode="VehicleLogBookDTO.vehicleJourneyFrom"
						path="vehicleLogBookDTO.vehicleJourneyFrom"
						isDisabled="${command.saveMode eq 'V'}"
						isReadonly="${command.saveMode eq 'E'}" isMandatory="true"
						maxlegnth="100" cssClass="" />

					<apptags:input labelCode="VehicleLogBookDTO.vehicleJourneyTo"
						path="vehicleLogBookDTO.vehicleJourneyTo"
						isDisabled="${command.saveMode eq 'V'}" maxlegnth="100"
						cssClass="" />

				</div>

				<div class="form-group">
					<apptags:input labelCode="VehicleLogBookDTO.dayStartMeterReading"
						path="vehicleLogBookDTO.dayStartMeterReading"
						isDisabled="${command.saveMode eq 'V'}"
						isReadonly="${command.saveMode eq 'E'}" isMandatory="true"
						maxlegnth="12"
						cssClass="hasDecimal" />

					<apptags:input labelCode="VehicleLogBookDTO.dayEndMeterReading"
						path="vehicleLogBookDTO.dayEndMeterReading"
						isDisabled="${command.saveMode eq 'V'}" maxlegnth="12"
						cssClass="hasDecimal" />
				</div>

				<div class="form-group">
					<apptags:input labelCode="VehicleLogBookDTO.fuelInLitre"
						path="vehicleLogBookDTO.fuelInLitre"
						isDisabled="${command.saveMode eq 'V'}" maxlegnth="8"
						cssClass="hasDecimal" />

					<apptags:textArea labelCode="VehicleLogBookDTO.dayVisitDescription"
						path="vehicleLogBookDTO.dayVisitDescription" isMandatory="true"
						maxlegnth="250" cssClass=""
						isReadonly="${command.saveMode eq 'V'}" />

				</div>
				<div class="form-group">
					<apptags:textArea labelCode="VehicleLogBookDTO.reason"
						path="vehicleLogBookDTO.reason" isMandatory="true" maxlegnth="250"
						cssClass="" isReadonly="${command.saveMode eq 'V'}" />

				</div>

				<%-- 
					<apptags:input labelCode="VehicleLogBookDTO.time"
						path="vehicleLogBookDTO.time" isMandatory="true" cssClass="" /> --%>


				<div class="text-center margin-top-10">
					<c:if test="${command.saveMode ne 'V'}">
						<input type="button" value="<spring:message code="bt.save"/>"
							onclick="confirmToProceed(this)" class="btn btn-success"
							id="Submit">
						<c:if test="${command.saveMode ne 'E'}">
							<button type="Reset" class="btn btn-warning"
								onclick="openForm('vehicleLogBookCon.html','logBook')">
								<spring:message code="lgl.reset" text="Reset"></spring:message>
							</button>
						</c:if>
					</c:if>
					<input type="button"
						onclick="window.location.href='vehicleLogBookCon.html'"
						class="btn btn-danger  hidden-print" value="Back">
				</div>
			</form:form>
		</div>
	</div>
</div>








