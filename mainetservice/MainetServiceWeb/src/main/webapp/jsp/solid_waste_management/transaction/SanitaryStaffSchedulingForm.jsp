<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/script-library.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/SanitaryStaffScheduling.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.employee.scheduling" text="Employee Scheduling" /></strong>
			</h2>
		</div>
		<div id="content" class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<!-- Start Form -->
			<form:form action="SanitaryStaffScheduling.html" method="POST"
				name="sanitaryStaffSchedulingForm" class="form-horizontal"
				id="sanitaryStaffSchedulingForm" commandName="command">
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
					<label class="col-sm-2 control-label required-control " for=""><spring:message
							code='swm.sanitary.scheduleType' /></label>
					<div class="col-sm-4">
						<form:select path="employeeScheduleDto.emsType"
							cssClass="form-control mandColorClass" id="scheduleType"
							onchange="getScheduleType()" isMandatory="true">
							<form:option value="">
								<spring:message code="solid.waste.select" />
							</form:option>
							<form:option value="D">
								<spring:message code="swm.sanitary.report.dis" />
							</form:option>
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
					<apptags:date labelCode="swm.scheduleFrom"
						fieldclass="fromDateClass"
						datePath="employeeScheduleDto.emsFromdate" isMandatory="true" readonly="true">
					</apptags:date>
					<apptags:date labelCode="swm.scheduleTo" fieldclass="toDateClass"
						datePath="employeeScheduleDto.emsTodate"
						cssClass="custDate mandColorClass" isMandatory="false"  readonly="true">
					</apptags:date>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="swm.sanitary.reccurance" text="Reccurance" /> </label>
					<div class="col-sm-4">
						<label class="radio-inline "> <form:radiobutton
								id="reccurance1" path="employeeScheduleDto.emsReocc" value="D"
								onclick="Daily();" /> <spring:message
								code="swm.sanitary.report.daily" />
						</label> <label class="radio-inline "> <form:radiobutton
								id="reccurance2" path="employeeScheduleDto.emsReocc" value="W"
								onclick="Weekly();" /> <spring:message
								code="swm.sanitary.report.Weekly" />
						</label> <label class="radio-inline "> <form:radiobutton
								id="reccurance3" path="employeeScheduleDto.emsReocc" value="M"
								onclick="Monthly()" /> <spring:message
								code="swm.sanitary.report.Monthly" />
						</label> <label class="radio-inline "> <form:radiobutton
								id="reccurance4" path="employeeScheduleDto.emsReocc" value="Y"
								onclick="Yearly()" /> <spring:message
								code="swm.sanitary.report.yearly" />
						</label>
					</div>
				</div>
				<div class="yes hidebox" style="display: none">
					<div class="form-group ">
						<label class="col-sm-2 control-label required-control" for=""><spring:message
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
				<div id="sanatary"></div>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveData(this);" id="btnSave">
							<spring:message code="swm.sanitary.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<button type="Reset" class="btn btn-warning"
							onclick="openAddSanitaryStaffScheduling('SanitaryStaffScheduling.html','AddSanitaryStaffScheduling');">
							<spring:message code="solid.waste.reset" text="Reset" />
						</button>
					</c:if>
					<apptags:backButton url="SanitaryStaffScheduling.html"></apptags:backButton>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>












