<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="js/adh/agencyRegistration.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="js/adh/adhOwner.js"></script>
<!-- End JSP Necessary Tags -->
<div id="agencyAcknowledgement1">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="agency.registration.form.title"
						text="Application For Agency Registration" />
				</h2>
			</div>
			<div class="widget-content padding">

				<div class="mand-label clearfix">
					<span><spring:message code="adh.mand" text="Field with"></spring:message><i
						class="text-red-1">*</i> <spring:message code="adh.mand.field"
							text="is mandatory"></spring:message></span>
				</div>
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<ul>
						<li><label id="errorId"></label></li>
					</ul>
				</div>

				<form:form action="AgencyRegistrationForm.html"
					name="AgencyRegistrationForm" id="AgencyRegistrationForm"
					class="form-horizontal">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />

		
					<form:hidden path="licMaxTenureDays" id="licMaxTenureDays" />
					<form:hidden path="payableFlag" id="payableFlag" />
					<form:hidden path="" id="viewMode" value="${command.viewMode}" />
					<div class="accordion-toggle">

						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#Applicant"><spring:message
									code="" text="Applicant Details" /></a>
						</h4>
						<div class="panel-collapse collapse in" id="Applicant">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="applicantTitle"><spring:message
										code="applicantinfo.label.title" /></label>
								<c:set var="baseLookupCode" value="TTL" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="applicantDetailDto.applicantTitle"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false" disabled="${command.saveMode eq 'VIEW'}"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" />
								<label class="col-sm-2 control-label required-control"
									for="firstName"><spring:message
										code="applicantinfo.label.firstname" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control preventSpace hasCharacter"
										readonly="${command.saveMode eq 'VIEW'}" maxlength="200"
										path="applicantDetailDto.applicantFirstName" id="firstName"
										data-rule-required="true"></form:input>
								</div>
							</div>


							<div class="form-group">
								<label class="col-sm-2 control-label" for="middleName"><spring:message
										code="applicantinfo.label.middlename" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control preventSpace hasCharacter"
										readonly="${command.saveMode eq 'VIEW'}" maxlength="200"
										path="applicantDetailDto.applicantMiddleName" id="middleName"></form:input>
								</div>
								<label class="col-sm-2 control-label required-control"
									for="lastName"><spring:message
										code="applicantinfo.label.lastname" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control preventSpace hasCharacter"
										readonly="${command.saveMode eq 'VIEW'}" maxlength="200"
										path="applicantDetailDto.applicantLastName" id="lastName"
										data-rule-required="true"></form:input>
								</div>
							</div>

							<div class="form-group">

								<label class="col-sm-2 control-label required-control"><spring:message
										code="applicantinfo.label.mobile" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control hasMobileNo" readonly="${command.saveMode eq 'VIEW'}"
										maxlength="10" path="applicantDetailDto.mobileNo"
										id="mobileNo" data-rule-required="true"></form:input>
								</div>
								<label class="col-sm-2 control-label"><spring:message
										code="applicantinfo.label.email" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control preventSpace" maxlength="200"
										readonly="${command.saveMode eq 'VIEW'}" path="applicantDetailDto.emailId"
										id="emailId"></form:input>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="address.line1" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control preventSpace" readonly="${command.saveMode eq 'VIEW'}"
										path="applicantDetailDto.areaName" id="areaName"
										maxlength="1000" data-rule-required="true"></form:input>
								</div>
								<label class="col-sm-2 control-label"><spring:message
										code="address.line2" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control preventSpace" readonly="${command.saveMode eq 'VIEW'}"
										maxlength="50" path="applicantDetailDto.villageTownSub"
										id="villTownCity"></form:input>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message
										code="address.line3" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text"
										class="form-control preventSpace" readonly="${command.saveMode eq 'VIEW'}"
										maxlength="300" path="applicantDetailDto.roadName"
										id="roadName"></form:input>
								</div>
								<label class="col-sm-2 control-label required-control"><spring:message
										code="applicantinfo.label.pincode" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control hasNumber"
										readonly="${command.saveMode eq 'VIEW'}" path="applicantDetailDto.pinCode"
										id="pinCode" maxlength="6" data-rule-required="true"></form:input>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message
										code="applicantinfo.label.aadhaar" /></label>
								<div class="col-sm-4">
									<form:input name="" type="text" class="form-control hasNumber"
										readonly="${command.saveMode eq 'VIEW'}" path="applicantDetailDto.aadharNo"
										id="aadharNo" maxlength="12" data-mask="9999 9999 9999" />
								</div>

							</div>
						</div>
					</div>

					<div class="accordion-toggle">

						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#a1"><spring:message
									code="agency.detail" text="Agency Details" /></a>
						</h4>
						<div class="panel-collapse collapse" id="a1">
						<%-- 	<div class="form-group">

								<label class="col-sm-2 control-label required-control" for=""><spring:message
										code="agency.category" text="Agency Category" /></label>
								<c:set var="baseLookupCode" value="ADC" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="agencyRequestDto.masterDto.agencyCategory"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false" selectOptionLabelCode="adh.select"
									isMandatory="true" showOnlyLabel="Agency Category" />
									
									<div class="col-sm-4">
									<c:set value="${command.agenctCategoryLookUp}"
										var="agenctCategoryLookUp"></c:set>

									<form:select path="agencyRequestDto.masterDto.agencyCategory"
										id="agencyCategoryId" class="chosen-select-no-results"
										disabled="true">
										<form:option value="${agenctCategoryLookUp.lookUpId}">${agenctCategoryLookUp.lookUpDesc}</form:option>
									</form:select>


								</div>
								
								<apptags:input labelCode="agency.name"
									path="agencyRequestDto.masterDto.agencyName" maxlegnth="200"
									cssClass="hasCharacter preventSpace" isMandatory="true"></apptags:input>
							</div> --%>
							
							<div class="panel-body">
											<div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
							        code="adh.new.advertisement.agencyType" text="Agency Type" /></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="AOT" />
										<form:select path="agencyRequestDto.masterDto.trdFtype"
											onchange="getOwnerTypeDetailsFoForm()"
											class="form-control mandColorClass" id="trdFtype"
											disabled="${command.viewMode eq 'V' ? true : false }"
											data-rule-required="true">
											<form:option value="">
												<spring:message code="property.sel.optn.ownerType" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>
								 <div id="owner">
								 	
								 </div> 
								</div>
							
							<div class="form-group">

								<label class="col-sm-2 control-label required-control" for=""><spring:message
										code="agency.category" text="Agency Category" /></label>


								<div class="col-sm-4">
									<c:set value="${command.agenctCategoryLookUp}"
										var="agenctCategoryLookUp"></c:set>

									<form:select path="agencyRequestDto.masterDto.agencyCategory"
										id="agencyCategoryId" class="chosen-select-no-results"
										disabled="true">
										<form:option value="${agenctCategoryLookUp.lookUpId}">${agenctCategoryLookUp.lookUpDesc}</form:option>
									</form:select>


								</div>

								<apptags:input labelCode="agency.name" isDisabled="${command.saveMode eq 'VIEW'}"
									path="agencyRequestDto.masterDto.agencyName" maxlegnth="200"
									cssClass="hasCharacter preventSpace" isMandatory="true"></apptags:input>
							</div>

							<div class="form-group">
								<%-- <apptags:input labelCode="agency.owner"
									path="agencyRequestDto.masterDto.agencyOwner" isDisabled="${command.saveMode eq 'VIEW'}"
									cssClass="hasCharacter preventSpace" isMandatory="true"
									maxlegnth="200"></apptags:input>
								<apptags:input labelCode="agency.address" isDisabled="${command.saveMode eq 'VIEW'}"
									path="agencyRequestDto.masterDto.agencyAdd" maxlegnth="400"
									cssClass="preventSpace" isMandatory="true"></apptags:input> --%>
							</div>

							<%-- <div class="form-group">
								<apptags:input labelCode="agency.mobile.no" isDisabled="${command.saveMode eq 'VIEW'}"
									path="agencyRequestDto.masterDto.agencyContactNo"
									cssClass="hasMobileNo" isMandatory="true"></apptags:input>
								<apptags:input labelCode="agency.emailid" isDisabled="${command.saveMode eq 'VIEW'}"
									path="agencyRequestDto.masterDto.agencyEmail" maxlegnth="50"
									isMandatory="true" cssClass="hasemailclass preventSpace"></apptags:input>
							</div> --%>

							<div class="form-group">
								<%-- <apptags:input labelCode="agency.uid.no" isDisabled="${command.saveMode eq 'VIEW'}"
									path="agencyRequestDto.masterDto.uidNo" maxlegnth="12"
									cssClass="hasNumber"></apptags:input> --%>
									
								<apptags:input labelCode="agency.address" isDisabled="${command.saveMode eq 'VIEW'}"
									path="agencyRequestDto.masterDto.agencyAdd" maxlegnth="400"
									cssClass="preventSpace" isMandatory="true"></apptags:input> 	
								<apptags:input labelCode="agency.gst.no" isDisabled="${command.saveMode eq 'VIEW'}"
									path="agencyRequestDto.masterDto.gstNo" maxlegnth="15"
									cssClass="hasNumber"></apptags:input>
							</div>


							<%-- <div class="form-group">
								<apptags:input labelCode="agency.pan" isDisabled="${command.saveMode eq 'VIEW'}"
									path="agencyRequestDto.masterDto.panNumber" maxlegnth="20"
									cssClass="preventSpace"></apptags:input>
							</div> --%>

						</div>
					</div>

					<div class="accordion-toggle">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#a2"><spring:message
									code="agency.licence.title" text="Licence Details" /></a>
						</h4>
						<div class="panel-collapse collapse" id="a2">
							<div class="form-group">
								<apptags:date labelCode="agency.licence.from.date"  readonly="${command.saveMode eq 'VIEW'}"
									datePath="agencyRequestDto.masterDto.agencyLicFromDate"
									fieldclass="datepicker" isMandatory="true"></apptags:date>

								<apptags:date labelCode="agency.licence.to.date" readonly="${command.saveMode eq 'VIEW'}"
									datePath="agencyRequestDto.masterDto.agencyLicToDate"
									fieldclass="datepicker" isMandatory="true"></apptags:date>
							</div>
						</div>
					</div>

					<c:if test="${not empty command.checkList && command.saveMode ne 'VIEW' }">

						<div class="panel-group accordion-toggle">
							<h4 class="margin-top-0 margin-bottom-10 panel-title">
								<a data-toggle="collapse" href="#a3"><spring:message code=""
										text="Upload Attachment" /></a>
							</h4>
							<div class="panel-collapse collapse in" id="a3">

								<div class="panel-body">

									<div class="overflow margin-top-10 margin-bottom-10">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped"
												id="documentList">
												<tbody>
													<tr>
														<th><spring:message code="adh.sr.no" text="Sr No" /></th>
														<th><spring:message code="adh.document.name"
																text="Document Name" /></th>
														<th><spring:message code="adh.status" text="Status" /></th>
														<th width="500"><spring:message code="adh.upload"
																text="Upload" /></th>
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
																<td><spring:message code="adh.doc.mandatory" /></td>
															</c:if>
															<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																<td><spring:message code="adh.doc.optional" /></td>
															</c:if>
															<td>
																<div id="docs_${lk}" class="">
																	<apptags:formField fieldType="7" labelCode=""
																		hasId="true" fieldPath="checkList[${lk.index}]"
																		isMandatory="false" showFileNameHTMLId="true"
																		fileSize="BND_COMMOM_MAX_SIZE"
																		maxFileCount="CHECK_LIST_MAX_COUNT"
																		validnFunction="CHECK_LIST_VALIDATION_EXTENSION_HELPDOC"
																		currentCount="${lk.index}" />
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

					<c:if test="${command.payableFlag eq 'Y' && command.saveMode ne 'VIEW'  }">
						<div class="form-group margin-top-10">
							<label class="col-sm-2 control-label"> <spring:message
									code="water.field.name.amounttopay" />
							</label>
							<div class="col-sm-4">
								<input type="text" class="form-control"
									value="${command.offlineDTO.amountToShow}"
									data-rule-required="true" data-rule-maxlength="8"
									readonly="readonly" /> <a
									class="fancybox fancybox.ajax text-small text-info"
									href="AgencyRegistrationForm.html?showChargeDetails"> <spring:message
										code="water.field.name.amounttopay" /> <i
									class="fa fa-question-circle "></i>
								</a>
							</div>
						</div>
                     <!-- Defect #77088- for both online and offline payment -->
						<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
						
					<%-- <apptags:payment></apptags:payment> --%>
					</c:if>


					<div class="padding-top-10 text-center">

						<c:if
							test="${command.applicationchargeApplFlag eq 'Y' || command.checkListApplFlag eq 'Y' && command.saveMode ne 'VIEW' }">
							<button type="button" class="btn btn-success"
								id="confirmToProceed" onclick="getChecklistAndCharges(this)">
								<spring:message code="adh.confirm.to.proceed"
									text="Confirm To Proceed"></spring:message>
							</button>


						</c:if>

						<c:if
							test="${command.applicationchargeApplFlag eq 'N' && command.checkListApplFlag eq 'N' && command.saveMode ne 'VIEW' }">
							<button type="button" class="btn btn-success" id="save"
								onclick="saveAgencyRegistrationForm(this)">
								<spring:message code="adh.save" text="Save"></spring:message>
							</button>
						</c:if>
						<c:if test="${command.saveMode ne 'VIEW'}">
						<button type="button" class="btn btn-warning"
							onclick="window.location.href='AgencyRegistrationForm.html'"
							id="reset">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="adh.reset" text="Reset"></spring:message>
						</button>
						</c:if>

						<button type="button" class="btn btn-danger" id="back"
							onclick="window.location.href='CitizenHome.html'">
							<spring:message code="adh.back" text="Back"></spring:message>
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
<script>
	var agencyLicFromDate = $('#agencyLicFromDate').val();
	var agencyLicToDate = $('#agencyLicToDate').val();
	
	if (agencyLicFromDate) {
		$('#agencyLicFromDate').val(agencyLicFromDate.split(' ')[0]);
	}
	if (agencyLicToDate) {
		$('#agencyLicToDate').val(agencyLicToDate.split(' ')[0]);
	}
	$(document).ready(function() {
		$('.fancybox').fancybox();
	});
</script>