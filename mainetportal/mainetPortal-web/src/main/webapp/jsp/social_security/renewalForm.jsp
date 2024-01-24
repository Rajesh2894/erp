<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/validation.js"></script>
<script src="js/social_security/renewalForm.js"></script>
<script src="js/mainet/file-upload.js"></script>


<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message text="Renewal Form for Social Security" />
				</h2>
				<apptags:helpDoc url="AssetFunctionalLocation.html"></apptags:helpDoc>
				
			</div>
			<div class="widget-content padding">
				<form:form id="renewalFormId" action="RenewalForm.html"
					method="POST" class="form-horizontal" name="renewalFormId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv" style="display: none;"></div>

					<h4>Renewal of Life Certificate</h4>

					<div class="form-group">
						<apptags:input labelCode="Beneficiary Number"
							path="renewalFormDto.beneficiaryno" isMandatory="true"
							cssClass="hasAddressClass"></apptags:input>

						<input type="hidden" id="applicationId"
							path="applicationformdto.applicationId" />




						<button type="button" class="btn btn-success" title="Search"
							onclick="getDataOnBenef()">Search</button>
					</div>

					<div class="form-group">
						<apptags:input labelCode="Name"
							path="renewalFormDto.nameofApplicant" isReadonly="true"></apptags:input>

						<apptags:input labelCode="Scheme Name"
							path="renewalFormDto.selectSchemeNamedesc" isReadonly="true"></apptags:input>
					</div>
					<div class="form-group">
						<apptags:input labelCode="Last date of Life certificate"
							path="renewalFormDto.lastDateofLifeCerti" isReadonly="true"></apptags:input>
							
							<label class="col-sm-2 control-label required-control" for=""><spring:message
									text="Valid to date" /></label>
							<div class="col-sm-4">
							<div class="input-group">
								<form:input class="form-control datepicker" id="validtodateId"
									maxlength="10" data-label="#dispoDate"
									path="renewalFormDto.validtoDate" isMandatory="true"></form:input>
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
							</div>
					</div>

					<div class="form-group">
						<c:set var="count" value="0" scope="page"></c:set>
						<label class="col-sm-2 control-label"><spring:message
								code="rti.uploadfiles" /><span class="mand">*</span></label>
						<div class="col-sm-4">
							<apptags:formField fieldType="7"
								fieldPath="uploadFileList[0].uploadedDocumentPath"
								currentCount="${count}" showFileNameHTMLId="true"
								folderName="${count}" fileSize="BND_COMMOM_MAX_SIZE"
								isMandatory="false" maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
								cssClass="clear">
							</apptags:formField>
						</div>
						
					</div>
			
			<div class="text-center">

				<button type="button" class="btn btn-success" title="Submit"
					onclick="saveRenewalForm(this)">Submit</button>
				<apptags:backButton url="CitizenHome.html"></apptags:backButton>
				<button type="Reset" class="btn btn-warning" id="resetform"
										onclick="resetRenewalForm(this)">
										<spring:message text="Reset" />
								</button>
			</div>


			</form:form>
			</div>
		</div>
	</div>
</div>
</div>
</div>
</div>