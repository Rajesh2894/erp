<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script>
function resetOtherFields(){
		if ( $("#povertyLineId").val() == 'Y') {
	$("#bpldiv").show();
	 $("#bplNo").data('rule-required',true);
	}
	else
	{
	$("#bpldiv").hide();
	 $("#bplNo").data('rule-required',false);
	}  
}

</script>

<div class="panel panel-default">
<div class="panel-heading">
  <h4 class="panel-title">
	<a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#Applicant"><spring:message code="applicantinfo.label.header"/></a>
 </h4>
</div>
<div id="Applicant" class="panel-collapse collapse in">
 <div class="panel-body">
	<div class="form-group">
		<label class="col-sm-2 control-label required-control" for="applicantTitle"><spring:message code="applicantinfo.label.title"/></label>
		<c:set var="baseLookupCode" value="TTL" />
		<apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
			path="applicantDetailDto.applicantTitle" cssClass="form-control"
			hasChildLookup="false" hasId="true" showAll="false"
			selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" />
		<label class="col-sm-2 control-label required-control" for="firstName"><spring:message code="applicantinfo.label.firstname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasSpecialChara" maxlength="100"
				path="applicantDetailDto.applicantFirstName" id="firstName" data-rule-required="true"></form:input>
				 
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="middleName"><spring:message code="applicantinfo.label.middlename"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasSpecialChara" maxlength="100"
				path="applicantDetailDto.applicantMiddleName" id="middleName"></form:input>
		</div>
		<label class="col-sm-2 control-label required-control" for="lastName"><spring:message code="applicantinfo.label.lastname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasSpecialChara" maxlength="100"
				path="applicantDetailDto.applicantLastName" id="lastName" data-rule-required="true"></form:input>
				
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label required-control" for="gender"><spring:message code="applicantinfo.label.gender"/></label>
			<c:set var="baseLookupCode" value="GEN" />
		<apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
			path="applicantDetailDto.gender" cssClass="form-control"
			hasChildLookup="false" hasId="true" showAll="false"
			selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" />
		<label class="col-sm-2 control-label required-control" for="mobileNo"><spring:message code="applicantinfo.label.mobile"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasNumber"
				maxlength="10" path="applicantDetailDto.mobileNo" id="mobileNo" data-rule-minlength="10" data-rule-required="true"></form:input>
				
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="emailId"><spring:message code="applicantinfo.label.email"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="applicantDetailDto.emailId" id="emailId" data-rule-email="true"></form:input>
			
		</div>
		<label class="col-sm-2 control-label" for="flatNo"><spring:message code="applicantinfo.label.flatbuildingno"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control" maxlength="20"
				path="applicantDetailDto.flatBuildingNo" id="flatNo"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="buildingName"><spring:message code="applicantinfo.label.buildingname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control" maxlength="100"
				path="applicantDetailDto.buildingName" id="buildingName"></form:input>
		</div>
		<label class="col-sm-2 control-label" for="roadName"><spring:message code="applicantinfo.label.roadname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control" maxlength="100"
				path="applicantDetailDto.roadName" id="roadName"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="blockName"><spring:message code="applicantinfo.label.blockname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control" 
				path="applicantDetailDto.blockName" id="blockName" maxlength="10"></form:input>
		</div>
		<label class="col-sm-2 control-label required-control" for="areaName"><spring:message code="applicantinfo.label.areaname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control" maxlength="100"
				path="applicantDetailDto.areaName" id="areaName" data-rule-required="true"></form:input>
				
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label required-control" for="villTownCity"><spring:message code="applicantinfo.label.villtowncity"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"  maxlength="50"
				path="applicantDetailDto.villageTownSub" id="villTownCity" data-rule-required="true"></form:input>
					
		</div>
		<label class="col-sm-2 control-label required-control" for="pinCode"><spring:message code="applicantinfo.label.pincode"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasNumber"
				path="applicantDetailDto.pinCode" id="pinCode" maxlength="6" data-rule-required="true" data-rule-minlength="6"></form:input>
					
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="aadharNo"><spring:message code="applicantinfo.label.aadhaar"/></label>
		<div class="col-sm-4">
       <form:input name="" type="text" class="form-control"
				path="applicantDetailDto.aadharNo" id="aadharNo" data-mask="9999 9999 9999" />

		</div>
		<label class="col-sm-2 control-label required-control" for="povertyLineId"><spring:message code="applicantinfo.label.isabovepovertyline"/></label>
		<div class="col-sm-4">
			<form:select cssClass="form-control" id="povertyLineId" path="applicantDetailDto.isBPL" data-rule-required="true">
				<form:option value="" ><spring:message code="applicantinfo.label.select"/></form:option>
				<form:option value="Y"><spring:message code="applicantinfo.label.yes"/></form:option>
				<form:option value="N"><spring:message code="applicantinfo.label.no"/></form:option>
			</form:select>
				
		</div>
	</div>
	<div class="form-group ">
		<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
			showOnlyLabel="false" pathPrefix="applicantDetailDto.dwzid"
			isMandatory="true" hasLookupAlphaNumericSort="true"
			hasSubLookupAlphaNumericSort="true"
			cssClass="form-control  required-control" showAll="false" />
	</div>

	<div class="form-group" id="bpldiv">
		<label class="col-sm-2 control-label required-control" for="bplNo"><spring:message code="applicantinfo.label.bplno"/></label>
		<div class="col-sm-4">

			<form:input  type="text" class="form-control hideElement"
				path="applicantDetailDto.bplNo" id="bplNo" maxlength="16" data-rule-minlength="16"/>
	<div id='frmNewWaterForm_applicantDetailDto.bplNo_errorloc' class="error_strings"></div>
		</div>
	</div>
</div>
</div>
</div>


