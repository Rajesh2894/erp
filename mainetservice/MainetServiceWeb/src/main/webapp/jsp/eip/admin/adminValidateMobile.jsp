<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<script>
$( document ).ready(function()
{
	$("#mobNo").focus();

});
$( '.form-control' ).on( "copy cut paste drop", function() {
    return false;
});

jQuery('.hasMobileNo').keyup(function () 
		{  this.value = this.value.replace(/[^0-9]/g,'');
		    $(this).attr('maxlength','10');
		});
</script>
<script>
(function($){
  $(document).ready(function(){		
	if(getCookie("accessibility")=='Y')
		{
		$("#captchaL").hide()
		}
  });
})(jQuery);
</script>

	<div class="widget login">
	<div class="widget-header">
		<h2>
			<strong>
		<spring:message code="eip.citizen.resetPassword.validateMobile.FormHeader" />
</strong>
</h2>
</div>
<div class="widget-content padding">

<!-- Admin Reset Password -->
	<div class="error-div alert alert-danger" id="rest_error"></div> 

	<form:form id="adminValidateMobileForm" name="adminValidateMobileForm" method="POST" autocomplete="on">
		<div class="form-group">
				<label class="control-label" for="newPassword"> 
					<spring:message	code="eip.citizen.resetPassword.validateMobile.MobileNoEmail" /> :
				</label> 
				</div>
			<div class="form-group">
					<form:input id="mobNo" cssClass="form-control" path="mobileNumber" onkeypress="return admintryStepI(event)"   autocomplete="off" />
		</div>
		<div class="form-group" id="captchaL">
					<div class=" margin-top-10" id="captchaLDiv">
						<c:set var="rand"><%=java.lang.Math.round(java.lang.Math.random() * 10000)%></c:set>
						<img id="cimg" src="AdminResetPassword.html?captcha&id=${rand}"  alt="captcha value <%=request.getDateHeader("captcha3")%>"/>
						<a href="#" onclick="doRefreshLoginCaptcha()" class="margin-left-20" tabindex="-1"><i
							class="fa fa-refresh fa-lg"><span class="hide">Refresh</span></i></a>
					</div>
					<div class="">
					<label for="captchaSessionLoginValue" class="hide">captchaP</label>
						<form:input path="captchaSessionLoginValue"
							cssClass="form-control margin-top-20" placeholder='${captchaP}'
							onkeypress="return admintryStepI(event)" autocomplete="off" />
					</div>
				</div>
				<div class="clear"></div>
		<div class="row">
			<div class="col-sm-6">
			<input type="button" class="btn btn-success btn-block" onclick="getAdminResetPassStepII();"	value="<spring:message code="eip.commons.submitBT" text="Submit"/>"/>
			</div>
			<div class="col-sm-6">
			<input type="reset" class="btn btn-danger btn-block" value="<spring:message code="eip.commons.resetBT"/>" onclick="{$('.error-div').html('');}">  
		</div>	
		</div>
		
	</form:form>
</div>
</div>