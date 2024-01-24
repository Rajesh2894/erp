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
				code="property.LandDetail" /></a>
	</h4>
	
	<div class="panel-collapse collapse in" id="propertyDetail">
			<div class="form-group">
				<apptags:input cssClass="alphaNumeric preventSpace" labelCode="propertydetails.oldpropertyno" path="provisionalAssesmentMstDto.assOldpropno" maxlegnth="20" isDisabled="true"></apptags:input>

				<label class="col-sm-2 control-label"><spring:message code="property.landType" />
					</label>
				   
				   <c:set var="baseLookupCode" value="LDT" />
							<apptags:lookupField
									items="${command.getLevelData(baseLookupCode)}"
									path="provisionalAssesmentMstDto.assLandType" cssClass="form-control changeParameterClass"
									hasChildLookup="false" hasId="true" showAll="false"
									selectOptionLabelCode="property.select" disabled="true"/>
			</div>
			
			<div class="form-group">
				<apptags:input labelCode="property.district" path="provisionalAssesmentMstDto.assDistrict" maxlegnth="50" isDisabled="true"></apptags:input>
				<apptags:input labelCode="property.tahasil" path="provisionalAssesmentMstDto.assTahasil" maxlegnth="50" isDisabled="true"></apptags:input>
			</div>
			
			<div class="form-group">
				<apptags:input cssClass="alphaNumeric preventSpace" labelCode="propertydetails.village" path="provisionalAssesmentMstDto.tppVillageMauja" maxlegnth="50" isDisabled="true"></apptags:input>			
				<apptags:input cssClass="alphaNumeric preventSpace" labelCode="propertydetails.csnno" path="provisionalAssesmentMstDto.tppPlotNoCs" maxlegnth="50" isDisabled="true"></apptags:input>
			</div>
			
			<div class="form-group">
				<apptags:input labelCode="property.halkaNo" path="provisionalAssesmentMstDto.assHalkaNo" maxlegnth="50" isDisabled="true"></apptags:input>
				<apptags:input cssClass="alphaNumeric preventSpace" labelCode="propertydetails.plotno" path="provisionalAssesmentMstDto.tppPlotNo" maxlegnth="50" isDisabled="true"></apptags:input>
			</div>
			
			<div class="form-group">			
 				<apptags:input labelCode="property.propertyDeedNo" path="provisionalAssesmentMstDto.assDeedNo" maxlegnth="50" isDisabled="true"></apptags:input>
			</div>
	</div>
</div>

