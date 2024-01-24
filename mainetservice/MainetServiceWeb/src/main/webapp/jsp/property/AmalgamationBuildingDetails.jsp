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
<script>
$(document).ready(function(){
	var minYear= $("#dateOfEffect").val();
	//datepicker function for year of acquisition
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		minDate: minYear,
		maxDate: '-0d',
		yearRange: "-100:-0",
	});

});
</script>

<!-- End JSP Necessary Tags -->
<div class="accordion-toggle">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#BuildingDetails"><spring:message
				code="property.buildingdetails" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="BuildingDetails">
		<div class="form-group">
			<%-- <label class="col-sm-2 control-label required-control"><spring:message code="property.yearofacquisition"/></label>
			<div class="col-sm-4">
				<div class="input-group"> 
					
					
						<form:input path="provisionalAssesmentMstDto.assAcqDate" class="lessthancurrdate form-control mandColorClass dateClass" id="proAssAcqDate" disabled="true"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>										
					
				</div>
			</div>	 --%>
<%-- 				<form:input path="provisionalAssesmentMstDto.dateOfEffect_123"/> --%>
<%--   	      	 <apptags:date fieldclass="lessthancurrdate" labelCode="property.DateOfEffect" datePath="provisionalAssesmentMstDto.dateOfEffect" cssClass="form-control mandColorClass dateClass"></apptags:date>   --%>
			
			<label class="col-sm-2 control-label required-control"><spring:message code="property.yearofacquisition"/></label>
			<div class="col-sm-4">
				<div class="input-group"> 
					
					
						<form:input path="provisionalAssesmentMstDto.assAcqDate" class="lessthancurrdate form-control mandColorClass" id="dateOfEffect" disabled="true"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>										
					
				</div>
			</div>	
			
			
			<label class="col-sm-2 control-label required-control"><spring:message
									code="property.totalplot" text="" /></label>
			<div class="col-sm-4">						
			<form:input cssClass="form-control mandColorClass text-right"
									onkeypress="return hasAmount(event, this, 15, 2)"
									id="totalplot" path="provisionalAssesmentMstDto.assPlotArea"
									placeholder="999999.99"
									onchange="getAmountFormatInDynamic((this),'totalplot')"
									data-rule-required="true"></form:input>
			</div>
        </div>
      
		
		<%-- <div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message
									code="property.totalplot" text="" /></label>
			<div class="col-sm-4">						
			<form:input cssClass="form-control mandColorClass text-right"
									onkeypress="return hasAmount(event, this, 15, 2)"
									id="totalplot" path="provisionalAssesmentMstDto.assPlotArea"
									placeholder="999999.99"
									onchange="getAmountFormatInDynamic((this),'totalplot')"
									data-rule-required="true"></form:input>
			</div>
							
			
			<label class="col-sm-2 control-label required-control"><spring:message
									code="property.buildup" text="" /></label>
			<div class="col-sm-4">						
			<form:input cssClass="form-control mandColorClass text-right "
									onkeypress="return hasAmount(event, this, 15, 2)"
									id="buildup" path="provisionalAssesmentMstDto.assBuitAreaGr"
									placeholder="999999.99"
									onchange="getAmountFormatInDynamic((this),'buildup')"
									data-rule-required="true"></form:input>
			</div>
		</div> --%>
		
		<%-- <div class="form-group">
		<apptags:lookupFieldSet cssClass="form-control required-control"
					baseLookupCode="PTP" hasId="true" pathPrefix="provisionalAssesmentMstDto.assPropType"  
					hasLookupAlphaNumericSort="true"
					hasSubLookupAlphaNumericSort="true" showAll="false" isMandatory="true"/>
		</div> --%>

	</div>
</div>
