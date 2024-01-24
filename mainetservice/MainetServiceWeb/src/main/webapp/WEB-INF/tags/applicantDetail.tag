<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ attribute name="wardZone" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@ attribute name="disabled" required="false" rtexprvalue="true" type="java.lang.Boolean"%>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script src="js/mainet/applicantDetails.js"></script>

<div class="accordion-toggle">
 <h4 class="margin-top-0 margin-bottom-10 panel-title">
	<a data-toggle="collapse" href="#Applicant"><spring:message code="applicantinfo.label.header"/></a>
 </h4>
<div class="panel-collapse collapse in" id="Applicant">
	<div class="form-group">
		<label class="col-sm-2 control-label required-control" for="applicantTitle"><spring:message code="applicantinfo.label.title"/></label>
		<c:set var="baseLookupCode" value="TTL" />
		<apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
			path="applicantDetailDto.applicantTitle" cssClass="form-control"
			hasChildLookup="false" hasId="true" showAll="false" disabled="${disabled}"
			selectOptionLabelCode="applicantinfo.label.select"  isMandatory="true" />
		<label class="col-sm-2 control-label required-control" for="firstName"><spring:message code="applicantinfo.label.firstname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasSpecialChara" readonly="${disabled}"
				path="applicantDetailDto.applicantFirstName" id="firstName" data-rule-required="true"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="middleName"><spring:message code="applicantinfo.label.middlename"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasSpecialChara" readonly="${disabled}"
				path="applicantDetailDto.applicantMiddleName" id="middleName"></form:input>
		</div>
		<label class="col-sm-2 control-label required-control" for="lastName"><spring:message code="applicantinfo.label.lastname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasSpecialChara" readonly="${disabled}"
				path="applicantDetailDto.applicantLastName" id="lastName" data-rule-required="true"></form:input>
		</div>
	</div>
	<div class="form-group">
		<%-- <label class="col-sm-2 control-label required-control"><spring:message code="applicantinfo.label.gender"/></label>
			<c:set var="baseLookupCode" value="GEN" />
		<apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
			path="applicantDetailDto.gender" cssClass="form-control"
			hasChildLookup="false" hasId="true" showAll="false" disabled="${disabled}"
			selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" /> --%>
			<label class="col-sm-2 control-label required-control"><spring:message code="applicantinfo.label.mobile"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasMobileNo" readonly="${disabled}"
				maxlength="10" path="applicantDetailDto.mobileNo" id="mobileNo" data-rule-required="true"></form:input>
		</div>
		<label class="col-sm-2 control-label"><spring:message code="applicantinfo.label.email"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control" readonly="${disabled}"
				path="applicantDetailDto.emailId" id="emailId"></form:input>
		</div>
		
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label required-control"><spring:message code="address.line1"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"  readonly="${disabled}"
				path="applicantDetailDto.areaName" id="areaName" data-rule-required="true"></form:input>
		</div>
		<label class="col-sm-2 control-label"><spring:message code="address.line2"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"  readonly="${disabled}"
				path="applicantDetailDto.villageTownSub" id="villTownCity"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message code="address.line3"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"  readonly="${disabled}"
				path="applicantDetailDto.roadName" id="roadName" ></form:input>
		</div>
		<label class="col-sm-2 control-label required-control"><spring:message code="applicantinfo.label.pincode"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasNumber"  readonly="${disabled}"
				path="applicantDetailDto.pinCode" id="pinCode" maxlength="6" data-rule-required="true"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"><spring:message code="applicantinfo.label.aadhaar"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasNumber" readonly="${disabled}"
				path="applicantDetailDto.aadharNo" id="aadharNo" maxlength="12" data-mask="9999 9999 9999"/>
		</div>
		<label class="col-sm-2 control-label required-control"><spring:message code="applicantinfo.label.isabovepovertyline"/></label>
		<div class="col-sm-4">
			<form:select cssClass="form-control" id="povertyLineId" path="applicantDetailDto.isBPL" data-rule-required="true" disabled="${disabled}">
				<form:option value="" ><spring:message code="applicantinfo.label.select"/></form:option>
				<form:option value="Y"><spring:message code="applicantinfo.label.yes"/></form:option>
				<form:option value="N"><spring:message code="applicantinfo.label.no"/></form:option>
			</form:select>
		</div>
	</div>
	<div class="form-group">
		<apptags:lookupFieldSet baseLookupCode="${wardZone}" hasId="true"
			showOnlyLabel="false" pathPrefix="applicantDetailDto.dwzid"
			isMandatory="true" hasLookupAlphaNumericSort="true"
			hasSubLookupAlphaNumericSort="true" disabled="${disabled}"
			cssClass="form-control  required-control"/>
	</div>

	<div class="form-group" id="bpldiv">
		<label class="col-sm-2 control-label required-control"><spring:message code="applicantinfo.label.bplno"/></label>
		<div class="col-sm-4">

			<form:input name="" type="text" class="form-control required-control"
				path="applicantDetailDto.bplNo" id="bplNo" maxlength="16" readonly="${disabled}"/>

		</div>
	</div>

</div>
</div>
