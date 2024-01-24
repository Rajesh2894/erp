<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/vehicle_management/VehicleEMployee.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<!-- Start Main Page Heading -->
		<div class="widget-header">
			<h2>
				<spring:message code="vehicle.employee.details"
					text="Vehicle Employee Details Form" />
			</h2>
			<apptags:helpDoc url="vehicleEmpDetails.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="population.master.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="population.master.mand.field" text="is mandatory"></spring:message>
				</span>
			</div>
			<form:form action="vehicleEmpDetails.html" name=""
				id="PopulationMasterForm" class="form-horizontal">
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
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="vehicle.employee.info" text="Employee Information" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="vehicle.sanitary.employeeName" text="Employee Name" /></label>
									<div class="col-sm-4">
										<form:select path="sLRMEmployeeMasterDto.empUId" disabled="" onchange="getEmpDetails()"
											cssClass="form-control mandColorClass chosen-select-no-results" id="empUId"
											data-rule-required="true">
											<form:option value="">
												<spring:message code="solid.waste.select" />
											</form:option>
											<c:forEach items="${command.employeeObjectList}" var="employee">
												<form:option value="${employee[0]}">${employee[1]} ${employee[2]}</form:option>
											</c:forEach>
										</form:select>

									</div>
								</div>
								
								<div class="form-group">	
							    	<form:hidden path="sLRMEmployeeMasterDto.mrfId" id="mrfId" />
									<apptags:input labelCode="vehicle.deptId" path="sLRMEmployeeMasterDto.deptName"
											isMandatory="true" isDisabled="true" />
											
									<form:hidden path="sLRMEmployeeMasterDto.desgId" id="dsgnId" />
									<apptags:input labelCode="contract.label.designation" path="sLRMEmployeeMasterDto.desigName"
											isMandatory="true" isDisabled="true" />
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control" for=""><spring:message
											code="vehicle.employee.title" text="Title" /></label>
									<c:set var="baseLookupCode" value="TTL" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}" 
										path="sLRMEmployeeMasterDto.ttlId" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										disabled="true" selectOptionLabelCode="Select" isMandatory="true" />
									<label class="col-sm-2 control-label required-control" for=""><spring:message
											code="vehicle.employee.fName" text="First Name" /> </label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empName" maxlength="50" disabled="true"
											class="form-control hasNameClass valid" id="empName" />
									</div>

								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" for=""><spring:message
											code="vehicle.employee.mName" text="Middle Name" /> </label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empMName" maxlength="50" disabled="true"
											class="form-control hasNameClass valid" id="empMName" />
									</div>
									<label class="col-sm-2 control-label required-control" for=""><spring:message
											code="vehicle.employee.lName" text="Last Name" /> </label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empLName" maxlength="50" disabled="true"
											class="form-control hasNameClass valid" id="empLName" />
									</div>
								</div>
							
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="vehicle.employee.gender" text="Gender" /></label>
									<div class="col-sm-4">
										<label class="radio-inline "> <form:radiobutton disabled="true"
												id="gender" path="sLRMEmployeeMasterDto.gender" value="M"
												 /> <spring:message
												code="vehicle.employee.male" text="Male" />
										</label> <label class="radio-inline "> <form:radiobutton disabled="true"
												id="gender" path="sLRMEmployeeMasterDto.gender" value="F" />
											<spring:message code="vehicle.employee.female" text="Female" />
										</label>
									</div>
									<label class="col-sm-2 control-label required-control" for=""><spring:message
											code="swm.SLRM.employee.mobileNo" text="Mobile Number" /> </label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empMobNo" disabled="true"
											class="form-control hasMobileNo mandColorClass error" maxlength="10" id="empMobNo" onChange="validateContactNum()"/>
									</div>
								</div> 

								<div class="form-group">
									<label class="col-sm-2 control-label" for=""><spring:message
											code="vehicle.employee.email" text="Email ID" /> </label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empEmailId"  maxlength="90" disabled="true"
											class="form-control hasemailclass hasNoSpace mandColorClass error" id="empEmailId" />
									</div>
									
									<label class="col-sm-2 control-label required-control" for=""><spring:message
											code="vehicle.employee.address" text="Address" /></label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empAddress" maxlength="100" disabled="true"
											cssClass="form-control  mandColorClass  " id="empAddress" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label " for=""><spring:message
											code="vehicle.employee.address1" text="Address-1" /></label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empAddress1" maxlength="100" disabled="true"
											cssClass="form-control  mandColorClass  " id="empAddress1" />

									</div>
									<label class="col-sm-2 control-label" for=""><spring:message
											code="vehicle.employee.pincode" text="Pincode" /></label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empPincode" maxlength="6" disabled="true"
											cssClass="form-control mandColorClass hasPincode" id="empPincode" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit"
						onclick="Proceed(this)">
						<spring:message code="solid.waste.submit" text="Submit"></spring:message>
					</button>
					<button type="Reset" class="btn btn-warning"
						onclick="ResetForm(this)">
						<spring:message code="solid.waste.reset" text="Reset"></spring:message>
					</button>
					<apptags:backButton url="vehicleEmpDetails.html"></apptags:backButton>
				</div>
				
			</form:form>
		</div>
	</div>
</div>