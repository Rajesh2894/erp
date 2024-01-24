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

<script src="js/property/propertyAddressDetails.js"></script>


<style>
  #hideBillingDetails{display:none;}  
</style>
 
<div class="accordion-toggle">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#propertyAddress1">Correspondence Address</a>
	</h4>
	<div class="panel-collapse collapse" id="propertyAddress1">
		<div class="form-group">
			
			<apptags:textArea  labelCode="property.propertyaddress" isDisabled="true" path="provisionalAssesmentMstDto.assAddress" maxlegnth="1000" cssClass="hasNoSpace"></apptags:textArea>
			<apptags:input  labelCode="property.location" isDisabled="true"  path="provisionalAssesmentMstDto.locId" maxlegnth="1000" cssClass="hasNoSpace" ></apptags:input>	
		</div>
		

        <div>
		<apptags:input labelCode="property.pincode" path="provisionalAssesmentMstDto.assCorrPincode" isDisabled="true" cssClass="hasPincode" maxlegnth="6"></apptags:input>	
		<apptags:input cssClass="hasemailclass hasNoSpace" isDisabled="true" labelCode="property.email" path="provisionalAssesmentMstDto.assEmail" maxlegnth="254" dataRuleEmail="true" ></apptags:input> 
 		</div>
		<div class="form-group">
			<div class="col-sm-6">		
				
				
			</div>
		</div>
	</div>
</div>

