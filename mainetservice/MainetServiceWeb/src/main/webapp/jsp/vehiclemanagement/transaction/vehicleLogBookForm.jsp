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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/vehicle_management/vehiclelogBook.js"></script>

<script type="text/javascript">
	$(".Moredatepicker").timepicker({
		//s dateFormat: 'dd/mm/yy',		
		changeMonth : true,
		changeYear : true,
		minDate : '0',
	});

	$("#time").timepicker({

	});
	
	$(function() {
		$('.datetimepicker3').timepicker();
		
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
				<apptags:helpDoc url="vehLogBook.html"></apptags:helpDoc>
			</h2>
		</div>
		<div class="widget-content padding">

			<form:form action="vehLogBook.html" id="frmVehicleLogBook"
				name="frmVehicleLogBook" method="POST" commandName="command"
				class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="vehicle.maintenance.master.type" text="Census(year)" /></label>
					<c:set var="baseLookupCode" value="VCH" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="vehicleLogBookDTO.typeOfVehicle"
						cssClass="mandColorClass form-control" isMandatory="true"
						hasId="true" selectOptionLabelCode="selectdropdown"
						disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}"
						changeHandler="showVehicleRegNo()" />
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
						for="vehicle"><spring:message
							code="vehicle.sanitary.vehicleNo" text="Vehicle No" /></label>
					<div class="col-sm-4">

						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							id="veNo" path="vehicleLogBookDTO.veNo"
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${ListDriver}" var="vehicle">
								<form:option value="${vehicle.veName}">${vehicle.vehicleTypeDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label required-control"
						for="employee"><spring:message code="vehicle.vehicle.driver.name"
							text="Driver" /></label>
					<div class="col-sm-4">
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							id="driverName" path="vehicleLogBookDTO.driverId"
							disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${employees}" var="employee">
								<form:option value="${employee.empId}"
									label="${employee.fullName}"></form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>


				<div class="form-group">
					<fmt:formatDate pattern="dd/MM/yyyy"
						value="${vehicleLogBookDTO.outDate}" var="dateFormat" />
					<label class="col-sm-2 control-label"> <spring:message
							code="vehicle.logbook.out.date" text="Out Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="vehicleLogBookDTO.outDate" id="outDate"
								disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}"
								class="form-control mandColorClass datepicker dateValidation"
								maxLength="10" placeholder="dd/mm/yyyy" />
							<label class="input-group-addon" for="outDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=outDate></label>
						</div>
					</div>

					<apptags:input labelCode="vehicle.logbook.out.time"
						path="vehicleLogBookDTO.vehicleOutTime"
						isDisabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}"
						isMandatory="false"
						cssClass="form-control datetimepicker3 mandColorClass" />

					<%-- <apptags:date fieldclass="Moredatepicker"
						labelCode="VehicleLogBookDTO.vehicleOutTime"
						datePath="vehicleLogBookDTO.vehicleOutTime"
						isDisabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}"
						isMandatory="true" cssClass="form-control datetimepicker3 mandColorClass">
					</apptags:date> --%>

				</div>

				<div class="form-group">
					<fmt:formatDate pattern="dd/MM/yyyy"
						value="${vehicleLogBookDTO.inDate}" var="dateFormat" />
					<label class="col-sm-2"> <spring:message
							code="vehicle.logbook.in.date" text="In Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="vehicleLogBookDTO.inDate"
								disabled="${command.saveMode eq 'V'}" id="inDate"
								class="form-control mandColorClass datepicker dateValidation"
								maxLength="10" placeholder="dd/mm/yyyy" />
							<label class="input-group-addon" for="inDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=inDate></label>
						</div>
					</div>
					

					<%-- <apptags:date fieldclass="Moredatepicker"
						labelCode="VehicleLogBookDTO.vehicleInTime"
						datePath="vehicleLogBookDTO.vehicleInTime"
						isDisabled="${command.saveMode eq 'V'}"
						cssClass="">
					</apptags:date> --%>
					<!-- isMandatory="true" -->
					<apptags:input labelCode="vehicle.logbook.in.time"
						path="vehicleLogBookDTO.vehicleInTime"
						isDisabled="${command.saveMode eq 'V'}"
						cssClass="form-control datetimepicker3 mandColorClass" />
				</div>

				<div class="form-group">
					<apptags:input labelCode="VehicleLogBookDTO.vehicleJourneyFrom"
						path="vehicleLogBookDTO.vehicleJourneyFrom"
						isDisabled="${command.saveMode eq 'V'}"
						isReadonly="${command.saveMode eq 'E'}" isMandatory="false"
						maxlegnth="100" cssClass="" />

					<apptags:input labelCode="VehicleLogBookDTO.vehicleJourneyTo"
						path="vehicleLogBookDTO.vehicleJourneyTo"
						isDisabled="${command.saveMode eq 'V'}" maxlegnth="100"
						cssClass="" />

				</div>

				<div class="form-group">
					<%-- <%-- <apptags:input labelCode="VehicleLogBookDTO.dayStartMeterReading"
						path="vehicleLogBookDTO.dayStartMeterReading"
						isDisabled="${command.saveMode eq 'V'}"
						isReadonly="${command.saveMode eq 'E'}" isMandatory="true"
						maxlegnth="12"
						cssClass="hasDecimal" /> --%>

					<label class="control-label col-sm-2 "><spring:message
							code="VehicleLogBookDTO.dayStartMeterReading"
							text="Day Start Meter Reading" /></label>
					<div class="col-sm-4">
						<form:input cssClass="form-control text-right"
							path="vehicleLogBookDTO.dayStartMeterReading"
							onkeypress="return hasAmount(event, this, 12, 1)"
							readonly="${command.saveMode eq 'E'}" id="dayStartMeterReading"
							disabled="${command.saveMode eq 'V'}" />
					</div>

					<%-- <apptags:input labelCode="VehicleLogBookDTO.dayEndMeterReading"
						path="vehicleLogBookDTO.dayEndMeterReading"
						isDisabled="${command.saveMode eq 'V'}" maxlegnth="12"
						cssClass="hasDecimal" /> --%>
					<label class="control-label col-sm-2 "><spring:message
							code="VehicleLogBookDTO.dayEndMeterReading"
							text="Day End Meter Reading" /></label>
					<div class="col-sm-4">
						<form:input cssClass="form-control text-right"
							path="vehicleLogBookDTO.dayEndMeterReading"
							onkeypress="return hasAmount(event, this, 12, 1)"
							id="dayEndMeterReading" disabled="${command.saveMode eq 'V'}" />
					</div>
				</div>

				<div class="form-group">
					<apptags:input labelCode="VehicleLogBookDTO.fuelInLitre"
						path="vehicleLogBookDTO.fuelInLitre"
						isDisabled="${command.saveMode eq 'V'}" maxlegnth="8"
						cssClass="hasDecimal" />

					<apptags:textArea labelCode="VehicleLogBookDTO.dayVisitDescription"
						path="vehicleLogBookDTO.dayVisitDescription" isMandatory="false"
						maxlegnth="250" cssClass=""
						isReadonly="${command.saveMode eq 'V'}" />

				</div>
				<div class="form-group">
					<apptags:textArea labelCode="VehicleLogBookDTO.reason"
						path="vehicleLogBookDTO.reason" isMandatory="false" maxlegnth="250"
						cssClass="" isReadonly="${command.saveMode eq 'V'}" />

				</div>
				<c:if test="${command.saveMode ne 'V'}">
				<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="swm.fileupload" /></label>
						<div class="col-sm-4">
							<small class="text-blue-2"> <spring:message
									code="vehicle.file.upload.tooltip"
									text="(Upload File upto 5MB and Only pdf,doc,docx,jpeg,jpg,png,gif,bmp extension(s) file(s) are allowed.)" />
							</small>
							<apptags:formField fieldType="7" labelCode="" hasId="true"
								fieldPath="" isMandatory="false" showFileNameHTMLId="true"
								fileSize="BND_COMMOM_MAX_SIZE"
								maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
								validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
								currentCount="0" />
						</div>
					</div>
					</c:if>
				<c:if test="${!command.attachDocsList.isEmpty()}">
					<div class="table-responsive">
						<table class="table table-bordered table-striped" id="deleteDoc">
							<tr>
								<th width="8%"><spring:message
										code="population.master.srno" text="Sr. No." /></th>
								<th><spring:message code="scheme.view.document" text="" /></th>
							</tr>
							<c:set var="e" value="0" scope="page" />
							<c:forEach items="${command.attachDocsList}" var="lookUp">
								<tr>
									<td>${e+1}</td>
									<td><apptags:filedownload filename="${lookUp.attFname}"
											filePath="${lookUp.attPath}"
											actionUrl="vehicleMaintenanceMgmt.html?Download" /></td>
								</tr>
								<c:set var="e" value="${e + 1}" scope="page" />
							</c:forEach>
						</table>
					</div>
				</c:if>


				<%-- 
					<apptags:input labelCode="VehicleLogBookDTO.time"
						path="vehicleLogBookDTO.time" isMandatory="true" cssClass="" /> --%>


				<div class="text-center margin-top-10">
					<c:if test="${command.saveMode ne 'V'}">
						<c:if test="${command.saveMode ne 'E'}">
							<input type="button" value="<spring:message code="bt.save"/>"
								title='<spring:message code="bt.save"/>'
								onclick="confirmToProceed(this,'C')" class="btn btn-success"
								id="Submit">
						</c:if>
						<c:if test="${command.saveMode eq 'E'}">
							<input type="button" value="<spring:message code="bt.save"/>"
								title='<spring:message code="bt.save"/>'
								onclick="confirmToProceed(this,'E')" class="btn btn-success"
								id="Submit">
						</c:if>
						<c:if test="${command.saveMode ne 'E'}">
							<button type="Reset" class="btn btn-warning"
								title='<spring:message code="lgl.reset" text="Reset" />'
								onclick="openForm('vehLogBook.html','logBook')">
								<spring:message code="lgl.reset" text="Reset"></spring:message>
							</button>
						</c:if>
					</c:if>
					<input type="button"
						title='<spring:message code="vehicle.back" text="Back"/>'
						onclick="window.location.href='vehLogBook.html'"
						class="btn btn-danger  hidden-print"
						value="<spring:message code="vehicle.back" text="Back"/>">
				</div>
			</form:form>
		</div>
	</div>
</div>

