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
	src="js/solid_waste_management/SLRMEMployee.js"></script>
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
				<spring:message code="swm.SLRM.employee.form"
					text="SLRM Employee Details Form" />
			</h2>
			<apptags:helpDoc url="SLRMEmployeeMaster.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="population.master.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="population.master.mand.field" text="is mandatory"></spring:message>
				</span>
			</div>
			<form:form action="SLRMEmployeeMaster.html" name=""
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
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"
						for="desposalsite"><spring:message code="swm.dsplsite" />
					</label>
					<div class="col-sm-4">
						<form:select path="sLRMEmployeeMasterDto.mrfId"
							class="form-control mandColorClass chosen-select-no-results"
							label="Select" id="mrfId">
							<form:option value="0">
								<spring:message code="solid.waste.select" text="select" />
							</form:option>
							<c:forEach items="${command.mrfMasterList}" var="lookUp">
								<form:option value="${lookUp.mrfId}" code="">${lookUp.mrfPlantName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="contract.label.designation" text="Designation" /></label>
					<div class="col-sm-4">
						<form:select path="sLRMEmployeeMasterDto.desgId" disabled=""
							cssClass="form-control mandColorClass" id="dsgnId"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="solid.waste.select" />
							</form:option>
							<c:forEach items="${command.designationList}" var="lookup">
								<form:option value="${lookup.dsgid}">${lookup.dsgname}</form:option>
							</c:forEach>
						</form:select>

					</div>
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
									<label class="col-sm-2 control-label required-control" for=""><spring:message
											code="swm.SLRM.employee.code" text="Employee Code" /> </label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empUId"
											cssClass="form-control  mandColorClass  " id="empUId" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control" for=""><spring:message
											code="swm.SLRM.employee.title" text="Title" /></label>
									<c:set var="baseLookupCode" value="TTL" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="sLRMEmployeeMasterDto.ttlId" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										disabled="" selectOptionLabelCode="Select" isMandatory="true" />
									<label class="col-sm-2 control-label required-control" for=""><spring:message
											code="swm.SLRM.employee.fName" text="First Name" /> </label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empName"
											cssClass="form-control  mandColorClass  " id="empName" />
									</div>

								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" for=""><spring:message
											code="swm.SLRM.employee.mName" text="Middle Name" /> </label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empMName"
											cssClass="form-control  mandColorClass" id="empMName" />
									</div>
									<label class="col-sm-2 control-label required-control" for=""><spring:message
											code="swm.SLRM.employee.lName" text="Last Name" /> </label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empLName"
											cssClass="form-control  mandColorClass  " id="empLName" />
									</div>

								</div>
								<div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
											code="swm.SLRM.employee.gender" text="Gender" /></label>
									<div class="col-sm-4">
										<label class="radio-inline "> <form:radiobutton
												id="gender" path="sLRMEmployeeMasterDto.gender" value="M"
												checked="checked" /> <spring:message
												code="swm.SLRM.employee.male" text="Male" />
										</label> <label class="radio-inline "> <form:radiobutton
												id="gender" path="sLRMEmployeeMasterDto.gender" value="F" />
											<spring:message code="swm.SLRM.employee.female" text="Female" />
										</label>
									</div>
									<label class="col-sm-2 control-label required-control" for=""><spring:message
											code="swm.SLRM.employee.mobileNo" text="Mobile Number" /> </label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empMobNo"
											cssClass="form-control mandColorClass hasMobileNo" id="empMobNo" onChange="validateContactNum()"/>
									</div>

								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" for=""><spring:message
											code="swm.SLRM.employee.email" text="Email ID" /> </label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empEmailId"
											cssClass="form-control mandColorClass hasemailclass" id="empEmailId" />
									</div>
									<label class="col-sm-2 control-label required-control" for=""><spring:message
											code="swm.SLRM.employee.address" text="Address" /></label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empAddress"
											cssClass="form-control  mandColorClass  " id="empAddress" />
									</div>
								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label " for=""><spring:message
											code="swm.SLRM.employee.address1" text="Address-1" /></label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empAddress1"
											cssClass="form-control  mandColorClass  " id="empAddress1" />

									</div>
									<label class="col-sm-2 control-label required-control" for=""><spring:message
											code="swm.SLRM.employee.pincode" text="Pincode" /></label>
									<div class="col-sm-4">
										<form:input path="sLRMEmployeeMasterDto.empPincode"
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

					<apptags:backButton url="SLRMEmployeeMaster.html"></apptags:backButton>

				</div>
			</form:form>
		</div>
	</div>
</div>