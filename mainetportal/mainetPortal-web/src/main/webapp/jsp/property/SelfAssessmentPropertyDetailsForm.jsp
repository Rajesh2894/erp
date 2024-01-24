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
<script src="js/property/PropertylandDetails.js"></script>
<!-- End JSP Necessary Tags -->
<div class="accordion-toggle">
	<%-- <h4 class="margin-top-10 margin-bottom-10 panel-title">
		<a data-toggle="collapse" href="#propertyDetail"><spring:message
				code="property.LandDetail" /></a>
	</h4> --%>
	
	<div class="panel-collapse collapse in margin-top-15" id="propertyDetail">
			<div class="form-group">
				<apptags:input cssClass="alphaNumeric preventSpace" labelCode="propertydetails.oldpropertyno" path="provisionalAssesmentMstDto.assOldpropno" maxlegnth="20"></apptags:input>

				<label class="col-sm-2 control-label"><spring:message code="property.landType" />
					</label>
				   					<div class="col-sm-4">
									<c:set var="baseLookupCode" value="LDT" /> 
									<form:select path="provisionalAssesmentMstDto.assLandType"
											onchange="getLandTypeDetails()" class="form-control mandColorClass" id="assLandType">
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
	</div>
</div>

