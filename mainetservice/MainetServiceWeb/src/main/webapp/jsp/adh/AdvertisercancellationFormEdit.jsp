
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
	src="js/adh/advertiserCancellationForm.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
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
        
			<form:form action="ADHCancellationApproval.html"
				name="AdvertisercancellationForm" id="AdvertisercancellationForm"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

			 <div class="form-group">

									<label class="col-sm-2 control-label required-control"><spring:message
												code="advertiser.cancellation.licNo" text="agencyLic No." /></label>
										<div class="col-sm-4">
												
												 <form:input path="agencyRequestDto.masterDto.agencyLicNo" type="text"
												class="form-control" id="agencyLicNo" disabled="true" /> 
												
											
										</div>
											</div>  


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
								path="applicantDetailDto.applicantTitle" cssClass="form-control"
								hasChildLookup="false" hasId="true" showAll="false"
								disabled="true"
								selectOptionLabelCode="applicantinfo.label.select"
								isMandatory="true" />
							<label class="col-sm-2 control-label required-control"
								for="firstName"><spring:message
									code="applicantinfo.label.firstname" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="${disabled}"
									path="applicantDetailDto.applicantFirstName" id="firstName"
									data-rule-required="true" disabled="true"></form:input>
							</div>
						</div>


						<div class="form-group">
							<label class="col-sm-2 control-label" for="middleName"><spring:message
									code="applicantinfo.label.middlename" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="${disabled}"
									path="applicantDetailDto.applicantMiddleName" id="middleName"
									disabled="true"></form:input>
							</div>
							<label class="col-sm-2 control-label required-control"
								for="lastName"><spring:message
									code="applicantinfo.label.lastname" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="${disabled}"
									path="applicantDetailDto.applicantLastName" id="lastName"
									data-rule-required="true" disabled="true"></form:input>
							</div>
						</div>

						<div class="form-group">

							<label class="col-sm-2 control-label required-control"><spring:message
									code="applicantinfo.label.mobile" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control hasMobileNo"
									readonly="${disabled}" maxlength="10"
									path="applicantDetailDto.mobileNo" id="mobileNo"
									data-rule-required="true" disabled="true"></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message
									code="applicantinfo.label.email" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="${disabled}" path="applicantDetailDto.emailId"
									id="emailId" disabled="true"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="address.line1" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="${disabled}" path="applicantDetailDto.areaName"
									id="areaName" data-rule-required="true" disabled="true"></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message
									code="address.line2" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="${disabled}" path="applicantDetailDto.villageTownSub"
									id="villTownCity" disabled="true"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="address.line3" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control"
									readonly="${disabled}" path="applicantDetailDto.roadName"
									id="roadName" disabled="true"></form:input>
							</div>
							<label class="col-sm-2 control-label required-control"><spring:message
									code="applicantinfo.label.pincode" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control hasNumber"
									readonly="${disabled}" path="applicantDetailDto.pinCode"
									id="pinCode" maxlength="6" data-rule-required="true"
									disabled="true"></form:input>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="applicantinfo.label.aadhaar" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control hasNumber"
									readonly="${disabled}" path="applicantDetailDto.aadharNo"
									id="aadharNo" maxlength="12" data-mask="9999 9999 9999"
									disabled="true" />
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
							<apptags:date labelCode="advertiser.cancellation.date"
								datePath="agencyRequestDto.masterDto.cancellationDate"
								fieldclass="datepicker" isMandatory="true" isDisabled="true"></apptags:date>

							<apptags:input labelCode="advertiser.cancellation.reason"
								cssClass="hasCharacter"
								path="agencyRequestDto.masterDto.cancellationReason"
								isMandatory="true" isDisabled="true"></apptags:input>
						</div>

					</div>
				</div>

			 <c:if test="${not empty command.documentList}">

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
													<th><label class="tbold"><spring:message
																code="adh.sr.no" text="Sr No" /></label></th>
													<th><label class="tbold"><spring:message
																code="adh.document.name" text="Document Name" /></label></th>
													<th><label class="tbold"><spring:message
																code="" text="Download" /></label></th>
												</tr>

												<c:forEach items="${command.documentList}" var="lookUp"
													varStatus="lk">
													<tr>
														<td><label>${lookUp.clmSrNo}</label></td>
														<c:choose>
															<c:when
																test="${userSession.getCurrent().getLanguageId() eq 1}">
																<td><label>${lookUp.clmDescEngl}</label></td>
															</c:when>
															<c:otherwise>
																<td><label>${lookUp.clmDesc}</label></td>
															</c:otherwise>
														</c:choose>


														<td><apptags:filedownload
																filename="${lookUp.attFname}"
																filePath="${lookUp.attPath}"
																actionUrl="AdvertisercancellationForm.html?Download" /></td>

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

				 <apptags:CheckerAction hideForward="true" hideSendback="true"></apptags:CheckerAction> 

				<div class="text-center margin-bottom-10">

					<button type="button" class="btn btn-success" id="save"
						onclick="saveLicensecanellationApprovalForm(this)">
						<spring:message code="adh.save" text="Save"></spring:message>
					</button>

				     <%-- <button type="button" class="btn btn-warning" title="Reset"
						onclick="window.location.href='ADHCancellationApproval.html'">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.reset" text="Reset"></spring:message>
					</button>  --%>
					<button type="button" class="btn btn-danger" id="back"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="adh.back" text="Back"></spring:message>
					</button>
				</div> 



			</form:form>
		</div>
	</div>
</div>

