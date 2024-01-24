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
<script src="js/mainet/script-library.js"></script>

<div class="form-group">
	<label class="col-sm-2 control-label required-control"><spring:message
			code="adh.new.advertisement.applicantinfo.title" /></label>

	<apptags:lookupField items="${command.getLevelData('TTL')}"
		path="advertisementReqDto.applicantDetailDTO.applicantTitle"
		cssClass="form-control" hasChildLookup="false" hasId="true"
		showAll="false" selectOptionLabelCode="adh.select" isMandatory="true" />


	<label class="col-sm-2 control-label required-control"><spring:message
			code="adh.new.advertisement.applicantinfo.firstname" /></label>
	<div class="col-sm-4">
		<form:input class="form-control preventSpace hasCharacter"
			path="advertisementReqDto.applicantDetailDTO.applicantFirstName"
			id="firstName"></form:input>
	</div>
</div>

<div class="form-group">
	<label class="col-sm-2 control-label"><spring:message
			code="adh.new.advertisement.applicantinfo.middlename" /></label>
	<div class="col-sm-4">
		<form:input class="form-control preventSpace hasCharacter"
			path="advertisementReqDto.applicantDetailDTO.applicantMiddleName"
			id="middleName"></form:input>
	</div>
	<label class="col-sm-2 control-label required-control" for="lastName"><spring:message
			code="adh.new.advertisement.applicantinfo.lastname" /></label>
	<div class="col-sm-4">
		<form:input class="form-control preventSpace hasCharacter"
			path="advertisementReqDto.applicantDetailDTO.applicantLastName"
			id="lastName" data-rule-required="true"></form:input>
	</div>
</div>

<div class="form-group">
	<label class="col-sm-2 control-label required-control"><spring:message
			code="adh.new.advertisement.applicantinfo.gender" /></label>
	<apptags:lookupField items="${command.getLevelData('GEN')}"
		path="advertisementReqDto.applicantDetailDTO.gender"
		cssClass="form-control" hasChildLookup="false" hasId="true"
		showAll="false" selectOptionLabelCode="adh.select" isMandatory="true" />

	<label class="col-sm-2 control-label required-control"><spring:message
			code="adh.new.advertisement.applicantinfo.mobile" /></label>
	<div class="col-sm-4">
		<form:input class="form-control hasNumber"
			path="advertisementReqDto.applicantDetailDTO.mobileNo" id="mobileNo"
			maxlength="10" onblur="validateMobileNumber()"></form:input>
		<span id="mobNumber"
			style="padding-left: 5px; padding-bottom: 3px; font-size: 15px; font-family: serif;"></span>
	</div>
</div>

<div class="form-group">
	<label class="col-sm-2 control-label"><spring:message
			code="adh.new.advertisement.applicantinfo.email" /></label>
	<div class="col-sm-4">
		<form:input type="text" class="form-control"
			path="advertisementReqDto.applicantDetailDTO.emailId" id="emailId"
			onchange="emailValidation();"></form:input>
		<span id="email"
			style="padding-left: 5px; padding-bottom: 3px; font-size: 15px; font-family: serif;"></span>
	</div>
	<label class="col-sm-2 control-label"><spring:message
			code="adh.new.advertisement.applicantinfo.flatbuildingno" /></label>
	<div class="col-sm-4">
		<form:input type="text" class="form-control"
			path="advertisementReqDto.applicantDetailDTO.flatBuildingNo"
			id="flatNo"></form:input>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label"><spring:message
			code="adh.new.advertisement.applicantinfo.buildingname" /></label>
	<div class="col-sm-4">
		<form:input class="form-control"
			path="advertisementReqDto.applicantDetailDTO.buildingName"
			id="buildingName"></form:input>
	</div>
	<label class="col-sm-2 control-label"><spring:message
			code="adh.new.advertisement.applicantinfo.roadname" /></label>
	<div class="col-sm-4">
		<form:input class="form-control"
			path="advertisementReqDto.applicantDetailDTO.roadName" id="roadName"></form:input>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label"><spring:message
			code="adh.new.advertisement.applicantinfo.blockname" /></label>
	<div class="col-sm-4">
		<form:input class="form-control"
			path="advertisementReqDto.applicantDetailDTO.blockName"
			id="blockName" maxlength="10"></form:input>
	</div>

	<label class="col-sm-2 control-label required-control"><spring:message
			code="adh.new.advertisement.applicantinfo.areaname" /></label>
	<div class="col-sm-4">
		<form:input class="form-control"
			path="advertisementReqDto.applicantDetailDTO.areaName" id="areaName"
			data-rule-required="true"></form:input>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label required-control"><spring:message
			code="adh.new.advertisement.applicantinfo.villtowncity" /></label>
	<div class="col-sm-4">
		<form:input class="form-control"
			path="advertisementReqDto.applicantDetailDTO.villageTownSub"
			id="villTownCity" data-rule-required="true"></form:input>
	</div>

	<label class="col-sm-2 control-label required-control"><spring:message
			code="adh.new.advertisement.applicantinfo.pincode" /></label>
	<div class="col-sm-4">
		<form:input class="form-control hasPincode "
			path="advertisementReqDto.applicantDetailDTO.pinCode" id="pinCode"
			maxlength="6" onblur="validatePinCodeNumber();"></form:input>
		<span id="pinCodeNumber"
			style="padding-left: 5px; padding-bottom: 3px; font-size: 15px; font-family: serif;"></span>
	</div>
</div>
<div class="form-group">
	<label class="col-sm-2 control-label"><spring:message
			code="adh.new.advertisement.applicantinfo.aadhaar" /></label>
	<div class="col-sm-4">
		<form:input class="form-control"
			path="advertisementReqDto.applicantDetailDTO.aadharNo" id="aadharNo"
			data-mask="9999 9999 9999" maxlength="12" />

	</div>
	<%-- <label class="col-sm-2 control-label"><spring:message
			code="adh.new.advertisement.applicantinfo.pan.no" text="PAN No." /></label>
	<div class="col-sm-4">
		<form:input type="text" class="form-control"
			path="advertisementReqDto.applicantDetailDTO.panNo" id="panId"
			data-rule-maxlength="12" />
	</div> --%>
</div>
