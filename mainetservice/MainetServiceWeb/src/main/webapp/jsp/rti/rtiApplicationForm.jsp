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
<script type="text/javascript" src="js/rti/rtiApplicationForm.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<script>
	$(function() {

		$(".datepicker31").datepicker({
			dateFormat : 'dd/mm/yy',

		});
	});
</script>



<style>
textarea.form-control {
	resize: vertical !important;
	height: 2.3em;
}

.hide-calendar .ui-datepicker-calendar {
	display: none;
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

				<apptags:helpDoc url="RtiApplicationDetailForm.html"></apptags:helpDoc>
			</div>


			<div class="widget-content padding">
				<form:hidden path="command.isValidationError"
					value="${command.isValidationError}" id="isValidationError" />
				<form:form method="POST" action="RtiApplicationDetailForm.html"
					class="form-horizontal" id="rtiForm" name="rtiForm">
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
											isMandatory="true" showOnlyLabel="applicantinfo.label.title" />

										<div id="organization" style="display: none;">
											<apptags:input labelCode="rti.orgName"
												path="reqDTO.apmOrgnName" cssClass="hasCharacter"
												isMandatory="true"></apptags:input>
										</div>

									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label" for="MobileNumber"><spring:message
												code="rti.mobile1" text="MobileNumber" /><i
											class="text-red-1">*</i></label>
										<div class="col-sm-4">
											<form:input name="mobileNo" type="text"
												class="form-control hasNumber" id="MobileNumber"
												path="reqDTO.mobileNo" maxlength="10"></form:input>
										</div>


										<label class="col-sm-2 control-label required-control"
											for="applicantTitle" id="titleId"><spring:message
												code="rti.title" /></label>
										<c:set var="baseLookupCode" value="TTL" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="reqDTO.titleId" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" showOnlyLabel="rti.title" />
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label" for="FirstName1"><spring:message
												code="rti.firstName" text="First Name" /><i
											class="text-red-1">*</i></label>
										<div class="col-sm-4">
											<form:input name="firstName" type="text"
												class="form-control hasSpecialChara" id="FirstName1"
												path="reqDTO.fName"></form:input>
										</div>

										<label class="col-sm-2 control-label" for="MiddleName"><spring:message
												code="rti.middleName" text="Middle Name" /></label>
										<div class="col-sm-4">
											<form:input name="MiddletName" type="text"
												class="form-control hasSpecialChara" id="MiddleName"
												path="reqDTO.mName"></form:input>
										</div>


									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label" for="LastName1"><spring:message
												code="rti.lastName" text="Last Name" /><i
											class="text-red-1">*</i></label>
										<div class="col-sm-4">
											<form:input name="Last Name" type="text"
												class="form-control hasSpecialChara" id="LastName1"
												path="reqDTO.lName"></form:input>
										</div>

										<label class="col-sm-2 control-label required-control"
											for="gender"><spring:message
												code="applicantinfo.label.gender" /></label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField items="${command.getLevelData('GEN')}"
											path="reqDTO.gender" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" />



									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label required-control"
											for="address"><spring:message code="rti.address"
												text="Address" /></label>
										<div class="col-sm-4">
											<form:textarea class="form-control" id="rtiAddress"
												maxlength="1000" path="reqDTO.areaName"></form:textarea>
										</div>


										<label class="col-sm-2 control-label" for="Pincode"><spring:message
												code="rti.pinCode" text="Pincode" /><i class="text-red-1">*</i></label>
										<div class="col-sm-4">
											<form:input name="pincodeNo" type="text"
												class="form-control hasNumber" id="Pincode"
												path="reqDTO.pincodeNo" maxlength="6"></form:input>
										</div>


									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label" for="EmailID"><spring:message
												code="rti.emailId" text="Email ID" /></label>
										<div class="col-sm-4">
											<form:input name="" type="email" class="form-control"
												id="EmailID" path="reqDTO.email" data-rule-email="true"></form:input>
										</div>

										<apptags:input labelCode="rti.Aadhar"
											cssClass="form-control hasAadharNo" path="reqDTO.uid"
											maxlegnth="12"></apptags:input>

									</div>

									<div id="disp" style="display: none;">

										<div class="form-group">
											<label class="col-sm-2 control-label required-control"><spring:message
													code="rti.type" /></label>
											<c:set var="baseLookupCode" value="YNC" />

											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}"
												path="reqDTO.isBPL" cssClass="form-control"
												hasChildLookup="false" hasId="true" showAll="false"
												selectOptionLabelCode="applicantinfo.label.select"
												isMandatory="true" showOnlyLabel="applicantinfo.label.title" />

											<div id="bplshow" style="display: none;">

												<apptags:input labelCode="rti.bplNo" path="reqDTO.bplNo"
													isMandatory="true" maxlegnth="20"></apptags:input>

											</div>
										</div>
									</div>


									<div id="bplfields" style="display: none;">

										<div class="form-group">
											<%-- <apptags:input labelCode="rti.yearofissue" cssClass="hasNumber maxLength4" path="reqDTO.yearOfIssue" isMandatory="true"></apptags:input> --%>

											<label
												class="col-sm-2 control-label required-control datePicker"
												for="yearOfIssue"><spring:message
													code="rti.yearofissue" text="Year Of Issue" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input class="form-control mandColorClass"
														id="yearOfIssue" path="reqDTO.yearOfIssue"
														autocomplete="off"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>

											<apptags:input labelCode="rti.issueauthority"
												path="reqDTO.bplIssuingAuthority" cssClass="hasCharacter"
												isMandatory="true"></apptags:input>

										</div>
									</div>
									<div class="form-group">

										<c:set var="baseLookupCode" value="RWZ" />
										<apptags:lookupFieldSet
											cssClass="form-control required-control" baseLookupCode="RWZ"
											hasId="true" pathPrefix="reqDTO.trdWard"
											hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" showAll="false"
											isMandatory="true" />
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
													maxlength="1000" path="reqDTO.corrAddrsAreaName"></form:textarea>
											</div>

											<apptags:input labelCode="rti.pinCode" cssClass="hasPincode"
												path="reqDTO.corrAddrsPincodeNo" maxlegnth="6"></apptags:input>

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
											isMandatory="true" />

										<label class="col-sm-2 control-label required-control"
											for="inwardType"><spring:message
												code="rti.inwardtype" /></label>
										<c:set var="baseLookupCode" value="RIT" />
										<apptags:lookupField items="${command.getLevelData('RIT')}"
											path="reqDTO.inwardType" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" />

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
														id="inwAuthorityName" maxlength="50" /> <!-- <input type="text" class="form-control" id="reqDTO.inwAuthorityName"> --></td>
												<td><form:input path="reqDTO.inwAuthorityDept"
														type="text"
														class="form-control hasCharacter mandColorClass "
														id="inwAuthorityDept" /> <!-- <input type="text" class="form-control" id="reqDTO.departmentName"> --></td>
												<td><form:input path="reqDTO.inwAuthorityAddress"
														type="text" class="form-control mandColorClass "
														id="inwAuthorityAddress" maxlength="500" /> <!-- <input type="text" class="form-control" id="address"> --></td>
												<td><form:input path="reqDTO.inwReferenceNo"
														type="text" class="form-control mandColorClass hasNumber"
														id="inwReferenceNo" /> <!-- <input type="text" class="form-control hasNumber" id="reqDTO.inwReferenceNo"> --></td>
												<td>

													<div class="input-group">
														<form:input class="form-control datepicker"
															path="reqDTO.inwReferenceDate" isMandatory="true"
															placeholder="DD/MM/YYYY" id="inwReferenceDate" />
														<span class="input-group-addon"><i
															class="fa fa-calendar"></i></span>
													</div>
												</td>
											</tr>
										</table>
									</div>

									<div id="stamp" style="display: none;">
										<table
											class="table table-bordered table-condensed margin-bottom-10">
											<tr>
												<th width="10%"><spring:message code="rti.stampno"></spring:message><span
													class="mand">*</span></th>
												<th width="10%"><spring:message code="rti.stampamt"></spring:message><span
													class="mand">*</span></th>
												<th width="10%"><small class="text-blue-2"><spring:message
															code="rti.uploadfileupto" /></small><span class="mand">*</span></th>
											</tr>

											<tr>
												<td><form:input path="reqDTO.stampNo" type="text"
														class="form-control mandColorClass hasNumber" id="stampNo"
														maxlength="40" /> <!-- <input type="text" class="form-control hasNumber" id="reqDTO.stampNo" > --></td>
												<td><form:input path="reqDTO.stampAmt" type="text"
														class="form-control mandColorClass hasNumber"
														id="stampAmt" maxlength="10" /> <!-- <input type="text" class="form-control hasNumber" id="reqDTO.stampAmt"> --></td>
												<td width="10%">
													<div id="stampDoc" class="">
														<apptags:formField fieldType="7" labelCode="" hasId="true"
															fieldPath="uploadStamoDoc[0]" isMandatory="false"
															showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
															currentCount="100" />

													</div>
												</td>
											</tr>
										</table>
									</div>
									<div id="Post" style="display: none;">
										<table
											class="table table-bordered table-condensed margin-bottom-10">
											<tr>
												<th width="10%"><spring:message code="rti.postCard.no" text="Postal Card No"></spring:message><span
													class="mand">*</span></th>
												<th width="10%"><spring:message code="rti.amount" text="Amount"></spring:message><span
													class="mand">*</span></th>
												<th width="10%"><small class="text-blue-2"><spring:message
															code="rti.uploadfileupto" /></small><span class="mand">*</span></th>
											</tr>

											<tr>
												<td><form:input path="reqDTO.postalCardNo" type="text"
														class="form-control mandColorClass" id="postalCardNo"
														maxlength="40" /> <!-- <input type="text" class="form-control hasNumber" id="reqDTO.stampNo" > --></td>
												<td><form:input path="reqDTO.postalAmt" type="text"
														class="form-control mandColorClass hasNumber"
														id="postalAmt" maxlength="10" /> <!-- <input type="text" class="form-control hasNumber" id="reqDTO.stampAmt"> --></td>
												<td width="10%">
													<div id="postDoc" class="">
														<apptags:formField fieldType="7" labelCode="" hasId="true"
															fieldPath="uploadStamoDoc[0]" isMandatory="false"
															showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
															currentCount="230" />

													</div>
												</td>
											</tr>
										</table>
									</div>
									<div id="NonJudicial" style="display: none;">
										<table
											class="table table-bordered table-condensed margin-bottom-10">
											<tr>
												<th width="10%"><spring:message code="rti.nonJudicial.no" text=" NonJudicial No"></spring:message><span
													class="mand">*</span></th>
												<th width="10%"><spring:message code="rti.date" text="Date"></spring:message><span
													class="mand">*</span></th>
												<th width="10%"><small class="text-blue-2"><spring:message
															code="rti.uploadfileupto" /></th>
											</tr>

											<tr>
												<td><form:input path="reqDTO.nonJudclNo" type="text"
														class="form-control mandColorClass" id="nonJudclNo"
														maxlength="40" /> <!-- <input type="text" class="form-control hasNumber" id="reqDTO.stampNo" > --></td>
												<td><form:input class="form-control datepicker"
															path="reqDTO.nonJudclDate" isMandatory="true"
															placeholder="DD/MM/YYYY" id="nonJudclDate" />
														</td>
												<td width="10%">
													<div id="postDoc" class="">
														<apptags:formField fieldType="7" labelCode="" hasId="true"
															fieldPath="uploadStamoDoc[0]" isMandatory="false"
															showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
															currentCount="150" />

													</div>
												</td>
											</tr>
										</table>
									</div>
									<div id="Challan" style="display: none;">
										<table
											class="table table-bordered table-condensed margin-bottom-10">
											<tr>
												<th width="10%"><spring:message code="rti.challan.no" text="Challan No"></spring:message><span
													class="mand">*</span></th>
												<th width="10%"><spring:message code="rti.date" text="Date"></spring:message><span
													class="mand">*</span></th>
												<th width="10%"><small class="text-blue-2"><spring:message
															code="rti.uploadfileupto" /></small><span class="mand">*</span></th>
											</tr>

											<tr>
												<td><form:input path="reqDTO.challanNo" type="text"
														class="form-control mandColorClass" id="challanNo"
														maxlength="40" /> <!-- <input type="text" class="form-control hasNumber" id="reqDTO.stampNo" > --></td>
												<td><form:input class="form-control datepicker"
														path="reqDTO.challanDate" isMandatory="true"
														placeholder="DD/MM/YYYY" id="challanDate" /> </td>
												<td width="10%">
													<div id="postDoc" class="">
														<apptags:formField fieldType="7" labelCode="" hasId="true"
															fieldPath="uploadStamoDoc[0]" isMandatory="false"
															showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
															currentCount="170" />

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
												code="rti.related.dept" text="RTI Related Department" /> :</label>
										<apptags:lookupField items="${command.related_dept}"
											path="reqDTO.rtiRelatedDeptId" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" showOnlyLabel="applicantinfo.label.title" />

										<label class="col-sm-2 control-label required-control"><spring:message
												code="rti.location" /></label>
										<apptags:lookupField items="${command.locations}"
											path="reqDTO.rtiLocationId" cssClass="form-control"
											hasChildLookup="false" hasId="true" showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" showOnlyLabel="applicantinfo.label.title" />

									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label required-control"
											for="subject"><spring:message code="rti.subject" /></label>

										<div class="col-sm-4">
											<form:input path="reqDTO.rtiSubject" type="text"
												class="form-control mandColorClass" id="RtiSubject"
												data-rule-required="true" maxlength="500" />
											<!-- <input type="text" class="form-control mandColorClass" name="text" id="RtiSubject" data-rule-required="true" path="reqDTO.rtiSubject"></input> -->
											<div class="col-sm-10 col-sm-offset-2">
												<h6 class="text-blue-2">
													<spring:message code="rti.rtimaxSubject"></spring:message>
												</h6>
											</div>
										</div>
										<apptags:input labelCode="rti.dept" cssClass="form-control"
											path="reqDTO.departmentName" maxlegnth="6" isDisabled="true"></apptags:input>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="rtiApplication"><spring:message code="rti.desc"
												text="Description" /></label>
										<div class="col-sm-4">
											<form:textarea cssClass="form-control" path="reqDTO.rtiDesc"
												rows="" maxlength="3000" id="RtiDesc"
												onkeyup="countChar(this,3000,'textareaCount')"
												onfocus="countChar(this,3000,'textareaCount')" />
											<div class="pull-right">
													<spring:message code="charcter.remain" text="characters remaining " /><span id="textareaCount">3000</span>
											</div>
										</div>
									</div>



									<!-- MOM POINTS -->
									<%-- <div class="form-group">
												<label class="col-sm-2 control-label required-control" for="rtiDetails" ><spring:message code="rti.appDetail"/></label>
													
												<div class="col-sm-10">
													<form:textarea name="" cols="" rows="" class="form-control mandColorClass" id="RtiDetails" path="reqDTO.rtiDetails"></form:textarea>
												 	<!-- <textarea type="textarea" required class="form-control mandColorClass" name="textarea" id="textarea" data-rule-required="true"></textarea> -->
											    </div>
												<div class="col-sm-10 col-sm-offset-2">
										        	<h6 class="text-blue-2"><spring:message code="rti.rtimaxtext"></spring:message></h6>
										        </div>
									 		</div> --%>


									<div class="form-group">

										<label class="col-sm-2 control-label"><spring:message
												code="rti.uploadfiles" /><span class="mand">*</span></label>
										<div class="col-sm-4">
											<div id="uploadFiles" class="">
												<apptags:formField fieldType="7" labelCode="" hasId="true"
													fieldPath="uploadFileList[0]" isMandatory="false"
													showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
													maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
													validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
													currentCount="200" />
											</div>

											<h6 class="text-blue-2">
												<spring:message code="rti.uploadfileupto" />
											</h6>
										</div>

									</div>
								</div>
							</div>
						</div>

						<div id="payandCheckIdDiv">

							<c:if test="${not empty command.checkList}">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" class=""
												data-parent="#accordion_single_collapse"
												href="#Upload_Attachment"> <spring:message
													code="rti.uploadAttach" text="Upload Attachment"></spring:message><small
												class="text-blue-2">(Upload File upto 5MB and only
													.pdf or .doc)</small></a>
										</h4>
									</div>
									<div id="Upload_Attachment" class="panel-collapse collapse in">
										<div class="panel-body">
											<div class="overflow margin-top-10">
												<div class="table-responsive">
													<table
														class="table table-hover table-bordered table-striped">
														<tbody>
															<tr>
																<th><spring:message code="rti.srno" /></th>
																<th><spring:message code="rti.documentName" /></th>
																<th><spring:message code="rti.rtiStatus1" /></th>
																<th><spring:message code="label.checklist.upload" />
																</th>
															</tr>
															<c:forEach items="${command.checkList}" var="lookUp"
																varStatus="lk">
																<tr>
																	<td>${lookUp.documentSerialNo}</td>
																	<c:choose>
																		<c:when
																			test="${userSession.getCurrent().getLanguageId() eq 1}">
																			<td>${lookUp.doc_DESC_ENGL}</td>
																		</c:when>
																		<c:otherwise>
																			<td>${lookUp.doc_DESC_Mar}</td>
																		</c:otherwise>
																	</c:choose>
																	<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																		<td><spring:message code="water.doc.mand" /></td>
																	</c:if>
																	<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																		<td><spring:message code="water.doc.opt" /></td>
																	</c:if>
																	<td>
																		<div id="docs_${lk}" class="">
																			<apptags:formField fieldType="7" labelCode=""
																				hasId="true" fieldPath="checkList[${lk.index}]"
																				isMandatory="false" showFileNameHTMLId="true"
																				fileSize="BND_COMMOM_MAX_SIZE"
																				maxFileCount="CHECK_LIST_MAX_COUNT"
																				validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																				currentCount="${lk.index}"
																				checkListDesc="${docName}" />
																		</div>
																	</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>
							</c:if>



						</div>

						<div id="paymentDetails">
							<c:if test="${command.offlineDTO.amountToShow > '0'}">

								<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />

								<div class="form-group margin-top-10" id="AMTSHOW">
									<label class="col-sm-2 control-label"> <spring:message
											code="rti.amtpay" /></label>
									<div class="col-sm-4">
									<fmt:formatNumber value="${command.offlineDTO.amountToShow}" type="number" pattern="#0.00" var="formattedAmount" maxFractionDigits="2" minFractionDigits="2"/>
										<input type="text" class="form-control"
											value="${formattedAmount}" maxlength="12"
											readonly="readonly" /> <a
											class="fancybox fancybox.ajax text-small text-info"
											href="RtiApplicationDetailForm.html?showChargeDetails"> <spring:message
												code="rti.amtpay" /> <i class="fa fa-question-circle "></i></a>
									</div>
								</div>
							</c:if>
						</div>

						<div class="padding-top-10 text-center">
							<button type="button" class="btn btn-success btn-submit"
								id="save" onclick="saveRtiForm(rtiForm)">
								<spring:message code="rti.submit" />
							</button>
							<button type="Reset" class="btn btn-warning" id="resetform"
								onclick="resetRtiForm(this)">
								<spring:message code="rti.reset" />
							</button>
							<apptags:backButton url="AdminHome.html"></apptags:backButton>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>







