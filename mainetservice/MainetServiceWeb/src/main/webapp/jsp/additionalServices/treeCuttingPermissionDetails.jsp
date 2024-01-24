<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/additionalServices/treeCuttingtrimingPer.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="TCP.tree.cutting.per" text="Tree Cutting Permission" /></h2>
		</div>
		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
				class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
			</a>
		</div>
		<div class="widget-content padding">
			<form:form action="TreeCuttingTrimmingPermission.html" method="post"
				class="form-horizontal" name="TreeCuttingPermissionForm"
				id="TreeCuttingPermissionForm">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<h4 class="margin-top-0">
					<spring:message code="CFC.applicant.info" text="Applicant Information" />
				</h4>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="TCP.applicant.name" text="Applicant Name" /></label>
					<div class="col-sm-4">
						<form:input path="treeCuttingInfoDto.appName" id="appName"
							class="form-control mandColorClass" maxlength="100"  disabled="true"/>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="TCP.mobile" text="Mobile" /></label>

					<div class="col-sm-4">
						<form:input path="treeCuttingInfoDto.mobNumber" id="mobNumber"
							class="form-control mandColorClass hasNumber" maxlength="10"  disabled="true"/>
					</div>


				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="NHP.address.emailId" text="Email ID" /></label>

					<div class="col-sm-4">
						<form:input path="treeCuttingInfoDto.emailId" id="emailId"
							class="form-control mandColorClass" maxlength="50"  disabled="true"/>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="TCP.address" text="Address" /></label>
					<div class="col-sm-4">
						<form:input path="treeCuttingInfoDto.address" id="address"
							class="form-control mandColorClass" maxlength="100" disabled="true"/>
					</div>

				</div>

				<h4 class="margin-top-0">
					<spring:message code="TCP.tree.info" text="Information About Tree" />
				</h4>

				<input type="hidden" id="langId" value="${command.langId}" />
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="TCP.tree.desc.name" text="Tree Description/Name" /></label>
					<div class="col-sm-4">
						<form:input path="treeCuttingInfoDto.treeDesc" id="treeDesc"
							class="form-control mandColorClass" maxlength="100" />
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="TCP.tree.count" text="Count" /></label>

					<div class="col-sm-4">
						<form:input path="treeCuttingInfoDto.count" id="count"
							class="form-control mandColorClass hasNumber" maxlength="10" />
					</div>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="TCP.reason" text="Reason" /></label>
					<div class="col-sm-4">
						<form:input path="treeCuttingInfoDto.reason" id="reason"
							class="form-control mandColorClass" maxlength="100" />
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="TCP.tree.location" text="Location" /></label>

					<div class="col-sm-4">
						<form:input path="treeCuttingInfoDto.location" id="location"
							class="form-control mandColorClass" maxlength="100" />
					</div>

				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="TCP.tupe" text="Tree Cutting Type" /></label>
					<c:set var="baseLookupCode" value="TCT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="treeCuttingInfoDto.treeCutType" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="TCP.tupe" />


				</div>


				<c:if test="${command.saveMode ne 'V' ? true : false}">
					<div class="text-center clear padding-10">

						<button type="button" class="btn btn-success btn-submit"
							id="confirmToProceedId" onclick="proceedToChecklist(this)">

							<spring:message code="NHP.proceed" text="Proceed" />
							<i class="fa fa-sign-out padding-right-5"></i>
						</button>
					
						<button type="button" class="btn btn-warning"
							onclick="ResetTreeDetailForm()">
							<spring:message code="NHP.reset" />
						</button>
						
						<button type="back" class="btn btn-danger"
							onclick="backToApplicantForm()">
							<spring:message code="NHP.back" />
						</button>
					</div>
				</c:if>
				<c:if test="${command.saveMode eq 'V' ? true : false}">
					<div class="text-center clear padding-10">
						<%-- <button type="button" class="btn btn-blue-2 btn-sm"
							id="viewButton"
							onclick="viewHospitalInfo('NursingHomePermission.html','viewHospitalInfo')">

							<spring:message code="" text="Hospital Info" />
							<i class="fa fa-eye"></i>
						</button> --%>
						<button type="back" id="backButton" class="btn btn-danger"
							onclick="viewTreeInfoData('TreeCuttingTrimmingPermission.html','viewTreeCuttingInfo','${command.cfcApplicationMst.apmApplicationId}')">
							<spring:message code="NHP.back" />
						</button>
					</div>
				</c:if>

			</form:form>
		</div>
	</div>
</div>
