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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script type="text/javascript" src="js/rnl/service/estateBookingForm.js"></script>


<div class="panel-body">
	<div class="form-group">
		<label class="col-sm-2 control-label required-control"
			for="applicantTitle"><spring:message
				code="applicantinfo.label.title" /></label>
		<c:set var="baseLookupCode" value="TTL" />
		<apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
			path="bookingReqDTO.applicantDetailDto.applicantTitle"
			cssClass="form-control" hasChildLookup="false" hasId="true"
			showAll="false" selectOptionLabelCode="applicantinfo.label.select"
			isMandatory="true" />
		<label class="col-sm-2 control-label required-control" for="firstName"><spring:message
				code="applicantinfo.label.firstname" /></label>
		<div class="col-sm-4">
			<form:input type="text" class="form-control"
				path="bookingReqDTO.applicantDetailDto.applicantFirstName"
				id="firstName" data-rule-required="true"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="middleName"><spring:message
				code="applicantinfo.label.middlename" /></label>
		<div class="col-sm-4">
			<form:input type="text" class="form-control"
				path="bookingReqDTO.applicantDetailDto.applicantMiddleName"
				id="middleName"></form:input>
		</div>
		<label class="col-sm-2 control-label required-control" for="lastName"><spring:message
				code="applicantinfo.label.lastname" /></label>
		<div class="col-sm-4">
			<form:input type="text" class="form-control"
				path="bookingReqDTO.applicantDetailDto.applicantLastName"
				id="lastName" data-rule-required="true"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label required-control" for="gender"><spring:message
				code="applicantinfo.label.gender" /></label>
		<c:set var="baseLookupCode" value="GEN" />
		<apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
			path="bookingReqDTO.applicantDetailDto.gender"
			cssClass="form-control" hasChildLookup="false" hasId="true"
			showAll="false" selectOptionLabelCode="applicantinfo.label.select"
			isMandatory="true" />
		<label class="col-sm-2 control-label required-control" for="mobileNo"><spring:message
				code="applicantinfo.label.mobile" /></label>
		<div class="col-sm-4">
			<form:input type="text" class="form-control"
				path="bookingReqDTO.applicantDetailDto.mobileNo" id="mobileNo"
				maxlength="10" data-rule-required="true" data-rule-digits="true"
				data-rule-minlength="10" data-rule-maxlength="10"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="emailId"><spring:message
				code="applicantinfo.label.email" /></label>
		<div class="col-sm-4">
			<form:input type="text" class="form-control"
				path="bookingReqDTO.applicantDetailDto.emailId" id="emailId"
				data-rule-email="true"></form:input>
		</div>
		<label class="col-sm-2 control-label" for="flatNo"><spring:message
				code="applicantinfo.label.flatbuildingno" /></label>
		<div class="col-sm-4">
			<form:input type="text" class="form-control"
				path="bookingReqDTO.applicantDetailDto.flatBuildingNo" id="flatNo"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="buildingName"><spring:message
				code="applicantinfo.label.buildingname" /></label>
		<div class="col-sm-4">
			<form:input type="text" class="form-control"
				path="bookingReqDTO.applicantDetailDto.buildingName"
				id="buildingName"></form:input>
		</div>
		<label class="col-sm-2 control-label" for="roadName"><spring:message
				code="applicantinfo.label.roadname" /></label>
		<div class="col-sm-4">
			<form:input type="text" class="form-control"
				path="bookingReqDTO.applicantDetailDto.roadName" id="roadName"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="blockName"><spring:message
				code="applicantinfo.label.blockname" /></label>
		<div class="col-sm-4">
			<form:input type="text" class="form-control"
				path="bookingReqDTO.applicantDetailDto.blockName" id="blockName"
				maxlength="10"></form:input>
		</div>
		<label class="col-sm-2 control-label required-control" for="areaName"><spring:message
				code="applicantinfo.label.areaname" /></label>
		<div class="col-sm-4">
			<form:input type="text" class="form-control"
				path="bookingReqDTO.applicantDetailDto.areaName" id="areaName"
				data-rule-required="true"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label required-control"
			for="villTownCity"><spring:message
				code="applicantinfo.label.villtowncity" /></label>
		<div class="col-sm-4">
			<form:input type="text" class="form-control"
				path="bookingReqDTO.applicantDetailDto.villageTownSub"
				id="villTownCity" data-rule-required="true"></form:input>
		</div>
		<label class="col-sm-2 control-label required-control" for="pinCode"><spring:message
				code="applicantinfo.label.pincode" /></label>
		<div class="col-sm-4">
			<form:input type="text" class="form-control "
				path="bookingReqDTO.applicantDetailDto.pinCode" id="pinCode" minlength="6"
				maxlength="6" data-rule-required="true" data-rule-maxlength="6"
				data-rule-digits="true"></form:input>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="aadharNo"><spring:message
				code="applicantinfo.label.aadhaar" /></label>
		<div class="col-sm-4">
			<form:input type="text" class="form-control"
				path="bookingReqDTO.applicantDetailDto.aadharNo" id="aadharNo"
				maxlength="14" data-mask="9999 9999 9999" />

		</div>
		<label class="col-sm-2 control-label" for="panNo"><spring:message
				code="rnl.book.panno" text="PAN No." /></label>
		<div class="col-sm-4">
			<form:input type="text" class="form-control"
				path="bookingReqDTO.applicantDetailDto.panNo" id="panNo"
				minlength="10" maxlength="10" />
		</div>
	</div>
	
    <%-- <c:if test="${command.isBplShowHide eq 'Y'}"> --%>
	<div class="form-group">
		<label class="col-sm-2 control-label" for="Isbelow poverty line"><spring:message
				code="applicantinfo.label.isabovepovertyline"
				text="Is below poverty line" /></label>
		<div class="col-sm-4">
			<form:select path="bookingReqDTO.applicantDetailDto.isBPL" id="isBPL" class="form-control"
				data-rule-required="true">
				<form:option value="0">
					<spring:message code="applicantinfo.label.select" />
				</form:option>
				<form:option value="Y">
					<spring:message code="applicantinfo.label.yes" />
				</form:option>
				<form:option value="N">
					<spring:message code="applicantinfo.label.no" />
				</form:option>
			</form:select>
		</div>
		<div class="form-group" id="bpldiv" style="display: none">
			<label class="col-sm-2 control-label" for="bplNo"><spring:message
					code="applicantinfo.label.bplno" text="Bpl No." /></label>
			<div class="col-sm-4">
				<form:input type="text" class="form-control"
					path="bookingReqDTO.applicantDetailDto.bplNo" id="bplNo"
					maxlength="20" />

			</div>
		</div>

	</div>
	<%-- </c:if> --%>
	<%-- <c:if test="${command.orgShowHide eq 'Y'}"> --%>
	  <div class="form-group">
		<label class="col-sm-2 control-label" for="employeeOrganization"><spring:message
				code="application.label.isorg.employee"
				text="Is Organization Employee." /></label>
		<div class="col-sm-4">
			<form:select path="bookingReqDTO.applicantDetailDto.isOrganisationEmployeeFalg" id="empOrg" class="form-control"
				data-rule-required="true">
				<form:option value="0">
					<spring:message code="applicantinfo.label.select" />
				</form:option>
				<form:option value="Y">
					<spring:message code="applicantinfo.label.yes" />
				</form:option>
				<form:option value="N">
					<spring:message code="applicantinfo.label.no" />
				</form:option>
			</form:select>
		</div>
	  </div>
	<%-- </c:if> --%>