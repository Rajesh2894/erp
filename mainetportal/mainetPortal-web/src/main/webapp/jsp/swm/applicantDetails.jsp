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


   
	<div class="panel-body">
		<div class="form-group">
		<label class="col-sm-2 control-label required-control" for="applicantTitle"><spring:message code="applicantinfo.label.title"/></label>
		<c:set var="baseLookupCode" value="TTL" />
		<apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
			path="collectorReqDTO.applicantDetailDto.applicantTitle" cssClass="form-control"
			hasChildLookup="false" hasId="true" showAll="false"
			selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" />
		<label class="col-sm-2 control-label required-control" for="firstName"><spring:message code="applicantinfo.label.firstname"/></label>
		<div class="col-sm-4">
			<form:input  type="text" class="form-control"
				path="collectorReqDTO.applicantDetailDto.applicantFirstName" id="firstName" data-rule-required="true"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="middleName"><spring:message code="applicantinfo.label.middlename"/></label>
		<div class="col-sm-4">
			<form:input  type="text" class="form-control"
				path="collectorReqDTO.applicantDetailDto.applicantMiddleName" id="middleName"></form:input>
		</div>
		<label class="col-sm-2 control-label required-control" for="lastName"><spring:message code="applicantinfo.label.lastname"/></label>
		<div class="col-sm-4">
			<form:input  type="text" class="form-control"
				path="collectorReqDTO.applicantDetailDto.applicantLastName" id="lastName" data-rule-required="true"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label required-control" for="gender"><spring:message code="applicantinfo.label.gender"/></label>
			<c:set var="baseLookupCode" value="GEN" />
		<apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
			path="collectorReqDTO.applicantDetailDto.gender" cssClass="form-control"
			hasChildLookup="false" hasId="true" showAll="false"
			selectOptionLabelCode="applicantinfo.label.select" isMandatory="true" />
		<label class="col-sm-2 control-label required-control" for="mobileNo"><spring:message code="applicantinfo.label.mobile"/></label>
		<div class="col-sm-4">
			<form:input  type="text" class="form-control hasNumber"
				 path="collectorReqDTO.applicantDetailDto.mobileNo" id="mobileNo" data-rule-required="true" data-rule-digits="true" data-rule-minlength="10" data-rule-maxlength="10"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="emailId"><spring:message code="applicantinfo.label.email"/></label>
		<div class="col-sm-4">
			<form:input  type="text" class="form-control hasemailclass"
				path="collectorReqDTO.applicantDetailDto.emailId" id="emailId" data-rule-email="true"></form:input>
		</div>	
			<label class="col-sm-2 control-label required-control" for="areaName"><spring:message code="disposal.site.master.adress" text="Address"/></label>
		<div class="col-sm-4">
			<form:textarea  type="text" class="form-control"
				path="collectorReqDTO.applicantDetailDto.areaName" id="areaName" data-rule-required="true"></form:textarea>
		</div>
	</div>

	<div class="form-group">		
		<label class="col-sm-2 control-label required-control" for="pinCode"><spring:message code="applicantinfo.label.pincode"/></label>
		<div class="col-sm-4">
			<form:input  type="text" class="form-control hasPincode"
				path="collectorReqDTO.applicantDetailDto.pinCode" id="pinCode"  data-rule-required="true" data-rule-maxlength="6" data-rule-digits="true"></form:input>
		</div>
	</div>

	</div>

	


