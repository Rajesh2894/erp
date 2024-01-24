<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="js/adh/advertiserCancellationForm.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="js/mainet/dashboard/moment.min.js"></script>
<script src="js/mainet/dashboard/fullcalendar.min.js"></script>
<!-- End JSP Necessary Tags -->
<!-- ABM3223---User Story 112154  -->

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="advertiser.cancellation.titile" />
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

			<form:form action="AdvertisercancellationForm.html"
				name="AdvertisercancellationForm" id="AdvertisercancellationForm"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="formDisplayFlag" id="formDisplayFlag" />
					<%-- <form:hidden path="licMaxTenureDays" id="licMaxTenureDays" />
					<form:hidden path="licMinTenureDays" id="licMinTenureDays" />  --%>
				 <c:if test="${command.formDisplayFlag eq 'N'}"> 
                                         <%-- <div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
												code="advertiser.cancellation.licNo" text="agencyLic No." /></label>
										<div class="col-sm-4">

											<form:input path="agencyRequestDto.masterDto.agencyLicNo" type="text"
												class="form-control" id="agencyLicNo"/>
												
												 <form:input path="agencyRequestDto.masterDto.agencyLicNo" type="text"
												class="form-control" id="agencyLicNo" onchange="searchAdvertiserNameByLicNo(this)" /> 
												
											
										</div>
											</div> --%>
												<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="agency.registration.renewal.label.license.no"
									text="License No." /></label>
							<div class="col-sm-4">
								<form:select path="" id="agencyLicNo"
									class="chosen-select-no-results" data-rule-required="true">
									<form:option value="0" selected="selected">
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
									path="applicantDetailDto.applicantTitle"
									cssClass="form-control" hasChildLookup="false" hasId="true"
									showAll="false" disabled="true"
									selectOptionLabelCode="applicantinfo.label.select"
									isMandatory="true" />
								
								<apptags:input labelCode="applicantinfo.label.firstname" maxlegnth="200"
										path="applicantDetailDto.applicantFirstName" cssClass=""
										isMandatory="false" isDisabled="true"></apptags:input>
							</div>

							<div class="form-group">
								<apptags:input labelCode="applicantinfo.label.middlename" maxlegnth="200"
										path="applicantDetailDto.applicantMiddleName" cssClass=""
										isMandatory="true" isDisabled="true"></apptags:input>
										
								<apptags:input labelCode="applicantinfo.label.lastname" maxlegnth="200"
										path="applicantDetailDto.applicantLastName" cssClass=""
										isMandatory="true" isDisabled="true"></apptags:input>
							</div>

							<div class="form-group">

								
								<apptags:input labelCode="applicantinfo.label.mobile" maxlegnth="10"
										path="applicantDetailDto.mobileNo" cssClass="hasMobileNo"
										isMandatory="true" isDisabled="true"></apptags:input>
										
								
								
								<apptags:input labelCode="applicantinfo.label.email" maxlegnth="50"
										path="applicantDetailDto.emailId" cssClass="hasemailclass"
										isMandatory="false" isDisabled="true"></apptags:input>
										
							</div>

							<div class="form-group">
								
								<apptags:input labelCode="address.line1" maxlegnth="1000"
										path="applicantDetailDto.areaName" cssClass=""
										isMandatory="true" isDisabled="true"></apptags:input>
								
								
								<apptags:input labelCode="address.line2" maxlegnth="50"
										path="applicantDetailDto.villageTownSub" cssClass=""
										isMandatory="false" isDisabled="true"></apptags:input>
							</div>

							<div class="form-group">
								
								<apptags:input labelCode="address.line3" maxlegnth="300"
										path="applicantDetailDto.roadName" cssClass=""
										isMandatory="false" isDisabled="true"></apptags:input>
								
								<apptags:input labelCode="applicantinfo.label.pincode" maxlegnth="6"
										path="applicantDetailDto.pinCode" cssClass=""
										isMandatory="true" isDisabled="true"></apptags:input>
							</div>

							<div class="form-group">

								<apptags:input labelCode="applicantinfo.label.aadhaar" maxlegnth="12"
										path="applicantDetailDto.aadharNo" cssClass=""
										isMandatory="false" isDisabled="true"></apptags:input>

							</div>
							
						</div>
						<div class="accordion-toggle">

							<h4 class="margin-top-0 margin-bottom-10 panel-title">
								<a data-toggle="collapse" href="#a1"><spring:message
										code="agency.detail" text="Agency Details" /></a>
							</h4>
							<div class="panel-collapse collapse" id="a1">
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
									<apptags:input labelCode="agency.owner"
										path="agencyRequestDto.masterDto.agencyOwner" cssClass=""
										isMandatory="true" isDisabled="true"></apptags:input>
									<apptags:input labelCode="agency.address"
										path="agencyRequestDto.masterDto.agencyAdd" maxlegnth="400"
										isMandatory="true" isDisabled="true"></apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="agency.mobile.no"
										path="agencyRequestDto.masterDto.agencyContactNo"
										cssClass="hasMobileNo" isMandatory="true" isDisabled="true"></apptags:input>
									<apptags:input labelCode="agency.emailid"
										path="agencyRequestDto.masterDto.agencyEmail" maxlegnth="50"
										isMandatory="true" cssClass="hasemailclass" isDisabled="true"></apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="agency.uid.no"
										path="agencyRequestDto.masterDto.uidNo" isDisabled="true"></apptags:input>
									<apptags:input labelCode="agency.gst.no"
										path="agencyRequestDto.masterDto.gstNo" maxlegnth="15"
										cssClass="hasNumber" isDisabled="true"></apptags:input>
								</div>


								<div class="form-group">
									<apptags:input labelCode="agency.pan"
										path="agencyRequestDto.masterDto.panNumber" maxlegnth="20"
										isDisabled="true"></apptags:input>
								</div>

							</div>
						</div>
						<div class="accordion-toggle">
							<h4 class="margin-top-0 margin-bottom-10 panel-title">
								<a data-toggle="collapse" href="#a2"><spring:message
										code="agency.licence.title" text="Licence Details" /></a>
							</h4>
							<div class="panel-collapse collapse in" id="a2">
								<div class="form-group">
									<apptags:date labelCode="agency.licence.from.date" readonly="${command.saveMode eq 'VIEW'}"
										datePath="agencyRequestDto.masterDto.agencyLicFromDate"
										fieldclass="datepicker" isMandatory="true"></apptags:date>

									<apptags:date labelCode="agency.licence.to.date" readonly="${command.saveMode eq 'VIEW'}"
										datePath="agencyRequestDto.masterDto.agencyLicToDate"
										fieldclass="datepicker" isMandatory="true"></apptags:date>
								</div>

							</div>
						</div>

						 <div class="accordion-toggle">
							<h4 class="margin-top-0 margin-bottom-10 panel-title">
								<a data-toggle="collapse" href="#a2"><spring:message
										code="agency.licence.title" text="Licence Details" /></a>
							</h4>
							<div class="panel-collapse collapse in" id="a2">
								<div class="form-group">
										<apptags:date labelCode="advertiser.cancellation.date"
						               datePath="agencyRequestDto.masterDto.cancellationDate" fieldclass="datepicker"
						                isMandatory="true"></apptags:date>

										<apptags:input labelCode="advertiser.cancellation.reason"
						      cssClass="hasCharacter" path="agencyRequestDto.masterDto.cancellationReason"
						       isMandatory="true"></apptags:input>
								</div>

							</div>
						</div>

						<c:if test="${not empty command.checkList && command.saveMode ne 'VIEW' }">

							<div class="panel-group accordion-toggle">
								<h4 class="margin-top-0 margin-bottom-10 panel-title">
									<a data-toggle="collapse" href="#a3"><spring:message
											code="" text="Upload Attachment" /></a>
								</h4>
								<div class="panel-collapse collapse in" id="a3">

									<div class="panel-body">

										<div class="overflow margin-top-10 margin-bottom-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped"
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
				</c:if>  
					
					<c:if test="${command.payableFlag eq 'Y' && command.saveMode ne 'VIEW' }">
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
									href="EstateBooking.html?showChargeDetails"> <spring:message
										code="water.field.name.amounttopay" /> <i
									class="fa fa-question-circle "></i>
								</a>
							</div>
						</div>
                    
						<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
						
						<%-- <apptags:payment></apptags:payment> --%>
					</c:if>
					
					 <c:if test="${command.formDisplayFlag eq 'N' && command.saveMode ne 'VIEW' }">
						 <div class="text-center margin-bottom-10">
							<button type="button" class="btn btn-blue-2" title="Search"
								onclick="searchAdvertiser()">
								<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
								<spring:message code="adh.search" text="Search"></spring:message>
							</button>
							<button type="button" class="btn btn-warning" title="Reset"
								onclick="window.location.href='AdvertisercancellationForm.html'">
								<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
								<spring:message code="adh.reset" text="Reset"></spring:message>
							</button>
							<button type="button" class="btn btn-danger"
								data-toggle="tooltip" data-original-title="Back"
								onclick="window.location.href='CitizenHome.html'">
								<i class="fa fa-chevron-circle-left padding-right-5"
									aria-hidden="true"></i>
								<spring:message code="adh.back" text="Back"></spring:message>
							</button>
						</div> 
					 </c:if> 

					<c:if test="${command.formDisplayFlag eq 'Y'}"> 
						<div class="text-center margin-bottom-10">
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
								<button type="button" class="btn btn-success"
									data-toggle="tooltip" data-original-title="Save"
									onclick="saveAgencyRegistrationCancellation(this)">
									<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
									<spring:message code="adh.save" text="Save"></spring:message>
								</button>
							</c:if>
							<c:set var = "backUrl" scope = "session" value = "AdvertisercancellationForm.html"/>
							<c:if test="${command.saveMode eq 'VIEW'}">
								<c:set var = "backUrl" scope = "session" value = "CitizenHome.html"/>
							</c:if>
							<button type="button" class="btn btn-danger"
								data-toggle="tooltip" data-original-title="Back"
								onclick="window.location.href='${backUrl}'">
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

