<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<script type="text/javascript"
	src="js/security_management/employeeSchedulingAdd.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="" text="Employee Scheduling" /></strong>
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
			<form:form action="employeeCalendar.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmEmployeeSchedulingForm" id="frmEmployeeSchedulingForm">

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="form-group">

					<c:out value="${calanderList}"></c:out>
					<form:input path="" id="empTypeId" type="hidden" />



					<label class="control-label col-sm-2 required-control" for="">
						<spring:message code="EmployeeSchedulingDTO.empTypeId"
							text="Employee Type" />
					</label>
					<c:set var="baseLookupCode" value="EMT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="employeeSchedulingDTO.empTypeId"
						cssClass="mandColorClass form-control" hasId="true"
						selectOptionLabelCode="selectdropdown"
						disabled="${command.saveMode eq 'V'}" />

					<label class="col-sm-2 control-label required-control"> <spring:message
							code="EmployeeSchedulingDTO.vendorId" text=" VendorList" />
					</label>
					<div class="col-sm-4">
						<form:select id="vmVendorid" path="employeeSchedulingDTO.vendorId"
							data-rule-required="true" cssClass="form-control vmVendorid"
							disabled="${command.saveMode eq 'V'}">
							<form:option value="0">
								<spring:message code="select" text="Select " />
							</form:option>
							<c:forEach items="${VendorList}" var="Vendor">
								<form:option value="${Vendor.vmVendorid}"
									code="${Vendor.vmVendorcode}_ ${Vendor.vmVendorname}">${Vendor.vmVendorcode}-${Vendor.vmVendorname}
								</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">


					<apptags:date fieldclass="datepicker"
						datePath="employeeSchedulingDTO.contStaffSchFrom"
						labelCode="EmployeeSchedulingDTO.emplSchFrom"
						cssClass="mandColorClass" isMandatory="true"
						isDisabled="${command.saveMode eq 'V'}">
					</apptags:date>

					<apptags:date fieldclass="datepicker"
						datePath="employeeSchedulingDTO.contStaffSchTo"
						labelCode="EmployeeSchedulingDTO.emplSchTo"
						cssClass="mandColorClass" isMandatory="true"
						isDisabled="${command.saveMode eq 'V'}">
					</apptags:date>

				</div>


				<div class="panel-group accordion-toggle" id="judgefeeTable">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="" text="Scheduling Details" />
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
													<th width="10%"><spring:message code="" text="Sr. No." />
													</th>
													<th width="40%"><spring:message
															code="EmployeeSchedulingDTO.locId" text="Location Name" /><i
														class="text-red-1">*</i></th>
													<th width="20%"><spring:message
															code="EmployeeSchedulingDTO.cpdShiftId" text="Shift" />
														<i class="text-red-1">*</i></th>
													<th width="25%"><spring:message code=""
															text="Employee Name With Weekly-Off" /> <i
														class="text-red-1">*</i></th>

												</tr>
											</thead>
											<tbody>

												<tr>
													<td><form:input path=""
															cssClass="form-control mandColorClass" id="sequence${e}"
															value="${e+1}" disabled="true" /></td>
													<td><form:select id="location${e}"
															path="employeeSchedulingDTOList[${e}].locId"
															disabled="${command.saveMode eq 'V'}"
															cssClass="form-control " data-rule-required="true">
															<form:option value="0">
																<spring:message code="Select" text="Select" />
															</form:option>
															<c:forEach items="${location}" var="location">
																<form:option value="${location.locId}">${location.locNameEng}-${location.locArea}</form:option>
															</c:forEach>
														</form:select></td>

													<td><form:select id="cpdShiftId${e}"
															path="employeeSchedulingDTOList[${e}].cpdShiftId"
															cssClass="form-control" hasId="true"
															disabled="${command.saveMode eq 'V'}"
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

													<%-- <td><form:input
															path="employeeSchedulingDTO.contStaffName"
															cssClass="form-control mandColorClass" disabled="true" /></td> --%>

													<td><form:select id="employeeId${e}"
															path="employeeSchedulingDTOList[${e}].contStaffIdNo"
															cssClass="form-control " data-rule-required="true"
															disabled="${command.saveMode eq 'V'}">
															<form:option value="0">
																<spring:message code="Select" text="Select" />
															</form:option>
															<c:forEach items="${empName}" var="empl">
																<form:option value="${empl.contStaffIdNo}">${empl.contStaffName}-${empl.dayDesc}</form:option>
															</c:forEach>
														</form:select></td>
												</tr>
												<c:set var="e" value="${e + 1}" scope="page" />
											</tbody>
										</table>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>

				<div class="text-center clear padding-10">

					<button type="button" id="back" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="window.location.href='employeeCalendar.html'"
						id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="" text="Back" />
					</button>

				</div>
			</form:form>
		</div>
	</div>
</div>