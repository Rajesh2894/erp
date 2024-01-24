<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

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
<script src="js/mainet/validation.js"></script>

 <h4 class="margin-top-0"><a data-toggle="collapse" href="#Applicant"><spring:message code="common.applicantDetails" text="Applicant Details" /></a></h4>
	
 <div class="collapse" id="Applicant">
  <div class="form-group">
	
	<label class="col-sm-2 control-label required-control"><spring:message code="common.addOwnTitle" /></label>
	<spring:message code="common.addOwnTitle" var="applicantTitle" />
			<div class="col-sm-4">
			<c:set var="baseLookupCode" value="TTL" />
			<c:if test="${command.applicantDetailDto.applicantTitle eq 0}">
                     	<apptags:lookupField items="${command.getServiceLevelData(baseLookupCode)}"
							 path="applicantDetailDto.applicantTitle" 
							 cssClass="col-50 mandClassColor form-control" 
							 hasChildLookup="false" 
							 hasId="true"
							 showAll="false"
							 selectOptionLabelCode="pt.select"/>
                     </c:if>
                     
                     <c:if test="${command.applicantDetailDto.applicantTitle ne 0}">
					 <apptags:lookupField items="${command.getServiceLevelData(baseLookupCode)}"
							 path="applicantDetailDto.applicantTitle" 
							 cssClass="col-50 mandClassColor form-control" 
							 hasChildLookup="false" 
							 hasId="true"
							 showAll="false"	
							 selectOptionLabelCode="pt.select"/>
							
					</c:if> 
                  </div>    
                    
			
	<label class="col-sm-2 control-label required-control"><spring:message code="common.firstName" /></label>
				<div class="col-sm-4 ">
				<form:input path="applicantDetailDto.applicantFirstName" id="firstName"
					cssClass="input2 mandClassColor hasSpecialChara form-control"  maxlength="100" />
				</div>
			</div>
			
		<div class="form-group">
			<label class="col-sm-2 control-label required-control"><spring:message code="bnd.mainet.birthCert.Fath/Husb" /></label>
				<div class="col-sm-4 ">
				<form:input path="applicantDetailDto.applicantMiddleName" id="fatherOrHusbandName"
					cssClass="input2 hasSpecialChara form-control"  maxlength="50"/>
				</div>
				
			<label class="col-sm-2 control-label required-control"><spring:message code="common.lastName" /></label>	
				<div class="col-sm-4">
				<form:input path="applicantDetailDto.applicantLastName" id="lastName"
					cssClass="input2 mandClassColor hasSpecialChara form-control" 	maxlength="100" />
			</div>
		</div>

	
	<div class="form-group">
		<label class="col-sm-2 control-label required-control"><spring:message	code="bnd.sex" /></label>	
		<div class="col-sm-4">
				<c:if test="${empty command.applicantDetailDto.gender}">
					<form:select cssClass="mandClassColor form-control" path="applicantDetailDto.gender" id="gender">
							<form:option value="">Select</form:option>
							<form:option value="M">Male</form:option>
							<form:option value="F">Female</form:option>
						</form:select> 
				</c:if>
				<c:if test="${not empty command.applicantDetailDto.gender}">
					<form:select cssClass="mandClassColor form-control" path="applicantDetailDto.gender" id="gender" >
							<form:option value="">Select</form:option>
							<form:option value="M">Male</form:option>
							<form:option value="F">Female</form:option>
						</form:select> 
				</c:if>
		</div>
				
					<label class="col-sm-2 control-label required-control"><spring:message	code="property.buildingName"/></label>
					<div class="col-sm-4">
					<form:input path="applicantDetailDto.buildingName" id="buildingName"  class="mandClassColor hasSpecialChara form-control"  maxlength="50" />
					</div>
	</div>
		
		<div class="form-group">	
				<label for="roadName" class="col-sm-2 control-label"><spring:message	code="property.roadName" /></label>
				<div class="col-sm-4">
				<form:input path="applicantDetailDto.roadName" id="roadName"	cssClass="alphaNumeric form-control" maxlength="50"/>
				</div>
				
				<label class="col-sm-2"><spring:message	code="property.areaName"/></label>
				<div class="col-sm-4">
				<form:input path="applicantDetailDto.areaName" id="areaName"  class="hasSpecialChara form-control"  maxlength="50" />
				</div>
		</div>
		
		<div class="form-group">	
				<label class="col-sm-2 control-label required-control"><spring:message	code="property.blockName" /></label>
				<div class="col-sm-4">
				<form:input path="applicantDetailDto.blockName" id="blockName"	cssClass="mandClassColor alphaNumeric form-control" maxlength="10"/>
				</div>
				
				<label class="col-sm-2"><spring:message	code="property.housingName"/></label>
				<div class="col-sm-4">
				<form:input path="applicantDetailDto.housingComplexName" id="housingComplexName"  class=" hasSpecialChara form-control"  maxlength="50"/>
				</div>
		</div>
		
		
		<div class="form-group">	
				<label class="col-sm-2 control-label"><spring:message	code="property.wing" /></label>
				<div class="col-sm-4">
				<form:input path="applicantDetailDto.wing" id="wing"	cssClass="alphaNumeric form-control" maxlength="10"/>
				</div>
				
				<label class="col-sm-2 control-label"><spring:message	code="pt.floorno"/></label>
				<div class="col-sm-4">
				<form:input path="applicantDetailDto.floorNo" id="floorNo"  class="alphaNumeric form-control" maxlength="10"/>
				</div>
		</div>
		
		
		<div class="form-group">	
				<label class="col-sm-2 control-label required-control"><spring:message	code="property.village" /></label>
				<div class="col-sm-4">
				<form:input path="applicantDetailDto.villageTownSub" id="villageTownSub"	cssClass="mandClassColor hasSpecialChara form-control" maxlength="50"/>
				</div>
				
				<label class="col-sm-2 control-label required-control"><spring:message	code="pt.pincode"/></label>
				<div class="col-sm-4">
				<form:input path="applicantDetailDto.pinCode" id="pinCode"  class="mandClassColor hasNumber form-control"  maxlength="6" />
				</div>
		</div>
		
		
		
		<div class="form-group">	
				<label class="col-sm-2 control-label"><spring:message	code="property.phone1" /></label>
				<div class="col-sm-4">
				<form:input path="applicantDetailDto.phone1" id="phone1" cssClass="hasMobileNo form-control" maxlength="10"/>
				</div>
				
				<label class="col-sm-2"><spring:message	code="property.phone2"/></label>
				<div class="col-sm-4">
				<form:input path="applicantDetailDto.phone2" id="phone2"  class="hasMobileNo form-control"  maxlength="10"/>
				</div>
		</div>
		
		<div class="form-group">	
				
				<label class="col-sm-2 control-label"><spring:message	code="property.contactName" /></label>
				<div class="col-sm-4">
				<form:input path="applicantDetailDto.contactPersonName" id="contactPersonName"	cssClass=" hasSpecialChara form-control" maxlength="100"/>
				</div>
				
				<label class="col-sm-2 control-label required-control"><spring:message	code="pt.mobileno"/></label>
				<div class="col-sm-4">
				<form:input path="applicantDetailDto.mobileNo" id="mobileNo"  class="mandClassColor hasNumber form-control"  maxlength="10" />
				</div>
		</div>
		
		
		
		<div class="form-group">	
				<label class="col-sm-2 control-label required-control"><spring:message	code="pt.emailid" /></label>
				<div class="col-sm-4">
				<form:input path="applicantDetailDto.emailId" id="emailId"	cssClass="mandClassColor hasemailclass form-control" maxlength="100"/>
				</div>
		</div>
	</div>					