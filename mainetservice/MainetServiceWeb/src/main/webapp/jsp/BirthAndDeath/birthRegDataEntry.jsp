<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<script type="text/javascript" src="js/birthAndDeath/dataEntryForBirthReg.js"></script>

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
					<spring:message code="dataentry.birthRegForm" text="Birth Registration Data Entry Form" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding">
				<form:form id="frmBirthRegistrationForm"
					action="dataEntryForBirthReg.html" method="POST"
					class="form-horizontal" name="birthRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="birthRegDto.brId"
						cssClass="hasNumber form-control" id="brId" />
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
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />

										<apptags:date fieldclass="datepicker" isDisabled="${command.saveMode eq 'V'}"
											labelCode="BirthRegistrationDTO.brDob" 
											datePath="birthRegDto.brDob" isMandatory="true">
										</apptags:date>
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
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
										<apptags:input labelCode="BirthRegistrationDTO.BrBirthWt"
											path="birthRegDto.BrBirthWt" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
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
											path="birthRegDto.brChildName" isMandatory="" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="400">
										</apptags:input>
										<apptags:input labelCode="BirthRegistrationDTO.brChildNameMar"
											path="birthRegDto.brChildNameMar" isMandatory=""  isDisabled="${command.saveMode eq 'V'}"
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
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
                                      <c:if test="${command.saveMode ne 'V'}">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="BirthRegistrationDTO.hiId" text="Hospital Name" />
										</label>

										<div class="col-sm-4">
											<form:select path="birthRegDto.hiId" cssClass="form-control"
												id="hospitalList" data-rule-required="false" >
												<form:option value="" >
													<spring:message code="Select" text="Select" />
												</form:option>
												<c:forEach items="${command.hospitalMasterDTOList}"
													var="hospList" >
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
										</c:if>
									</div>

									<div class="form-group">
										<%-- <label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="Place Of Birth(in English)"
												text="Place Of Birth(in English)" />
										</label> --%>
										<apptags:input labelCode="BirthRegistrationDTO.brBirthPlace"
											path="birthRegDto.brBirthPlace" isMandatory="true"  isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="200">
										</apptags:input>
										<apptags:input
											labelCode="BirthRegistrationDTO.brBirthPlaceMar"
											path="birthRegDto.brBirthPlaceMar" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="200">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="BirthRegistrationDTO.brBirthAddr"
											path="birthRegDto.brBirthAddr" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="800">
										</apptags:input>

										<apptags:input labelCode="BirthRegistrationDTO.brBirthAddrMar"
											path="birthRegDto.brBirthAddrMar" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="800">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input
											labelCode="BirthRegistrationDTO.brInformantName"
											path="birthRegDto.brInformantName" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="800">
										</apptags:input>
										<apptags:input
											labelCode="BirthRegistrationDTO.brInformantNameMar"
											path="birthRegDto.brInformantNameMar" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control" maxlegnth="800">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input
											labelCode="BirthRegistrationDTO.brInformantAddr"
											path="birthRegDto.brInformantAddr" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumClass form-control" maxlegnth="800">
										</apptags:input>
										<apptags:input
											labelCode="BirthRegistrationDTO.brInformantAddrMar"
											path="birthRegDto.brInformantAddrMar" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
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
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
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
											isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
									</div>

									<div class="form-group">
										<apptags:input labelCode="BirthRegistrationDTO.brPregDuratn"
											path="birthRegDto.brPregDuratn" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNumber form-control" maxlegnth="2">
										</apptags:input>
										<apptags:input labelCode="BirthRegistrationDTO.brBirthMark"
											path="birthRegDto.brBirthMark" isMandatory="true" isDisabled="${command.saveMode eq 'V'}"
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
											path="birthRegDto.parentDetailDTO.pdFathername" isDisabled="${command.saveMode eq 'V'}"
											isMandatory="true" cssClass="hasNameClass form-control"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdFathernameMar"
											path="birthRegDto.parentDetailDTO.pdFathernameMar" isDisabled="${command.saveMode eq 'V'}"
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
											cssClass="form-control" isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />

										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="ParentDetailDTO.cpdFOccuId" text="Occupation" />
										</label>
										<c:set var="baseLookupCode" value="OCU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdFOccuId"
											cssClass="form-control" isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />

									</div>

									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.pdMothername"
											path="birthRegDto.parentDetailDTO.pdMothername" isDisabled="${command.saveMode eq 'V'}"
											isMandatory="true" cssClass="hasNameClass form-control"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdMothernameMar"
											path="birthRegDto.parentDetailDTO.pdMothernameMar" isDisabled="${command.saveMode eq 'V'}"
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
											cssClass="form-control" isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />

										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="ParentDetailDTO.cpdMOccuId" text="Occupation" />
										</label>
										<c:set var="baseLookupCode" value="OCU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdMOccuId"
											cssClass="form-control" isMandatory="true" hasId="true" disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />

									</div>

									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.pdAgeAtMarry"
											path="birthRegDto.parentDetailDTO.pdAgeAtMarry" isDisabled="${command.saveMode eq 'V'}"
											isMandatory="true" cssClass="hasNumber form-control"
											maxlegnth="2">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdAgeAtBirth"
											path="birthRegDto.parentDetailDTO.pdAgeAtBirth" isDisabled="${command.saveMode eq 'V'}"
											isMandatory="true" cssClass="hasNumber form-control"
											maxlegnth="2">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.pdLiveChildn"
											path="birthRegDto.parentDetailDTO.pdLiveChildn" isDisabled="${command.saveMode eq 'V'}"
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
											path="birthRegDto.parentDetailDTO.motheraddress"  isDisabled="${command.saveMode eq 'V'}"
											isMandatory="true" cssClass="hasNumClass form-control"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.motheraddressMar"
											path="birthRegDto.parentDetailDTO.motheraddressMar" isDisabled="${command.saveMode eq 'V'}"
											isMandatory="true" cssClass="hasNumClass form-control"
											maxlegnth="350">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.pdParaddress"
											path="birthRegDto.parentDetailDTO.pdParaddress" isDisabled="${command.saveMode eq 'V'}"
											isMandatory="true" cssClass="hasNumClass form-control"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdParaddressMar"
											path="birthRegDto.parentDetailDTO.pdParaddressMar"  isDisabled="${command.saveMode eq 'V'}"
											isMandatory="true" cssClass="hasNumClass form-control"
											maxlegnth="350">
										</apptags:input>
										</div>
										<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.permanent.parent.addr"
											path="birthRegDto.parentDetailDTO.pdAddress" isDisabled="${command.saveMode eq 'V'}"
											isMandatory="true" cssClass="hasNumClass form-control"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.permanent.parent.addrMar"
											path="birthRegDto.parentDetailDTO.pdAddressMar" isDisabled="${command.saveMode eq 'V'}"
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
											hasSubLookupAlphaNumericSort="true" showAll="false" disabled="${command.saveMode eq 'V' ? true : false }"/>
											
								
											<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="applicant.wardName" text="WARD" />
										</label>
										<c:set var="baseLookupCode" value="BWD" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.wardid" disabled="${command.saveMode eq 'V' ? true : false }"
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
											path="birthRegDto.parentDetailDTO.cpdReligionId" disabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="form-control" isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="ParentDetailDTO.pdRegUnitId" text="Registration Unit" />
										</label>
										<c:set var="baseLookupCode" value="REU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.pdRegUnitId" disabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="form-control" isMandatory="true" hasId="true"
											selectOptionLabelCode="selectdropdown" />
									</div>
									<div class="form-group">
										<apptags:date fieldclass="datepicker" isDisabled="${command.saveMode eq 'V'}"
											labelCode="BirthRegistrationDTO.brRegDate" 
											datePath="birthRegDto.brRegDate" isMandatory="true">
										</apptags:date>
										<apptags:input labelCode="BirthRegDto.brRegNo"
											 path="birthRegDto.brRegNo" isDisabled="${command.saveMode eq 'V'}"
											isMandatory="true" cssClass="alphaNumeric" maxlegnth="20">
										</apptags:input>
									</div>
								</div>
							</div>
						</div>
					</div>
					
				
						<div class="text-center padding-top-10">
						<c:if test="${command.saveMode ne 'V'}">
							<button type="button" align="center" class="btn btn-green-3"
								data-toggle="tooltip" data-original-title=""
								onclick="saveBirthData(this)">
								<spring:message code="BirthRegDto.save" />
							</button>
							<button type="button" align="center" onclick="resetMemberMaster(this);"
								class="btn btn-warning" data-toggle="tooltip"
								data-original-title="">
								<spring:message code="BirthRegDto.reset" />
							</button>
						 </c:if>
							<button type="button" align="center" class="btn btn-danger "
								data-toggle="tooltip" data-original-title=""
								onclick="window.location.href ='dataEntryForBirthReg.html'">
								<spring:message code="BirthRegDto.back" />
							</button>
						</div>
				</form:form>
			</div>
		</div>
	</div>
</div>


