<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/marriage_mgmt/marriageReg.js"></script>
<script type="text/javascript" src="/js/marriage_mgmt/viewMarriageReg.js"></script>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<style>
input[type=radio]{
	opacity:1 !important;
}
	.bg-deep-danger {
	color: #982525;
}

</style>


<div id="marriageTabForm">
	<form:form action="MarriageRegistration.html" id="marriageFormId"
		method="post" class="form-horizontal">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
			<ul>
				<li><label id="errorId"></label></li>
			</ul>
		</div>
		<form:hidden path="modeType" id="modeType" />
		<form:hidden path="marriageDTO.marId" id="marId" />
		<input type="hidden" path="" id="applicableENV" value="${command.applicableENV}" />
		
		<c:if test="${command.applicableENV}">
						<h5 class="bg-deep-danger"><i><spring:message code="mrm.mahras.note" text="Maha Note"/></i></h5>
		</c:if>

		<div class="panel-group accordion-toggle margin-top-5" id="accordion_single_collapse1">
			<div class="panel panel-default">
				<div class="panel-heading">
				
					
				
					<h4 class="panel-title ">
						<a data-toggle="collapse" data-parent="#accordion_single_collapse1" href="#Applicant" tabindex="-1"><spring:message
						code="mrm.marriage.applicantDet" text="Applicant Details" /></a>
					</h4>
				</div>
				<h4><strong><i><spring:message code="mrm.applicantNote" text="Note"/></i></strong></h4>
				<c:if test="${command.modeType ne 'V'}">
					<div class="form-group" style="margin-left:20px">
							<%-- <label class="col-sm-2 control-label"><spring:message
								code="" text="" /> </label> --%>
							
								<label class="radio-inline margin-top-5"> 
								<form:radiobutton path="marriageDTO.applnCopyTo" class="applnCopyTo" disabled="${not empty command.marriageDTO.applicantDetailDto.applicantFirstName}"
										id="husbandR" value="H" />
										 <spring:message code="mrm.hus.appCopy" text="If the Applicant is Husand" /></label>
								 <label	class="radio-inline margin-top-5">
								  <form:radiobutton path="marriageDTO.applnCopyTo" class="applnCopyTo" disabled="${not empty command.marriageDTO.applicantDetailDto.applicantFirstName}"
										id="wifeR" value="W" />
										 <spring:message code="mrm.wife.appCopy" text="If the Applicant is Wife" />
								</label>
								<label class="radio-inline margin-top-5">
								 <form:radiobutton path="marriageDTO.applnCopyTo" class="applnCopyTo" disabled="${not empty command.marriageDTO.applicantDetailDto.applicantFirstName}"
									  id="otherR"  value="O" /> <spring:message code="mrm.other.appCopy" text="Others" />
							
					</div>
					</c:if>
				
				<div class="panel-collapse collapse in " id="Applicant">
					<div class="panel-body">
						<div class="form-group">
							<label class="col-sm-2 control-label required-control"
								for="applicantTitle"><spring:message
							code="applicantinfo.label.title" text="Title" /></label>
							<c:set var="baseLookupCode" value="TTL" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="marriageDTO.applicantDetailDto.applicantTitle"
								cssClass="form-control" hasChildLookup="false" hasId="true"
								showAll="false" disabled="${command.modeType eq 'V' || not empty command.marriageDTO.applicantDetailDto.applicantTitle }"
								selectOptionLabelCode="applicantinfo.label.select"
								isMandatory="true" />
							<label class="col-sm-2 control-label required-control"
								for="firstName"><spring:message
									code="applicantinfo.label.firstname" text="First Name" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text"
									class="form-control hasNameClass"
									readonly="${command.modeType eq 'V' || not empty command.marriageDTO.applicantDetailDto.applicantFirstName}"
									 maxlength="200"
									path="marriageDTO.applicantDetailDto.applicantFirstName"
									id="firstName" data-rule-required="true"></form:input>
							</div>
						</div>
					
					
						<div class="form-group">
							<label class="col-sm-2 control-label" for="middleName"><spring:message
								code="applicantinfo.label.middlename" text="Middle Name"/></label>
							<div class="col-sm-4">
								<form:input name="" type="text"
									class="form-control hasNameClass"
									readonly="${command.modeType eq 'V' || not empty command.marriageDTO.applicantDetailDto.applicantFirstName}"
									 maxlength="200"
									path="marriageDTO.applicantDetailDto.applicantMiddleName"
									id="middleName"></form:input>
							</div>
							<label class="col-sm-2 control-label required-control"
								for="lastName"><spring:message
									code="applicantinfo.label.lastname" text="Last Name" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text"
									class="form-control hasNameClass"
									readonly="${command.modeType eq 'V' || not empty command.marriageDTO.applicantDetailDto.applicantFirstName}"
									 maxlength="200"
									path="marriageDTO.applicantDetailDto.applicantLastName"
									id="lastName" data-rule-required="true"></form:input>
								</div>
							</div>
						
						<div class="form-group">
						
							<label class="col-sm-2 control-label required-control"><spring:message
								code="applicantinfo.label.mobile" text="Mobile No" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control hasMobileNo"
									readonly="${command.modeType eq 'V' || not empty command.marriageDTO.applicantDetailDto.applicantFirstName}"
									 maxlength="10"
									path="marriageDTO.applicantDetailDto.mobileNo" id="mobileNo"
									data-rule-required="true"></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message
									code="applicantinfo.label.email" text="Email Id" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text"
									class="form-control preventSpace" maxlength="200"
									readonly="${command.modeType eq 'V' || not empty command.marriageDTO.applicantDetailDto.applicantFirstName}"
									path="marriageDTO.applicantDetailDto.emailId" id="emailId"></form:input>
								</div>
							</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
								code="address.line1" text="Line 1" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text"
									class="form-control preventSpace"
									readonly="${command.modeType eq 'V' || not empty command.marriageDTO.applicantDetailDto.applicantFirstName}"
									path="marriageDTO.applicantDetailDto.areaName" id="areaName"
									maxlength="1000" data-rule-required="true"></form:input>
							</div>
							<label class="col-sm-2 control-label"><spring:message
									code="address.line2" text="Line 2" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text"
									class="form-control preventSpace"
									readonly="${command.modeType eq 'V' || not empty command.marriageDTO.applicantDetailDto.applicantFirstName}"
									 maxlength="50"
									path="marriageDTO.applicantDetailDto.villageTownSub"
									id="villTownCity"></form:input>
								</div>
							</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
								code="address.line3" text="Line 3" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text"
									class="form-control preventSpace"
									readonly="${command.modeType eq 'V'  || not empty command.marriageDTO.applicantDetailDto.applicantFirstName}" 
									maxlength="300"
									path="marriageDTO.applicantDetailDto.roadName" id="roadName"></form:input>
							</div>
							<label class="col-sm-2 control-label required-control"><spring:message
									code="applicantinfo.label.pincode" text="Pincode" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control hasNumber"
									readonly="${command.modeType eq 'V' || not empty command.marriageDTO.applicantDetailDto.applicantFirstName}"
									path="marriageDTO.applicantDetailDto.pinCode" id="pinCode" minlength="6"
									maxlength="6" data-rule-required="true"></form:input>
								</div>
							</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
							code="applicantinfo.label.aadhaar" text="Aadhar NO" /></label>
							<div class="col-sm-4">
								<form:input name="" type="text" class="form-control "
									readonly="${command.modeType eq 'V' || not empty command.marriageDTO.applicantDetailDto.applicantFirstName}"
									path="marriageDTO.applicantDetailDto.aadharNo" id="aadharNo"
									maxlength="14" data-mask="9999 9999 9999" />
							</div>
					
							<apptags:lookupFieldSet cssClass="form-control required-control"
								baseLookupCode="HWZ" hasId="true" pathPrefix="marriageDTO.ward"
								hasLookupAlphaNumericSort="true" disabled="${command.modeType eq 'V'}"
								hasSubLookupAlphaNumericSort="true" showAll="false"
								isMandatory="true" />
					
						</div>
					</div>
				</div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" LogOut.html href="#MarriageDet" tabindex="-1"><spring:message
								code="mrm.marriage.details" text="Marriage Details"/></a>
					</h4>
				</div>
				<div id="MarriageDet" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="form-group">
							<apptags:date fieldclass="datepicker"
								labelCode="mrm.marriage.marriageDate"
								readonly="${command.modeType eq 'V'}" isMandatory="true"
								datePath="marriageDTO.marDate"></apptags:date>

							<%-- <apptags:date fieldclass="datepicker" isMandatory="true"
								labelCode="mrm.marriage.applicationDate" readonly="true"
								datePath="marriageDTO.appDate"></apptags:date> --%>

						</div>

						<div class="form-group">
							<apptags:textArea labelCode="mrm.marriage.placeMarE"
								path="marriageDTO.placeMarE"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								 maxlegnth="100">
							</apptags:textArea>
							<apptags:textArea labelCode="mrm.marriage.placeMarR"
								path="marriageDTO.placeMarR"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								 maxlegnth="100"></apptags:textArea>
						</div>

						<div class="form-group">
							<c:set var="baseLookupCodeRLG" value="RLG" />
							<label class="col-sm-2 control-label required-control "
								for="assetgroup"> <spring:message
									code="mrm.marriage.personalLaw" text="Personal Law"/>
							</label>
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCodeRLG)}"
								path="marriageDTO.personalLaw"
								disabled="${command.modeType eq 'V'}"
								cssClass="form-control chosen-select-no-results"
								hasChildLookup="false" hasId="true" showAll="false"
								selectOptionLabelCode="Select" isMandatory="true" />
						</div>

					</div>
				</div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse1" href="#a1" tabindex="-1"><spring:message
								code="mrm.priest.details" text="Priest Details" /></a>
					</h4>
				</div>
				<div id="a1" class="panel-collapse collapse in">
					<div class="panel-body">

						<div class="form-group">
							<apptags:input labelCode="mrm.marriage.priestNameE"
								path="marriageDTO.priestNameE"
								isReadonly="${command.modeType eq 'V'}" isMandatory="true"
								cssClass="hasCharacter form-control" maxlegnth="99"></apptags:input>
							<apptags:input labelCode="mrm.marriage.priestNameR"
								path="marriageDTO.priestNameR"
								isReadonly="${command.modeType eq 'V'}" isMandatory="true"
								cssClass="  form-control hasNameClass" maxlegnth="99"></apptags:input>
						</div>
						<div class="form-group">
							<apptags:textArea labelCode="mrm.marriage.priestFullAdd"
								path="marriageDTO.priestAddress"
								isDisabled="${command.modeType eq 'V'}" isMandatory="true"
								 maxlegnth="499"></apptags:textArea>

							<label class="col-sm-2 control-label required-control">
								<spring:message code="mrm.marriage.religion" text="Religion" />
							</label>
							<c:set var="baseLookupCode" value="RLG" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="marriageDTO.priestReligion"
								disabled="${command.modeType eq 'V'}" hasChildLookup="false"
								hasId="true" showAll="false" selectOptionLabelCode="Select"
								isMandatory="true"
								cssClass="form-control chosen-select-no-results" />
						</div>

						<div class="form-group">
							<apptags:input labelCode="mrm.marriage.age"
								path="marriageDTO.priestAge"
								isReadonly="${command.modeType eq 'V'}" isMandatory="true"
								cssClass="form-control hasNumber" maxlegnth="3"></apptags:input>
						</div>

					</div>
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
														code="mrm.srno" text="Sr No" /></label></th>
											<th><label class="tbold"><spring:message
														code="mrm.documentName" text="Document Name" /></label></th>
											<th><spring:message code="mrm.documentDesc"
													text="Document Description" /></th>
											<th><label class="tbold"><spring:message code="mrm.downlaod" text="Download" /></label></th>
										</tr>
										
										
									          	<c:forEach items="${command.documentList}" var="lookUp" varStatus="index">
														<tr>
														<td class="text-center"><label>${index.count}</label></td>
															<c:choose>
																<c:when
																	test="${userSession.getCurrent().getLanguageId() eq 1}">
																	<td class="text-center"><label>${lookUp.doc_DESC_ENGL}</label></td>
																</c:when>
																<c:otherwise>
																	<td class="text-center"><label>${lookUp.doc_DESC_Mar}</label></td>
																</c:otherwise>
															</c:choose>
															<td class="text-center"><label>${lookUp.docDescription}</label></td>								
															<td align="center"><apptags:filedownload
																	filename="${lookUp.documentName}"
																	filePath="${lookUp.uploadedDocumentPath}"
																	actionUrl="MarriageRegistration.html?Download">
																</apptags:filedownload></td>
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
		
			
		<c:if test="${command.approvalProcess ne 'Y' }">
			<div class="text-center">
				<c:choose>
					<c:when
						test="${command.modeType eq 'C' || command.modeType eq 'V' || command.modeType eq 'D'}">
						<c:choose>
							<c:when test="${command.modeType eq 'C'  }">
								<c:set var="backButtonAction" value="backToDashBoard()" />
							</c:when>
							<c:otherwise>
								<c:set var="backButtonAction" value="backToDashBoard()" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<%-- <c:set var="backButtonAction" value="backToHomePage()" /> --%>
					</c:otherwise>
				</c:choose>

				<c:if
					test="${command.modeType eq 'C' || command.modeType eq 'E' || command.modeType eq 'D'}">
					<button type="button" class="button-input btn btn-success"
						name="button" value="Save" onclick="saveMarriage(this)" id="save">
						<spring:message code="mrm.button.save&next" text="Save & Next" />
					</button>
				</c:if>
				<c:if test="${command.modeType eq 'C' || command.modeType eq 'D'}">
					<button type="Reset" class="btn btn-warning" onclick="resetMarriageInfo()">
						<spring:message code="mrm.button.reset" text="Reset" />
					</button>
				</c:if>

				<button type="button" class="btn btn-danger" name="button" id="Back"
					value="Back" onclick="${backButtonAction}">
					<spring:message code="mrm.button.back" text="Back" />
				</button>
			</div>
		</c:if>
	</form:form>
</div>

<script>
	var marriageDate = $("#marriageTabForm input[id=marDate]").val();
	
	var applDate = $("#marriageTabForm input[id=appDate]").val();
	if (marriageDate) {
		$("#marriageTabForm input[id=marDate]").val(marriageDate.split(' ')[0]);
	}
	if (applDate) {
		$("#marriageTabForm input[id=appDate]").val(applDate.split(' ')[0]);
	}
</script>