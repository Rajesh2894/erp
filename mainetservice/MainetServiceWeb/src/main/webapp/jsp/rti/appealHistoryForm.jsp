<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/rti/appealHistory.js"></script>

<style>
textarea.form-control {
	resize: vertical !important;
	height: 2.3em;
}
</style>

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="rti.applFormHeader"></spring:message></b>

				</h2>

			</div>

			<div class="widget-content padding">
				<form:hidden path="command.isValidationError" value=""
					id="isValidationError" />
				<form:form method="POST" action="AppealHistory.html"
					class="form-horizontal" id="appealHistoryForm"
					name="appealHistoryForm">

					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv"></div>
					</div>

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">

						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="rti.applicantDetails" /></a>
							</h4>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="form-group">

										<label id="applicantType"
											class="col-sm-2 control-label required-control"
											for="applicationType"><spring:message
												code="rti.typeApp" /></label>
										<c:set var="baseLookupCode" value="ATP" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="reqDTO.applicationType" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" showOnlyLabel="applicantinfo.label.title"
											disabled="true" />

										<div id="organization" style="display: none;">
											<apptags:input labelCode="rti.orgName"
												path="reqDTO.apmOrgnName" cssClass="hasCharacter"
												isMandatory="true" isDisabled="true"></apptags:input>
										</div>

									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label required-control"
											for="applicantTitle"><spring:message code="rti.title" /></label>
										<c:set var="baseLookupCode" value="TTL" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="reqDTO.titleId" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" showOnlyLabel="rti.title" disabled="true" />

										<apptags:input labelCode="rti.firstName"
											cssClass="hasCharacter mandColorClass" path="reqDTO.fName"
											isMandatory="true" isDisabled="true"></apptags:input>

									</div>

									<div class="form-group">

										<apptags:input labelCode="rti.middleName"
											cssClass="hasCharacter" path="reqDTO.mName" isDisabled="true"></apptags:input>
										<apptags:input labelCode="rti.lastName"
											cssClass="hasCharacter" path="reqDTO.lName"
											isMandatory="true" isDisabled="true"></apptags:input>

									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label required-control"><spring:message
												code="applicantinfo.label.gender" /></label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField items="${command.getLevelData('GEN')}"
											path="reqDTO.gender" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" disabled="true" />

										<apptags:input labelCode="rti.mobile1"
											cssClass="form-control hasMobileNo" path="reqDTO.mobileNo"
											isMandatory="true" maxlegnth="10" isDisabled="true"></apptags:input>

									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label required-control"
											for="address"><spring:message code="rti.address"
												text="Address" /></label>
										<div class="col-sm-4">
											<form:textarea class="form-control" id="rtiAddress"
												maxlength="1000" path="reqDTO.areaName" disabled="true"></form:textarea>
										</div>

										<apptags:input labelCode="rti.pinCode" cssClass="hasPincode"
											path="reqDTO.pincodeNo" isMandatory="true" maxlegnth="6"
											isDisabled="true"></apptags:input>


									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label" for="EmailID"><spring:message
												code="rti.emailId" text="Email ID" /></label>
										<div class="col-sm-4">
											<form:input name="" type="email" class="form-control"
												id="EmailID" path="reqDTO.email" data-rule-email="true"
												disabled="true"></form:input>
										</div>

										<apptags:input labelCode="rti.Aadhar"
											cssClass="form-control hasAadharNo" path="reqDTO.uid"
											maxlegnth="12" isDisabled="true"></apptags:input>

									</div>


									<div id="disp" style="display: none;">

										<div class="form-group">


											<apptags:input labelCode="rti.type" cssClass="form-control"
												path="reqDTO.isBPL" maxlegnth="12" isDisabled="true"></apptags:input>


											<div id="bplshow" style="display: none;">

												<apptags:input labelCode="rti.bplNo" path="reqDTO.bplNo"
													isMandatory="true" isDisabled="true"></apptags:input>

											</div>
										</div>
									</div>


									<div id="bplfields" style="display: none;">

										<div class="form-group">


											<label
												class="col-sm-2 control-label required-control datePicker"
												for="yearOfIssue"><spring:message
													code="rti.yearofissue" text="Year Of Issue" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input class="form-control mandColorClass"
														id="yearOfIssue" path="reqDTO.yearOfIssue" disabled="true"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>

											<apptags:input labelCode="rti.issueauthority"
												path="reqDTO.bplIssuingAuthority" cssClass="hasCharacter"
												isMandatory="true" isDisabled="true"></apptags:input>

										</div>
									</div>

								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a4"> <spring:message
										code="rti.corrAdd" /></a>
							</h4>
							<div id="a4" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="form-group">

										<label for="address" class="col-sm-4 control-label"><spring:message
												code="rti.selectAdd"></spring:message> </label>
										<div class="col-sm-4">
											<input type="checkbox"
												class="addr margin-top-10 margin-left-0" checked
												onChange="valueChanged()" data-rule-required="true">
										</div>

									</div>

									<div id="address" class="add" style="display: none;">

										<div class="form-group">


											<label class="col-sm-2 control-label" for="address"><spring:message
													code="care.address" text="Address" /></label>
											<div class="col-sm-4">
												<form:textarea class="form-control" id="rtiAddress"
													maxlength="1000" path="reqDTO.corrAddrsAreaName"
													disabled="true"></form:textarea>
											</div>

											<apptags:input labelCode="rti.pinCode" cssClass="hasPincode"
												path="reqDTO.corrAddrsPincodeNo" maxlegnth="6"
												isDisabled="true"></apptags:input>

										</div>
									</div>
								</div>
							</div>
						</div>


						<div class="panel panel-default" id="accordion_single_collapse">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a9"> <spring:message
										code="rti.refdetails" /></a>
							</h4>
							<div id="a9" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="form-group">

										<label class="col-sm-2 control-label required-control"
											for="refMode"><spring:message code="rti.refmode" /></label>
										<c:set var="baseLookupCode" value="RRM" />
										<apptags:lookupField items="${command.getLevelData('RRM')}"
											path="reqDTO.applReferenceMode" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" disabled="true" />

										<label class="col-sm-2 control-label required-control"
											for="inwardType"><spring:message
												code="rti.inwardtype" /></label>
										<c:set var="baseLookupCode" value="RIT" />
										<apptags:lookupField items="${command.getLevelData('RIT')}"
											path="reqDTO.inwardType" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" disabled="true" />

									</div>

									<div id="E" style="display: none;">
										<table
											class="table table-bordered table-condensed margin-bottom-10">
											<tr>
												<th><spring:message code="rti.authorityName"></spring:message><span
													class="mand">*</span></th>
												<th><spring:message code="rti.dept"></spring:message><span
													class="mand">*</span></th>
												<th><spring:message code="rti.address"></spring:message><span
													class="mand">*</span></th>
												<th><spring:message code="rti.refno"></spring:message><span
													class="mand">*</span></th>
												<th><spring:message code="rti.refdate"></spring:message><span
													class="mand">*</span></th>
											</tr>
											<tr>
												<td><form:input path="reqDTO.inwAuthorityName"
														type="text"
														class="form-control hasCharacter mandColorClass "
														id="inwAuthorityName" /> <!-- <input type="text" class="form-control" id="reqDTO.inwAuthorityName"> --></td>
												<td><form:input path="reqDTO.inwAuthorityDept"
														type="text"
														class="form-control hasCharacter mandColorClass "
														id="inwAuthorityDept" /> <!-- <input type="text" class="form-control" id="reqDTO.departmentName"> --></td>
												<td><form:input path="reqDTO.inwAuthorityAddress"
														type="text" class="form-control mandColorClass "
														id="inwAuthorityAddress" /> <!-- <input type="text" class="form-control" id="address"> --></td>
												<td><form:input path="reqDTO.inwReferenceNo"
														type="text" class="form-control mandColorClass hasNumber"
														id="inwReferenceNo" /> <!-- <input type="text" class="form-control hasNumber" id="reqDTO.inwReferenceNo"> --></td>
												<td>
													<div class="input-group">
														<form:input path="reqDTO.inwReferenceDate"
															class="form-control mandColorClass" readOnly="true"
															id="custDate" />
														<!-- <input type="date" class="form-control" name="date" id="date"> -->
														<label class="input-group-addon"
															for="trasaction-date-icon30"><i
															class="fa fa-calendar"></i> <input type="hidden"
															id="trasaction-date-icon30"> </label>
													</div>
												</td>
											</tr>
										</table>
									</div>

								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="rti.rtiInfo" /></a>
							</h4>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="form-group">

										<label class="col-sm-2 control-label required-control"><spring:message
												code="rti.dept" /></label>
										<apptags:lookupField items="${command.departments}"
											path="reqDTO.rtiDeptId" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											disabled="true" isMandatory="true"
											showOnlyLabel="applicantinfo.label.title" />

										<label class="col-sm-2 control-label required-control"><spring:message
												code="rti.location" /></label>

										<div class="col-sm-4">
											<form:input path="reqDTO.locationName" type="text"
												class="form-control mandColorClass" id="locationName"
												disabled="true" data-rule-required="true" />
										</div>

									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="subject"><spring:message code="rti.subject" /></label>

										<div class="col-sm-10">
											<form:input path="reqDTO.rtiSubject" type="text"
												class="form-control mandColorClass" id="RtiSubject"
												disabled="true" data-rule-required="true" />
											<!-- <input type="text" class="form-control mandColorClass" name="text" id="RtiSubject" data-rule-required="true" path="reqDTO.rtiSubject"></input> -->
										</div>

										<div class="col-sm-10 col-sm-offset-2">
											<h6 class="text-blue-2">
												<spring:message code="rti.rtimaxSubject"></spring:message>
											</h6>
										</div>
									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label required-control"
											for="Decision Date"><spring:message
												code="rti.decisionReceiveDate"
												text="Date on which decision receive" /></label>

										<c:if test="${command.viewEditFlag eq 'E'}">
											<div class="col-sm-4">
												<div class="input-group">

													<form:input class="form-control mandColorClass datepicker"
														id="rtiDeciRecDate" path="reqDTO.rtiDeciRecDate"></form:input>

													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>

											</div>
										</c:if>


										<c:if test="${command.viewEditFlag eq 'V'}">
											<div class="col-sm-4">
												<form:input class="form-control mandColorClass datepicker"
													id="rtiDeciRecDate" path="reqDTO.rtiDeciRecDate"
													disabled="true"></form:input>
											</div>
										</c:if>




										<%-- <c:if test="${empty rtiAppList.rtiDeciRecDate}">
											<form:input class="form-control mandColorClass datepicker"
														id="rtiDeciRecDate"
														path="reqDTO.rtiDeciRecDate" disabled="true"></form:input>
											</c:if> --%>

									</div>
									<%-- <div class="form-group">

										<label class="col-sm-2 control-label"><spring:message
												code="rti.uploadfiles" /></label>
										<div class="col-sm-4">
											<div id="uploadFiles" class="">
												<apptags:formField fieldType="7" labelCode="" hasId="true"
													fieldPath="uploadFileList[0]" isMandatory="false"
													showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
													maxFileCount="CHECK_LIST_MAX_COUNT"
													validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
													currentCount="200" />
											</div>

											<h6 class="text-blue-2">
												<spring:message code="rti.uploadfileupto" />
											</h6>
										</div>

									</div> --%>


								</div>
							</div>
						</div>
					</div>
					<div class="padding-top-10 text-center">
						<c:if test="${command.viewEditFlag eq 'E'}">
							<button type="button" class="btn btn-success btn-submit"
								id="save" onclick="saveAppealHistory(appealHistoryForm)">
								<spring:message code="rti.submit" />
							</button>
						</c:if>

						<button type="button" class="btn btn-danger" id="back"
							onclick="rtiBack()">Back</button>
					</div>


				</form:form>
			</div>
		</div>
	</div>
</div>
<script>
	var rtiDeciRecDate = $('#rtiDeciRecDate').val();

	if (rtiDeciRecDate) {
		$('#rtiDeciRecDate').val(rtiDeciRecDate.split(' ')[0]);
	}
</script>