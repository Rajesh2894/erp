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
<c:choose>
	<c:when test="${command.santType eq 'D'}">
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
								code="swm.sanitary.fromTime" text="From Time" /><span
							class="mand">*</span></th>
						<th scope="col" width="10%"><spring:message
								code="swm.sanitary.toTime" text="To Time" /><span class="mand">*</span></th>

						<th scope="col" width="6%"><a href="javascript:void(0);"
							data-toggle="tooltip" data-placement="top"
							onclick="addEntryData('id_sanitaryschedulingTbl1');"
							class=" btn btn-success btn-sm"><i class="fa fa-plus-circle">
							</i></a></th>
					</tr>

				</thead>
				<tbody>
					<tr class="appendableClass">
						<td align="center"><form:input path="command.srNo"
								cssClass="form-control mandColorClass " id="sequence${d}"
								value="${d+1}" disabled="true" /></td>
						<td align="center"><form:select
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].empid"
								class="form-control mandColorClass " label="Select"
								id="empid${d}">
								<form:option value="">
									<spring:message code="solid.waste.select" />
								</form:option>
								<c:forEach items="${command.employeeBeanList}" var="emp">
									<form:option value="${emp.empId}">${emp.fullName}</form:option>
								</c:forEach>
							</form:select></td>
						<td align="center"><form:select
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].mrfId"
								class="form-control mandColorClass " label="Select"
								id="deId${d}">
								<form:option value="">
									<spring:message code="solid.waste.select" />
								</form:option>
								<c:forEach items="${command.mRFMasterDtoList}" var="lookup">
									<form:option value="${lookup.mrfId}">${lookup.mrfPlantName}</form:option>
								</c:forEach>
							</form:select></td>
						<td align="center"><form:select
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].emsdCollType"
								class="form-control mandColorClass " label="Select"
								id="emsdCollType${d}">
								<form:option value="">
									<spring:message code="solid.waste.select" />
								</form:option>
								<c:forEach items="${command.emsdCollTypeList}" var="vesType">
									<form:option value="${vesType.lookUpId}"
										code="${vesType.lookUpCode}">${vesType.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>
						<td align="center"><form:input
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].startTime"
								class="form-control datetimepicker3 mandColorClass"
								maxlength="10" id="startTime${d}" /></td>

						<td align="center"><form:input
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].endTime"
								class="form-control datetimepicker3 mandColorClass"
								maxlength="10" id="endTime${d}" /></td>
						<td align="center"><a class="btn btn-danger btn-sm delButton"
							onclick="deleteEntry('id_sanitaryschedulingTbl1',$(this),'removedIds');">
								<i class="fa fa-minus"></i>
						</a></td>
					</tr>
					<c:set var="d" value="${d + 1}" scope="page" />
				</tbody>
			</table>
		</div>
	</c:when>
	<c:when test="${command.santType eq 'T'}">
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
								code="swm.sanitary.vehicle" text="Vehicle" /><span class="mand">*</span></th>
						<th scope="col" width="10%"><spring:message
								code="swm.sanitary.fromTime" text="From Time" /><span
							class="mand">*</span></th>
						<th scope="col" width="10%"><spring:message code="swm.toTime"
								text="To Time" /><span class="mand">*</span></th>

						<th scope="col" width="6%"><a href="javascript:void(0);"
							data-toggle="tooltip" data-placement="top"
							onclick="addEntryData('id_sanitaryschedulingTbl2');"
							class=" btn btn-success btn-sm"><i class="fa fa-plus-circle">
							</i></a></th>
					</tr>
				</thead>
				<tbody id="id">
					<tr class="appendableClass2">
						<td align="center"><form:input path="command.srNo"
								cssClass="form-control mandColorClass " id="sequence${d}"
								value="${d+1}" disabled="true" /></td>
						<td align="center"><form:select
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].empid"
								class="form-control mandColorClass " label="Select"
								id="empid${d}">
								<form:option value="">
									<spring:message code="solid.waste.select" />
								</form:option>
								<c:forEach items="${command.employeeBeanList}" var="emp">
									<form:option value="${emp.empId}">${emp.fullName}</form:option>
								</c:forEach>
							</form:select></td>

						<td align="center"><form:select
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].emsdCollType"
								class="form-control mandColorClass " label="Select"
								id="emsdCollType${d}">
								<form:option value="">
									<spring:message code="solid.waste.select" />
								</form:option>
								<c:forEach items="${command.emsdCollTypeList}" var="vesType">
									<form:option value="${vesType.lookUpId}"
										code="${vesType.lookUpCode}">${vesType.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>
						<td align="center"><form:select
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].locId"
								class="form-control mandColorClass " label="Select"
								id="locId${d}">
								<form:option value="0">
									<spring:message code="solid.waste.select" />
								</form:option>
								<c:forEach items="${command.locList}" var="lookUp">
									<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
								</c:forEach>
							</form:select></td>
						<td align="center"><form:select
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].veId"
								class="form-control mandColorClass " label="Select"
								id="veId${d}">
								<form:option value="0">
									<spring:message code="solid.waste.select" />
								</form:option>
								<c:forEach items="${command.vehicleMasterList}" var="lookup">
									<form:option value="${lookup.veId}">${lookup.veNo}</form:option>
								</c:forEach>
							</form:select></td>

						<td align="center"><form:input
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].startTime"
								class="form-control datetimepicker3 mandColorClass"
								maxlength="10" id="startTime${d}" /></td>

						<td align="center"><form:input
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].endTime"
								class="form-control datetimepicker3 mandColorClass"
								maxlength="10" id="endTime${d}" /></td>


						<td align="center"><a class="btn btn-danger btn-sm delButton"
							onclick="deleteEntry('id_sanitaryschedulingTbl2',$(this),'removedIds');">
								<i class="fa fa-minus"></i>
						</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</c:when>
	<c:when test="${command.santType eq 'V'}">
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
								code="swm.sanitary.fromTime" text="From Time" /><span
							class="mand">*</span></th>
						<th scope="col" width="10%"><spring:message
								code="swm.sanitary.toTime" text="To Time" /><span class="mand">*</span></th>

						<th scope="col" width="6%"><a href="javascript:void(0);"
							data-toggle="tooltip" data-placement="top"
							onclick="addEntryData('id_sanitaryschedulingTbl3');"
							class=" btn btn-success btn-sm"><i class="fa fa-plus-circle">
							</i></a></th>
					</tr>

				</thead>
				<tbody>
					<tr>
						<td align="center"><form:input path="command.srNo"
								cssClass="form-control mandColorClass " id="sequence${d}"
								value="${d+1}" disabled="true" /></td>
						<td align="center"><form:select
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].empid"
								class="form-control mandColorClass " label="Select"
								id="empid${d}">
								<form:option value="0">
									<spring:message code="solid.waste.select" />
								</form:option>
								<c:forEach items="${command.employeeBeanList}" var="emp">
									<form:option value="${emp.empId}">${emp.fullName}</form:option>
								</c:forEach>
							</form:select></td>

						<td align="center"><form:select
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].veId"
								class="form-control mandColorClass " label="Select"
								id="veId${d}">
								<form:option value="0">
									<spring:message code="solid.waste.select" />
								</form:option>
								<c:forEach items="${command.vehicleMasterList}" var="lookup">
									<form:option value="${lookup.veId}">${lookup.veNo}</form:option>
								</c:forEach>
							</form:select></td>



						<td align="center"><form:select
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].beatId"
								class="form-control mandColorClass " label="Select"
								id="roId${d}">
								<form:option value="0">
									<spring:message code="solid.waste.select" />
								</form:option>
								<c:forEach items="${command.routeMasterList}" var="lookup">
									<form:option value="${lookup.beatId}">${lookup.beatName}</form:option>
								</c:forEach>
							</form:select></td>


						<td align="center"><form:select
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].emsdCollType"
								class="form-control mandColorClass " label="Select"
								id="emsdCollType${d}">
								<form:option value="0">
									<spring:message code="solid.waste.select" />
								</form:option>
								<c:forEach items="${command.emsdCollTypeList}" var="vesType">
									<form:option value="${vesType.lookUpId}"
										code="${vesType.lookUpCode}">${vesType.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>

						<td align="center"><form:input
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].startTime"
								class="form-control datetimepicker3 mandColorClass"
								maxlength="10" id="startTime${d}" /></td>

						<td align="center"><form:input
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].endTime"
								class="form-control datetimepicker3 mandColorClass"
								maxlength="10" id="endTime${d}" /></td>


						<td align="center"><a class="btn btn-danger btn-sm delButton"
							onclick="deleteEntry('id_sanitaryschedulingTbl3',$(this),'removedIds');">
								<i class="fa fa-minus"></i>
						</a></td>

					</tr>
				</tbody>
			</table>
		</div>
	</c:when>
	<c:otherwise>
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
							pathPrefix="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].codWard"
							isMandatory="true" hasLookupAlphaNumericSort="true"
							isNotInForm="true" hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control" showAll="false"
							hasTableForm="true" showData="false" columnWidth="10%" />
						<th scope="col" width="10%"><spring:message
								code="swm.sanitary.task" text="Task" /><span class="mand">*</span></th>
						<th scope="col" width="10%"><spring:message
								code="swm.sanitary.fromTime" text="From Time" /><span
							class="mand">*</span></th>
						<th scope="col" width="10%"><spring:message
								code="swm.sanitary.toTime" text="To Time" /><span class="mand">*</span></th>

						<th class="text-center" scope="col" width="6%"><a
							href="javascript:void(0);" data-toggle="tooltip"
							data-original-title="Add"
							class="addCF btn btn-success btn-sm unit" id="unitDetailTable"><i
								class="fa fa-plus-circle"></i></a></th>
					</tr>
				</thead>
				<tbody>
					<tr class="firstUnitRow">
						<td align="center"><form:input path="command.srNo"
								cssClass="form-control mandColorClass " id="sequence"
								value="${d+1}" disabled="true" /></td>
						<td align="center"><form:select
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].empid"
								class="form-control mandColorClass " label="Select" id="empid">
								<form:option value="">
									<spring:message code="solid.waste.select" />
								</form:option>
								<c:forEach items="${command.employeeBeanList}" var="emp">
									<form:option value="${emp.empId}">${emp.fullName}</form:option>
								</c:forEach>
							</form:select></td>
						<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
							pathPrefix="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].codWard"
							isMandatory="true" hasLookupAlphaNumericSort="true"
							isNotInForm="true" hasSubLookupAlphaNumericSort="true"
							cssClass="form-control required-control " showAll="false"
							hasTableForm="true" showData="true" columnWidth="10%" />
						<td align="center"><form:select
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].emsdCollType"
								class="form-control mandColorClass " label="Select"
								id="emsdCollType">
								<form:option value="">
									<spring:message code="solid.waste.select" />
								</form:option>
								<c:forEach items="${command.emsdCollTypeList}" var="vesType">
									<form:option value="${vesType.lookUpId}"
										code="${vesType.lookUpCode}">${vesType.descLangFirst}</form:option>
								</c:forEach>
							</form:select></td>
						<td align="center"><form:input
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].startTime"
								class="form-control datetimepicker3 mandColorClass"
								maxlength="10" id="startTime" /></td>
						<td align="center"><form:input
								path="command.employeeScheduleDto.tbSwEmployeeScheddets[${d}].endTime"
								class="form-control datetimepicker3 mandColorClass"
								maxlength="10" id="endTime" /></td>
						<td align="center"><a class="btn btn-danger btn-sm delButton"
							onclick="deleteEntry2('unitDetailTable',$(this),'removedIds');">
								<i class="fa fa-minus"></i>
						</a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</c:otherwise>
</c:choose>
