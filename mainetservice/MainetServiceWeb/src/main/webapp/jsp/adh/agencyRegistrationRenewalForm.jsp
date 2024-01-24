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
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/adh/agencyRegistrationRenewal.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="js/adh/adhRenewalOwner.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="agency.registration.renewal.form.title"
					text="Renewal Of Advertisement License" />
			</h2>
		</div>

		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message></span>
			</div>
			<form:form action="AgencyRegistrationRenewal.html"
				name="AgencyRegistrationRenewal" id="AgencyRegistrationRenewal"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="licMaxTenureDays" id="licMaxTenureDays" />
				<form:hidden path="licMinTenureDays" id="licMinTenureDays" />
				<form:hidden path="payableFlag" id="payableFlag" />
				<form:hidden path="" id="viewMode" value="${command.viewMode}" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<c:if test="${command.formDisplayFlag eq 'N'}">
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="agency.registration.renewal.label.license.no"
								text="License No." /></label>
						<div class="col-sm-4">
							<form:select path="" id="agencyLicNo"
								class="chosen-select-no-results" data-rule-required="true">
								<form:option value="">
									<spring:message code="adh.select" text="Select"></spring:message>
								</form:option>
								<c:forEach items="${command.masterDtoList}" var="masterDtoList">
									<form:option value="${masterDtoList.agencyLicNo}">${masterDtoList.agencyLicNo} - ${masterDtoList.agencyName}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</c:if>

				<c:if test="${command.formDisplayFlag eq 'Y'}">


					<div class="panel-collapse collapse in" id="Applicant">
						<div class="form-group">
							<label class="col-sm-2 control-label required-control"
								for="applicantTitle"><spring:message
									code="applicantinfo.label.title" /></label>
							<c:set var="baseLookupCode" value="TTL" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="applicantDetailDto.applicantTitle" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="false"
								disabled="true"
								selectOptionLabelCode="applicantinfo.label.select"
								isMandatory="true" />
							<label class="col-sm-2 control-label required-control"
								for="firstName"><spring:message
									code="applicantinfo.label.firstname" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text"
									class="form-control preventSpace hasCharacter"
									readonly="true" maxlength="200"
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
									readonly="true" maxlength="200"
									path="applicantDetailDto.applicantMiddleName" id="middleName"></form:input>
							</div>
							<label class="col-sm-2 control-label required-control"
								for="lastName"><spring:message
									code="applicantinfo.label.lastname" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text"
									class="form-control preventSpace hasCharacter"
									readonly="true" maxlength="200"
									path="applicantDetailDto.applicantLastName" id="lastName"
									data-rule-required="true"></form:input>
							</div>
						</div>

						<div class="form-group">

							<label class="col-sm-2 control-label required-control"><spring:message
									code="applicantinfo.label.mobile" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control hasMobileNo"
									readonly="true" maxlength="10"
									path="applicantDetailDto.mobileNo" id="mobileNo"
									data-rule-required="true"></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message
									code="applicantinfo.label.email" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text"
									class="form-control preventSpace" maxlength="200"
									readonly="true" path="applicantDetailDto.emailId"
									id="emailId"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="address.line1" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text"
									class="form-control preventSpace" readonly="true"
									path="applicantDetailDto.areaName" id="areaName"
									maxlength="1000" data-rule-required="true"></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message
									code="address.line2" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text"
									class="form-control preventSpace" readonly="true"
									maxlength="50" path="applicantDetailDto.villageTownSub"
									id="villTownCity"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="address.line3" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text"
									class="form-control preventSpace" readonly="true"
									maxlength="300" path="applicantDetailDto.roadName"
									id="roadName"></form:input>
							</div>
							<label class="col-sm-2 control-label required-control"><spring:message
									code="applicantinfo.label.pincode" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control hasNumber"
									readonly="true" path="applicantDetailDto.pinCode"
									id="pinCode" maxlength="6" data-rule-required="true"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="applicantinfo.label.aadhaar" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control hasNumber"
									readonly="true" path="applicantDetailDto.aadharNo"
									id="aadharNo" maxlength="12" data-mask="9999 9999 9999" />
							</div>

						</div>
					</div>
					<div class="accordion-toggle">

						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#a1"><spring:message
									code="agency.detail" text="Agency Details" /></a>
						</h4>
						<div class="panel-collapse collapse in" id="a1">
							<div class="panel-body">
								<div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
											code="adh.new.advertisement.agencyType" text="Agency Type" /></label>
									 <div class="col-sm-4"> 
										<c:set var="baseLookupCode" value="AOT" />
										 <form:select path="agencyRequestDto.masterDto.trdFtype"
											onchange="getOwnerTypeDetails()"
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
										<%-- <apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
							    path="agencyRequestDto.masterDto.trdFtype"
								cssClass="form-control" hasChildLookup="false" hasId="true"
								showAll="false" selectOptionLabelCode="property.sel.optn.ownerType"
								disabled="true" isMandatory="true" 
								showOnlyLabel="Agency Type" /> --%>
									 </div> 

								 </div>
								<div id="owner"></div>
							</div>
							<div class="form-group">

								<label class="col-sm-2 control-label required-control" for=""><spring:message
										code="agency.category" text="Agency Category" /></label>
								<c:set var="baseLookupCode" value="ADC" />
								<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="agencyRequestDto.masterDto.agencyCategory"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false" selectOptionLabelCode="adh.select"
									isMandatory="true" showOnlyLabel="Agency Category"
									disabled="true" />
								<apptags:input labelCode="agency.name"
									path="agencyRequestDto.masterDto.agencyName" maxlegnth="400"
									isMandatory="true" isDisabled="true"></apptags:input>
							</div>

							<div class="form-group">
								<%-- <apptags:input labelCode="agency.owner"
									path="agencyRequestDto.masterDto.agencyOwner" cssClass=""
									isMandatory="true" isDisabled="true"></apptags:input>
								<apptags:input labelCode="agency.address"
									path="agencyRequestDto.masterDto.agencyAdd" maxlegnth="400"
									isMandatory="true" isDisabled="true"></apptags:input> --%>
							</div>

							<%-- <div class="form-group">
								<apptags:input labelCode="agency.mobile.no"
									path="agencyRequestDto.masterDto.agencyContactNo"
									cssClass="hasMobileNo" isMandatory="true" isDisabled="true"></apptags:input>
								<apptags:input labelCode="agency.emailid"
									path="agencyRequestDto.masterDto.agencyEmail" maxlegnth="50"
									isMandatory="true" cssClass="hasemailclass" isDisabled="true"></apptags:input>
							</div> --%>

							<div class="form-group">
								<%-- <apptags:input labelCode="agency.uid.no"
									path="agencyRequestDto.masterDto.uidNo" isDisabled="true"></apptags:input> --%>
								<apptags:input labelCode="agency.address"
									path="agencyRequestDto.masterDto.agencyAdd" maxlegnth="400"
									cssClass="preventSpace" isMandatory="true" isDisabled="true"></apptags:input>
								<apptags:input labelCode="agency.gst.no"
									path="agencyRequestDto.masterDto.gstNo" maxlegnth="15"
									cssClass="hasNumber" isDisabled="true"></apptags:input>
							</div>


							<%-- <div class="form-group">
								<apptags:input labelCode="agency.pan"
									path="agencyRequestDto.masterDto.panNumber" maxlegnth="20"
									isDisabled="true"></apptags:input>
							</div> --%>

						</div>
					</div>

					<div class="accordion-toggle">
						<h4 class="margin-top-0 margin-bottom-10 panel-title">
							<a data-toggle="collapse" href="#a2"><spring:message
									code="agency.licence.title" text="Licence Details" /></a>
						</h4>
						<div class="panel-collapse collapse in" id="a2">
							<div class="form-group">
								<apptags:date labelCode="agency.licence.from.date"
									datePath="agencyRequestDto.masterDto.agencyLicFromDate"
									fieldclass="datepicker" isMandatory="true"></apptags:date>

								<apptags:date labelCode="agency.licence.to.date"
									datePath="agencyRequestDto.masterDto.agencyLicToDate"
									fieldclass="datepicker" isMandatory="true"></apptags:date>
							</div>

						</div>
					</div>


					<c:if test="${not empty command.checkList}">

						<div class="panel-group accordion-toggle">
							<h4 class="margin-top-0 margin-bottom-10 panel-title">
								<a data-toggle="collapse" href="#a3"><spring:message code=""
										text="Upload Attachment" /></a>
							</h4>
							<div class="panel-collapse collapse in" id="a3">

								<div class="panel-body">

									<div class="overflow margin-top-10 margin-bottom-10">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped">
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
				</c:if>

				<c:if test="${command.payableFlag eq 'Y' }">
					<div class="form-group margin-top-10">
						<label class="col-sm-2 control-label"><spring:message
								code="water.field.name.amounttopay" /></label>
						<div class="col-sm-4">
							<input type="text" class="form-control text-right"
								value="${command.offlineDTO.amountToShow}" maxlength="12"
								disabled="true"></input> <a
								class="fancybox fancybox.ajax text-small text-info"
								href="AgencyRegistration.html?showChargeDetails"><spring:message
									code="water.lable.name.chargedetail" /> <i
								class="fa fa-question-circle "></i></a>
						</div>
					</div>
					<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
				</c:if>

				<c:if test="${command.formDisplayFlag eq 'N'}">
					<div class="text-center margin-bottom-10">
						<button type="button" class="btn btn-blue-2" title="Search"
							onclick="searchAdvertiser()">
							<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
							<spring:message code="adh.search" text="Search"></spring:message>
						</button>
						<button type="button" class="btn btn-warning" title="Reset"
							onclick="window.location.href='AgencyRegistrationRenewal.html'">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="adh.reset" text="Reset"></spring:message>
						</button>
						<button type="button" class="btn btn-danger" data-toggle="tooltip"
							data-original-title="Back"
							onclick="window.location.href='AdminHome.html'">
							<i class="fa fa-chevron-circle-left padding-right-5"
								aria-hidden="true"></i>
							<spring:message code="adh.back" text="Back"></spring:message>
						</button>
					</div>
				</c:if>

				<c:if test="${command.formDisplayFlag eq 'Y'}">
					<div class="text-center margin-bottom-10">
						<c:if
							test="${command.applicationchargeApplFlag eq 'Y' || command.checkListApplFlag eq 'Y'}">
							<button type="button" class="btn btn-success"
								id="confirmToProceed" onclick="getChecklistAndCharges(this)">
								<spring:message code="adh.confirm.to.proceed"
									text="Confirm To Proceed"></spring:message>
							</button>

						</c:if>
						<c:if
							test="${command.applicationchargeApplFlag eq 'N' && command.checkListApplFlag eq 'N'}">
							<button type="button" class="btn btn-success"
								data-toggle="tooltip" data-original-title="Save"
								onclick="saveAgencyRegistrationRenewal(this)">
								<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
								<spring:message code="adh.save" text="Save"></spring:message>
							</button>
						</c:if>
						<button type="button" class="btn btn-danger" data-toggle="tooltip"
							data-original-title="Back"
							onclick="window.location.href='AgencyRegistrationRenewal.html'">
							<i class="fa fa-chevron-circle-left padding-right-5"
								aria-hidden="true"></i>
							<spring:message code="adh.back" text="Back"></spring:message>
						</button>
					</div>
				</c:if>
			</form:form>

		</div>
	</div>
</div>
