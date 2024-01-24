<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/legal/caseEntry.js"></script>
<script src="js/mainet/file-upload.js"></script>

<!-- End JSP Necessary Tags -->
<%
	response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">

		<div class="widget-header">
			<h2>
				<spring:message code="lgl.caseEntry.form.heading"
					text="Case Entry Form Heading" />
			</h2>
			<apptags:helpDoc url="CaseEntry.html" />
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">

				<span><spring:message code="legal.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="legal.mand.field"
						text="is mandatory"></spring:message></span>
			</div>
			<form:form action="CaseEntry.html" name="CaseEntry"
				id="CaseEntryForm" class="form-horizontal" commandName="command">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
					
 					<form:hidden path="saveMode" id="saveMode" />
 					<form:hidden path="caseEntryDTO.cseId" id="cseId" />
 					<form:hidden path="envFlag" id="envFlag"/>

				<div class="form-group">
                  <c:if test="${command.envFlag eq 'Y' }">
                  <apptags:input pageId="CaseEntry" labelCode="caseEntryDTO.cseName"
						path="caseEntryDTO.cseName" isMandatory="true"
						cssClass="form-control" maxlegnth="250"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input></c:if>
					
                    <c:if test="${command.envFlag ne 'Y' }">
					<apptags:input pageId="CaseEntry" labelCode="caseEntryDTO.cseName"
						path="caseEntryDTO.cseName" isMandatory="true"
						cssClass="form-control hasNameClass" maxlegnth="250"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input></c:if>

					<c:if test="${ not empty command.caseEntryDTO.caseNo }">
						<apptags:input pageId="CaseEntry" labelCode="caseEntryDTO.caseNo"
							path="caseEntryDTO.caseNo" isMandatory="true"
							cssClass="form-control" maxlegnth="20" isDisabled="true"></apptags:input>
					</c:if>

				</div>

              <c:if test="${command.dsclEnv eq 'Y' }">
				<div class="form-group">
					<apptags:input pageId="CaseEntry"
						labelCode="caseEntryDTO.cseFilingNo"
						path="caseEntryDTO.cseFilingNumber" cssClass="form-control "
						maxlegnth="16"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

					<apptags:date pageId="CaseEntry"
						labelCode="caseEntryDTO.cseFilingDate"
						datePath="caseEntryDTO.cseFilingDate" isMandatory="true"
						cssClass="form-control" fieldclass="datepicker" mask="99/99/9999"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:date>

				</div>
				</c:if>
			
				<div class="form-group">

					<apptags:input pageId="CaseEntry"
						labelCode="caseEntryDTO.cseSuitNo" path="caseEntryDTO.cseSuitNo"
						isMandatory="true" cssClass="form-control alpaSpecial" maxlegnth="20"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

					<apptags:input pageId="CaseEntry"
						labelCode="caseEntryDTO.cseRefsuitNo"
						path="caseEntryDTO.cseRefsuitNo" cssClass=" form-control alpaSpecial"
						maxlegnth="20"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

				</div>

				<div class="form-group">

					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="caseEntryDTO.cseTypId" text="Case Type" /></label>
					<%-- <c:set var="baseLookupCode" value="TOC" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="caseEntryDTO.cseTypId"
						cssClass="mandColorClass form-control" isMandatory="true"
						hasId="true" selectOptionLabelCode="selectdropdown"
						disabled="${command.saveMode eq 'V' ? true : false }" /> --%>

					<div class="col-sm-4">
						<c:set var="baseLookupCode" value="TOC" />
						<form:select path="caseEntryDTO.cseTypId" class="form-control"
							id="cseTypId" onchange="displayJudgeFeesTable(this)"
							disabled="${command.saveMode eq 'V' ? true : false }">
							<form:option value="0"><spring:message code="lgl.select" text="Select" /></form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option code="${lookUp.lookUpCode}"
									value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>

						</form:select>
					</div>

					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="caseEntryDTO.csePeicDroa" text="Organisation As" /></label>
					<c:set var="baseLookupCode" value="OZA" />
					<!-- OZA -->
					<apptags:lookupField pageId="CaseEntry"
						labelCode="caseEntryDTO.csePeicDroa"
						items="${command.getLevelData(baseLookupCode)}"
						path="caseEntryDTO.csePeicDroa"
						cssClass="mandColorClass form-control" isMandatory="true"
						hasId="true" selectOptionLabelCode="selectdropdown"
						disabled="${command.saveMode eq 'V' ? true : false }" />
				</div>
				<div class="form-group">
					<apptags:lookupFieldSet pageId="CaseEntry"
						labelCode="caseEntryDTO.cseCatId" baseLookupCode="CCT"
						hasId="true" showOnlyLabel="false"
						pathPrefix="caseEntryDTO.cseCatId" isMandatory="true"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control" showAll="false"
						columnWidth="20%"
						disabled="${command.saveMode eq 'V' ? true : false }" />
				</div>

				<div class="form-group">

					<apptags:date pageId="CaseEntry" labelCode="caseEntryDTO.cseDate"
						datePath="caseEntryDTO.cseDate" isMandatory="true"
						cssClass="form-control" fieldclass="datepicker" mask="99/99/9999"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:date>

					<apptags:date pageId="CaseEntry"
						labelCode="caseEntryDTO.cseEntryDt"
						datePath="caseEntryDTO.cseEntryDt" isMandatory="true"
						cssClass="form-control" fieldclass="datepicker" mask="99/99/9999"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:date>

				</div>

				<div class="form-group">

					<label class="control-label col-sm-2 required-control"> <spring:message
							code="caseEntryDTO.crtId" text="Court Name" />
					</label>
					<div class="col-sm-4">
						<!-- chosen-select-no-results -->
						<form:select class=" mandColorClass form-control"
							path="caseEntryDTO.crtId" id="crtId"
							disabled="${command.saveMode eq 'V' ? true : false }">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.courtMasterDTOList}"
								var="courtMasterDTO">
								<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${courtMasterDTO.id}">${courtMasterDTO.crtNameReg}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${courtMasterDTO.id}">${courtMasterDTO.crtName}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>
 
                    <c:if test="${command.envFlag ne 'Y' }">
					<apptags:input pageId="CaseEntry"
						labelCode="caseEntryDTO.cseMatdet1" path="caseEntryDTO.cseMatdet1"
						isMandatory="true" cssClass="hasSpecialCharAndNumber form-control"
						maxlegnth="1000"
						isDisabled="${command.saveMode eq 'V' ? true : false }" />
					</c:if>
					<c:if test="${command.envFlag eq 'Y' }">
					<apptags:input pageId="CaseEntry"
						labelCode="caseEntryDTO.cseMatdet1" path="caseEntryDTO.cseMatdet1"
						isMandatory="true" cssClass="form-control"
						maxlegnth="1000"
						isDisabled="${command.saveMode eq 'V' ? true : false }" />
					</c:if>
					

			

				</div>

				<div class="form-group">

					<label class="control-label col-sm-2 required-control" for=""><spring:message
							code="caseEntryDTO.cseState" text="State" /></label>
					<c:set var="baseLookupCode" value="STT" />
					<!-- STT -->
					<apptags:lookupField pageId="CaseEntry"
						labelCode="caseEntryDTO.cseState"
						items="${command.getLevelData(baseLookupCode)}"
						path="caseEntryDTO.cseState"
						cssClass="mandColorClass form-control" hasChildLookup="false"
						hasId="true" showAll="false"
						selectOptionLabelCode="selectdropdown" showOnlyLabel="State"
						disabled="${command.saveMode eq 'V' ? true : false }" />

					<apptags:input pageId="CaseEntry" labelCode="caseEntryDTO.cseCity"
						path="caseEntryDTO.cseCity" isMandatory="true"
						cssClass="form-control hasNameClass" maxlegnth="250"
						isDisabled="${command.saveMode eq 'V' ? true : false }" />

				</div>

				<div class="form-group">
					<%-- 
					<apptags:input labelCode="caseEntryDTO.cseSectAppl"
						path="caseEntryDTO.cseSectAppl" isMandatory="true"
						cssClass="hasSpecialCharAndNumber form-control" maxlegnth="1000"
						isDisabled="${command.saveMode eq 'V' ? true : false }" /> --%>

					<label class="control-label col-sm-2 required-control" for="Census"><spring:message
							code="caseEntryDTO.cseCaseStatusId" text="Case Status" /></label>

					<c:set var="baseLookupCode" value="CSS" />
					<apptags:lookupField pageId="CaseEntry"
						labelCode="caseEntryDTO.cseCaseStatusId"
						items="${command.getLevelData(baseLookupCode)}"
						path="caseEntryDTO.cseCaseStatusId"
						cssClass="mandColorClass form-control" isMandatory="true"
						hasId="true" selectOptionLabelCode="selectdropdown"
						disabled="${command.saveMode eq 'V' ? true : false }" />

					<c:if test="${command.envFlag ne 'Y' }">
						<apptags:input pageId="CaseEntry"
							labelCode="caseEntryDTO.cseRemarks"
							path="caseEntryDTO.cseRemarks" isMandatory="true"
							cssClass="hasSpecialCharAndNumber form-control" maxlegnth="1000"
							isDisabled="${command.saveMode eq 'V' ? true : false }" />
					</c:if>
					<c:if test="${command.envFlag eq 'Y' }">
						<apptags:input pageId="CaseEntry"
							labelCode="caseEntryDTO.cseRemarks"
							path="caseEntryDTO.cseRemarks" isMandatory="true"
							cssClass="form-control" maxlegnth="1000"
							isDisabled="${command.saveMode eq 'V' ? true : false }" />
					</c:if>
				</div>

				<div class="form-group">

					<label class="control-label col-sm-2 required-control"> <spring:message
							code="caseEntryDTO.advId" />
					</label>
					<div class="col-sm-4">
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="caseEntryDTO.advId" id="advId"
							disabled="${command.saveMode eq 'V' ? true : false }">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.advocates}" var="advocate">
								<form:option value="${advocate.advId}">${advocate.advFirstNm} ${advocate.advMiddleNm} ${advocate.advLastNm}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					
					<apptags:date labelCode="caseEntryDTO.orderDate"
						datePath="caseEntryDTO.orderDate" isMandatory="false"
						cssClass="form-control" fieldclass="datepicker"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:date>
					
			
					</div>

                   <div class="form-group">
					<c:if
						test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}">
						<label class="control-label col-sm-2"> <spring:message
								code="caseEntryDTO.concernedUlb" text="ULB" />
						</label>

						<div class="col-sm-4">
							<form:select path="caseEntryDTO.concernedUlb"
								class="form-control" id="concernedUlb"
								disabled="${command.saveMode eq 'V' ? true : false }">
								<form:option value="0">
									<spring:message code="selectdropdown" text="select" />
								</form:option>
								<c:forEach items="${command.orgList}" var="orgArray">
									<c:if test="${userSession.languageId eq 1}">
										<form:option value="${orgArray[0]}" label="${orgArray[1]}"></form:option>
									</c:if>
									<c:if test="${userSession.languageId eq 2}">
										<form:option value="${orgArray[0]}" label="${orgArray[2]}"></form:option>
									</c:if>
								</c:forEach>
							</form:select>
						</div>
					</c:if>
					<!-- changes done as per MOM -->
					<%-- <label class="control-label col-sm-2 required-control"> <spring:message
							code="caseEntryDTO.officeInCharge" />
					</label>
					<div class="col-sm-4">
						<form:select class="form-control chosen-select-no-results"
							path="caseEntryDTO.officeIncharge" id="officeIncharge"
							disabled="${command.saveMode eq 'V' ? true : false }"
							onchange="setValues(this)">
							<form:option value="0">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.employee}" var="emp">
								<form:option value="${emp.empId}" mobno="${emp.empmobno}"
									email="${emp.empemail}" dept="${emp.deptName}">${emp.fullName}</form:option>
							</c:forEach>
						</form:select>
					</div> --%>
					<form:hidden path="caseEntryDTO.orgid" id="orgid" />
				</div>

				<div class="form-group">
				<c:if test="${command.envFlag eq 'Y' }">
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="caseentry.location" />
					</label>
					<div class="col-sm-4">
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="caseEntryDTO.locId" id="locId"
							disabled="${command.saveMode eq 'V' ? true : false }">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.locations}" var="location">
								<form:option value="${location.locId}">${location.locName}</form:option>
							</c:forEach>
						</form:select>
					</div>
					</c:if>

					<label class="control-label col-sm-2 required-control"> <spring:message
							code="caseEntryDTO.cseDeptid" />
					</label>
					<div class="col-sm-4">
						<form:select
							class=" mandColorClass form-control chosen-select-no-results"
							path="caseEntryDTO.cseDeptid" id="cseDeptid"
							disabled="${command.saveMode eq 'V' ? true : false }">
							<form:option value="">
								<spring:message code="lgl.select" text="Select" />
							</form:option>
							<c:forEach items="${command.departments}" var="department">
								<c:choose>
									<c:when
										test="${userSession.getCurrent().getLanguageId() ne '1'}">
										<form:option value="${department.dpDeptid}">${department.dpNameMar}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${department.dpDeptid}">${department.dpDeptdesc}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>
				</div>


				<%-- 	<div class="form-group">
					<apptags:input labelCode="Mobile No." path="caseEntryDTO.oicMobile"
						isReadonly="true" />
					<apptags:input labelCode="Email ID" path="caseEntryDTO.oicEmail"
						isReadonly="true" />
				</div>

				<div class="form-group">

					<apptags:date labelCode="caseEntryDTO.appointmentDate"
						datePath="caseEntryDTO.appointmentDate" isMandatory="true"
						cssClass="form-control" fieldclass="datepicker"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:date>

					<apptags:input labelCode="Department"
						path="caseEntryDTO.oicDepartment" isReadonly="true" />

				</div> --%>
				<%-- 
				<div class="form-group">
					<apptags:input labelCode="lgl.address" path="caseEntryDTO.address"
						cssClass="alphaNumeric form-control"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						maxlegnth="190"></apptags:input>

					<apptags:input labelCode="lgl.orderNo" path="caseEntryDTO.orderNo"
						cssClass="alphaNumeric form-control"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						maxlegnth="190"></apptags:input>

				</div> --%>

				<%-- 	<div class="form-group">

					<c:if test="${command.saveMode ne 'V' }">

						<label class="col-sm-2 control-label" for="UploadDocument"><spring:message
								code="scheme.master.upload" text="" /></label>
						<div class="col-sm-4">
							<small class="text-blue-2" style="padding-left: 10px;">(UploadFile
								upto 5MB and only pdf,doc,xls,xlsx)</small>
							<apptags:formField fieldType="7" fieldPath=""
								showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
								isMandatory="false" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
								validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
								currentCount="0">
							</apptags:formField>
						</div>
						
					</c:if>

					<c:if test="${command.saveMode eq 'V' }">

						<div class="table-responsive">
							<table class="table table-bordered table-striped" id="deleteDoc">
								<tr>

									<th width="8%"><spring:message code="lgl.srno"
											text="Sr. No." /></th>
									<th><spring:message code="lgl.view.document"
											text="View Document" /></th>

								</tr>
								<c:set var="e" value="0" scope="page" />
								<c:forEach items="${command.attachDocsList}" var="lookUp">
									<tr>
										<td>${e+1}</td>
										<td><apptags:filedownload filename="${lookUp.attFname}"
												filePath="${lookUp.attPath}"
												actionUrl="CaseEntry.html?Download" /></td>

									</tr>
									<c:set var="e" value="${e + 1}" scope="page" />
								</c:forEach>
							</table>
						</div>
					</c:if>
					
					<!--  caseEntryDTO.advId= Advocate -->
					
				</div> --%>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default" id="judgefeeTable">
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
												<th width="100"><spring:message code="lgl.srno"></spring:message></th>
												<th><spring:message
														code="lgl.arbitration.select.judge.name"
														text="Select Judge Name"></spring:message><i
													class="text-red-1">*</i></th>
												<th><spring:message
														code="lgl.arbitration.select.feetype" text="Fee Type"></spring:message><i
													class="text-red-1">*</i></th>
												<th><spring:message code="lgl.arbitration.amount"
														text="Amount"></spring:message></th>
												<c:if test="${command.saveMode ne 'V'}">
													<th width="60"><a href="#a6" data-toggle="tooltip"
														data-placement="top" class="btn btn-blue-2  btn-sm"
														data-original-title="Add" onclick="addEntryData2();"><strong
															class="fa fa-plus"></strong><span class="hide"></span></a></th>
												</c:if>
											</tr>
										</thead>
										<tfoot>

										</tfoot>
										<tbody>
											<c:choose>
												<c:when test="${empty command.arbitoryFeeList}">
													<tr class="judgeDetailRows">
														<c:set var="e" value="0" scope="page"></c:set>
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sequence${e}"
																value="${e+1}" disabled="true" /></td>



														<td><form:select
																path="caseEntryDTO.tbLglArbitoryFee[${e}].judgeName"
																id="judgeName${e}" class="form-control hasCharacter"
																data-rule-required="true">
																<form:option value="0">Select</form:option>

																<c:forEach items="${command.judgeNameList}" var="judge">
																	<form:option value="${judge.id}">${judge.fulName}</form:option>
																</c:forEach>
															</form:select></td>

														<td><c:set var="baseLookupCode" value="FET" /> <form:select
																path="caseEntryDTO.tbLglArbitoryFee[${e}].arbitoryfeeType"
																class="form-control" id="feeType${e}">
																<form:option value="0">Select</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="caseEntryDTO.tbLglArbitoryFee[${e}].arbitoryAmount"
																type="text" id="amount${e}"
																class="form-control text-right hasNumber" /></td>
														<td><a href="#a7" data-toggle="tooltip"
															data-placement="top" class="btn btn-danger btn-sm"
															data-original-title="Delete"
															onclick="deleteEntry2($(this),'removedIds');"> <strong
																class="fa fa-trash"></strong> <span class="hide"><spring:message
																		code="lgl.delete" text="Delete" /></span>
														</a></td>

													</tr>
												</c:when>
												<c:otherwise>
													<c:set var="d" value="0" scope="page" />
													<c:forEach items="${command.arbitoryFeeList}" var="data"
														varStatus="index">
														<tr class="judgeDetailRows">

															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass"
																	id="sequence${d}" value="${d+1}" disabled="true" /></td>
															<td><form:select
																	path="caseEntryDTO.tbLglArbitoryFee[${d}].judgeName"
																	id="judgeName${d}" class="form-control"
																	data-rule-required="true"
																	disabled="${command.saveMode eq 'V' ? true : false }">
																	<form:option value="0">Select</form:option>

																	<c:forEach items="${command.judgeNameList}" var="judge">
																		<form:option value="${judge.id}">${judge.fulName}</form:option>
																	</c:forEach>
																</form:select></td>

															<td><c:set var="baseLookupCode" value="FET" /> <form:select
																	path="caseEntryDTO.tbLglArbitoryFee[${d}].arbitoryfeeType"
																	class="form-control" id="feeType${d}"
																	disabled="${command.saveMode eq 'V' ? true : false }">
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
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>
															<c:if test="${command.saveMode ne 'V'}">
																<td><a href="#a8" data-toggle="tooltip"
																	data-placement="top" class="btn btn-danger btn-sm"
																	data-original-title="Delete"
																	onclick="deleteEntry2($(this),'removedIds');"> <strong
																		class="fa fa-trash"></strong> <span class="hide"><spring:message
																				code="lgl.delete" text="Delete" /></span>
																</a></td>
															</c:if>

														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:otherwise>

											</c:choose>

										</tbody>

									</table>
								</div>

							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"><spring:message
										code="lgl.plaintiff.details" text="Plaintiff Details" /></a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="table-responsive">
									<table id="casePlantiffDetails" summary="Plantiff Data"
										class="table table-bordered table-striped">
										<c:set var="d" value="0" scope="page"></c:set>
										<thead>
											<tr>
												<th width="60"><spring:message code="lgl.srno"
														text="Sr. No." /></th>
												<th width="200"><spring:message
														code="caseEntryDTO.tbLglCasePddetails.csedName"
														text="Plantiff Name" /><i class="text-red-1">*</i></th>

												<th width="200"><spring:message
														code="caseEntryDTO.tbLglCasePddetails.csedFname"
														text="Plantiff Father Name" /><span class="mand showMand" >*</span></th>

												<th width="200"><spring:message code="lgl.partyType"
														text="Party Type" /><i class="text-red-1">*</i></th>
												</th>
												<th width="200"><spring:message
														code="caseEntryDTO.tbLglCasePddetails.csedContactno"
														text="Plantiff Conact No" /><i class="text-red-1"></i></th>
												<th><spring:message
														code="caseEntryDTO.tbLglCasePddetails.csedEmailid"
														text="Plantiff Email Address" /><i class="text-red-1"></i></th>
												<th><spring:message
														code="caseEntryDTO.tbLglCasePddetails.csedAddress"
														text="Plantiff Address" /><span class="mand showMand">*</span></th>
												<c:if test="${command.saveMode ne 'V'}">		
												<th width="8%"><spring:message
														code="lgl.action" text="Action" /></th>
												</c:if>		
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when
													test="${command.saveMode eq 'V' or command.saveMode eq 'E' }">
													<c:set var="e" value="0" scope="page"></c:set>
													<c:forEach items="${command.plenfiffEntryDetailDTOList}"
														var="data" varStatus="index">

														<tr class="appendableClassP">
															<!-- plenfiffEntryDetailDTOList -->

															<form:hidden
																path="plenfiffEntryDetailDTOList[${e}].csedFlag"
																value="P" readonly="true" id="csedFlag${status.index}" />

															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass"
																	id="sequence${e}" value="${e+1}" disabled="true" /></td>

															<td><form:input
																	path="plenfiffEntryDetailDTOList[${e}].csedName"
																	cssClass="form-control mandColorClass required-control hasNameClass"
																	id="csedNamePlantiff${e}" maxlength="500"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><form:input
																	path="plenfiffEntryDetailDTOList[${e}].csedFname"
																	cssClass="form-control mandColorClass required-control hasNameClass"
																	id="csedFNamePlantiff${e}" maxlength="500"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>


															<td><c:set var="baseLookupCode" value="PAT" /> <form:select
																	path="plenfiffEntryDetailDTOList[${e}].csedPartyType"
																	class="form-control" id="csedPartyType${e}" onchange="" disabled="${command.saveMode eq 'V' ? true : false }">
																	<form:option value="0" disabled="true">Select</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option code="${lookUp.lookUpCode}"
																			value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>

																</form:select></td>

															<td><form:input
																	path="plenfiffEntryDetailDTOList[${e}].csedContactno"
																	cssClass="hasMobileNo required-control form-control"
																	id="csedContactnoPlantiff${e}" maxlength="10"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><form:input
																	path="plenfiffEntryDetailDTOList[${e}].csedEmailid"
																	cssClass="hasemailclass required-control form-control"
																	id="csedEmailidPlantiff${e}" maxlength="100"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><form:input
																	path="plenfiffEntryDetailDTOList[${e}].csedAddress"
																	cssClass="form-control mandColorClass hasNameClass"
																	id="csedAddressPlantiff${e}" maxlength="200"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<c:if test="${command.saveMode ne 'V'}">
																<td class="text-center" width="8%">
																<a href="#a9" data-toggle="tooltip"
																	data-placement="top" class="btn btn-blue-2  btn-sm"
																	data-original-title="<spring:message code="lgl.add" text="Add" />" onclick="addEntryData();"><strong
																		class="fa fa-plus"></strong><span class="hide"></span></a>
																<a href="#a10" data-toggle="tooltip"
																	data-placement="top" class="btn btn-danger btn-sm"
																	data-original-title="<spring:message code="lgl.delete" text="Delete" />"
																	onclick="deleteEntry($(this),'removedIds');"> <strong
																		class="fa fa-trash"></strong> <span class="hide"><spring:message
																				code="lgl.delete" text="Delete" /></span>
																</a></td>
															</c:if>
														</tr>

														<c:set var="e" value="${e + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="appendableClassP">
														<!-- plenfiffEntryDetailDTOList -->

														<form:hidden
															path="plenfiffEntryDetailDTOList[${d}].csedFlag"
															value="P" readonly="true" id="csedFlag${status.index}" />

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sequence${d}"
																value="${d+1}" disabled="true" /></td>

														<td><form:input
																path="plenfiffEntryDetailDTOList[${d}].csedName"
																cssClass="form-control mandColorClass required-control hasNameClass"
																id="csedNamePlantiff${d}" maxlength="500"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td><form:input
																path="plenfiffEntryDetailDTOList[${d}].csedFname"
																cssClass="form-control mandColorClass required-control hasNameClass"
																id="csedFNamePlantiff${d}" maxlength="500"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>


														<td><c:set var="baseLookupCode" value="PAT" /> <form:select
																path="plenfiffEntryDetailDTOList[${d}].csedPartyType"
																class="form-control" id="csedPartyType${d}" onchange="" disabled="${command.saveMode eq 'V' ? true : false }">
																<form:option value="0">Select</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option code="${lookUp.lookUpCode}"
																		value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>

															</form:select></td>

														<td><form:input
																path="plenfiffEntryDetailDTOList[${d}].csedContactno"
																cssClass="hasMobileNo required-control form-control"
																id="csedContactnoPlantiff${d}" maxlength="10"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td><form:input
																path="plenfiffEntryDetailDTOList[${d}].csedEmailid"
																cssClass="hasemailclass required-control form-control"
																id="csedEmailidPlantiff${d}" maxlength="100"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td><form:input
																path="plenfiffEntryDetailDTOList[${d}].csedAddress"
																cssClass="form-control mandColorClass required-control hasNameClass"
																id="csedAddressPlantiff${d}" maxlength="200"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<c:if test="${command.saveMode ne 'V'}">
															<td class="text-center" width="8%">
															<a href="#a9" data-toggle="tooltip"
																data-placement="top" class="btn btn-blue-2  btn-sm"
																data-original-title="<spring:message code="lgl.add" text="Add" />" onclick="addEntryData();"><strong
																	class="fa fa-plus"></strong><span class="hide"></span></a>
															<a href="#a11" data-toggle="tooltip"
																data-placement="top" class="btn btn-danger btn-sm"
																data-original-title="<spring:message code="lgl.delete" text="Delete" />"
																onclick="deleteEntry($(this),'removedIds');"> <strong
																	class="fa fa-trash"></strong> <span class="hide"><spring:message
																			code="lgl.delete" text="Delete" /></span>
															</a></td>
														</c:if>
													</tr>
												</c:otherwise>
											</c:choose>
											<c:set var="d" value="${d+1}" scope="page" />
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a3" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a3"><spring:message
										code="lgl.defender.details" text="Defender Details" /></a>
							</h4>
						</div>
						<div id="a3" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="table-responsive">
									<table id="caseDefenderDetails" summary="Defender Data"
										class="table table-bordered table-striped">
										<c:set var="d" value="0" scope="page"></c:set>

										<thead>
											<tr>
												<th width="60"><spring:message code="lgl.srno"
														text="Sr. No." /></th>
												<th width="200"><spring:message
														code="defender.csedName" text="Defender Name"></spring:message><i
													class="text-red-1">*</i></th>
												<th width="200"><spring:message code="lgl.partyType"
														text="Party Type" /><i class="text-red-1">*</i></th>
												</th>
												<th width="200"><spring:message
														code="defender.csedContactno" text="Defender Contact No" /><i
													class="text-red-1"></i></th>
												<th width="200"><spring:message code="defender.csedEmailid"
														text="Defender Email Address" /><i class="text-red-1"></i></th>
												<th width="200"><spring:message code="defender.csed_Address"
														text="Defender Address" /><span class="mand showMand">*</span></th>
												<c:if test="${command.saveMode ne 'V'}">		
												<th width="8%"><spring:message	code="lgl.action" text="Action" /></th>
												</c:if>
											</tr>
										</thead>
										<tbody>

											<c:choose>
												<c:when
													test="${command.saveMode eq 'V' or command.saveMode eq 'E' }">
													<c:set var="i" value="0" scope="page"></c:set>
													<c:forEach items="${command.defenderEntryDetailDTOList}"
														var="data" varStatus="index">
														<tr class="appendableClassD">
															<form:hidden
																path="defenderEntryDetailDTOList[${i}].csedFlag"
																value="D" readonly="true" id="csedFlag${status.index}" />

															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass"
																	id="sequence${i}" value="${i+1}" disabled="true" /></td>

															<td><form:input
																	path="defenderEntryDetailDTOList[${i}].csedName"
																	cssClass="form-control mandColorClass required-control hasNameClass"
																	id="csedNameDefender${i}" maxlength="500"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><c:set var="baseLookupCode" value="PAT" /> <form:select
																	path="defenderEntryDetailDTOList[${i}].csedParty"
																	class="form-control" id="csedParty${i}" onchange="" disabled="${command.saveMode eq 'V' ? true : false }">
																	<form:option value="0">Select</form:option>
																	<c:forEach
																		items="${command.getLevelData(baseLookupCode)}"
																		var="lookUp">
																		<form:option code="${lookUp.lookUpCode}"
																			value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>

																</form:select></td>

															<td><form:input
																	path="defenderEntryDetailDTOList[${i}].csedContactno"
																	cssClass="hasMobileNo required-control form-control"
																	id="csedContactnoDefender${i}" maxlength="10"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>
															<td><form:input
																	path="defenderEntryDetailDTOList[${i}].csedEmailid"
																	cssClass="hasemailclass required-control form-control"
																	id="csedEmailidDefender${i}" maxlength="100"
																	disabled="${command.saveMode eq 'V' ? true : false }"
																	dataRuleEmail="true" /></td>
															<td><form:input
																	path="defenderEntryDetailDTOList[${i}].csedAddress"
																	cssClass="form-control mandColorClass"
																	id="csedAddressDefender${i}" maxlength="200"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<c:if test="${command.saveMode ne 'V'}">
																<td class="text-center" width="8%">
																<a href="#a12" data-toggle="tooltip"
																	data-placement="top" class="btn btn-blue-2  btn-sm"
																	data-original-title="<spring:message code="lgl.add" text="Add" />" onclick="addEntryData1();"><strong
																		class="fa fa-plus"></strong><span class="hide"></span></a>
																<a href="#a13" data-toggle="tooltip"
																	data-placement="top" class="btn btn-danger btn-sm"
																	data-original-title="<spring:message code="lgl.delete" text="Delete" />"
																	onclick="deleteEntry1($(this),'removedIds');"> <strong
																		class="fa fa-trash"></strong> <span class="hide"><spring:message
																				code="lgl.delete" text="Delete" /></span>
																</a></td>
															</c:if>

														</tr>
														<c:set var="i" value="${i + 1}" scope="page" />
													</c:forEach>

												</c:when>

												<c:otherwise>
													<tr class="appendableClassD">
														<form:hidden
															path="defenderEntryDetailDTOList[${d}].csedFlag"
															value="D" readonly="true" id="csedFlag${status.index}" />

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sequence${d}"
																value="${d+1}" disabled="true" /></td>

														<td><form:input
																path="defenderEntryDetailDTOList[${d}].csedName"
																cssClass="form-control mandColorClass required-control hasNameClass"
																id="csedNameDefender${d}" maxlength="500"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td><c:set var="baseLookupCode" value="PAT" /> <form:select
																path="defenderEntryDetailDTOList[${d}].csedParty"
																class="form-control" id="csedParty${d}" onchange="" disabled="${command.saveMode eq 'V' ? true : false }" >
																<form:option value="0">Select</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option code="${lookUp.lookUpCode}"
																		value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>

															</form:select></td>

														<td><form:input
																path="defenderEntryDetailDTOList[${d}].csedContactno"
																cssClass="hasMobileNo required-control form-control"
																id="csedContactnoDefender${d}" maxlength="10"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>
														<td><form:input
																path="defenderEntryDetailDTOList[${d}].csedEmailid"
																cssClass="hasemailclass required-control form-control"
																id="csedEmailidDefender${d}" maxlength="100"
																disabled="${command.saveMode eq 'V' ? true : false }"
																dataRuleEmail="true" /></td>
														<td><form:input
																path="defenderEntryDetailDTOList[${d}].csedAddress"
																cssClass="form-control mandColorClass "
																id="csedAddressDefender${d}" maxlength="200"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<c:if test="${command.saveMode ne 'V'}">
															<td class="text-center" width="8%">
															<a href="#a12" data-toggle="tooltip"
																data-placement="top" class="btn btn-blue-2  btn-sm"
																data-original-title="<spring:message code="lgl.add" text="Add" />" onclick="addEntryData1();"><strong
																	class="fa fa-plus"></strong><span class="hide"></span></a>
															<a href="#a14" data-toggle="tooltip"
																data-placement="top" class="btn btn-danger btn-sm"
																data-original-title="<spring:message code="lgl.delete" text="Delete" />"
																onclick="deleteEntry1($(this),'removedIds');"> <strong
																	class="fa fa-trash"></strong> <span class="hide"><spring:message
																			code="lgl.delete" text="Delete" /></span>
															</a></td>
														</c:if>

													</tr>
												</c:otherwise>
											</c:choose>
											<c:set var="d" value="${d + 1}" scope="page" />
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>


					<!-- Officer In-Charge Details --------------------------------------------------------------------------------------------->


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
												<th width="60"><spring:message code="lgl.srno"
														text="Sr. No." /></th>
												<th width="200"><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicName"
														text="Officer In-charge Name" /><span class="mand showMand" >*</span></th>

												<th width="200"><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicDesg"
														text="Officer In-charge Designation" /><span class="mand showMand" >*</span></th>

												<th width="200"><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicDept"
														text="Officer In-charge Department" /><i class="text-red-1">*</i></th>

												<th width="200"><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicAddress"
														text="Officer In-charge Address" /><span class="mand showMand" >*</span></th>

												<th width="200"><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicPhoneNo"
														text="Officer In-charge Mobile NO" /><span class="mand showMand" >*</span></th>

												<th width="200"><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicEmailId"
														text="Officer In-charge Email Address" /><span class="mand showMand" >*</span></th>


												<th width="200"><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicAppointmentDate"
														text="Officer In-charge Appointment Date" /><span class="mand showMand" >*</span></th>



												<th width="200"><spring:message
														code="caseEntryDTO.tbLglCaseOICdetails.oicOrderNo"
														text="Officer In-charge Order number" /><span class="mand showMand" >*</span></th>
												
												<c:if test="${command.saveMode ne 'V'}">
												<th width="8%"><spring:message code="lgl.action" text="Action" /></th>
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
																	cssClass="form-control hasNameClass"
																	id="officerInchargeName${j}" maxlength="500"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><form:input
																	path="officerInchargeDetailDTOList[${j}].oicDesg"
																	cssClass="form-control hasNameClass"
																	id="officerInchargeDesignation${j}" maxlength="200"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><form:input
																	path="officerInchargeDetailDTOList[${j}].oicDept"
																	cssClass="form-control hasNameClass"
																	id="officerInchargeDepartment${j}" maxlength="250"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><form:input
																	path="officerInchargeDetailDTOList[${j}].oicAddress"
																	cssClass="form-control"
																	id="officerInchargeAddress${j}" maxlength="200"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>


															<td><form:input
																	path="officerInchargeDetailDTOList[${j}].oicPhoneNo"
																	cssClass="hasMobileNo  form-control"
																	id="officerInchargePhoneNo${j}" maxlength="10"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><form:input
																	path="officerInchargeDetailDTOList[${j}].oicEmailId"
																	cssClass="hasemailclass  form-control"
																	id="officerInchargeEmailId${j}" maxlength="100"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<td><div class="input-group">
																	<form:input
																		path="officerInchargeDetailDTOList[${j}].oicAppointmentDate"
																		id="officerInchargeAppointmentDate${j}"
																		class="form-control mandColorClass datepicker dateValidation"
																		value=""
																		disabled="${command.saveMode eq 'V' ? true : false }"
																		maxLength="10" />
																	<label class="input-group-addon"
																		for="oicAppointmentDate"><i
																		class="fa fa-calendar"></i><span class="hide">
																			<spring:message code="" text="icon" />
																	</span><input type="hidden" id=officerInchargeAppointmentDate></label>
																</div></td>

															<td><form:input
																	path="officerInchargeDetailDTOList[${j}].oicOrderNo"
																	cssClass="form-control"
																	id="officerInchargeOrderNo${j}" maxlength="200"
																	disabled="${command.saveMode eq 'V' ? true : false }" /></td>

															<c:if test="${command.saveMode ne 'V'}">
																<td class="text-center" width="8%">
																<a href="#a15" data-toggle="tooltip"
																	data-placement="top" class="btn btn-blue-2  btn-sm"
																	data-original-title="<spring:message code="lgl.add" text="Add" />" onclick="addEntryData3();"><strong
																		class="fa fa-plus"></strong><span class="hide"></span></a>
																<a href="#a16" data-toggle="tooltip"
																	data-placement="top" class="btn btn-danger btn-sm"
																	data-original-title="<spring:message code="lgl.delete" text="Delete" />"
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
																cssClass="form-control hasNameClass"
																id="officerInchargeName${h}" maxlength="500"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>


														<td><form:input
																path="officerInchargeDetailDTOList[${h}].oicDesg"
																cssClass="form-control hasNameClass"
																id="officerInchargeDesignation${h}" maxlength="200"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td><form:input
																path="officerInchargeDetailDTOList[${h}].oicDept"
																cssClass="form-control hasNameClass"
																id="officerInchargeDepartment${h}" maxlength="250"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td><form:input
																path="officerInchargeDetailDTOList[${h}].oicAddress"
																cssClass="form-control"
																id="officerInchargeAddress${h}" maxlength="200"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>


														<td><form:input
																path="officerInchargeDetailDTOList[${h}].oicPhoneNo"
																cssClass="hasMobileNo  form-control"
																id="officerInchargePhoneNo${h}" maxlength="10"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td><form:input
																path="officerInchargeDetailDTOList[${h}].oicEmailId"
																cssClass="hasemailclass  form-control"
																id="officerInchargeEmailId${h}" maxlength="100"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td><div class="input-group">
																<form:input
																	path="officerInchargeDetailDTOList[${h}].oicAppointmentDate"
																	id="officerInchargeAppointmentDate${h}"
																	class="form-control  datepicker dateValidation"
																	value=""
																	disabled="${command.saveMode eq 'V' ? true : false }"
																	maxLength="10" />
																<label class="input-group-addon"
																	for="oicAppointmentDate"><i
																	class="fa fa-calendar"></i><span class="hide"> <spring:message
																			code="" text="icon" />
																</span><input type="hidden" id=officerInchargeAppointmentDate></label>
															</div></td>

														<td><form:input
																path="officerInchargeDetailDTOList[${h}].oicOrderNo"
																cssClass="form-control "
																id="officerInchargeOrderNo${h}" maxlength="100"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>


														<c:if test="${command.saveMode ne 'V'}">
															<td class="text-center" width="8%">
															<a href="#a15" data-toggle="tooltip"
																data-placement="top" class="btn btn-blue-2  btn-sm"
																data-original-title="<spring:message code="lgl.add" text="Add" />" onclick="addEntryData3();"><strong
																	class="fa fa-plus"></strong><span class="hide"></span></a>
															<a href="#a17" data-toggle="tooltip"
																data-placement="top" class="btn btn-danger btn-sm"
																data-original-title="<spring:message code="lgl.delete" text="Delete" />"
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



					<!-- FILE UPLOAD------------------------------------------------------------------------------------------------>
                    
					<div class="panel panel-default">
					  <c:if test="${(fn:length(command.attachDocsList)>0 && (command.saveMode eq 'V')) || (command.saveMode eq 'E' || command.saveMode eq 'C')}">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a4" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a4"> <spring:message
										code="legal.case.doc" text="Case Documents Uploading" /><small class="text-blue-2 margin-left-5"> <spring:message
															code="legal.doc.msg.case" text="(Only .pdf, .png and .jpg is allowed upto 5 MB)" /></small></a>
							</h4>
						</div>
						</c:if>
						<div id="a4" class="panel-collapse collapse in">
							<form:hidden path="removeCommonFileById"
								id="removeCommonFileById" />

							<div class="panel-body">

								<c:if test="${fn:length(command.attachDocsList)>0}">
									<div class="table-responsive">
										<table class="table table-bordered table-striped"
											id="deleteCommonDoc">
											<tr>
												<th width="" align="center"><spring:message
														code="lgl.srno" text="" /><input type="hidden" id="srNo"></th>
												<th scope="col" width="64%" align="center"><spring:message
														code="lgl.doc.desc"
														text="Document Description" /></th>
												<th scope="col" width="30%" align="center"><spring:message
														code="scheme.view.document" /></th>
												<c:if test="${command.saveMode ne 'V'}">
													<th scope="col" width="8%"><spring:message
															code="lgl.action" text="Action"></spring:message></th>
												</c:if>
											</tr>
											<c:set var="e" value="0" scope="page" />
											<c:forEach items="${command.attachDocsList}" var="lookUp">
												<tr>
													<td>${e+1}</td>
													<td>${lookUp.dmsDocName}</td>
													<td><apptags:filedownload
															filename="${lookUp.attFname}"
															filePath="${lookUp.attPath}"
															actionUrl="CaseEntry.html?Download" /></td>
													<c:if test="${command.saveMode ne 'V'}">
														<td class="text-center"><a href='#'
															id="deleteCommonFile" onclick="return false;"
															class="btn btn-danger btn-sm"><i class="fa fa-trash"></i></a>
															<form:hidden path="" value="${lookUp.attId}" /></td>
													</c:if>
												</tr>
												<c:set var="e" value="${e + 1}" scope="page" />
											</c:forEach>
										</table>
									</div>
									<br>
								</c:if>
                               <c:if test="${command.saveMode eq 'E' || command.saveMode eq 'C'}">
								<div id="doCommonFileAttachment">
									<div class="table-responsive">
										<c:set var="cd" value="0" scope="page" />
										<c:if test="${command.saveMode ne 'V'}">
											<table class="table table-bordered table-striped"
												id="attachCommonDoc">
												<tr>
													<th><spring:message
															code="lgl.doc.desc"
															text="Document Description" /></th>
													<th><spring:message code="lgl.comments.upload"
															text="Upload Document" /></th>
													<th scope="col" width="8%"><spring:message	code="lgl.action" text="Action" /></th>		
												</tr>
												

												<tr class="appendableUploadClass">


													<td><form:input
															path="commonFileAttachment[${cd}].doc_DESC_ENGL"
															maxlength="100" class=" form-control" /></td>
													<td class="text-center"><apptags:formField
															pageId="CaseEntry" labelCode="caseEntryDTO.formField"
															fieldType="7"
															fieldPath="commonFileAttachment[${cd}].doc_DESC_ENGL"
															currentCount="${cd}" showFileNameHTMLId="true"
															fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CARE_VALIDATION_EXTENSION"
															callbackOtherTask="callbackOtherTask()">
														</apptags:formField>
														
													</td>
													<td class="text-center" width="8%">
													<a onclick='doCommonFileAttachment(this);'
														title="<spring:message code="lgl.add" text="Add" />"
														class="btn btn-blue-2 btn-sm addButton"><i
															class="fa fa-plus-circle"></i></a>
													<a href='#18'
														id="0_file_${cd}" onclick="doFileDelete(this)"
															title="<spring:message code="lgl.delete" text="Delete" />"
														class='btn btn-danger btn-sm delButton'><i
															class="fa fa-trash"></i></a></td>
												</tr>
												<c:set var="cd" value="${cd+1}" scope="page" />
											</table>
										</c:if>
									</div>
								</div>
								</c:if>

							</div>
						</div>
					</div>
				</div>
				<!--  -->

				<div class="text-center padding-bottom-10">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="Proceed(this)">
							<spring:message code="lgl.advocate.proceed" text="Proceed"></spring:message>
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<button type="Reset" class="btn btn-warning"
							onclick="resetForm(this);">
							<spring:message code="lgl.reset" text="Reset"></spring:message>
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'E'}">
						<button type="Reset" class="btn btn-warning"
							onclick="resetCaseEntry();" id="resetId">
							<spring:message code="lgl.reset" text="Reset"></spring:message>
						</button>
					</c:if>

					<apptags:backButton url="CaseEntry.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>
