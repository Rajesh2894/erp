<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/mainet/themes/jquery-ui-timepicker-addon.css" />
<script type="text/javascript" src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/vehicle_management/petrolRequisitionApproval.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	prepareDateTag();
	$('.datepicker').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true
	});
});

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
				<strong><spring:message code="PetrolRequisition.approval.form"
						text="Fuel Requisition Approval Form" /></strong>
				<apptags:helpDoc url="petrolRequestForm.html"></apptags:helpDoc>
			</h2>
		</div>
		<div class="widget-content padding">

			<form:form action="PetrolRegApprov.html"
				id="frmPetrolRegApprovalCloser" name="frmPetrolRegApproval"
				method="POST" commandName="command" class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="warning-div error-div aaaaaalert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="PetrolRequisitionDTO.date" text="Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="entity.date" id="date" disabled="true"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false" maxLength="10" />
							<label class="input-group-addon" for="Date"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=date></label>
						</div>
					</div>
					<apptags:input labelCode="PetrolRequisitionDTO.time"
						path="entity.time" isDisabled="true" isMandatory="true"
						cssClass="hasNumber mandColorClass" />
				</div>

				<div class="form-group">
					<apptags:input labelCode="PetrolRequisitionDTO.department"
						path="entity.deptDesc" isMandatory="true" isDisabled="true"
						maxlegnth="8" cssClass="hasDecimal" />

					<label class="control-label col-sm-2 required-control"
						for="VehicleType"><spring:message
							code="vehicle.master.vehicle.type" text="Vehicle Type" /></label>
					<c:set var="baseLookupCode" value="VCH" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="entity.vehicleType" disabled="true"
						cssClass="form-control required-control chosen-select-no-results"
						isMandatory="true" selectOptionLabelCode="selectdropdown"
						hasId="true" />
				</div>

				<div class="form-group">
					<apptags:input labelCode="PetrolRequisitionDTO.veNo"
						path="entity.veNo" isMandatory="false" isDisabled="true"
						maxlegnth="8" cssClass="hasDecimal" />

					<apptags:input labelCode="vehicle.master.vehicle.chasis.no"
						path="entity.veChasisSrno" isMandatory="true" isDisabled="true"
						maxlegnth="8" cssClass="hasDecimal" />
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="FuelType"><spring:message code="PetrolRequisitionDTO.fuelType" text="Fuel Type" /></label>
					<apptags:lookupField items="${command.getLevelData('VFT')}"
						path="entity.fuelType" disabled="true"
						cssClass="form-control required-control chosen-select-no-results"
						isMandatory="true" selectOptionLabelCode="selectdropdown"
						hasId="true" />

					<label class="col-sm-2 control-label required-control"
						for="employee"><spring:message
							code="fuelling.advice.driverName" text="Driver Name" /></label>
					<div class="col-sm-4">
						<form:select id="driverName" path="entity.driverName"
							disabled="true" cssClass="form-control" data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${employees}" var="empl">
								<form:option value="${empl.empId}">${empl.fullName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<apptags:input labelCode="PetrolRequisitionDTO.vehicleMeterRead"
						path="entity.vehicleMeterRead" isMandatory="false"
						isDisabled="true" maxlegnth="8" cssClass="hasDecimal" />
					<apptags:input labelCode="PetrolRequisitionDTO.fuelQuant"
						path="entity.fuelQuantity" isMandatory="true" isDisabled="true"
						maxlegnth="8" cssClass="hasDecimal" />
				</div>

				<div class="form-group">
					<apptags:input labelCode="PetrolRequisitionDTO.coupon"
						isDisabled="true" path="entity.couponNo" isMandatory="true"
						cssClass="alphaNumeric form-control" maxlegnth="20">
					</apptags:input>

					<apptags:textArea labelCode="vechile.lubricants"
						isMandatory="false" path="entity.petrolDesc"
						cssClass="hasNumClass form-control" isDisabled="true"
						maxlegnth="500" />
				</div>

				<div class="widget-header">
					<h2>
						<strong><spring:message code="petrolRequisition.user.action" text="User Action" /></strong>
					</h2>
				</div>
				<br>
				<div class="form-group">
					<apptags:radio radioLabel="petrolRequisition.approve,petrolRequisition.reject"
						radioValue="APPROVED,REJECTED" isMandatory="true"
						labelCode="petrolRequisition.decision" path="entity.petrolRegstatus"
						defaultCheckedValue="APPROVED">
					</apptags:radio>

					<apptags:textArea labelCode="vehicle.maintenance.remark" isMandatory="true"
						path="entity.petrolRegRemark" cssClass="hasNumClass form-control"
						maxlegnth="100" />
				</div>

				<div class="text-center">
					<button type="button" value="<spring:message code="bt.save"/>"
						class="btn btn-green-3" title="<spring:message code="vehicle.submit" text="Submit" />"
						onclick="savePetrolReqApprovalData(this)">
						<spring:message code="vehicle.submit" text="Submit" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
				
			</form:form>
		</div>
	</div>
</div>








