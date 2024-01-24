<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/fire_management/callRegister.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/fire_management/callClosureApproval.js"></script>
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
						code="FireCallRegisterDTO.form.closer" text="Call Closure" /></strong>
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

			<form:form action="FireCallClosureApproval.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="frmFireCallRegister" id="frmFireCallClosureApproval">
				<%-- <form:hidden path="saveMode" id="saveMode" /> --%>
				
				<spring:message code="FireCallRegisterDTO.callAttendDate.valid"
					text="Closer Date should be greater than Call Date"
					var="validCallAttendDate" />
				<form:hidden path="" value="${validCallAttendDate}"
					id="validCallAttendDate" />

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
									<spring:message code="CallDetailsDTO.callDetails"
										text="Call Details" />
								</a>
							</h4>
						</div>

									<div class="panel-body">
								<div class="form-group">
									<apptags:date labelCode="FireCallRegisterDTO.date"
										datePath="entity.date" fieldclass="lessthancurrdate"
										isMandatory="true" isDisabled="${command.saveMode eq 'V'}"/>
									<apptags:date labelCode="FireCallRegisterDTO.time"
										datePath="entity.time" fieldclass="timepicker"
										isMandatory="true" isDisabled="${command.saveMode eq 'V'}"/>
								</div>

							<%-- 	<div class="form-group">

									<label class="control-label col-sm-2 required-control"
										for="cpdFireStation"> <spring:message
											code="FireCallRegisterDTO.cpdFireStation" text="Fire Station" /></label>
									<c:set var="baseLookupCode" value="FSN" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="entity.cpdFireStation"
										cssClass="mandColorClass form-control" isMandatory="true"
										hasId="true" selectOptionLabelCode="selectdropdown" disabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}"/>
									<apptags:input labelCode="FireCallRegisterDTO.callerName"
										path="entity.callerName" cssClass="hasNameClass"
										isMandatory="false" maxlegnth="50" isDisabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}" /> --%>
										
										<div class="form-group">
									<apptags:input labelCode="FireCallRegisterDTO.callerName"
										path="entity.callerName" cssClass="hasNameClass"
										isMandatory="false" maxlegnth="50" isDisabled="${command.saveMode eq 'V'}" />
									<apptags:textArea labelCode="FireCallRegisterDTO.callerAdd"
										cssClass="alphaNumeric" maxlegnth="100"
										path="entity.callerAdd" isMandatory="false" isDisabled="${command.saveMode eq 'V'}"/>

								</div>
								<div class="form-group">
									<apptags:input labelCode="FireCallRegisterDTO.callerMobileNo"
										cssClass="hasMobileNo" maxlegnth="10" dataRuleMinlength="10"
										path="entity.callerMobileNo" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"/>
									<apptags:input labelCode="FireCallRegisterDTO.incidentLocation"
										cssClass="alphaNumeric" maxlegnth="100"
										path="entity.incidentLocation" isMandatory="false" isDisabled="${command.saveMode eq 'V'}"/>

								</div>
								<%-- 	<apptags:textArea labelCode="FireCallRegisterDTO.incidentDesc"
										path="entity.incidentDesc" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="true" isDisabled="${command.saveMode eq 'V' || command.saveMode eq 'E'}" />

								</div> --%>


								<div class="form-group">
								
									<apptags:textArea labelCode="FireCallRegisterDTO.incidentDesc"
										path="entity.incidentDesc" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="true" isDisabled="${command.saveMode eq 'V'}" />
									<apptags:textArea
										labelCode="FireCallRegisterDTO.operatorRemarks"
										path="entity.operatorRemarks" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="false" isDisabled="${command.saveMode eq 'V'}" />

								</div>
								
								<div class="form-group">
									<apptags:radio radioLabel="bt.yes,bt.no" radioValue="Y,N"
										labelCode="FireCallRegisterDTO.callerArea"
										path="entity.callerArea" disabled="${command.saveMode eq 'V'}"/>
								
								<c:if test="${taskNameToCheck}">
								<label class="col-sm-2 control-label"><spring:message
											code="workflow.checkAct.AttchDocument" /></label>
									<div class="col-sm-4">
										<apptags:formField fieldType="7" fieldPath="" isDisabled="${command.saveMode eq 'V'}"
											showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
											isMandatory="false" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
											validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
											currentCount="0">
										</apptags:formField>
									</div>
								</c:if>	
								</div>
								
								<c:if test="${command.attachDocsList ne null && not empty command.attachDocsList}">
									<div class="form-group">
										<label class="col-sm-2 control-label"><spring:message
												code="" text="Uploaded Files" /></label>
										<div class="col-sm-12 text-left">
											<div class="table-responsive">
												<table class="table table-bordered table-striped"
													id="attachDocument">
													<tr>
														<th><spring:message code="scheme.document.name"
																text="Document Name" /></th>
														<th><spring:message code="scheme.view.document"
																text="View Documents" /></th>
													</tr>
													<c:forEach items="${command.attachDocsList}" var="lookUp">
														<tr>
															<td>${lookUp.attFname}</td>
															<td><apptags:filedownload
																	filename="${lookUp.attFname}"
																	dmsDocId="${lookUp.dmsDocId}"
																	filePath="${lookUp.attPath}"
																	actionUrl="FireCallClosure.html?Download" /></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</div>
									</div>
								</c:if>
								
								<%-- <div class="form-group">
								<div class="col-sm-4">
									<c:if test="${command.entity.atdFname ne null && command.entity.atdFname ne ''}">
										<div class="form-group">
											<label class="col-sm-2 control-label" for="ExcelFileUpload">
												<spring:message code="intranet.upldFileNm" text="Uploaded File Name" />
											</label>&nbsp
											<apptags:filedownload filename="${command.entity.atdFname}"
												filePath="${command.entity.atdPath}" actionUrl="DeathRegistration.html?Download" >
											</apptags:filedownload>
										</div>
									</c:if>	
								</div>
								</div> --%>
								</div>
							</div>
						
						
						
						
                              <div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-target="#a1" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#collapse1">
										<spring:message code="FireCallRegisterDTO.form.Record"
											text="Call Attend Record" />
									</a>
								</h4>
							</div>
							<br>
												<div class="form-group">
												<label class="control-label col-sm-2 required-control"
													for="fireStationsAttendCall"> <spring:message
														code="FireCallRegisterDTO.cpdFireStation" text="Fire Station" /></label>
												<c:set var="baseLookupCode" value="FSN" />
												<div class="col-sm-4">
													<form:select id="cpdFireStationList" path="entity.cpdFireStationList" cssClass="form-control chosen-select-no-results" data-rule-required="true" multiple="true" disabled="${command.saveMode eq 'V'}">
														<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
															<form:option value="${lookUp.lookUpId}"	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
														</c:forEach>
													</form:select>
												</div> 
												
									
												
								<label class="control-label col-sm-2 required-control" for="nameVisitingOff">
									<spring:message code="FireCallRegisterDTO.form.duty.officer" text="Duty Officer" />
								</label>
								<div class="col-sm-4">
									<form:select path="entity.dutyOfficer" id="dutyOfficer"
										data-rule-required="true"
										cssClass="form-control chosen-select-no-results"
										disabled="${command.saveMode eq 'V'}">
										<form:option value="">
											<spring:message code="Select" text="Select" />
										</form:option>
										<c:forEach items="${secuDeptEmployee}" var="empl">
											<form:option value="${empl.empId}" label="${empl.fullName} - ${empl.designName}"></form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>

                             	<div class="form-group">
								<label class="control-label col-sm-2 "
									for="VehicleType"><spring:message code="FireCallRegisterDTO.form.name.of.operator"
										text="Name of Operator" /></label>
								<c:set var="baseLookupCode" value="OPR" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="entity.operator" cssClass="form-control " disabled="${command.saveMode eq 'V'}"
									selectOptionLabelCode="selectdropdown"
									hasId="true" />

								<label class="control-label col-sm-2" for="nameVisitingOff">
									<spring:message code="FireCallRegisterDTO.form.assign.vehicle" text="Assign vehicle" />
								</label>
								<div class="col-sm-4">
									<form:select path="entity.assignVehicleList" id="nameVisitingOff"
										data-rule-required="true" multiple="true"
										cssClass="form-control chosen-select-no-results" disabled="${command.saveMode eq 'V'}">
										<form:option value="">
											<spring:message code="Select" text="Select" />
										</form:option>
										<c:forEach items="${listVeh}" var="empl">
											<form:option value="${empl.assignVehicle}"
												label="${empl.vehNoDesc}"></form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>

							<div class="form-group">
								<apptags:date fieldclass="timepicker"
									labelCode="FireCallRegisterDTO.vehicleOutTime"
									datePath="entity.vehicleOutTime" isMandatory="false" readonly="${command.saveMode eq 'V'}"
									cssClass="">
								</apptags:date>

								<apptags:date fieldclass="timepicker"
									labelCode="FireCallRegisterDTO.vehicleInTime" readonly="${command.saveMode eq 'V'}"
									datePath="entity.vehicleInTime" cssClass="">
								</apptags:date>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2"
									for="Remarks"><spring:message code="FireCallRegisterDTO.form.other.details"
										text="Other Details" /></label>
								<div class="col-sm-4">
									<form:textarea path="entity.otherDetails" maxLength="100" readonly="${command.saveMode eq 'V'}"
										class="form-control mandColorClass" id="purpose"></form:textarea>
								</div>
                            </div>


					<div class="panel panel-default">
						<div class="panel-heading">

							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="FireCallRegisterDTO.form.vardi"
										text="Vardi Details" />
								</a>
							</h4>
						</div>

						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">

									<label class="control-label col-sm-2" for="cpdCallType">
										<spring:message code="FireCallRegisterDTO.cpdCallType"
											text="Call Type" />
									</label>
									<c:set var="baseLookupCode" value="FCN" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="entity.cpdCallType"
										cssClass="mandColorClass form-control" isMandatory="false"
										hasId="true" selectOptionLabelCode="selectdropdown"
										disabled="${command.saveMode eq 'V'}" />
									<label class="col-sm-2 control-label" for="nameOfOfficer"><spring:message
											code="FireCallRegisterDTO.nameOfOfficer"
											text="FireCallRegisterDTO.nameOfOfficer" /></label>
									<div class="col-sm-4">
										<form:select path="entity.nameOfOfficer"
											cssClass="form-control" id="nameOfOfficer"
											disabled="${command.saveMode eq 'V'}">
											<form:option value="">
												<spring:message code="Select" text="Select" />
											</form:option>
											<c:forEach items="${fireDeptEmployee}" var="emp">
												<form:option value="${emp.empId}">${emp.fullName}(${emp.designName})</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<apptags:input labelCode="FireCallRegisterDTO.nameOfOwner"
										cssClass="alphaNumeric" maxlegnth="100"
										path="entity.nameOfOwner" isMandatory="false"
										isDisabled="${command.saveMode eq 'V'}" />
									<apptags:input labelCode="FireCallRegisterDTO.nameOfOccupier"
										cssClass="alphaNumeric" maxlegnth="100"
										path="entity.nameOfOccupier" isMandatory="false"
										isDisabled="${command.saveMode eq 'V'}" />
								</div>

							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">

							<h4 class="panel-title">
								<a data-target="#a3" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="FireCallRegisterDTO.form.dept.inj"
										text="Department Injury Details" />
								</a>
							</h4>
						</div>
						<div id="a3" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<apptags:input
										labelCode="FireCallRegisterDTO.noOfDeptInjuryMale"
										cssClass="hasNumber" maxlegnth="10"
										path="entity.noOfDeptInjuryMale" isMandatory="false"
										isDisabled="${command.saveMode eq 'V'}" />
									<apptags:input
										labelCode="FireCallRegisterDTO.noOfDeptDeathMale"
										cssClass="hasNumber" maxlegnth="10"
										path="entity.noOfDeptDeathMale" isMandatory="false"
										isDisabled="${command.saveMode eq 'V'}" />
								</div>

								<div class="form-group">
									<apptags:input
										labelCode="FireCallRegisterDTO.noOfDeptInjuryFemale"
										cssClass="hasNumber" maxlegnth="10"
										path="entity.noOfDeptInjuryFemale" isMandatory="false"
										isDisabled="${command.saveMode eq 'V'}" />
									<apptags:input
										labelCode="FireCallRegisterDTO.noOfDeptDeathFemale"
										cssClass="hasNumber" maxlegnth="10"
										path="entity.noOfDeptDeathFemale" isMandatory="false"
										isDisabled="${command.saveMode eq 'V'}" />
								</div>

							</div>
						</div>
					</div>


					<div class="panel panel-default">
						<div class="panel-heading">

							<h4 class="panel-title">
								<a data-target="#a4" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="FireCallRegisterDTO.form.civilian.inj"
										text="Civilian Injury Details" />
								</a>
							</h4>
						</div>
						<div id="a4" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<apptags:input labelCode="FireCallRegisterDTO.noOfInjuryChild"
										cssClass="hasNumber" maxlegnth="10"
										path="entity.noOfInjuryChild" isMandatory="false"
										isDisabled="${command.saveMode eq 'V'}" />
									<apptags:input labelCode="FireCallRegisterDTO.noOfDeathChild"
										cssClass="hasNumber" maxlegnth="10"
										path="entity.noOfDeathChild" isMandatory="false"
										isDisabled="${command.saveMode eq 'V'}" />
								</div>

								<div class="form-group">
									<apptags:input labelCode="FireCallRegisterDTO.noOfInjuryMale"
										cssClass="hasNumber" maxlegnth="10"
										path="entity.noOfInjuryMale" isMandatory="false"
										isDisabled="${command.saveMode eq 'V'}" />
									<apptags:input labelCode="FireCallRegisterDTO.noOfDeathMale"
										cssClass="hasNumber" maxlegnth="10"
										path="entity.noOfDeathMale" isMandatory="false"
										isDisabled="${command.saveMode eq 'V'}" />
								</div>
								<div class="form-group">
									<apptags:input labelCode="FireCallRegisterDTO.noOfInjuryFemale"
										cssClass="hasNumber" maxlegnth="10"
										path="entity.noOfInjuryFemale" isMandatory="false"
										isDisabled="${command.saveMode eq 'V'}" />
									<apptags:input labelCode="FireCallRegisterDTO.noOfDeathFemale"
										cssClass="hasNumber" maxlegnth="10"
										path="entity.noOfDeathFemale" isMandatory="false"
										isDisabled="${command.saveMode eq 'V'}" />
								</div>
							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">

							<h4 class="panel-title">
								<a data-target="#a5" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="FireCallRegisterDTO.form.call.attend"
										text="Call Attendance Record" />
								</a>
							</h4>
						</div>
						<div id="a5" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="control-label col-sm-2" for="cpdCallType">
										<spring:message code="FireCallRegisterDTO.cpdNatureOfCall"
											text="Nature of Call" />
									</label>
									<c:set var="baseLookupCode" value="FCN" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="entity.cpdNatureOfCall"
										cssClass="mandColorClass form-control" isMandatory="false"
										hasId="true" selectOptionLabelCode="selectdropdown"
										disabled="${command.saveMode eq 'V'}" />
									<label class="control-label col-sm-2" for="cpdCallType">
										<spring:message code="FireCallRegisterDTO.cpdReasonOfFire"
											text="Reason of Fire" />
									</label>
									<c:set var="baseLookupCode" value="ROF" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="entity.cpdReasonOfFire"
										cssClass="mandColorClass form-control" isMandatory="false"
										hasId="true" selectOptionLabelCode="selectdropdown"
										disabled="${command.saveMode eq 'V'}" />
								</div>

								<div class="form-group">
									<apptags:date labelCode="FireCallRegisterDTO.callAttendDate"
										datePath="entity.callAttendDate" fieldclass="lessthancurrdate"
										isMandatory="true" isDisabled="${command.saveMode eq 'V'}" />
									<apptags:date labelCode="FireCallRegisterDTO.callAttendTime"
										datePath="entity.callAttendTime" fieldclass="timepicker"
										isMandatory="true" isDisabled="${command.saveMode eq 'V'}" />
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="fireStationsAttendCall"> <spring:message
											code="FireCallRegisterDTO.fireStationsAttendCall"
											text="Fire Station Attend Call" /></label>
									<c:set var="baseLookupCode" value="FSN" />
									<div class="col-sm-4">
										<form:select id="fireStationsAttendCallList"
											path="entity.fireStationsAttendCallList"
											cssClass="form-control chosen-select-no-results"
											data-rule-required="true" multiple="true"
											disabled="${command.saveMode eq 'V'}">
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<apptags:input labelCode="FireCallRegisterDTO.reasonForDelay"
										cssClass="alphaNumeric" maxlegnth="100"
										path="entity.reasonForDelay" isMandatory="false"
										isDisabled="${command.saveMode eq 'V'}" />
								</div>

							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">

							<h4 class="panel-title">
								<a data-target="#a6" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="FireCallRegisterDTO.form.release.detail"
										text="Release Person Details From Incident" />
								</a>
							</h4>
						</div>
						<div id="a6" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label for="col-sm-2" class="col-sm-2 control-label ">
										<spring:message
											code="FireCallRegisterDTO.rescuedWithoutFireDeptMale"
											text="Rescued Without Fire Dept" />
									</label>
									<div class="col-sm-2">
										<form:input path="entity.rescuedWithoutFireDeptMale"
											cssClass="hasNumber form-control" maxlegnth="10"
											placeholder="Male" data-rule-required="false"
											disabled="${command.saveMode eq 'V'}" />
									</div>
									<div class="col-sm-2">
										<form:input path="entity.rescuedWithoutFireDeptFemale"
											cssClass="hasNumber form-control" maxlegnth="10"
											placeholder="Female" data-rule-required="false"
											disabled="${command.saveMode eq 'V'}" />
									</div>

									<label for="col-sm-2" class="col-sm-2 control-label ">
										<spring:message
											code="FireCallRegisterDTO.rescuedWithFireDeptMale"
											text="Rescued With Fire Dept" />
									</label>
									<div class="col-sm-2">
										<form:input path="entity.rescuedWithFireDeptMale"
											cssClass="hasNumber form-control" maxlegnth="10"
											placeholder="Male" data-rule-required="false"
											disabled="${command.saveMode eq 'V'}" />
									</div>
									<div class="col-sm-2">
										<form:input path="entity.rescuedWithFireDeptFemale"
											cssClass="hasNumber form-control" maxlegnth="10"
											placeholder="Female" data-rule-required="false"
											disabled="${command.saveMode eq 'V'}" />
									</div>
								</div>
								
								<div class="form-group">
									<label for="plotArea"
											class="col-sm-2 control-label"><spring:message
												 text="Property Saved (Rs. In Lakhs)" /></label>
										<div class="col-sm-4">
											<form:input id="propertySaved"
												path="entity.propertySaved"
												cssClass="form-control text-left"
												disabled="${command.saveMode eq 'V'}"
												onkeypress="return hasAmount(event, this, 5, 2)" />
										</div>
										
										<label for="plotArea"
											class="col-sm-2 control-label"><spring:message
												text="Property Lost (Rs. In Lakhs)" /></label>
										<div class="col-sm-4">
											<form:input id="propertyLost"
												path="entity.propertyLost"
												cssClass="form-control text-left"
												disabled="${command.saveMode eq 'V'}"
												onkeypress="return hasAmount(event, this, 5, 2)" />
										</div>
									</div>

								<div class="form-group">
									<%-- <label for="col-sm-2" class="col-sm-2 control-label ">
										<spring:message
											code="FireCallRegisterDTO.rescuedWithFireDeptVeheicleMale"
											text="Rescued With FireDept Veheicle" />
									</label>
									<div class="col-sm-2">
										<form:input path="entity.rescuedWithFireDeptVeheicleMale"
											cssClass="hasNumber form-control" maxlegnth="10"
											placeholder="Male" data-rule-required="false"
											disabled="${command.saveMode eq 'V'}" />
									</div>
									<div class="col-sm-2">
										<form:input path="entity.rescuedWithFireDeptVeheicleFemale"
											cssClass="hasNumber form-control" maxlegnth="10"
											placeholder="Female" data-rule-required="false"
											disabled="${command.saveMode eq 'V'}" />
									</div> --%>



                                 	<label class="control-label col-sm-2"
													for="callAttendEmployeeList"> <spring:message
														code="FireCallRegisterDTO.callAttendEmployee" text="Employee who Attend Call" /></label>
												<div class="col-sm-4">
													<form:select id="callAttendEmployeeList" path="entity.callAttendEmployeeList" cssClass="form-control chosen-select-no-results" data-rule-required="true" multiple="true" disabled="${command.saveMode eq 'V'}">
														
														<c:forEach items="${fireDeptEmployee}" var="empl">
																<form:option value="${empl.empId}">${empl.fullName}(${empl.designName})</form:option>
														</c:forEach>
													</form:select>
									</div> 

								</div>
								<div class="form-group">
									<apptags:textArea labelCode="FireCallRegisterDTO.closerRemarks"
										path="entity.closerRemarks" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="true"
										isDisabled="${command.saveMode eq 'V'}" />
								</div>
								
								 <c:if test="${command.attachDocs ne null && not empty command.attachDocs}">
									<div class="form-group">
										<label class="col-sm-2 control-label"><spring:message
												code="" text="Uploaded Files" /></label>
										<div class="col-sm-12 text-left">
											<div class="table-responsive">
												<table class="table table-bordered table-striped"
													id="attachDocument">
													<tr>
														<th><spring:message code="scheme.document.name"
																text="Document Name" /></th>
														<th><spring:message code="scheme.view.document"
																text="View Documents" /></th>
													</tr>
													<c:forEach items="${command.attachDocs}" var="lookUp">
														<tr>
															<td>${lookUp.attFname}</td>
															<td><apptags:filedownload
																	filename="${lookUp.attFname}"
																	dmsDocId="${lookUp.dmsDocId}"
																	filePath="${lookUp.attPath}"
																	actionUrl="FireCallClosure.html?Download" /></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</div>
									</div>
								</c:if>

								<div class="widget-header">
									<h2>
										<strong><spring:message code="FireCallRegisterDTO.form.user.action" text="User Action" /></strong>
									</h2>
								</div>

								<br>

							<%-- 	<div class="col-sm-12 text-left">
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="attachDocs">
											<tr>
												<th><spring:message code="scheme.document.name" text="" /></th>
												<th><spring:message code="scheme.view.document" text="" /></th>
											</tr>
											<c:forEach items="${command.fetchDocumentList}" var="lookUp">
												<tr>
													<td align="center">${lookUp.attFname}</td>
													<td align="center"><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="DeathRegistration.html?Download">
														</apptags:filedownload></td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</div> --%>
								

								<div class="form-group">
								
									<apptags:radio radioLabel="FireCallRegisterDTO.form.approve,FireCallRegisterDTO.form.send.back"
											radioValue="APPROVED,REJECTED" isMandatory="true"
											labelCode="FireCallRegisterDTO.form.user.status" path="entity.callRegClosureStatus"
											defaultCheckedValue="APPROVED">
									</apptags:radio>
									
								
									
									<apptags:textArea labelCode="FireCallRegisterDTO.form.remark" isMandatory="true"
										path="entity.callRegClosureRemark"
										cssClass="hasNumClass form-control" maxlegnth="100" />
								</div>
								 
							</div>
						</div>
					</div>
				</div>
                </div>
				<!-- Start button -->
				<div class="text-center">
					<button type="button" class="btn btn-green-3" title='<spring:message code="bt.save" text="Submit" />'
						onclick="saveCallClosureApprovalData(this)">
						<spring:message code="bt.save" text="Submit" />
					</button>

					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
				<!-- End button -->


			</form:form>
			<!-- End Form -->
		</div>
	</div>
</div>

<!-- End Widget Content here -->
<!-- End of Content -->

<script type="text/javascript">

function saveCallClosureApprovalData(element) {
	var errorList = [];	
	//$.fancybox.close();
	if($("#callRegClosureRemark").val()==""){
		errorList.push(getLocalMessage("FireCallRegisterDTO.form.validation.remark"));
		 displayErrorsOnPage(errorList);
	}
	else{
	 var divName = '.content';
	 var formName = findClosestElementId(element, 'form');
	 var theForm = '#' + formName; 
	 var requestData = __serializeForm(theForm);
	 var object = __doAjaxRequest($(theForm).attr('action')+ '?saveFireCallClosureApproval', 'POST',requestData, false,'json');		
	 if(object.error != null && object.error != 0  ){	
		 $.each(object.error, function(key, value){
			    $.each(value, function(key, value){
			    	if(value != null && value != ''){				    		
			    		errorList.push(value);
			    	}				        
			    });
			});
		 displayErrorsOnPage(errorList);
	 }else{
		if(object.FireWfStatus =='REJECTED'){
			 showBoxForApproval(getLocalMessage('FireCallRegisterDTO.para.rejection'));
		 }else if(object.FireWfStatus =='SEND_BACK'){
			 showBoxForApproval(getLocalMessage('audit.para.sendBack'));
		 }else if(object.FireWfStatus =='FORWARD_TO'){
			 showBoxForApproval(getLocalMessage('audit.para.sendBack'));
		 }else{
			 showBoxForApproval(getLocalMessage("FireCallRegisterDTO.form.success.msg"));
		 }
	 } 
   }
}


function showBoxForApproval(succesMessage){
  	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed =  getLocalMessage("FireCallRegister.form.proceed");
	var no = 'No';	
	message += '<p class="text-blue-2 text-center padding-15">'
		    + succesMessage +'</p>';
	
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
			+ ' onclick="closeApproval();"/>' + '</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
}

function closeApproval() {
    window.location.href = 'AdminHome.html';
    $.fancybox.close();
}

</script>




