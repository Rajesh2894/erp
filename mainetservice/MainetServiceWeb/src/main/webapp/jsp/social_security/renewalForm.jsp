<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/social_security/renewalForm.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>


<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message text="Renewal Form for Social Security" code="social.renewal.titile"/>
				</h2>
				<apptags:helpDoc url="AssetFunctionalLocation.html"></apptags:helpDoc>
				<!-- <div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
				</div> -->
			</div>
			<div class="widget-content padding">
				<form:form id="renewalFormId" action="RenewalForm.html"
					method="POST" class="form-horizontal" name="renewalFormId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<h4><spring:message code="social.renewal.form" text="Renewal of Life Certificate"/></h4>

					<div class="form-group">
						
							
							<label class="col-sm-2 control-label required-control" for=""><spring:message
								text="Beneficiary Name / Number" code="soc.ben.name.num"/></label>

						<div class="col-sm-4">
							<form:select path="renewalFormDto.beneficiaryno"
								id="benefnumNname" class="form-control chosen-select-no-results"
								data-rule-required="true">
								<form:option value="0">Select</form:option>

								<c:forEach items="${command.renewalFormDtoList}"
									var="beneficiaryNname">
									<%-- <form:option value="${beneficiaryNname.nameofApplicant}">${beneficiaryNname.nameofApplicant} => ${beneficiaryNname.beneficiaryno}</form:option> --%>
									<form:option value="${beneficiaryNname.beneficiaryno}">${beneficiaryNname.benefnoNname}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div class="form-group">
						<apptags:input labelCode="social.sec.nameapplicant"
							path="renewalFormDto.nameofApplicant" isReadonly="true"></apptags:input>
							
							<apptags:input labelCode="social.sec.schemename"
							path="renewalFormDto.selectSchemeNamedesc" isReadonly="true"></apptags:input>
					</div>
					<div class="form-group">
						<apptags:input labelCode="social.sec.lifecerti"
							path="renewalFormDto.lastDateofLifeCerti" isReadonly="true"></apptags:input>

						<label class="col-sm-2 control-label required-control" for=""><spring:message
								text="Valid to date" code="social.renewal.validToDate"/></label>
						<div class="col-sm-4">
							<div class="input-group">
								<form:input class="form-control datepicker" id="validtodateId"
									 data-label="#dispoDate"
									path="renewalFormDto.validtoDate" isMandatory="true"></form:input>
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						
						
					</div>

					<div class="form-group">

						<label class="col-sm-2 control-label"><spring:message
								code="social.uploadfiles" text="Upload File"/><span class="mand">*</span></label>
						<div class="col-sm-4">
							<div id="uploadFiles" class="">
								<apptags:formField fieldType="7" labelCode="" hasId="true"
									fieldPath="uploadFileList[0]" isMandatory="false"
									showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
									maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
									currentCount="0" />
									<small class="text-blue-2"> <spring:message code="vehicle.file.upload.tooltip"
											text="(Upload File upto 5MB and Only pdf,doc,docx,jpeg,jpg,png,gif,bmp extension(s) file(s) are allowed.)" />
									</small>
							</div>
						</div>

					</div>


					<div class="text-center">

						<button type="button" class="btn btn-success" title="Submit"
							onclick="saveRenewalForm(this)"><spring:message text="Submit" code="social.btn.submit"/></button>
						
						<button type="Reset" class="btn btn-warning" id="resetform"
										onclick="resetRenewalForm(this)">
										<spring:message text="Reset" code="social.btn.reset"/>
								</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
						
					</div>


				</form:form>
			</div>
		</div>
	</div>
</div>
</div>
</div>
</div>