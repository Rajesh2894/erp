<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<script src="/js/eip/agency/agencyForgotPasswordProcess.js"></script>
<script>
$(function(e){
	$("#emplType").focus();
	
	jQuery('.hasMobileNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	    $(this).attr('maxlength','10');
		});
});

$(document).ready(function() {
	myFunction();
});


function myFunction(){
	$("#emplType").val($("#emplType option[code='O']").val());
}

function set(){
 
		var empTypeValue = $('#hiddenEmpType').val();
				$('#emplType option[value="' + empTypeValue + '"]').prop('selected', true);
	}

$("#resettButon").click(function() {
	$('.error-div').html("");
	
	$('#emplType option:eq(0)').attr("selected","selected");
 $("#emplType").get(0).selectedIndex = 0;
 
$('#mobileNumber').val('');
	
	});


</script>

<div class="row padding-40">
      <div class="col-md-4 col-md-offset-4">
        <div class="login-panel">
          <div class="widget margin-bottom-0">
  <div class="widget-header">
    <h2><strong><spring:message code="eip.citizen.forgotPassword.FormHeader" text="Citizen Forgot Password" /></strong></h2>
  </div>
  <div class="widget-content padding">
  <div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
    <div id="basic-form">
      

	<form:form id="agencyForgotPasswordForm" name="agencyForgotPasswordForm" method="POST" role="form">
	<spring:message	code="eip.select.type" var="selectLabelLang" />
        <div class="form-group">
      		<label for="type"><i class="fa fa-slideshare"></i> <spring:message	code="eip.agency.reg.type" text="Type of Agency " /></label>
					
				    
 					
 		<form:select path="agencyEmployee.emplType" class="form-control" id="emplType" name="agencyEmployee.emplType">
			<c:set var="baseLookupCode" value="NEC"/>
			<form:option value="">Select</form:option>
			<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
				<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
			</c:forEach>
		</form:select>
		 <form:hidden path="empType" id="hiddenEmpType" value="${command.empType}"/>								   

        </div>
        <div class="form-group">
        <label for="exampleInputEmail1"><i class="fa fa-mobile-phone"></i> <spring:message	code="eip.citizen.forgotPassword.MobileNo" text="Mobile No."/></label>
        <form:input id="mobileNumber" cssClass="form-control mandClassColor hasMobileNo12" path="mobileNumber" onkeypress="return tryStep1(event)"  maxlength="12" />
        </div>
        
        
        <div class="row">
        	<div class="col-lg-6 col-md-6 col-xs-6"><input type="button"  class="btn btn-success btn-block" onclick="getAgencyForgotPassStep2('Register');"	value="<spring:message code="eip.commons.submitBT" text="Submit"/>" /> </div>
        	<div class="col-lg-6 col-md-6 col-xs-6"><input type="button" class="btn btn-warning btn-block" value="<spring:message code="eip.agency.login.resetBT"/>" id="resettButon" ></div>
        </div>
        
        
      </form:form>
    </div>
  </div>
</div>
</div>
</div>
</div>
