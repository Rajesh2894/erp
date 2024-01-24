<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- End JSP Necessary Tags -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/SanitaryStaffScheduling.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/script-library.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.employee.scheduling"
						text="Employee Scheduling" /></strong>
			</h2>
		</div>
		<div id="content" class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<form:form action="SanitaryStaffScheduling.html"
				id="sanitaryStaffSchedulingId" name="sanitaryStaffScheduling"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<c:if test="${command.saveMode eq 'E' }">
					<div class="form-group">
						<label class="col-sm-2 control-label required-control " for=""><spring:message
								code='swm.sanitary.scheduleType' /></label>
						<div class="col-sm-4">
							<form:select path="employeeScheduleDto.emsType"
								cssClass="form-control mandColorClass" id="scheduleType"
								onchange="getScheduleType()" isMandatory="true" disabled="true">
								<form:option value="">
									<spring:message code="solid.waste.select" />
								</form:option>
								<form:option value="D">
									<spring:message code="swm.sanitary.report.dis" />
								</form:option>
								<%-- <form:option value="T">Task Wise</form:option> --%>
								<form:option value="V">
									<spring:message code="swm.sanitary.report.vehicle" />
								</form:option>
								<form:option value="A">
									<spring:message code="swm.sanitary.report.area" />
								</form:option>
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<apptags:date labelCode="swm.sanitary.scheduleFrom"
							fieldclass="fromDateClass"
							datePath="employeeScheduleDto.emsFromdate" isMandatory="true"
							isDisabled="true"></apptags:date>

						<apptags:date labelCode="swm.sanitary.scheduleTo"
							fieldclass="toDateClass" datePath="employeeScheduleDto.emsTodate"
							isMandatory="true" isDisabled="true"></apptags:date>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label" for=""><spring:message
								code="swm.sanitary.reccurance" text="Reccurance" /> </label>
						<div class="col-sm-4">
							<label class="radio-inline "> <form:radiobutton
									id="reccurance1" path="employeeScheduleDto.emsReocc" value="D"
									onclick="Daily();" disabled="true" /> <spring:message
									code="swm.sanitary.report.daily" />
							</label> <label class="radio-inline "> <form:radiobutton
									id="reccurance2" path="employeeScheduleDto.emsReocc" value="W"
									onclick="Weekly();" disabled="true" /> <spring:message
									code="swm.sanitary.report.Weekly" />
							</label> <label class="radio-inline "> <form:radiobutton
									id="reccurance3" path="employeeScheduleDto.emsReocc" value="M"
									onclick="Monthly()" disabled="true" /> <spring:message
									code="swm.sanitary.report.Monthly" />
							</label> <label class="radio-inline "> <form:radiobutton
									id="reccurance4" path="employeeScheduleDto.emsReocc" value="Y"
									onclick="Yearly()" disabled="true" /> <spring:message
									code="swm.sanitary.report.yearly" />
							</label>
						</div>
					</div>
					<div class="yes hidebox" style="display: none">
						<div class="form-group ">
							<label class="col-sm-2 control-label" for=""><spring:message
									code="swm.employee.scheduling.days" text="Days" /> </label>
							<div class="col-sm-8">
								<label class="checkbox-inline"> <input type="checkbox"
									id="rec0" class="days" name="day" value="1" /> <spring:message
										code="swm.sanitary.weekday.monday" />
								</label> <label class="checkbox-inline  days"> <input
									type="checkbox" id="rec1" class="days" name="day" value="2" />
									<spring:message code="swm.sanitary.weekday.tuesday" />
								</label> <label class="checkbox-inline days"> <input
									type="checkbox" id="rec2" class="days" name="day" value="3" />
									<spring:message code="swm.sanitary.weekday.wednesday" />
								</label> <label class="checkbox-inline days"> <input
									type="checkbox" id="rec3" class="days" name="day" value="4" />
									<spring:message code="swm.sanitary.weekday.thursday" />
								</label> <label class="checkbox-inline days"> <input
									type="checkbox" id="rec4" class="days" name="day" value="5" />
									<spring:message code="swm.sanitary.weekday.friday" /> &nbsp;
								</label> <label class="checkbox-inline days"> <input
									type="checkbox" id="rec5" class="days" name="day" value="6" />
									<spring:message code="swm.sanitary.weekday.saturday" />
								</label> <label class="checkbox-inline days"> <input
									type="checkbox" id="rec6" class="days" name="day" value="7" />
									<spring:message code="swm.sanitary.weekday.sunday" />
								</label>
								<form:hidden path="employeeScheduleDto.weekdays" id="weekdays" />
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${command.saveMode eq 'V' }">
					<div class="form-group">
						<label class="col-sm-2 control-label required-control " for=""><spring:message
								code='swm.sanitary.scheduleType' /></label>
						<div class="col-sm-4">
							<form:select path="employeeScheduleDto.emsType"
								cssClass="form-control mandColorClass" id="scheduleType"
								onchange="getScheduleType()" isMandatory="true" disabled="true">
								<form:option value="">
									<spring:message code="solid.waste.select" />
								</form:option>
								<form:option value="D">
									<spring:message code="swm.sanitary.report.dis" />
								</form:option>
								<%-- <form:option value="T">Task Wise</form:option> --%>
								<form:option value="V">
									<spring:message code="swm.sanitary.report.vehicle" />
								</form:option>
								<form:option value="A">
									<spring:message code="swm.sanitary.report.area" />
								</form:option>
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<apptags:date labelCode="swm.sanitary.scheduleFrom"
							fieldclass="fromDateClass"
							datePath="employeeScheduleDto.emsFromdate" isMandatory="true"
							readonly="true"></apptags:date>

						<apptags:date labelCode="swm.sanitary.scheduleTo"
							fieldclass="toDateClass" datePath="employeeScheduleDto.emsTodate"
							isMandatory="true" readonly="true"></apptags:date>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label" for=""><spring:message
								code="swm.sanitary.reccurance" text="Reccurance" /> </label>
						<div class="col-sm-4">
							<label class="radio-inline "> <form:radiobutton
									id="reccurance1" path="employeeScheduleDto.emsReocc" value="D"
									disabled="true" /> <spring:message
									code="swm.sanitary.report.daily" />
							</label> <label class="radio-inline "> <form:radiobutton
									id="reccurance2" path="employeeScheduleDto.emsReocc" value="W"
									disabled="true" /> <spring:message
									code="swm.sanitary.report.Weekly" />
							</label> <label class="radio-inline "> <form:radiobutton
									id="reccurance3" path="employeeScheduleDto.emsReocc" value="M"
									disabled="true" /> <spring:message
									code="swm.sanitary.report.Monthly" />
							</label>
						</div>
					</div>
				</c:if>
				<c:choose>
					<c:when
						test="${command.santType eq 'D' && command.saveMode eq 'E' }">
						<c:set var="d" value="0" scope="page" />
						<div class="table-responsive clear">
							<table class="table table-bordered table-striped"
								id="id_sanitaryschedulingTbl1">
								<thead>

									<tr>
										<th scope="col" width="3%"><spring:message
												code="swm.sanitary.sanitaryId" text="Sr.No." /><input
											type="hidden" id="srNo"></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.employeeName" text="Employee Name" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.disposableSite" text="Disposable Site" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.task" text="Task" /><span class="mand">*</span></th>

										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.report.schedule.date"
												text="Schedule Date" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.fromTime" text="From Time" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.toTime" text="To Time" /><span
											class="mand">*</span></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach
										items="${command.employeeScheduleDto.tbSwEmployeeScheddets}"
										var="data" varStatus="index">
										<tr class="appendableClass">
											<td align="center"><form:input path="srNo"
													cssClass="form-control mandColorClass " id="sequence${d}"
													value="${index.count}" disabled="true" /></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].empid"
													class="form-control mandColorClass " label="Select"
													id="empid${index.index}" disabled="true">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.employeeBeanList}" var="emp">
														<form:option value="${emp.empId}">${emp.fullName}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].mrfId"
													class="form-control mandColorClass " label="Select"
													id="deId${index.index}" disabled="true">
													<form:option value="0">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.mRFMasterDtoList}" var="lookup">
														<form:option value="${lookup.mrfId}">${lookup.mrfPlantName}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].emsdCollType"
													class="form-control mandColorClass " label="Select"
													id="emsdCollType${index.index}">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.emsdCollTypeList}"
														var="vesType">
														<form:option value="${vesType.lookUpId}"
															code="${vesType.lookUpCode}">${vesType.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].scheduleDate"
													class="form-control  mandColorClass"
													id="scheduldate${index.index}" disabled="true"
													maxlength="10" /></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].startTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="startTime${index.index}" /></td>

											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].endTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="endTime${index.index}" /></td>
										</tr>
									</c:forEach>
									<c:set var="d" value="${d + 1}" scope="page" />
								</tbody>
							</table>
						</div>
					</c:when>
					<c:when
						test="${command.santType eq 'T' && command.saveMode eq 'E'}">
						<c:set var="d" value="0" scope="page" />
						<div class="table-responsive clear">
							<table class="table table-bordered table-striped"
								id="id_sanitaryschedulingTbl2">
								<thead>

									<tr>
										<th scope="col" width="3%"><spring:message
												code="swm.sanitary.sanitaryId" text="Sr.No." /></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.employeeName" text="Employee Name" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.task" text="Task" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.location" text="Location" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.vehicle" text="Vehicle" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.report.schedule.date"
												text="Schedule Date" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.report.schedule.date"
												text="Schedule Date" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.fromTime" text="From Time" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.toTime" text="To Time" /><span class="mand">*</span></th>

									</tr>
								</thead>
								<tbody id="id">
									<c:forEach
										items="${command.employeeScheduleDto.tbSwEmployeeScheddets}"
										var="data" varStatus="index">

										<tr class="appendableClass2">
											<td align="center"><form:input path="srNo"
													cssClass="form-control mandColorClass " id="sequence${d}"
													value="${index.count}" disabled="true" /></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].empid"
													class="form-control mandColorClass " label="Select"
													id="empid${index.index}" disabled="true">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.employeeBeanList}" var="emp">
														<form:option value="${emp.empId}">${emp.fullName}</form:option>
													</c:forEach>
												</form:select></td>

											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].emsdCollType"
													class="form-control mandColorClass " label="Select"
													id="emsdCollType${index.index}" disabled="true">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.emsdCollTypeList}"
														var="vesType">
														<form:option value="${vesType.lookUpId}"
															code="${vesType.lookUpCode}">${vesType.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].locId"
													class="form-control mandColorClass " label="Select"
													id="locId${index.index}" disabled="true">
													<form:option value="0">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.locList}" var="lookUp">
														<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].veId"
													class="form-control mandColorClass " label="Select"
													id="veId${index.index}" disabled="true">
													<form:option value="0">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.vehicleMasterList}"
														var="lookup">
														<form:option value="${lookup.veId}">${lookup.veNo}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].scheduleDate"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="startTime${index.index}" disabled="true" /></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].startTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="startTime${index.index}" /></td>

											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].endTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="endTime${index.index}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:when>

					<c:when
						test="${command.santType eq 'V' && command.saveMode eq 'E'}">
						<c:set var="d" value="0" scope="page" />
						<div class="table-responsive clear">
							<table class="table table-bordered table-striped"
								id="id_sanitaryschedulingTbl3">
								<thead>

									<tr>
										<th scope="col" width="3%"><spring:message
												code="swm.sanitary.sanitaryId" text="Sr.No." /></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.employeeName" text="Employee Name" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.vehicleNo" text="Vehicle No." /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.route" text="Route" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.task" text="Task" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.report.schedule.date"
												text="Schedule Date" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.fromTime" text="From Time" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.toTime" text="To Time" /><span
											class="mand">*</span></th>

									</tr>

								</thead>
								<tbody>
									<c:forEach
										items="${command.employeeScheduleDto.tbSwEmployeeScheddets}"
										var="data" varStatus="index">
										<tr>
											<td align="center"><form:input path="srNo"
													cssClass="form-control mandColorClass " id="sequence${d}"
													value="${index.count}" disabled="true" /></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].empid"
													class="form-control mandColorClass " label="Select"
													id="empid${index.index}" disabled="true">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.employeeBeanList}" var="emp">
														<form:option value="${emp.empId}">${emp.fullName}</form:option>
													</c:forEach>
												</form:select></td>

											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].veId"
													class="form-control mandColorClass " label="Select"
													id="veId${index.index}" disabled="true">
													<form:option value="0">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.vehicleMasterList}"
														var="lookup">
														<form:option value="${lookup.veId}">${lookup.veNo}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].beatId"
													class="form-control mandColorClass " label="Select"
													id="roId${index.index}" disabled="true">
													<form:option value="0">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.routeMasterList}" var="lookup">
														<form:option value="${lookup.beatId}">${lookup.beatName}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].emsdCollType"
													class="form-control mandColorClass " label="Select"
													id="emsdCollType${index.index}">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.emsdCollTypeList}"
														var="vesType">
														<form:option value="${vesType.lookUpId}"
															code="${vesType.lookUpCode}">${vesType.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].scheduleDate"
													class="form-control  mandColorClass" id="scheduldate"
													disabled="true" /></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].startTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="startTime${index.index}" /></td>

											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].endTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="endTime${index.index}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:when>
					<c:when
						test="${command.santType eq 'A' && command.saveMode eq 'E'}">
						<c:set var="d" value="0" scope="page" />
						<div class="table-responsive clear">
							<table id="unitDetailTable"
								class="table table-striped table-bordered appendableClass unitDetails">
								<thead>

									<tr>
										<th scope="col" width="3%"><spring:message
												code="swm.vehicleId" text="Sr.No." /></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.employeeName" text="Employee Name" /><span
											class="mand">*</span></th>
										<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
											pathPrefix="employeeScheduleDto.tbSwEmployeeScheddets[${d}].codWard"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control required-control" showAll="false"
											hasTableForm="true" showData="false" columnWidth="10%" />
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.task" text="Task" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.report.schedule.date"
												text="Schedule Date" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.fromTime" text="From Time" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.toTime" text="To Time" /><span
											class="mand">*</span></th>

									</tr>
								</thead>
								<tbody>
									<c:forEach
										items="${command.employeeScheduleDto.tbSwEmployeeScheddets}"
										var="data" varStatus="index">
										<tr class="firstUnitRow">
											<td align="center"><form:input path="srNo"
													cssClass="form-control mandColorClass " id="sequence${d}"
													value="${index.count}" disabled="true" /></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].empid"
													class="form-control mandColorClass " label="Select"
													id="empid${index.index}" disabled="true">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.employeeBeanList}" var="emp">
														<form:option value="${emp.empId}">${emp.fullName}</form:option>
													</c:forEach>
												</form:select></td>
											<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
												pathPrefix="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].codWard"
												isMandatory="true" hasLookupAlphaNumericSort="true"
												hasSubLookupAlphaNumericSort="true"
												cssClass="form-control required-control " showAll="false"
												hasTableForm="true" showData="true" columnWidth="10%"
												disabled="true" />
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].emsdCollType"
													class="form-control mandColorClass " label="Select"
													id="emsdCollType${index.index}">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.emsdCollTypeList}"
														var="vesType">
														<form:option value="${vesType.lookUpId}"
															code="${vesType.lookUpCode}">${vesType.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].scheduleDate"
													class="form-control  mandColorClass" id="scheduldate"
													disabled="true" /></td>

											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].startTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="startTime${index.index}" /></td>

											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].endTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="endTime${index.index}" /></td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:when>
				</c:choose>
				<!-----------------------------------------View----------------------------------------------------------->
				<c:choose>
					<c:when
						test="${command.santType eq 'D' && command.saveMode eq 'V' }">
						<c:set var="d" value="0" scope="page" />
						<div class="table-responsive clear">
							<table class="table table-bordered table-striped"
								id="id_sanitaryschedulingTbl1">
								<thead>
									<tr>
										<th scope="col" width="3%"><spring:message
												code="swm.sanitary.sanitaryId" text="Sr.No." /><input
											type="hidden" id="srNo"></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.employeeName" text="Employee Name" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.disposableSite" text="Disposable Site" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.task" text="Task" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.report.schedule.date"
												text="Schedule Date" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.fromTime" text="From Time" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.toTime" text="To Time" /><span
											class="mand">*</span></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach
										items="${command.employeeScheduleDto.tbSwEmployeeScheddets}"
										var="data" varStatus="index">
										<tr class="appendableClass">
											<td align="center"><form:input path="srNo"
													cssClass="form-control mandColorClass " id="sequence${d}"
													value="${index.count}" disabled="true" /></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].empid"
													class="form-control mandColorClass " disabled="true"
													label="Select" id="empid${index.index}">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.employeeBeanList}" var="emp">
														<form:option value="${emp.empId}">${emp.fullName}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].mrfId"
													class="form-control mandColorClass " label="Select"
													id="deId${index.index}" disabled="true">
													<form:option value="0">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.mRFMasterDtoList}" var="lookup">
														<form:option value="${lookup.mrfId}">${lookup.mrfPlantName}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].emsdCollType"
													class="form-control mandColorClass " label="Select"
													id="emsdCollType${index.index}" disabled="true">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.emsdCollTypeList}"
														var="vesType">
														<form:option value="${vesType.lookUpId}"
															code="${vesType.lookUpCode}">${vesType.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].scheduleDate"
													class="form-control  mandColorClass" maxlength="10"
													id="startTime${index.index}" disabled="true" /></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].startTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="startTime${index.index}" disabled="true" /></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].endTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="endTime${index.index}" disabled="true" /></td>
										</tr>
									</c:forEach>
									<c:set var="d" value="${d + 1}" scope="page" />
								</tbody>
							</table>
						</div>
					</c:when>
					<c:when
						test="${command.santType eq 'T' && command.saveMode eq 'V'}">
						<c:set var="d" value="0" scope="page" />
						<div class="table-responsive clear">
							<table class="table table-bordered table-striped"
								id="id_sanitaryschedulingTbl2">
								<thead>

									<tr>
										<th scope="col" width="3%"><spring:message
												code="swm.sanitary.sanitaryId" text="Sr.No." /></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.employeeName" text="Employee Name" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.task" text="Task" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.location" text="Location" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.vehicle" text="Vehicle" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.report.schedule.date"
												text="Schedule Date" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.fromTime" text="From Time" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.toTime" text="To Time" /><span class="mand">*</span></th>
									</tr>
								</thead>
								<tbody id="id">
									<c:forEach
										items="${command.employeeScheduleDto.tbSwEmployeeScheddets}"
										var="data" varStatus="index">
										<tr class="appendableClass2">
											<td align="center"><form:input path="srNo"
													cssClass="form-control mandColorClass " id="sequence${d}"
													value="${index.count}" disabled="true" /></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].empid"
													class="form-control mandColorClass " label="Select"
													id="empid${index.index}" disabled="true">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.employeeBeanList}" var="emp">
														<form:option value="${emp.empId}">${emp.fullName}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].emsdCollType"
													class="form-control mandColorClass " label="Select"
													id="emsdCollType${index.index}" disabled="true">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.emsdCollTypeList}"
														var="vesType">
														<form:option value="${vesType.lookUpId}"
															code="${vesType.lookUpCode}">${vesType.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].locId"
													class="form-control mandColorClass " label="Select"
													id="locId${index.index}" disabled="true">
													<form:option value="0">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.locList}" var="lookUp">
														<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].veId"
													class="form-control mandColorClass " label="Select"
													id="veId${index.index}" disabled="true">
													<form:option value="0">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.vehicleMasterList}"
														var="lookup">
														<form:option value="${lookup.veId}">${lookup.veNo}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].scheduleDate"
													class="form-control  mandColorClass" id="scheduldate"
													readonly="true" /></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].startTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="startTime${index.index}" disabled="true" /></td>

											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].endTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="endTime${index.index}" disabled="true" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:when>
					<c:when
						test="${command.santType eq 'V' && command.saveMode eq 'V'}">
						<c:set var="d" value="0" scope="page" />
						<div class="table-responsive clear">
							<table class="table table-bordered table-striped"
								id="id_sanitaryschedulingTbl3">
								<thead>

									<tr>
										<th scope="col" width="3%"><spring:message
												code="swm.sanitary.sanitaryId" text="Sr.No." /></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.employeeName" text="Employee Name" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.vehicleNo" text="Vehicle No." /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.route" text="Route" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.task" text="Task" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.report.schedule.date"
												text="Schedule Date" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.fromTime" text="From Time" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.toTime" text="To Time" /><span
											class="mand">*</span></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach
										items="${command.employeeScheduleDto.tbSwEmployeeScheddets}"
										var="data" varStatus="index">
										<tr>
											<td align="center"><form:input path="srNo"
													cssClass="form-control mandColorClass " id="sequence${d}"
													value="${index.count}" disabled="true" /></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].empid"
													class="form-control mandColorClass " label="Select"
													id="empid${index.index}" disabled="true">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.employeeBeanList}" var="emp">
														<form:option value="${emp.empId}">${emp.fullName}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].veId"
													class="form-control mandColorClass " label="Select"
													id="veId${index.index}" disabled="true">
													<form:option value="0">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.vehicleMasterList}"
														var="lookup">
														<form:option value="${lookup.veId}">${lookup.veNo}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].beatId"
													class="form-control mandColorClass " label="Select"
													id="roId${index.index}" disabled="true">
													<form:option value="0">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.routeMasterList}" var="lookup">
														<form:option value="${lookup.beatId}">${lookup.beatName}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].emsdCollType"
													class="form-control mandColorClass " label="Select"
													id="emsdCollType${index.index}" disabled="true">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.emsdCollTypeList}"
														var="vesType">
														<form:option value="${vesType.lookUpId}"
															code="${vesType.lookUpCode}">${vesType.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].scheduleDate"
													class="form-control  mandColorClass" id="scheduldate"
													readonly="true" /></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].startTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="startTime${index.index}" disabled="true" /></td>

											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].endTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="endTime${index.index}" disabled="true" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:when>
					<c:when
						test="${command.santType eq 'A' && command.saveMode eq 'V'}">
						<c:set var="d" value="0" scope="page" />
						<div class="table-responsive clear">
							<table id="unitDetailTable"
								class="table table-striped table-bordered appendableClass unitDetails">
								<thead>
									<tr>
										<th scope="col" width="3%"><spring:message
												code="swm.vehicleId" text="Sr.No." /></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.employeeName" text="Employee Name" /><span
											class="mand">*</span></th>
										<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
											pathPrefix="employeeScheduleDto.tbSwEmployeeScheddets[${d}].codWard"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control required-control" showAll="false"
											hasTableForm="true" showData="false" columnWidth="10%" />
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.task" text="Task" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.report.schedule.date"
												text="Schedule Date" /><span class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.fromTime" text="From Time" /><span
											class="mand">*</span></th>
										<th scope="col" width="10%"><spring:message
												code="swm.sanitary.toTime" text="To Time" /><span
											class="mand">*</span></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach
										items="${command.employeeScheduleDto.tbSwEmployeeScheddets}"
										var="data" varStatus="index">
										<tr class="firstUnitRow">
											<td align="center"><form:input path="srNo"
													cssClass="form-control mandColorClass " id="sequence${d}"
													value="${index.count}" disabled="true" /></td>
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].empid"
													class="form-control mandColorClass " label="Select"
													id="empid${index.index}" disabled="true">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.employeeBeanList}" var="emp">
														<form:option value="${emp.empId}">${emp.fullName}</form:option>
													</c:forEach>
												</form:select></td>
											<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
												pathPrefix="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].codWard"
												isMandatory="true" hasLookupAlphaNumericSort="true"
												hasSubLookupAlphaNumericSort="true"
												cssClass="form-control required-control " showAll="false"
												hasTableForm="true" showData="true" columnWidth="10%"
												disabled="true" />
											<td align="center"><form:select
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].emsdCollType"
													class="form-control mandColorClass " label="Select"
													id="emsdCollType${index.index}" disabled="true">
													<form:option value="">
														<spring:message code="solid.waste.select" />
													</form:option>
													<c:forEach items="${command.emsdCollTypeList}"
														var="vesType">
														<form:option value="${vesType.lookUpId}"
															code="${vesType.lookUpCode}">${vesType.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].scheduleDate"
													class="form-control  mandColorClass" id="scheduldate"
													readonly="true" /></td>
											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].startTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="startTime${index.index}" disabled="true" /></td>

											<td align="center"><form:input
													path="employeeScheduleDto.tbSwEmployeeScheddets[${index.index}].endTime"
													class="form-control datetimepicker3 mandColorClass"
													maxlength="10" id="endTime${index.index}" disabled="true" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:when>
				</c:choose>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="updateData(this);" id="btnSave">
							<spring:message code="swm.sanitary.submit" text="Submit" />
						</button>
					</c:if>
					<apptags:backButton url="SanitaryStaffScheduling.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
