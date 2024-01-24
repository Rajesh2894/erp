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
	<h4 class="margin-top-10 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#propertyDetail"><spring:message
				code="property.Propertydetail" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="propertyDetail">
		
			
			<div class="form-group">
				<label for="assOldpropno" class="col-sm-2 control-label"><spring:message
						code="propertydetails.oldpropertyno" /></label>
				<div class="col-sm-4">
					<form:input id="assOldpropno" path="entity.proAssOldpropno"
							class="form-control" disabled="${viewMode}" />
				</div>
				<label for="tppPlotNoCs" class="col-sm-2 required-control"><spring:message
						code="propertydetails.csnno" />
				</label>
				<div class="col-sm-4">
					<form:input id="tppPlotNoCs" path="entity.tppPlotNoCs"
						class=" form-control" disabled="${viewMode}" />
				</div>
			</div>
			<div class="form-group">
				<label for="tppSurveyNumber" class="col-sm-2 required-control">
					<spring:message code="propertydetails.surveyno." />
				</label>
				<div class="col-sm-4">
					<form:input id="tppSurveyNumber" path="entity.tppSurveyNumber"
						class="form-control" disabled="${viewMode}" />
				</div>
				<label for="text-1492065774173" class="col-sm-2 required-control">
					<spring:message code="propertydetails.khatano" />
					
				</label>
				<div class="col-sm-4">
					<form:input id="tppKhataNo" path="entity.tppKhataNo"
						class="form-control" disabled="${viewMode}" />
				</div>
			</div>
			<div class="form-group">
				<label for="tppTojiNo" class="col-sm-2 required-control"><spring:message
						code="propertydetails.tojino" />
				</label>
				<div class="col-sm-4">
					<form:input id="tppTojiNo" path="entity.tppTojiNo"
						class="hasNumber form-control" disabled="${viewMode}" />
				</div>
				<label for="tppPlotNo" class="col-sm-2 required-control"><spring:message
						code="propertydetails.plotno" />
				</label>
				<div class="col-sm-4 ">
					<form:input id="tppPlotNo" path="entity.tppPlotNo"
						class="hasNumber form-control" disabled="${viewMode}" />
				</div>
			</div>
			<div class="form-group">
				<label for="tassStreetNo" class="col-sm-2 required-control"><spring:message
						code="propertydetails.streetno" /> 
				</label>
				<div class="col-sm-4">
					<form:input id="" path="entity.proAssStreetNo"
						class="form-control" disabled="${viewMode}" />
				</div>
				<label for="tppVillageMauja" class="col-sm-2 required-control"><spring:message
						code="propertydetails.village" /> 
				</label>
				<div class="col-sm-4">
					<form:input id="tppVillageMauja" path="entity.tppVillageMauja"
						class="form-control" disabled="${viewMode}" />
				</div>
			</div>
		
	</div>
</div>

