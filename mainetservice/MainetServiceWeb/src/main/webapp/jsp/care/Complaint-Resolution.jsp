<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/care/complaint-resolution.js"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?libraries=places&key=AIzaSyAvvwgwayDHlTq9Ng83ouZA_HWSxbni25c"></script>
<style>
textarea.form-control {
	resize: vertical !important;
	height: 4.6em;
}
</style>
<link rel="stylesheet"
	href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css"
	type="text/css">
<script type="text/javascript"
	src="assets/libs/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="care.complaint.resloution"
					text="Complaint Resolution" />
			</h2>

			<apptags:helpDoc url="GrievanceResolution.html"></apptags:helpDoc>

		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="care.fieldwith"
						text="Field with " /> <i class="text-red-1">*</i> <spring:message
						code="care.ismandatory" text="is mandatory" /> </span>
			</div>
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>


			<form:form id="care" name="care" class="form-horizontal"
				commandName="command" action="GrievanceResolution.html"
				method="POST" enctype="multipart/form-data">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="" id="reqType" value="${command.requestType}" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#referenceDetails"> <spring:message
										code="care.referencedetails" text="Reference Details" />
								</a>
							</h4>
						</div>

						<div id="referenceDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<c:if test="${command.kdmcEnv ne 'Y'}">
									<!-- hide in case of KDCM -->
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="Mode"><spring:message code="care.mode"
												text="Mode" /></label>
										<apptags:lookupField items="${command.getLevelData('RFM')}"
											path="careRequest.referenceMode" cssClass="form-control"
											selectOptionLabelCode="Select" hasId="true"
											isMandatory="true" disabled="true" />

										<label class="col-sm-2 control-label required-control"
											for="Category"><spring:message code="care.category"
												text="Category" /></label>
										<apptags:lookupField items="${command.getLevelData('RFC')}"
											path="careRequest.referenceCategory" cssClass="form-control"
											selectOptionLabelCode="Select" hasId="true"
											isMandatory="true" disabled="true" />

									</div>
								</c:if>
								
								<!-- #130188 -->
								<div class="form-group">
									<apptags:lookupFieldSet cssClass="form-control required-control"
										baseLookupCode="${command.prefixName}" hasId="true" pathPrefix="careRequest.ward"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" showAll="false"
										isMandatory="true" disabled="true" />
								</div>
								<%-- <div class="form-group">
									<apptags:input labelCode="care.zone"
										path="careRequest.ward1Desc" isDisabled="true"></apptags:input>

									<c:if test="${command.careRequest.ward2Desc ne null}">
										<apptags:input labelCode="care.word"
											path="careRequest.ward2Desc" isDisabled="true"></apptags:input>
									</c:if>
								</div>
								<div class="form-group">
									<c:if test="${command.careRequest.ward3Desc ne null}">

										<apptags:input labelCode="care.word"
											path="careRequest.ward3Desc" isDisabled="true"></apptags:input>
									</c:if>
									<c:if test="${command.careRequest.ward4Desc ne null}">
										<apptags:input labelCode="care.word"
											path="careRequest.ward4Desc" isDisabled="true"></apptags:input>
									</c:if>
								</div>
								<div class="form-group">
									<c:if test="${command.careRequest.ward5Desc ne null}">
										<apptags:input labelCode="care.word"
											path="careRequest.ward5Desc" isDisabled="true"></apptags:input>
									</c:if>
								</div> --%>

								<div class="form-group">
									<apptags:date labelCode="care.referencedate"
										fieldclass="datepicker" showDefault="false"
										datePath="careRequest.referenceDate" isMandatory="true"
										isDisabled="true"></apptags:date>
								</div>

								<div class="form-group">
									<%-- 
									<apptags:radio radioLabel="Service Request,Complaint"
										radioValue="R,C" labelCode="care.applicationType"
										path="careRequest.applnType" defaultCheckedValue="C"
										disabled="true"></apptags:radio> --%>
									<c:set var="ApplicantType" value="${command.applicationType}"
										scope="session" />
									<c:set var="ComplaintLabel" value="${command.labelType}"
										scope="session" />
									<apptags:radio radioLabel="${ComplaintLabel}"
										radioValue="${ApplicantType}" labelCode="care.applicationType"
										path="careRequest.applnType" disabled="true"
										defaultCheckedValue="C"></apptags:radio>
								</div>


							</div>
						</div>

						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#Applicant">
									<spring:message code="care.applicantInformation"
										text="Applicant Information" />
								</a>
							</h4>
						</div>
						<div id="Applicant" class="panel-collapse collapse">
							<div class="panel-body">

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="titleId"><spring:message code="care.name" /></label>

									<div class="col-sm-1 padding-right-0">
										<form:input name="title" type="text" class="form-control"
											id="Title1" path="" value="${title}" readonly="true"></form:input>

									</div>

									<div class="col-sm-1 padding-right-0 padding-left-0">
										<form:input type="text" class="form-control" id="FirstName1"
											readonly="true" path="applicantDetailDto.fName"></form:input>
									</div>
									<div class="col-sm-1 padding-right-0 padding-left-0">
										<form:input type="text" class="form-control" id="MiddleName"
											readonly="true" path="applicantDetailDto.mName"></form:input>
									</div>
									<div class="col-sm-1  padding-left-0">
										<form:input type="text" class="form-control" id="LastName1"
											readonly="true" path="applicantDetailDto.lName"></form:input>
									</div>
									<label class="col-sm-2 control-label required-control"
										for="MobileNumber"><spring:message
											code="care.mobilenumber" text="Mobile Number" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control" id="MobileNumber"
											readonly="true" path="applicantDetailDto.mobileNo"></form:input>
									</div>
								</div>


								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="Gender"><spring:message code="care.gender"
											text="Gender" /></label>
									<div class="col-sm-4">
										<form:input name="Gender" type="text" class="form-control"
											id="Gender" path="" value="${gender}" readonly="true"></form:input>
									</div>
									<label class="col-sm-2 control-label" for="EmailID"><spring:message
											code="care.emailid" text="Email ID" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control" id="EmailID"
											readonly="true" path="applicantDetailDto.email"></form:input>
									</div>

								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="address"><spring:message code="care.address"
											text="Address" /></label>
									<div class="col-sm-4">
										<form:textarea class="form-control" id="address"
											maxlength="1000" readonly="true"
											path="applicantDetailDto.areaName"></form:textarea>
									</div>
									<label class="col-sm-2 control-label" for="Pincode"><spring:message
											code="care.pincode" text="Pincode" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control" id="Pincode"
											readonly="true" path="applicantDetailDto.pincodeNo"></form:input>
									</div>
								</div>


							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse"
									href="#Complaint_Details"><spring:message
										code="care.complaintdetails" text="Complaint Details" /></a>
							</h4>
						</div>
						<div id="Complaint_Details" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label" for="TokenNumber"><spring:message
											code="care.tokenno" text="Token Number:" /> </label>
									<div class="col-sm-4">
										<form:input name="" type="text" id="tokenNumber"
											class="form-control" readonly="true" path=""
											value="${complaintId}"></form:input>
									</div>

									<label class="col-sm-2 control-label" for="extReferNumber"><spring:message
											code="care.extReferNumber" text="care.extReferNumber" /></label>
									<div class="col-sm-4">
										<form:input name="" readonly="true" type="text"
											id="extReferNumber" class="form-control"
											path="careRequest.extReferNumber"></form:input>
									</div>

								</div>

								<div class="form-group">
									<%-- <label class="col-sm-2 control-label required-control"
										for="Department"><spring:message code="care.department" text="Department"/></label>
									<div class="col-sm-4">
										 <form:select class="form-control" name="" id="Department"
											disabled="true"
											path="careRequest.departmentComplaintDesc">
											<form:option value="${command.careRequest.departmentComplaint}">
												<c:choose>
									                <c:when test="${userSession.languageId eq 1}">
									                	<h3 class="margin-bottom-0">${command.careRequest.departmentComplaintDesc}</h3>
									                </c:when>
									                <c:otherwise>
									                	<h3 class="margin-bottom-0">${command.careRequest.departmentComplaintDescReg}</h3>
									                </c:otherwise>
								               </c:choose> 
											</form:option>
										</form:select> 
									</div>
									
									<label class="col-sm-2 control-label required-control"
										for="ComplaintType"><spring:message code="care.complaintType" text="Complaint Type"/></label>
									<div class="col-sm-4">
										<form:select class="form-control" name="" id="ComplaintType"
											disabled="true" path="careRequest.complaintType">
											<form:option value="${command.careRequest.complaintType}">
												<c:choose>
									                <c:when test="${userSession.languageId eq 1}">
									                	<h3 class="margin-bottom-0">${command.careRequest.complaintTypeDesc}</h3>
									                </c:when>
									                <c:otherwise>
									                	<h3 class="margin-bottom-0">${command.careRequest.complaintTypeDescReg}</h3>
									                </c:otherwise>
								               </c:choose> 
											</form:option>
										</form:select>
									</div> --%>

									<label class="col-sm-2 control-label" for=""><spring:message
											code="care.department" text="Department" /> </label>
									<div class="col-sm-4">
										<c:choose>
											<c:when test="${userSession.languageId eq 1}">
												<form:input name="" type="text" id="" class="form-control"
													readonly="true" path=""
													value="${command.careRequest.departmentComplaintDesc}"></form:input>
											</c:when>
											<c:otherwise>
												<form:input name="" type="text" id="Department"
													class="form-control" readonly="true" path=""
													value="${command.careRequest.departmentComplaintDescReg}"></form:input>
											</c:otherwise>
										</c:choose>

									</div>

									<label class="col-sm-2 control-label" for=""><spring:message
											code="care.complaintType" text="Complaint Type" /></label>
									<div class="col-sm-4">
										<c:choose>
											<c:when test="${userSession.languageId eq 1}">
												<form:input name="" type="text" id="" class="form-control"
													readonly="true" path=""
													value="${command.careRequest.complaintTypeDesc}"></form:input>
											</c:when>
											<c:otherwise>
												<form:input name="" type="text" id="Department"
													class="form-control" readonly="true" path=""
													value="${command.careRequest.complaintTypeDescReg}"></form:input>
											</c:otherwise>
										</c:choose>

									</div>

								</div>
								<div class="form-group">

									<label class="col-sm-2 control-label required-control"
										for="ComplaintDescription"><spring:message
											code="care.complaintdescription" text="Complaint Description" /></label>
									<div class="col-sm-4">
										<form:textarea name="" cols="" rows="" class="form-control"
											id="ComplaintDescription" readonly="true"
											path="careRequest.description"></form:textarea>
									</div>
									<div id="residentDivId">
										<label class="col-sm-2 control-label required-control" for="residentId"><spring:message
											code="care.residentId" text="Resident ID (UID/Election ID)" /></label>
										<div class="col-sm-4">
											<form:input readonly="true" type="text" id="residentId" class="form-control"
												path="careRequest.residentId" maxlength="19" ></form:input>
										</div>
									</div>
									<!-- Removed as per told by samadhan sir -->
									<%-- <label class="col-sm-2 control-label" for="Pincode2"><spring:message
											code="care.pincode" text="Pincode" /></label>
									<div class="col-sm-4">
										<form:input name="" type="text" class="form-control"
											id="Pincode2" readonly="true" path="careRequest.pincode"></form:input>
									</div> --%>
								</div>

								<div class="form-group">
									<c:choose>
										<c:when test="${command.kdmcEnv eq 'Y'}">

											<label class="col-sm-2 control-label" for="pendingDays"><spring:message
													code="care.pending.days" text="Pending Days" /></label>
											<div class="col-sm-4">
												<form:input name="" type="text" id="pendingDays"
													class="form-control" readonly="true" path="ageOfRequest"></form:input>
											</div>

										</c:when>
										<c:otherwise>
											<label class="col-sm-2 control-label required-control"
												for="ComplaintLocation"><spring:message
													code="care.location" text="Complaint Location" /></label>
											<div class="col-sm-4">
												<c:choose>
													<c:when test="${userSession.languageId eq 1}">
														<form:input name="" type="text" class="form-control"
															id="ComplaintLocation" readonly="true"
															path="careRequest.locationEngName"></form:input>
													</c:when>
													<c:otherwise>
														<form:input name="" type="text" class="form-control"
															id="ComplaintLocation" readonly="true"
															path="careRequest.locationRegName"></form:input>
													</c:otherwise>
												</c:choose>
											</div>
										</c:otherwise>
									</c:choose>

									<label class="col-sm-2 control-label" for="Landmark"><spring:message
											code="care.landmark" text="Landmark" /></label>
									<div class="col-sm-4">
										<form:input name="" type="text" id="Landmark"
											class="form-control" readonly="true"
											path="careRequest.landmark"></form:input>
									</div>
								</div>

								<c:if test="${command.kdmcEnv ne 'Y'}">
									<!-- hide in case of KDCM -->
									<div class="form-group">
										<label class="col-sm-2 control-label" for="pendingDays"><spring:message
												code="care.pending.days" text="Pending Days" /></label>
										<div class="col-sm-4">
											<form:input name="" type="text" id="pendingDays"
												class="form-control" readonly="true" path="ageOfRequest"></form:input>
										</div>
									</div>
								</c:if>

								<div class="text-center">
									<a data-toggle="collapse" href="#collapseExample"
										class="btn btn-blue-2" id="viewloc"><i
										class="fa fa-map-marker"></i> <spring:message
											code="care.view.complaint.location"
											text="View Complaint Location" /></a>
								</div>
								<div class="collapse margin-top-10" id="collapseExample">
									<div class="border-1 padding-5"
										style="height: 400px; width: 1000px;" id="map-canvas"></div>
								</div>
							</div>
						</div>
					</div>
					<c:if test="${not empty actions}">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#ActionHistory"><spring:message
											code="care.action.history" text="Action History" /></a>
								</h4>
							</div>
							<div id="ActionHistory" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="table-responsive">

										<table class="table table-bordered table-condensed">
											<tr>
												<th><spring:message code="care.srno" text="Sr. No." /></th>
												<th><spring:message code="care.action.datetime"
														text="Date & Time" /></th>
												<th width="18%"><spring:message
														code="care.action.Action" text="Action" /></th>
												<th><spring:message code="care.action.employee.name"
														text="Employee Name" /></th>
												<th><spring:message code="care.action.employee.email"
														text="Email" /></th>
												<th><spring:message code="care.action.designation"
														text="Designation" /></th>
												<th width="20%"><spring:message
														code="care.action.remarks" text="Remarks" /></th>
												<th><spring:message code="care.action.attachments"
														text="Attachments" /></th>
											</tr>
											<c:set var="rowCount" value="0" scope="page" />
											<c:forEach items="${actions}" var="action" varStatus="status">
												<tr>
													<td><c:set var="rowCount" value="${rowCount+1}"
															scope="page" /> <c:out value="${rowCount}"></c:out></td>
													<td><fmt:formatDate pattern="dd/MM/yyyy hh:mm a"
															value="${action.dateOfAction}" /></td>
													<td><c:out value="${action.decision}"></c:out></td>
													<td><c:out value="${action.empName}"></c:out></td>
													<td><c:out value="${action.empEmail}"></c:out></td>
													<td><c:if test="${not empty action.empGroupDescEng}">
															<c:out value="${userSession.languageId eq 1 ? action.empGroupDescEng : action.empGroupDescReg}"></c:out>
														</c:if> </td>
														<!-- D#129373 -->
														<%-- <c:if test="${empty action.empGroupDescEng}">
															<spring:message code="care.Citizen" text="Citizen" />
														</c:if> --%>
													<td><c:out value="${action.comments}"></c:out></td>
													<td>
														<ul>
															<c:forEach items="${action.attachements}" var="lookUp"
																varStatus="status">
																<li><apptags:filedownload
																		filename="${lookUp.lookUpCode}"
																		filePath="${lookUp.defaultVal}"
																		dmsDocId="${lookUp.dmsDocId}"
																		actionUrl="GrievanceDepartmentReopen.html?Download"></apptags:filedownload></li>
															</c:forEach>
														</ul>
													</td>
												</tr>
											</c:forEach>
										</table>


									</div>

								</div>
							</div>
						</div>

					</c:if>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#ByDepartment"><spring:message
										code="care.department.action" text="Department Action" /></a>
							</h4>
						</div>
						<div id="ByDepartment" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="ApplicationAction"><spring:message
											code="care.actiononapplication" text="Action On Application" /></label>
									<div class="col-sm-4">
										<select id="ApplicationAction" class="form-control"
											name="careDepartmentAction.decision">
											<option value=""><spring:message code="care.select"
													text="Select" /></option>

											<%-- <c:choose>
												<c:when test="${fn:contains(command.currentEmployeeGroup.grCode, 'GR_CALLCENTER')}">
													<option value="APPROVED"><spring:message code="care.closed" text="Closed"/></option> 
													<option value="SEND_BACK"><spring:message code="care.sendBack" text="Send back to department"/></option>
												</c:when>
												<c:otherwise>
													<option value="APPROVED"><spring:message code="care.closed" text="Closed"/></option> 
													<option value="REJECTED"><spring:message code="care.rejected" text="Rejected"/></option>
													<option value="FORWARD_TO_DEPARTMENT"><spring:message code="care.forwardto.department" text="Forward to Relevant Department"/></option> 
													<option value="FORWARD_TO_EMPLOYEE"><spring:message code="care.forwardto.employee" text="Forward to Employee within Department"/></option>
													<option value="HOLD"><spring:message code="care.hold" text="Hold"/></option>
												</c:otherwise>
											</c:choose> --%>

											<c:choose>
												<c:when test="${command.kdmcEnv == 'Y' || callCenterAppli eq 'N' }">
													<c:set var="isCallCenter"
														value="${fn:contains(command.currentEmployeeGroup.grCode, 'GR_CALLCENTER')}" />
													<c:set var="baseLookupCodeCDL" value="CDL" />
													<c:set var="baseLookupCodeCLO" value="CLO" />
													<c:set var="callDenyDecisions"
														value="APPROVED,SEND_FOR_FEEDBACK" />
													<c:set var="deptDenyDecisions" value="FOLLOWUP,HOLD" />
													<%-- 	<c:set var="callDenyDecisions"
												value="APPROVED,SEND_FOR_FEEDBACK,FORWARD_TO_DEPARTMENT,FORWARD_TO_EMPLOYEE,HOLD" />
											<c:set var="deptDenyDecisions"
												value="APPROVED,SEND_BACK,FOLLOWUP,FORWARD_TO_DEPARTMENT,FORWARD_TO_EMPLOYEE,HOLD" /> --%>
												</c:when>
												<c:otherwise>
													<c:set var="isCallCenter"
														value="${fn:contains(command.currentEmployeeGroup.grCode, 'GR_CALLCENTER')}" />
													<c:set var="baseLookupCodeCDL" value="CDL" />
													<c:set var="baseLookupCodeCLO" value="CLO" />
													<%-- <c:set var="callDenyDecisions"
												value="APPROVED,SEND_FOR_FEEDBACK,HOLD" /> --%>
													<c:set var="callDenyDecisions"
														value="APPROVED,SEND_FOR_FEEDBACK,FORWARD_TO_DEPARTMENT,FORWARD_TO_EMPLOYEE,HOLD" />
													<c:set var="deptDenyDecisions"
														value="APPROVED,SEND_BACK,FOLLOWUP,HOLD" />
													<%-- 	<c:set var="callDenyDecisions"
												value="APPROVED,SEND_FOR_FEEDBACK,FORWARD_TO_DEPARTMENT,FORWARD_TO_EMPLOYEE,HOLD" />
											<c:set var="deptDenyDecisions"
												value="APPROVED,SEND_BACK,FOLLOWUP,FORWARD_TO_DEPARTMENT,FORWARD_TO_EMPLOYEE,HOLD" /> --%>
												</c:otherwise>
											</c:choose>

											<c:forEach items="${command.getLevelData(baseLookupCodeCLO)}"
												var="lookUp">
												<c:if
													test="${lookUp.lookUpCode eq 'CALL' && lookUp.otherField eq 'YES'}">
													<c:set var="callDenyDecisions"
														value="${fn:substringAfter(callDenyDecisions, 'APPROVED,')}" />
												</c:if>
												<%--  <c:if test="${lookUp.lookUpCode eq 'DEPT' && lookUp.otherField eq 'YES'}">
												 	<c:set var="deptDenyDecisions" value="${fn:substringAfter(deptDenyDecisions, 'APPROVED,')}" />
												 </c:if> --%>
											</c:forEach>

											<c:forEach items="${command.getLevelData(baseLookupCodeCDL)}"
												var="lookUp">
												<c:choose>
													<c:when test="${isCallCenter}">
														<c:if
															test="${!fn:contains(callDenyDecisions, lookUp.lookUpCode)}">
															<option value="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</option>
														</c:if>
													</c:when>
													<c:otherwise>
														<c:if
															test="${!fn:contains(deptDenyDecisions, lookUp.lookUpCode)}">
															<option value="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</option>
														</c:if>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									</div>
									
									<c:if test="${command.kdmcEnv == 'Y'}">
										<div id="reasonToForwardDiv" style="display: none;">
												<label for="reasonToForwardId"
													class="col-sm-2 control-label required-control "><spring:message
														code="care.reason" text="Reason to forward" /> </label>
											<div class="col-sm-4">
												<%-- <apptags:lookupField items="${command.getLevelData('CFR')}"
													path="careDepartmentAction.reasonToForwardId"
													cssClass="form-control" selectOptionLabelCode="Select"
													hasId="true" isMandatory="true" /> --%>
												<form:select class="form-control" name="" id="reasonToForwardId"
													path="careDepartmentAction.reasonToForwardId">
													<form:option value="">
														<spring:message code="care.select" text="Select" />
													</form:option>
													<c:forEach var="lookup" items="${command.getLevelData('CFR')}">
														<form:option value="${lookup.lookUpId}">${lookup.lookUpDesc}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>		
									</c:if>
								</div>
								
								<div class="boxaction RelevantDepartment">
									<div class="form-group">
										<label for="Department"
											class="col-sm-2 control-label required-control"><spring:message
												code="care.department" text="Department" /></label>
										<div class="col-sm-4">
											<form:select class="form-control" name="" id="dpDeptId"
												path="careDepartmentAction.forwardDepartment"
												onchange="showComplaintType(this)">
												<form:option value="">
													<spring:message code="care.select" text="Select" />
												</form:option>
												<c:forEach var="dep" items="${deptList}">
													<c:choose>
														<c:when test="${userSession.languageId eq 1}">
															<form:option value="${dep.department.dpDeptid}">${dep.department.dpDeptdesc}</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="${dep.department.dpDeptid}">${dep.department.dpNameMar}</form:option>
														</c:otherwise>
													</c:choose>
												</c:forEach>

											</form:select>
										</div>

										<div class="ComplaintSubType_Div">
											<label class="col-sm-2 control-label required-control"
												for="ComplaintSubType"><spring:message
													code="care.complaintType" text="Complaint Type" /></label>
											<div class="col-sm-4">
												<form:select class="form-control" name=""
													id="ComplaintSubType"
													path="careDepartmentAction.forwardComplaintType">
													<form:option value="">
														<spring:message code="care.select" text="Select" />
													</form:option>
												</form:select>

											</div>
										</div>

									</div>
								</div>




								<div class="form-group">
									<!-- <div id="areaMappingId"></div>  -->
									<div class="All_wardZone_Div">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="care.select" text="Select" /></label>
										<div class="col-sm-4 ">
											<span> <label class="radio-inline"> <form:radiobutton
														value="A" path="" class="WardZoneAll"
														name="deptRadioAction" /> <spring:message code="care.all"
														text="All" />
											</label> <label class="radio-inline"> <form:radiobutton
														value="W" path="" class="WardZoneAll"
														name="deptRadioAction" /> <spring:message
														code="care.wardzone" text="Ward-Zone" />
											</label>
											</span>
										</div>
										<div class="col-sm-6 "></div>
									</div>
								</div>



								<div class="wardZoneBlockDiv">
									<div class="form-group">
										<div id="areaMappingId"></div>
									</div>
								</div>
								<div class="boxaction EmployeewithinDepartment">
									<div class="form-group">
										<%-- 
										<label class="col-sm-2 control-label"><spring:message
												code="care.select" text="Select" /></label> --%>
										<div id="radio_div" class="col-sm-4">

											<%-- <label class="radio-inline"> <input
												id="id_EmployeewithinDepartment_loc"
												name="careDepartmentAction.forwardToEmployeeType"
												class="forwardtoemployee" type="radio" value="LOCATION">
												<spring:message code="care.compl.location" text="Location" /> --%>
											</label> <label class="radio-inline"> <input
												id="id_EmployeewithinDepartment_emp"
												name="careDepartmentAction.forwardToEmployeeType"
												class="forwardtoemployee" type="radio" value="EMPLOYEE">
												<spring:message code="care.employee.withindept"
													text="Employee Within Department" />
											</label>
										</div>
									</div>
									<div class="boxshowhide Locationwiseradio"
										style="display: none;">
										<div class="form-group">
											<div id="areaMappingId1"></div>
										</div>
									</div>
									<div class="boxshowhide WithinDepartment"
										style="display: none;">
										<div class="form-group">
											<label for="EmployeeName"
												class="col-sm-2 control-label required-control "><spring:message
													code="care.employeename" text="Employee Name" /> </label>
											<div class="col-sm-4">
												<form:select class="form-control" multiple="multiple"
													id="EmployeeName"
													path="careDepartmentAction.forwardToEmployee">
													<c:set var="loginEmpId"
														value="${userSession.employee.empId}"></c:set>
													<c:forEach var="employee" items="${employeeList}">
														<c:if test="${loginEmpId ne employee.empId}">
															<form:option value="${employee.empId}">${employee.empname} ${employee.empmname} ${employee.emplname}</form:option>
														</c:if>
													</c:forEach>
												</form:select>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" for="UploadDocuments"><spring:message
											code="care.upload" text="Upload Document" /></label>
									<div class="col-sm-4">
										<small class="text-blue-2"> <spring:message
												code="care.validator.fileUploadNote"
												text="(Upload File upto 5MB and only pdf,doc,docx,jpeg,jpg,png,gif)" />
										</small>
										<apptags:formField fieldType="7" labelCode="" hasId="true"
											fieldPath="" isMandatory="false" showFileNameHTMLId="true"
											fileSize="BND_COMMOM_MAX_SIZE"
											maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
											currentCount="0" />
									</div>
									<label class="col-sm-2 control-label required-control"
										for="Remark"><spring:message code="care.remarks"
											text="Remark" /></label>
									<div class="col-sm-4">
										<form:textarea class="form-control" id="Remark"
											path="careDepartmentAction.comments"
											maxlength="1000" onkeyup="countChar(this,1000,'descriptionCount')"
											 onfocus="countChar(this,1000,'descriptionCount')"></form:textarea>
											
										<div class="pull-right">
											<spring:message code="charcter.remain" text="characters remaining " /><span id="descriptionCount">1000</span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="downloadefiles"></div>
				<div class="text-center">
					<button type="button" class="btn btn-success btn-submit"
						id="btnSave">
						<spring:message code="care.submit" text="Submit" />
					</button>

					<button type="button" class="btn btn-success btn-submit"
						id="btnInspection">
						<spring:message code="care.submit" text="Submit" />
					</button>

					<button type="Reset" class="btn btn-warning"
						onclick="resetFormData()">
						<spring:message code="care.reset" text="Reset" />
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="care.back" text="Back" />
					</button>
				</div>
				<form:hidden id="applicationId" name="applicationId" path=""
					value="${applicationId}" />
				<form:hidden id="taskId" path="taskId" name="taskId"
					value="${taskId}" />
				<form:hidden id="remarks" path="" name="remarks"
					value="${careDepartmentAction.comments}" />
				<form:hidden id="compLatitude" path="" name="remarks"
					value="${command.careRequest.latitude}" />
				<form:hidden id="compLongitude" path="" name="remarks"
					value="${command.careRequest.longitude}" />
				<form:hidden id="langId" path="" name="Language Id"
					value="${userSession.languageId}" />
				<form:hidden id="kdmcEnv" path="" name="Kdmc Env"
					value="${command.kdmcEnv}" />			
			</form:form>
		</div>
	</div>
</div>


<script type="text/javascript">
	$(document)
			.ready(
					function() {
						
						//if residentId value getting than display residentDivId
						let residentValue = '${command.careRequest.residentId}';
						if(residentValue == '' || residentValue == null){
							$('#residentDivId').hide();
						}

						var documentId = $('#applicationId').val();

						$('#EmployeeName').multiselect({
							includeSelectAllOption : true,
							selectAllName : 'select-all-name'
						});

						$(function() {
							$('.multiselect-ui').multiselect({
								includeSelectAllOption : true
							});
						});

						$("#ApplicationAction")
								.change(
										function() {

											$("#dpDeptId").val('');
											$(".wardZoneBlockDiv").hide();
											$(".All_wardZone_Div").hide();
											$(".ComplaintSubType_Div").hide();
											$('input[name=WardZoneAll]')
													.removeAttr("checked",
															false);
											$('.forwardtoemployee').removeAttr(
													"checked", false);
											$('.Locationwiseradio').hide();
											$('.WithinDepartment').hide();
											$('#EmployeeName').multiselect(
													'deselect',
													$("#EmployeeName").val());

											$('#btnSave').show();
											$('#btnInspection').hide();
											$("#reasonToForwardDiv").hide();
											$("#reasonToForwardId").val('');

											$(this)
													.find("option:selected")
													.each(
															function() {
																if ($(this)
																		.attr(
																				"value") == "FORWARD_TO_DEPARTMENT") {
																	$(
																			".boxaction")
																			.not(
																					".RelevantDepartment")
																			.hide();
																	$(
																			".RelevantDepartment")
																			.show();
																	$("#reasonToForwardDiv").show();

																} else if ($(
																		this)
																		.attr(
																				"value") == "FORWARD_TO_EMPLOYEE") {
																	$(
																			'#radio_div')
																			.hide();
																	$(
																			".boxaction")
																			.not(
																					".EmployeewithinDepartment")
																			.hide();
																	$(
																			".EmployeewithinDepartment")
																			.show();
																	$("#reasonToForwardDiv").show();

																	var requestData = "deptId="
																			+ $(
																					'#Department')
																					.val()
																			+ "&compTypeId="
																			+ $(
																					"#ComplaintType")
																					.val();
																	var response = __doAjaxRequest(
																			"GrievanceResolution.html?isWardZoneRequired",
																			'post',
																			requestData,
																			false,
																			'html');
																	var isWardZoneRequired = (response == 'true');
																	if (!isWardZoneRequired) {
																		$(
																				'#id_EmployeewithinDepartment_loc')
																				.prop(
																						'disabled',
																						true);
																		$(
																				"#id_EmployeewithinDepartment_emp")
																				.prop(
																						"checked",
																						true)
																				.trigger(
																						"click");
																	}

																}else if($(this).attr("value") == "SEND_FOR_INSPECTION") {
																	$('#btnSave').hide();
																	$('#btnInspection').show();
																} else {
																	$(
																			".boxaction")
																			.hide();
																}
															});
										}).change();
					});
</script>

