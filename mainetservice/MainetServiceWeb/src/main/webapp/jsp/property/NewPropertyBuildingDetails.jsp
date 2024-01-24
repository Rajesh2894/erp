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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<style>
.addColor{
	background-color: #fff !important
}
</style>

<!-- End JSP Necessary Tags -->
<div class="accordion-toggle">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#BuildingDetails"><spring:message
				code="property.buildingdetails" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="BuildingDetails">
		<div class="form-group">
		<label class="col-sm-2 control-label required-control"><spring:message code="property.yearofacquisition"/></label>
		<div class="col-sm-4">
		<div class="input-group"> 
		<form:input path="provisionalAssesmentMstDto.assAcqDate" class="lessthancurrdate form-control mandColorClass dateClass addColor grpPropertyClass" disabled="${command.assType eq 'A' ? true : false}" id="proAssAcqDate" onChange="getFinancialYear();"  data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off"/>
		<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		<form:hidden path="leastFinYear" id="minFinancialYear"/>	
		</div>
		</div>
		
		<label class="col-sm-2 control-label required-control"><spring:message
									code="property.totalplot" text="Total plot area " /></label>
									<div class="col-sm-4">
		<spring:message code="property.Area.PlaceHolder" text="999999.99 sq.ft/sq.mt" var="area"/>
		<form:input cssClass="form-control mandColorClass text-right grpPropertyClass"  
									onkeypress="return hasAmount(event, this, 15, 2)"
									id="totalplot" path="provisionalAssesmentMstDto.assPlotArea"
									placeholder="${area}"
									onchange="getAmountFormatInDynamic((this),'totalplot')"
									data-rule-required="true"></form:input>
		</div>
		</div>
		
		


	</div>
</div>
