<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<script>
$(document).ready(function(){
	/* if($("#currentUser").val()=='NOUSER'){
			$('input[type=text]').removeAttr('readonly');
			} */
	
	$("input").on("keypress", function(e) {
	    if (e.which === 32 && !this.value.length)
	        e.preventDefault();
	});
	
	/* D108031 */
	var address = $('#address').val();
	if(address.includes("null")){
		var add = address.replaceAll("null", "").replaceAll(",","");
		$('#address').val(add);
	}
			
});

window.onload = function() {
	  document.getElementById("MobileNumber").focus();
	};
</script>


<div id="Applicant" class="panel-collapse collapse in">
	<div class="panel-body">
		<c:set var="isDisabled" value="${userSession.employee.emploginname ne 'NOUSER'}"></c:set>	
		
		<div class="form-group">
		<label class="col-sm-2 control-label required-control" for="MobileNumber"><spring:message code="care.mobilenumber" text="Mobile Number"/></label>
			<div class="col-sm-4">
				 <form:input name="mobileNo" type="text" class="form-control hasNumber" id="MobileNumber" disabled="${isDisabled}"
				path="applicantDetailDTO.mobileNo" maxlength="10"></form:input>
			</div>
			<label class="col-sm-2 control-label required-control" for="applicantDetailDTO.titleId"><spring:message code="care.name" text="Name"/></label>
			<div class="col-sm-1 padding-right-0">
				<form:select path="applicantDetailDTO.titleId" class="form-control" disabled="${isDisabled}">
					<c:set var="baseLookupCode" value="TTL" />
					<form:option value="0"><spring:message code="Select" text="Select"/></form:option>
					<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
						<form:option value="${lookUp.lookUpId}"	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
					</c:forEach>
				</form:select>
			</div>
			<div class="col-sm-1 padding-right-0 padding-left-0">
			<spring:message code="care.firstname" text="First Name" var="firstPlaceHolder"/>
				<form:input name="" type="text" class="form-control hasSpecialChara" disabled="${isDisabled}" 
					path="applicantDetailDTO.fName" id="firstName" placeholder="${firstPlaceHolder}"></form:input>
			</div>
			<div class="col-sm-1 padding-right-0 padding-left-0">
			<spring:message code="care.middlename" text="Middle Name" var="middlePlaceHolder"/>
			  <form:input name="MiddleName" type="text" class="form-control hasSpecialChara" disabled="${isDisabled}" 
			  	id="MiddleName"  path="applicantDetailDTO.mName" placeholder="${middlePlaceHolder}" ></form:input>
			</div>
			<div class="col-sm-1  padding-left-0">
			<spring:message code="care.lastname" text="Last Name" var="lastPlaceHolder"/>
			  <form:input name="lastName" type="text" class="form-control hasSpecialChara" disabled="${isDisabled}" 
			  	id="LastName1" path="applicantDetailDTO.lName" placeholder="${lastPlaceHolder}"></form:input>
			</div>
			
		</div>
	
		
		<div class="form-group">
			<label class="col-sm-2 control-label required-control" for="empGender"><spring:message code="care.gender" text="Gender"/></label>
			<div class="col-sm-4">    
				<form:select path="applicantDetailDTO.gender" id="empGender" class="form-control" disabled="${isDisabled}">
					<c:set var="baseLookupCode" value="GEN" />
					<form:option value="0"><spring:message code="Select" text="Select"/></form:option>
					<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
						<form:option value="${lookUp.lookUpId}"	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
					</c:forEach>
				</form:select>
			</div>
			<label class="col-sm-2 control-label" for="EmailID"><spring:message code="care.emailid" /></label>
			<div class="col-sm-4">
			  <form:input name="" type="email" class="form-control" id="EmailID" disabled="${isDisabled}" path="applicantDetailDTO.email"></form:input>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-2 control-label required-control" for="address"><spring:message code="care.address" text="Address"/></label>  
			<div class="col-sm-4">
				<form:textarea class="form-control" id="address" maxlength="1000" disabled="${isDisabled}" path="applicantDetailDTO.areaName"></form:textarea>
			</div>
			<label class="col-sm-2 control-label" for="Pincode"><spring:message code="care.pincode" /></label>
			<div class="col-sm-4">
			  <form:input name="" type="text" class="form-control hasNumber" id="Pincode" disabled="${isDisabled}"
			  	 path="applicantDetailDTO.pincodeNo" maxlength="6"></form:input>
			</div>
		</div>
		
		
	  </div>
</div>