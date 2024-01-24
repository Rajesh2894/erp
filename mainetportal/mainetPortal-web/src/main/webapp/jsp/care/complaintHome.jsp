<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/care/complaint-registration.js"></script>


<!-- Start Content here -->
<div class="content animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="care.registration" />
			</h2>
			<div class="mand-label clearfix">
				<span><spring:message code="care.fieldwith" /><i
					class="text-red-1">* </i> <spring:message code="care.ismendatory" /></span>
			</div>

			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message
							code="care.help" /></span></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form id="care" name="care" class="form-horizontal"
				commandName="command" action="grievance.html" method="POST"
				enctype="multipart/form-data">
				<jsp:include page="/jsp/care/validationComplaint.jsp" />
				<form:hidden path="userSession.employee.emploginname"
					id="currentUser" />

				<div class="box NewComplaint">
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse"
										href="#referenceDetails"> <spring:message
											code="care.applicantInformation" />
									</a>
								</h4>
							</div>
							<div id="referenceDetails" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="Mode"><spring:message code="care.mode"
												text="Mode" /></label>
										<apptags:lookupField items="${command.getLevelData('RFM')}"
											path="careRequestDto.referenceMode" cssClass="form-control"
											disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="Select" hasId="true"
											isMandatory="true" />

										<label class="col-sm-2 control-label required-control"
											for="Category"><spring:message code="care.category"
												text="Category" /></label>
										<apptags:lookupField items="${command.getLevelData('RFC')}"
											path="careRequestDto.referenceCategory"
											cssClass="form-control"
											disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="Select" hasId="true"
											isMandatory="true" />

									</div>
									<div class="form-group">
										<apptags:lookupFieldSet
											cssClass="form-control required-control" baseLookupCode="CWZ"
											hasId="true" pathPrefix="careRequestDto.ward"
											hasLookupAlphaNumericSort="true"
											disabled="${command.saveMode eq 'V' ? true : false }"
											hasSubLookupAlphaNumericSort="true" showAll="false"
											isMandatory="true" />
									</div>

									<div class="form-group">
										<c:if test="${command.saveMode eq 'V' ? true : false }">
											<label
												class="col-sm-2 control-label required-control datePicker"><spring:message
													code="care.referencedate" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input class="form-control datepicker"
														path="careRequestDto.referenceDate" isMandatory="true"
														id="referenceDate"
														disabled="${command.saveMode eq 'V' ? true : false }" />
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
												</div>
											</div>
										</c:if>
										<c:if test="${command.saveMode eq 'V' ? false : true }">
											<apptags:date labelCode="care.referencedate"
												fieldclass="datepicker" showDefault="true"
												datePath="careRequestDto.referenceDate" isMandatory="true"></apptags:date>
										</c:if>
									</div>

									<div class="form-group">
										<c:set var="ApplicantType" value="${command.applicationType}"
											scope="session" />
										<c:set var="ComplaintLabel" value="${command.labelType}"
											scope="session" />
										<apptags:radio radioLabel="${ComplaintLabel}"
											radioValue="${ApplicantType}"
											labelCode="care.applicationType"
											path="careRequestDto.applnType" disabled=""
											defaultCheckedValue="C" changeHandler="requestType()"></apptags:radio>
									</div>

								</div>
							</div>

						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#Applicant">
										<spring:message code="care.applicantInformation" />
									</a>
								</h4>
							</div>
							<jsp:include page="/jsp/care/applicantDetails.jsp" />

						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse"
										href="#Complaint_Location_Details"><spring:message
											code="care.complaintdetails" /></a>
								</h4>
							</div>
							<div id="Complaint_Location_Details"
								class="panel-collapse collapse in">
								<div class="panel-body">
									<c:if test="${userSession.organisation.defaultStatus eq 'Y'}">
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="district"><spring:message code="care.district"
													text="District" /></label>
											<apptags:lookupField items="${command.getLevelData('DIS')}"
												path="careRequestDto.district"
												cssClass="form-control chosen-select-no-results"
												disabled="${command.saveMode eq 'V' ? true : false }"
												selectOptionLabelCode="Select" hasId="true"
												isMandatory="false" changeHandler="getOrganisation()" />


											<label class="col-sm-2 control-label required-control"
												for="orgId"><spring:message code="care.organization"
													text="Organization" /></label>
											<div class="col-sm-4">
												<form:select path="careRequestDto.orgId"
													cssClass="form-control chosen-select-no-results" id="orgId"
													onchange="getDepartment();"
													disabled="${command.saveMode eq 'V' ? true : false }">
													<form:option value="0">
														<spring:message code='Select' text="Select" />
													</form:option>
													<c:forEach items="${command.org}" var="list">
														<form:option value="${list.orgid}">${list.oNlsOrgname}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>

									</c:if>
									<div class="form-group">
										<!--  <div id="id_department_div"> -->
										<label class="col-sm-2 control-label required-control"
											for="departmentComplaint"><spring:message
												code="care.department" /></label>
										<div class="col-sm-4">
											<form:select class="form-control chosen-select-no-results"
												name="" id="departmentComplaint"
												disabled="${command.saveMode eq 'V' ? true : false }"
												path="careRequestDto.departmentComplaint"
												onchange="getComplaintTypeAndLoc()">
												<form:option value="0">
													<spring:message code="Select" text="Select" />
												</form:option>
												<c:forEach var="dep" items="${command.dept}">
													<form:option value="${dep.department.dpDeptid}">
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId()== '1'}">${dep.department.dpDeptdesc}</c:when>
															<c:otherwise>${dep.department.dpNameMar}</c:otherwise>
														</c:choose>
													</form:option>
												</c:forEach>
											</form:select>
										</div>

										<label class="col-sm-2 control-label required-control"
											for="complaintType"><spring:message
												code="care.complaintType" text="Complaint Type" /></label>
										<div class="col-sm-4">
											<form:select class="form-control chosen-select-no-results"
												name="" id="complaintType"
												disabled="${command.saveMode eq 'V' ? true : false }"
												path="careRequestDto.complaintType">
												<form:option value="0">
													<spring:message code='Select' text="Select" />
												</form:option>
												<c:forEach var="comp" items="${command.complaintTypes}">
													<form:option value="${comp.lookUpId}">${comp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>

									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="ComplaintDescription"><spring:message
												code="care.complaintDescription" /></label>
										<div class="col-sm-4">												
											<form:textarea cssClass="form-control" path="careRequestDto.description" 
												rows="" maxlength="3000" id="ComplaintDescription" onkeyup="countCharacter(this,3000,'complaintDescriptionCount')"
												 onfocus="countCharacter(this,3000,'complaintDescriptionCount')" disabled="${command.saveMode eq 'V' ? true : false }"/>
											<div class="pull-right">
												<spring:message code="charcter.remain" text="characters remaining " /><span id="complaintDescriptionCount">3000</span>
											</div>
										</div>

										<label class="col-sm-2 control-label required-control"
											for="location"><spring:message
												code="care.nearestLocation" text="Nearest Location" /></label>
										<div class="col-sm-4">
											<form:select class="form-control chosen-select-no-results"
												name="" id="location" path="careRequestDto.location"
												onchange="getPincode()"
												disabled="${command.saveMode eq 'V' ? true : false }">
												<form:option value="0">
													<spring:message code='Select' text="Select" />
												</form:option>
												<c:forEach var="loc" items="${command.locations}">
													<form:option value="${loc.lookUpId}">${loc.descLangFirst}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label" for="Pincode2"><spring:message
												code="care.complaintPincode" /></label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control hasNumber"
												id="Pincode2" path="" maxlength="6"
												disabled="${command.saveMode eq 'V' ? true : false }"
												onchange="getLocation()"></form:input>
										</div>
										<label class="col-sm-2 control-label" for="Landmark"><spring:message
												code="care.landmark" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" id="Landmark"
												class="form-control" path="careRequestDto.landmark"
												disabled="${command.saveMode eq 'V' ? true : false }"></form:input>
										</div>
									</div>
									
									<div class="form-group">
										<div id="residentDivId">
											<label class="col-sm-2 control-label required-control" for="residentId"><spring:message
												code="care.residentId" text="Resident ID (UID/Election ID)" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" id="residentId" class="form-control"
													path="careRequestDto.residentId" maxlength="19" 
													disabled="${command.saveMode eq 'V' ? true : false }"></form:input>
											</div>
										</div>
									</div>
									<c:if test="${command.saveMode eq 'V' ? false : true }">
										<div class="form-group">
											<label class="col-sm-2 control-label"><spring:message
													code="care.uploadphoto" /></label>
											<div class="col-sm-4">
												<apptags:formField fieldType="7" labelCode="" hasId="true"
													fieldPath="" isMandatory="false" showFileNameHTMLId="true"
													fileSize="BND_COMMOM_MAX_SIZE"
													maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
													validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
													currentCount="0" />
												<div class="col-sm-8">
													<small class="text-blue-2"><spring:message
															code="care.attachmentsNote" /></small>
												</div>

											</div>

											<c:if test="${command.loginFlag eq 'N'}">
												<div id="otpReqDivId">
													<div class="col-sm-2">
														<button type="button" id="btnOTP" class="btn btn-success"
															onclick="generateOTP()">
															<spring:message code="care.generateOTP"
																text="Generate OTP" />
														</button>
													</div>
													<spring:message code="care.placeholder.MobileOTP"
														text="Please Enter Mobile OTP" var="placeholderMobileOTP"></spring:message>
													<div class="col-sm-4">
														<form:input type="text" maxlength="6" id="mobileOTP"
															class="form-control" path="enteredMobileOTP"
															isMandatory="true" placeholder="${placeholderMobileOTP}"></form:input>
														<small class="text-blue-2 otp"></small>
													</div>
												</div>

											</c:if>

										</div>
									</c:if>
								</div>
							</div>
						</div>
					</div>
					<c:if test="${not empty command.attachments}">
						<!------------------------------------------------------------  -->
						<!--  AttachDocuments Form starts here -->
						<!------------------------------------------------------------  -->
						<div class="panel panel-default" id="accordion_single_collapse">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a19"> <spring:message
										code="care.attach.document" text="Attached Documents" /></a>
							</h4>
							<div id="a19" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-sm-12 text-left">
											<div class="table-responsive">
												<table class="table table-bordered table-striped"
													id="attachDocs">
													<tr>
														<th><spring:message code="care.document.name"
																text="Document Name" /></th>
														<th><spring:message code="care.view.document"
																text="View Documents" /></th>
													</tr>
													<c:forEach items="${command.attachments}" var="lookUp">
														<tr>
															<td align="center">${lookUp.documentName}</td>
															<td align="center"><apptags:filedownload
																	filename="${lookUp.documentName}"
																	filePath="${lookUp.uploadedDocumentPath}"
																	actionUrl="grievance.html?Download">
																</apptags:filedownload></td>
														</tr>
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
					<!------------------------------------------------------------  -->
					<!--   AttachDocuments Form  ends here -->
					<!------------------------------------------------------------  -->



					<div class="text-center">
						<c:if test="${command.saveMode eq 'V' ? false : true }">
							<button type="button" id="btnSave" class="btn btn-success"
								onclick="saveOrUpdateForm(this,'','','saveDetails')">
								<spring:message code="care.submit" />
							</button>
							<%-- <apptags:resetButton cssClass="rstButton"></apptags:resetButton> --%>
							<button type="Reset" class="btn btn-warning" onclick="resetFormData()">
								<spring:message code="care.reset" text="Reset" />
							</button>
						</c:if>
						<button type="button" class="btn btn-danger"
							onclick="window.location.href='CitizenHome.html'">
							<spring:message code="care.back" text="Back" />
						</button>
					</div>
				</div>
				<input type="hidden" id="taskName" name="taskName"
					value="${taskName}" />
				<input type="hidden" id="taskId" name="taskId" value="${taskId}" />
				<input type="hidden" id="processName" name="processName"
					value="${processName}" />
				<input type="hidden" id="mode" name="mode" value="${mode}" />
				<form:hidden id="id" name="id" path="" value="${careRequest.id}" />
				<form:hidden id="decision" name="decision" path=""
					value="${actionStatus}" />
				<form:hidden id="comments" name="comments" path=""
					value="${actionComment}" />
				<input type="hidden" id="reopenComplaintMode"
					value="${reopenComplaintMode}" />
				<input type="hidden" id="complaintTypeHidden"
					name="complaintTypeHidden" value="${complaintType}" />
				<form:hidden id="id_latitude" name="latitude"
					path="careRequestDto.latitude" />
				<form:hidden id="id_longitude" name="longitude"
					path="careRequestDto.longitude" />
			</form:form>
		</div>
	</div>
</div>
<script>
	$(document)
			.ready(
					function() {

						$("#hiddenDOCprefix").hide();

						var complaintTypeId = document
								.getElementById("complaintTypeHidden").value;
						if (complaintTypeId != '') {
							var getAllComplaintTypeByDepartment = "departmentcomplaint/ajax/findDepartmentComplaintByDepartmentId.html";
							var id = document
									.getElementById("departmentComplaint").value;
							var requestData = 'id=' + id;
							var response = __doAjaxRequest(
									getAllComplaintTypeByDepartment, 'post',
									requestData, false, 'html');
							var obj = jQuery.parseJSON(response);
							if (obj != null) {
								$('#ComplaintSubType').empty();
								$('#ComplaintSubType').append(
										'<option value="">Select</option>');
								$.each(obj, function(i, item) {
									if (complaintTypeId == item.compId) {
										$('#ComplaintSubType').append(
												'<option  value="' + item.compId + '"selected=selected>'
														+ item.complaintDesc
														+ '</option>');
									} else {
										$('#ComplaintSubType').append(
												'<option  value="' + item.compId + '">'
														+ item.complaintDesc
														+ '</option>');
									}
								});
							}

						}

						var reopenComplaintMode = document
								.getElementById("reopenComplaintMode").value;
						if (reopenComplaintMode == "true") {
							document.getElementById('ReopenComplaint').checked = true;
							$(".box").not(".ExistingComplaint").hide();
							$(".ExistingComplaint").show();
							var response = __doAjaxRequest(
									"grievance.html?getAllGrievanceRaisedByRegisteredCitizenView",
									'post', "", false, 'html');
							$('#reopenComplaintId').html(response);
						}

						$('#Title1').attr("readonly", "readonly");
						$('#Gender').attr("readonly", "readonly");

					});
</script>

