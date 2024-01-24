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
<div class="accordion-toggle">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#BuildingDetails"><spring:message
				code="property.buildingdetails" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="BuildingDetails">
		<div class="form-group">
			<label for="text-1492067589943" class="col-sm-2  required-control" ><spring:message code="property.yearofacquisition"/>
			</label>
			<div class="col-sm-4">
				<form:input type="text" path="entity.proAssAcqDate" id="assAcqDate"
				class="form-control datepicker2"  readonly="true"/>
			</div>
		
				<apptags:lookupFieldSet cssClass="form-control required-control"
					baseLookupCode="PTP" hasId="true" pathPrefix="entity.proAssPropType" isMandatory="true"
					hasLookupAlphaNumericSort="true"
					hasSubLookupAlphaNumericSort="true" showAll="true" />

			
		</div>
		<div class="form-group">

			<label for="text-1492067590207" class="col-sm-2  required-control"><spring:message
				code="property.totalplot" /></label>
			<div class="col-sm-4">
				<form:input id="assPlotArea" path="entity.proAssPlotArea"
				class="form-control" disabled="${viewMode}" />
			</div>
		</div>
		<div class="form-group">
			<label for="text-1492067707144" class="col-sm-2 required-control"><spring:message
				code="property.buildup" />
			</label>
			<div class="col-sm-4">
				<form:input id="assBuitAreaGr" path="entity.proAssBuitAreaGr"
				class="form-control" disabled="${viewMode}" />
			</div>
		</div>


	</div>
</div>
