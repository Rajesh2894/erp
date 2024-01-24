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
<script type="text/javascript"
	src="js/solid_waste_management/report/surveyReport.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="swm.survey.entry.form"
						text="Survey Entry Form" /></strong>
			</h2>
			<apptags:helpDoc url="SurveyReportMaster.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<!-- Start Form -->
			<form:form action="SurveyReportMaster.html" commandName="command"
				class="form-horizontal form" name="" id="id_surveyReport">
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<!-- End Validation include tag -->

				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="swm.survey.location.name" text="Location Name" /></label>
					<div class="col-sm-4">
						<form:input path="surveyReportDTO.locName"
							cssClass="form-control  mandColorClass  " id="locName"
							readonly="true" />
					</div>
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="swm.survey.location.address" text="Location Address" /> </label>
					<div class="col-sm-4">
						<form:input path="surveyReportDTO.locAddress"
							cssClass="form-control  mandColorClass  " id="locAddress"
							readonly="true" />
					</div>
				</div>

				<div class="form-group">
					<%-- 		<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="" text="Ward" /></label>
					<div class="col-sm-4">
						<form:input path="surveyReportDTO.codWard2Str"
							cssClass="form-control  mandColorClass  " id="ward"
							readonly="true" />
					</div> --%>
					<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
						pathPrefix="surveyReportDTO.codWard"
						isMandatory="true" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control " showAll="false"
						hasTableForm="false" showData="true" columnWidth="10%"
						disabled="${command.saveMode eq 'V' ? true : false }" />

				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="swm.survey.supervisor.name" text="Supervisor Name" /> </label>
					<div class="col-sm-4">
						<form:input path="surveyReportDTO.suName"
							cssClass="form-control  mandColorClass  " id="sName"
							disabled="${command.saveMode eq 'V' ? true : false }" />
					</div>
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="swm.survey.supervisor.mobile.No"
							text="Supervisor Mobile No" /> </label>
					<div class="col-sm-4">
						<form:input path="surveyReportDTO.suMobileNo"
							cssClass="form-control  mandColorClass hasMobileNo" id="sMobNo"
							disabled="${command.saveMode eq 'V' ? true : false }" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="swm.survey.supervisor.president"
							text="President Name if Applicable" /></label>
					<div class="col-sm-4">
						<form:input path="surveyReportDTO.suPresident"
							cssClass="form-control  mandColorClass  " id="pName"
							disabled="${command.saveMode eq 'V' ? true : false }" />
					</div>
					<label class="col-sm-2 control-label " for=""><spring:message
							code="swm.survey.supervisor.committee.Name" text="Committee Name" /></label>
					<div class="col-sm-4">
						<form:input path="surveyReportDTO.commiteeName"
							cssClass="form-control  mandColorClass  " id="cName"
							disabled="${command.saveMode eq 'V' ? true : false }" />
					</div>


				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="vehicle.master.vehicle.remarks" text="Remark" /></label>
					<div class="col-sm-4">
						<form:textarea path="surveyReportDTO.remark"
							cssClass="form-control  mandColorClass  " id="remark"
							disabled="${command.saveMode eq 'V' ? true : false }" />
					</div>
					<apptags:input labelCode="swm.survey.Date" isReadonly="true"
						cssClass="datepicker datepicker" path="surveyReportDTO.suDate"
						isMandatory="true"
						isDisabled="${command.saveMode eq 'V' ? true : false }" />
				</div>


				<!-- Start button -->
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'V' ? false : true }">
						<button type="button" class="btn btn-success btn-submit"
							onclick="Proceed(this)" id="btnSave">
							<spring:message code="solid.waste.submit" text="Submit" />
						</button>
					</c:if>
					<apptags:backButton url="SurveyReportMaster.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>





