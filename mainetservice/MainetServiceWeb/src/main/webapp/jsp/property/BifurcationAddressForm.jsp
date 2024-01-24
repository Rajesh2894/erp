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
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- End JSP Necessary Tags -->



<div class="accordion-toggle">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#propertyAddress"><spring:message
				code="property.Propertyaddress" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="propertyAddress">
		<div class="form-group">
			
			<apptags:textArea labelCode="property.propertyaddress" path="provisionalAssesmentMstDto.assAddress" maxlegnth="1000" cssClass="preventSpace" isReadonly="true"></apptags:textArea>
			<apptags:input cssClass="" labelCode="property.location" path="provisionalAssesmentMstDto.locationName" maxlegnth="254" isReadonly="true" ></apptags:input>
		
        </div> 
         
		
		
		<div class="form-group">
		<label class="col-sm-2 control-label" for="proAssPincode"><spring:message code="property.pincode"/></label>
		<div class="col-sm-4">
			<form:input  type="text" class="form-control hasPincode"
				 path="provisionalAssesmentMstDto.assPincode" id="proAssPincode" maxlength="6"  data-rule-digits="true" data-rule-minlength="6" data-rule-maxlength="6" readOnly="true"></form:input>
		</div>
		<label class="col-sm-2 control-label " for="tppKhataNo"><spring:message code="property.khata.no"/></label>
		<div class="col-sm-4">
			<form:input  type="text" class="form-control" path="provisionalAssesmentMstDto.tppKhataNo" id="tppKhataNo" readOnly="true"></form:input>
		</div>
		
		</div>
		
		<div class="form-group">
				<label class="col-sm-2 control-label " for="tppPlotNo"><spring:message	code="property.house.no" /></label>
				<div class="col-sm-4">
					<form:input type="text" class="form-control" path="provisionalAssesmentMstDto.tppPlotNo" id="tppPlotNo" readOnly="true"></form:input>
				</div>
				<label class="col-sm-2 control-label " for="tppSurveyNumber"><spring:message code="property.khasra.no" /></label>
				<div class="col-sm-4">
					<form:input type="text" class="form-control" path="provisionalAssesmentMstDto.tppSurveyNumber" id="tppSurveyNumber" readOnly="true"></form:input>
				</div>
			</div>
	
	</div>
</div>

<div class="accordion-toggle">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#correspondenceAddress"><spring:message
				code="property.correspondenceaddress" /></a>
	</h4>

	<div class="panel-collapse collapse in" id="correspondenceAddress">
			
		<div class="form-group">
			<apptags:textArea labelCode="property.correspondenceaddress" path="provisionalAssesmentMstDto.assCorrAddress" maxlegnth="1000" cssClass="preventSpace" isDisabled="true"></apptags:textArea>			
			
<%-- 			<label class="col-sm-2 control-label" for="proAssCorrPincode"><spring:message code="property.pincode"/></label> --%>
<!-- 			<div class="col-sm-4"> -->
<%-- 			<form:input  type="text" class="form-control hasPincode" --%>
<%-- 				 path="provisionalAssesmentMstDto.proAssCorrPincode" id="proAssCorrPincode" maxlength="6" data-rule-required="true" data-rule-digits="true" data-rule-minlength="6" data-rule-maxlength="6"></form:input> --%>
<!-- 			</div> -->
			<apptags:input labelCode="property.pincode" path="provisionalAssesmentMstDto.assCorrPincode" cssClass="hasPincode" maxlegnth="6" isDisabled="true"></apptags:input>	
		</div>
		<%-- <div class="form-group">
			<apptags:input labelCode="property.email" path="provisionalAssesmentMstDto.assCorrEmail" maxlegnth="254" dataRuleEmail="true" cssClass="hasNoSpace"></apptags:input>
		</div>	 --%>
	</div>	
</div>
