<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="" src="js/land_estate/landEstate.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="land.acq.title" text="Land Acquisition" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="fiels.mandatory.message" text="Field with * is mandatory" /></span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="LandAcquisition.html" cssClass="form-horizontal"
				id="landAcqId">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<form:hidden path="saveMode" id="saveMode" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="land.acq.form.details" text="Land Details" /> </a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<apptags:input labelCode="land.acq.pay.to" isMandatory="true" isReadonly="true"
										path="acquisitionDto.payTo"></apptags:input>

									<apptags:input labelCode="land.acq.area" isReadonly="true"
										path="acquisitionDto.lnArea" isMandatory="true"></apptags:input>

								</div>
								
								<div class="form-group">
									<apptags:input labelCode="land.acq.mobNo" isReadonly="true"
										path="acquisitionDto.lnMobNo" cssClass="alphaNumeric"
										maxlegnth="10" ></apptags:input>

									<apptags:input labelCode="land.acq.address" isReadonly="true"
										path="acquisitionDto.lnAddress" cssClass="alphaNumeric"
										maxlegnth="500" isMandatory="true"></apptags:input>
								</div>


								<div class="form-group">
									<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.acq.location" /> <span><i
											class="text-red-1">*</i></span>
									</label>
									<div class="col-sm-4 ">
										<form:select path="acquisitionDto.locId" id="locId"
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true"
											disabled="true">
											<form:option value="0">Select</form:option>
											<c:forEach items="${command.locationList}" var="location">
												<form:option value="${location.locId}">${location.locNameEng}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="land.acq.acqType" text="Acquisition Type" /></label>
									<c:set var="baseLookupCode" value="AQM" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="acquisitionDto.acqModeId" cssClass="form-control"
										hasChildLookup="false" hasId="true"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="Acquisition Mode"
										disabled="true" />
								</div>


								<div class="form-group">
									<apptags:input labelCode="land.acq.surveyNo" isReadonly="true"
										path="acquisitionDto.lnServno" cssClass="alphaNumeric"
										maxlegnth="10" isMandatory="true"></apptags:input>


									<apptags:input labelCode="land.acq.currentUsage" isReadonly="true"
										path="acquisitionDto.currentUsage" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="true"></apptags:input>

								</div>
								<div class="form-group">
									<apptags:input labelCode="land.acq.currentMarktValue" isReadonly="true"
										path="acquisitionDto.currentMarktValue" cssClass="hasNumber1"
										maxlegnth="100" isMandatory="false"></apptags:input>

									<apptags:input labelCode="land.acq.purpose" isMandatory="true" isReadonly="true"
										path="acquisitionDto.acqPurpose" cssClass="hasCharacter"
										maxlegnth="100"></apptags:input>

								</div>
								
								<div class="form-group">
									<apptags:input labelCode="land.acq.desc" isReadonly="true"
										path="acquisitionDto.lnDesc" cssClass="alphaNumeric"
										maxlegnth="100" isMandatory="true"></apptags:input>


									<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.acq.oth" /> <span><i class="text-red-1">*</i></span>
									</label>

									<div class="col-sm-4 ">
										<form:select path="acquisitionDto.lnOth" id="lnOth"
											cssClass="form-control chosen-select-no-results" 
											isMandatory="true" class="form-control mandColorClass" 
											data-rule-required="true" disabled="true" >
											<form:option value="0">select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select>
									</div>

								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.acq.Title" text="Tittle Document available" />
									</label>
									<div class="col-sm-4 ">
										<form:select path="acquisitionDto.lnTitle"
											cssClass="form-control chosen-select-no-results" 
											class="form-control mandColorClass" data-rule-required="true" disabled="true" >
											<form:option value="0">select</form:option>
											<form:option value="Y">Yes</form:option>
											<form:option value="N">No</form:option>
										</form:select>
									</div>

									<apptags:input labelCode="land.acq.remark" isReadonly="true"
										path="acquisitionDto.lnRemark" cssClass="alphaNumeric "
										maxlegnth="100"></apptags:input>

								</div>

								<%-- <div class="form-group">
									<apptags:date fieldclass="datepicker" labelCode="land.acq.date"
										datePath="acquisitionDto.acqDate" 
										readonly="true" isDisabled="true"></apptags:date>

								</div> --%>

								<!-- Document code set -->
								<div class="form-group">
									<c:set var="count" value="${count + 1}" scope="page" />

									<label for="" class="col-sm-2 control-label"> <spring:message
											code="council.member.documents" text="Documents" /></label>
									<c:set var="count" value="0" scope="page"></c:set>
									<div class="col-sm-4">
										

										<c:if
											test="${command.attachDocsList ne null  && not empty command.attachDocsList }">
											<input type="hidden" name="deleteFileId"
												value="${command.attachDocsList[0].attId}">
											<input type="hidden" name="downloadLink"
												value="${command.attachDocsList[0]}">
											<apptags:filedownload
												filename="${command.attachDocsList[0].attFname}"
												filePath="${command.attachDocsList[0].attPath}"
												actionUrl="LandAcquisition.html?Download"></apptags:filedownload>
										</c:if>

									</div>
								</div>

								<div class="text-center padding-bottom-10" id="scrutinyDiv">
									<jsp:include page="/jsp/cfc/sGrid/scrutinyButtonTemplet.jsp"></jsp:include>
								</div>


							</div>
						</div>
					</div>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->