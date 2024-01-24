<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/adh/renewalAdvertisementApplication.js"></script>
<script type="text/javascript" src="js/adh/newAdvertisementDetails.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.min.js"></script>
<script src="js/mainet/dashboard/moment.min.js"></script>
<script src="js/mainet/dashboard/fullcalendar.min.js"></script>


<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Advertisement Of Renewal Application " />
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
			<form:form method="POST"
				action="RenewalAdvertisementApplication.html"
				class="form-horizontal" id="renewalAdvertisementApplicationForm"
				name="renewalAdvertisementApplicationForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="licMaxTenureDays" id="licMaxTenureDays" />
				<form:hidden path="saveMode" id="saveMode" />
				<%-- <form:input path="licMaxTenure" id="licMaxTenure" /> --%>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">

						<!-- ------------------------------------ Applicant Information Start ---------------------------- -->

						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse"
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="adh.new.advertisement.applicant.information"
										text="Applicant Information" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div id="ApplicantInformation">
									<jsp:include page="/jsp/adh/applicantInformation.jsp"></jsp:include>
								</div>
							</div>
						</div>
						<!-- ------------------------------------ Applicant Information End ---------------------------- -->

						<!-- ------------------------------------ Applicant Details Start ---------------------------- -->

						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse"
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="adh.new.advertisement.applicant.details"
										text="Applicant Details" />
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<form:hidden id="hideAgnId" path=""
									value="${command.advertisementReqDto.advertisementDto.agencyId}" />

								<div id="renewalAdvDetails">
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="adh.new.advertisement.application.category"
												text="Advertiser Category" /></label>
										<div class="col-sm-4">
											<form:select
												path="advertisementReqDto.advertisementDto.appCategoryId"
												cssClass="form-control chosen-select-no-results"
												id="advertiseCategory" onchange="getAgencyDetails();">
												<form:option value="">
													<spring:message code="adh.select" text="Select" />
												</form:option>
												<c:forEach items="${command.getLevelData('ADC')}"
													var="licenseType">
													<form:option value="${licenseType.lookUpId}"
														code="${licenseType.lookUpCode}">${licenseType.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
										<label class="col-sm-2 control-label required-control"><spring:message
												code="adh.new.advertisement.license.type"
												text="License Type" /></label>
										<div class="col-sm-4">
											<form:select
												path="advertisementReqDto.advertisementDto.licenseType"
												onchange="getLicType()"
												cssClass="form-control chosen-select-no-results"
												id="licenseType">
												<form:option value="">
													<spring:message code="adh.select" text="Select" />
												</form:option>
												<c:forEach items="${command.getLevelData('LIT')}"
													var="licenseType">
													<form:option value="${licenseType.lookUpId}"
														code="${licenseType.lookUpCode}">${licenseType.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="adh.new.advertisement.application.license.from.date"
												text="License From Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input
													path="advertisementReqDto.advertisementDto.licenseFromDate"
													id="licenseFromDate" readonly="true"
													class="form-control mandColorClass datePicker"
													maxlength="10" onblur="isFromDate();" />
												<label class="input-group-addon" for="licenseFromDate"><i
													class="fa fa-calendar"></i><span class="hide"> <spring:message
															code="" text="icon" /></span><input type="hidden"
													id=licenseFromDate></label>
											</div>
											<span id="fromDate"
												style="padding-left: 5px; padding-bottom: 3px; font-size: 15px; font-family: serif;"></span>
										</div>
										<label class="col-sm-2 control-label required-control"><spring:message
												code="adh.new.advertisement.application.license.to.date"
												text="License To Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input
													path="advertisementReqDto.advertisementDto.licenseToDate"
													id="licenseToDate" onblur="isToDate();"
													class="form-control mandColorClass datePicker"
													maxlength="10" />
												<label class="input-group-addon" for="licenseToDate"><i
													class="fa fa-calendar"></i><span class="hide"> <spring:message
															code="" text="icon" /></span><input type="hidden"
													id=licenseToDate></label>
											</div>
											<span id="toDate"
												style="padding-left: 5px; padding-bottom: 3px; font-size: 15px; font-family: serif;"></span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="adh.new.advertisement.location.type"
												text="Location Type" /></label>
										<div class="col-sm-4">
											<form:select
												path="advertisementReqDto.advertisementDto.locCatType"
												cssClass="form-control chosen-select-no-results"
												id="locCatType">
												<form:option value="">
													<spring:message code="adh.select" text="Select" />
												</form:option>
												<form:option value="E">
													<spring:message
														code="adh.new.advertisement.existing.location"
														text="Existing Location" />
												</form:option>
												<form:option value="N">
													<spring:message code="adh.new.advertisement.new.location"
														text="New Location" />
												</form:option>
											</form:select>
										</div>
										<label class="col-sm-2 control-label required-control "><spring:message
												code="adh.new.advertisement.advertiser.name"
												text="Advertiser Name"></spring:message> </label>
										<div class="col-sm-4">
											<form:select
												path="advertisementReqDto.advertisementDto.agencyId"
												cssClass="form-control chosen-select-no-results" id="agnId">
												<form:option value="">
													<spring:message code="adh.select" text="Select" />
												</form:option>
											</form:select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="adh.new.advertisement.location" text="Location" /></label>
										<div class="col-sm-4">
											<form:select
												path="advertisementReqDto.advertisementDto.locId"
												cssClass="form-control chosen-select-no-results" id="locId">
												<form:option value="">
													<spring:message code="adh.select" text="Select" />
												</form:option>
												<c:forEach items="${command.listOfLookUp}" var="location">
													<form:option value="${location.lookUpId}">${location.descLangFirst}</form:option>
												</c:forEach>
											</form:select>
										</div>
										<label class="col-sm-2 control-label required-control"><spring:message
												code="adh.new.advertisement.propertyType"
												text="Property Type" /></label>
										<div class="col-sm-4">
											<form:select
												path="advertisementReqDto.advertisementDto.propTypeId"
												cssClass="form-control chosen-select-no-results"
												id="propTypeId">
												<form:option value="">
													<spring:message code="adh.select" text="Select" />
												</form:option>
												<c:forEach items="${command.getLevelData('ONT')}"
													var="propertyType">
													<form:option value="${propertyType.lookUpId}"
														code="${propertyType.lookUpCode}">${propertyType.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
									<div class="form-group">
										<apptags:input
											labelCode="adh.new.advertisement.property.owner"
											path="advertisementReqDto.advertisementDto.propOwnerName"></apptags:input>

									</div>
								</div>
							</div>
						</div>
						<!-- ------------------------------------ Applicant Details End ---------------------------- -->

						<!-- ------------------------------------ Adevertisement Details Start ---------------------------- -->
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse"
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="adh.new.advertisement.advertising.details"
										text="Advertising Details" />
								</a>
							</h4>
						</div>

						<div id="a3" class="panel-collapse collapse in">
							<div class="panel-body">
								<div id="AdevertisementDetails">
									<jsp:include page="/jsp/adh/newAdvertisementDetails.jsp"></jsp:include>
								</div>
							</div>
						</div>

						<!-- ------------------------------------ Advertisement Details End ---------------------------- -->

						<!------------------- Check List related Design Start ------------------------------------------>
						<c:if test="${not empty command.checkList  && command.saveMode ne 'VIEW'}">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse"
										data-parent="#accordion_single_collapse" href="#a4"> <spring:message
											code="adh.upload.attachement" text="Upload Attachment" />
									</a>
								</h4>
							</div>

							<div id="a4" class="panel-collapse collapse in">
								<div class="panel-body">

									<div id="checklistAndPaymentId">
										<div class="overflow margin-top-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
														<tr>
															<th><spring:message code="" text="Sr No" /></th>
															<th><spring:message code="" text="Document Name" /></th>
															<th><spring:message code="" text="Status" /></th>
															<th><spring:message code="" text="Upload" /></th>
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
																		<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
																		<td><label>${lookUp.doc_DESC_Mar}</label></td>
																	</c:otherwise>
																</c:choose>
																<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td><spring:message code="water.doc.mand" /></td>
																</c:if>
																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td><spring:message code="water.doc.opt" /></td>
																</c:if>
																<td>
																	<div id="docs_${lk}" class="">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath="checkList[${lk.index}]"
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION_HELPDOC"
																			currentCount="${lk.index}" checkListDesc="${docName}" />
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
						<c:if test="${command.payable eq true  && command.saveMode ne 'VIEW'}">
							<div class="form-group margin-top-10">
								<label class="col-sm-2 control-label"><spring:message
										code="water.field.name.amounttopay" /></label>
								<div class="col-sm-4">
									<input type="text" class="form-control" readonly="readonly"
										value="${command.offlineDTO.amountToShow}" maxlength="12"></input>
									<a class="fancybox fancybox.ajax text-small text-info"
										href="RenewalAdvertisementApplication.html?showChargeDetails"><spring:message
											code="water.lable.name.chargedetail" /> <i
										class="fa fa-question-circle "></i></a>
								</div>
							</div>
							<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
							<%-- <apptags:payment></apptags:payment> --%>
						</c:if>


						<!------------------- Check List related Design End ------------------------------------------>

						<div class="text-center margin-top-15">
							<c:if
								test="${empty command.checkList && command.enableSubmit ne 'Y' && command.saveMode ne 'VIEW'}">
								<button type="button" class="btn btn-success"
									onclick="getRenewalAdvertChecklistAndCharges(this)">
									<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
									<spring:message code="adh.confirm.proceed"
										text="Confirm to Proceed"></spring:message>
								</button>
							</c:if>
							<c:if test="${command.enableSubmit eq 'Y' && command.saveMode ne 'VIEW' }">
								<button type="button" class="btn btn-success"
									onclick="saveRenewalAdvApplication(this);">
									<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
									<spring:message code="adh.save" text="Save"></spring:message>
								</button>
							</c:if>
							<c:set var = "backUrl" scope = "session" value = "RenewalAdvertisementApplication.html"/>
							<c:if test="${command.saveMode eq 'VIEW'}">
								<c:set var = "backUrl" scope = "session" value = "CitizenHome.html"/>
							</c:if>
							
							<button type="button" class="btn btn-danger"
								onclick="window.location.href ='${backUrl}'">
								<i class="fa fa-chevron-circle-left padding-right-5"
									aria-hidden="true"></i>
								<spring:message code="adh.back" text="Back"></spring:message>
							</button>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
<script>
	var licenseFromDate = $("#licenseFromDate").val();
	var licenseToDate = $("#licenseToDate").val();

	if (licenseFromDate) {
		$('#licenseFromDate').val(licenseFromDate.split(' ')[0]);
	}
	if (licenseToDate) {
		$('#licenseToDate').val(licenseToDate.split(' ')[0]);
	}

	$(document).ready(function() {
		$('.fancybox').fancybox();
	});
</script>