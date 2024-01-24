<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script type="text/javascript" src="js/care/complaint-registration.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("input").on("keypress", function(e) {
			if (e.which === 32 && !this.value.length)
				e.preventDefault();
		});

		$("#care").validate({
			onkeyup : function(element) {
				this.element(element);
				console.log('onkeyup fired');
			},
			onfocusout : function(element) {
				this.element(element);
				console.log('onfocusout fired');
			}
		});
		document.care.MobileNumber.focus();
		//Defect #143955
		var complaintDescriptionVal = $('#ComplaintDescription').val();
		if(complaintDescriptionVal == null || complaintDescriptionVal == '') {
			localStorage.setItem('textRemaining','3000');
		}
	});
	//Defect #143955
	var textRemain = localStorage.getItem('textRemaining');
	var countField = $('#complaintDescriptionCount');
	if(textRemain == null || textRemain ==''){
		var countFieldNum = countField.text();
		countField.html(countFieldNum);
	} else{
		countField.html(textRemain);
	}
	$('#resetBtn').on('click', function(){
		localStorage.clear();
	});
</script>

<style>
textarea.form-control {
	resize: vertical !important;
	height: 4.6em;
}
</style>


<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<%-- <div class="widget-header">
		 
          <h2><spring:message code="care.complaint.registration.dept" text="Complaint Registration Department"/></h2>

           </div> --%>
		<apptags:helpDoc url="GrievanceDepartmentRegistration.html"></apptags:helpDoc>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="care.fieldwith"
						text="Field with " /><i class="text-red-1">*</i> <spring:message
						code="care.ismandatory" text="is mandatory" /></span>
			</div>


			<form:form id="care" name="care" class="form-horizontal"
				commandName="command" action="GrievanceDepartmentRegistration.html"
				method="POST" enctype="multipart/form-data">


				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
				</div>


				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<ul>
						<li><label id="errorId"></label></li>
					</ul>
				</div>
				<form:hidden path="" id="reqType" value="${command.requestType}" />
				<form:hidden path="" id="kdmcEnv" value="${command.kdmcEnv}" />
				<h4>
					<spring:message code="care.referencedetails"
						text="Reference Details" />
				</h4>


				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="Mode"><spring:message
							code="care.mode" text="Mode" /></label>
					<apptags:lookupField items="${command.getLevelData('RFM')}"
						path="careRequest.referenceMode" cssClass="form-control"
						selectOptionLabelCode="Select" hasId="true" isMandatory="true" />

					<label class="col-sm-2 control-label required-control"
						for="Category"><spring:message code="care.category"
							text="Category" /></label>
					<apptags:lookupField items="${command.getLevelData('RFC')}"
						path="careRequest.referenceCategory" cssClass="form-control"
						selectOptionLabelCode="Select" hasId="true" isMandatory="true" />

				</div>

				<div class="form-group">
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="CWZ" hasId="true" pathPrefix="careRequest.ward"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="false"
						isMandatory="true" />
				</div>

				<div class="form-group">
					<c:set var="ApplicantType" value="${command.applicationType}"
						scope="session" />
					<c:set var="ComplaintLabel" value="${command.labelType}"
						scope="session" />
					<apptags:radio radioLabel="${ComplaintLabel}"
						radioValue="${ApplicantType}" labelCode="care.applicationType"
						path="careRequest.applnType" disabled=""
						changeHandler="requestType()"></apptags:radio>
					<%-- <label class="col-sm-2 control-label"><spring:message
							code="" text="Application Type" /> <span class="mand">*</span></label>
					<div class="col-sm-4">
						<label class="radio-inline margin-top-5"> <form:radiobutton
								path="careRequest.applnType" class="applicationType"
								id="serviceRequest" value="R" /> <spring:message code=""
								text="Service Request" /></label> <label
							class="radio-inline margin-top-5"> <form:radiobutton
								path="careRequest.applnType" class="applicationType"
								id="complaint" value="C" /> <spring:message code=""
								text="Complaint" />
						</label>
					</div> --%>
				</div>


				<h4>
					<spring:message code="care.applicantInformation"
						text="Applicant Information" />
				</h4>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="MobileNumber"><spring:message
							code="care.mobilenumber" text="Mobile Number" /></label>
					<div class="col-sm-4">
						<form:input name="mobileNo" type="text"
							class="form-control hasNumber" id="MobileNumber"
							path="applicantDetailDto.mobileNo" maxlength="10"></form:input>
					</div>
					<label class="col-sm-2 control-label required-control"
						for="titleId"><spring:message code="care.name" /></label>

					<div class="col-sm-1 padding-right-0">
						<form:select path="applicantDetailDto.titleId"
							class="form-control" id="titleId">
							<c:set var="baseLookupCode" value="TTL" />
							<form:option value="0">
								<spring:message code="Select" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData(baseLookupCode)}"
								var="lookUp">
								<form:option value="${lookUp.lookUpId}"
									code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<div class="col-sm-1 padding-right-0 padding-left-0">
						<spring:message code="care.firstname" text="care.firstname"
							var="firstnameplh"></spring:message>
						<form:input name="firstName" type="text"
							class="form-control hasSpecialChara" id="FirstName1"
							path="applicantDetailDto.fName" placeholder="${firstnameplh}"></form:input>
					</div>
					<div class="col-sm-1 padding-right-0 padding-left-0">
						<spring:message code="care.middlename" text="care.middlename"
							var="middlenameplh"></spring:message>
						<form:input name="MiddleName" type="text"
							class="form-control hasSpecialChara" id="MiddleName"
							path="applicantDetailDto.mName" placeholder="${middlenameplh}"></form:input>
					</div>
					<div class="col-sm-1  padding-left-0">
						<spring:message code="care.lastname" text="care.lastname"
							var="lastnameplh"></spring:message>
						<form:input name="lastName" type="text"
							class="form-control hasSpecialChara " id="LastName1"
							path="applicantDetailDto.lName" placeholder="${lastnameplh}"></form:input>
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="gender"><spring:message
							code="care.gender" /></label>
					<apptags:lookupField items="${command.getLevelData('GEN')}"
						path="applicantDetailDto.gender" cssClass="form-control"
						selectOptionLabelCode="Select" hasId="true" isMandatory="false" />

					<label class="col-sm-2 control-label" for="EmailID"><spring:message
							code="care.emailid" text="Email ID" /></label>
					<div class="col-sm-4">
						<form:input name="" type="email" class="form-control" id="EmailID"
							path="applicantDetailDto.email" data-rule-email="true"></form:input>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="address"><spring:message code="care.address"
							text="Address" /></label>
					<div class="col-sm-4">
						<form:textarea class="form-control" id="address" maxlength="1000"
							path="applicantDetailDto.areaName"></form:textarea>
					</div>

					<label class="col-sm-2 control-label" for="Pincode"><spring:message
							code="care.pincode" text="Pincode" /></label>
					<div class="col-sm-4">
						<form:input name="" type="text" class="form-control hasNumber"
							id="Pincode" path="applicantDetailDto.pincodeNo" maxlength="6" minlength="6"></form:input>
					</div>
				</div>


				<h4>
					<spring:message code="care.complaintdetails"
						text="Complaint Details" />
				</h4>


				<c:if test="${userSession.organisation.defaultStatus eq 'Y' }">
					<div class="form-group">
						<label class="col-sm-2 control-label required-control"
							for="titleId"><spring:message code="care.district"
								text="District" /></label>
						<apptags:lookupField items="${command.getLevelData('DIS')}"
							path="careRequest.district"
							cssClass="form-control district chosen-select-no-results"
							selectOptionLabelCode="Select" hasId="true" isMandatory="false" />

						<div id="id_org_div">
							<apptags:select labelCode="care.organisation"
								items="${command.organisations}" path="careRequest.orgId"
								selectOptionLabelCode="Select" isMandatory="true"
								isLookUpItem="true" cssClass="chosen-select-no-results"></apptags:select>
						</div>
					</div>
				</c:if>

				<div class="form-group">
					<div id="id_department_div">
						<%--  <apptags:select labelCode="care.department" items="${command.departments}"  path="careRequest.departmentComplaint" 
	                    	selectOptionLabelCode="Select" isMandatory="true" isLookUpItem="true" cssClass="chosen-select-no-results"></apptags:select> --%>
						<label class="col-sm-2 control-label required-control"> <spring:message
								code="care.department" text="" />
						</label>
						<div class="col-sm-4">
							<form:select path="careRequest.departmentComplaint"
								class="form-control chosen-select-no-results mandColorClass"
								id="departmentComplaint" data-rule-required="true">
								<form:option value="0">
									<spring:message code='Select' />
								</form:option>

								<c:forEach items="${command.departments}" var="lookup">
									<c:choose>
										<c:when test="${userSession.languageId eq 1}">
											<form:option value="${lookup.lookUpId}">${lookup.descLangFirst}</form:option>
										</c:when>
										<c:otherwise>
											<form:option value="${lookup.lookUpId}">${lookup.descLangSecond}</form:option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</form:select>
						</div>
					</div>

					<div id="id_complaintType_div">
						<apptags:select labelCode="care.complaintType"
							items="${command.complaintTypes}" 
							path="careRequest.complaintType" selectOptionLabelCode="Select"
							isMandatory="true" isLookUpItem="true"
							cssClass="chosen-select-no-results  compType"></apptags:select>
					</div>
				</div>


				<div class="form-group">

					<div id="id_location_div">
						<apptags:select labelCode="care.location"
							items="${command.locations}" path="careRequest.location"
							selectOptionLabelCode="Select" isMandatory="true"
							isLookUpItem="true" cssClass="chosen-select-no-results"></apptags:select>
					</div>
					
					<div id="residentDivId">
						<label class="col-sm-2 control-label required-control" for="residentId"><spring:message
							code="care.residentId" text="Resident ID (UID/Election ID)" /></label>
						<div class="col-sm-4">
							<form:input name="" type="text" id="residentId" class="form-control"
								path="careRequest.residentId" maxlength="19" ></form:input>
						</div>
					</div>

				</div>
				
				
				
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="ComplaintDescription"><spring:message
							code="care.complaintdescription" text="Complaint Description" /></label>
					<div class="col-sm-4">
						<form:textarea name="" cols="" rows="" class="form-control"
							id="ComplaintDescription" path="careRequest.description" maxlength="3000"
							onkeyup="countChar(this,3000,'complaintDescriptionCount')" onfocus="countChar(this,3000,'complaintDescriptionCount')"></form:textarea>
						<div class="pull-right">
							<spring:message code="charcter.remain" text="characters remaining " /><span id="complaintDescriptionCount" class="margin-left-5">3000</span>
						</div>
					</div>
					
					<label class="col-sm-2 control-label" for="Landmark"><spring:message
							code="care.landmark" text="Landmark" /></label>
					<div class="col-sm-4">
						<form:input name="" type="text" id="Landmark" class="form-control"
							path="careRequest.landmark"></form:input>
					</div>
				</div>

				<!-- Removed as per told by samadhan sir -->
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label" for="Pincode2"><spring:message
							code="care.pincode" text="Pincode" /></label>
					<div class="col-sm-4">
						<form:input type="text" class="form-control hasNumber"
							id="Pincode2" path="" maxlength="6"></form:input>
					</div>
				</div> --%>

				<div class="form-group">

					<%-- <apptags:date labelCode="care.referencedate"
						fieldclass="datepicker" showDefault="true"
						datePath="careRequest.referenceDate" isMandatory="true"></apptags:date> --%>
	
					<label class="col-sm-2 control-label required-control"><spring:message
					code="care.referencedate" text="care.referencedate" /></label>
					
					<div class="col-sm-4">
                        <div class="input-group">
                            <form:input path="careRequest.referenceDate"
                                placeHolder="dd-mm-yyyy"
                                class="form-control mandColorClass datepicker" isMandatory="true"/>
                            <label class="input-group-addon"><i
                                class="fa fa-calendar"></i><span class="hide"> <spring:message
                                        code="" text="icon" /></span><input type="hidden"></label>
                        </div>
                    </div>
					
					<input type="hidden" id="amtDuesCheck" name="" value="N" />

					<label class="col-sm-2 control-label" id="refNoLblId" for="extReferNumber"><spring:message
							code="care.extReferNumber" text="care.extReferNumber" /></label>
					<div class="col-sm-4">
						<form:input name="" type="text" id="extReferNumber"
							class="form-control" path="careRequest.extReferNumber"></form:input>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" id="careDocLbl" for="UploadDocument"><spring:message
							code="care.upload" text="Upload Document" /></label>
					<div class="col-sm-4">
						
							<apptags:formField fieldType="7" labelCode="" hasId="true"
								fieldPath="" isMandatory="false" showFileNameHTMLId="true"
								fileSize="BND_COMMOM_MAX_SIZE"
								maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
								validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
								currentCount="0" cssClass="btn-sm" multiple="true"/>
						
						<div class="col-sm-9">
							<small class="text-blue-2 "> <spring:message
									code="care.validator.fileUploadNote"
									text="(Upload File upto 5MB and only pdf,doc,docx,jpeg,jpg,png,gif,bmp)" />
							</small>
						</div>
					</div>
				</div>



				<div class="text-center">
					<apptags:submitButton entityLabelCode="Submit"
						cssClass="button-input btn btn-success compSubmit" actionParam="saveDetails"></apptags:submitButton>
					<button type="Reset" class="btn btn-warning" id="resetBtn" onclick="window.location.href='GrievanceDepartmentRegistration.html'">
						<spring:message code="care.reset" text="Reset" />
					</button>
					<c:if test="${userSession.employee.designation.dsgshortname ne 'OPR' }">
						<button type="button"
							onclick="window.location.href='AdminHome.html'"
							class="btn btn-danger">
							<spring:message code="care.back" text="Back" />
						</button>
					</c:if>
				</div>
				<input type="hidden" id="taskName" name="taskName"
					value="${taskName}" />
				<input type="hidden" id="taskId" name="taskId" value="${taskId}" />
				<input type="hidden" id="processName" name="processName"
					value="${processName}" />
				<form:hidden id="decision" name="decision" path=""
					value="${actionStatus}" />
				<form:hidden id="comments" name="comments" path=""
					value="${actionComment}" />
				<input type="hidden" id="csrf_parameter_name"
					name="csrf_parameter_name" value="${_csrf.parameterName}" />
				<input type="hidden" id="csrf_token" name="csrf_token"
					value="${_csrf.token}" />
				<input type="hidden" id="complaintTypeHidden"
					name="complaintTypeHidden" value="${complaintType}" />
				<form:hidden path="labelType" />
				<form:hidden path="applicationType" />
			</form:form>
		</div>
	</div>
	<!-- End of info box -->


</div>

<!--Scroll To Top-->
<a class="tothetop" href="javascript:;"><i class="fa fa-angle-up"></i></a>

<!-- End of page -->

<div class="md-overlay"></div>
</body>

</html>