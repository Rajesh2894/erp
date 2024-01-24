<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="panel panel-default">

			
<div class="accordion-toggle ">
	
	<c:choose>
	<c:when test="${command.getOldLandTypePrefix() eq 'KPK'}">	
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#khasraDetails"><spring:message code="property.landDetails.KhasraDetails" text="Khasra Details"/></a>
	</h4>
	<div class="panel-collapse collapse in" id="khasraDetails">
			

	<div class="form-group">	
		
		<label class="col-sm-2 control-label" for="ownerName"><spring:message code="property.landDetails.ownerFirstName" /></label>
			<div class="col-sm-4">			
			<c:set var="ownerFirstName"  value="${command.arrayOfKhasraDetails.khasraDetails[0].ownerdetails[0].ownerDetails[0].ocdname}"	/>
			<c:set var="ownerLastName"  value="${command.arrayOfKhasraDetails.khasraDetails[0].ownerdetails[0].ownerDetails[0].ocdname1}"	/>
			
   			<form:input path="command.arrayOfKhasraDetails.khasraDetails[0].ownerdetails[0].ownerDetails[0].ocdname" value="${ownerFirstName} ${ownerLastName}" class="form-control" readonly="true"/>	
   			</div>	
   		
   		<label class="col-sm-2 control-label" for="ownerName"><spring:message code="property.landDetails.fathersFirstName" /></label>   		
   			<div class="col-sm-4">			
			<c:set var="fathersFirstName"  value="${command.arrayOfKhasraDetails.khasraDetails[0].ownerdetails[0].ownerDetails[0].ofather}"	/>
			<c:set var="FathersLastName"  value="${command.arrayOfKhasraDetails.khasraDetails[0].ownerdetails[0].ownerDetails[0].ofather1}"	/>
			
   			<form:input path="command.arrayOfKhasraDetails.khasraDetails[0].ownerdetails[0].ownerDetails[0].ofather" value="${fathersFirstName} ${FathersLastName}" class="form-control" readonly="true"/>	
   			</div>  
	
 		</div>
		
	
	<div class="form-group">		
		<apptags:input labelCode="property.landDetails.GenderOfOwner" isDisabled="true" path="command.arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].gender"></apptags:input>
		<apptags:input labelCode="property.landDetails.CasteOfOwner" isDisabled="true" path="command.arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].ocastenm"></apptags:input>				
	</div>	
	
	<div class="form-group">	
	
		<apptags:input labelCode="property.landDetails.OwnerAddress" isDisabled="true" path="command.arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].oaddr1"></apptags:input>
		<apptags:input labelCode="property.landDetails.OwnerMobileNo" isDisabled="true" path="command.arrayOfKhasraDetails.KhasraDetails[0].ownerdetails[0].OwnerDetails[0].mobileno"></apptags:input>
				
	</div>	
	
	<div class="form-group">	
	 	<apptags:input labelCode="property.landDetails.LandArea" isDisabled="true" path="command.arrayOfKhasraDetails.KhasraDetails[0].Area"></apptags:input>
	 	<apptags:input labelCode="property.landDetails.OwnerType" isDisabled="true" path="command.arrayOfKhasraDetails.KhasraDetails[0].OwnerType"></apptags:input>
	
	</div>
	
 </div>
 
 </c:when>
 

<c:when test="${command.getOldLandTypePrefix() eq 'NZL'}">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#NZLDetails"><spring:message code="property.landDetails.NajoolDetails" text="Najool Details"/></a>
	</h4>
	<div class="panel-collapse collapse in" id="NZLDetails">
			

	<div class="form-group">		
		<apptags:input labelCode="property.landDetails.OwnerName" isDisabled="true" path="command.arrayOfPlotDetails.PlotDetails[0].najoolOwnerDetails[0].najoolOwnerDetailsList[0].OwnerName"></apptags:input>	
		<apptags:input labelCode="property.landDetails.Department" isDisabled="true" path="command.arrayOfPlotDetails.PlotDetails[0].Department"></apptags:input>				
	</div>					
			
	<div class="form-group">	
	
		<apptags:input labelCode="property.landDetails.Area" isDisabled="true" path="command.arrayOfPlotDetails.PlotDetails[0].area_foot"></apptags:input>
		<apptags:input labelCode="property.landDetails.rights_type_nm" isDisabled="true" path="command.arrayOfPlotDetails.PlotDetails[0].rights_type_nm"></apptags:input>
				
	</div>	
	
	<div class="form-group">		
		<apptags:textArea labelCode="property.landDetails.Remark" path="command.arrayOfPlotDetails.PlotDetails[0].remark" isDisabled="true"></apptags:textArea>			
	</div>
		
 	</div>
	

</c:when>

<c:when test="${command.getOldLandTypePrefix() eq 'DIV'}">
	<h4 class="margin-top-0 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#DIVDetails"><spring:message code="property.landDetails.DiversionDetails" text="Diversion Details"/></a>
	</h4>
	<div class="panel-collapse collapse in" id="DIVDetails">
			

	<div class="form-group">		
		<apptags:input labelCode="property.landDetails.OwnerName" isDisabled="true" path="command.arrayOfDiversionPlotDetails.DiversionPlotDetails[0].diversionOwnerDetails[0].diversionOwnerDetailsList[0].OwnerName"></apptags:input>	
		<apptags:input labelCode="property.landDetails.Department" isDisabled="true" path="command.arrayOfDiversionPlotDetails.DiversionPlotDetails[0].Department"></apptags:input>				
	</div>					
			
	<div class="form-group">		
		<apptags:input labelCode="property.landDetails.Area" isDisabled="true" path="command.arrayOfDiversionPlotDetails.DiversionPlotDetails[0].area_foot"></apptags:input>
		<apptags:input labelCode="property.landDetails.rights_type_nm" isDisabled="true" path="command.arrayOfDiversionPlotDetails.DiversionPlotDetails[0].rights_type_nm"></apptags:input>				
	</div>	
	
	<div class="form-group">		
		<apptags:textArea labelCode="property.landDetails.Remark" path="command.arrayOfDiversionPlotDetails.DiversionPlotDetails[0].remark" isDisabled="true"></apptags:textArea>			
	</div>
		
 	</div>
	

</c:when>
 </c:choose>
 </div>

</div>