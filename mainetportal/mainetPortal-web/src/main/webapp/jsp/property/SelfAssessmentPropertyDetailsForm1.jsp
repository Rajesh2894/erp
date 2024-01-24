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
	
	<div class="panel-collapse collapse" id="propertyDetail">
			<div class="form-group">
				<apptags:input cssClass="alphaNumeric hasNoSpace" labelCode="propertydetails.oldpropertyno" isDisabled="true" path="provisionalAssesmentMstDto.assOldpropno" maxlegnth="20"></apptags:input>

				<apptags:input cssClass="alphaNumeric hasNoSpace"  labelCode="propertydetails.csnno" isDisabled="true" path="provisionalAssesmentMstDto.tppPlotNoCs" maxlegnth="50"></apptags:input>
			</div>
			<div class="form-group">
				<apptags:input cssClass="alphaNumeric hasNoSpace"  labelCode="propertydetails.surveyno." isDisabled="true" path="provisionalAssesmentMstDto.tppSurveyNumber" maxlegnth="25"></apptags:input>

				<apptags:input cssClass="alphaNumeric hasNoSpace"  labelCode="propertydetails.khatano" isDisabled="true" path="provisionalAssesmentMstDto.tppKhataNo" maxlegnth="50"></apptags:input>

			</div>
			<div class="form-group">
				<apptags:input cssClass="alphaNumeric hasNoSpace"  labelCode="propertydetails.tojino" isDisabled="true" path="provisionalAssesmentMstDto.tppTojiNo" maxlegnth="50"></apptags:input>

				<apptags:input cssClass="alphaNumeric hasNoSpace"  labelCode="propertydetails.plotno" isDisabled="true" path="provisionalAssesmentMstDto.tppPlotNo" maxlegnth="50"></apptags:input>

			</div>
			<div class="form-group">
				<apptags:input cssClass="alphaNumeric hasNoSpace"  labelCode="propertydetails.streetno" isDisabled="true" path="provisionalAssesmentMstDto.assStreetNo" maxlegnth="500"></apptags:input>

				<apptags:input cssClass="alphaNumeric hasNoSpace"  labelCode="propertydetails.village" isDisabled="true" path="provisionalAssesmentMstDto.tppVillageMauja" maxlegnth="50"></apptags:input>

			</div>
		
	</div>
</div>

