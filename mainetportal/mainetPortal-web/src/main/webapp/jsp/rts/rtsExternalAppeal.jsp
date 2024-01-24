<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/rts/rtsExternalAppeal.js"></script>
<script src="js/mainet/script-library.js"></script>

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="" text="RTS External Appeal Form"></spring:message></b>
				</h2>
				<div class="additional-btn">
					<apptags:helpDoc url=""></apptags:helpDoc>
				</div>
			</div>

			<div class="widget-content padding">
				<form:hidden path="command.isValidationError"
					value="${command.isValidationError}" id="isValidationError" />
				<form:form method="POST" action="RTSExternalAppeal.html"
					class="form-horizontal" id="externalAppeal" name="externalAppeal">
					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv" style="display: none;"></div>
					</div>

					<!--  Applicant Details starts here -->


					<%-- <h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a4"> <spring:message
									text="Applicant Details" /></a>
						</h4> --%>



					<div class="form-group">
						<apptags:input labelCode="cfc.application"
							cssClass=" mandColorClass "
							path="objectionDetailsDto.apmApplicationId" isMandatory="true"
							maxlegnth="15"></apptags:input>

					</div>
					<div class="form-group">

						<label class="col-sm-2 control-label required-control"><spring:message code="rti.title" text="Title"></spring:message></label>
						<c:set var="baseLookupCodeTTL" value="TTL" />
						<apptags:lookupField
							items="${command.getLevelData(baseLookupCodeTTL)}"
							path="objectionDetailsDto.title"
							cssClass="form-control chosen-select-no-results"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="applicantinfo.label.select"
							isMandatory="true" />


						<apptags:input labelCode="obj.firstName"
							cssClass="hasCharacter mandColorClass hasNoSpace"
							path="objectionDetailsDto.fName" isMandatory="true"
							maxlegnth="100"></apptags:input>
					</div>

					<div class="form-group">
						<apptags:input labelCode="obj.middleName"
							cssClass="hasCharacter mandColorClass hasNoSpace"
							path="objectionDetailsDto.mName" maxlegnth="100"></apptags:input>

						<apptags:input labelCode="obj.lastName"
							cssClass="hasCharacter mandColorClass hasNoSpace"
							path="objectionDetailsDto.lName" isMandatory="true"
							maxlegnth="100"></apptags:input>
					</div>

					<div class="form-group">

						<label class="col-sm-2 control-label required-control"><spring:message
								code="applicantinfo.label.gender" /></label>
						<c:set var="baseLookupCode" value="GEN" />
						<apptags:lookupField items="${command.getLevelData('GEN')}"
							path="objectionDetailsDto.gender"
							cssClass="form-control chosen-select-no-results"
							hasChildLookup="false" hasId="true" showAll="false"
							selectOptionLabelCode="applicantinfo.label.select"
							isMandatory="true" />
						<label class="col-sm-2 control-label"><spring:message
								code="applicantinfo.label.aadhaar" /></label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control hasNumber"
								path="objectionDetailsDto.uid" id="aadharNo" maxlength="12"
								data-mask="9999 9999 9999" />
						</div>
					</div>

					<div class="form-group">
						<apptags:input labelCode="obj.mobile1"
							cssClass="hasMobileNo mandColorClass "
							path="objectionDetailsDto.mobileNo" isMandatory="true"
							maxlegnth="12"></apptags:input>
						<apptags:input labelCode="obj.emailId"
							cssClass="hasemailclass  mandColorClass hasNoSpace"
							path="objectionDetailsDto.eMail" isMandatory="false"
							maxlegnth="49"></apptags:input>
					</div>

					<div class="form-group">
						<label for="text-1"
							class="control-label col-sm-2 required-control"><spring:message code="ack.address" text="Address"></spring:message></label>
						<div class="col-sm-4">
							<form:textarea rows="2" cols="50" maxlength="199"
								path="objectionDetailsDto.address" id="address"
								class="form-control padding-left-10"></form:textarea>
						</div>

					</div>

					<div class="form-group">

						<label class="col-sm-2 control-label "><spring:message
								code="obj.reasonForAppeal" text="Reason For Appeal" /></label>
						<div class="col-sm-4">
							<label><form:radiobutton
									path="objectionDetailsDto.objectionReason" value="INR"
									checked="true" /> <spring:message code="obj.reasonINT"
									text="Information Not Received" /></label> </label> <label> <form:radiobutton
									path="objectionDetailsDto.objectionReason" value="ABD" />
								<spring:message code="obj.reasonABD"
									text="Aggrieved By Decision" />
							</label>
						</div>

					</div>
					<div class="form-group">
						<label for="text-1"
							class="control-label col-sm-2 required-control">
							<spring:message code="rti.groundForAppeal" text="Ground for Appeal"></spring:message></label>
						<div class="col-sm-4">
							<form:textarea rows="2" cols="50" maxlength="149"
								path="objectionDetailsDto.objectionDetails" id="groundForAppeal"
								class="form-control padding-left-10"></form:textarea>
						</div>

					</div>

					<div class="form-group">
						<c:set var="count" value="0" scope="page"></c:set>
						<label class="col-sm-2 control-label"><spring:message
								code="rti.uploadfiles" /></label>
						<div class="col-sm-4">
							<div id="uploadFiles" class="">
								<apptags:formField fieldType="7" labelCode="" hasId="true"
									fieldPath="" isMandatory="false" showFileNameHTMLId="true"
									fileSize="BND_COMMOM_MAX_SIZE"
									maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
									currentCount="0" />
							</div>
						</div>
					</div>
					<div class="text-center">
						<button type="button" class="btn btn-success" title="Submit"
							onclick="saveForm(this)"><spring:message code="rti.submit" text="Submit"></spring:message></button>
						<apptags:backButton url="CitizenHome.html"></apptags:backButton>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>