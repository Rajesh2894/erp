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

<div class="panel panel-default">
<div class="panel-heading">
  <h4 class="panel-title">
	<a data-toggle="collapse" class="" data-parent="#accordion_single_collapse" href="#Applicant"><spring:message code="applicantinfo.label.header.care" text="Applicant Information"/>
	</a>
 </h4>
</div>
<div id="Applicant" class="panel-collapse in">
 <div class="panel-body">
	<div class="form-group">
		<label class="col-sm-2 control-label required-control" for="applicantTitle"><spring:message code="applicantinfo.label.title"/></label>
		<c:set var="baseLookupCode" value="TTL" />
		<apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
			path="careApplicantDetails.applicantTitle" cssClass="form-control"
			hasChildLookup="false" hasId="true" showAll="false"
			selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" />
		<label class="col-sm-2 control-label required-control" for="firstName"><spring:message code="applicantinfo.label.firstname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="careApplicantDetails.applicantFirstName" id="firstName"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="middleName"><spring:message code="applicantinfo.label.middlename"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="careApplicantDetails.applicantMiddleName" id="middleName"></form:input>
		</div>
		<label class="col-sm-2 control-label required-control" for="lastName"><spring:message code="applicantinfo.label.lastname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="careApplicantDetails.applicantLastName" id="lastName"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label required-control" for="gender"><spring:message code="applicantinfo.label.gender"/></label>
			<c:set var="baseLookupCode" value="GEN" />
		 <apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
			path="careApplicantDetails.gender" cssClass="form-control"
			hasChildLookup="false" hasId="true" showAll="false"
			selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" />
			
		<label class="col-sm-2 control-label required-control" for="mobileNo"><spring:message code="applicantinfo.label.mobile"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasNumber"
				maxlength="10" path="careApplicantDetails.mobileNo" id="mobileNo"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="emailId"><spring:message code="applicantinfo.label.email"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="careApplicantDetails.emailId" id="emailId"></form:input>
		</div>
		<label class="col-sm-2 control-label" for="flatNo"><spring:message code="applicantinfo.label.flatbuildingno"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="careApplicantDetails.flatBuildingNo" id="flatNo"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="buildingName"><spring:message code="applicantinfo.label.buildingname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="careApplicantDetails.buildingName" id="buildingName"></form:input>
		</div>
		<label class="col-sm-2 control-label" for="roadName"><spring:message code="applicantinfo.label.roadname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="careApplicantDetails.roadName" id="roadName"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="blockName"><spring:message code="applicantinfo.label.blockname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="careApplicantDetails.blockName" id="blockName" maxlength="10"></form:input>
		</div>
		<label class="col-sm-2 control-label required-control" for="areaName"><spring:message code="applicantinfo.label.areaname"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="careApplicantDetails.areaName" id="areaName"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label required-control" for="villTownCity"><spring:message code="applicantinfo.label.villtowncity"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control"
				path="careApplicantDetails.villageTownSub" id="villTownCity"></form:input>
		</div>
		<label class="col-sm-2 control-label required-control" for="pinCode"><spring:message code="applicantinfo.label.pincode"/></label>
		<div class="col-sm-4">
			<form:input name="" type="text" class="form-control hasNumber"
				path="careApplicantDetails.pinCode" id="pinCode" maxlength="6"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="aadharNo"><spring:message code="applicantinfo.label.aadhaar"/></label>
		<div class="col-sm-4">
       <form:input name="" type="text" class="form-control"
				path="careApplicantDetails.aadharNo" id="aadharNo" maxlength="12" data-mask="9999 9999 9999"/>

		</div>
	</div>
	
</div>
</div>
</div>


