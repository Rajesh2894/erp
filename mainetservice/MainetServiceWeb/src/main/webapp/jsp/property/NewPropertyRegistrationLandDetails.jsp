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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/property/PropertylandDetails.js"></script>

<!-- End JSP Necessary Tags -->

<div class="accordion-toggle margin-top-15">
	
	<div class="panel-collapse collapse in" id="propertyDetail">
			<div class="form-group">
				<apptags:input cssClass="alphaNumeric preventSpace grpPropertyClass" labelCode="propertydetails.oldpropertyno" path="provisionalAssesmentMstDto.assOldpropno" maxlegnth="20"></apptags:input>

				<label class="col-sm-2 control-label"><spring:message code="property.landType" />
					</label>
				   					<div class="col-sm-4">
									<c:set var="baseLookupCode" value="LDT" /> 
									<form:select path="provisionalAssesmentMstDto.assLandType"
											onchange="getLandTypeDetails()" class="form-control mandColorClass grpPropertyClass" id="assLandType">
												<form:option value=""><spring:message code="property.landDetails.select" text="Select"/></form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
									</form:select>
									</div>	
				  
			</div>
			<div id="landType">
			
						</div>
			
			<div class="form-group">
			<label class="col-sm-2 control-label"><spring:message code="property.building.permission" text="Building Permission"/></label>
			<div class="col-sm-4">
			<form:select class="form-control" path="provisionalAssesmentMstDto.buildingPermission" id="buildingPermission">
				<form:option value="">
					<spring:message code="property.sel.optn" text="Select Option"></spring:message>
				</form:option>
				<form:option value="Y"><spring:message code="unit.Yes" text="Yes"/></form:option>
				<form:option value="N"><spring:message code="Unit.No" text="No"/></form:option>
			</form:select>
			</div>
			<apptags:input cssClass="alphaNumeric preventSpace " labelCode="property.buildingpermission" 
				path="provisionalAssesmentMstDto.buildingPermissionNumber" maxlegnth="100"></apptags:input>
				
			</div>		
	</div>
</div>

