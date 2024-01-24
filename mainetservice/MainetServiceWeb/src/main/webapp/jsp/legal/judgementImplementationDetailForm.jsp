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
<script src="assets/libs/fullcalendar/moment.min.js"></script> 
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/legal/judgementImplementationDetail.js"></script>
<script src="js/mainet/validation.js"></script>

<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">

		<div class="widget-header">
			<h2>
				<spring:message code="" text="Judgement Implementation Detail Form" />
			</h2>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">

				<span><spring:message code="legal.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="legal.mand.field"
						text="is mandatory"></spring:message></span>
			</div>
			<form:form action="JudgementImplementationDetail.html"
				name="JudgementImplementation" id="JudgementImplementation"
				class="form-horizontal" commandName="command">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="caseEntryDTO.cseId" id="cseId" />
				<form:hidden path="removeAttendeeId" id="removeAttendeeId" />
				<form:hidden path="caseEntryDTO.judgementMasterDate" id="judgementMasterDate" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="form-group">

					<apptags:input labelCode="caseEntryDTO.cseName"
						path="caseEntryDTO.cseName" isMandatory="true"
						cssClass="alphaNumeric form-control" maxlegnth="250"
						isDisabled="true"></apptags:input>
						
						<label class="control-label col-sm-2 required-control"> <spring:message
							code="caseEntryDTO.cseDeptid" />
					</label>
					<div class="col-sm-4">
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="caseEntryDTO.cseDeptid" id="cseDeptid" disabled="true">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.departmentList}" var="departmentList">
								<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${departmentList.dpDeptid}">${departmentList.dpNameMar}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${departmentList.dpDeptid}">${departmentList.dpDeptdesc}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>

				</div>

				<div class="form-group">

					<apptags:input labelCode="caseEntryDTO.cseSuitNo"
						path="caseEntryDTO.cseSuitNo" isMandatory="true"
						cssClass="alphaNumeric form-control " maxlegnth="20"
						isDisabled="true"></apptags:input>

					<apptags:input labelCode="caseEntryDTO.cseRefsuitNo"
						path="caseEntryDTO.cseRefsuitNo" 
						cssClass="alphaNumeric form-control" maxlegnth="20"
						isDisabled="true"></apptags:input>

				</div>

				<div class="form-group">
					<apptags:lookupFieldSet baseLookupCode="CCT" hasId="true"
						showOnlyLabel="false" pathPrefix="caseEntryDTO.cseCatId"
						isMandatory="true" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control margin-bottom-10"
						showAll="false" columnWidth="20%" disabled="true" />
				</div>

				<div class="form-group">

					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="caseEntryDTO.cseTypId" text="Case Type" /></label>
					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="TOC" />
						<form:select path="caseEntryDTO.cseTypId" class="form-control"
							id="cseTypId" onchange="displayJudgeFeesTable(this)"
							disabled="true">
							<form:option value="0">Select</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="caseEntryDTO.csePeicDroa" text="Organisation As" /></label>
					<c:set var="baseLookupCode" value="OZA" />
					<!-- OZA -->
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="caseEntryDTO.csePeicDroa"
						cssClass="mandColorClass form-control" isMandatory="true"
						hasId="true" selectOptionLabelCode="selectdropdown"
						disabled="true" />
				</div>

				<div class="form-group">

					<%-- <apptags:date labelCode="caseEntryDTO.cseDate"
						datePath="caseEntryDTO.cseDate" isMandatory="true"
						cssClass="form-control" fieldclass="datepicker" isDisabled="true"></apptags:date> --%>
					<label for="text-1" class="control-label col-sm-2 required-control">Start Date</label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input class="form-control datepicker" id="cseDate"
								path="caseEntryDTO.cseDate"
							 	 maxlength="10"
								disabled="${command.saveMode eq 'E' ? true : false }"
								onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"/>
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>

						</div>
					</div>

					<%-- <apptags:date labelCode="caseEntryDTO.cseEntryDt"
						datePath="caseEntryDTO.cseEntryDt" isMandatory="true"
						cssClass="form-control" fieldclass="datepicker" isDisabled="true"></apptags:date> --%>
						
				<label for="text-1" class="control-label col-sm-2 required-control">End Date</label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input class="form-control datepicker" id="cseEntryDt"
								path="caseEntryDTO.cseEntryDt"
							 	 maxlength="10"
								disabled="${command.saveMode eq 'E' ? true : false }"  
								onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"/>
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>

						</div>
					</div>		

				</div>

				<div class="form-group">

					<label class="control-label col-sm-2 required-control"> <spring:message
							code="caseEntryDTO.crtId" text="Court Name" />
					</label>
					<div class="col-sm-4">
						<!-- chosen-select-no-results -->
						<form:select class=" mandColorClass form-control"
							path="caseEntryDTO.crtId" id="crtId" disabled="true">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.courtNameList}" var="courtNameList">
								<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${courtNameList.id}">${courtNameList.crtNameReg}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${courtNameList.id}">${courtNameList.crtName}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>
					
					<apptags:input labelCode="caseEntryDTO.cseMatdet1"
						path="caseEntryDTO.cseMatdet1" isMandatory="true"
						cssClass="hasSpecialCharAndNumber form-control" maxlegnth="1000"
						isDisabled="true" />
					

					<%-- <label class="control-label col-sm-2 required-control"> <spring:message
							code="caseEntryDTO.locId" />
					</label>
					<div class="col-sm-4">
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="caseEntryDTO.locId" id="locId" disabled="true">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.locationList}" var="location">
								<form:option value="${location.locId}">${location.locName}</form:option>
							</c:forEach>
						</form:select>
					</div>
 --%>
				</div>
				
					<div class="form-group">

					<label class="control-label col-sm-2 required-control" for=""><spring:message
							code="caseEntryDTO.cseState" text="State" /></label>
					<c:set var="baseLookupCode" value="STT" />
					<!-- STT -->
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="caseEntryDTO.cseState"
						cssClass="mandColorClass form-control" hasChildLookup="false"
						hasId="true" showAll="false"
						selectOptionLabelCode="selectdropdown" showOnlyLabel="State"
						disabled="${command.saveMode eq 'V' ? true : false or command.saveMode eq 'E'? true : false}" />

					<apptags:input labelCode="caseEntryDTO.cseCity"
						path="caseEntryDTO.cseCity" isMandatory="true"
						cssClass="form-control" maxlegnth="250"
						isDisabled="${command.saveMode eq 'V' ? true : false or command.saveMode eq 'E'? true : false}" />

				</div>

			

				<div class="form-group">

					<%-- <apptags:input labelCode="caseEntryDTO.cseSectAppl"
						path="caseEntryDTO.cseSectAppl" isMandatory="true"
						cssClass="hasSpecialCharAndNumber form-control" maxlegnth="1000"
						isDisabled="true" /> --%>

					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="caseEntryDTO.cseCaseStatusId" text="Case Status" /></label>
					<c:set var="baseLookupCode" value="CSS" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="caseEntryDTO.cseCaseStatusId"
						cssClass="mandColorClass form-control" isMandatory="true"
						hasId="true" selectOptionLabelCode="selectdropdown"
						disabled="true" />
						
							
					<apptags:input labelCode="caseEntryDTO.cseRemarks"
						path="caseEntryDTO.cseRemarks" isMandatory="true"
						cssClass="hasSpecialCharAndNumber form-control" maxlegnth="1000"
						isDisabled="true" />
						

				</div>

				<div class="form-group">

					<label class="control-label col-sm-2 required-control"> <spring:message
							code="caseEntryDTO.advId" />
					</label>
					<div class="col-sm-4">
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="caseEntryDTO.advId" id="advId" disabled="true">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.advocateList}" var="advocateList">
								<form:option value="${advocateList.advId}">${advocateList.advFirstNm} ${advocateList.advMiddleNm} ${advocateList.advLastNm}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<%-- <label class="control-label col-sm-2 required-control"> <spring:message
							code="caseEntryDTO.officeInCharge" />
					</label>
					<div class="col-sm-4">
						<form:select class="form-control chosen-select-no-results"
							path="caseEntryDTO.officeIncharge" id="officeIncharge"
							disabled="true" onchange="setValues(this)">
							<form:option value="0">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.employeeList}" var="emp">
								<form:option value="${emp.empId}" mobno="${emp.empmobno}"
									email="${emp.empemail}" dept="${emp.deptName}">${emp.fullName}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>
				</div>

				<%-- <div class="form-group">
					<apptags:input labelCode="Mobile No." path="caseEntryDTO.oicMobile"
						isReadonly="true" />
					<apptags:input labelCode="Email ID" path="caseEntryDTO.oicEmail"
						isReadonly="true" />
				</div>

				<div class="form-group">

					<apptags:date labelCode="caseEntryDTO.appointmentDate"
						datePath="caseEntryDTO.appointmentDate" isMandatory="true"
						cssClass="form-control" fieldclass="datepicker" isDisabled="true"></apptags:date>

					<apptags:input labelCode="Department"
						path="caseEntryDTO.oicDepartment" isReadonly="true" />

				</div> --%>
	<!-- OFFICER IN CHARGE STARTS -->

			<div class="panel-group accordion-toggle" id="oicId">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a5" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a5"><spring:message
										code="lgl.officerIncharge.details"
										text="Officer In-Charge Details" /></a>
							</h4>
						</div>
						<div id="a5" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="table-responsive">
									<table id="officerInchargeDetails"
										summary="Officer Incharge Data"
										class="table table-bordered table-striped">
										<c:set var="h" value="0" scope="page"></c:set>
										<thead>
											<tr>
												<th width="50"><spring:message code="lgl.srno"
														text="Sr. No." /></th>
												<th><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicName"
														text="Officer In-charge Name" /><i class="text-red-1">*</i></th>

												<th><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicDesg"
														text="Officer In-charge Designation" /><i
													class="text-red-1">*</i></th>

												<th><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicDept"
														text="Officer In-charge Department" /><i
													class="text-red-1">*</i></th>

												<th><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicAddress"
														text="Officer In-charge Address" /><i class="text-red-1">*</i></th>

												<th><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicPhoneNo"
														text="Officer In-charge Mobile NO" /><i
													class="text-red-1">*</i></th>

												<th><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicEmailId"
														text="Officer In-charge Email Address" /><i
													class="text-red-1">*</i></th>


												<th><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicAppointmentDate"
														text="Officer In-charge Appointment Date" /><i
													class="text-red-1">*</i></th>



												<th><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicOrderNo"
														text="Officer In-charge Order number" /><i
													class="text-red-1">*</i></th>


												<c:if test="${command.saveMode ne 'V'}">
													<th width="50"><a href="#" data-toggle="tooltip"
														data-placement="top" class="btn btn-blue-2  btn-sm"
														data-original-title="Add" onclick="addEntryData3();"><strong
															class="fa fa-plus"></strong><span class="hide"></span></a></th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when
													test="${command.saveMode eq 'V' or (command.saveMode eq 'E' && not empty command.officerInchargeDetailDTOList)}">
													<c:set var="j" value="0" scope="page"></c:set>
													<c:forEach items="${command.officerInchargeDetailDTOList}"
														var="data" varStatus="index">

														<tr class="appendableClassO">
															<!-- officerInchargeDetailDTOList -->

															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass"
																	id="sequence${j}" value="${j+1}" disabled="true" /></td>

															<td><form:input
																	path="officerInchargeDetailDTOList[${j}].oicName"
																	cssClass="form-control mandColorClass required-control hasCharacter"
																	id="officerInchargeName${j}" maxlength="500"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><form:input
																	path="officerInchargeDetailDTOList[${j}].oicDesg"
																	cssClass="form-control mandColorClass required-control"
																	id="officerInchargeDesignation${j}" maxlength="200"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><form:input
																	path="officerInchargeDetailDTOList[${j}].oicDept"
																	cssClass="form-control mandColorClass required-control"
																	id="officerInchargeDepartment${j}" maxlength="250"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><form:input
																	path="officerInchargeDetailDTOList[${j}].oicAddress"
																	cssClass="form-control mandColorClass required-control"
																	id="officerInchargeAddress${j}" maxlength="200"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>


															<td><form:input
																	path="officerInchargeDetailDTOList[${j}].oicPhoneNo"
																	cssClass="hasMobileNo required-control form-control"
																	id="officerInchargePhoneNo${j}" maxlength="10"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><form:input
																	path="officerInchargeDetailDTOList[${j}].oicEmailId"
																	cssClass="hasemailclass required-control form-control"
																	id="officerInchargeEmailId${j}" maxlength="100"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><div class="input-group">
																	<form:input
																		path="officerInchargeDetailDTOList[${j}].oicAppointmentDate"
																		id="officerInchargeAppointmentDate${j}"
																		class="form-control mandColorClass datepicker dateValidation"
																		value="" readonly="false" data-rule-required="true"
																		maxLength="10"
																		disabled="${command.saveMode eq 'V' ? true : false }"
																		 />
																	<label class="input-group-addon"
																		for="oicAppointmentDate"><i
																		class="fa fa-calendar"></i><span class="hide">
																			<spring:message code="" text="icon" />
																	</span><input type="hidden" id=officerInchargeAppointmentDate></label>
																</div></td>

															<td><form:input
																	path="officerInchargeDetailDTOList[${j}].oicOrderNo"
																	cssClass="form-control mandColorClass required-control"
																	id="officerInchargeOrderNo${j}" maxlength="200"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<c:if test="${command.saveMode ne 'V'}">
																<td><a href="#" data-toggle="tooltip"
																	data-placement="top" class="btn btn-danger btn-sm"
																	data-original-title="Delete"
																	onclick="deleteEntry3($(this),'removedIds');"> <strong
																		class="fa fa-trash"></strong> <span class="hide"><spring:message
																				code="lgl.delete" text="Delete" /></span>
																</a></td>
															</c:if>
														</tr>
														<c:set var="j" value="${j+1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="appendableClassO">
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sequence${h}"
																value="${h+1}" disabled="true" /></td>

														<td><form:input
																path="officerInchargeDetailDTOList[${h}].oicName"
																cssClass="form-control mandColorClass required-control hasCharacter"
																id="officerInchargeName${h}" maxlength="500"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>


														<td><form:input
																path="officerInchargeDetailDTOList[${h}].oicDesg"
																cssClass="form-control mandColorClass required-control"
																id="officerInchargeDesignation${h}" maxlength="200"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td><form:input
																path="officerInchargeDetailDTOList[${h}].oicDept"
																cssClass="form-control mandColorClass required-control"
																id="officerInchargeDepartment${h}" maxlength="250"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td><form:input
																path="officerInchargeDetailDTOList[${h}].oicAddress"
																cssClass="form-control mandColorClass required-control"
																id="officerInchargeAddress${h}" maxlength="200"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>


														<td><form:input
																path="officerInchargeDetailDTOList[${h}].oicPhoneNo"
																cssClass="hasMobileNo required-control form-control"
																id="officerInchargePhoneNo${h}" maxlength="10"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td><form:input
																path="officerInchargeDetailDTOList[${h}].oicEmailId"
																cssClass="hasemailclass required-control form-control"
																id="officerInchargeEmailId${h}" maxlength="100"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td><div class="input-group">
																<form:input
																	path="officerInchargeDetailDTOList[${h}].oicAppointmentDate"
																	id="officerInchargeAppointmentDate${h}"
																	class="form-control mandColorClass datepicker dateValidation"
																	value="" readonly="false" data-rule-required="true"
																	maxLength="10" />
																<label class="input-group-addon"
																	for="oicAppointmentDate"><i
																	class="fa fa-calendar"></i><span class="hide"> <spring:message
																			code="" text="icon" />
																</span><input type="hidden" id=officerInchargeAppointmentDate></label>
															</div></td>

														<td><form:input
																path="officerInchargeDetailDTOList[${h}].oicOrderNo"
																cssClass="form-control mandColorClass required-control"
																id="officerInchargeOrderNo${h}" maxlength="100"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>


														<c:if test="${command.saveMode ne 'V'}">
															<td><a href="#" data-toggle="tooltip"
																data-placement="top" class="btn btn-danger btn-sm"
																data-original-title="Delete"
																onclick="deleteEntry3($(this),'removedIds');"> <strong
																	class="fa fa-trash"></strong> <span class="hide"><spring:message
																			code="lgl.delete" text="Delete" /></span>
															</a></td>
														</c:if>
													</tr>
												</c:otherwise>
											</c:choose>
											<c:set var="h" value="${h+1}" scope="page" />
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>

				
				
				
				<!-- ENDS -->		

				<c:if test="${not empty  command.arbitoryFeeList}">
					<div class="panel-group accordion-toggle" id="judgefeeTable">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-target="#a1" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#a1"> <spring:message
											code="" text="Judge Fees" />
									</a>
								</h4>
							</div>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">


									<div class="table-responsive">

										<table id="judgeFeeDetails" summary="Defender Data"
											class="table table-bordered table-striped">
											<c:set var="d" value="0" scope="page" />
											<thead>
												<tr>
													<th width="100"><spring:message code="area.details.id"></spring:message></th>
													<th><spring:message
															code="lgl.arbitration.select.judge.name"
															text="Select Judge Name"></spring:message><i
														class="text-red-1">*</i></th>
													<th><spring:message
															code="lgl.arbitration.select.feetype" text="Fee Type"></spring:message><i
														class="text-red-1">*</i></th>
													<th><spring:message code="lgl.arbitration.amount"
															text="Amount"></spring:message></th>

												</tr>
											</thead>
											<tfoot>

											</tfoot>
											<tbody>

												<c:set var="d" value="0" scope="page" />

												<c:forEach items="${command.arbitoryFeeList}" var="data"
													varStatus="index">
													<tr class="judgeDetailRows">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sequence${d}"
																value="${d+1}" disabled="true" /></td>
														<td><form:select
																path="caseEntryDTO.tbLglArbitoryFee[${d}].judgeName"
																id="judgeName${d}" class="form-control"
																data-rule-required="true" disabled="true">
																<form:option value="0">Select</form:option>

																<c:forEach items="${command.judgeNameList}" var="judge">
																	<form:option value="${judge.id}">${judge.fulName}</form:option>
																</c:forEach>
															</form:select></td>

														<td><c:set var="baseLookupCode" value="FET" /> <form:select
																path="caseEntryDTO.tbLglArbitoryFee[${d}].arbitoryfeeType"
																class="form-control" id="feeType${d}" disabled="true">
																<form:option value="0">Select</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="caseEntryDTO.tbLglArbitoryFee[${d}].arbitoryAmount"
																type="text" id="amount${d}"
																class="form-control text-right hasNumber"
																disabled="true" /></td>


													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>


											</tbody>

										</table>
									</div>

								</div>
							</div>
						</div>
					</div>

				</c:if>
				<div class="panel-group accordion-toggle" id="caseHearingTable">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="" text="Hearing Details" />
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive">
									<table id="hearingdetailsTable"
										class="table table-bordered table-striped">
										<thead>

											<tr>
												<th><spring:message code="label.checklist.srno"
														text="Sr.No." /></th>
												<th><spring:message code="CaseHearingDTO.hrDate"
														text="Hearing Date" /></th>
												<th><spring:message code="CaseHearingDTO.hrStatus"
														text="Status" /></th>
												<th><spring:message code="CaseHearingDTO.hrPreparation"
														text="Preparation" /></th>
												<th><spring:message code="lgl.document.uploadDocuments"
														text="Upload Documents" /></th>
												<th>action</th>

											</tr>
										</thead>
										<tfoot>

										</tfoot>
										<tbody>
											<tr>
												<c:set value="0" var="e"></c:set>
												<td align="center"><form:input path=""
														cssClass="form-control mandColorClass" id="sequence${e}"
														value="${e+1}" disabled="true" /></td>
												<td><form:input path="currentCaseHearingList.hrDate"
														id="hrDate" cssClass="form-control mandColorClass"
														disabled="true" /></td>
												<td class="text-center"><c:set var="baseLookupCode"
														value="HSC" /> <form:select
														path="currentCaseHearingList.hrStatus"
														cssClass="form-control mandColorClass" id="hrStatus"
														data-rule-required="true" disabled="true">
														<form:option value="0">
															<spring:message code="selectdropdown" text="select" />
														</form:option>
														<c:forEach
															items="${command.getSortedLevelData(baseLookupCode)}"
															var="lookUp">
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
														</c:forEach>
													</form:select></td>
												<td class="text-center"><form:input
														path="currentCaseHearingList.hrPreparation"
														class="form-control" id="hrPreparation" disabled="true" /></td>
												<td class="text-center"><c:set var="count"
														value="${index.index}" /> <c:set var="lookUp"
														value="${command.attachDocsList[count]}" /> <c:if
														test="${lookUp ne null }">
														<apptags:filedownload filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="JudgementImplementationDetail.html?Download" />
													</c:if></td>
												<td><button type="button" class="btn btn-blue-2 btn-sm"
														title="View Hearing History"
														onclick="fetchcaseHearingList()">
														<i class="fa fa-eye"></i>
													</button></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>





				<div class="panel-group accordion-toggle" id="judgeImplementation">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a3" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="" text="Judgement Implementation Details" />
								</a>
							</h4>
						</div>
						<div id="a3" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">

									<apptags:input
										labelCode="lgl.judge.implementation.name.implementer"
										path="judgeDetailDto.implementerName" isMandatory="true"
										maxlegnth="50"
										cssClass=" form-control  hasCharacter preventSpace"
											onChange = "removeSpace(this, this.value)"
										isDisabled="${command.saveMode eq 'V' ? true : false || command.orgFlag eq 'Y' }"></apptags:input>
									<apptags:input
										labelCode="lgl.judge.implementation.designator.implementer"
										cssClass=" form-control preventSpace "
										onChange = "removeSpace(this, this.value)"
										path="judgeDetailDto.desigOfImplementer" isMandatory="true" maxlegnth="50"
										isDisabled="${command.saveMode eq 'V' ? true : false || command.orgFlag eq 'Y' }"></apptags:input>

								</div>
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="lgl.judge.implementation.ph.no"
											path="judgeDetailDto.implementerPhoneNo" maxlegnth="12"
											cssClass="hasNumber required-control form-control"
											isMandatory="true"
											isDisabled="${command.saveMode eq 'V' ? true : false || command.orgFlag eq 'Y'}"></apptags:input>
										<apptags:input labelCode="lgl.judge.implementation.email"
											path="judgeDetailDto.implementerEmail" isMandatory="true"
											cssClass="hasemailclass required-control form-control"
											isDisabled="${command.saveMode eq 'V' ? true : false || command.orgFlag eq 'Y'}"></apptags:input>
									</div>
								</div>


							</div>
						</div>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<c:if test="${command.saveMode ne 'V'}">
					<c:if test="${command.orgFlag ne 'Y'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="Proceed(this)">
							<spring:message code="legal.btn.submit" text="Proceed"></spring:message>
						</button>
					</c:if>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<button type="Reset" class="btn btn-warning"
						 onclick="window.location.href='JudgementImplementationDetail.html'">
							<spring:message code="lgl.reset" text="Reset"></spring:message>
						</button>
					</c:if>

					<apptags:backButton url="JudgementImplementationDetail.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
