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
<script src="js/mainet/dashboard/moment.min.js"></script>
<script type="text/javascript" src="js/birthAndDeath/birthCorrection.js"></script>

<script>
	$('[data-toggle="tooltip"]').tooltip({
		trigger : 'hover'
	})
	$(document).ready(function() {
		var list = document.getElementById('corrCategory');
		var listArray = new Array();
		if (list != null) {
			for (i = 0; i < list.options.length; i++) {
				listArray[i] = list.options[i].value;
			}
			$.each(listArray, function(index) {

				$("." + listArray[index]).attr("disabled", true);
			});
		}
	});

</script>

<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="BirthRegDto.BrcFrm"
						text="Birth Correction Form" />
				</h2>
				<apptags:helpDoc url="BirthCorrectionForm.html"></apptags:helpDoc>			
			</div>
			<div class="widget-content padding" id="ashish">
				<form:form id="frmBirthCorrectionForm" commandName="command"
					action="BirthCorrectionForm.html" method="POST"
					class="form-horizontal" name="birthCorrectionFormId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="birthRegDto.brId"
						cssClass="hasNumber form-control" id="brId" />

					<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display:none;"></div>
					<!-- fetch data after search start -->

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						<c:if test="${command.saveMode eq 'E'}">
						<div class="panel panel-default" id="correct">
						<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a2" tabindex="-1"> <spring:message
										code="bnd.correction.category" text="Correction Catagory" /></a>
							</h4>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="form-group">
										<label class="col-sm-2 control-label"
											for="corrCategory"><spring:message
										code="bnd.correction.category" text="Correction Catagory" /></label>
										<div class="col-sm-4">
											<form:select path="birthRegDto.corrCategory" multiple="true" onchange="correctionsCategory(this)" id="corrCategory"
												class="form-control chosen-select-no-results " disabled="false" label="Select">
												<c:set var="baseLookupCode" value="BCC" />
												<%-- <form:option value="">
													<spring:message code="solid.waste.select" text="select" />
												</form:option> --%>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpCode}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
								</div>

							</div>
						</div>
							</c:if>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#birthCor-1" tabindex="-1"> <spring:message
											code="BirthRegistrationDTO.birthRegistration"
											text="Birth Registration" /></a>
								</h4>
							</div>
							<div id="birthCor-1" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
									<%-- 	<apptags:date fieldclass="datepicker" 
											labelCode="BirthRegistrationDTO.brDob"  isDisabled="true"
											datePath="birthRegDto.brDob" cssClass="form-control mandColorClass BRDOB" >
										</apptags:date> --%>

										<label class="col-sm-2 control-label"> <spring:message
												code="BirthRegistrationDTO.brDob" text="Date of Birth" />
										</label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input path="birthRegDto.brDateOfBirth"
													cssClass="form-control mandColorClass datepicker BRDOB" id="brDob"
													disabled="true"/>
												<div class="input-group-addon">
													<i class="fa fa-calendar"></i>
												</div>
											</div>
										</div>

										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="BirthRegistrationDTO.brSex" text="Sex" />
										</label>
										<c:set var="baseLookupCode" value="GEN" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.brSex" cssClass="form-control BRSEX"
											isMandatory="true" hasId="true"
											disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
									</div>
									<div class="form-group">
										<apptags:input labelCode="BirthRegDto.brRegNo"
											path="birthRegDto.brRegNo" isReadonly="true"
											isMandatory="false" cssClass="alphaNumeric BRREGNO" maxlegnth="20">
										</apptags:input>
										</div>
										
										
									<div class="form-group">
										<c:set var="baseLookupCode" value="BDZ" />
										<apptags:lookupFieldSet
											cssClass="form-control required-control" baseLookupCode="BDZ"
											hasId="true" pathPrefix="birthRegDto.bndDw"
											hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" showAll="false"
											isMandatory="true" />
									</div>
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#birthCor-2" tabindex="-1">
										<spring:message code="BirthRegistrationDTO.childbirthDetails"
											text="Child Birth Details" />
									</a>
								</h4>
							</div>
							<div id="birthCor-2" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="BirthRegistrationDTO.brChildName"
											path="birthRegDto.brChildName" isMandatory=""
											isDisabled="${command.saveMode eq 'V' ? true : false }"
											cssClass="hasNameClass form-control BRCHILDNAME" maxlegnth="400">
										</apptags:input>
										<apptags:input labelCode="BirthRegistrationDTO.brChildNameMar"
											path="birthRegDto.brChildNameMar" isMandatory=""
											isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control BRCHILDNAME" maxlegnth="400">
										</apptags:input>
									</div>


									<div class="form-group">
										<apptags:input labelCode="BirthRegistrationDTO.brBirthPlace"
											path="birthRegDto.brBirthPlace" isMandatory="true"
											isDisabled="${command.saveMode eq 'V'}"
											cssClass="hasNameClass form-control BRBIRTHPLACE" maxlegnth="200">
										</apptags:input>
										<apptags:input
											labelCode="BirthRegistrationDTO.brBirthPlaceMar"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.brBirthPlaceMar" isMandatory="true"
											cssClass="hasNameClass form-control BRBIRTHPLACE" maxlegnth="200">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="BirthRegistrationDTO.brBirthAddr"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.brBirthAddr" isMandatory="true"
											cssClass="hasNumClass form-control BRBIRTHADDR" maxlegnth="800">
										</apptags:input>
										<apptags:input labelCode="BirthRegistrationDTO.brBirthAddrMar"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.brBirthAddrMar" isMandatory="true"
											cssClass="hasNumClass form-control BRBIRTHADDR" maxlegnth="800">
										</apptags:input>
									</div>
									<div class="form-group">
										<apptags:input
											labelCode="BirthRegistrationDTO.brInformantName"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.brInformantName" isMandatory="true"
											cssClass="hasNameClass form-control" maxlegnth="800">
										</apptags:input>
										<apptags:input
											labelCode="BirthRegistrationDTO.brInformantAddr"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.brInformantAddr" isMandatory="true"
											cssClass="hasNumClass form-control" maxlegnth="800">
										</apptags:input>
									</div>
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#birthCor-3" tabindex="-1">
										<spring:message code="BirthRegistrationDTO.parentDetails"
											text="Parent Details" />
									</a>
								</h4>
							</div>
							<div id="birthCor-3" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.pdFathername"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.parentDetailDTO.pdFathername"
											isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdFathernameMar"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.parentDetailDTO.pdFathernameMar"
											isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
											maxlegnth="350">
										</apptags:input>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2"
											for="Census"> <spring:message code="ParentDetailDTO.pdFatherEducation"
												text=" Father Education" />
										</label>
										<c:set var="baseLookupCode" value="EDU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdFEducnId"
											cssClass="form-control CPDFEDUCNID" isMandatory="" hasId="true"
											disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />

										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="ParentDetailDTO.pdFatherOccupation"
												text="Father Occupation" />
										</label>
										<c:set var="baseLookupCode" value="OCU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdFOccuId"
											cssClass="form-control CPDFOCCUID" isMandatory="" hasId="true"
											disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />

									</div>

									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.pdMothername"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.parentDetailDTO.pdMothername"
											isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdMothernameMar"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.parentDetailDTO.pdMothernameMar"
											isMandatory="true" cssClass="hasNameClass form-control PARENTNAME"
											maxlegnth="350">
										</apptags:input>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="ParentDetailDTO.pdMotherEducation"
												text="Mother Education" />
										</label>
										<c:set var="baseLookupCode" value="EDU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdMEducnId"
											cssClass="form-control CPDMEDUCNID" isMandatory="" hasId="true"
											disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />

										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message code="ParentDetailDTO.pdMotherOccupation"
												text=" Mother Occupation" />
										</label>
										<c:set var="baseLookupCode" value="OCU" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.parentDetailDTO.cpdMOccuId"
											cssClass="form-control CPDMOCCUID" isMandatory="" hasId="true"
											disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />

									</div>
									<div class="form-group">

										<apptags:input labelCode="ParentDetailDTO.pdAgeAtBirth"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.parentDetailDTO.pdAgeAtBirth"
											isMandatory="true" cssClass="hasNumber form-control PDAGEATBIRTH"
											maxlegnth="2">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdLiveChildn"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.parentDetailDTO.pdLiveChildn"
											isMandatory="" cssClass="hasNumber form-control PDLIVECHILDN"
											maxlegnth="2">
										</apptags:input>
									</div>
								</div>
							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#birthCor-4" tabindex="-1">
										<spring:message code="ParentDetailDTO.parentAddressDetails"
											text="Parent's Address Details" />
									</a>
								</h4>
							</div>
							<div id="birthCor-4" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.pdParaddress"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.parentDetailDTO.pdParaddress"
											isMandatory="true" cssClass="hasNumClass form-control ADDRESS"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.pdParaddressMar"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.parentDetailDTO.pdParaddressMar"
											isMandatory="true" cssClass="hasNumClass form-control ADDRESS"
											maxlegnth="350">
										</apptags:input>
									</div>

									<div class="form-group">
										<apptags:input labelCode="ParentDetailDTO.permanent.parent.addr"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.parentDetailDTO.pdAddress"
											isMandatory="" cssClass="hasNumClass form-control ADDRESS"
											maxlegnth="350">
										</apptags:input>
										<apptags:input labelCode="ParentDetailDTO.permanent.parent.addrMar"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.parentDetailDTO.pdAddressMar"
											isMandatory="" cssClass="hasNumClass form-control ADDRESS"
											maxlegnth="350">
										</apptags:input>
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
											cssClass="form-control CPDRELIGIONID" isMandatory="true" hasId="true"
											disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
											
										<label class="control-label col-sm-2 required-control"
											for="Census"> <spring:message
												code="applicantinfo.label.ward" text="Ward" />
										</label>
										<c:set var="baseLookupCode" value="BWD" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="birthRegDto.wardid"
											cssClass="form-control WARDID" isMandatory="false" hasId="true"
											disabled="${command.saveMode eq 'V' ? true : false }"
											selectOptionLabelCode="selectdropdown" />
									</div>
								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 type="h4" class="panel-title table" id="">
									<a data-toggle="collapse" class="collapsed" href="#birthCor-5" tabindex="-1">
										<spring:message text="Certificate print details"
											code="Issuaance.death.certificate.print" />
									</a>
								</h4>
							</div>
							<div id="birthCor-5" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="form-group">
									<c:if test="${command.saveMode ne 'V'}">
										<apptags:input labelCode="BirthRegDto.alreayIssuedCopy"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.alreayIssuedCopy"
											cssClass="hasNumClass form-control" isReadonly="true">
										</apptags:input>
										</c:if>
										<label class="col-sm-2 control-label required-control"><spring:message
											code="BirthRegDto.numberOfCopies" text="Number of Copies" /></label>
									<div class="col-sm-4">
										<form:input class="form-control hasNumber" disabled="${command.saveMode eq 'V'}"
											path="birthRegDto.noOfCopies" id="noOfCopies" 
											maxlength="2" ></form:input>
									</div>
										
										<%-- <apptags:input labelCode="BirthRegDto.numberOfCopies"
											isDisabled="${command.saveMode eq 'V'}"
											path="birthRegDto.noOfCopies" onblur="getAmountOnNoOfCopes()"
											cssClass="hasNumber form-control" isMandatory="true">
										</apptags:input> --%>
									</div>
								<%-- 	<c:if
										test="${command.birthRegDto.chargesStatus eq 'CC' || command.birthRegDto.chargesStatus eq 'CA'}">
										<div class="form-group" id="amountid">
											<apptags:input labelCode="BirthRegDto.amount"
												path="birthRegDto.amount"
												cssClass="hasNumClass form-control" isReadonly="true">
											</apptags:input>
										</div>
									</c:if> --%>							
										</div>
							</div>
						</div>
					</div>

					<c:if
						test="${command.saveMode eq 'V' && not empty command.viewCheckList}">
						<div class="form-group">
							<div class="panel-heading">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class="" href="#birthReg-5" tabindex="-1"> <spring:message
											text="Attached Documents" />
									</a>
								</h4>
							</div>
							<div class="col-sm-12 text-left">
								<div class="table-responsive">
									<table class="table table-bordered table-striped"
										id="attachDocs">
										<tbody>
											<tr>
												<th><label class="tbold"><spring:message
															code="label.checklist.srno" text="Sr No" /></label></th>
												<th><label class="tbold"><spring:message
															code="bnd.documentName" text="Document Type" /></label></th>
												<th><label class="tbold"><spring:message
															code="bnd.documentDesc" text="Document Description" /></label></th>
												<th><spring:message code="birth.view.document"
														text="View Documents" /></th>
											</tr>
											<c:forEach items="${command.viewCheckList}" var="lookUp"
												varStatus="index">
												<tr>
													<td class="text-center"><label>${index.count}</label></td>
													<c:choose>
														<c:when
															test="${userSession.getCurrent().getLanguageId() eq 1}">
															<td class="text-center"><label>${lookUp.doc_DESC_ENGL}</label></td>
														</c:when>
														<c:otherwise>
															<td class="text-center"><label>${lookUp.doc_DESC_Mar}</label></td>
														</c:otherwise>
													</c:choose>
													<td class="text-center"><label>${lookUp.docDescription}</label></td>
													<td align="center">
														<div id="docs_${lk}">
															<apptags:filedownload filename="${lookUp.documentName}"
																filePath="${lookUp.uploadedDocumentPath}"
																actionUrl="BirthCorrectionForm.html?Download">
															</apptags:filedownload>
														</div>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</c:if>

					<c:if test="${command.birthRegDto.statusCheck eq 'A'}">
						<div class="padding-top-10 text-center" id="chekListChargeId">
							<c:if test="${command.saveMode ne 'V'}">
								<button type="button" class="btn btn-success"
									data-toggle="tooltip" data-original-title="" id="proceedId"
									onclick="getChecklistAndCharges(this)">
									<spring:message code="BirthRegDto.proceed" text="Proceed" />
								</button>
							</c:if>
							<c:if test="${command.saveMode ne 'V'}">
								<button type="button" onclick="resetMemberMaster(this);"
									class="btn btn-warning" data-toggle="tooltip"
									data-original-title="" id="resetId">
									<i class="fa padding-left-4" aria-hidden="true"></i>
									<spring:message code="BirthRegDto.reset" />
								</button>
							</c:if>

							<button type="button" class="btn btn-danger " id="backId"
								data-toggle="tooltip" data-original-title=""
								onclick="window.location.href ='BirthCorrectionForm.html'">
								<i class="fa padding-left-4" aria-hidden="true"></i>
								<spring:message code="BirthRegDto.back" />
							</button>
						</div>					

						<c:if test="${not empty command.checkList}">
							<h4>
								<spring:message text="Upload Documents" code="TbDeathregDTO.form.uploadDocuments" />
							</h4>
							<fieldset class="fieldRound">
								<div class="overflow">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped"
											id="BirthCorrTable">
											<tbody>
												<tr>
													<th><label class="tbold"><spring:message
																code="label.checklist.srno" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="bnd.documentName" text="Document Type" /></label></th>
													<th><label class="tbold"><spring:message code="bnd.documentDesc"
													text="Document Description" /></label></th>
													<th><label class="tbold"><spring:message
																code="label.checklist.status" text="Status" /></label></th>
													<th><label class="tbold"><spring:message
																code="bnd.upload.document" text="Upload" /></label></th>
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
														<%-- <td><form:input
														path="checkList[${lk.index}].docDescription" type="text"
														class="form-control alphaNumeric " maxLength="50"
														id="docDescription[${lk.index}]" data-rule-required="true" />
														</td> --%>
														<c:choose>
													<c:when test="${lookUp.docDes ne null}">
														<td><form:select
																path="checkList[${lk.index}].docDescription"
																class="form-control" id="docTypeSelect_${lk.index}">
																<form:option value="">
																	<spring:message code="mrm.select" />
																</form:option>
																<c:set var="baseLookupCode" value="${lookUp.docDes}" />
																<c:forEach items="${command.getLevelData(baseLookupCode)}"
																	var="docLookup">
																	<form:option value="${docLookup.lookUpDesc}">${docLookup.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
															</c:when>
													<c:otherwise>
													<td></td>
													</c:otherwise>
												</c:choose>

														<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
															<td>${lookUp.descriptionType}<spring:message code="bnd.acknowledgement.doc.mand"
																	text="Mandatory" /></td>
														</c:if>

														<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
															<td>${lookUp.descriptionType}<spring:message code="bnd.acknowledgement.doc.opt"
																	text="Optional" /></td>
														</c:if>
														<td>
															<div id="docs_${lk}">
																<apptags:formField fieldType="7" labelCode=""
																	hasId="true" fieldPath="checkList[${lk.index}]"
																	isMandatory="false" showFileNameHTMLId="true"
																	fileSize="CARE_COMMON_MAX_SIZE"
																	maxFileCount="CHECK_LIST_MAX_COUNT"
																	validnFunction="CHECK_LIST_VALIDATION_EXTENSION_TOOLTIP"
																	checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
																	checkListDesc="${docName}" currentCount="${lk.index}" />
															</div>
															<small class="text-blue-2"> <spring:message
																code="bnd.checklist.uploadToolTip"
																text="Upload File upto 1MB and only pdf, doc, docx extension(s) file(s) are allowed" />
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</fieldset>
						</c:if>
					</c:if>
					<form:hidden path="birthRegDto.chargesStatus" id="chargeStatus" />
			<%-- 		<c:if test="${command.birthRegDto.chargesStatus eq 'CA'}">
						<div class="panel panel-default" id="payId">
							<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
						</div>
					</c:if> --%>
					<c:if
						test="${command.birthRegDto.statusCheck eq 'NA' || not empty command.checkList || command.birthRegDto.chargesStatus eq 'CA'}">

						<div class="text-center">
							<c:if test="${command.saveMode ne 'V'}">
								<button type="button" class="btn btn-success"
									data-toggle="tooltip" data-original-title=""
									onclick="saveBirthCorrData(this)">
									<i class="fa padding-left-4" aria-hidden="true"></i>
									<spring:message code="BirthRegDto.save" />
								</button>
							</c:if>
							<c:if test="${command.saveMode ne 'V'}">
								<button type="button" onclick="resetMemberMaster(this);"
									class="btn btn-warning" data-toggle="tooltip"
									data-original-title="" id="resetId">
									<i class="fa padding-left-4" aria-hidden="true"></i>
									<spring:message code="BirthRegDto.reset" />
								</button>
							</c:if>
							<button type="button" class="btn btn-danger " id="backId"
								data-toggle="tooltip" data-original-title=""
								onclick="window.location.href ='BirthCorrectionForm.html'">
								<i class="fa padding-left-4" aria-hidden="true"></i>
								<spring:message code="BirthRegDto.back" />
							</button>
						</div>
					</c:if>
					
					<c:if test="${command.viewMode eq 'V'}">
					<div class="text-center padding-top-10">
					<apptags:backButton url="CitizenHome.html"></apptags:backButton>
					</div>
					</c:if>
					<!-- fetch data after search end -->

				</form:form>
			</div>
		</div>
	</div>
</div>


