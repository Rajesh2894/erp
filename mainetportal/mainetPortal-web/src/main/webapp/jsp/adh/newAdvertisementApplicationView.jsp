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
	src="js/adh/newAdvertisementApplication.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.min.js"></script>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="adh.new.advertisement.application.title"
					text="Application For New Advertisement Permission" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message></span>
			</div>

			<form:form method="POST" action="NewAdvertisementApplication.html"
				class="form-horizontal" id="newAdvertisementApplicationView"
				name="newAdvertisementApplicationView">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<!-- ------------------------------------ Applicant Information Start ---------------------------- -->

				<form:hidden path="scrutinyViewMode" id="scrutinyViewMode" />
				<h4 class="margin-top-0">
					<spring:message code="adh.new.advertisement.applicant.information"
						text="Applicant Information"></spring:message>
				</h4>
				<div id="ApplicantInformation">
					<jsp:include page="/jsp/adh/applicantInformation.jsp"></jsp:include>
				</div>

				<!-- ------------------------------------ Applicant Information End ---------------------------- -->

				<!-- ------------------------------------ Applicant Details Start ---------------------------- -->

				<form:hidden id="hideAgnId" path=""
					value="${command.advertisementReqDto.advertisementDto.agencyId}" />
				<h4 class="margin-top-0">
					<spring:message code="adh.new.advertisement.applicant.details"
						text="Applicant Details"></spring:message>
				</h4>
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="adh.new.advertisement.application.category"
							text="Advertiser Category" /></label>
					<div class="col-sm-4">
						<form:select
							path="advertisementReqDto.advertisementDto.appCategoryId"
							cssClass="form-control chosen-select-no-results"
							id="advertiseCategory" onchange="getAgencyDetails();"
							disabled="true">
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
					<label class="col-sm-2 control-label "><spring:message
							code="adh.new.advertisement.license.type" text="License Type" /></label>
					<div class="col-sm-4">
						<form:select
							path="advertisementReqDto.advertisementDto.licenseType"
							cssClass="form-control chosen-select-no-results" id="licenseType"
							disabled="true">
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
					<label class="col-sm-2 control-label"><spring:message
							code="adh.new.advertisement.application.license.from.date"
							text="License From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input
								path="advertisementReqDto.advertisementDto.licenseFromDate"
								id="licenseFromDate"
								class="form-control mandColorClass datePicker" maxlength="10" />
							<label class="input-group-addon" for="licenseFromDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=licenseFromDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="adh.new.advertisement.application.license.to.date"
							text="License To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input
								path="advertisementReqDto.advertisementDto.licenseToDate"
								id="licenseToDate"
								class="form-control mandColorClass datePicker" maxlength="10" />
							<label class="input-group-addon" for="licenseToDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=licenseToDate></label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="adh.new.advertisement.location.type" text="Location Type" /></label>
					<div class="col-sm-4">
						<form:select
							path="advertisementReqDto.advertisementDto.locCatType"
							cssClass="form-control chosen-select-no-results" id="locCatType"
							disabled="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select" />
							</form:option>
							<form:option value="E">
								<spring:message code="adh.new.advertisement.existing.location"
									text="Existing Location" />
							</form:option>
							<form:option value="N">
								<spring:message code="adh.new.advertisement.new.location"
									text="New Location" />
							</form:option>
						</form:select>
					</div>
					<label class="col-sm-2 control-label "><spring:message
							code="adh.new.advertisement.advertiser.name"
							text="Advertiser Name"></spring:message> </label>
					<div class="col-sm-4">
						<form:select path="advertisementReqDto.advertisementDto.agencyId"
							cssClass="form-control chosen-select-no-results" id="agnId"
							disabled="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select" />
							</form:option>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="adh.new.advertisement.location" text="Location" /></label>
					<div class="col-sm-4">
						<form:select path="advertisementReqDto.advertisementDto.locId"
							cssClass="form-control chosen-select-no-results" id="locId"
							disabled="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select" />
							</form:option>
							<c:forEach items="${command.locationMasList}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="adh.new.advertisement.propertyType" text="Property Type" /></label>
					<div class="col-sm-4">
						<form:select
							path="advertisementReqDto.advertisementDto.propTypeId"
							cssClass="form-control chosen-select-no-results" id="propTypeId"
							disabled="true">
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
					<apptags:input labelCode="adh.new.advertisement.property.owner"
						path="advertisementReqDto.advertisementDto.propOwnerName"></apptags:input>

					<label class="col-sm-2 control-label "><spring:message
							code="adh.new.advertisement.application.status"
							text="Application Status" /></label>
					<div class="col-sm-4">
						<form:select path="advertisementReqDto.advertisementDto.adhStatus"
							cssClass="form-control chosen-select-no-results" id="adhStatus"
							disabled="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select" />
							</form:option>
							<form:option value="A">
								<spring:message code="adh.active" text="Active" />
							</form:option>
							<form:option value="T">
								<spring:message code="adh.new.advertisement.terminate"
									text="Terminate" />
							</form:option>
							<form:option value="C">
								<spring:message code="adh.new.advertisement.closed"
									text="Closed" />
							</form:option>
							<form:option value="S">
								<spring:message code="adh.new.advertisement.suspended"
									text="Suspended" />
							</form:option>
						</form:select>
					</div>
				</div>
				<!-- ------------------------------------ Applicant Details End ---------------------------- -->
			</form:form>
		</div>
	</div>
</div>

<!-- Start Content End -->