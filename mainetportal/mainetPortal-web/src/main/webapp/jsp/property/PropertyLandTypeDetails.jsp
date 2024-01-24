<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/mainet/validation.js"></script>
<script src="js/property/PropertylandDetails.js"></script>
 
<c:choose>
<c:when test="${command.getLandTypePrefix() eq 'KPK'}">


		<div class="form-group">					
		<apptags:select labelCode="property.district" items="${command.district}" path="command.provisionalAssesmentMstDto.assDistrict" isLookUpItem="true" selectOptionLabelCode="Select" changeHandler="getTehsilListByDistrict()" isMandatory="true"></apptags:select>
		
												
		<label class="col-sm-2 control-label required-control"
									for="assTahasil"><spring:message
										code="property.tahasil" /></label>
									<div class="col-sm-4">
									 <form:select path="command.provisionalAssesmentMstDto.assTahasil" id="assTahasil" class="form-control mandColorClass" onchange="getVillageListByTehsil()" data-rule-required="true">
		  						
		  						<c:forEach items="${command.tehsil}" var="lookup">
									 <form:option  value="${lookup.lookUpCode}" selected="${lookup.lookUpCode == factor.getAssTahasil() ? 'selected' : ''}">${lookup.lookUpDesc}</form:option>										
 									
		  						</c:forEach>
		 						</form:select>
			</div>
		</div>	
		<div class="form-group">			
		
			<label class="col-sm-2 control-label required-control"
									for="tppVillageMauja"><spring:message
										code="propertydetails.village" /></label>
						<div class="col-sm-4">
									
								<form:select path="command.provisionalAssesmentMstDto.tppVillageMauja" id="tppVillageMauja" class="form-control mandColorClass" data-rule-required="true">
		  						<c:forEach items="${command.village}" var="lookup">								
								<form:option  value="${lookup.lookUpCode}" selected="${lookup.lookUpCode == factor.getTppVillageMauja() ? 'selected' : ''}">${lookup.lookUpDesc}</form:option>																		
		  						</c:forEach>
		 						</form:select>
						</div>	
									
			<%-- <label class="col-sm-2 control-label"
									for="khasra"><spring:message code="propertydetails.csnno" text="Khasra No"/></label>
				<div class="col-sm-4">									
				<form:input path="command.provisionalAssesmentMstDto.tppPlotNoCs" type="text" class="form-control mandColorClass required-control" id="khasara" />
				</div> --%>
				
			<label class="col-sm-2 control-label required-control"
									for="khasraNo"><spring:message code="propertydetails.csnno" text=""/></label>
	
			<div class="col-sm-1 padding-right-0">
					<form:input type="text" class="form-control preventSpace" id="khasraNo" path="command.knownKhaNo" onkeyup="toGetKhasraNoList()" data-rule-required="true"/>
			</div>
			<div class="col-sm-3 padding-left-0">
										<%-- <form:hidden class="form-control" id="tppPlotNoCs" path="command.enteredKhasraNo"/> --%>					
					<form:select path="command.provisionalAssesmentMstDto.tppPlotNoCs" id="displayKhaNo" class="form-control" onchange="fetchKhasaraDetails()" data-rule-required="true">
		  						<c:forEach items="${command.khasraNo}" var="lookup">
		  						
									 <form:option  value="${lookup.descLangFirst}" selected="${lookup.descLangFirst == factor.getTppPlotNoCs() ? 'selected' : ''}" >${lookup.descLangFirst}</form:option>										
 									
		  						</c:forEach>
		 			</form:select>	
		 	</div>
		 	
			<div class="form-group khaNoNote">
			<label class="col-sm-4  col-sm-offset-8 text-green-1" for="">
			<spring:message code="property.landDetails.khasraNoNote" text="Please select valid option from the select list on right associated with the entered Khasra no."/> </label>
			</div>	
		</div>
		
		<div id="showApiDetails">
					
		</div>	
</c:when>
<c:when test="${command.getLandTypePrefix() eq 'NZL' || command.getLandTypePrefix() eq 'DIV'}">
		<div class="form-group">			
				<apptags:select labelCode="property.district" items="${command.district}" path="command.provisionalAssesmentMstDto.assDistrict" isLookUpItem="true" selectOptionLabelCode="property.landDetails.select" changeHandler="getTehsilListByDistrict()" isMandatory="true"></apptags:select>
		
		<label class="col-sm-2 control-label required-control"
									for="assTahasil"><spring:message
										code="property.tahasil" /></label>
									<div class="col-sm-4">
								<form:select path="command.provisionalAssesmentMstDto.assTahasil" id="assTahasil" class="form-control mandColorClass" onchange="getVillageListByTehsil()" data-rule-required="true">
		  						
		  						<c:forEach items="${command.tehsil}" var="lookup">									
								<form:option  value="${lookup.lookUpCode}" selected="${lookup.lookUpCode == factor.getAssTahasil() ? 'selected' : ''}">${lookup.lookUpDesc}</form:option>										
									
		  						</c:forEach>
		 						</form:select>
			</div>
		
		</div>
			
		<div class="form-group">			
				<label class="col-sm-2 control-label required-control"
									for="tppVillageMauja"><spring:message
										code="propertydetails.village" /></label>
				<div class="col-sm-4">
				
									 <form:select path="command.provisionalAssesmentMstDto.tppVillageMauja" id="tppVillageMauja" class="form-control mandColorClass" onchange="getMohallaListByVillageId()" data-rule-required="true">
		  						<c:forEach items="${command.village}" var="lookup">
								
								<form:option  value="${lookup.lookUpCode}" selected="${lookup.lookUpCode == factor.getTppVillageMauja() ? 'selected' : ''}">${lookup.lookUpDesc}</form:option>										
									
		  						</c:forEach>
		 						</form:select>
				</div>	
				
				<label class="col-sm-2 control-label required-control"
									for="mohalla"><spring:message code="property.landDetails.Mohalla" text="Mohalla"/></label>
				<div class="col-sm-4">
							<form:select path="command.provisionalAssesmentMstDto.mohalla" id="mohalla" class="form-control mandColorClass" onchange="getStreetListByMohallaId()" data-rule-required="true">
		  						<c:forEach items="${command.mohalla}" var="lookup">
								<form:option  value="${lookup.lookUpCode}" selected="${lookup.lookUpCode == factor.getMohalla() ? 'selected' : ''}">${lookup.lookUpDesc}</form:option>										
									
		  						</c:forEach>
		 					</form:select>
				</div>				
				
		</div>	
		
		<div class="form-group">		

				<label class="col-sm-2 control-label required-control"
									for="assStreetNo"><spring:message code="property.landDetails.Block_Sheet" text="Block/Sheet"/></label>
				<div class="col-sm-4">
							<form:select path="command.provisionalAssesmentMstDto.assStreetNo" id="assStreetNo" class="form-control mandColorClass"  data-rule-required="true">
		  						<c:forEach items="${command.blockStreet}" var="lookup">
								<form:option  value="${lookup.lookUpCode}" selected="${lookup.lookUpCode == factor.getAssStreetNo() ? 'selected' : ''}">${lookup.lookUpDesc}</form:option>										
									
		  						</c:forEach>
		 					</form:select>
				</div>	
<%-- 				<apptags:input labelCode="propertydetails.plotno" path="command.provisionalAssesmentMstDto.tppPlotNo"></apptags:input> --%>

			<label class="col-sm-2 control-label required-control"
									for="plotno"><spring:message code="propertydetails.plotno" text=""/></label>
	
			<div class="col-sm-1 padding-right-0">
 					<form:input type="text" class="form-control preventSpace" id="plotNo" path="command.knownKhaNo" onkeyup="toGetPlotNoList()" data-rule-required="true"/>

			</div>
			<div class="col-sm-3 padding-left-0">
<%-- 							<form:hidden class="form-control" id="getEnteredPlotNo" path="command.enteredPlotNo"/>					 --%>
					<form:select path="command.provisionalAssesmentMstDto.tppPlotNo" id="tppPlotNo" class="form-control" onchange="fetchNajoolAndDiversionDetails()" data-rule-required="true">
		  						
		  						<c:forEach items="${command.plotNo}" var="lookup">
									 <form:option  value="${lookup.descLangFirst}" selected="${lookup.descLangFirst == factor.getTppPlotNo() ? 'selected' : ''}" >${lookup.descLangFirst}</form:option>										
 									
		  						</c:forEach>
		 			</form:select>	
		 	</div>
		 	
			<div class="form-group plotNoNote">
			<label class="col-sm-4  col-sm-offset-8 text-green-1" for="">
			
			<spring:message code="property.landDetails.plotNoNote" text="Please select valid option from the select list on right associated with the entered plot no."/>
			 </label>
	 
	 		</div>
		</div>		
		
		<div id="showApiDetails">
					
		</div>
		
</c:when>
</c:choose>
