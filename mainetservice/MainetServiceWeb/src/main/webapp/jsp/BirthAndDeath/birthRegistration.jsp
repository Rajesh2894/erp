<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<script type="text/javascript"
	src="js/birthAndDeath/birthRegistration.js"></script>
<script type="text/javascript" src="js/cfc/challan/offlinePay.js"></script>


<script>
	$('[data-toggle="tooltip"]').tooltip({
		trigger : 'hover'
	})
</script>

<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="BirthRegistrationDTO.form.name"
						text="Birth Registration Form" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding">
				<form:form id="frmBirthRegistrationForm"
					action="BirthRegistrationForm.html" method="POST"
					class="form-horizontal" name="birthRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="birthRegDto.statusFlag"
						cssClass="hasNumber form-control" id="status" />
					<form:hidden path="birthRegDto.brDraftId"
						cssClass="hasNumber form-control" id="brDraftId" />
					<form:hidden path="birthRegDto.statusCheck"
						cssClass="hasNumber form-control" id="statusCheck" />
					<form:hidden code="" path="birthRegDto.apmApplicationId"
						cssClass="hasNumber form-control" id="apmApplicationId" />
					<%-- <form:hidden path="removeFileById" id="removeFileById" /> --%>

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#birthReg-1"> <spring:message
											code="BirthRegistrationDTO.birthRegistration"
											text="Birth Registration" /></a>
								</h4>
							</div>
							<div id="birthReg-1" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="BirthRegistrationDTO.cpdRefTypeId" text="Birth Type" />
										</label>
										<c:set var="baseLookupCode" value="REF" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.cpdRefTypeId" cssClass="form-control"
											isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />

										<label class="col-sm-2 control-label required-control"><spring:message
												code="BirthRegistrationDTO.brDob" text="Date of Birth" /></label>
										<div class="col-sm-4">
											<div class="input-group">
											 <fmt:formatDate pattern="dd/MM/yyyy" value="${command.birthRegDto.brDob}" var="date" />
												<form:input path="birthRegDto.brDob"  value="${date}"
													cssClass="form-control mandColorClass" id="brDob"
													readonly="true" />
												<div class="input-group-addon">
													<i class="fa fa-calendar"></i>
												</div>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="BirthRegistrationDTO.brSex" text="Sex" />
										</label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.brSex" cssClass="form-control"
											isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />
										<apptags:input labelCode="BirthRegistrationDTO.BrBirthWt"
											path="birthRegDto.BrBirthWt" isMandatory="true"
											cssClass="hasDecimal form-control" maxlegnth="3">
										</apptags:input>
									</div>
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#birthReg-2">
										<spring:message code="BirthRegistrationDTO.childbirthDetails"
											text="Child Birth Details" />
									</a>
								</h4>
							</div>
							<div id="birthReg-2" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="BirthRegistrationDTO.brChildName"
											path="birthRegDto.brChildName" isMandatory=""
											cssClass="hasNameClass form-control" maxlegnth="400">
										</apptags:input>
										<apptags:input labelCode="BirthRegistrationDTO.brChildNameMar"
											path="birthRegDto.brChildNameMar" isMandatory=""
											cssClass="hasNameClass form-control" maxlegnth="400">
										</apptags:input>
									</div>

									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="BirthRegistrationDTO.brBirthPlaceType"
												text="Birth Place Type" />
										</label>
										<c:set var="baseLookupCode" value="DPT" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											changeHandler="selecthosp(this)"
											path="birthRegDto.brBirthPlaceType" cssClass="form-control"
											isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />

										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="BirthRegistrationDTO.hiId" text="Hospital Name" />
										</label>

										<div class="col-sm-4">
											<form:select path="birthRegDto.hiId" cssClass="form-control"
												id="hospitalList" data-rule-required="false">
												<form:option value="">
													<spring:message code="Select" text="Select" />
												</form:option>
												<c:forEach items="${command.hospitalMasterDTOList}"
													var="hospList">
													<c:choose>
														<c:when
															test="${userSession.getCurrent().getLanguageId() eq 1}">
															<form:option value="${hospList.hiId}">${hospList.hiName}</form:option>
														</c:when>
														<c:otherwise>
															<form:option value="${hospList.hiId}">${hospList.hiNameMar}</form:option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</form:select>
										</div>
									</div>

									<div class="form-group">
										<%-- <label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="Place Of Birth(in English)"
												text="Place Of Birth(in English)" />
										</label> --%>
										<apptags:input labelCode="BirthRegistrationDTO.brBirthPlace"
											path="birthRegDto.brBirthPlace" isMandatory="true"
											cssClass="hasNameClass form-control" maxlegnth="200">
										</apptags:input>
										<apptags:input
											labelCode="BirthRegistrationDTO.brBirthPlaceMar"
											path="birthRegDto.brBirthPlaceMar" isMandatory="true"
											cssClass="hasNameClass form-control" maxlegnth="200">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="BirthRegistrationDTO.brBirthAddr"
											path="birthRegDto.brBirthAddr" isMandatory="true"
											cssClass="hasNumClass form-control" maxlegnth="800">
										</apptags:input>

										<apptags:input labelCode="BirthRegistrationDTO.brBirthAddrMar"
											path="birthRegDto.brBirthAddrMar" isMandatory="true"
											cssClass="hasNumClass form-control" maxlegnth="800">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input
											labelCode="BirthRegistrationDTO.brInformantName"
											path="birthRegDto.brInformantName" isMandatory="true"
											cssClass="hasNameClass form-control" maxlegnth="800">
										</apptags:input>
										<apptags:input
											labelCode="BirthRegistrationDTO.brInformantNameMar"
											path="birthRegDto.brInformantNameMar" isMandatory="true"
											cssClass="hasNameClass form-control" maxlegnth="800">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input
											labelCode="BirthRegistrationDTO.brInformantAddr"
											path="birthRegDto.brInformantAddr" isMandatory="true"
											cssClass="hasNumClass form-control" maxlegnth="800">
										</apptags:input>
										<apptags:input
											labelCode="BirthRegistrationDTO.brInformantAddrMar"
											path="birthRegDto.brInformantAddrMar" isMandatory="true"
											cssClass="hasNumClass form-control" maxlegnth="800">
										</apptags:input>
									</div>

									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"><spring:message
												code="BirthRegistrationDTO.cpdAttntypeId"
												text="Attention Type" /> </label>
										<c:set var="baseLookupCode" value="ATT" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.cpdAttntypeId" cssClass="form-control"
											isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />

										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="BirthRegistrationDTO.cpdDelMethId"
												text="Delivery Method" />
										</label>
										<c:set var="baseLookupCode" value="DEM" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.cpdDelMethId" cssClass="form-control"
											isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />
									</div>

									<div class="form-group">
										<apptags:input labelCode="BirthRegistrationDTO.brPregDuratn"
											path="birthRegDto.brPregDuratn" isMandatory="true"
											cssClass="hasNumber form-control" maxlegnth="2">
										</apptags:input>
										<apptags:input labelCode="BirthRegistrationDTO.brBirthMark"
											path="birthRegDto.brBirthMark" isMandatory="true"
											cssClass="hasNameClass form-control" maxlegnth="350">
										</apptags:input>
									</div>
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#birthReg-3">
										<spring:message code="BirthRegistrationDTO.parentDetails"
											text="Parent Details" />
									</a>
								</h4>
							</div>
							<div id="birthReg-3" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.pdFathername"
											path="birthRegDto.parentDetailDTO.pdFathername"
											isMandatory="true" cssClass="hasNameClass form-control"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdFathernameMar"
											path="birthRegDto.parentDetailDTO.pdFathernameMar"
											isMandatory="true" cssClass="hasNameClass form-control"
											maxlegnth="350">
										</apptags:input>
									</div>

									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="ParentDetailDTO.cpdFEducnId" text="Education" />
										</label>
										<c:set var="baseLookupCode" value="EDU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdFEducnId"
											cssClass="form-control" isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />

										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="ParentDetailDTO.cpdFOccuId" text="Occupation" />
										</label>
										<c:set var="baseLookupCode" value="OCU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdFOccuId"
											cssClass="form-control" isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />

									</div>

									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.pdMothername"
											path="birthRegDto.parentDetailDTO.pdMothername"
											isMandatory="true" cssClass="hasNameClass form-control"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdMothernameMar"
											path="birthRegDto.parentDetailDTO.pdMothernameMar"
											isMandatory="true" cssClass="hasNameClass form-control"
											maxlegnth="350">
										</apptags:input>
									</div>

									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="ParentDetailDTO.cpdMEducnId" text="Education" />
										</label>
										<c:set var="baseLookupCode" value="EDU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdMEducnId"
											cssClass="form-control" isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />

										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="ParentDetailDTO.cpdMOccuId" text="Occupation" />
										</label>
										<c:set var="baseLookupCode" value="OCU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdMOccuId"
											cssClass="form-control" isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />

									</div>

									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.pdAgeAtMarry"
											path="birthRegDto.parentDetailDTO.pdAgeAtMarry"
											isMandatory="true" cssClass="hasNumber form-control"
											maxlegnth="2">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdAgeAtBirth"
											path="birthRegDto.parentDetailDTO.pdAgeAtBirth"
											isMandatory="true" cssClass="hasNumber form-control"
											maxlegnth="2">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.pdLiveChildn"
											path="birthRegDto.parentDetailDTO.pdLiveChildn"
											isMandatory="true" cssClass="hasNumber form-control"
											maxlegnth="2">
										</apptags:input>
									</div>
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#birthReg-4">
										<spring:message code="ParentDetailDTO.parentAddressDetails"
											text="Parent's Address Details" />
									</a>
								</h4>
							</div>
							<div id="birthReg-4" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.motheraddress"
											path="birthRegDto.parentDetailDTO.motheraddress"
											isMandatory="true" cssClass="hasNumClass form-control"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.motheraddressMar"
											path="birthRegDto.parentDetailDTO.motheraddressMar"
											isMandatory="true" cssClass="hasNumClass form-control"
											maxlegnth="350">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.pdParaddress"
											path="birthRegDto.parentDetailDTO.pdParaddress"
											isMandatory="true" cssClass="hasNumClass form-control"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdParaddressMar"
											path="birthRegDto.parentDetailDTO.pdParaddressMar"
											isMandatory="true" cssClass="hasNumClass form-control"
											maxlegnth="350">
										</apptags:input>
										</div>
										<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.permanent.parent.addr"
											path="birthRegDto.parentDetailDTO.pdAddress"
											isMandatory="true" cssClass="hasNumClass form-control"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.permanent.parent.addrMar"
											path="birthRegDto.parentDetailDTO.pdAddressMar"
											isMandatory="true" cssClass="hasNumClass form-control"
											maxlegnth="350">
										</apptags:input>
									</div>
									

									<div class="form-group">
										<apptags:lookupFieldSet
											cssClass="form-control required-control margin-bottom-10"
											baseLookupCode="TRY" hasId="true"
											pathPrefix="birthRegDto.parentDetailDTO.cpdId"
											hasLookupAlphaNumericSort="true" isMandatory="true"
											hasSubLookupAlphaNumericSort="true" showAll="false" />
											
								
											<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="bnd.ward" text="WARD" />
										</label>
										<c:set var="baseLookupCode" value="BWD" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.wardid"
											cssClass="form-control" isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />

									</div>
									

									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="ParentDetailDTO.cpdReligionId" text="Religion" />
										</label>
										<c:set var="baseLookupCode" value="RLG" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdReligionId"
											cssClass="form-control" isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="ParentDetailDTO.pdRegUnitId" text="Registration Unit" />
										</label>
										<c:set var="baseLookupCode" value="REU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.pdRegUnitId"
											cssClass="form-control" isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />

									</div>
									<%-- <c:out value="${command.birthRegDto.statusCheck}"></c:out>	 --%>
									<div class="form-group">
										<apptags:date fieldclass="datepicker"
											labelCode="BirthRegistrationDTO.brRegDate" readonly="true"
											datePath="birthRegDto.brRegDate" isMandatory="true">
										</apptags:date>
										<%-- <apptags:input
											labelCode="Registration Date"
											path="birthRegDto.brRegDate"
											isMandatory="false" cssClass="alphaNumeric" maxlegnth="20">
										</apptags:input> --%>

										<apptags:input labelCode="BirthRegDto.brRegNo"
											onBlur="getRegNo()" path="birthRegDto.brRegNo"
											isMandatory="true" cssClass="alphaNumeric" maxlegnth="20">
										</apptags:input>
									</div>
								</div>
							</div>
						</div>
					</div>
					<c:if test="${command.birthRegDto.statusCheck eq 'A'}">
						<div class="padding-top-10 text-center" id="chekListChargeId">

							<button type="button" onclick="saveDraftBirthData(this)"
								id="savedrftId" class="btn btn-blue-3" data-toggle="tooltip"
								data-original-title="">
								<spring:message code="BirthRegDto.savedrft" />
							</button>
							<button type="button" class="btn btn-submit"
								data-toggle="tooltip" data-original-title="" id="proceedId"
								onclick="getChecklistAndCharges(this)">
								<spring:message code="BirthRegDto.proceed" text="Proceed" />
							</button>
							<button type="button" onclick="resetMemberMaster(this);"
								class="btn btn-warning" data-toggle="tooltip"
								data-original-title="" id="resetId">
								<spring:message code="BirthRegDto.reset" />
							</button>

							<button type="button" class="btn btn-danger " id="backId"
								data-toggle="tooltip" data-original-title=""
								onclick="window.location.href ='BirthRegistrationForm.html'">
								<spring:message code="BirthRegDto.back" />
							</button>

						</div>

						<c:if test="${not empty command.checkList}">
							<h4>
								<spring:message text="Upload Documents" />
							</h4>
							
							<c:choose>
							<c:when test="${not empty command.fetchDocumentList}">
							<fieldset class="fieldRound">
								<div class="overflow" id="attachId">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped"
											id="attachmentTable">
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="label.checklist.srno" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="label.checklist.docname" text="Document Required" /></label></th>
													<th><label class="tbold"><spring:message
																code="label.checklist.status" text="Status" /></label></th>
													<th><label class="tbold"><spring:message
																code="label.checklist.upload" text="Upload" /></label></th>
												</tr>
												<tr>
												<tr>
															<c:forEach items="${command.fetchDocumentList}"
																var="lukUp">
																<td align="center">${lukUp.clmSrNo}</td>
																<td align="center">${lukUp.clmDesc}</td>
																<td align="center"><c:if
																		test="${lukUp.mandatory eq 'Y'}">
																		<spring:message code="" text="Mandatory" />
																	</c:if> <c:if test="${lukUp.mandatory eq 'N'}">
																		<spring:message code="" text="Optional" />
																	</c:if></td>
																<td align="center"><apptags:filedownload
																		filename="${lukUp.attFname}"
																		filePath="${lukUp.attPath}"
																		actionUrl="BirthRegistrationForm.html?Download">
																	</apptags:filedownload>
																	<small class="text-blue-2 "> Multiple Documents/Images up to 1 MB can be upload.only pdf,png,jpg is allowed.</small>
																	
																	</td>
															</c:forEach>
														
												</tr>

											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
							</c:when>
							<c:otherwise>
							<fieldset class="fieldRound">
								<div class="overflow" id ="checkTableId">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped"
											id="DeathTable" >
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="label.checklist.srno" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="label.checklist.docname" text="Document Required" /></label></th>
													<th><label class="tbold"><spring:message
																code="label.checklist.status" text="Status" /></label></th>
													<th><label class="tbold"><spring:message
																code="label.checklist.upload" text="Upload" /></label></th>
												</tr>

												<c:forEach items="${command.checkList}" var="lookUp"
													varStatus="lk">

													<tr>
														<td>${lookUp.documentSerialNo}</td>
																<c:choose>
																	<c:when
																		test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
																		<td><label>${lookUp.doc_DESC_ENGL}</label></td>
																	</c:when>
																	<c:otherwise>
																		<c:set var="docName" value="${lookUp.doc_DESC_Mar}" />
																		<td><label>${lookUp.doc_DESC_Mar}</label></td>
																	</c:otherwise>
																</c:choose>
																<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td>${lookUp.descriptionType}<spring:message
																			code="" text="Mandatory" /></td>
																</c:if>

																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td>${lookUp.descriptionType}<spring:message
																			code="" text="Optional" /></td>
																</c:if>
																<td>
																	<div id="docs_${lk}">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath="checkList[${lk.index}]"
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="CARE_COMMON_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																			checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
																			checkListDesc="${docName}" currentCount="${lk.index}" />
																	</div>

																</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
							</c:otherwise>
							</c:choose>
						</c:if>
					</c:if>
					<form:hidden path="birthRegDto.chargesStatus" id="chargeStatus" />
					<c:if test="${command.birthRegDto.chargesStatus eq 'CA'}">
						<div class="form-group margin-top-10">
							<label class="col-sm-2 control-label"><spring:message
									code="water.field.name.amounttopay" text="Amount to Pay"/></label>
							<div class="col-sm-4">
								<input type="text" class="form-control" disabled="disabled"
									value="${command.chargesAmount}" maxlength="12"
									id="chargesAmount"></input>
							</div>
						</div>
                      <c:if test="${command.birthRegDto.chargesStatus eq 'CA' && command.chargesAmount ne 0}">
						<div class="panel panel-default">
							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
						</div>
						</c:if>
					</c:if>
					<c:if
						test="${command.birthRegDto.statusCheck eq 'NA' || not empty command.checkList || command.birthRegDto.chargesStatus eq 'CA'}">
						<div class="text-center padding-top-10">
							<%-- <button type="button" onclick="saveDraftBirthData(this)"
								id="savedrftId" class="btn btn-blue-3" data-toggle="tooltip"
								data-original-title="">
								<spring:message code="BirthRegDto.savedrft" />
							</button> --%>
						<c:if test="${command.chargesFetched ne 'N'}">
							<button type="button" class="btn btn-green-3"
								data-toggle="tooltip" data-original-title=""
								onclick="saveBirthData(this)">
								<spring:message code="BirthRegDto.save" />
							</button>
							</c:if>
							<%-- 	<button type="button" value="<spring:message code="bt.save"/>"
								onclick="saveDraftBirthData(this)" class="btn btn-green-3"
								title="Submit">
								Save As Draft
							</button> --%>
							<button type="button" onclick="resetMemberMaster(this);"
								class="btn btn-warning" data-toggle="tooltip"
								data-original-title="">
								<spring:message code="BirthRegDto.reset" />
							</button>
							<button type="button" class="btn btn-danger "
								data-toggle="tooltip" data-original-title=""
								onclick="window.location.href ='BirthRegistrationForm.html'">
								<spring:message code="BirthRegDto.back" />
							</button>
						</div>
					</c:if>
				</form:form>
			</div>
		</div>
	</div>
</div>


