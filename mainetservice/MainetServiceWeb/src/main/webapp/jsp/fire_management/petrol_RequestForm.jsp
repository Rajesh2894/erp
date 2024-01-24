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
<script type="text/javascript" src="js/fire_management/petrolRequisition.js"></script>

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
				<strong><spring:message code=""
						text="Petrol Requisition Form" /></strong>
				<apptags:helpDoc url="petrolRequestForm.html"></apptags:helpDoc>
			</h2>
		</div>
		<div class="widget-content padding">

			<form:form action="petrolRequestForm.html" id="frmPetrolRequestForm"
				name="frmPetrolRequestForm" method="POST" commandName="command"
				class="form-horizontal form">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />


				<div
					class="warning-div error-div aaaaaalert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				
				         <div class="form-group">
				     	<label class="col-sm-2 control-label required-control"> <spring:message
							code="PetrolRequisitionDTO.date" text="Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="petrolRequisitionDTO.date" id="date" disabled="${command.saveMode eq 'V'}"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false" maxLength="10" />
							<label class="input-group-addon" for="Date"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=date></label>
						</div>
					</div>
					
					<apptags:input labelCode="PetrolRequisitionDTO.time"
						path="petrolRequisitionDTO.time"
						isDisabled="${command.saveMode eq 'V'}" isMandatory="true"
						cssClass="hasNumber mandColorClass" />
			
				</div>
                         
                     
			    	<div class="form-group">
			    	
			    	<label class="col-sm-2 control-label required-control"
						for="department"><spring:message code="PetrolRequisitionDTO.department" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="petrolRequisitionDTO.department"  disabled="${command.saveMode eq 'V'}"
							cssClass="form-control" id="department" data-rule-required="true"
							isMandatory="true" onchange="getEmployeeByDept(this);">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="dept">
								<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
			    	
			            <label class="control-label col-sm-2 required-control" for="VehicleType"><spring:message
							code="vehicle.master.vehicle.type" text="Vehicle Type" /></label>
					<c:set var="baseLookupCode" value="VCH" />
					<apptags:lookupField items="${command.getLevelData(baseLookupCode)}" path="petrolRequisitionDTO.vehicleType"
					disabled="${command.saveMode eq 'V'}"  cssClass="form-control required-control chosen-select-no-results" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />
					
				</div>
				
				
				
					<div class="form-group">
				
				<label class="col-sm-2 control-label required-control" for="vehicle"><spring:message
							code="PetrolRequisitionDTO.veNo" text="Vehicle" /></label>
					<div class="col-sm-4">
						<form:select id="veNo" path="petrolRequisitionDTO.veNo" cssClass="form-control"
						  disabled="${command.saveMode eq 'V'}"	data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${ListVehicles}" var="vehicle">
								<form:option value="${vehicle.veNo}">${vehicle.veNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					 <label class="control-label col-sm-2 required-control" for="FuelType"><spring:message
							code="PetrolRequisitionDTO.fuelType" text="Fuel Type" /></label>
					<c:set var="baseLookupCode" value="TYI" />
					<apptags:lookupField items="${command.getLevelData(baseLookupCode)}" path="petrolRequisitionDTO.fuelType"
					disabled="${command.saveMode eq 'V'}"	cssClass="form-control required-control chosen-select-no-results" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />	
				</div>
				
		
                   <div class="form-group">
					<apptags:input labelCode="PetrolRequisitionDTO.fuelQuantity"
						path="petrolRequisitionDTO.fuelQuantity"  isMandatory="true"
						isDisabled="${command.saveMode eq 'V'}" maxlegnth="8"
						cssClass="hasDecimal" />
						
						
		               <label class="col-sm-2 control-label required-control" for="employee">Driver Name</label>
				     	<div class="col-sm-4">
						<form:select id="driverName" path="petrolRequisitionDTO.driverName"
						disabled="${command.saveMode eq 'V'}"	cssClass="form-control" data-rule-required="true">

							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${employees}" var="empl">
								<form:option value="${empl.empId}">${empl.fullName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
							
	<%-- 	 <div class="form-group">
			<apptags:radio radioLabel="Approve,Reject" radioValue="A,R" 
				labelCode="Request Status" path="petrolRequisitionDTO.requestStatus" >
			</apptags:radio>
			</div> --%>

				<div class="text-center margin-top-10">
					<c:if test="${command.saveMode ne 'V'}">
						<input type="button" value="<spring:message code="bt.save"/>"
							onclick="confirmToProceed(this)" class="btn btn-success"
							id="Submit">
						<c:if test="${command.saveMode ne 'E'}">
							<button type="Reset" class="btn btn-warning"
								onclick="openForm('petrolRequestForm.html','petrolRequest')">
								<spring:message code="lgl.reset" text="Reset"></spring:message>
							</button>
						</c:if>
					</c:if>
					<input type="button"
						onclick="window.location.href='petrolRequestForm.html'"
						class="btn btn-danger  hidden-print" value="Back">
				</div>
			</form:form>
		</div>
	</div>
</div>








