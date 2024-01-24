<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<link rel="stylesheet" type="text/css"
	href="css/mainet/themes/jquery-ui-timepicker-addon.css" />
<script type="text/javascript"
	src="js/mainet/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript"
	src="js/security_management/shiftMaster.js"></script>
<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="ShiftMasterDTO.shiftMaster"
						text="Shift Master" /></strong>
			</h2>
		</div>
		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand" /><i
					class="text-red-1">* </i> <spring:message
						code="leadlift.master.ismand" /> </span>
			</div>
			<!-- End mand-label -->

			<form:form action="ShiftMaster.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmShiftMaster" id="frmShiftMaster">

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>
				
				<div class="form-group">
					<label class="control-label col-sm-2 required-control" for="">
						<spring:message code="ShiftMasterDTO.shiftId" text="Current Shift" />
					</label>
					<c:set var="baseLookupCode" value="SHT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="shiftMasterDTO.shiftId"
						cssClass="mandColorClass form-control" hasId="true"
						disabled="${command.saveMode eq 'V'}" isMandatory="true"
						selectOptionLabelCode="selectdropdown" />

					<apptags:textArea labelCode="ShiftMasterDTO.shiftDesc"
						path="shiftMasterDTO.shiftDesc" maxlegnth="250"
						cssClass="alphaNumeric" isMandatory="true"
						isReadonly="${command.saveMode eq 'V'}" />
				</div>

				<div class="form-group">
					<apptags:date labelCode="ShiftMasterDTO.fromTime"
						datePath="shiftMasterDTO.fromTime" fieldclass="timepicker"
						isMandatory="true" isDisabled="${command.saveMode eq 'V'}" />

					<apptags:date labelCode="ShiftMasterDTO.toTime"
						datePath="shiftMasterDTO.toTime" fieldclass="timepicker"
						isMandatory="true" isDisabled="${command.saveMode eq 'V'}" />
				</div>

				<div class="form-group">
					<apptags:radio radioLabel="ShiftMasterDTO.yes,ShiftMasterDTO.no"
						radioValue="Y,N" labelCode="ShiftMasterDTO.isCrossDayShift"
						isMandatory="true" path="shiftMasterDTO.isCrossDayShift"
						disabled="${command.saveMode eq 'V' }" />

					<apptags:radio radioLabel="ShiftMasterDTO.yes,ShiftMasterDTO.no"
						radioValue="Y,N" labelCode="ShiftMasterDTO.isGeneralShift"
						isMandatory="true" path="shiftMasterDTO.isGeneralShift"
						disabled="${command.saveMode eq 'V' }" />
				</div>
				<c:if test="${command.saveMode ne 'A'}">
				<div class="form-group">
					<apptags:radio
						radioLabel="ShiftMasterDTO.active,ShiftMasterDTO.inactive"
						radioValue="A,I" labelCode="ShiftMasterDTO.status"
						isMandatory="true" path="shiftMasterDTO.status"
						disabled="${command.saveMode eq 'V' }" />
				</div>
				</c:if>
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'A'}">
						<input type="button"
							value="<spring:message code="ShiftMasterDTO.form.save"/>"
							onclick="confirmToProceed(this,'A')" class="btn btn-success"
							id="Save">
					</c:if>
					<c:if test="${command.saveMode eq 'E' }">
						<input type="button"
							value="<spring:message code="ShiftMasterDTO.form.save"/>"
							onclick="confirmToProceed(this,'E')" class="btn btn-success"
							id="Save">
					</c:if>
					<c:if test="${command.saveMode eq 'A'}">
						<button type="Reset" class="btn btn-warning"
							onclick="addShiftMaster('ShiftMaster.html','ADD','A');">
							<spring:message code="ShiftMasterDTO.form.reset"
								text="Reset"></spring:message>
						</button>
					</c:if>
					<apptags:backButton url="ShiftMaster.html"></apptags:backButton>
				</div>

			</form:form>
		</div>
	</div>
</div>