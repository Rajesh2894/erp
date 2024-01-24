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
<script type="text/javascript"
	src="js/vehicle_management/petrolRequisition.js"></script>

<script type="text/javascript">
	$(".Moredatepicker").timepicker({
		//s dateFormat: 'dd/mm/yy',		
		changeMonth : true,
		changeYear : true,
		minDate : '0',
	});

	$("#time").timepicker({

	});
	var mode=$("#mode").val();
	if(mode=='ADD'){
	$(function () {
    	var d = new Date();
        var currMonth = d.getMonth();
        var currYear = d.getFullYear();
        var startDate = new Date(currYear, currMonth, 1);
        $("#date").datepicker();
        $("#date").datepicker("setDate", d);
	});
	}
</script>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="fuel.request.form"
						text="Fuel Requisition Form" /></strong>
			</h2>
			<apptags:helpDoc url="petrolRequestForm.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">

			<form:form action="petrolRequisitionForm.html"
				name="frmPetrolRequestForm" method="POST" commandName="command"
				class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

               <form:hidden path="saveMode" id="mode" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="vehicle.fuelling.date" text="Date" /></label>
					<div class="col-sm-4"> <%-- disabled="${command.saveMode eq 'V'}" --%>
						<div class="input-group">
							<form:input path="petrolRequisitionDTO.date" id="date"
								class="form-control mandColorClass datepicker dateValidation"
								value="" maxLength="10" placeholder="dd/mm/yyyy" />
							<label class="input-group-addon" for="Date"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=date></label>
						</div>
					</div>

					<apptags:input labelCode="vehicle.time"
						path="petrolRequisitionDTO.time"
						isDisabled="${command.saveMode eq 'V'}" isMandatory="true"
						cssClass="hasNumber mandColorClass" />
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="department"><spring:message
							code="vehicle.deptId" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="petrolRequisitionDTO.department"
							disabled="${command.saveMode eq 'V'}" cssClass="form-control"
							id="department" data-rule-required="true" isMandatory="true"
							onchange="getVehicleTypeByDept()">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="dept">
								<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2 required-control"
						for="VehicleType"><spring:message
							code="vehicle.master.vehicle.type" text="Vehicle Type" /></label>
					<apptags:lookupField items="${command.getLevelData('VCH')}"
						hasId="true" path="petrolRequisitionDTO.vehicleType"
						cssClass="form-control" selectOptionLabelCode="selectdropdown"
						disabled="${command.saveMode eq 'V'}" isMandatory="true"
						changeHandler="showVehicleRegNo()" />
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"
						for="vehicle"><spring:message
							code="insurance.detail.vehicleno" text="Vehicle No" /></label>
					<div class="col-sm-4">
						<form:select path="petrolRequisitionDTO.veId" id="veid"
							class="chosen-select-no-results form-control mandColorClass " label="Select"
							onchange="showFuelType(this)"
							disabled="${command.saveMode eq 'V'}">
							<form:option value="0">
								<spring:message code="oem.select" text="select" />
							</form:option>
							<c:forEach items="${ListVehicles}" var="vehicle">
								<form:option value="${vehicle.veNo}">${vehicle.vehicleTypeDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="control-label col-sm-2 required-control"
						for="MakeModel"><spring:message
							code="vehicle.master.vehicle.chasis.no" text="Chasis No." /></label>
					<div class="col-sm-4">
						<form:select path="petrolRequisitionDTO.veChasisSrno" id="chasisno"
							class="chosen-select-no-results form-control mandColorClass " label="Select"
							onchange="showFuelType(this)"
							disabled="${command.saveMode eq 'V'}">
							<form:option value="">
								<spring:message code="oem.select" text="select" />
							</form:option>
							<c:forEach items="${ListOfChassisNo}" var="vehicle">
								<form:option value="${vehicle.veNo}">${vehicle.veChasisSrno}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="FuelType"><spring:message
							code="vehicle.fuelType" text="Fuel Type" /></label>
					<%-- 	<c:set var="baseLookupCode" value="TYI" /> --%>
					<apptags:lookupField items="${command.getLevelData('VFT')}"
						path="petrolRequisitionDTO.fuelType" hasId="true"
						disabled="${command.saveMode eq 'V'}" cssClass="form-control"
						isMandatory="true" selectOptionLabelCode="selectdropdown" />
					
					<label class="col-sm-2 control-label required-control"
						for="employee"><spring:message code="VehicleLogBookDTO.driverName" text="Driver Name" /></label>
					<div class="col-sm-4">
						<form:select id="driverName"
							path="petrolRequisitionDTO.driverName"
							disabled="${command.saveMode eq 'V'}" cssClass="form-control chosen-select-no-results"
							data-rule-required="true">
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
					<label class="control-label col-sm-2 required-control"
						for="fuelQuantity"> <spring:message code="PetrolRequisitionDTO.fuelQuant"
							text="Fuel Quantity" />
					</label>
					<div class="col-sm-2">
						<form:input type='text' path="petrolRequisitionDTO.fuelQuantity"
							maxlength="6" id="fuelQuant" placeholder="0.0"
							disabled="${command.saveMode eq 'V'}"
							class="mandColorClass form-control hasDecimal" />
					</div>
					<div class="col-sm-2">
					<form:select disabled="${command.saveMode eq 'V'}"
						path="petrolRequisitionDTO.fuelQuantUnit"
						class="form-control " cssClass="form-control required-control"
						id="fuelQuantityUnit">
						<form:option value="0">
							<spring:message code="solid.waste.select" text="Select" />
						</form:option>
						<c:forEach items="${command.getLevelData('UOM')}" var="lookup">
							<c:if
								test="${lookup.otherField eq 'CAPACITY' or (lookup.otherField eq 'WEIGHT' and lookup.descLangFirst eq 'Kilogram')}">
								<form:option value="${lookup.lookUpId}"
									code="${lookup.lookUpCode}">${lookup.descLangFirst}</form:option>
							</c:if>
						</c:forEach>
					</form:select>
					</div>

					<apptags:input labelCode="PetrolRequisitionDTO.coupon"  isDisabled="${command.saveMode eq 'V'}"
						path="petrolRequisitionDTO.couponNo" isMandatory="true"
						cssClass="alphaNumeric form-control" maxlegnth="20">
					</apptags:input>
				</div>
				
				<div class="form-group">				
				<label for="vehicleMeterRead"
						class="col-sm-2 control-label "><spring:message
							code="PetrolRequisitionDTO.vehicleMeterRead"
							text="Vehicle Reading KM" /></label>
					<div class="col-sm-4">
						<form:input type='text'
							path="petrolRequisitionDTO.vehicleMeterRead" maxlength="8"
							id="vehicleMeterRead" placeholder="0.0"
							disabled="${command.saveMode eq 'V'}"
							class="mandColorClass form-control hasDecimal" />
					</div>

					<apptags:textArea labelCode="vechile.lubricants" isMandatory="false"
						path="petrolRequisitionDTO.petrolDesc"
						cssClass="hasNumClass form-control"
						isDisabled="${command.saveMode eq 'V'}" maxlegnth="500" />
				</div>

				<c:if test="${command.saveMode eq 'V' && command.petrolRequisitionDTO.requestStatus ne 'O'}">
					<div class="form-group">
						<apptags:radio radioLabel="petrolRequisition.approve,petrolRequisition.reject"
							radioValue="APPROVED,REJECTED" isMandatory="true" disabled="true"
							labelCode="petrolRequisition.decision" path="petrolRequisitionDTO.petrolRegstatus">
						</apptags:radio>
						
						<apptags:textArea labelCode="vehicle.maintenance.remark" isMandatory="true"
							isDisabled="true" path="petrolRequisitionDTO.petrolRegRemark"
							cssClass="hasNumClass form-control" maxlegnth="100" />
					</div>
				</c:if>


				<div class="text-center margin-top-10">
					<c:if test="${command.saveMode ne 'V'}">
						<input type="button" value="<spring:message code="bt.save"/>"
							title='<spring:message code="bt.save"/>'
							onclick="confirmToProceed(this)" class="btn btn-success"
							id="Submit">
						<c:if test="${command.saveMode ne 'E'}">
							<button type="Reset" class="btn btn-warning"
								title='<spring:message code="lgl.reset" text="Reset" />'
								onclick="openForm('petrolRequisitionForm.html','petrolRequest')">
								<spring:message code="lgl.reset" text="Reset"></spring:message>
							</button>
						</c:if>
					</c:if>
					<input type="button"
						title='<spring:message code="vehicle.back" text="Back"/>'
						onclick="window.location.href='petrolRequisitionForm.html'"
						class="btn btn-danger  hidden-print" value="<spring:message code="vehicle.back" text="Back"/>">
				</div>
			</form:form>
		</div>
	</div>
</div>








