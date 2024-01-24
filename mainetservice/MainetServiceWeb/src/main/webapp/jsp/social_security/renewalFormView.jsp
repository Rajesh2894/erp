<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/social_security/renewalFormView.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message text="Renewal Form for Social Security" />
			</h2>
			<apptags:helpDoc url="AssetFunctionalLocation.html"></apptags:helpDoc>
			<!-- <div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div> -->
		</div>
		<div class="widget-content padding">
			<form:form id="renewalFormId" action="RenewalForm.html" method="POST"
				class="form-horizontal" name="renewalFormId">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<h4>Renewal of Life Certificate</h4>

				<div class="form-group">
					<apptags:input labelCode="Beneficiary Number"
						path="renewalFormDto.beneficiaryno" isMandatory="true"
						cssClass="hasAddressClass" isReadonly="true"></apptags:input>
						
						

					<input type="hidden" id="applicationId"
						path="applicationformdto.applicationId" />

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
								path="renewalFormDto.validtoDate" isMandatory="true"
								disabled="true"></form:input>
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>

						</div>
					</div>
				</div>

				<div class="form-group">

					<div class="table-responsive">
						<table class="table table-bordered table-striped"
							id="cfcattachdoc">

							<tr>
								<th><spring:message code="scheme.document.name"
										text="Life Certificate" /></th>
								<th><spring:message code="scheme.view.document"
										text="Life Certificate" /></th>
							</tr>
							<c:forEach items="${command.cfcAttachment}" var="lookUp">
								<tr>
									<td>${lookUp.attFname}</td>
									<td><apptags:filedownload filename="${lookUp.attFname}"
											filePath="${lookUp.attPath}"
											actionUrl="RenewalForm.html?Download" /></td>
								</tr>
							</c:forEach>
						</table>
					</div>

				</div>

				<%-- <div class="text-center">
					<apptags:CheckerAction hideForward="true" hideSendback="true"></apptags:CheckerAction>
				</div> --%>
<%-- 
				<div class="text-center">
					<button type="button" id="saveRenewalFormDataId"
						class="btn btn-success btn-submit"
						onclick="saveRenewalFormData(this)">
						<spring:message code="asset.transfer.save" text="Save" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div> --%>


				<div class="panel-group accordion-toggle" id="reneqalformVewId">
					<div class="panel panel-default">
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<div class="widget-content padding">
										<apptags:CheckerAction hideForward="true" hideSendback="true"></apptags:CheckerAction>
									</div>
									<div class="text-center">
										<button type="button" id="saveRenewalFormDataId"
											class="btn btn-success btn-submit"
											onclick="saveRenewalFormData(this)">
											<spring:message code="asset.transfer.save" text="Save" />
										</button>
										<apptags:backButton url="AdminHome.html"></apptags:backButton>
									</div>

								</div>
							</div>

						</div>
					</div>
				</div>



			</form:form>
		</div>
	</div>
</div>


