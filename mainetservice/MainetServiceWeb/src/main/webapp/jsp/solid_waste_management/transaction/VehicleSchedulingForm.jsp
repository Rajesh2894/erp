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
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"	src="js/solid_waste_management/VehicleScheduling.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/script-library.js"></script>
<link
	href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css"
	rel="stylesheet" type="text/css">
<style>
.multiselect-native-select :is(.btn-group, .multiselect-container) {
	width: 100%;
}
.multiselect-container {
	overflow-y: scroll;
	height: 100px;
	position: relative !important;
}

.fileUpload.fileinput.fileinput-new .fileUploadClass {
	left: 30px !important;
}
</style>
<script
	src="assets/libs/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
<script>
	$(document).ready(function() {
		multiselect();
	});
	function multiselect() {
		var noneSelectedText = getLocalMessage('none.selected.text');
		var allSelectedText = getLocalMessage('all.selected.text');
		var selectedText = getLocalMessage('selected.text');
		$('.multiselect-ui').multiselect({
			buttonText : function(options, select) {
				//  console.log(select[0].length);
				if (options.length === 0) {
					return noneSelectedText;
				}
				if (options.length === select[0].length) {
					return allSelectedText + ' (' + select[0].length + ')';
				} else if (options.length >= 1) {
					return options.length + ' ' + selectedText;
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
</script>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.vehicleschedulingform"
						text="vehicleschedulingform" /></strong>
				<apptags:helpDoc url="VehicleScheduling.html" />
			</h2>
		</div>
		<div id="content" class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<!-- Start Form -->
			<form:form action="CollectionScheduling.html" method="POST"
				name="vehicleSchedulingForm" class="form-horizontal"
				id="vehicleSchedulingForm" commandName="command">
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
									<spring:message code="swm.vehicleschedulinginfo"
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
												for="gender"><spring:message code="swm.vehicleType" /></label>
											<apptags:lookupField items="${command.getLevelData('VCH')}"
												path="vehicleScheduleDto.veVetype" cssClass="form-control"
												selectOptionLabelCode="selectdropdown" hasId="true"
												isMandatory="true" changeHandler="showVehicleRegNo()"
												disabled="true" />
											<label class="col-sm-2 control-label required-control"
												for="gender"><spring:message code="swm.vehicleRegNo" /></label>
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
										<div class="form-group">
											<apptags:date labelCode="swm.scheduleFrom"
												fieldclass="datepicker"
												datePath="vehicleScheduleDto.vesFromdt" isMandatory="true"
												readonly="true"></apptags:date>

											<apptags:date labelCode="swm.scheduleTo"
												fieldclass="datepicker"
												datePath="vehicleScheduleDto.vesTodt" isMandatory="true"
												readonly="true"></apptags:date>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="input-id"><spring:message code="swm.recurrence"
													text="Recurrence" /> </label>
											<div class="col-sm-4">
												<label class="radio-inline "> <form:radiobutton
														id="reccurance1" path="vehicleScheduleDto.vesReocc"
														value="D" disabled="true" /> <spring:message
														code="swm.sanitary.report.daily" />
												</label> <label class="radio-inline "> <form:radiobutton
														id="reccurance2" path="vehicleScheduleDto.vesReocc"
														value="W" disabled="true" /> <spring:message
														code="swm.sanitary.report.Weekly" />
												</label> <label class="radio-inline "> <form:radiobutton
														id="reccurance3" path="vehicleScheduleDto.vesReocc"
														value="M" disabled="true" /> <spring:message
														code="swm.sanitary.report.Monthly" />
												</label> <label class="radio-inline "> <form:radiobutton
														id="reccurance4" path="vehicleScheduleDto.vesReocc"
														value="Y" onclick="Yearly()" disabled="true"/> <spring:message
														code="swm.sanitary.report.yearly" />
												</label>
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="gender"><spring:message code="swm.vehicleType" /></label>
											<apptags:lookupField items="${command.getLevelData('VCH')}"
												path="vehicleScheduleDto.veVetype" cssClass="form-control chosen-select-no-results"
												selectOptionLabelCode="selectdropdown" hasId="true"
												changeHandler="showVehicleRegNo()" isMandatory="true" />
											<label class="col-sm-2 control-label required-control"
												for="gender"><spring:message code="swm.vehicleRegNo" /></label>
											<div class="col-sm-4">
												<form:select path="vehicleScheduleDto.veId" id="veid"
													class="form-control mandColorClass chosen-select-no-results " label="Select">
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
										<div class="form-group">
											<apptags:date labelCode="swm.scheduleFrom"
												fieldclass="fromDateClass"
												datePath="vehicleScheduleDto.vesFromdt" isMandatory="true"  readonly="true"></apptags:date>

											<apptags:date labelCode="swm.scheduleTo"
												fieldclass="toDateClass"
												datePath="vehicleScheduleDto.vesTodt" isMandatory="true"  readonly="true"></apptags:date>
										</div>
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="input-id"><spring:message code="swm.recurrence"
													text="Recurrence" /> </label>
											<div class="col-sm-4">
												<label class="radio-inline "> <form:radiobutton
														id="reccurance1" path="vehicleScheduleDto.vesReocc"
														value="D" onclick="Daily();" /> <spring:message
														code="swm.sanitary.report.daily" />
												</label> <label class="radio-inline "> <form:radiobutton
														id="reccurance2" path="vehicleScheduleDto.vesReocc"
														value="W" onclick="Weekly();" /> <spring:message
														code="swm.sanitary.report.Weekly" />
												</label> <label class="radio-inline "> <form:radiobutton
														id="reccurance3" path="vehicleScheduleDto.vesReocc"
														value="M" onclick="Monthly()" /> <spring:message
														code="swm.sanitary.report.Monthly" />
												</label> <label class="radio-inline "> <form:radiobutton
														id="reccurance4" path="vehicleScheduleDto.vesReocc"
														value="Y" onclick="Yearly()" /> <spring:message
														code="swm.sanitary.report.yearly" />
												</label>
											</div>
										</div>

										<div class="yes hidebox" style="display: none">
											<div class="form-group ">
												<label class="col-sm-2 control-label" for=""><spring:message
														code="swm.employee.scheduling.days" text="Days" /> </label>
												<div class="col-sm-8">
													<label class="checkbox-inline"> <input
														type="checkbox" id="reccu1" class="days" name="day"
														value="1" /> <spring:message
															code="swm.sanitary.weekday.monday" text="Monday" />
													</label> <label class="checkbox-inline  days"> <input
														type="checkbox" id="reccu2" class="days" name="day"
														value="2" /> <spring:message
															code="swm.sanitary.weekday.tuesday" text="Tuesday" />
													</label> <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance3" class="days" name="day"
														value="3" /> <spring:message
															code="swm.sanitary.weekday.wednesday" text="Wednesday" />
													</label> <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance4" class="days" name="day"
														value="4" /> <spring:message
															code="swm.sanitary.weekday.thursday" text="Thursday" />
													</label> <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance5" class="days" name="day"
														value="5" /> <spring:message
															code="swm.sanitary.weekday.friday" text="Friday" />
														&nbsp;
													</label> <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance6" class="days" name="day"
														value="6" /> <spring:message
															code="swm.sanitary.weekday.saturday" text="Saturday" />
													</label> <label class="checkbox-inline days"> <input
														type="checkbox" id="reccurance7" class="days" name="day"
														value="7" /> <spring:message
															code="swm.sanitary.weekday.sunday" text="Sunday" />
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
												<th scope="col" width="3%"><spring:message code="swm.vehicleId" text="Sr.No." /></th>
												<th scope="col" width="10%"><spring:message code="swm.route" text="Beat No." /><span class="mand">*</span></th>
												<th scope="col" width="15%"><spring:message	code="employee.verification.employee.name" text="Employee Name"/></th>
												<th scope="col" width="15%"><spring:message	code="swm.collectionType" text="Collection Type" /><span class="mand">*</span></th>
												<c:if
													test="${fn:length(command.vehicleScheduleDto.tbSwVehicleScheddets) > 0 && command.saveMode ne 'C'}">
													<th scope="col" width="10%"><spring:message code="swm.sanitary.report.schedule.date"
															text="Schedule Date" /><span class="mand">*</span></th>
												</c:if>
												<th scope="col" width="10%"><spring:message code="swm.intime"
														text="In Time" /><span class="mand">*</span></th>
												<th scope="col" width="10%"><spring:message code="swm.outtime"
														text="Out Time" /><span class="mand">*</span></th>
												<c:if test="${command.saveMode eq 'C'}">
													<th scope="col" width="8%"><spring:message code="solid.waste.action" text="Action" /></th>
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

															<td align="center"><form:select
																	path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].beatId"
																	class="form-control mandColorClass " label="Select"
																	id="beatId${d}"
																	disabled="${command.saveMode eq 'V'|| command.saveMode eq 'E' ? true : false }">
																	<form:option value="0">
																		<spring:message code="solid.waste.select"
																			text="select" />
																	</form:option>
																	<c:forEach items="${command.routeMasterList}"
																		var="lookup">
																		<form:option value="${lookup.beatId}">${lookup.beatNo } &nbsp; ${ lookup.beatName}</form:option>
																	</c:forEach>
																</form:select></td>
															<td>																							
																<c:forEach items="${vehicleInfo.employeeList}"  var="emp" varStatus="loop">															
																	${loop.count}.${emp.fullName}	<br>						
																	</c:forEach>
																	</td>
															<td align="center"><form:select
																	path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].vesCollType"
																	class="form-control mandColorClass " label="Select"
																	id="vesCollType${d}"
																	disabled="${command.saveMode eq 'V' ? true : false }">
																	<form:option value="">
																		<spring:message code="solid.waste.select"
																			text="select" />
																	</form:option>
																	<c:forEach items="${command.vesCollTypeList}"
																		var="vesType">
																		<form:option value="${vesType.lookUpId}"
																			code="${vesType.lookUpCode}">${vesType.descLangFirst}</form:option>
																	</c:forEach>
																</form:select></td>
															<td align="center"><form:input
																	path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].sheduleDate"
																	class="form-control  mandColorClass" maxlength="10"
																	id="sheduleDate${d}"
																	disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' ? true : false }" /></td>

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
																<td class="text-center" width="8%">
																<a href="javascript:void(0);" data-toggle="tooltip"
																	data-placement="top"
																	onclick="addEntryData('vehicleschedulingTbl');"
																	class=" btn btn-success btn-sm"><i class="fa fa-plus-circle"> </i></a>
																<a class="btn btn-danger btn-sm delButton"
																	onclick="deleteEntry('vehicleschedulingTbl',$(this),'removedIds');">
																		<i class="fa fa-minus"></i>
																</a></td>
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
																path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].beatId"
																class="form-control mandColorClass " label="Select"
																id="beatId${d}">
																<form:option value="0">
																	<spring:message code="solid.waste.select" text="select" />
																</form:option>
																<c:forEach items="${command.routeMasterList}"
																	var="lookup">
																	<form:option value="${lookup.beatId}">${lookup.beatNo } &nbsp; ${ lookup.beatName}</form:option>
																</c:forEach>
															</form:select></td>
														<td width="10%">
														<form:select width="40%"
																path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].empId"
																label="Select" cssClass="form-control multiselect-ui"
																multiple="true" id="empId${d}"
																disabled="${command.saveMode eq 'V'|| command.saveMode eq 'E' ? true : false }">														
																<c:forEach items="${command.employeeBeanList}" var="emp">
																	<form:option value="${emp.empId}">${emp.fullName}</form:option>
																</c:forEach>
															</form:select></td>
														<td align="center" Style="font-size: 15px"><form:select
																path="vehicleScheduleDto.tbSwVehicleScheddets[${d}].vesCollType"
																class="form-control mandColorClass " label="Select"
																id="vesCollType${d}">
																<form:option value="">
																	<spring:message code="solid.waste.select" text="select" />
																</form:option>
																<c:forEach items="${command.vesCollTypeList}"
																	var="vesType">
																	<form:option value="${vesType.lookUpId}"
																		code="${vesType.lookUpCode}">${vesType.descLangFirst}</form:option>
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
														<td class="text-center" width="8%">
														<a href="javascript:void(0);" data-toggle="tooltip"
															data-placement="top"
															onclick="addEntryData('vehicleschedulingTbl');" class=" btn btn-success btn-sm"
															title="<spring:message code="solid.waste.add" text="Add"></spring:message>">
															<i class="fa fa-plus-circle"> </i>
															
															</a>
														<a	class="btn btn-danger btn-sm delButton"
															onclick="deleteEntry('vehicleschedulingTbl',$(this),'removedIds');"
															 title="<spring:message code="solid.waste.delete" text="Delete"></spring:message>">
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
						<button type="button" class="btn btn-success btn-submit" title='<spring:message code="swm.submit" text="Submit" />'
							onclick="Proceed(this,'C')" id="btnSave">
							<spring:message code="swm.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'E'}">					
						<button type="button" class="btn btn-success btn-submit" title='<spring:message code="swm.submit" text="Submit" />'
							onclick="Proceed(this,'E')" id="btnSave">
							<spring:message code="swm.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<button type="button" class="btn btn-warning" title='<spring:message code="solid.waste.reset" text="Reset" />'
							onclick="openAddVehicleScheduling('CollectionScheduling.html','AddVehicleScheduling');">
							<spring:message code="solid.waste.reset" text="Reset" />
						</button>
					</c:if>
					<apptags:backButton url="CollectionScheduling.html"></apptags:backButton>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>







