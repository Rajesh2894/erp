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
<script type="text/javascript" src="js/fire_management/petrolRequisitionApproval.js"></script>


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

			<form:form action="PetrolRegApproval.html" id="frmPetrolRegApprovalCloser"
				name="frmPetrolRegApproval" method="POST" commandName="command"
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
						<div class="input-group"> <!-- petrolRequisitionDTO.date -->
							<form:input path="entity.date" id="date" disabled="true"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false" maxLength="10" />
							<label class="input-group-addon" for="Date"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=date></label>
						</div>
					</div>
					<!-- petrolRequisitionDTO.time -->
					<apptags:input labelCode="PetrolRequisitionDTO.time"
						path="entity.time"
						isDisabled="true" isMandatory="true"
						cssClass="hasNumber mandColorClass" />
			
				</div>
                         
                     
			    	<div class="form-group">
			    	<!-- petrolRequisitionDTO.department -->
			    	<%-- <label class="col-sm-2 control-label required-control"
						for="department"><spring:message code="PetrolRequisitionDTO.department" text="Department" /></label>
					<div class="col-sm-4">
						<form:select path="entity.deptDesc"  disabled="true"
							cssClass="form-control" id="department" data-rule-required="true"
							isMandatory="true" onchange="getEmployeeByDept(this);">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${departments}" var="dept">
								<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>
					<apptags:input labelCode="PetrolRequisitionDTO.veNo"
						path="entity.deptDesc"  isMandatory="true"
						isDisabled="true" maxlegnth="8"
						cssClass="hasDecimal" />
			    	
			    	<!-- petrolRequisitionDTO.vehicleType -->
			            <label class="control-label col-sm-2 required-control" for="VehicleType"><spring:message
							code="vehicle.master.vehicle.type" text="Vehicle Type" /></label>
					<c:set var="baseLookupCode" value="VCH" />
					<apptags:lookupField items="${command.getLevelData(baseLookupCode)}" path="entity.vehicleType"
					disabled="true"  cssClass="form-control required-control chosen-select-no-results" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />
					
				</div>
				
				
				
					<div class="form-group">
				<!-- petrolRequisitionDTO.veNo -->
				<%-- <label class="col-sm-2 control-label required-control" for="vehicle"><spring:message
							code="PetrolRequisitionDTO.veNo" text="Vehicle" /></label> --%>
					<%-- <div class="col-sm-4">
						<form:select id="veNo" path="" cssClass="form-control"
						  disabled="true"	data-rule-required="true">
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${ListVehicles}" var="vehicle">
								<form:option value="${vehicle.veNo}">${vehicle.veNo}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>
					<apptags:input labelCode="PetrolRequisitionDTO.veNo"
						path="entity.veNo"  isMandatory="true"
						isDisabled="true" maxlegnth="8"
						cssClass="hasDecimal" />
					
					<!-- petrolRequisitionDTO.fuelType -->
					 <label class="control-label col-sm-2 required-control" for="FuelType"><spring:message
							code="PetrolRequisitionDTO.fuelType" text="Fuel Type" /></label>
					<c:set var="baseLookupCode" value="TYI" />
					<apptags:lookupField items="${command.getLevelData(baseLookupCode)}" path="entity.fuelType"
					disabled="true"	cssClass="form-control required-control chosen-select-no-results" isMandatory="true"
						selectOptionLabelCode="selectdropdown" hasId="true" />	
				</div>
				
		<!-- petrolRequisitionDTO.fuelQuantity -->
                   <div class="form-group">
					<apptags:input labelCode="PetrolRequisitionDTO.fuelQuantity"
						path="entity.fuelQuantity"  isMandatory="true"
						isDisabled="true" maxlegnth="8"
						cssClass="hasDecimal" />
						
						<!-- petrolRequisitionDTO.driverName -->
		               <label class="col-sm-2 control-label required-control" for="employee">Driver Name</label>
				     	<div class="col-sm-4">
						<form:select id="driverName" path="entity.driverName" disabled="true"
							cssClass="form-control" data-rule-required="true">

							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${employees}" var="empl">
								<form:option value="${empl.empId}">${empl.fullName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>


         <div class="widget-header">
			<h2>
				<strong><spring:message code="" text="User Action" /></strong>
			</h2>
		</div>
		
		<br>
		
		<div class="col-sm-12 text-left">
			<div class="table-responsive">
				<table class="table table-bordered table-striped"
					id="attachDocs">
					<tr>
						<th><spring:message code="scheme.document.name" text="" /></th>
						<th><spring:message code="scheme.view.document" text="" /></th>
					</tr>
					<c:forEach items="${command.fetchDocumentList}" var="lookUp">
						<tr>
							<td align="center">${lookUp.attFname}</td>
							<td align="center">
							<apptags:filedownload filename="${lookUp.attFname}"
							   	filePath="${lookUp.attPath}" actionUrl="DeathRegistration.html?Download" >
							</apptags:filedownload>	
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		
	                <div class="form-group">	
	                <apptags:radio radioLabel="Approve,Reject" radioValue="APPROVED,REJECTED" isMandatory="true"
					labelCode="Status" path="entity.petrolRegstatus" defaultCheckedValue="APPROVED" >
				    </apptags:radio>
				     
	                <apptags:textArea
					labelCode="Remark" isMandatory="true"
					path="entity.petrolRegRemark" cssClass="hasNumClass form-control"
					maxlegnth="100" />  
	                </div>
		
			
		                <div class="text-center">
		                <button type="button" 
		                value="<spring:message code="bt.save"/>"
							class="btn btn-green-3" title="Submit"
							onclick="savePetrolReqApprovalData(this)">
							Save<i class="fa padding-left-4" aria-hidden="true"></i>
						</button>
		                
			             <!-- <button type="button" class="btn btn-green-3" title="Submit"
					         onclick="savePetrolReqApprovalData(this)">
					         Save<i class="fa padding-left-4" aria-hidden="true"></i>
			             </button> -->
			
			           <apptags:backButton url="AdminHome.html"></apptags:backButton>
		               </div>	

			</form:form>
		</div>
	</div>
</div>








