<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/council/councilMemberMaster.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script> 

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="council.member.master.title"
					text="Council Member Master" />
			</h2>
			<!-- <div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div> -->
			<apptags:helpDoc url="CouncilMemberMaster.html" />
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="coucil.fiels.mandatory.message"
						text="Field with * is mandatory" /></span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="CouncilMemberMaster.html"
				cssClass="form-horizontal" id="MemberMaster">
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
										code="council.member.master.title"
										text="Council Member Master" /> </a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<apptags:input labelCode="council.member.name"
										path="couMemMasterDto.couMemName" cssClass="form-control hasSpecialChara"
										isMandatory="true"></apptags:input>
									<label class="col-sm-2 control-label required-control"
										for="inwardType"><spring:message
											code="council.master.gender" text="Gender" /></label>
									<c:set var="baseLookupCode" value="GEN" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="couMemMasterDto.couGen" cssClass="form-control"
										hasChildLookup="false" hasId="true"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="Gender"
										disabled="${command.saveMode eq 'V'}" />

								</div>

								<div class="form-group">
									<c:set var="baseLookupCode" value="EWZ" />
									<apptags:lookupFieldSet
										cssClass="form-control required-control" baseLookupCode="EWZ"
										hasId="true" pathPrefix="couMemMasterDto.couEleWZId"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" isMandatory="true"
										disabled="${command.saveMode eq 'V'}" />
								</div>

								<!-- As per RFP changes done -->
								<%-- 			<div class="form-group">
									<label class="control-label col-sm-2"><spring:message
											code="council.member.designation" text="Designation" /><span
										class="mand">*</span></label>
									<div class="col-sm-4 ">
										<form:select path="couMemMasterDto.couDesgId" id="designation"
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true"
											disabled="${command.saveMode eq 'V'}">
											<form:option value="">Select</form:option>
											<c:forEach items="${command.designationList}" var="lookUp">
												<form:option value="${lookUp.dsgid}">${lookUp.dsgname}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div> --%>


								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="inwardType"><spring:message
											code="council.member.educationQualification"
											text="Education Qualification" /></label>
									<c:set var="baseLookupCode" value="ECN" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="couMemMasterDto.couEduId" cssClass="form-control"
										hasChildLookup="false" hasId="true"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="Education Qualification"
										disabled="${command.saveMode eq 'V'}" />

									<label class="col-sm-2 control-label required-control"
										for="inwardType"><spring:message
											code="council.master.caste" text="Caste" /></label>
									<c:set var="baseLookupCode" value="CTG" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="couMemMasterDto.couCastId" cssClass="form-control"
										hasChildLookup="false" hasId="true"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="Caste"
										disabled="${command.saveMode eq 'V'}" />

								</div>

								<div class="form-group">
									<apptags:input labelCode="council.member.mobile.number"
										path="couMemMasterDto.couMobNo" maxlegnth="10"
										cssClass="hasMobileNo" isMandatory="true"></apptags:input>
									<%-- <apptags:date fieldclass="datepicker"
										datePath="couMemMasterDto.couDOB"
										labelCode="council.member.dob" cssClass="mandColorClass"
										isMandatory="true" isDisabled="${command.saveMode eq 'V'}"></apptags:date> --%>
										
									
						 <label for="text-1" class="control-label col-sm-2 required-control"><spring:message
											code="council.member.dob" text="Date of Birth" /></label>
						 
							<div class="col-sm-4">
								<div class="input-group">
							<form:input class="form-control datepicker" id="couDOB"
								path="couMemMasterDto.couDOB"
								 maxlength="10" isMandatory="true" 
								disabled="${command.saveMode eq 'V' ? true : false }"  
								onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"/>
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>

						</div>
					</div>
								</div>
								<div class="form-group">
									<apptags:input labelCode="council.member.email.id"
										onBlur="validateEmail(this)" path="couMemMasterDto.couEmail"
										cssClass="hasemailclass"></apptags:input>

									<apptags:textArea labelCode="council.member.address"
										path="couMemMasterDto.couAddress" cssClass="hasAddressClass"
										isMandatory="true" isDisabled="${command.saveMode eq 'V'}"></apptags:textArea>
								</div>
								<div class="form-group">
									<apptags:date fieldclass="datepicker"
										datePath="couMemMasterDto.couElecDate"
										labelCode="council.member.election.date"
										cssClass="mandColorClass" isMandatory="true"
										isDisabled="${command.saveMode eq 'V'}"></apptags:date>
									<apptags:date fieldclass="datepicker"
										datePath="couMemMasterDto.couOathDate"
										labelCode="council.member.oath.date" cssClass="mandColorClass"
										isMandatory="true" isDisabled="${command.saveMode eq 'V'}"></apptags:date>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="inwardType"><spring:message
											code="council.member.partyaffiliation"
											text="Party Affiliation" /></label>
									<c:set var="baseLookupCode" value="PAF" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="couMemMasterDto.couPartyAffilation"
										cssClass="form-control partyAffilation" hasChildLookup="false" hasId="true"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="Party Affiliation"
										disabled="${command.saveMode eq 'V'}" />
									<div class="partyName hidden">
									<apptags:input labelCode="council.member.party.name"
										path="couMemMasterDto.couPartyName" cssClass="form-control"></apptags:input>
									</div>

								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label required-control"
										for="inwardType"><spring:message
											code="council.member.type" text="Member Type" /></label>
									<c:set var="baseLookupCode" value="MET" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="couMemMasterDto.couMemberType" cssClass="form-control"
										hasChildLookup="false" hasId="true"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" showOnlyLabel="council.member.type"
										disabled="${command.saveMode eq 'V'}" />


									<label for="" class="col-sm-2 control-label"> <spring:message
											code="council.member.upload" text="Upload Photograph" /></label>
									<c:set var="count" value="0" scope="page"></c:set>
									<div class="col-sm-4">
										<small class="text-blue-2"> <spring:message
												code="council.member.image.upload"
												text="(Upload Image File upto 2 MB)" />
										</small>
										<c:if
											test="${command.saveMode eq 'C'  || command.saveMode eq 'E' }">
											<apptags:formField fieldType="7"
												fieldPath="attachments[${count}].uploadedDocumentPath"
												currentCount="${count}" folderName="${count}"
												fileSize="COMMOM_MAX_SIZE" showFileNameHTMLId="true"
												isMandatory="true" maxFileCount="COUNCIL_FILE_COUNT"
												validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
												cssClass="clear">
											</apptags:formField>
										</c:if>

										<c:if
											test="${command.attachDocsList ne null  && not empty command.attachDocsList }">
											<input type="hidden" name="deleteFileId"
												value="${command.attachDocsList[0].attId}">
											<input type="hidden" name="downloadLink"
												value="${command.attachDocsList[0]}">
											<apptags:filedownload
												filename="${command.attachDocsList[0].attFname}"
												filePath="${command.attachDocsList[0].attPath}"
												actionUrl="CouncilMemberMaster.html?Download"></apptags:filedownload>
										</c:if>
									</div>

								</div>




							</div>
							<!-- Start button -->
							<div class="text-center">
								<c:if test="${command.saveMode ne 'V'}">
									<button type="button" class="btn btn-success" title="Submit"
										onclick="saveform(this)">
										<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
										<spring:message code="council.button.submit" text="Submit" />
									</button>
								</c:if>
								<c:if test="${command.saveMode eq 'C'}">
									<button type="button" onclick="addMemberMaster('CouncilMemberMaster.html','addCouncilMember');"
										class="btn btn-warning" title="Reset">
										<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
										<spring:message code="council.button.reset" text="Reset" />
									</button>
								</c:if>
								<button type="button" class="button-input btn btn-danger"
									name="button-Cancel" value="Cancel" style=""
									onclick="backMemberMasterForm();" id="button-Cancel">
									<i class="fa fa-chevron-circle-left padding-right-5"></i>
									<spring:message code="council.button.back" text="Back" />
								</button>
							</div>
							<!-- End button -->
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