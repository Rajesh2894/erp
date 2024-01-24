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
		<!-- <div class="widget-header">
			<h2>Tree Cutting/Trimming Permission</h2>
		</div> -->
		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
				class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
			</a>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="cfc.mandatory.message"
						text="Field with * is mandatory" /></span>
			</div>
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
					<spring:message code="NHP.applicant.detail" text="Applicant Detail" />
				</h4>
				
				<input type="hidden" id="langId" value="${command.langId}" />
				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.title" text="Title" /></label>
					<div class="col-sm-4">
						<form:select path="cfcApplicationMst.apmTitle"
							class="form-control" id="titleId">
							<c:set var="baseLookupCode" value="TTL" />

							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.first.name" text="First Name" /></label>
					<div class="col-sm-4">
						<form:input path="cfcApplicationMst.apmFname" id="firstName"
							class="form-control mandColorClass" maxlength="100" />
					</div>

				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label"><spring:message
							code="NHP.middle.name" text="Middle Name" /></label>
					<div class="col-sm-4">
						<form:input path="cfcApplicationMst.apmMname" id="midName"
							class="form-control mandColorClass" maxlength="100" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.last.name" text="Last Name" /></label>
					<div class="col-sm-4">
						<form:input path="cfcApplicationMst.apmLname" id="lName"
							class="form-control mandColorClass" maxlength="100" />
					</div>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.gender" text="Gender" /></label>
					<div class="col-sm-4">
						<form:select path="cfcApplicationMst.apmSex" class="form-control"
							id="gender">
							<c:set var="baseLookupCode" value="GEN" />
							<form:option value="">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpCode}"
									code="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<!-- Applicant Address Detail -->

				<h4 class="margin-top-0">
					<spring:message code="NHP.applicant.address"
						text="Applicant Address" />
				</h4>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="NHP.address.building.name" text="Building Name" /></label>

					<div class="col-sm-4">
						<form:input path="cfcApplicationAddressEntity.apaBldgnm"
							id="buildingName" class="form-control mandColorClass"
							maxlength="100" />
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="NHP.address.building.block" text="Block" /></label>

					<div class="col-sm-4">
						<form:input path="cfcApplicationAddressEntity.apaBlockName"
							id="block" class="form-control mandColorClass" maxlength="100" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="NHP.address.road.name" text="Road Name" /></label>

					<div class="col-sm-4">
						<form:input path="cfcApplicationAddressEntity.apaRoadnm"
							id="roadName" class="form-control mandColorClass" maxlength="100" />
					</div>

				</div>
				<div class="form-group">

					<c:set var="baseLookupCode" value="CWZ" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="CWZ" hasId="true"
						pathPrefix="treeCutingTrimingSummaryDto.cfcWard"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="false"
						isMandatory="true" />


				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.address.city" text="City" /></label>

					<div class="col-sm-4">
						<form:input path="cfcApplicationAddressEntity.apaCityName"
							id="city" class="form-control mandColorClass" maxlength="100" />
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="NHP.address.pinCode" text="PinCode" /></label>

					<div class="col-sm-4">
						<form:input path="cfcApplicationAddressEntity.apaPincode"
							id="pinCode" class="form-control mandColorClass hasNumber"
							maxlength="6" />
					</div>

				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.address.mobileNumber" text="Mobile Number" /></label>

					<div class="col-sm-4">
						<form:input path="cfcApplicationAddressEntity.apaMobilno"
							id="mobNumber" class="form-control mandColorClass hasNumber"
							maxlength="10" />
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="NHP.address.emailId" text="Email ID" /></label>

					<div class="col-sm-4">
						<form:input path="cfcApplicationAddressEntity.apaEmail"
							id="emailId" class="form-control mandColorClass" maxlength="50" />
					</div>

				</div>

				<!-- Service Details -->
				<form:hidden path="cfcApplicationMst.smServiceId" id="serviceId" />
				<%-- <h4 class="margin-top-0">
					<spring:message code="NHP.service.details" text="Service Details" />
				</h4>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="NHP.service.name" text="Service Name" /></label>
					<div class="col-sm-4">
						<form:select path="cfcApplicationMst.smServiceId"
							class="form-control mandColorClass chosen-select-no-results"
							id="serviceId"
							disabled="${command.saveMode eq 'V' ? true : false}">
							<form:option value="">
								<spring:message code="work.management.select" text="Select" />
							</form:option>
							<c:choose>
								<c:when test="${command.langId eq 1}"> <!-- changes -->
									<c:forEach items="${command.tbServicesMsts}"
										var="tbServicesMst">
										<form:option value="${tbServicesMst.smServiceId }">${tbServicesMst.smServiceName }</form:option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.tbServicesMsts}"
										var="tbServicesMst">
										<form:option value="${tbServicesMst.smServiceId }">${tbServicesMst.smServiceNameMar }</form:option>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</form:select>
					</div>
				</div> --%>
				
				<c:if test="${command.saveMode ne 'V' ? true : false}">
					<div class="text-center clear padding-10">

						<button type="button" class="btn btn-success btn-submit"
							id="confirmToProceedId" onclick="proceedForTreeDetailForm(this)">
							<spring:message code="NHP.proceed" text="Proceed" />
							<i class="fa fa-sign-out padding-right-5"></i>
						</button>
						
						<button type="button" class="btn btn-warning"
							onclick="ResetForm()">
							<spring:message code="NHP.reset" />
						</button>
						
						<button type="back" class="btn btn-danger" onclick="backForm()">
							<spring:message code="NHP.back" />
						</button>

					</div>
				</c:if>
				<c:if test="${command.saveMode eq 'V' ? true : false}">
					<div class="text-center clear padding-10">

						<button type="button" id="viewTreeInfo"
							class="btn btn-success btn-submit"
							onclick="viewTreeInfoD('TreeCuttingTrimmingPermission.html','viewTreeInfo')">
							<spring:message code="" text="Tree Info" />
						</button>

						<button type="back" id="backButton" class="btn btn-danger"
							onclick="resetSummaryForm()">
							<spring:message code="common.sequenceconfig.back" />
						</button>
					</div>
				</c:if>
			</form:form>
		</div>
	</div>
</div>
