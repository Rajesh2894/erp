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
	<h4 class="margin-top-10 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#propertyDetail"><spring:message
				code="property.Propertydetail" /></a>
	</h4>
	
	<div class="panel-collapse collapse in" id="propertyDetail">
			<div class="form-group">
				<apptags:input cssClass="alphaNumeric preventSpace" labelCode="propertydetails.oldpropertyno" path="provisionalAssesmentMstDto.assOldpropno" maxlegnth="20"></apptags:input>

				<apptags:input cssClass="alphaNumeric preventSpace" labelCode="propertydetails.csnno" path="provisionalAssesmentMstDto.tppPlotNoCs" maxlegnth="50"></apptags:input>
			</div>
			<div class="form-group">
<%-- 				<apptags:input cssClass="alphaNumeric preventSpace" isMandatory="true" labelCode="propertydetails.surveyno." path="provisionalAssesmentMstDto.tppSurveyNumber" maxlegnth="25"></apptags:input>
 --%>
				<apptags:input cssClass="alphaNumeric preventSpace" labelCode="propertydetails.khatano" path="provisionalAssesmentMstDto.tppKhataNo" maxlegnth="50"></apptags:input>
				<apptags:input cssClass="alphaNumeric preventSpace" isMandatory="true" labelCode="propertydetails.plotno" path="provisionalAssesmentMstDto.tppPlotNo" maxlegnth="50"></apptags:input>
				
			</div>
			<%-- <div class="form-group">
				<apptags:input cssClass="alphaNumeric preventSpace" isMandatory="true" labelCode="propertydetails.tojino" path="provisionalAssesmentMstDto.tppTojiNo" maxlegnth="50"></apptags:input>

				<apptags:input cssClass="alphaNumeric preventSpace" isMandatory="true" labelCode="propertydetails.plotno" path="provisionalAssesmentMstDto.tppPlotNo" maxlegnth="50"></apptags:input>

			</div> --%>
			<%-- <div class="form-group">			
					<apptags:input cssClass="alphaNumeric preventSpace" isMandatory="true" labelCode="propertydetails.streetno" path="provisionalAssesmentMstDto.assStreetNo" maxlegnth="500"></apptags:input>
					<apptags:input cssClass="alphaNumeric preventSpace" isMandatory="true" labelCode="propertydetails.village" path="provisionalAssesmentMstDto.tppVillageMauja" maxlegnth="50"></apptags:input>

			</div> --%>
		
	</div>
</div>

