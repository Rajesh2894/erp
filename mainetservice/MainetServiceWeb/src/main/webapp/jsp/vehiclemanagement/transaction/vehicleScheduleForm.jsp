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
	src="js/vehicle_management/VehicleScheduling.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/script-library.js"></script>
<link
	href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css"
	rel="stylesheet" type="text/css">
<style>
.multiselect-container {
	overflow-y: scroll;
	height: 100px;
	position: relative !important;
}

.fileUpload.fileinput.fileinput-new .fileUploadClass {
	left: 30px !important;
}
.radio-button {
	padding-top: 6px;
}
table#vehicleschedulingTbl tbody tr td:last-child {
	white-space: nowrap;
}
</style>
<script
	src="assets/libs/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>

<!-- <script>
	$(document).ajaxComplete(function() {
		multiselect();
	});
	function multiselect() {
		$('.multiselect-ui').multiselect({
			buttonText : function(options, select) {
				//  console.log(select[0].length);
				if (options.length === 0) {
					return 'None selected';
				}
				if (options.length === select[0].length) {
					return 'All selected (' + select[0].length + ')';
				} else if (options.length >= 1) {
					return options.length + ' selected';
				} else {
					var labels = [];
					console.log(options);
					options.each(function() {
						labels.push($(this).val());
					});
					return labels.join(', ') + '';
				}
			}

		});
	}
</script>  -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message
						code="vehicle.vehicleschedulingform" text="vehicleschedulingform" /></strong>
				<apptags:helpDoc url="vehicleScheduling.html" />
			</h2>
		</div>
		<div id="content" class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<!-- Start Form -->
			<form:form action="vehicleScheduling.html" method="POST"
				name="vehicleSchedulingForm" class="form-horizontal"
				id="vehicleSchedulingForm" commandName="command">
				<form:hidden id = "id" path = "vehicleScheduleDto.vesId" value = ""/>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="vehicle.vehicleschedulinginfo"
										text="Vehicle Scheduling Information" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:choose>
									<c:when
										test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="gender"><spring:message
													code="vehicle.vehicleType" /></label>
											<apptags:lookupField items="${command.getLevelData('VCH')}"
												path="vehicleScheduleDto.veVetype" cssClass="form-control"
												selectOptionLabelCode="selectdropdown" hasId="true"
												isMandatory="true" changeHandler="showVehicleRegNo()"
												disabled="true" />
											<label class="col-sm-2 control-label required-control"
												for="gender"><spring:message
													code="vehicle.vehicleRegNo" /></label>
											<div class="col-sm-4">
												<form:select path="vehicleScheduleDto.veId" id="veid"
													class="form-control mandColorClass " label="Select"
													disabled="true">
													<form:option value="0">
														<spring:message code="solid.waste.select" text="select" />
													</form:option>
													<c:forEach items="${command.vehicleMasterList}"
														var="lookup">
														<form:option value="${lookup.veId}" code="${lookup.veId}">${lookup.veNo}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
										<form:hidden id = "hidenDel" path = "vehicleScheduleDto.isDeleted" value = "N"/>
										<div class="form-group">
											<apptags:date labelCode="vehicle.scheduleFrom"
												fieldclass="datepicker" isDisabled = "true"
												datePath="vehicleScheduleDto.vesFromdt" isMandatory="true"
												readonly="true"></apptags:date>

											<apptags:date labelCode="vehicle.scheduleTo"
												fieldclass="datepicker" isDisabled = "true"
												datePath="vehicleScheduleDto.vesTodt" isMandatory="true"
												readonly="true"></apptags:date>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="input-id"><spring:message
													code="vehicle.recurrence" text="Recurrence" /> </label>
											<div class="col-sm-4 radio-button">
												<label class="radio-inline "> <form:radiobutton
														id="reccurance1" path="vehicleScheduleDto.vesReocc"
														value="D" disabled="true" /> <spring:message
														code="vehicle.sanitary.report.daily" />
												</label> <label class="radio-inline "> <form:radiobutton
														id="reccurance2" path="vehicleScheduleDto.vesReocc"
														value="W" disabled="true" /> <spring:message
														code="vehicle.sanitary.report.Weekly" />
												</label> <label class="radio-inline "> <form:radiobutton
														id="reccurance3" path="vehicleScheduleDto.vesReocc"
														value="M" disabled="true" /> <spring:message
														code="vehicle.sanitary.report.Monthly" />
												</label> <label class="radio-inline "> <form:radiobutton
														id="reccurance4" path="vehicleScheduleDto.vesReocc"
														value="Y" onclick="Yearly()" disabled="true" /> <spring:message
														code="vehicle.sanitary.report.yearly" />
												</label>
											</div>
										</div>
										<div class="form-group ">
										
                                <label class="col-sm-2 control-label" for=""><spring:message
														code="vehicle.employee.scheduling.days" text="Days" /></label>
												<div class="col-sm-8">
												 <c:set var = "theString" value = "${command.vehicleScheduleDto.vesWeekday}"/>
													
                                                    
											<c:choose>
                                                                  <c:when test = "${fn:contains(theString, '1')}">
																  <label class="checkbox-inline"> <input
														 type="checkbox" id="reccu1" class="days" name="day"
														value="1"  checked = 'checked' disabled = 'disabled'/> <spring:message
															code="vehicle.sanitary.weekday.monday" text="Monday" /></label>
																 </c:when>
																  <c:otherwise>
																  <label class="checkbox-inline"> <input
														 type="checkbox" id="reccu1" class="days" name="day"
														value="1"  disabled = 'disabled' /> <spring:message
															code="vehicle.sanitary.weekday.monday" text="Monday" /></label>
																 </c:otherwise>
											 
											  </c:choose>
													
													
													 <c:choose>
                                                                 <c:when test = "${fn:contains(theString, '2')}">
												 <label class="checkbox-inline  days"> <input
														type="checkbox" id="reccu2" class="days" name="day"
														value="2" checked = 'checked' disabled='disabled'/> <spring:message
															code="vehicle.sanitary.weekday.tuesday" text="Tuesday" /></label>
															 </c:when>
																  <c:otherwise>
																   <label class="checkbox-inline  days"> <input
														type="checkbox" id="reccu2" class="days" name="day"
														value="2" disabled='disabled'/> <spring:message
															code="vehicle.sanitary.weekday.tuesday" text="Tuesday" /></label>
																  </c:otherwise>
																  </c:choose>
													
													 <c:choose>
                                                                  <c:when test = "${fn:contains(theString, '3')}">
																   <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance3" class="days" name="day"
														value="3" checked = 'checked' disabled='disabled'/> <spring:message
															code="vehicle.sanitary.weekday.wednesday"
															text="Wednesday" /></label>
																   </c:when>
																  <c:otherwise>
																   <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance3" class="days" name="day"
														value="3" disabled='disabled'/> <spring:message
															code="vehicle.sanitary.weekday.wednesday"
															text="Wednesday" /></label>
																   </c:otherwise>
																  </c:choose>
																  
																  <c:choose>
                                                                  <c:when test = "${fn:contains(theString, '4')}">
																  <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance4" class="days" name="day"
														value="4" checked='checked' disabled='disabled'/> <spring:message
															code="vehicle.sanitary.weekday.thursday" text="Thursday" /></label>
																   </c:when>
																  <c:otherwise>
																 <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance4" class="days" name="day"
														value="4" disabled='disabled'/> <spring:message
															code="vehicle.sanitary.weekday.thursday" text="Thursday" /></label>
																   </c:otherwise>
																  </c:choose>
																  
																  <c:choose>
                                                                  <c:when test = "${fn:contains(theString, '5')}">
																   <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance5" class="days" name="day"
														value="5" checked = 'checked'disabled='disabled'/> <spring:message
															code="vehicle.sanitary.weekday.friday" text="Friday" /></label>
														&nbsp;
																   </c:when>
																  <c:otherwise>
																 <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance5" class="days" name="day"
														value="5"disabled='disabled'/> <spring:message
															code="vehicle.sanitary.weekday.friday" text="Friday" /></label>
														&nbsp;
																   </c:otherwise>
																  </c:choose>
													<c:choose>
                                                                <c:when test = "${fn:contains(theString, '6')}">
																    <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance6" class="days" name="day"
														value="6" checked = 'checked' disabled='disabled'/> <spring:message
															code="vehicle.sanitary.weekday.saturday" text="Saturday" /></label>
															
																   </c:when>
																  <c:otherwise>
																    <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance6" class="days" name="day"
														value="6"  disabled='disabled'/> <spring:message
															code="vehicle.sanitary.weekday.saturday" text="Saturday" /></label>
																   </c:otherwise>
																  </c:choose>
													
													<c:choose>
                                                                <c:when test = "${fn:contains(theString, '7')}">
																   <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance7" class="days" name="day"
														value="7" checked ='checked' disabled='disabled'/> <spring:message
															code="vehicle.sanitary.weekday.sunday" text="Sunday" /></label>
																   </c:when>
																  <c:otherwise>
																   <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance7" class="days" name="day"
														value="7" disabled='disabled'/> <spring:message
															code="vehicle.sanitary.weekday.sunday" text="Sunday" /></label>
																   </c:otherwise>
																  </c:choose>
													
													
													<form:hidden path="vehicleScheduleDto.vesWeekday"
														id="vesWeekday" />
												</div>
                                         
												
											</div>
									</c:when>
									<c:otherwise>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="gender"><spring:message
													code="vehicle.vehicleType" /></label>
											<apptags:lookupField items="${command.getLevelData('VCH')}"
												path="vehicleScheduleDto.veVetype" cssClass="form-control"
												selectOptionLabelCode="selectdropdown" hasId="true"
												changeHandler="showVehicleRegNo()" isMandatory="true" />
											<label class="col-sm-2 control-label required-control"
												for="gender"><spring:message
													code="vehicle.vehicleRegNo" /></label>
											<div class="col-sm-4">
												<form:select path="vehicleScheduleDto.veId" id="veid"
													class="form-control mandColorClass " label="Select">
													<form:option value="0">
														<spring:message code="solid.waste.select" text="select" />
													</form:option>
												</form:select>

											</div>
										</div>
										<div class="form-group">
											<apptags:date labelCode="vehicle.scheduleFrom"
												fieldclass="fromDateClass"
												datePath="vehicleScheduleDto.vesFromdt" isMandatory="true"
												readonly=""></apptags:date>

											<apptags:date labelCode="vehicle.scheduleTo"
												fieldclass="toDateClass"
												datePath="vehicleScheduleDto.vesTodt" isMandatory="true"
												readonly=""></apptags:date>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="input-id"><spring:message
													code="vehicle.recurrence" text="Recurrence" /> </label>
											<div class="col-sm-4 radio-button">
												<label class="radio-inline "> <form:radiobutton
														id="reccurance1" path="vehicleScheduleDto.vesReocc"
														value="D" onclick="Daily();" /> <spring:message
														code="vehicle.sanitary.report.daily" />
												</label> <label class="radio-inline "> <form:radiobutton
														id="reccurance2" path="vehicleScheduleDto.vesReocc"
														value="W" onclick="Weekly();" /> <spring:message
														code="vehicle.sanitary.report.Weekly" />
												</label> <label class="radio-inline "> <form:radiobutton
														id="reccurance3" path="vehicleScheduleDto.vesReocc"
														value="M" onclick="Monthly()" /> <spring:message
														code="vehicle.sanitary.report.Monthly" />
												</label> <label class="radio-inline "> <form:radiobutton
														id="reccurance4" path="vehicleScheduleDto.vesReocc"
														value="Y" onclick="Yearly()" /> <spring:message
														code="vehicle.sanitary.report.yearly" />
												</label>
											</div>
										</div>

										<div class="yes hidebox" style="display: none">
											<div class="form-group ">
												<label class="col-sm-2 control-label" for=""><spring:message
														code="vehicle.employee.scheduling.days" text="Days" /> </label>
												<div class="col-sm-8">
													<label class="checkbox-inline"> <input
														type="checkbox" id="reccu1" class="days" name="day"
														value="1" /> <spring:message
															code="vehicle.sanitary.weekday.monday" text="Monday" />
													</label> <label class="checkbox-inline  days"> <input
														type="checkbox" id="reccu2" class="days" name="day"
														value="2" /> <spring:message
															code="vehicle.sanitary.weekday.tuesday" text="Tuesday" />
													</label> <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance3" class="days" name="day"
														value="3" /> <spring:message
															code="vehicle.sanitary.weekday.wednesday"
															text="Wednesday" />
													</label> <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance4" class="days" name="day"
														value="4" /> <spring:message
															code="vehicle.sanitary.weekday.thursday" text="Thursday" />
													</label> <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance5" class="days" name="day"
														value="5" /> <spring:message
															code="vehicle.sanitary.weekday.friday" text="Friday" />
														&nbsp;
													</label> <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance6" class="days" name="day"
														value="6" /> <spring:message
															code="vehicle.sanitary.weekday.saturday" text="Saturday" />
													</label> <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance7" class="days" name="day"
														value="7" /> <spring:message
															code="vehicle.sanitary.weekday.sunday" text="Sunday" />
													</label>
													<form:hidden path="vehicleScheduleDto.vesWeekday"
														id="vesWeekday" />
												</div>
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<%-- grid start--%>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-target="#a2" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"> <spring:message
											code="swm.vehicleinfor" text="Vehicle Information" />
									</a>
								</h4>
							</div>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">
									<c:set var="d" value="0" scope="page" />
									<table class="table table-bordered table-striped"
										id="vehicleschedulingTbl">
										<thead>
											<tr>
												<th scope="col" width="5%"><spring:message
														code="refueling.pump.master.sr.no" text="Sr.No." /></th>
												<th scope="col" width="10%"><spring:message code="vehicle.deptId"
														text="Department" /><span class="mand">*</span></th>
												<th scope="col" width="10%"><spring:message code="vehicle.occupies.name"
														text="Occupier Name" /></th>
												<th scope="col" width="10%"><spring:message
														code="VehicleLogBookDTO.driverName"
														text="Driver Name" /></th>
												<th scope="col" width="15%"><spring:message code="vehicle.shift"
														text="Shift" /><span class="mand">*</span></th>
										  <c:if
													test="${fn:length(command.vehicleScheduleDto.tbSwVehicleScheddets) > 0 && command.saveMode ne 'C'}">
													<th scope="col" width="10%"><spring:message code="vehicle.sanitary.report.schedule.date"
															text="Schedule Date" /><span class="mand">*</span></th>
												</c:if> 
												<th scope="col" width="10%"><spring:message
														code="vehicle.sanitary.fromTime" text="In Time" /><span class="mand">*</span></th>
												<th scope="col" width="10%"><spring:message
														code="vehicle.sanitary.toTime" text="Out Time" /><span class="mand">*</span></th>
												<c:if test="${command.saveMode eq 'C'}">
													<%-- Defect #156839 --%>
													<th scope="col" width="6%">
														<spring:message code="vehicle.master.vehicle.action" text="Action" />
													</th>
												</c:if>
												<c:if test="${command.saveMode eq 'E'}">
													<th scope="col" width="5%">
														<button type="button" class="btn btn-danger btn-sm" id = "deleteAll" onclick="showConfirmBoxForMultiDelete(this,'D')"title="Delete All" > 
											                <i class="fa fa-trash"></i>
									                	</button>
														</th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when
													test="${fn:length(command.vehicleScheduleDto.tbSwVehicleScheddets) > 0 && command.saveMode ne 'C'}">

													<c:forEach var="vehicleInfo"
														items="${command.vehicleScheduleDto.tbSwVehicleScheddets}"
														varStatus="status">
														<tr class="firstUnitRow">
															<td><form:input path="" id="sNo${d}"
																	value="${d + 1}" readonly="true"
																	cssClass="form-control " /> <form:hidden
																	path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].vesdId"
																	id="termsId${d}" /></td>


															<td><form:input
																	path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].deptDesc"
																	class="form-control mandColorClass"  disabled = "true" 
																	id="department${d}"/></td>

															<td align="center"><form:select
																path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].occEmpName"
																cssClass="form-control chosen-select-no-results" multiple="true" label="select"
																 onclick="getAllItemsList(${d});"
																 id="occEmpName${d}"
																 disabled="${command.saveMode eq 'V' ? true : false }">
																
															  <c:forEach items="${vehicleInfo.emplList}" var="empl"> 
															  <c:choose>
                                                                  <c:when test="${empl.statusIS == 'Y'}">
																	<form:option value="${empl.empId}" selected="selected">${empl.fullName}</form:option>
																 </c:when>
																 <c:otherwise>
																 <form:option value="${empl.empId}"	>${empl.fullName}</form:option>
																</c:otherwise>
																 </c:choose>
																</c:forEach>  
																
																<%-- <c:forEach items="${occEmplist}" var="empl"> 
																	<form:option value="${empl.empId}" selected="selected">${empl.fullName}</form:option>
																 <form:option value="${empl.key}"	>${empl.value}</form:option>
																</c:forEach> 
																</c:choose> --%>
																<!-- ${empl.empId == occEmplist ? 'selected="selected"' : ''} -->
																<%--  <c:forEach items="${occEmplist}" var="empl"> 
																	<form:option value="${empl.empId}">${empl.fullName}</form:option>
																</c:forEach>  --%>

															</form:select>
															</td>
															<%-- <td><c:forEach items="${vehicleInfo.employeeList}"
																	var="emp" varStatus="loop">															
																	${loop.count}.${emp.fullName}	<br>
																</c:forEach></td> --%>

															<%-- 	<td><c:forEach items="${vehicleInfo.employeeList}"
																	var="emp" varStatus="loop">															
																	${loop.count}.${emp.fullName}	<br>
																</c:forEach></td> --%>
																
																
																
																
													<%-- <td>																							
																<c:forEach items="${vehicleInfo.employeeList}"  var="emp" varStatus="loop">															
																	${loop.count}.${emp.fullName}	<br>						
																	</c:forEach>
																	</td>	 --%>
                                                           <td align="center" Style="font-size: 15px"><form:select
																path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].empId"
																label="Select" cssClass="form-control "
																multiple="true" id="empId${d}"
																disabled="${command.saveMode eq 'V' ? true : false }">
																<c:forEach items="${vehicleInfo.employeeList}" var="emp">
																	
																	<c:choose>
                                                                  <c:when test="${emp.empAddress1 == 'Y'}">
																	<form:option value="${emp.empId}" selected="selected">${emp.fullName}</form:option>
																 </c:when>
																 <c:otherwise>
																<form:option value="${emp.empId}">${emp.fullName}</form:option>
																</c:otherwise>
																 </c:choose>
																	
																</c:forEach>
															</form:select></td>
															<td align="center"><form:select
																	path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].cpdShiftId"
																	class="form-control mandColorClass " label="Select"
																	id="cpdShiftId${d}"
																	disabled="${command.saveMode eq 'V' ? true : false }">
																	<c:set var="baseLookupCode" value="SHT" />
																	<form:option value="">
																		<spring:message code="solid.waste.select"
																			text="select" />
																	</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select></td>
                                                                           	<td align="center"><form:input
																	path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].sheduleDate"
																	class="form-control fromToDateClass  mandColorClass" maxlength="10"
																	id="sheduleDate${d}"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td> 
																	<!-- command.saveMode eq 'E' || -->
													

															<td align="center"><form:input
																	path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].startime"
																	class="form-control datetimepicker3 mandColorClass"
																	maxlength="10" id="startime${d}"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>


															<td align="center"><form:input
																	path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].endtime"
																	class="form-control datetimepicker3 mandColorClass"
																	maxlength="10" id="endtime${d}"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<c:if test="${command.saveMode eq 'C'}">
																<td align="center">
																	<%-- Defect #156839 --%>
																	<a href="javascript:void(0);" data-toggle="tooltip"
																		data-placement="top"
																		onclick="addEntryData('vehicleschedulingTbl');"
																		class=" btn btn-success btn-sm"><i
																			class="fa fa-plus-circle"> </i></a>
																	<a class="btn btn-danger btn-sm delButton"
																	onclick="deleteEntry('vehicleschedulingTbl',$(this),'removedIds');">
																		<i class="fa fa-minus"></i>
																</a></td>
															</c:if>
															<c:if test="${command.saveMode eq 'E'}">
																<td align="center"><%-- <form:checkbox path="accept" data-rule-required="true"></form:checkbox> --%>
														<form:checkbox path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].isDeleted" id="isDeleted${d}" class="form-control" cssClass="case" value="D"/>  
																</td>
															</c:if>

														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>

												</c:when>
												<c:otherwise>
													<tr class="firstUnitRow">
														<td><form:input path="" id="sNo${d}" value="1"
																readonly="true" cssClass="form-control" /></td>



														<td align="center"><form:select
																path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].department"
																class="form-control mandColorClass" onchange="getAllItemsList(${d});" id="department${d}">
																<form:option value="0">
																	<spring:message code="solid.waste.select" text="select" />
																</form:option>
																<c:forEach items="${departments}" var="dept">
																	<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
																</c:forEach>

															</form:select></td>
											
															
													 		<td align="center"><form:select
																path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].occEmpName"
																cssClass="form-control  " multiple="true"
																 id="occEmpName${d}">
															  <%-- <form:option value="0">
																	<spring:message code="Select" text="Select" />
																</form:option>  --%>
																 <c:forEach items="${command.employeList}" var="empl">
																	<form:option value="${empl.empId}">${empl.fullName}</form:option>
																</c:forEach>

															</form:select>
															</td> 


													    	<td align="center" Style="font-size: 15px"><form:select
																path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].empId"
																label="Select" cssClass="form-control "
																multiple="true" id="empId${d}"
																disabled="${command.saveMode eq 'V'|| command.saveMode eq 'E' ? true : false }">
																<c:forEach items="${command.employeeBeanList}" var="emp">
																	<form:option value="${emp.empId}">${emp.fullName}</form:option>
																</c:forEach>
															</form:select></td>

														<td><form:select id="cpdShiftId${d}"
																path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].cpdShiftId"
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

														<td align="center"><form:input
																path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].startime"
																class="form-control datetimepicker3 mandColorClass"
																maxlength="10" id="startime${d}" /></td>

														<td align="center"><form:input
																path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].endtime"
																class="form-control datetimepicker3 mandColorClass"
																maxlength="10" id="endtime${d}" /></td>
														<td align="center">
															<%-- Defect #156839 --%>
															<a href="javascript:void(0);" data-toggle="tooltip"
																data-placement="top"
																onclick="addEntryData('vehicleschedulingTbl');"
																class=" btn btn-success btn-sm"><i
																	class="fa fa-plus-circle"> </i></a>
															<a class="btn btn-danger btn-sm delButton"
																onclick="deleteEntry('vehicleschedulingTbl',$(this),'removedIds');">
																<i class="fa fa-minus"></i>
														</a></td>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<%-- grid end--%>
					</div>
				</div>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'C'}">
						<button type="button" class="btn btn-success btn-submit"
							title='<spring:message code="vehicle.submit" text="Submit" />'
							onclick="Proceed(this,'C')" id="btnSave">
							<spring:message code="vehicle.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success btn-submit"
							title='<spring:message code="vehicle.submit" text="Submit" />'
							onclick="Proceed(this,'E')" id="btnSave">
							<spring:message code="vehicle.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<button type="button" class="btn btn-warning"
							title='<spring:message code="solid.waste.reset" text="Reset" />'
							onclick="openAddVehicleScheduling('vehicleScheduling.html','AddVehicleScheduling');">
							<spring:message code="solid.waste.reset" text="Reset" />
						</button>
					</c:if>
					<apptags:backButton url="vehicleScheduling.html"></apptags:backButton>
				</div>

			</form:form>
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>

