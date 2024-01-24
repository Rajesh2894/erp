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
		<a data-toggle="collapse" href="#taxZoneDetails"><spring:message
				code="property.taxZoneDetails" /></a>
	</h4>
	
	
	<div class="panel-collapse collapse in" id="taxZoneDetails">
	<c:set var="orgId" value="${userSession.getCurrent().organisation.orgid}" />
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
 							<label class="text-red-1"
									for=""><spring:message code="property.propNote"/>				 	
 						 <a href="WZB/${orgId}_fileDoc.pdf" target="_blank"><spring:message code="property.clickHere"/></a></label>
						 </div>
		</div>
		
	</div>
	
</div>