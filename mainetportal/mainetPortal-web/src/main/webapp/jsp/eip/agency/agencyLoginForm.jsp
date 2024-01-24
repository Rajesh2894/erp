<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"pageEncoding="ISO-8859-1"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%> 
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%> 
<script src="js/eip/agency/agencyRegistrationForm.js"></script>
<script src="js/eip/agency/agencyResetPasswordProcess.js"></script>
<script src="js/eip/agency/agencyForgotPasswordProcess.js"></script>
<%
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	
%>
<script>
$('.form-control').bind("cut copy paste",function(e) {
    e.preventDefault();
});
function dispose() {
	
	$('.dialog').html('');
	$('.dialog').hide();
	disposeModalBox();
}
 
 function doServerLoading(obj)
 {
 	$(obj).html('');
 	var loading	 ="<p align='center'>Loading...</p>";
 	loading	+="<p align='center'>Authenticating please wait...</p>";
 	
 	$(obj).addClass('ajaxloader').fadeIn('slow');
 	$(obj).html(loading);
 	$(obj).load();
 }
 $(document).ready(function() 
{		myFunction();
	 $("#emplType").focus();	
	setTimeout( function() { $( '#emplType' ).focus() }, 500 );
	
	$(document).keyup(function(e) 
	{
	});
});
 
 function myFunction(){
		$("#title").val($("#title option[code='0']").val());
		$("#empGender").val($("#empGender option[code='O']").val());
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
	 $('#loginName').val('');
	 $('#emppassword').val('');
	 
		
		});

 
</script>
<div class="row padding-40">
      <div class="col-md-4 col-md-offset-4">
        <div class="login-panel">
          <div class="widget margin-bottom-0">
	<div class="widget-header">
		<h2><spring:message code="eip.agency.login.FormHeader"/></h2>	
	</div>
<div class="widget-content padding">
<div id="basic-form">
<div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
	<form:form id="agencyLoginForm" name="agencyLoginForm" method="POST" action="AgencyLogin.html" class="agency_login" autocomplete="off">
        <jsp:include page="/jsp/tiles/validationerror.jsp" />
		<spring:message code="eip.password.placeholer" var="passP"/>
		<spring:message code='eip.email.mobile.placeholer' var="mobileP"/>
		<spring:message	code="eip.select.type" var="selectLabelLang" />
      		<div class="form-group">
					<label for="emplType"><i class="fa fa-slideshare"></i>
					<spring:message	code="eip.agency.reg.type" text="Type of Agency " />
					</label>
					
				    
 					
 		<form:select path="agencyEmployee.emplType" class="form-control" id="emplType" name="agencyEmployee.emplType">
			<c:set var="baseLookupCode" value="NEC"/>
			<form:option value=""><spring:message code="selectdropdown"/></form:option>
			<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
				<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
			</c:forEach>
		</form:select>
		 <form:hidden path="empType" id="hiddenEmpType" value="${command.empType}"/>
						
								
					 </div>
				
					<div class="form-group">
						<label for="loginName"><i class="fa fa-user"></i> <spring:message code="eip.citizen.login.loginName"/></label>
				
						<form:input path="agencyEmployee.emploginname" cssClass="form-control" onkeypress="return tryAgencyLogin(event)"
					 	maxlength="50" id="loginName" placeholder="${mobileP}" autocomplete="off" />
					</div> 	
                    <div class="form-group">
						<label for="emppassword"><i class="fa fa-lock"></i> <spring:message code="eip.agency.login.password"/></label>
						<form:password path="agencyEmployee.emppassword" cssClass="showpassword form-control" placeholder="${passP}" 
						onkeypress="return tryAgencyLogin(event)" maxlength="50" id="emppassword" autocomplete="off"/>
				  </div>
				  
				  	<div class="row form-group" id="captchaL">
					<div class="col-lg-6" id="captchaLDiv">
						<c:set var="rand"><%=java.lang.Math.round(java.lang.Math.random() * 10000)%></c:set>
						<img id="cimg" src="AgencyRegistration.html?captcha&id=${rand}"  alt="captcha value <%=request.getDateHeader("captcha3")%>"/>
						<a href="#" onclick="doRefreshLoginCaptcha()" tabindex="-1"><i
							class="fa fa-refresh"><span class="hide">Refresh</span></i></a>
					</div>
					<div class="col-lg-6">
					<label for="captchaSessionLoginValue" class="hide">captchaP</label>
						<form:input path="captchaSessionLoginValue"
							cssClass="form-control" placeholder='${captchaP}' maxlength="4"
							onkeypress="return tryLogin(event)" autocomplete="off" />
					</div>
				</div>
			
	<div class="row">
		<div class="col-lg-6">
				<input type="button" class="btn btn-success btn-block" onclick="doAgencyLogin(this);" value="<spring:message code="eip.commons.submitBT"/>"/>
		</div>
		<div class="col-lg-6">
				<input type="reset" class="btn btn-warning btn-block" value="<spring:message code="eip.agency.login.resetBT"/>" id="resettButon">
		</div> 
	
	</div>
	
	<div class="text-center margin-top-10">
        <a href="javascript:void(0);" onclick="getAgencyRegistrationForm()();"> <spring:message code="eip.agency.reg" text="Agency Register"/></a> |
		<a href="javascript:void(0);" onclick="getAgencyForgotPassStep1();">
			<spring:message code="eip.citizen.login.forgotPassword" text="Recover OTP"/>
		</a> |
		
		<a href="javascript:void(0);" onclick="getAgencyResetPassStepI();"><spring:message code="eip.agency.login.ResetPassword" text="Reset Password"/></a>
	</div>
	
</form:form>
</div>
</div>
</div>
</div>
</div>
</div>

