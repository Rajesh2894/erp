<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript"
	src="js/security_management/contractualEmpData.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/security_management/employeeOverTime.js"></script>
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
<style>
<!--

-->
input[readonly] {
        background-color: white !important;
    }
</style>


<div class="empAttendance">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">

		<div class="widget">
			<div class="widget-header">
				<h2>
					<strong><spring:message code="EmployeeOvertimeEntryDTO.form.name"
							text="Security Employee Overtime Entry" /></strong>
				</h2>
			</div>

			<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message code="leadlift.master.ismand" />
				</span>
			</div>
			<!-- End mand-label -->
				<form:form action="EmployeeOvertimeEntry.html" method="POST"
					commandName="command" class="form-horizontal form"
					name="frmContractualStaffMasterForm"
					id="frmContractualStaffMasterForm">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv" style="display: none;">
						<i class="fa fa-plus-circle"></i>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control" for="">
							<spring:message code="EmployeeSchedulingDTO.empTypeId"
								text="Employee Type" />
						</label>
						<c:set var="baseLookupCode" value="EMT" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="employeeSchedulingDTO.empTypeId"
							cssClass="mandColorClass form-control" hasId="true"
							selectOptionLabelCode="selectdropdown" />

						<label class="col-sm-2 control-label required-control"> <spring:message
								code="EmployeeSchedulingDTO.vendorId" text=" VendorList" />
						</label>
						<div class="col-sm-4">
							<form:select id="vmVendorid"
								path="employeeSchedulingDTO.vendorId" data-rule-required="true"
								cssClass="form-control vmVendorid">
								<form:option value="0">
									<spring:message code="Select" text="Select " />
								</form:option>
								<c:forEach items="${command.vendorList}" var="Vendor">
									<form:option value="${Vendor.vmVendorid}"
										code="${Vendor.vmVendorcode}_ ${Vendor.vmVendorname}">${Vendor.vmVendorcode}-${Vendor.vmVendorname}
								</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div class="form-group">
						<label class="control-label col-sm-2 required-control"
							for="location"> <spring:message
								code="EmployeeSchedulingDTO.locId" text="Current Location" /></label>
						<div class="col-sm-4">
							<form:select id="location" path="employeeSchedulingDTO.locId"
								cssClass="form-control chosen-select-no-results ">

								<form:option value="">
									<spring:message code="Select" text="Select" />
								</form:option>
								<c:forEach items="${location}" var="location">
									<form:option value="${location.locId}">${location.locNameEng}-${location.locArea}</form:option>
								</c:forEach>
							</form:select>
						</div>

						<label class="control-label col-sm-2 required-control" for="">
							<spring:message code="EmployeeSchedulingDTO.cpdShiftId"
								text="Current Shift" />
						</label>

						<c:set var="baseLookupCode" value="SHT" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCode)}"
							path="employeeSchedulingDTO.cpdShiftId"
							cssClass="mandColorClass form-control" hasId="true"
							selectOptionLabelCode="selectdropdown" />
					</div>

					<div class="form-group">
						<apptags:date fieldclass="fromDateClass"
							datePath="employeeSchedulingDTO.contStaffSchFrom"
							labelCode="EmployeeSchedulingDTO.emplSchFrom"
							readonly = "false"
							cssClass="mandColorClass" isMandatory="true">
						</apptags:date>
						<label class="col-sm-2 control-label required-control"
							for="contStaffSchTo"><spring:message
								code="EmployeeSchedulingDTO.emplSchTo"
								text="Schedule To" /></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input
									cssClass="form-control mandColorClass"
									id="contStaffSchTo" readonly='true'
									path="employeeSchedulingDTO.contStaffSchTo"
									placeholder='DD/MM/YYYY'></form:input>
								<span class="input-group-addon"><i
									class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>
					
				

					<div class="text-center clear padding-10">

						<button type="button" id="search" class="btn btn-blue-2"
							onclick="searchEmployeeForOverTime(this)" title='<spring:message code="DeploymentOfStaffDTO.form.search" text="Search" />'>
							<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
							<spring:message code="DeploymentOfStaffDTO.form.search" text="Search" />
						</button>

						<button type="button" id="reset"
							onclick="window.location.href='EmployeeOvertimeEntry.html'"
							class="btn btn-warning" title='<spring:message code="DeploymentOfStaffDTO.form.reset" text="Reset" />'>
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="DeploymentOfStaffDTO.form.reset" text="Reset" />
						</button>
						
						<button type="button" id="back"
							class="button-input btn btn-danger" name="button-Cancel"
							value="Cancel" style=""
							onclick="window.location.href='AdminHome.html'" title='<spring:message code="EmployeeSchedulingDTO.back" text="Back" />'
							id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="EmployeeSchedulingDTO.back" text="Back" />
						</button>

					</div>

					<div class="panel-group accordion-toggle" id="judgefeeTable">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-target="#a1" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#a1"> <spring:message
											code="securityManagement.scheduling.details" text="Scheduling Details" />
									</a>
								</h4>
							</div>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">

									<c:set var="e" value="0" scope="page" />
									<div class="">

										<div class=" margin-top-10">
											<table
											class="table table-striped table-condensed table-bordered"
											id="frmEmployeeSchedulingTbl">

											<thead>
												<tr>
													<th width="10%"><spring:message code="securityManagement.scheduling.details"
															text="Schedule Date" /></th>
													<th width="10%"><spring:message code="securityManagement.employee.name"
															text="Employee Name" /><i class="text-red-1">*</i></th>
													<th width="15%"><spring:message
															code="EmployeeSchedulingDTO.locId" text="Location Name" /><i
														class="text-red-1">*</i></th>
													<th width="20%"><spring:message
															code="EmployeeSchedulingDTO.cpdShiftId" text="Shift" />
														<i class="text-red-1">*</i></th>
													<th width="20%"><spring:message code="securityManagement.overtime.shift"
															text="Over Time Shift" /> <i class="text-red-1">*</i></th>
													<th width="15%"><spring:message code="securityManagement.overtime.inHR"
															text="Over Time(In Hr)" /> <i class="text-red-1">*</i></th>
													<th width="20%"><spring:message code="DeploymentOfStaffDTO.remarkApproval"
															text="Remark" /></th>		

													</tr>
											</thead>

												<c:if test="${not empty command.detList}">
													<tbody>
														<c:forEach var="taxData" items="${command.detList}"
															varStatus="status">
															<tr>
														       <fmt:formatDate
																value="${taxData.shiftDate}" pattern="dd/MM/yyyy" var="date" />
																<td class="text-center"><form:input
																		cssClass="form-control mandColorClass datepicker"
																		id="contStaffSchFrom${e}" disabled="true"
																		path="detList[${e}].shiftDate" value="${date}"
																		placeholder='DD/MM/YYYY'></form:input></td>
																<td><form:select id="employeeId${e}"
																		path="detList[${e}].contStaffIdNo" disabled="true"
																		cssClass="form-control " data-rule-required="true">
																		<form:option value="0">
																			<spring:message code="Select" text="Select" />
																		</form:option>
																		<c:forEach items="${empName}" var="empl">
																			<form:option value="${empl.contStaffIdNo}">${empl.contStaffName}</form:option>
																		</c:forEach>
																	</form:select></td>


																<td><form:select id="location${e}" disabled="true"
																		path="detList[${e}].locId" cssClass="form-control "
																		data-rule-required="true">
																		<form:option value="0">
																			<spring:message code="Select" text="Select" />
																		</form:option>
																		<c:forEach items="${command.location}" var="location">
																			<form:option value="${location.locId}">${location.locNameEng}-${location.locArea}</form:option>
																		</c:forEach>
																	</form:select></td>

																<td><form:select id="cpdShiftId${e}"
																		path="detList[${e}].cpdShiftId" disabled="true"
																		cssClass="form-control" hasId="true"
																		data-rule-required="false">
																		<c:set var="baseLookupCode" value="SHT" />
																		<form:option value="0">
																			<spring:message code="Select" text="Select" />
																		</form:option>
																		<c:forEach
																			items="${command.getLevelData(baseLookupCode)}"
																			var="lookUp">
																			<form:option value="${lookUp.lookUpId}"
																				code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																		</c:forEach>
																	</form:select></td>

																<td><form:select id="otShiftId${e}"
																		path="detList[${e}].otShiftId" disabled="false"
																		cssClass="form-control" hasId="true"
																		data-rule-required="false">
																		<c:set var="baseLookupCode" value="SHT" />
																		<form:option value="0">
																			<spring:message code="Select" text="Select" />
																		</form:option>
																		<c:forEach
																			items="${command.getLevelData(baseLookupCode)}"
																			var="lookUp">
																			<spring:eval
				expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getNonHierarchicalLookUpObject(${command.employeeSchedulingDTO.cpdShiftId},${UserSession.organisation}).getLookUpCode()" var="lookUpCode"/>
																			<c:if test="${lookUp.lookUpCode ne lookUpCode}">
																				<form:option value="${lookUp.lookUpId}"
																					code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																			</c:if>
																		</c:forEach>	
																			
																	</form:select></td>
					
																<td><form:input
																		id="otShifthr${e}"
																		path="detList[${e}].otHour"
																		cssClass="text-right form-control datetimepicker3"
																		disabled="false" onkeypress="return hasAmount(event, this, 2, 2)" /></td>
																<td><form:input
																		path="detList[${e}].remarks" maxlength="500"
																		cssClass="text-right form-control"
																		disabled="false" /></td>		
															</tr>
															<c:set var="e" value="${e + 1}" scope="page" />
														</c:forEach>
													</tbody>
												</c:if>
											</table>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>


					<div class="text-center clear padding-10">
					<c:if test="${not empty command.detList}">
						<button type="button" id="saveBtn"
							class="btn btn-success btn-submit" onclick="confirmToSave(this)" title='<spring:message code="EmployeeSchedulingDTO.save" />'
							value="Save">
							<i class="fa fa-floppy-o" aria-hidden="true"></i>
							<spring:message code="EmployeeSchedulingDTO.save" />
						</button>
						
						<button type="button" id="back"
							class="button-input btn btn-danger" name="button-Cancel"
							value="Cancel" style=""
							onclick="window.location.href='AdminHome.html'" title='<spring:message code="EmployeeSchedulingDTO.back" text="Back" />'
							id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="EmployeeSchedulingDTO.back" text="Back" />
						</button>
					</c:if>

						

					</div>

				</form:form>
			</div>

		</div>
	</div>
</div>