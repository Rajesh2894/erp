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
<script type="text/javascript" src="js/rts/rtsSecondAppeal.js"></script>
<script src="js/mainet/script-library.js"></script>

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="" text="RTS Second Appeal Form"></spring:message></b>
				</h2>
				<div class="additional-btn">
					<apptags:helpDoc url=""></apptags:helpDoc>
				</div>
			</div>

			<div class="widget-content padding">
				<form:hidden path="command.isValidationError"
					value="${command.isValidationError}" id="isValidationError" />
				<form:form method="POST" action="SecondAppeal.html"
					class="form-horizontal" id="secondAppeal" name="SecondAppeal">

					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv" style="display: none;"></div>
					</div>

					<table class="table table-bordered table-striped" id="secondAppealTable">
						<tr>
							<th colspan="2">Applicant Information</th>
						</tr>
						<tr>
							<td width="30%">Application No.</td>
							<td width="70%">${command.firstAppealDto.applicationNo}</td>
						</tr>
						<tr>

							<td width="30%">Date</td>
							<td width="70%">${command.firstAppealDto.applicationDate}</td>
						</tr>
						<tr>

							<td width="30%">Applicant Name</td>
							<td width="70%">${command.firstAppealDto.name}</td>
						</tr>
						<tr>
							<td width="30%">Mobile Number</td>
							<td width="70%">${command.firstAppealDto.applicantDetailDTO.mobileNo}</td>
						</tr>

						<tr>
							<td width="30%">Email id</td>
							<td width="70%">${command.firstAppealDto.applicantDetailDTO.emailId}</td>
						</tr>

						<tr>
							<th colspan="2">Corresponding Address</th>
						</tr>
						<tr>
							<td width="30%">Address detail</td>
							<td width="70%">${command.firstAppealDto.correspondingAddress}</td>
						</tr>
						<tr>

							<td width="30%">Pincode</td>
							<td width="70%">${command.firstAppealDto.applicantDetailDTO.pinCode}</td>
						</tr>

						<tr>
							<th colspan="2">Permanent Address</th>
						</tr>
						<tr>
							<td width="30%">Address detail</td>
							<td width="70%"><form:input id="address"
									path="firstAppealDto.permanentAddress"
									cssClass="form-control mandColorClass " mandatory="true" /></td>
						</tr>
						<tr>
							<td width="30%">Pincode</td>
							<td width="70%"><form:input id="pincode"
									path="firstAppealDto.pincodeNo"
									cssClass="hasPincode form-control mandColorClass "
									mandatory="false" maxlegnth="6" /></td>
						</tr>
						<tr>
							<td width="30%">Action on Application</td>
							<td width="70%">${firstAppealDto.status}</td>
						</tr>


					</table>
					<div class="form-group">
						<label for="text-1"
							class="control-label col-sm-2 required-control">Reason
							for Appeal</label>
						<div class="col-sm-4">
							<label class="radio-inline "> <form:radiobutton
									id="reasonForAppeal" path="firstAppealDto.reasonForAppeal"
									value="INR" checked="true" /> <spring:message
									code="" text="Information not Recieved" />
							</label> <label class="radio-inline "> <form:radiobutton
									id="Capital" path="firstAppealDto.reasonForAppeal" value="ABD" />
								<spring:message code="" text="Aggrieved By Decision" />
							</label>
						</div>

					</div>
					<div class="form-group">
						<label for="text-1"
							class="control-label col-sm-2 required-control">Ground
							for Appeal</label>
						<div class="col-sm-4">
							<form:textarea rows="2" cols="50" maxlength="50"
								path="firstAppealDto.groundForAppeal" id="groundForAppeal"
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
							onclick="saveForm(this)">Submit</button>
						<apptags:backButton url="CitizenHome.html"></apptags:backButton>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

