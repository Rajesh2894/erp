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
<script src="js/mainet/validation.js"></script>

<!-- End JSP Necessary Tags -->
<div class="accordion-toggle">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#BuildingDetails"><spring:message text="Land / Building Details"/></a>
	</h4>
	<div class="panel-collapse collapse" id="BuildingDetails">
		<div class="form-group">
			
			<apptags:input  labelCode="property.yearofacquisition" isDisabled="true" path="provisionalAssesmentMstDto.assAcqDate" maxlegnth="1000" cssClass="hasNoSpace"></apptags:input>
			<apptags:input  labelCode="property.propertytype" isDisabled="true"  path="provisionalAssesmentMstDto.assPropType1" maxlegnth="1000" cssClass="hasNoSpace" ></apptags:input>	
		</div>
		<div class="form-group">
	    <apptags:input  labelCode="property.totalplot" isDisabled="true" path="provisionalAssesmentMstDto.assPlotArea" maxlegnth="1000" cssClass="hasNoSpace"></apptags:input>
			<apptags:input  labelCode="property.buildup" isDisabled="true"  path="provisionalAssesmentMstDto.assBuitAreaGr" maxlegnth="1000" cssClass="hasNoSpace" ></apptags:input>	
		</div>
		


	</div>
</div>
