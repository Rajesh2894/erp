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

<script src="js/property/propertyTaxCalculator.js" ></script>  

 
<!-- End JSP Necessary Tags -->
<div class="content">
<div class="widget">

        <div class="widget-header">
          <h2><spring:message code="property.taxCalculator" text="Property Tax Calculator"/></h2>
          <div class="additional-btn"> <a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message code="property.Help"/></span></a></div>
        </div>
        
        <div class="widget-content padding">
	
		
		<div class="mand-label clearfix">
			<span><spring:message code="property.Fieldwith"/><i class="text-red-1">* </i><spring:message code="property.ismandatory"/>
			</span>
		</div>
		<!-- End mand-label -->
		
		<!-- Start Form -->
		<form:form action="PropertyTaxCalculator.html"
					class="form-horizontal form" name="PropertyTaxCalculator"
					id="PropertyTaxCalculator">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;"></div>

<div class="accordion-toggle">
	 
<c:if test="${userSession.getCurrent().getOrganisation().getOrgid() ne '1'}">
<div class="form-group orgName">
		<label class="col-sm-2 control-label"
									for="orgName"><spring:message code="property.taxCalculator.MunicipalityName" text="Municipality Name"/></label>
			<div class="col-sm-4">									
					<form:input path="" type="text" class="form-control preventSpace" id="orgName" disabled="true" value="${command.getOrganizationName()}"/>
			</div>
			</div>
 </c:if>



	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#taxZoneDetails"><spring:message
				code="property.taxZoneDetails" /></a>
	</h4>
	<div class="panel-collapse collapse in" id="taxZoneDetails">

		<div class="form-group">
			<apptags:lookupFieldSet cssClass="form-control required-control"
					baseLookupCode="WZB" hasId="true" pathPrefix="provisionalAssesmentMstDto.assWard"  
					hasLookupAlphaNumericSort="true"
					hasSubLookupAlphaNumericSort="true" showAll="false" isMandatory="true"/>
		
		</div>
		<div class="form-group">
			<label for="road-type" class="col-sm-2 control-label required-control"><spring:message code="unitdetails.RoadType"/> </label>
				          <c:set var="baseLookupCode" value="RFT" />
						  <apptags:lookupField  items="${command.getLevelData(baseLookupCode)}"
				 							path="provisionalAssesmentMstDto.propLvlRoadType" cssClass="form-control" 
				 							hasChildLookup="false" hasId="true" showAll="false" 
											selectOptionLabelCode="Select Road Factor" isMandatory="true"/> 
						 
						 <div class="col-sm-4"> 	
 						 <spring:message code="property.propNote"/><a href="WZB/${orgId}_fileDoc.pdf" target="_blank"><spring:message code="property.clickHere"/></a>	
						 </div>
		</div>
		
	</div>
						
	<jsp:include page="/jsp/property/PropertyTaxCalculatorUnitDetails.jsp"></jsp:include>  
</div>
			
			
			
		<div class="text-center padding-10">			
			<button type="button" class="btn btn-success"
				onclick="NextToViewPage(this)" id="btnSave"><spring:message code="unit.proceed"/></button>
			<a type="button" class="btn btn-danger" href="CitizenHome.html">Back </a>
		</div>
					</form:form>
						
</div>
</div>
			</div>			
