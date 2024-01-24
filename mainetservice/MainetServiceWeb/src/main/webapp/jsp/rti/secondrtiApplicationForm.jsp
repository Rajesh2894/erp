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
<script type="text/javascript" src="js/rti/SecondApealRtiApplication.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>

<style>
/* .ui-datepicker-calendar {
	display: none;
} */
​
</style>

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
					<b><spring:message code="rti.applFormHeader1"
							text="Second Appeal Rti Application"></spring:message></b>

				</h2>

			</div>

			<div class="widget-content padding">
				<form:hidden path="command.isValidationError"
					value="${command.isValidationError}" id="isValidationError" />
				<form:form method="POST" action="SecondApealRtiApplication.html"
					class="form-horizontal" id="secondrtiForm" name="secondrtiForm">
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

										<%-- <apptags:input labelCode="rti.mobile1" cssClass="form-control hasMobileNo" path="reqDTO.mobileNo" isMandatory="true" maxlegnth="10" isDisabled="true"></apptags:input> --%>

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


									<div class="form-group">

										<label class="col-sm-2 control-label required-control"
											for="Decision Date"><spring:message
												code="rti.decisionReceiveDate"
												text="Date on which decision receive" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass datepicker"
													id="rtiDeciRecDate" path="reqDTO.rtiDeciRecDate"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>


										<apptags:input labelCode="rti.decisionDetails"
											path="reqDTO.rtiDeciDet" cssClass="hasCharacter"
											isMandatory="" isDisabled="" maxlegnth="240"></apptags:input>

									</div>

									<div class="form-group">

										<apptags:input labelCode="rti.actionTakenPIO"
											path="reqDTO.rtiPioAction" cssClass="hasCharacter"
											isMandatory="true" isDisabled="" maxlegnth="240"></apptags:input>

										<label class="col-sm-2 control-label required-control"
											for="actiondate"><spring:message
												code="rti.actionDate" text="Action Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass datepicker"
													id="rtiPioActionDate" path="reqDTO.rtiPioActionDate"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
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
													maxlength="1000" path="" disabled="false"></form:textarea>
											</div>

											<apptags:input labelCode="rti.pinCode" cssClass="hasPincode"
												path="" maxlegnth="6" isDisabled="false"></apptags:input>

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
											isMandatory="true" showOnlyLabel="applicantinfo.label.title" />

										<label class="col-sm-2 control-label required-control"><spring:message
												code="rti.location" /></label>
										<apptags:lookupField items="${command.locations}"
											cssClass="form-control" path="reqDTO.rtiLocationId"
											hasChildLookup="false" hasId="true" showAll="true"
											selectOptionLabelCode="applicantinfo.label.select"
											isMandatory="true" showOnlyLabel="applicantinfo.label.title" />


									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="subject"><spring:message code="rti.subject" /></label>

										<div class="col-sm-10">
											<div class="col-sm-10">
												<form:input path="reqDTO.rtiSubject"
													class="form-control mandColorClass" id="RtiSubject"
													data-rule-required="true" maxlength="500" />
												<!-- <input type="text" class="form-control mandColorClass" name="text" id="RtiSubject" data-rule-required="true" path="reqDTO.rtiSubject"></input> -->
											</div>

											<div class="col-sm-10 col-sm-offset-2">
												<h6 class="text-blue-2">
													<spring:message code="rti.rtimaxSubject"></spring:message>
												</h6>
											</div>
											<form:hidden path="reqDTO.appealType" id="appealTypeId" />
										</div>

										<div class="form-group">

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
										</div>
									</div>
								</div>
							</div>

							<!------------------------------------------------------------  -->
							<!--  AttachDocuments Form starts here -->
							<!------------------------------------------------------------  -->
							<c:if
								test="${not empty command.documentList &&  command.serviceFlag=='Y'}">
								<c:if test="${command.reqDTO.appealType =='S'}">
									<div class="panel panel-default" id="accordion_single_collapse">
										<h4 class="panel-title table" id="">
											<a data-toggle="collapse" class=""
												data-parent="#accordion_single_collapse" href="#a19"> <spring:message
													code="" text="Attached Documents" /></a>
										</h4>
										<div id="a19" class="panel-collapse collapse in">
											<div class="panel-body">
												<div class="form-group">
													<div class="col-sm-12 text-left">
														<div class="table-responsive">
															<table class="table table-bordered table-striped"
																id="attachDocs">
																<tr>
																	<th><spring:message code="scheme.document.name"
																			text="" /></th>
																	<th><spring:message code="scheme.view.document"
																			text="" /></th>
																</tr>
																<c:forEach items="${command.documentList}" var="lookUp">
																	<c:if test="${not empty lookUp.attFname}">
																		<tr>
																			<td align="center">${lookUp.clmDesc}</td>
																			<td align="center"><apptags:filedownload
																					filename="${lookUp.attFname}"
																					filePath="${lookUp.attPath}"
																					actionUrl="SecondApealRtiApplication.html?Download">
																				</apptags:filedownload></td>
																		</tr>
																	</c:if>
																</c:forEach>
															</table>
														</div>
													</div>
												</div>
												<div class="form-group"></div>
											</div>
										</div>
									</div>
								</c:if>
							</c:if>
							<!------------------------------------------------------------  -->
							<!--   AttachDocuments Form  ends here -->
							<!------------------------------------------------------------  -->


							<!------------Second RTI CheckList Start--------------------->
							<div id="secondChecklist">

								<c:if test="${not empty command.secondcheckList}">
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
										<div id="Upload_Attachment2"
											class="panel-collapse collapse in">
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
																<c:forEach items="${command.secondcheckList}"
																	var="lookUp" varStatus="lk">
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
																					hasId="true"
																					fieldPath="secondcheckList[${lk.index}]"
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


							<!-------------------Second RTI CheckList End----------------------->

						</div>
						<div class="padding-top-10 text-center">
							<button type="button" class="btn btn-success btn-submit"
								id="save" onclick="saveSecondAppealForm(secondrtiForm)">
								<spring:message code="rti.submit" />
							</button>
							<button type="button" class="btn btn-success btn-submit"
								id="back" onclick="rtiBack()">Back</button>
						</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
