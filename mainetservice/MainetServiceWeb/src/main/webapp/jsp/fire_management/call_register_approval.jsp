<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/fire_management/callRegister.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message
						code="ComplainRegisterDTO.form.name" text="Complain Register" /></strong>
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

			<form:form action="FireCallRegisterApproval.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmFireCallRegister" id="frmFireCallRegister">
				<%-- <form:hidden path="saveMode" id="saveMode" /> --%>

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
									<spring:message code="ComplainRegisterDTO.form.name"
										text="Complain Register" />
								</a>
							</h4>
						</div>

						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<apptags:date labelCode="FireCallRegisterDTO.date"
										datePath="entity.date" fieldclass="datepicker"
										isMandatory="true" isDisabled="${command.saveMode eq 'V'}"/>
									<apptags:date labelCode="FireCallRegisterDTO.time"
										datePath="entity.time" fieldclass="timepicker"
										isMandatory="true" isDisabled="${command.saveMode eq 'V'}"/>
								</div>

								<div class="form-group">

									<label class="control-label col-sm-2 required-control"
										for="cpdFireStation"> <spring:message
											code="FireCallRegisterDTO.cpdFireStation" text="Fire Station" /></label>
									<c:set var="baseLookupCode" value="FSN" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="entity.cpdFireStation"
										cssClass="mandColorClass form-control" isMandatory="true"
										hasId="true" selectOptionLabelCode="selectdropdown" disabled="${command.saveMode eq 'V'}"/>
									<apptags:input labelCode="FireCallRegisterDTO.callerName"
										path="entity.callerName" cssClass="hasNameClass"
										isMandatory="false" maxlegnth="50" isDisabled="${command.saveMode eq 'V'}" />

								</div>

								<div class="form-group">
									<apptags:textArea labelCode="FireCallRegisterDTO.callerAdd"
										cssClass="alphaNumeric" maxlegnth="100"
										path="entity.callerAdd" isMandatory="false" isDisabled="${command.saveMode eq 'V'}"/>
									<apptags:input labelCode="FireCallRegisterDTO.callerMobileNo"
										cssClass="hasMobileNo" maxlegnth="10" dataRuleMinlength="10"
										path="entity.callerMobileNo" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"/>

								</div>

								<div class="form-group">
									<apptags:input labelCode="FireCallRegisterDTO.incidentLocation"
										cssClass="alphaNumeric" maxlegnth="100"
										path="entity.incidentLocation" isMandatory="false" isDisabled="${command.saveMode eq 'V'}"/>
									<apptags:textArea labelCode="FireCallRegisterDTO.incidentDesc"
										path="entity.incidentDesc" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="true" isDisabled="${command.saveMode eq 'V'}" />

								</div>


								<div class="form-group">
									<apptags:textArea
										labelCode="FireCallRegisterDTO.operatorRemarks"
										path="entity.operatorRemarks" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="false" isDisabled="${command.saveMode eq 'V'}" />

									<apptags:radio radioLabel="bt.yes,bt.no" radioValue="Y,N"
										labelCode="FireCallRegisterDTO.callerArea"
										path="entity.callerArea" defaultCheckedValue="N" disabled="${command.saveMode eq 'V'}"/>

								</div>

								
						</div>

						<!-- Start button -->
						<apptags:CheckerAction hideForward="true" hideSendback="true"/>
						<div class="text-center clear padding-10">
							<button type="submit" class="button-input btn btn-success"
								onclick="confirmToProceed(this)" name="button-submit" style=""
								id="button-submit">
								<spring:message code="bt.save" text="Submit" />
							</button>
							<apptags:backButton url="AdminHome.html"></apptags:backButton>

						</div>
						<!-- End button -->

					</div>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>
<!-- End of Content -->
